package com.somanyfeeds.feeds.rss;

import com.somanyfeeds.articles.ArticleEntity;
import com.somanyfeeds.feeds.FeedProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RssFeedProcessor implements FeedProcessor<RssFeed> {
    @Override
    public List<ArticleEntity> process(RssFeed feed) {
        return null;
    }
}
