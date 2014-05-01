package ru.lanit.dibr.utils.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:43
 */
public abstract class AbstractFilter implements Filter {

    protected boolean inverted = false;
    protected List<String> stringsToSearch = new ArrayList<String>();

    protected AbstractFilter(boolean inverted) {
        this.inverted = inverted;
    }

    protected AbstractFilter(String pattern, boolean inverted) {
        this.inverted = inverted;
        this.addStringToSearch(pattern);
        this.isValid = true;
    }

    abstract protected String readFilteredLine(Source source) throws IOException;
    private boolean isValid = false;
    protected void onReset() {
    }

    public void disable() {
        stringsToSearch.clear();
        isValid = false;
    }

    public boolean isActive() {
        return isValid;
    }

    public Source apply(final Source source) {
        return new Source() {

            private boolean closed;

            public String readLine() throws IOException {
                if(closed) {
                    throw new RuntimeException("Stream is closed!");
                }
                return readFilteredLine(source);
            }

            public void reset() {
                source.reset();
                onReset();
            }

            public void close() {
                closed = true;
            }

            public void setPaused(boolean paused) {
                source.setPaused(paused);
            }

            public boolean isPaused() {
                return source.isPaused();
            }
        };
    }

    protected String removeLineNumbers(String selected) {
        selected = selected.replaceAll("^[\\s\\d]*:\\s", "");
        selected = selected.replaceAll("\n[\\s\\d]*:\\s", "\n");
        return selected;
    }

    public AbstractFilter addStringToSearch(String str) {
        isValid = true;
        stringsToSearch.add(str);
        return this;
    }

    public void removeStringFromSearch(String str) {
        stringsToSearch.remove(str);
    }

    public List<String> getStringsToSearch() {
        return stringsToSearch;
    }
}
