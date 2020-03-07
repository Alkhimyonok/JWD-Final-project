<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 30.01.2020
  Time: 0:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="bundle"/>
<html>
<head>
    <title>Editions</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div id="header"><c:import url="/pages/edition/header.jsp"/></div>
<div id="main">
    <c:forEach items="${list}" var="edition">
        <div class="card" style="width: 30rem;">
            <img src="${edition.pathImg}" class="card-img-top" alt="please, wait">
            <div class="card-body">
                <h5 class="card-title">${edition.title}</h5>
                <p class="card-text"><fmt:message key="locale.price" bundle="${bundle}"/>${edition.priceMonth}$</p>
                <a href="/controller?command=detail_edition&idEdition=${edition.id}" class="btn btn-primary">
                    <fmt:message key="locale.detail" bundle="${bundle}"/></a>
            </div>
        </div>
    </c:forEach>

    <div id="err"><c:import url="/pages/edition/err.jsp"/></div>
    <div id="footer"><c:import url="/pages/edition/footer.jsp"/></div>
</div>
</body>
</html>
