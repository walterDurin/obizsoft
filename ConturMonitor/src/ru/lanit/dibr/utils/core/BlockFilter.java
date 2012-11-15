package ru.lanit.dibr.utils.core;

import java.io.IOException;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:43
 */
public class BlockFilter extends AbstractSearchFilter {

    private StringBuffer blockBuffer = new StringBuffer();
    private String blockPattern;


    public BlockFilter(String blockPattern, String pattern, boolean inverted) {
        super(pattern, inverted);
        this.blockPattern = blockPattern;
    }


    @Override
    protected String readFilteredLine(Source source) throws IOException {
        String nextLine = source.readLine();
        String result = LogSource.SKIP_LINE;
        if (nextLine != null) {
            if (nextLine.matches(".*" + blockPattern + ".*")) {
                if (pattern==null || (blockBuffer.indexOf(pattern) >= 0) ^ inverted) {
                    result = blockBuffer.toString();
                }
                blockBuffer.setLength(0);
            }
            if (nextLine != LogSource.SKIP_LINE) {
                blockBuffer.append(nextLine).append("\n");
            }
        }
        return result;
    }

    @Override
    protected void onReset() {
        blockBuffer.setLength(0);
    }
}
