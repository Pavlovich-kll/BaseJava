<%@ page import="com.basejava.webapp.model.ExperienceSection" %>
<%@ page import="com.basejava.webapp.model.SelfInfoSection" %>
<%@ page import="com.basejava.webapp.model.SkillsSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--https://javawebinar.github.io/--%>

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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <table cellspacing="1">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.basejava.webapp.model.Section"/>
            <tr>
                <td colspan="2">
                    <h3>
                        <a name="type.name">${type.title}</a>
                    </h3>
                </td>
            </tr>
            <c:choose>
                <c:when test="${type=='PERSONAL'}">
                    <tr>
                        <td colspan="2">
                            <%=((SelfInfoSection) section).getSelfInfo()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='OBJECTIVE'}">
                    <tr>
                        <td colspan="2">
                            <%=((SelfInfoSection) section).getSelfInfo()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                    <tr>
                        <td colspan="2">
                            <c:forEach var="value" items="<%=((SkillsSection) section).getSkills()%>">
                                <li>${value}</li>
                            </c:forEach>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="company" items="<%=((ExperienceSection) section).getCompanies()%>">
                        <tr>
                            <td colspan="2">
                                <h4>
                                <a href="${company.link.url}">${company.link.name}</a>
                                </h4>
                            </td>
                        </tr>
                        <c:forEach var="position" items="${company.positions}">
                            <tr>
                                <td width="15%" style="vertical-align: top">${position.startDate} / ${position.endDate}</td>
                                <td><span style="font-weight: bold">${position.position}</span><br>${position.description}
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>