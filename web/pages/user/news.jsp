<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 31.01.2020
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>News</title>
</head>
<body>
<div id="header"><c:import url="/pages/edition/header.jsp"/></div>
<div id="main">
    <div id="main-center">
        <div id="site-wrapper-p" style="float: left; margin-left: 120px">
            <ul class="news homepage">
                <c:forEach items="${news}" var="post">
                    <li><span class="tagimg"></span>
                        <a href="${post.post}">
                            <img src="${post.pathImg}" width=" 250px" height="250px"/>
                            <h3>${post.edition.title}
                            </h3>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div id="footer"><c:import url="/pages/edition/footer.jsp"/></div>
</div>
</body>
</html>