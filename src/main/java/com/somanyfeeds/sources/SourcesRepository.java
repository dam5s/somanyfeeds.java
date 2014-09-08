package com.somanyfeeds.sources;

import com.somanyfeeds.feeds.FeedEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.somanyfeeds.feeds.FeedEntity.Type.*;
import static com.somanyfeeds.feeds.FeedEntity.feedEntityBuilder;
import static com.somanyfeeds.sources.SourceEntity.sourceEntityBuilder;
import static com.somanyfeeds.sources.SourceType.*;
import static java.util.Comparator.comparing;

@Repository
public class SourcesRepository {
    private final Map<String, SourceEntity> allSources;

    public SourcesRepository() {
        allSources = new ConcurrentHashMap<>();
        addSource("github", "Github", ATOM, "https://github.com/dam5s.atom", GITHUB);
        addSource("gplus", "Google+", RSS, "http://gplusrss.com/rss/feed/aa49b266059d0628a1c112dabaec23a152aa2bad054d8", GOOGLE_PLUS);
        addSource("pivotal", "Pivotal Blog", RSS, "http://pivotallabs.com/author/dleberrigaud/feed/", OTHER);
    }

    public List<SourceEntity> findAll() {
        return allSources
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    public List<SourceEntity> findAllBySlug(List<String> sourceSlugs) {
        List<SourceEntity> sources = sourceSlugs.stream()
                .map(allSources::get)
                .collect(Collectors.toList());

        sources.sort(comparing(SourceEntity::getName));
        return sources;
    }

    private void addSource(String slug, String name, FeedEntity.Type feedType, String url, SourceType sourceType) {
        FeedEntity feed = feedEntityBuilder()
                .type(feedType)
                .url(url)
                .build();

        allSources.put(slug, sourceEntityBuilder()
                        .name(name)
                        .slug(slug)
                        .feed(feed)
                        .type(sourceType)
                        .build()
        );
    }
}
