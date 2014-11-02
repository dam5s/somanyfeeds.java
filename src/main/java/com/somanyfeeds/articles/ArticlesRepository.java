package com.somanyfeeds.articles;

import com.somanyfeeds.sources.SourceEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Repository
public class ArticlesRepository {
    private final Map<SourceEntity, List<ArticleEntity>> articlesBySource = new ConcurrentHashMap<>();

    public List<ArticleEntity> findAllInSources(List<SourceEntity> sources) {
        return sources
                .stream()
                .flatMap((source) -> articlesBySource.getOrDefault(source, emptyList()).stream())
                .sorted(comparing(ArticleEntity::getDate))
                .collect(toList());
    }

    public void updateArticlesForSource(SourceEntity source, List<ArticleEntity> articles) {
        articlesBySource.put(source, articles);
    }
}
