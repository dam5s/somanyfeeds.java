package somanyfeeds.articles;

import com.somanyfeeds.articles.*;
import com.somanyfeeds.sources.*;
import org.junit.*;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.somanyfeeds.articles.ArticleEntity.articleEntityBuilder;
import static com.somanyfeeds.sources.SourceEntity.sourceEntityBuilder;
import static java.util.Arrays.asList;
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
    public void testListAllArticles() throws Exception {
        SourceEntity myBlog = sourceEntityBuilder().name("My Blog").slug("my-blog").build();
        SourceEntity myPhotos = sourceEntityBuilder().name("My Photos").slug("my-photos").build();

        List<SourceEntity> allSources = asList(myBlog, myPhotos);
        List<ArticleEntity> articlesForAllSources = asList(
                articleEntityBuilder().title("The Article").build()
        );

        doReturn(allSources).when(sourcesRepository).findAll();
        doReturn(articlesForAllSources).when(articlesRepository).findAllInSources(allSources);

        List<String> expectedSlugs = asList("my-blog", "my-photos");
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-articles"))
                .andExpect(model().attribute("sources", asList(
                        new SourcePresenter(myBlog, expectedSlugs),
                        new SourcePresenter(myPhotos, expectedSlugs)
                )))
                .andExpect(model().attribute("articles", articlesForAllSources));
    }

    @Test
    public void testListArticles() throws Exception {
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

        mockMvc.perform(get("/my-blog,my-photos"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-articles"))
                .andExpect(model().attribute("sources", asList(
                        new SourcePresenter(myBlog, expectedSlugs),
                        new SourcePresenter(myPhotos, expectedSlugs),
                        new SourcePresenter(myCode, expectedSlugs)
                )))
                .andExpect(model().attribute("articles", articlesForSomeSources));
    }
}
