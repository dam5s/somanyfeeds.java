package com.somanyfeeds.feeds;

public class UnknownFeedTypeException extends RuntimeException {
    public UnknownFeedTypeException(FeedEntity.Type type) {
        super("Error finding feed processor for type " + type);
    }
}
