package com.somanyfeeds.articles;

import com.somanyfeeds.sources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public String redirectToDefaultSources() {
        return "redirect:/gplus,pivotal";
    }

    @RequestMapping(value = "/{sourceSlugs}", headers = {"accept=text/html"})
    public String listArticlesAsHtml(@PathVariable List<String> sourceSlugs, Model model) {
        List<SourcePresenter> sourcePresenters = sourcesRepository
                .findAll()
                .stream()
                .map(source -> new SourcePresenter(source, sourceSlugs))
                .collect(Collectors.toList());

        model.addAttribute("sources", sourcePresenters);
        model.addAttribute("articles", getArticles(sourceSlugs));

        return "list-articles";
    }

    @RequestMapping(value = "/{sourceSlugs}", headers = {"accept=application/json"})
    public @ResponseBody ArticlesJson listArticlesAsJson(@PathVariable List<String> sourceSlugs) {
        return new ArticlesJson(getArticles(sourceSlugs));
    }

    private List<ArticleEntity> getArticles(List<String> sourceSlugs) {
        List<SourceEntity> selectedSources = sourcesRepository.findAllBySlug(sourceSlugs);
        return articlesRepository.findAllInSources(selectedSources);
    }

    private class ArticlesJson {
        public List<ArticleEntity> articles;

        private ArticlesJson(List<ArticleEntity> articles) {
            this.articles = articles;
        }
    }
}
