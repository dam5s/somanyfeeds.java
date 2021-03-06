package somanyfeeds.articles;

import com.somanyfeeds.articles.*;
import com.somanyfeeds.sources.*;
import org.junit.*;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.somanyfeeds.articles.ArticleEntity.articleEntityBuilder;
import static com.somanyfeeds.sources.SourceEntity.sourceEntityBuilder;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ArticlesControllerTest {
    @Mock
    SourcesRepository sourcesRepository;
    @Mock
    ArticlesRepository articlesRepository;

    MockMvc mockMvc;
    ArticlesController controller;

    @Before
    public void setup() {
        initMocks(this);
        controller = new ArticlesController(sourcesRepository, articlesRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testRedirectToDefaultSources() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/gplus,pivotal"));
    }

    @Test
    public void testListArticles_asHtml() throws Exception {
        SourceEntity myBlog = sourceEntityBuilder().name("My Blog").slug("my-blog").build();
        SourceEntity myPhotos = sourceEntityBuilder().name("My Photos").slug("my-photos").build();
        SourceEntity myCode = sourceEntityBuilder().name("My Code").slug("my-code").build();

        List<SourceEntity> allSources = asList(myBlog, myPhotos, myCode);
        List<SourceEntity> someSources = asList(myBlog, myPhotos);
        List<ArticleEntity> articlesForSomeSources = asList(
                articleEntityBuilder().title("An Article").build(),
                articleEntityBuilder().title("Another Article").build(),
                articleEntityBuilder().title("The Photo").build()
        );

        doReturn(allSources).when(sourcesRepository).findAll();
        doReturn(someSources).when(sourcesRepository).findAllBySlug(asList("my-blog", "my-photos"));
        doReturn(articlesForSomeSources).when(articlesRepository).findAllInSources(someSources);

        List<String> expectedSlugs = asList("my-blog", "my-photos");

        mockMvc.perform(
                get("/my-blog,my-photos")
                        .accept(MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML_XML, MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(view().name("list-articles"))
                .andExpect(model().attribute("sources", asList(
                        new SourcePresenter(myBlog, expectedSlugs),
                        new SourcePresenter(myPhotos, expectedSlugs),
                        new SourcePresenter(myCode, expectedSlugs)
                )))
                .andExpect(model().attribute("articles", articlesForSomeSources));
    }

    @Test
    public void testListArticles_asJson() throws Exception {
        SourceEntity myBlog = sourceEntityBuilder().name("My Blog").slug("my-blog").build();
        SourceEntity myPhotos = sourceEntityBuilder().name("My Photos").slug("my-photos").build();

        List<SourceEntity> someSources = asList(myBlog, myPhotos);
        List<ArticleEntity> articlesForSomeSources = asList(
                articleEntityBuilder().title("An Article").build(),
                articleEntityBuilder().title("Another Article").build(),
                articleEntityBuilder().title("The Photo").build()
        );

        doReturn(someSources).when(sourcesRepository).findAllBySlug(asList("my-blog", "my-photos"));
        doReturn(articlesForSomeSources).when(articlesRepository).findAllInSources(someSources);

        mockMvc.perform(
                get("/my-blog,my-photos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articles", hasSize(3)))
                .andExpect(jsonPath("$.articles[0].title", equalTo("An Article")))
                .andExpect(jsonPath("$.articles[1].title", equalTo("Another Article")))
                .andExpect(jsonPath("$.articles[2].title", equalTo("The Photo")));
    }
}
