package com.somanyfeeds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ArticlesController {
    private final SourcesRepository sourcesRepository;
    private final ArticlesRepository articlesRepository;

    @Autowired
    public ArticlesController(SourcesRepository sourcesRepository, ArticlesRepository articlesRepository) {
        this.sourcesRepository = sourcesRepository;
        this.articlesRepository = articlesRepository;
    }

    @RequestMapping("/")
    public String listAllArticles(Model model) {
        List<SourceEntity> sources = sourcesRepository.findAll();

        return listArticlesForSources(model, sources);
    }

    @RequestMapping("/{sourceSlugs}")
    public String listArticles(@PathVariable List<String> sourceSlugs, Model model) {
        List<SourceEntity> sources = sourcesRepository.findAllBySlug(sourceSlugs);

        return listArticlesForSources(model, sources);
    }

    private String listArticlesForSources(Model model, List<SourceEntity> sources) {
        List<ArticleEntity> articles = articlesRepository.findAllInSources(sources);
        model.addAttribute("sources", sources);
        model.addAttribute("articles", articles);

        return "list-articles";
    }
}
