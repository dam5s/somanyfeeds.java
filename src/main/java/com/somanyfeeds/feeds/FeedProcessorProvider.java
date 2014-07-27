package com.somanyfeeds.feeds;

import com.somanyfeeds.feeds.atom.AtomFeedProcessor;
import com.somanyfeeds.feeds.rss.RssFeedProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeedProcessorProvider {
    private final RssFeedProcessor rssFeedProcessor;
    private final AtomFeedProcessor atomFeedProcessor;

    @Autowired
    public FeedProcessorProvider(RssFeedProcessor rssFeedProcessor, AtomFeedProcessor atomFeedProcessor) {
        this.rssFeedProcessor = rssFeedProcessor;
        this.atomFeedProcessor = atomFeedProcessor;
    }

    public FeedProcessor get(FeedEntity feed) {
        switch (feed.getType()) {
            case RSS:
                return rssFeedProcessor;
            case ATOM:
                return atomFeedProcessor;
        }

        throw new UnknownFeedTypeException(feed.getType());
    }
}
