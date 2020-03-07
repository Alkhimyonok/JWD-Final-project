<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 16.02.2020
  Time: 23:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="bundle"/>
<html>
<head>
    <title>Subscriptions in credit</title>
</head>
<body>
<div id="header"><c:import url="/pages/admin/adminHeader.jsp"/></div>
<div id="main">
    <c:forEach items="${creditList}" var="subscribe">
        <div id="site-wrapper-p" style="float: left; margin-left: 120px">
            <ul class="products homepage">
                <li><span class="tagimg"></span>
                    <h3>${subscribe.date}
                    </h3>
                    <h3>Email user: ${subscribe.user.email}
                    </h3>
                    <h3>Status user: ${subscribe.user.status}
                    </h3>
                    <a href="/controller?command=block_user&idUser=${subscribe.user.id}">
                        <fmt:message key="locale.admin.blockUser" bundle="${bundle}"/> </a>
                </li>
            </ul>
        </div>
    </c:forEach>
</div>
</body>
</html>
