package com.somanyfeeds.feeds;

import com.somanyfeeds.articles.ArticleEntity;
import com.somanyfeeds.articles.ArticlesRepository;
import com.somanyfeeds.sources.SourceEntity;
import com.somanyfeeds.sources.SourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedUpdatesService {
    private final SourcesRepository sourcesRepository;
    private final ArticlesRepository articlesRepository;
    private final FeedProcessorProvider feedProcessorProvider;

    @Autowired
    public FeedUpdatesService(SourcesRepository sourcesRepository, ArticlesRepository articlesRepository, FeedProcessorProvider feedProcessorProvider) {
        this.sourcesRepository = sourcesRepository;
        this.articlesRepository = articlesRepository;
        this.feedProcessorProvider = feedProcessorProvider;
    }

    public void updateAll() {
        for (SourceEntity source : sourcesRepository.findAll()) {
            FeedProcessor feedProcessor = feedProcessorProvider.get(source.getFeed());
            List<ArticleEntity> articles = feedProcessor.process(source.getFeed());
            articlesRepository.updateArticlesForSource(source, articles);
        }
    }
}
