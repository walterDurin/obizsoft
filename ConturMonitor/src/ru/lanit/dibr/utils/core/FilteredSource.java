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
    private Source queueFilteredSource;
    private Source screenFilteredSource;
    private Source resultFilteredSource;
    private List<Filter> queueFilters = new ArrayList<Filter>();
    private List<Filter> screenFilters = new ArrayList<Filter>();
    public FilterScreen filterScreen;

    public FilteredSource(Source source) {
        this.source = source;
        this.queueFilteredSource = source;
        this.resultFilteredSource = this.queueFilteredSource;
    }

    public void addFilterToQueue(final Filter filter) {
        doMethod(new Method() {
            public void run() {
                removeFilter(filter);
                queueFilters.add(filter);
                resetQueueFilters();
            }
        });
    }

    public void addFilterToScreen(final Filter filter) {
        doMethod(new Method() {
            public void run() {
                removeFilter(filter);
                screenFilters.add(filter);
                resetScreenFilters();
            }
        });
    }

    private void doMethod(Method m) {
        boolean sourceIsPaused = source.isPaused();
        //≈сли вывод уже приостановлен то не мен€ем поведени€
        if(!sourceIsPaused) {
            source.setPaused(true);
        }
        source.reset();
        m.run();
        //≈сли вывод уже приостановлен то не мен€ем поведени€
        if(!sourceIsPaused) {
            source.setPaused(false);
        }
    }

//    private void resetForParticularList(List filterList) {
//        if(filterList!=null) {
//            if(filterList==queueFilters) {
//                resetQueueFilters();
//            } else if (filterList==screenFilters) {
//                resetScreenFilters();
//            }
//        }
//    }
//
    private void resetQueueFilters() {
        queueFilteredSource = source;
        for (Filter nextFilter : queueFilters) {
            queueFilteredSource = nextFilter.apply(queueFilteredSource);
        }
        if(filterScreen!=null) {
            screenFilteredSource = filterScreen.apply(queueFilteredSource);
            resultFilteredSource = screenFilteredSource;
        } else {
            resultFilteredSource = queueFilteredSource;
        }
    }

    private void resetScreenFilters() {
        screenFilteredSource = queueFilteredSource;
        if(!screenFilters.isEmpty()) {
            filterScreen = new FilterScreen();
            for (Filter nextFilter : screenFilters) {
                filterScreen.add(nextFilter);
            }
            screenFilteredSource = filterScreen.apply(queueFilteredSource);
        } else {
            filterScreen = null;
        }
        resultFilteredSource = screenFilteredSource;
    }

    public void removeFilter(final Filter filterToRmove) {
        doMethod(new Method() {
            public void run() {
                removeSameFilters(filterToRmove);
            }
        });
    }

    private void removeSameFilters(Filter newFilter) {
        for (Filter filter : queueFilters) {
            if(filter.equals(newFilter)) {
                queueFilters.remove(filter);
                resetQueueFilters();
                return;
            }
        }
        for (Filter filter : screenFilters) {
            if(filter.equals(newFilter)) {
                screenFilters.remove(filter);
                resetScreenFilters();
                return;
            }
        }
    }

    public void clearFilters() {
        doMethod(new Method() {
            public void run() {
                queueFilters.clear();
                screenFilters.clear();
                resetQueueFilters();
                resetScreenFilters();
            }
        });
    }

    public void clearQueueFilters() {
        doMethod(new Method() {
            public void run() {
                queueFilters.clear();
                resetQueueFilters();
            }
        });
    }

    public void clearScreenFilters() {
        doMethod(new Method() {
            public void run() {
                screenFilters.clear();
                resetScreenFilters();
            }
        });
    }

    public String readLine() throws IOException {
        return resultFilteredSource.readLine();
    }

    public void reset() {
        resultFilteredSource.reset();
    }

    public void close() {
        //TODO
    }

    public void setPaused(boolean paused) {
        resultFilteredSource.setPaused(paused);
    }

    public boolean isPaused() {
        return resultFilteredSource.isPaused();
    }

    private interface Method {
        public void run();
    }
}
