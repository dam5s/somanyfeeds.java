package com.somanyfeeds.feeds;

import com.somanyfeeds.articles.ArticleEntity;

import java.util.List;

public interface FeedProcessor<T extends FeedEntity> {
    public List<ArticleEntity> process(T feed);
}
