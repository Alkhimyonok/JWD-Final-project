<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 02.02.2020
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tr" uri="translateTag" %>
<html>
<head>
    <title></title>
</head>
<body>
<div id="header"><c:import url="/pages/edition/header.jsp"/></div>
<div id="content">
    <ul class="author">
        <li>
            <h3>${author.name}
            </h3>
            <h4><tr:translateTag info="${author.description}" locale="${sessionScope.locale}"/>
            </h4>
        </li>
    </ul>
</div>
<div id="editions">
    <c:forEach items="${list}" var="edition">
        <a href="/controller?command=detail_edition&idEdition=${edition.id}"><span>${edition.title}</span></a>
    </c:forEach>
    <div id="footer"><c:import url="/pages/edition/footer.jsp"/></div>
</div>
</body>
</html>