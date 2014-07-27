package com.somanyfeeds;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class SourcesRepository {
    private final Map<String, SourceEntity> allSources;

    public SourcesRepository() {
        allSources = new HashMap<>();
        allSources.put("github", new SourceEntity("Github", "github"));
        allSources.put("gplus", new SourceEntity("Google+", "gplus"));
        allSources.put("pivotal", new SourceEntity("Pivotal Blog", "pivotal"));
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

        sources.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));
        return sources;
    }
}
