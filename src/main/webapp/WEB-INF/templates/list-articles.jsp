<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="sources" type="java.util.List<com.somanyfeeds.sources.SourcePresenter>" scope="request"/>
<jsp:useBean id="articles" type="java.util.List<com.somanyfeeds.articles.ArticleEntity>" scope="request"/>
<!doctype html>
<html>
<head>
    <title>damo.io</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes">

    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto+Slab:300,400,700|Roboto:100,300,400,700">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/small-screens.css">
</head>
<body>
<div id="app-header">
    <h1>damo.io</h1>
    <ul id="menu">
        <c:forEach var="src" items="${sources}">
            <li class="${src.selected ? "selected" : ""}">
                <a href="${src.link}">${src.name}</a>
            </li>
        </c:forEach>
    </ul>
</div>
<div id="app-content">
    <c:forEach var="article" items="${articles}">
        <article class="base-article">
            <header>
                <h3 class="date">${article.date}</h3>
                <h2><a href="${article.link}">${article.title}</a></h2>
            </header>
            <section>${article.content}</section>
        </article>
    </c:forEach>
</div>

<script type="application/dart" src="${pageContext.request.contextPath}/dart/application.dart"></script>
<script src="${pageContext.request.contextPath}/dart/packages/browser/dart.js"></script>
</body>
</html>
