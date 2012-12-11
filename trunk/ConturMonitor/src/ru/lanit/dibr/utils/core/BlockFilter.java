package ru.lanit.dibr.utils.core;

import java.io.IOException;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:43
 */
public class BlockFilter extends AbstractSearchFilter {

    private StringBuffer blockBufferToSearch = new StringBuffer();
    private StringBuffer blockBuffer = new StringBuffer();
    private boolean isPatternFoundInCurrentBlock = false;
    private String blockPattern;


    public BlockFilter(String blockPattern, String pattern, boolean inverted) {
        super(pattern, inverted);
        this.blockPattern = blockPattern;
    }

    @Override
    protected String readFilteredLine(Source source) throws IOException {
        return inverted?hideFilter(source):showFilter(source);
    }

    protected String showFilter(Source source) throws IOException {
        String nextLine = source.readLine();
        String result = LogSource.SKIP_LINE;

        if(nextLine != null && nextLine!=LogSource.SKIP_LINE) {
            boolean isNewBlockLine = nextLine.matches(".*" + blockPattern + ".*");
            if(isPatternFoundInCurrentBlock) {
                if (isNewBlockLine) {
                    isPatternFoundInCurrentBlock = false;
                } else {
                    result = nextLine;
                }
            }

            //нужна повторна€ проверка, т.к. флаг может помен€тьс€
            if(!isPatternFoundInCurrentBlock) {
                if (isNewBlockLine) {
                    blockBuffer.setLength(0);
                    blockBufferToSearch.setLength(0);
                }
                if(blockBuffer.length()>0) {
                    blockBuffer.append("\n");
                    blockBufferToSearch.append("\n");
                }
                blockBuffer.append(nextLine);
                blockBufferToSearch.append(removeLineNumbers(nextLine));
                if (pattern==null || (blockBufferToSearch.indexOf(pattern) >= 0) ^ inverted) {
                    isPatternFoundInCurrentBlock = true;
                    result = blockBuffer.toString();
                    blockBuffer.setLength(0);
                    blockBufferToSearch.setLength(0);
                }
            }
        }
        return result;
    }

    protected String hideFilter(Source source) throws IOException {
        String nextLine = source.readLine();
        String result = LogSource.SKIP_LINE;
        if (nextLine != null) {
            if (nextLine.matches(".*" + blockPattern + ".*")) {
                if (pattern==null || (blockBufferToSearch.indexOf(pattern) >= 0) ^ inverted) {
                    result = blockBuffer.toString();
                }
                blockBuffer.setLength(0);
                blockBufferToSearch.setLength(0);
            }
            if (nextLine != LogSource.SKIP_LINE) {
                blockBuffer.append(nextLine).append("\n");
                blockBufferToSearch.append(removeLineNumbers(nextLine)).append("\n");
            }
        }
        return result;
    }

    @Override
    protected void onReset() {
        blockBuffer.setLength(0);
        blockBufferToSearch.setLength(0);
    }
}
