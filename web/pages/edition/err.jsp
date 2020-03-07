<%@ page import="com.epam.jwd.web_app.dao.DaoFactory" %><%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 15.02.2020
  Time: 10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="bundle"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div id="err">
    <c:set var="err" scope="request" value="${requestScope.err}"/>
    <c:if test="${err!=null}">
        <div class="alert alert-danger" role="alert">
            <h5><fmt:message key="${err}" bundle="${bundle}"/>
        </div>
    </c:if>
</div>
</body>
</html>
