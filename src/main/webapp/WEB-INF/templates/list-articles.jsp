<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="sources" type="java.util.List<com.somanyfeeds.sources.SourcePresenter>" scope="request"/>
<jsp:useBean id="articles" type="java.util.List<com.somanyfeeds.articles.ArticleEntity>" scope="request"/>
<!doctype html>
<html>
    <head>
        <title>damo.io</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes">

        <script src="/components/platform/platform.js"></script>
        <link rel="import" href="/components/font-roboto/roboto.html">
        <link rel="import" href="/components/core-header-panel/core-header-panel.html">
        <link rel="import" href="/components/core-menu/core-menu.html">
        <link rel="import" href="/components/paper-menu-button/paper-menu-button.html">
        <link rel="import" href="/components/paper-item/paper-item.html">
        <link rel="import" href="/components/core-item/core-item.html">
        <link rel="import" href="/custom-components/base-article.html">

        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lora:400,700">
        <link rel="stylesheet" href="/css/base.css">
        <link rel="stylesheet" href="/css/small-screens.css">
    </head>
    <body unresolved touch-action="auto">
        <core-header-panel mode="waterfall-tall">
            <div id="app-header" class="core-header" layout horizontal end>
                <h1 flex>damo.io</h1>
                <core-menu id="large-screen-menu" layout horizontal>
                    <c:forEach var="src" items="${sources}">
                        <core-item label="${src.name}" class="${src.selected ? "selected" : ""}">
                            <a href="${src.link}"></a>
                        </core-item>
                    </c:forEach>
                </core-menu>
                <paper-menu-button id="small-screen-menu" icon="menu" halign="right">
                    <c:forEach var="src" items="${sources}">
                        <core-item label="${src.name}" class="${src.selected ? "selected" : ""}">
                            <a href="${src.link}"></a>
                        </core-item>
                    </c:forEach>
                </paper-menu-button>
            </div>
            <div class="container" layout vertical center>
                <c:forEach var="article" items="${articles}">
                    <base-article>
                        <h2><a href="${article.link}">${article.title}</a></h2>
                        <h3>${article.date}</h3>
                        <section>${article.content}</section>
                    </base-article>
                </c:forEach>
            </div>
        </core-header-panel>
    </body>
</html>
