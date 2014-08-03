package somanyfeeds.feeds;

import com.somanyfeeds.HttpGateway;
import com.somanyfeeds.articles.ArticleEntity;
import com.somanyfeeds.feeds.atom.*;
import org.junit.*;

import java.io.IOException;
import java.time.*;
import java.util.List;

import static com.somanyfeeds.feeds.FeedEntity.Type.ATOM;
import static com.somanyfeeds.feeds.FeedEntity.feedEntityBuilder;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static somanyfeeds.TestUtils.loadResource;

public class AtomFeedProcessorTest {
    HttpGateway httpGateway;
    AtomFeedProcessor processor;

    @Before
    public void setup() {
        httpGateway = mock(HttpGateway.class);
        processor = new AtomFeedProcessor(httpGateway);
    }

    @Test
    public void testProcess() throws IOException {
        AtomFeedEntity feed = (AtomFeedEntity) feedEntityBuilder().type(ATOM).url("http://example.com/feed.atom").build();
        String atomString = loadResource("sample.atom.xml");

        doReturn(atomString).when(httpGateway).get("http://example.com/feed.atom");

        List<ArticleEntity> articles = processor.process(feed);

        assertThat(articles, hasSize(30));

        ZonedDateTime expectedDate = ZonedDateTime.of(
                LocalDateTime.of(2014, Month.JULY, 27, 15, 57, 56),
                ZoneId.of("UTC")
        );
        assertThat(articles.get(0).getLink(), equalTo("https://github.com/dam5s/somayfeeds.java/compare/master"));
        assertThat(articles.get(0).getDate(), equalTo(expectedDate));
        assertThat(articles.get(0).getTitle(), containsString("dam5s created branch master at dam5s/somayfeeds.java"));
        assertThat(articles.get(0).getContent(), containsString("<!-- create -->\n            <div class=\"simple\">\n"));
    }
}
