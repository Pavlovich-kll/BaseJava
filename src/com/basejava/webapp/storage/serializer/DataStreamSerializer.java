package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.basejava.webapp.model.SectionType.*;

public class DataStreamSerializer implements SerializeStrategy {
    private final static String dummy = "";

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            dos.writeInt(resume.getContacts().size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            dos.writeInt(resume.getSections().size());
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.toString());
                Section section = entry.getValue();
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        writeSelfInfo(section, dos, PERSONAL);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeSkills(section, dos, QUALIFICATIONS);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCompanies(section, dos, EDUCATION);
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int index = 0; index < sectionsSize; index++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.setSection(sectionType, new SelfInfoSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        readSkills(dis, sectionType, resume, QUALIFICATIONS);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        readCompanies(dis, sectionType, resume, EDUCATION);
                        break;
                }
            }
            return resume;
        }
    }

    public void readSkills(DataInputStream dis, SectionType sectionType, Resume resume, Enum ACHIEVEMENT) throws IOException {
        int skillsSize = dis.readInt();
        List<String> skills = new ArrayList<>();
        for (int i = 0; i < skillsSize; i++) {
            skills.add(dis.readUTF());
        }
        resume.setSection(sectionType, new SkillsSection(skills));
    }

    public void readCompanies(DataInputStream dis, SectionType sectionType, Resume resume, Enum EXPERIENCE) throws IOException {
        List<Company> companies = new ArrayList<>();
        int companiesSize = dis.readInt();
        for (int i1 = 0; i1 < companiesSize; i1++) {
            String name = dis.readUTF();
            String notDummyUrl = dis.readUTF();
            String url = notDummyUrl.equals(dummy) ? null : notDummyUrl;
            Link link = new Link(name, url);
            List<Company.Position> positions = new ArrayList<>();
            int positionsSize = dis.readInt();
            for (int i2 = 0; i2 < positionsSize; i2++) {
                YearMonth startDate = YearMonth.parse(dis.readUTF());
                YearMonth endDate = YearMonth.parse(dis.readUTF());
                String notDummyDescription = dis.readUTF();
                String description = notDummyDescription.equals(dummy) ? null : notDummyDescription;
                String title = dis.readUTF();
                positions.add(new Company.Position(title, description, startDate, endDate));
            }
            companies.add(new Company(link, positions));
            resume.setSection(sectionType, new ExperienceSection(companies));
        }
    }

    public void writeSelfInfo(Section section, DataOutputStream dos, Enum OBJECTIVE) throws IOException {
        dos.writeUTF(((SelfInfoSection) section).getSelfInfo());
    }

    public void writeSkills(Section section, DataOutputStream dos, Enum ACHIEVEMENT) throws IOException {
        List<String> skills = ((SkillsSection) section).getSkills();
        dos.writeInt(skills.size());
        for (String skill : skills) {
            dos.writeUTF(skill);
        }
    }

    public void writeCompanies(Section section, DataOutputStream dos, Enum EXPERIENCE) throws IOException {
        List<Company> companies = ((ExperienceSection) section).getCompanies();
        int companySize = companies.size();
        dos.writeInt(companySize);
        for (Company company : companies) {
            Link link = company.getLink();
            dos.writeUTF(link.getName());
            dos.writeUTF(link.getUrl() == null ? dummy : link.getUrl());
            List<Company.Position> positions = company.getPositions();
            int positionSize = positions.size();
            dos.writeInt(positionSize);
            for (Company.Position position : positions) {
                dos.writeUTF(String.valueOf(position.getStartDate()));
                dos.writeUTF(String.valueOf(position.getEndDate()));
                dos.writeUTF(position.getDescription() == null ? dummy : position.getDescription());
                dos.writeUTF(position.getTitle());
            }
        }
    }
}