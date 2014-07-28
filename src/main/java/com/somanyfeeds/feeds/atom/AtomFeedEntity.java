package com.somanyfeeds.feeds.atom;

import com.somanyfeeds.feeds.FeedEntity;

public class AtomFeedEntity extends FeedEntity {
    public AtomFeedEntity(String url) {
        super(url, Type.ATOM);
    }
}
