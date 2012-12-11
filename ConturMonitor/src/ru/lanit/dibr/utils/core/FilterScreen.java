package ru.lanit.dibr.utils.core;

import java.io.IOException;
import java.util.*;

/**
 * User: Vova
 * Date: 09.12.12
 * Time: 17:53
 */
public class FilterScreen extends AbstractFilter {
    private Source initialSource;
    private RepeatedSource repeatedSource;
    private Set<Filter> screenFilters = new HashSet<Filter>();
    private Map<Filter, Source> sources;

    public FilterScreen() {
    }


    @Override
    public Source apply(Source source) {
        initialSource = source;
        sources = new HashMap<Filter, Source>();
        repeatedSource = new RepeatedSource(source,screenFilters.size());
        for (Filter filter : screenFilters) {
            sources.put(filter, filter.apply(repeatedSource));
        }
        return super.apply(source);
    }

    public void add(Filter filter) {
        if(screenFilters.add(filter) && repeatedSource!=null) {
            repeatedSource.setRepeatCount(screenFilters.size());
            sources.put(filter, filter.apply(repeatedSource));
        }
    }

    public void remove(Filter filter) {
        if(screenFilters.remove(filter)) {
            repeatedSource.setRepeatCount(screenFilters.size());
            sources.remove(filter);
        }
    }

    @Override
    protected String readFilteredLine(Source source) throws IOException {
        String previousNotEmptyData = LogSource.SKIP_LINE;
        for (Filter filter : screenFilters) {
            String currentData = sources.get(filter).readLine();
            if(currentData!=null && currentData!=LogSource.SKIP_LINE && !currentData.isEmpty()) {
                if(previousNotEmptyData==LogSource.SKIP_LINE) {
                    previousNotEmptyData = currentData;
                } else {
                    if(!previousNotEmptyData.equals(currentData)) {
                        throw new RuntimeException("Filters returns different data!\n"+previousNotEmptyData+"\n"+currentData);
                    }
                }
            }
        }
        return previousNotEmptyData;
    }
}
