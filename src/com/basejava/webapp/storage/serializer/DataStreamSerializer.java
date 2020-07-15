package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializeStrategy {
    private final String dummy = "";

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = resume.getSections();
            writeCollection(dos, sections.entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                Section section = entry.getValue();
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((SelfInfoSection) section).getSelfInfo());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((SkillsSection) section).getSkills(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((ExperienceSection) section).getCompanies(), company -> {
                            dos.writeUTF(company.getLink().getName());
                            dos.writeUTF(company.getLink().getUrl() == null ? dummy : company.getLink().getUrl());
                            writeCollection(dos, company.getPositions(), position -> {
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription() == null ? dummy : position.getDescription());
                                dos.writeUTF(String.valueOf(position.getStartDate()));
                                dos.writeUTF(String.valueOf(position.getEndDate()));
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readItems(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.setSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new SelfInfoSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new SkillsSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new ExperienceSection(readList(dis, () -> new Company(
                        new Link(dis.readUTF(), readUrl(dis)), readList(dis, () -> new Company.Position(
                        dis.readUTF(), readDescription(dis), YearMonth.parse(dis.readUTF()), YearMonth.parse(dis.readUTF())
                ))
                )));
            default:
                throw new IllegalStateException("section type error");
        }
    }

    private String readDescription(DataInputStream dis) throws IOException {
        String notDummyDescription = dis.readUTF();
        return notDummyDescription.equals(dummy) ? null : notDummyDescription;
    }

    private String readUrl(DataInputStream dis) throws IOException {
        String notDummyUrl = dis.readUTF();
        return notDummyUrl.equals(dummy) ? null : notDummyUrl;
    }

    @FunctionalInterface
    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    private interface ElementReader<T> {
        T read() throws IOException;
    }

    @FunctionalInterface
    private interface ElementProcessor {
        void process() throws IOException;
    }

    private void readItems(DataInputStream dis, ElementProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T skill : collection) {
            writer.write(skill);
        }
    }
}