<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 15.02.2020
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="search">
    <div id="header"><c:import url="/pages/edition/header.jsp"/></div>
    <h1>Search</h1>
    <form action="/controller?command=search" method="post">
        <table style="with: 100%">
            <tr>
                <td>Title</td>
                <td><input type="text" name="title"/></td>
            </tr>
            <tr>
                <td>Author</td>
                <td><input type="text" name="author"/></td>
            </tr>
        </table>
        <input type="submit" value="Search"/>
    </form>
    <div id="footer"><c:import url="/pages/edition/footer.jsp"/></div>
</div>
</body>
</html>
