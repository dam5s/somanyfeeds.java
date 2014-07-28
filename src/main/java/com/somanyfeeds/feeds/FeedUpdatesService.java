package com.somanyfeeds.feeds;

import com.somanyfeeds.articles.*;
import com.somanyfeeds.sources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedUpdatesService {
    private final SourcesRepository sourcesRepository;
    private final ArticlesRepository articlesRepository;
    private final DispatchingFeedProcessor feedProcessor;

    @Autowired
    public FeedUpdatesService(SourcesRepository sourcesRepository, ArticlesRepository articlesRepository, DispatchingFeedProcessor feedProcessor) {
        this.sourcesRepository = sourcesRepository;
        this.articlesRepository = articlesRepository;
        this.feedProcessor = feedProcessor;
    }

    public void updateAll() {
        for (SourceEntity source : sourcesRepository.findAll()) {
            FeedEntity feed = source.getFeed();
            List<ArticleEntity> articles = feedProcessor.process(feed);
            articlesRepository.updateArticlesForSource(source, articles);
        }
    }
}
