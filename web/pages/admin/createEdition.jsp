<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 16.02.2020
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Edition</title>
</head>
<body>
<div id="header"><c:import url="/pages/admin/adminHeader.jsp"/></div>
<div id="create edition">
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="create_edition"/>
        <table style="with: 100%">
            <tr>
                <td>Title</td>
                <td><input type="text" name="title"/></td>
            </tr>
            <tr>
                <td>Author name</td>
                <td><input type="text" name="author"/></td>
            </tr>
            <tr height="12%" width="12%">
                <td>Description</td>
                <td><input type="text" name="description"/></td>
            </tr>
            <tr>
                <td>Path to the cover of the edition</td>
                <td><input type="text" name="pathImg"/></td>
            </tr>
            <tr>
                <td>Monthly price</td>
                <td><input type="number" name="priceMonth"/></td>
            </tr>
            <input type="radio" id="magazine"
                   name="type" value="magazine" checked>
            <label for="magazine">Magazine</label>

            <input type="radio" id="newspaper"
                   name="type" value="newspaper">
            <label for="newspaper">Newspaper</label>

            <input type="radio" id="comics"
                   name="type" value="comics">
            <label for="comics">Comics</label>
        </table>
        <input type="submit" value="Create Edition"/>
    </form>
</div>
<div id="err"><c:import url="/pages/edition/err.jsp"/></div>
</body>
</html>
