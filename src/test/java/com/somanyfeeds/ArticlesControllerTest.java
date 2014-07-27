package com.somanyfeeds;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.somanyfeeds.ArticleEntity.articleEntityBuilder;
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
        List<SourceEntity> allSources = asList(
                new SourceEntity("My Blog", "my-blog")
        );
        List<ArticleEntity> articlesForAllSources = asList(
                articleEntityBuilder().title("The Article").build()
        );

        doReturn(allSources).when(sourcesRepository).findAll();
        doReturn(articlesForAllSources).when(articlesRepository).findAllInSources(allSources);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-articles"))
                .andExpect(model().attribute("sources", allSources))
                .andExpect(model().attribute("articles", articlesForAllSources));
    }

    @Test
    public void testListArticles() throws Exception {
        List<SourceEntity> someSources = asList(
                new SourceEntity("My Blog", "my-blog"),
                new SourceEntity("My Photos", "my-photos")
        );
        List<ArticleEntity> articlesForSomeSources = asList(
                articleEntityBuilder().title("An Article").build(),
                articleEntityBuilder().title("Another Article").build(),
                articleEntityBuilder().title("The Photo").build()
        );

        doReturn(someSources).when(sourcesRepository).findAllBySlug(asList("my-blog", "my-photos"));
        doReturn(articlesForSomeSources).when(articlesRepository).findAllInSources(someSources);

        mockMvc.perform(get("/my-blog,my-photos"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-articles"))
                .andExpect(model().attribute("sources", someSources))
                .andExpect(model().attribute("articles", articlesForSomeSources));
    }
}
