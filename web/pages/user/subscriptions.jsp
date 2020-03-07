<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 02.02.2020
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Subscriptions</title>
</head>
<body>
<div id="header"><c:import url="/pages/edition/header.jsp"/></div>
<div id="subscriptions">
    <ul>
        <c:forEach items="${list}" var="subscribe">
        <li>
            <img src="${subscribe.edition.pathImg}" width=" 250px" height="250px"/>
            <h3>${subscribe.edition.title}
            </h3>
            <h4>${subscribe.edition.type}
            </h4>
            <h4>${subscribe.edition.priceMonth}$
            </h4>
            <h4>${subscribe.date}
            </h4>
        </li>
    </ul>
    <c:if test="${subscribe.status eq 'done'}">
        <form action="/controller?command=unsubscribe&idSubscribe=${subscribe.id}" method="post">
            <input type="submit" value="Unsubscribe"/>
        </form>
    </c:if>
    <c:if test="${subscribe.status eq 'credit'}">
        <a class="checkout" href="/pages/user/pay.jsp?total=${subscribe.edition.priceMonth}&id=${subscribe.id}">Paying</a>
    </c:if>
    </c:forEach>
    <div id="footer"><c:import url="/pages/edition/footer.jsp"/></div>
</div>
</body>
</html>
