<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.ExperienceSection" %>
<%@ page import="com.basejava.webapp.model.SkillsSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.basejava.webapp.model.Section"/>
            <tr>
                <td><h3><a name="type.name">${type.title}</a></h3></td>
            </tr>
            <c:choose>
                <c:when test="${type=='PERSONAL'}">
                    <textarea name='${type}' cols=75 rows=5><%=section%></textarea>
                </c:when>
                <c:when test="${type=='OBJECTIVE'}">
                    <textarea name='${type}' cols=75 rows=5><%=section%></textarea>
                </c:when>

                <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                    <textarea name='${type}' cols=100
                              rows=10><%=String.join("\n", ((SkillsSection) section).getSkills())%></textarea>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="company" varStatus="counter"
                               items="<%=((ExperienceSection) section).getCompanies()%>">
                        <dl>
                            <dt>Название учреждения:</dt>
                            <dd><input type="text" name='${type}' value="${company.link.name}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт:</dt>
                            <dd><input type="text" name='${type}url' value="${company.link.url}"></dd>
                        </dl>
                        <div style="margin-left: 30px">
                        <c:forEach var="position" items="${company.positions}">
                            <dl>
                                <dt>Период:</dt>
                                <dd><input type="text" placeholder="yyyy-MM" name="${type}${counter.index}startDate"
                                           value="${position.startDate}"></dd>
                                <dd><input type="text" placeholder="yyyy-MM" name="${type}${counter.index}endDate"
                                           value="${position.endDate}"></dd>
                            </dl>
                            <dl>
                                <dt>Должность:</dt>
                                <dd><input type="text" name="${type}${counter.index}position"
                                           value="${position.position}"></dd>
                            </dl>
                            <dl>
                                <dt>Описание:</dt>
                                <dd><input type="text" name="${type}${counter.index}description"
                                           value="${position.description}"></dd>
                            </dl>
                        </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="javascript:history.go(-1); return false;">Отмена</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>