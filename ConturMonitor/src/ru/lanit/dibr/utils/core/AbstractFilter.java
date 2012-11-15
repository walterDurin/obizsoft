package ru.lanit.dibr.utils.core;

import java.io.IOException;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:43
 */
public abstract class AbstractFilter implements Filter {

    abstract protected String readFilteredLine(Source source) throws IOException;

    protected void onReset() {
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
        };
    }
}
