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
    public String listAllArticles(Model model) {
        List<SourceEntity> selectedSources = sourcesRepository.findAll();
        List<String> sourceSlugs = selectedSources.stream().map(SourceEntity::getSlug).collect(Collectors.toList());

        List<SourcePresenter> sourcePresenters = selectedSources
                .stream()
                .map(source ->
                                new SourcePresenter(source, sourceSlugs)
                )
                .collect(Collectors.toList());

        return listArticlesForSources(model, selectedSources, sourcePresenters);
    }

    @RequestMapping("/{sourceSlugs}")
    public String listArticles(@PathVariable List<String> sourceSlugs, Model model) {
        List<SourceEntity> selectedSources = sourcesRepository.findAllBySlug(sourceSlugs);
        List<SourcePresenter> sourcePresenters = sourcesRepository
                .findAll()
                .stream()
                .map(source -> new SourcePresenter(source, sourceSlugs))
                .collect(Collectors.toList());


        return listArticlesForSources(model, selectedSources, sourcePresenters);
    }

    private String listArticlesForSources(Model model, List<SourceEntity> selectedSources, List<SourcePresenter> sourcePresenters) {
        List<ArticleEntity> articles = articlesRepository.findAllInSources(selectedSources);

        model.addAttribute("articles", articles);
        model.addAttribute("sources", sourcePresenters);

        return "list-articles";
    }
}
