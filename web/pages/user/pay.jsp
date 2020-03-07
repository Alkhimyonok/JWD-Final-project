<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 01.02.2020
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Payment</title>
</head>
<body>
<div id="header"><c:import url="/pages/edition/header.jsp"/></div>
<div align="center">
    <h1>Pay</h1>
    <c:set var="total" value="${param.total}"/>
    <form action="/controller?command=pay&total=${total}&param=${param.param}&id=${param.id}" method="post">
        <table style="with: 100%">
            <tr>
                <td>Bank account number</td>
                <td><input name="accountNumber"/></td>
            </tr>
            <tr>
                <td>Code</td>
                <td><input name="code"/></td>
            </tr>

        </table>
        <input type="submit" value="Pay"/>
    </form>
    <div id="err"><c:import url="/pages/edition/err.jsp"/></div>
</div>
</body>
</html>
