package ru.lanit.dibr.utils.core;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:43
 */
public class GrepFilter extends AbstractFilter {

    public GrepFilter(String pattern, boolean inverted) {
        super(pattern, inverted);
    }

    @Override
    protected String readFilteredLine(Source source) throws IOException {
        String nextLine;
        if ((nextLine = source.readLine()) != null) {
            if ((nextLine.contains(pattern) ^ inverted)) {
                return nextLine;
            }
        }
        return LogSource.SKIP_LINE;
    }
}
