package com.somanyfeeds.feeds.rss;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
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
public class RssFeedProcessor implements FeedProcessor<RssFeedEntity> {
    private final XmlMapper mapper;
    private final HttpGateway httpGateway;

    @Autowired
    public RssFeedProcessor(HttpGateway httpGateway) {
        this.httpGateway = httpGateway;
        this.mapper = new XmlMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<ArticleEntity> process(RssFeedEntity feed) {
        try {
            String rssString = httpGateway.get(feed.getUrl());
            Rss rss = mapper.readValue(rssString, Rss.class);

            return rss.getItems().stream()
                    .map(this::buildArticle)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArticleEntity buildArticle(Item item) {
        return articleEntityBuilder()
                .title(item.title)
                .link(item.link)
                .content(item.description)
                .date(ZonedDateTime.ofInstant(item.pubDate.toInstant(), ZoneId.of("UTC")))
                .build();
    }

    public static class Rss {
        public Channel channel;

        public List<Item> getItems() {
            return channel.items;
        }
    }

    public static class Channel {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("item")
        List<Item> items;
    }

    public static class Item {
        public String title;
        public String link;
        public Date pubDate;
        public String description;
    }
}
