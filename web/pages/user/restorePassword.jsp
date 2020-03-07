<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 22.02.2020
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Restore Password</title>
</head>
<body>
<div id="header"><c:import url="/pages/edition/header.jsp"/></div>
<div id="register" align="center">
    <h1>Reset password</h1>
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="reset_password"/>
        <table style="with: 100%">
            <tr>
                <td>First Name</td>
                <td><input type="text" name="firstName"/></td>
            </tr>
            <tr>
                <td>Last Name</td>
                <td><input type="text" name="lastName"/></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="email" name="email"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="newPassword1"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="newPassword2"/></td>
            </tr>
        </table>
        <input type="submit" value="Restore password"/>
    </form>
</div>
<div id="err"><c:import url="/pages/edition/err.jsp"/></div>
</body>
</html>
