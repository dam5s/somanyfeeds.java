package com.somanyfeeds.sources;

import com.somanyfeeds.feeds.FeedEntity;

public class SourceEntity {
    private final String name;
    private final String slug;
    private final FeedEntity feed;
    private final SourceType type;

    private SourceEntity(Builder builder) {
        this.name = builder.name;
        this.slug = builder.slug;
        this.feed = builder.feed;
        this.type = builder.type;
    }

    public static Builder sourceEntityBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public FeedEntity getFeed() {
        return feed;
    }

    public SourceType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SourceEntity that = (SourceEntity) o;

        if (feed != null ? !feed.equals(that.feed) : that.feed != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (slug != null ? !slug.equals(that.slug) : that.slug != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (slug != null ? slug.hashCode() : 0);
        result = 31 * result + (feed != null ? feed.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private String name;
        private String slug;
        private FeedEntity feed;
        private SourceType type;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public Builder feed(FeedEntity feed) {
            this.feed = feed;
            return this;
        }

        public Builder type(SourceType type) {
            this.type = type;
            return this;
        }

        public SourceEntity build() {
            return new SourceEntity(this);
        }
    }
}
