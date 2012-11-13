package ru.lanit.dibr.utils.core;

import java.io.IOException;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:43
 */
public class AbstractFilter implements Filter {
    protected String pattern;
    protected boolean inverted = false;

    public AbstractFilter(String pattern, boolean inverted) {
        this.pattern = pattern;
        this.inverted = inverted;
    }

    public boolean isInverted() {
        return inverted;
    }

    protected String readFilteredLine(Source source) throws IOException {
        if(true)
            throw new RuntimeException("Not implemented");
        return source.readLine();
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
            }

            public void close() {
                closed = true;
            }

            public void setPaused(boolean paused) {
                source.setPaused(paused);
            }
        };
    }
}
