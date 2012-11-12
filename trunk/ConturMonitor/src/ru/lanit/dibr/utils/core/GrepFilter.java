package ru.lanit.dibr.utils.core;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:43
 */
public class GrepFilter implements Filter {
    private String pattern;
    private boolean inverted = false;

    public GrepFilter(String pattern, boolean inverted) {
        this.pattern = pattern;
        this.inverted = inverted;
    }

    public boolean isInverted() {
        return inverted;
    }

    public Source apply(final Source source) {
        return new Source() {

            private boolean closed;

            public String readLine() throws IOException {
                String nextLine;
                while ((nextLine = source.readLine()) != null && !closed) {
                    if(pattern==null || pattern.length()==0) {
                        return nextLine;
                    }
                    if ((nextLine.contains(pattern) ^ inverted)) {
                        return nextLine;
                    }
                }
                return source.readLine();
            }

            public void reset() throws IOException {
                source.reset();
            }

            public void close() {
                closed = true;
            }
        };
    }
}
