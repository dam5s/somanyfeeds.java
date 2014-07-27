package com.somanyfeeds.feeds.rss;

import com.somanyfeeds.feeds.FeedEntity;

public class RssFeed extends FeedEntity {
    public RssFeed(String url) {
        super(url, Type.RSS);
    }
}
