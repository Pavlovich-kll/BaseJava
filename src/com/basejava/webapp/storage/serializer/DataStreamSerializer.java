package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.basejava.webapp.model.SectionType.*;

public class DataStreamSerializer implements SerializeStrategy {

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

            readSelfInfo(dis, resume, OBJECTIVE);
            readSelfInfo(dis, resume, PERSONAL);
            readSkills(dis, resume, ACHIEVEMENT);
            readSkills(dis, resume, QUALIFICATIONS);
            readCompanies(dis, resume, EXPERIENCE);
            readCompanies(dis, resume, EDUCATION);
            return resume;
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.toString());
            }
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                Section section = entry.getValue();
                writeSelfInfo(entry, section, dos, resume, OBJECTIVE);
                writeSelfInfo(entry, section, dos, resume, PERSONAL);
                writeSkills(entry, section, dos, ACHIEVEMENT);
                writeSkills(entry, section, dos, QUALIFICATIONS);
                writeCompanies(entry, section, dos, resume, EXPERIENCE);
                writeCompanies(entry, section, dos, resume, EDUCATION);
            }
        }
    }

    public void readSelfInfo(DataInputStream dis, Resume resume, Enum PERSONAL) throws IOException {
        int sectionSize = dis.readInt();
        for (int i = 0; i < sectionSize; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            resume.setSection(sectionType, new SelfInfoSection(dis.readUTF()));
        }
    }

    public void readSkills(DataInputStream dis, Resume resume, Enum ACHIEVEMENT) throws IOException {
        int skillsSize = dis.readInt();
        for (int i = 0; i < skillsSize; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            resume.setSection(sectionType, new SkillsSection(dis.readUTF()));
        }
    }

    public void readCompanies(DataInputStream dis, Resume resume, Enum EXPERIENCE) throws IOException {
        int companiesSize = dis.readInt();
        List<Company> companies = new ArrayList<>();
        for (int i1 = 0; i1 < companiesSize; i1++) {
            String name = dis.readUTF();
            String url = dis.readUTF();
            int positionsCompanySize = dis.readInt();
            Company.Position positions = new Company.Position();
            for (int i2 = 0; i2 < positionsCompanySize; i2++) {
                String filling = dis.readUTF();
                YearMonth startDate = YearMonth.parse(dis.readUTF());
                YearMonth endDate = YearMonth.parse(dis.readUTF());
                positions = new Company.Position(filling, startDate, endDate);
            }
            companies.add(new Company(name, url, positions));
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            resume.setSection(sectionType, new ExperienceSection(companies));
        }
    }

    public void writeSelfInfo(Map.Entry<SectionType, Section> entry,Section section ,DataOutputStream dos, Resume resume, Enum OBJECTIVE) throws IOException {
        dos.writeInt(resume.getSections().size());
        SectionType sectionType = entry.getKey();
        dos.writeUTF(sectionType.toString());
        dos.writeUTF(section.toString());
    }

    public void writeSkills(Map.Entry<SectionType, Section> entry, Section section, DataOutputStream dos, Enum ACHIEVEMENT) throws IOException {
        List<String> skills = ((SkillsSection) section).getSkills();
        dos.writeInt(skills.size());
        for (String skill : skills) {
            dos.writeUTF(skill);
        }
    }

    public void writeCompanies(Map.Entry<SectionType, Section> entry, Section section, DataOutputStream dos, Resume resume, Enum EXPERIENCE) throws IOException {
        List<Company> companies = ((ExperienceSection) section).getCompanies();
        dos.writeInt(companies.size());
        for (Company company : companies) {
            List<Company.Position> positions = company.getPositions();
            dos.writeInt(positions.size());
            for (Company.Position position : positions) {
                dos.writeUTF(String.valueOf(position.getStartDate()));
                dos.writeUTF(String.valueOf(position.getEndDate()));
                dos.writeUTF(position.getFilling());
            }
        }
    }
}