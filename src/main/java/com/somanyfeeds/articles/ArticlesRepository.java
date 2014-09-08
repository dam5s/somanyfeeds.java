package com.somanyfeeds.articles;

import com.somanyfeeds.sources.SourceEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Repository
public class ArticlesRepository {
    private final Map<SourceEntity, List<ArticleEntity>> articlesBySource = new ConcurrentHashMap<>();

    public List<ArticleEntity> findAllInSources(List<SourceEntity> sources) {
        List<ArticleEntity> articles = new ArrayList<>();

        sources
                .stream()
                .flatMap((source) -> articlesBySource.get(source).stream())
                .sorted(comparing(ArticleEntity::getDate))
                .collect(toList());

        return articles;
    }

    public void updateArticlesForSource(SourceEntity source, List<ArticleEntity> articles) {
        articlesBySource.put(source, articles);
    }
}
