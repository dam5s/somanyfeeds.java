<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="sources" type="java.util.List<com.somanyfeeds.sources.SourcePresenter>" scope="request"/>
<jsp:useBean id="articles" type="java.util.List<com.somanyfeeds.articles.ArticleEntity>" scope="request"/>
<!doctype html>
<html>
<head>
    <title>damo.io</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes">

    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lora:400,700|RobotoDraft:regular,bold,medium">
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
<div id="app-content" layout vertical center>
    <c:forEach var="article" items="${articles}">
        <article class="base-article">
            <h2><a href="${article.link}">${article.title}</a></h2>

            <h3>${article.date}</h3>
            <section>${article.content}</section>
        </article>
    </c:forEach>
</div>
</body>
</html>
