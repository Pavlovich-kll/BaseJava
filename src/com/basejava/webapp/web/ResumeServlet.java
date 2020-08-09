package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + " !");
        printTable(response);

    }

    private void printTable(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        List<Resume> list = storage.getAllSorted();
        writer.println(
                "<html>\n" +
                        "<head>\n" +
                            "<title>List of resumes</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<table>\n" +
                            "<tr>\n" +
                                "<th>UUID</th>\n" +
                                "<th>Full Name</th>\n" +
                            "</tr>");

        for (Resume resume : list) {
            writer.println("<tr>");
            writer.println("<td>" + resume.getUuid() + "</td>");
            writer.println("<td>" + resume.getFullName() + "</td>");
            writer.println("</tr>");
        }

        writer.println(
                        "</table>\n" +
                        "</body\n" +
                "</html>\n");
    }
}