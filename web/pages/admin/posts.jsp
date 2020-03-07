<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 15.02.2020
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="bundle"/>
<html>
<head>
    <title>posts</title>
</head>
<body>
<div id="header"><c:import url="/pages/admin/adminHeader.jsp"/></div>
<div id="main">
    <div id="main-center">
        <c:forEach items="${posts}" var="post">
            <div id="site-wrapper-p" style="float: left; margin-left: 120px">
                <ul class="news homepage">
                    <li><span class="tagimg"></span>
                        <a href="${post.post}">
                            <img src="${post.pathImg}" width=" 250px" height="250px"/>
                            <h3>${post.edition.title}
                            </h3>
                        </a>
                    </li>
                </ul>
                <div class="edit-button">
                    <a class="checkout" href="/controller?command=delete_post&post=${post.post}">
                        <fmt:message key="locale.admin.post.delete" bundle="${bundle}"/> </a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<div id="err"><c:import url="/pages/edition/err.jsp"/></div>
</body>
</html>
