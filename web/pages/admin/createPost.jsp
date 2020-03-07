<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 11.02.2020
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create Post</title>
</head>
<body>
<h1>Create post</h1>
<h3>Please, select file to upload</h3> <br/>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="create_post"/>
    <table style="with: 100%">
        <tr>
            <td>Title</td>
            <td><input type="text" name="title"/></td>
        </tr>
        <tr>
            <td>Path to the cover of the post</td>
            <td><input type="text" name="pathImg"/></td>
        </tr>
        <tr>
            <td>Path to the post</td>
            <td><input type="text" name="post"/></td>
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
    <input type="submit" value="Create Post"/>
</form>
<div id="err"><c:import url="/pages/edition/err.jsp"/></div>
</body>
</html>
