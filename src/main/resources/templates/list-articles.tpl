yieldUnescaped "<!doctype html>"
html {
    head {
        title("itsDamo.com")
        meta(charset: "utf-8")
        meta(name: "viewport", content: "width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")

        script(src: "/components/platform/platform.js") {}
        link(rel: "import", href: "/components/font-roboto/roboto.html")
        link(rel: "import", href: "/components/core-header-panel/core-header-panel.html")
        link(rel: "import", href: "/components/core-menu/core-menu.html")
        link(rel: "import", href: "/components/paper-menu-button/paper-menu-button.html")
        link(rel: "import", href: "/components/paper-item/paper-item.html")
        link(rel: "import", href: "/custom-components/base-article.html")
        link(rel: "stylesheet", href: "/css/base.css")
        link(rel: "stylesheet", href: "/css/small-screens.css")
    }
    body(unresolved: true, "touch-action": "auto") {
        "core-header-panel"(mode: "waterfall-tall") {
            div(id: "app-header", class: "core-header", layout: true, horizontal: true, end: true) {
                h1(flex: true) {
                    yield "itsDamo.com"
                }
                "core-menu"(id: "large-screen-menu", multi: true, layout: true, horizontal: true) {
                    sources.each { src ->
                        "paper-item"(label: src.name) {
                            a(href: "#") {}
                        }
                    }
                }
                "paper-menu-button"(id: "small-screen-menu", icon: "menu", halign: "right", multi: true) {
                    sources.each { src ->
                        "paper-item"(label: src.name) {
                            a(href: "#") {}
                        }
                    }
                }
            }

            div(class: "container", layout: true, vertical: true, center: true) {
                articles.each { article ->
                    "base-article" {
                        h2 {
                            a(href: article.link) { yield article.title }
                        }
                        h3(article.date)
                        section(article.content)
                    }
                }
            }
        }
    }
}
