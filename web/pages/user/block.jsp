<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 17.02.2020
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="bundle"/>
<html>
<head>
    <title><fmt:message key="locale.block.title" bundle="${bundle}"/></title>
</head>
<body>
<div id="header"><c:import url="/pages/edition/header.jsp"/></div>
<div id="main">
    <div id="main-center">
        <div id="block">
            <h2><fmt:message key="locale.err.block" bundle="${bundle}"/></h2>
            <c:set var="total" value="${0}"/>
            <c:forEach items="${blockList}" var="subscribe">
                <ul class="subscribe-credit">
                    <li><img src="${subscribe.edition.pathImg}" width=" 250px" height="250px"/></li>
                    <li><h3>${subscribe.date}</h3></li>
                    <li><h3>${subscribe.edition.priceMonth}</h3></li>
                    <c:set var="total" value="${total+subscribe.edition.priceMonth}"/>
                </ul>
            </c:forEach>
            <a href="/pages/user/pay.jsp?total=${total}&param=block">
                <span><fmt:message key="locale.pay" bundle="${bundle}"/></span>
            </a>
        </div>
    </div>
</div>
</body>
</html>
