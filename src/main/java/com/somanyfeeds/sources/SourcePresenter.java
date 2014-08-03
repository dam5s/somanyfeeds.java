package com.somanyfeeds.sources;

import java.util.*;
import java.util.stream.Collectors;

public class SourcePresenter {
    private final SourceEntity source;
    private final List<String> slugs;

    public SourcePresenter(SourceEntity source, List<String> slugs) {
        this.source = source;
        this.slugs = slugs;
    }

    public String getName() {
        return source.getName();
    }

    public String getSlug() {
        return source.getSlug();
    }

    public boolean isSelected() {
        return slugs.contains(getSlug());
    }

    public String getLink() {
        ArrayList<String> newSlugs = new ArrayList<>(slugs);
        if (isSelected()) {
            newSlugs.remove(getSlug());
        } else {
            newSlugs.add(getSlug());
        }

        return "/" + newSlugs.stream().sorted().collect(Collectors.joining(","));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SourcePresenter presenter = (SourcePresenter) o;

        if (slugs != null ? !slugs.equals(presenter.slugs) : presenter.slugs != null) return false;
        if (source != null ? !source.equals(presenter.source) : presenter.source != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (slugs != null ? slugs.hashCode() : 0);
        return result;
    }
}
