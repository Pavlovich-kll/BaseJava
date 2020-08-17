package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.util.DateUtil;
import com.basejava.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    //https://ru.stackoverflow.com/questions/1018236/Вывод-таблицы-БД-через-сервлет
//https://docstore.mik.ua/orelly/java-ent/servlet/ch03_03.htm
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        if (HtmlUtil.isNotEmpty(uuid)) {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        } else {
            resume = new Resume(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isNotEmpty(value)) {
                resume.setContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());

            if (HtmlUtil.isNotEmpty(value)) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSection(type, new SelfInfoSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.setSection(type, new SkillsSection(value.split("\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Company> companiesList = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            List<Company.Position> positionsList = new ArrayList<>();
                            String typeName = type.name() + i;
                            String[] positions = request.getParameterValues(typeName + "position");
                            String[] descriptions = request.getParameterValues(typeName + "description");
                            String[] startDate = request.getParameterValues(typeName + "startDate");
                            String[] endDate = request.getParameterValues(typeName + "endDate");
                            for (int j = 0; j < positions.length; j++) {
                                if (HtmlUtil.isNotEmpty(positions[j])) {
                                    positionsList.add(new Company.Position(
                                            positions[j],
                                            descriptions[j],
                                            DateUtil.parse(startDate[j]),
                                            DateUtil.parse(endDate[j])));
                                }
                            }
                            companiesList.add(new Company(new Link(name, urls[i]), positionsList));
                        }
                        resume.setSection(type, new ExperienceSection(companiesList));
                        break;
                }
            }
        }
        if (!HtmlUtil.isNotEmpty(uuid)) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "add":
                resume = Resume.EMPTY_RESUME;
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");//меняем url после удаления, т.е. запрос на указанный location resume;
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType sectionType : SectionType.values()) {
                    Section section = resume.getSection(sectionType);
                    switch (sectionType) {
                        case PERSONAL:
                        case OBJECTIVE:
                            if (section == null) {
                                section = SelfInfoSection.empty;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = SkillsSection.empty;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            ExperienceSection experienceSection = (ExperienceSection) section;
                            List<Company> list = new ArrayList<>();
                            if (experienceSection == null) {
                                section = new ExperienceSection(Company.empty);
                            } else {
                                for (Company company : experienceSection.getCompanies()) {
                                    List<Company.Position> positions = new ArrayList<>(company.getPositions());
                                    list.add(new Company(company.getLink(), positions));
                                }
                                section = new ExperienceSection(list);
                            }
                            break;
                    }
                    resume.setSection(sectionType, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}