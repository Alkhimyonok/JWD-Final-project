<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 22.01.2020
  Time: 22:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>
<div id="header"><c:import url="/pages/edition/header.jsp"/></div>
<div id="register" align="center">
    <h1>User Login Form</h1>
    <form action="/controller" method="post">
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
    <a href="/pages/user/restorePassword.jsp"><span>Restore password</span></a>
</div>
<div id="err"><c:import url="/pages/edition/err.jsp"/></div>
<div id="footer"><c:import url="/pages/edition/footer.jsp"/></div>
</body>
</html>
