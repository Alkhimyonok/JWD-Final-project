<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 27.01.2020
  Time: 22:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tr" uri="translateTag" %>
<html>
<head>
    <title>Description</title>
</head>
<body>
<div id="header"><c:import url="/pages/edition/header.jsp"/></div>
<div id="content">
    <div class="left-2">
        <div id="site-wrapper-p" style="float: left; margin-left: 120px">
            <ul class="products homepage">
                <li>
                    <img src="${edition.pathImg}" width=" 250px" height="250px"/>
                    <h3>${edition.title}
                    </h3>
                    <h4>${edition.type}
                    </h4>
                    <a href="/controller?command=detail_author&idAuthor=${edition.author.id}"><span>${edition.author.name}</span></a>
                    <h4><tr:translateTag info="${edition.description}" locale="${sessionScope.locale}"/>
                    </h4>
                    <h4>${edition.priceMonth}$
                    </h4>
                    <h4>${edition.countSubscribers} subscribers
                    </h4>
                </li>
            </ul>
        </div>
    </div>
    <div id="add">
        <form action="/controller?command=add_edition&idEdition=${edition.id}"
              method="post">
            <input type="submit" value="Add to cart"/>
        </form>
    </div>
    <div id="err"><c:import url="/pages/edition/err.jsp"/></div>
    <div id="footer"><c:import url="/pages/edition/footer.jsp"/></div>
</div>
</body>
</html>
