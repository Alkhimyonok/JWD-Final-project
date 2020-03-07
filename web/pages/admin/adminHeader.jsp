<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 15.02.2020
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="bundle"/>
<html>
<head>
    <title>Header</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand">
        <fmt:message key="locale.header.logo" bundle="${bundle}"/>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
                   role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    <fmt:message key="locale.header.drop" bundle="${bundle}"/>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="/controller?command=list_editions&param=admin">
                        <fmt:message key="locale.header.list" bundle="${bundle}"/>
                    </a>
                    <a class="dropdown-item" href="/controller?command=list_editions&type=magazine&param=admin">
                        <fmt:message key="locale.header.magazines" bundle="${bundle}"/>
                    </a>
                    <a class="dropdown-item" href="/controller?command=list_editions&type=newspaper&param=admin">
                        <fmt:message key="locale.header.newspapers" bundle="${bundle}"/>
                    </a>
                    <a class="dropdown-item" href="/controller?command=list_editions&type=comics&param=admin">
                        <fmt:message key="locale.header.comics" bundle="${bundle}"/>
                    </a>
                </div>
            </li>
            <c:set var="user" scope="session" value="${sessionScope.user}"/>
            <li class="nav-item">
                <a class="nav-link disabled" href="/pages/admin/createPost.jsp">
                    <fmt:message key="locale.header.admin.createPost" bundle="${bundle}"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="/pages/admin/createEdition.jsp">
                    <fmt:message key="locale.header.admin.createEdition" bundle="${bundle}"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="/controller?command=list_subscribe_credit">
                    <fmt:message key="locale.header.admin.userCredit" bundle="${bundle}"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="/controller?command=logout&param=admin">
                    <fmt:message key="locale.header.logout" bundle="${bundle}"/>
                </a>
            </li>
        </ul>
    </div>
</nav>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>
