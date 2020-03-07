<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 26.01.2020
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="bundle"/>
<html>
<head>
    <title><fmt:message key="locale.register" bundle="${bundle}"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div id="header"><c:import url="/pages/edition/header.jsp"/></div>
<div id="register" align="center">
    <h1><fmt:message key="locale.registerForm" bundle="${bundle}"/></h1>
    <form action="/controller?command=register" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="locale.firstName" bundle="${bundle}"/></label>
            <div class="col-sm-10">
                <input type="text" name="newFirstName" class="form-control">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="locale.lastName" bundle="${bundle}"/></label>
            <div class="col-sm-10">
                <input type="text" name="newLastName" class="form-control">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="locale.email" bundle="${bundle}"/></label>
            <div class="col-sm-10">
                <input type="email" name="newEmail" class="form-control">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="locale.password" bundle="${bundle}"/></label>
            <div class="col-sm-10">
                <input type="password" name="newPassword" class="form-control">
            </div>
        </div>
        <input type="submit" value="<fmt:message key="locale.register" bundle="${bundle}"/>"/>
    </form>
</div>
<div id="err"><c:import url="/pages/edition/err.jsp"/></div>
</body>
</html>
