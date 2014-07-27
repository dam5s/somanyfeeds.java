package com.somanyfeeds;

import java.util.Date;

public class ArticleEntity {
    private final String title;
    private final String link;
    private final String content;
    private final Date date;

    public ArticleEntity(String title, String link, String content, Date date) {
        this.title = title;
        this.link = link;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public static Builder articleEntityBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String link;
        private String content;
        private Date date;

        private Builder() {
            date = new Date();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder link(String link) {
            this.link = link;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public ArticleEntity build() {
            return new ArticleEntity(title, link, content, date);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleEntity that = (ArticleEntity) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return date != null ? date.hashCode() : 0;
    }
}
