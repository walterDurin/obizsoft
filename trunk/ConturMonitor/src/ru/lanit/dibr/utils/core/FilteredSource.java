package ru.lanit.dibr.utils.core;

import java.io.IOException;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:11
 */
public class FilteredSource implements Source {
    private final Source source;
    private Source filteredSource;

    public FilteredSource(Source source) {
        this.source = source;
        this.filteredSource = source;
    }

    public void addFilter(Filter filter) {
        source.reset();
        filteredSource = filter.apply(source);
    }

    public void clearFilters() {
        source.reset();
        filteredSource = source;
    }

    public String readLine() throws IOException {
        return filteredSource.readLine();
    }

    public void reset() {
        filteredSource.reset();
    }

    public void close() {
        //TODO
    }

    public void setPaused(boolean paused) {
        filteredSource.setPaused(paused);
    }
}
