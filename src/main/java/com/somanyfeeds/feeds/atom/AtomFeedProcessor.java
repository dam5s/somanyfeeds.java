package com.somanyfeeds.feeds.atom;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.*;
import com.somanyfeeds.HttpGateway;
import com.somanyfeeds.articles.ArticleEntity;
import com.somanyfeeds.feeds.FeedProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.somanyfeeds.articles.ArticleEntity.articleEntityBuilder;

@Service
public class AtomFeedProcessor implements FeedProcessor<AtomFeedEntity> {
    private final HttpGateway httpGateway;
    private final XmlMapper mapper;

    @Autowired
    public AtomFeedProcessor(HttpGateway httpGateway) {
        this.httpGateway = httpGateway;
        this.mapper = new XmlMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public List<ArticleEntity> process(AtomFeedEntity feed) {
        try {
            String atomString = httpGateway.get(feed.getUrl());
            Feed atom = mapper.readValue(atomString, Feed.class);

            return atom.entries.stream()
                    .map(this::buildArticle)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArticleEntity buildArticle(Entry entry) {
        return articleEntityBuilder()
                .title(entry.title)
                .link(entry.link)
                .content(entry.content)
                .date(entry.published)
                .build();
    }

    public static class Feed {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("entry")
        List<Entry> entries;
    }

    public static class Entry {
        public String title;
        public String link;
        public String content;
        public ZonedDateTime published;

        public void setTitle(Title title) {
            this.title = title.text;
        }

        public void setLink(Link link) {
            if (link == null) {
                return;
            }
            this.link = link.href;
        }

        public void setPublished(Date date) {
            if (date == null) {
                return;
            }
            published = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
        }

        public void setContent(Content content) {
            this.content = content.text;
        }
    }

    public static class Title {
        @JacksonXmlText
        public String text;
    }

    public static class Link {

        @JacksonXmlProperty(isAttribute = true)
        public String href;
    }

    public static class Content {
        @JacksonXmlText
        public String text;
    }
}
