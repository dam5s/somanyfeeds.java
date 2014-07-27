package com.somanyfeeds.feeds;

import com.somanyfeeds.feeds.atom.AtomFeed;
import com.somanyfeeds.feeds.rss.RssFeed;

public abstract class FeedEntity {
    private final String url;
    private final Type type;

    protected FeedEntity(String url, Type type) {
        this.url = url;
        this.type = type;
    }

    public static Builder feedEntityBuilder() {
        return new Builder();
    }

    public String getUrl() {
        return url;
    }

    public Type getType() {
        return type;
    }

    ;

    public static enum Type {RSS, ATOM}

    public static class Builder {
        private Type type;
        private String url;

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public FeedEntity build() {
            switch (type) {
                case RSS:
                    return new RssFeed(url);
                case ATOM:
                    return new AtomFeed(url);
            }
            throw new UnknownFeedTypeException(type);
        }
    }
}
