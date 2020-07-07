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
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();

            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                SectionType sectionType = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(sectionType.toString());
                switch (sectionType) {
                    case OBJECTIVE:
                        writeSelfInfo(section, dos, OBJECTIVE);
                        break;
                    case PERSONAL:
                        writeSelfInfo(section, dos, PERSONAL);
                        break;
                    case ACHIEVEMENT:
                        writeSkills(section, dos, ACHIEVEMENT);
                        break;
                    case QUALIFICATIONS:
                        writeSkills(section, dos, QUALIFICATIONS);
                        break;
                    case EXPERIENCE:
                        writeCompanies(section, dos, EXPERIENCE);
                        break;
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
                    readSelfInfo(dis, resume, OBJECTIVE);
                    break;
                    case PERSONAL:
                    readSelfInfo(dis, resume, PERSONAL);
                    break;
                    case ACHIEVEMENT:
                    readSkills(dis, resume, ACHIEVEMENT);
                    break;
                    case QUALIFICATIONS:
                    readSkills(dis, resume, QUALIFICATIONS);
                    break;
                    case EXPERIENCE:
                    readCompanies(dis, resume, EXPERIENCE);
                    break;
                    case EDUCATION:
                    readCompanies(dis, resume, EDUCATION);
                    break;
                }
            }
            return resume;
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