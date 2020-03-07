<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 20.01.2020
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="bundle"/>
<html>
<head>
    <title><fmt:message key="locale.index.title" bundle="${bundle}"/></title>
</head>

<body>
<div id="main">
    <div id="header"><c:import url="/pages/edition/header.jsp"/></div>
    <div id="main-center">

        <div id="site-wrapper-p">
            <h2>Novelty</h2>
            <ul class="products homepage">
                <c:forEach items="${novelty}" var="post">
                    <li><span class="tagimg"></span>
                        <a href="/controller?command=detail_edition&idEdition=${post.edition.id}">
                            <img src="${post.pathImg}" width=" 250px" height="250px"/>
                            <h3>${post.edition.title}</h3>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div id="site-wrapper-p">
            <h2>Top editions</h2>
            <ul class="products homepage">
                <c:forEach items="${top}" var="edition">
                    <li><span class="tagimg"></span>
                        <a href="/controller?command=detail_edition&idEdition=${edition.id}">
                            <img src="${edition.pathImg}" width=" 250px" height="250px"/>
                            <h3>${edition.title}</h3>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div id="footer"><c:import url="/pages/edition/footer.jsp"/></div>
</div>
</body>
</html>