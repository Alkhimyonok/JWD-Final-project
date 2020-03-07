<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 15.02.2020
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="bundle"/>
<html>
<head>
    <title>Editions</title>
</head>
<body>
<div id="header"><c:import url="/pages/admin/adminHeader.jsp"/></div>
<div id="main">
    <div id="main-center">
        <c:forEach items="${list}" var="edition">
            <div id="site-wrapper-p" style="float: left; margin-left: 120px">
                <ul class="products homepage">

                    <li><span class="tagimg"></span>
                        <a href="/controller?command=posts_edition&idEdition=${edition.id}">
                            <img src="${edition.pathImg}" width=" 250px" height="250px"/>
                            <h3>${edition.title}
                            </h3>
                            <h3>Company: ${edition.author.name}
                            </h3>
                            <h3>Monthly subscription: ${edition.priceMonth}$
                            </h3>
                        </a>
                    </li>
                </ul>
                <div class="edit-button">
                    <a class="checkout" href="/controller?command=delete_edition&idEdition=${edition.id}">
                        <fmt:message key="locale.admin.editions.delete" bundle="${bundle}"/> </a>
                    <a class="checkout" href="/controller?command=posts_edition&idEdition=${edition.id}">
                        <fmt:message key="locale.admin.editions.posts" bundle="${bundle}"/> </a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
