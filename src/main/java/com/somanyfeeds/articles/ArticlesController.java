package com.somanyfeeds.articles;

import com.somanyfeeds.sources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
        List<SourcePresenter> sourcePresenters = selectedSources
                .stream()
                .map(SourcePresenter::selectedSource)
                .collect(Collectors.toList());

        return listArticlesForSources(model, selectedSources, sourcePresenters);
    }

    @RequestMapping("/{sourceSlugs}")
    public String listArticles(@PathVariable List<String> sourceSlugs, Model model) {
        List<SourceEntity> selectedSources = sourcesRepository.findAllBySlug(sourceSlugs);
        List<SourcePresenter> sourcePresenters = new ArrayList<>();

        for (SourceEntity source : sourcesRepository.findAll()) {
            if (selectedSources.contains(source)) {
                sourcePresenters.add(SourcePresenter.selectedSource(source));
            } else {
                sourcePresenters.add(SourcePresenter.unselectedSource(source));
            }
        }

        return listArticlesForSources(model, selectedSources, sourcePresenters);
    }

    private String listArticlesForSources(Model model, List<SourceEntity> selectedSources, List<SourcePresenter> sourcePresenters) {
        List<ArticleEntity> articles = articlesRepository.findAllInSources(selectedSources);

        model.addAttribute("articles", articles);
        model.addAttribute("sources", sourcePresenters);

        return "list-articles";
    }
}
