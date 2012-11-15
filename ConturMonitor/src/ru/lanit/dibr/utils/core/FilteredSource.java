package ru.lanit.dibr.utils.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:11
 */
public class FilteredSource implements Source {
    private final Source source;
    private Source filteredSource;
    private List<Filter> filters = new ArrayList<Filter>();

    public FilteredSource(Source source) {
        this.source = source;
        this.filteredSource = source;
    }

    public void addFilter(Filter filter) {
        source.reset();
        justRemoveFilter(filter.getClass());
        filters.add(filter);
        resetChain();
    }

    private void resetChain() {
        filteredSource = source;
        for (Filter nextFilter : filters) {
            filteredSource = nextFilter.apply(filteredSource);
        }
    }

    public void removeFilter(Class c) {
        reset();
        justRemoveFilter(c);
        resetChain();
    }

    private void justRemoveFilter(Class c) {
        for (Filter filter : filters) {
            if(filter.getClass().getCanonicalName().equals(c.getCanonicalName()) ) {
                filters.remove(filter);
                break;
            }
        }
    }

    public void clearFilters() {
        source.reset();
        filteredSource = source;
        filters.clear();
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
