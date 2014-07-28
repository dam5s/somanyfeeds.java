package com.somanyfeeds.feeds.rss;

import com.somanyfeeds.feeds.FeedEntity;

public class RssFeedEntity extends FeedEntity {
    public RssFeedEntity(String url) {
        super(url, Type.RSS);
    }
}
