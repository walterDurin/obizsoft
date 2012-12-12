package ru.lanit.dibr.utils.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:43
 */
public class GrepFilter extends AbstractSearchFilter {

    private List<String> stringsToSearch = new ArrayList<String>();

    public GrepFilter(String pattern, boolean inverted) {
        super(pattern, inverted);
        stringsToSearch.add(pattern);
    }

    public GrepFilter(boolean inverted, String... pattern) {
        super(pattern[0], inverted);
        stringsToSearch.addAll(Arrays.asList(pattern));
    }

    public void addStringToSearch(String str) {
        stringsToSearch.add(str);
    }

    @Override
    protected String readFilteredLine(Source source) throws IOException {
        String nextLine;
        if ((nextLine = source.readLine()) != null && nextLine!=LogSource.SKIP_LINE) {
            boolean oneOfStringsIsFound = false;
            for (String nextString : stringsToSearch) {
                if (oneOfStringsIsFound = removeLineNumbers(nextLine).contains(nextString)) {
                    break;
                }
            }
            if(oneOfStringsIsFound^inverted) {
                return nextLine;
            }
        }
        return LogSource.SKIP_LINE;
    }
}
