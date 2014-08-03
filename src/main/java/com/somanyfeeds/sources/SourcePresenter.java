package com.somanyfeeds.sources;

public class SourcePresenter {
    private final SourceEntity source;
    private final boolean selected;

    private SourcePresenter(SourceEntity source, boolean selected) {
        this.source = source;
        this.selected = selected;
    }

    public static SourcePresenter selectedSource(SourceEntity source) {
        return new SourcePresenter(source, true);
    }

    public static SourcePresenter unselectedSource(SourceEntity source) {
        return new SourcePresenter(source, false);
    }

    public String getName() {
        return source.getName();
    }

    public String getSlug() {
        return source.getSlug();
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SourcePresenter that = (SourcePresenter) o;

        if (selected != that.selected) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (selected ? 1 : 0);
        return result;
    }
}
