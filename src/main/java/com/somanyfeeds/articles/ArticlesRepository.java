package com.somanyfeeds.articles;

import com.somanyfeeds.sources.SourceEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ArticlesRepository {
    private final Map<SourceEntity, List<ArticleEntity>> articlesBySource = new HashMap<>();

    public List<ArticleEntity> findAllInSources(List<SourceEntity> sources) {
        List<ArticleEntity> articles = new ArrayList<>();

        for (SourceEntity source : sources) {
            articles.addAll(articlesBySource.get(source));
        }

        articles.sort((a1, a2) -> a1.getDate().compareTo(a2.getDate()));
        return articles;
    }

    public void updateArticlesForSource(SourceEntity source, List<ArticleEntity> articles) {
        articlesBySource.put(source, articles);
    }
}
