<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 28.01.2020
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<div id="header"><c:import url="/pages/edition/header.jsp"/></div>
<div id="main">
    <div class="product">
        <c:set var="total" value="${0}"/>
        <c:forEach items="${cart}" var="subscribe">
            <li>
                <a href="/controller?command=detail_edition&idEdition=${subscribe.edition.id}">
                    <img src="${subscribe.edition.pathImg}" width=" 250px"
                         height="250px"/>
                    <span>${subscribe.edition.title}
                    </span>
                </a>
                <a href="/controller?command=detail_author&idAuthor=${subscribe.edition.author.id}">
    <span>Author: ${subscribe.edition.author.name}
    </span>
                </a>
                <span>Monthly subscription: ${subscribe.edition.priceMonth}
    $</span>
                <c:set var="total" value="${total+subscribe.edition.priceMonth}"/>
            </li>
            <form action="/controller?command=delete_subscribe&idSubscribe=${subscribe.id}"
                  method="post">
                <input type="submit" value="Delete from cart"/>
            </form>
        </c:forEach>

    </div>
    <div class="totals">
        <label>Result</label>
        <h6>${total}$</h6>
        <a class="checkout" href="/pages/user/pay.jsp?total=${total}">Paying</a>
        <a class="checkout" href="/controller?command=credit">In credit</a>
    </div>
    <div id="err"><c:import url="/pages/edition/err.jsp"/></div>
    <div id="footer"><c:import url="/pages/edition/footer.jsp"/></div>
</div>
</body>
</html>
