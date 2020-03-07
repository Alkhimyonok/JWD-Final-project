<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 19.02.2020
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="register" align="center">
    <h1>User Register Form</h1>
    <form action="/controller?command=update_user" method="post">
        <table style="with: 80%">
            <tr>
                <td>First name</td>
                <input type="text" name="newFirstName" value="${user.firstName}"/>
            </tr>
            <tr>
                <td>Last name</td>
                <input type="text" name="newLastName" value="${user.lastName}"/>
            </tr>
            <tr>
                <td>Email</td>
                <input type="email" name="newEmail" value="${user.email}"/>
            </tr>
        </table>
        <input type="submit" value="Save"/>
    </form>
    <a href="/pages/user/resetPassword.jsp">
        <span>Reset Password</span>
    </a>
    <a href="/controller?command=delete_user">
        <span>Delete user</span>
    </a>
    <div id="footer"><c:import url="/pages/edition/footer.jsp"/></div>
</div>
</body>
</html>
