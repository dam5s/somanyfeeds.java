package com.somanyfeeds.feeds.atom;

import com.somanyfeeds.articles.ArticleEntity;
import com.somanyfeeds.feeds.FeedProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtomFeedProcessor implements FeedProcessor<AtomFeed> {
    @Override
    public List<ArticleEntity> process(AtomFeed feed) {
        return null;
    }
}
