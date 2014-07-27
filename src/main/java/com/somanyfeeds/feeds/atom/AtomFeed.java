package com.somanyfeeds.feeds.atom;

import com.somanyfeeds.feeds.FeedEntity;

public class AtomFeed extends FeedEntity {
    public AtomFeed(String url) {
        super(url, Type.ATOM);
    }
}
