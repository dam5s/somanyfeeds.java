package com.somanyfeeds.feeds;

import com.somanyfeeds.articles.ArticleEntity;
import com.somanyfeeds.feeds.atom.*;
import com.somanyfeeds.feeds.rss.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispatchingFeedProcessor implements FeedProcessor<FeedEntity> {
    private final RssFeedProcessor rssFeedProcessor;
    private final AtomFeedProcessor atomFeedProcessor;

    @Autowired
    public DispatchingFeedProcessor(RssFeedProcessor rssFeedProcessor, AtomFeedProcessor atomFeedProcessor) {
        this.rssFeedProcessor = rssFeedProcessor;
        this.atomFeedProcessor = atomFeedProcessor;
    }

    public List<ArticleEntity> process(FeedEntity feed) {
        if (feed instanceof RssFeedEntity) {
            return rssFeedProcessor.process((RssFeedEntity) feed);
        }
        if (feed instanceof AtomFeedEntity) {
            return atomFeedProcessor.process((AtomFeedEntity) feed);
        }

        throw new UnknownFeedTypeException(feed.getType());
    }
}
