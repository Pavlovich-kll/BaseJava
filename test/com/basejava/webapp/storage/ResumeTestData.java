package com.basejava.webapp.storage;

import com.basejava.webapp.model.*;

import java.time.YearMonth;
import java.util.List;

public class ResumeTestData {
    private static String uuid1;
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static Resume resume_1;
    private static final Resume resume_2 = new Resume("uuid2","name2");
    private static final Resume resume_3 = new Resume("uuid3","name3");;

    public static void main(String[] args) {
        resume_1 = new Resume("uuid1", "Grigory Kislin");
        uuid1 = resume_1.getUuid();

        resume_1.setContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume_1.setContact(ContactType.SKYPE, "grigory.kislin");
        resume_1.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume_1.setContact(ContactType.LINKEDIN, new Link("Linkedin","https://www.linkedin.com/in/gkislin"));
        resume_1.setContact(ContactType.GITHUB, new Link("Профиль GitHub", "https://github.com/gkislin"));
        resume_1.setContact(ContactType.STACKOVERFLOW, new Link("Профиль GitHub", "https://stackoverflow.com/users/548473/grigory-kislin"));
        resume_1.setContact(ContactType.HOME_PAGE, new Link("Профиль GitHub", "http://gkislin.ru/"));

        resume_1.setSection(SectionType.OBJECTIVE, new SelfInfoSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume_1.setSection(SectionType.PERSONAL, new SelfInfoSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume_1.setSection(SectionType.ACHIEVEMENT, new SkillsSection(List.of("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.")));
        resume_1.setSection(SectionType.QUALIFICATIONS, new SkillsSection(List.of("Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy;",
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2;",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce.")));
        resume_1.setSection(SectionType.EXPERIENCE, new ExperienceSection(
                new Company("Wrike","https://www.wrike.com/", "something", YearMonth.of(2014, 10), YearMonth.of(2016, 1)),
                new Company("Luxoft","https://career.luxoft.com/locations/russia/", "something", YearMonth.of(2010, 12), YearMonth.of(2012, 4))));

        resume_1.setSection(SectionType.EDUCATION, new ExperienceSection(
                new Company("Coursera", "https://www.coursera.org/learn/progfun1", "\"Functional Programming Principles in Scala\" by Martin Odersky", YearMonth.of(2013, 3), YearMonth.of(2013, 5)),
                new Company("Luxoft", "https://www.luxoft-training.ru","Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", YearMonth.of(2011, 3), YearMonth.of(2011, 4))));

    }
}