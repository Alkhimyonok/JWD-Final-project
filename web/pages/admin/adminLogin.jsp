<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 12.02.2020
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin Login</title>
</head>
<body>
<div id="register" align="center">
    <h1>Admin Login Form</h1>
    <form action="/controller?param=admin" method="post">
        <input type="hidden" name="command" value="login"/>
        <table style="with: 100%">
            <tr>
                <td>Email</td>
                <td><input type="email" name="email"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password"/></td>
            </tr>
        </table>
        <input type="submit" value="Sign in"/>
    </form>
</div>
<div id="err"><c:import url="/pages/edition/err.jsp"/></div>
</body>
</html>
