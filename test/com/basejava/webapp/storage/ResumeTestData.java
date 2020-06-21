package com.basejava.webapp.storage;

import com.basejava.webapp.model.*;

import java.time.LocalDate;

public class ResumeTestData {
    public static Resume resume_1;
    public static String UUID1;

    public static void main(String[] args) {
        resume_1 = new Resume("1", "KislinSergay");

        resume_1.setContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume_1.setContact(ContactType.SKYPE, "grigory.kislin");
        resume_1.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume_1.setContact(ContactType.LINKEDIN, "Профиль LinkedIn");
        resume_1.setContact(ContactType.GITHUB, "Профиль GitHub");
        resume_1.setContact(ContactType.STACKOVERFLOW, "Профиль Stackoverflow");
        resume_1.setContact(ContactType.HOME_PAGE, "Домашняя страница");

        resume_1.setSection(SectionType.PERSONAL, new SelfInfoSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume_1.setSection(SectionType.OBJECTIVE, new SelfInfoSection("position"));
        resume_1.setSection(SectionType.QUALIFICATIONS, new SkillsSection("Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,"));
        resume_1.setSection(SectionType.EXPERIENCE, new ExperienceSection(
                new Company("Wrike", "something", LocalDate.of(2014, 10, 30), LocalDate.of(2016, 1, 30)),
                new Company("Luxoft", "something", LocalDate.of(2010, 12, 30), LocalDate.of(2012, 4, 30))));

        resume_1.setSection(SectionType.EDUCATION, new ExperienceSection(
                new Company("Coursera", "gogogog", LocalDate.of(2013, 3, 30), LocalDate.of(2013, 5, 30)),
                new Company("Luxoft", "gogogog", LocalDate.of(2011, 3, 30), LocalDate.of(2011, 4, 30))));

        UUID1 = resume_1.getUuid();
    }
}