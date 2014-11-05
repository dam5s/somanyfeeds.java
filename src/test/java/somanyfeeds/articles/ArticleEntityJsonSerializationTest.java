package somanyfeeds.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somanyfeeds.articles.ArticleEntity;
import com.somanyfeeds.config.ObjectMapperProvider;
import org.junit.Test;

import java.time.ZonedDateTime;

import static com.somanyfeeds.articles.ArticleEntity.articleEntityBuilder;
import static javax.json.Json.createObjectBuilder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ArticleEntityJsonSerializationTest {
    private static final ObjectMapper objectMapper = new ObjectMapperProvider().get();

    @Test public void testSerialization() throws Exception {
        ArticleEntity article = articleEntityBuilder()
            .title("Great Article About Stuff.")
            .link("http://example.com/blog/article-about-stuff")
            .content("This is a great article about stuff and other things...")
            .date(ZonedDateTime.parse("2014-10-09T08:07:06Z"))
            .build();

        String jsonString = objectMapper.writeValueAsString(article);

        assertThat(jsonString, equalTo(
            createObjectBuilder()
                .add("title", "Great Article About Stuff.")
                .add("link", "http://example.com/blog/article-about-stuff")
                .add("content", "This is a great article about stuff and other things...")
                .add("date", "2014-10-09T08:07:06Z")
                .build()
                .toString()
        ));
    }

    @Test public void testSerialization_WithNullValues() throws Exception {
        ArticleEntity article = articleEntityBuilder().build();

        String jsonString = objectMapper.writeValueAsString(article);

        assertThat(jsonString, equalTo("{}"));
    }
}
