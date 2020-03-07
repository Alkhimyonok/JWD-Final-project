<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 13.02.2020
  Time: 2:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Language</title>
</head>
<body>
<div id="locale-hello">
    <h1>Hello, dear friend!</h1>
    <h3>Please, choose a language</h3>
</div>
<div id="locale-choose">
    <a href="controller?command=index&lang=ru">RU</a>
    <a href="controller?command=index&lang=en">EN</a>
</div>
</body>
</html>
