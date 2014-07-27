package com.somanyfeeds;

import org.springframework.stereotype.Repository;

import java.util.*;

import static com.somanyfeeds.ArticleEntity.articleEntityBuilder;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;

@Repository
public class ArticlesRepository {
    private final Map<SourceEntity, List<ArticleEntity>> articlesBySource = new HashMap<>();

    public ArticlesRepository() {
        ArticleEntity githubArticle = articleEntityBuilder().title("Github article title").build();
        SourceEntity githubSource = new SourceEntity("Github", "github");

        articlesBySource.put(githubSource, asList(githubArticle));
        articlesBySource.put(
                new SourceEntity("Google+", "gplus"), asList(
                        articleEntityBuilder().title("GPlus article title").build()
                )
        );
        articlesBySource.put(
                new SourceEntity("Pivotal Blog", "pivotal"), asList(
                        articleEntityBuilder().title("Blog article title").build()
                )
        );
    }

    public List<ArticleEntity> findAllInSources(List<SourceEntity> sources) {
        List<ArticleEntity> articles = new ArrayList<>();

        for (SourceEntity source : sources) {
            articles.addAll(articlesBySource.get(source));
        }

        articles.sort((a1, a2) -> a1.getDate().compareTo(a2.getDate()));
        return articles;
    }
}
