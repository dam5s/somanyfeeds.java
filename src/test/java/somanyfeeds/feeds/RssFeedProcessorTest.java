package somanyfeeds.feeds;

import com.somanyfeeds.HttpGateway;
import com.somanyfeeds.articles.ArticleEntity;
import com.somanyfeeds.feeds.rss.*;
import org.junit.*;

import java.io.IOException;
import java.time.*;
import java.util.List;

import static com.somanyfeeds.feeds.FeedEntity.Type.RSS;
import static com.somanyfeeds.feeds.FeedEntity.feedEntityBuilder;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static somanyfeeds.TestUtils.loadResource;

public class RssFeedProcessorTest {
    HttpGateway httpGateway;
    RssFeedProcessor processor;

    @Before
    public void setup() {
        httpGateway = mock(HttpGateway.class);
        processor = new RssFeedProcessor(httpGateway);
    }

    @Test
    public void testProcess() throws IOException {
        RssFeedEntity feed = (RssFeedEntity) feedEntityBuilder().type(RSS).url("http://example.com/feed.rss").build();
        String rssString = loadResource("sample.rss.xml");

        doReturn(rssString).when(httpGateway).get("http://example.com/feed.rss");

        List<ArticleEntity> articles = processor.process(feed);

        assertThat(articles, hasSize(10));

        ZonedDateTime expectedDate = ZonedDateTime.of(
                LocalDateTime.of(2013, Month.MAY, 12, 19, 33, 13),
                ZoneId.of("UTC")
        );

        assertThat(articles.get(9).getLink(), equalTo("https://plus.google.com/105039413587880910287/posts/FiXRB9KBvYY"));
        assertThat(articles.get(9).getDate(), equalTo(expectedDate));
        assertThat(articles.get(9).getTitle(), containsString("Considering taking some of wednesday/thursday off to be able to follow Google I/O live streamsÂ  #io2013..."));
        assertThat(articles.get(9).getContent(), containsString("<div class='content'>Considering taking some of wednesday/thursday "));
    }
}
