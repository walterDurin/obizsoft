package ru.lanit.dibr.utils.core;

import java.io.IOException;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:11
 */
public class FilteredSource implements Source {
    private Source source;
    private Source filteredSource;

    public FilteredSource(Source source) {
        this.source = source;
        this.filteredSource = source;
    }

    public void addFilter(Filter filter) {
        filteredSource = filter.apply(source);
    }

    public String readLine() throws IOException {
        return filteredSource.readLine();
    }

    public void reset() throws IOException {
        filteredSource.reset();
    }

    public void close() {
        //TODO
    }
}
