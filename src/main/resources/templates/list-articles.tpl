yieldUnescaped "<!DOCTYPE html>"
html(lang: "en") {
    head {
        title("So Many Feeds")
        meta(charset: "utf-8")
        meta(name: "viewport", content: "width=device-width")
    }
    body {
        div(id: "articles") {
            articles.each { a ->
                article {
                    header {
                        h1(a.title)
                        h2(a.date)
                    }
                    section(a.content)
                }
            }
        }
    }
}
