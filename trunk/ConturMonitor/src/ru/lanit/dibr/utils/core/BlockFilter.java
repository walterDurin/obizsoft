package ru.lanit.dibr.utils.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:43
 */
public class BlockFilter extends AbstractFilter {

    private StringBuffer blockBufferToSearch = new StringBuffer();
    private StringBuffer blockBuffer = new StringBuffer();
    private int blockBufferLines = 0;
    private int skippedLines = 0;
    private StringBuffer resultBuffer = new StringBuffer();
    private String blockPattern;
    private boolean foundSkipLine = false;

    public BlockFilter(String blockPattern, String pattern, boolean inverted) {
        super(pattern, inverted);
        stringsToSearch.add(pattern);
        this.blockPattern = blockPattern;
    }

    public BlockFilter(String blockPattern, boolean inverted) {
        super(inverted);
        this.blockPattern = blockPattern;
    }

    @Override
    protected String readFilteredLine(Source source) throws IOException {
        String result;
        if(resultBuffer.length()!=0) {
            int i = resultBuffer.indexOf("\n");
            if(i>0) {
                result = resultBuffer.substring(0, i);
                resultBuffer.delete(0, i+1);
            } else {
                result = resultBuffer.toString();
                resultBuffer.setLength(0);
            }
            return result;
        }
        if(foundSkipLine) {
            foundSkipLine = false;
            return LogSource.SKIP_LINE;
        }
        if(skippedLines>0) {
            --skippedLines;
            return LogSource.SKIP_LINE;
        }
        result = hideFilter(source);
        return result;
    }

    protected String hideFilter(Source source) throws IOException {
        String nextLine;
        String nextBlockFirstLine = null;
        //Вычитываем блок до следующего блока или до появления SKIP_LINE (что пока что считаем появлением нового блока)
        while ( !(foundSkipLine = ((nextLine = source.readLine()) == LogSource.SKIP_LINE)) && nextLine != null) {
            if(blockBuffer.length()>0 && nextLine.matches(".*" + blockPattern + ".*")) {
                nextBlockFirstLine = nextLine;
                break;
            }
            if(blockBuffer.length()>0) {
                blockBuffer.append("\n");
                blockBufferToSearch.append("\n");
            }
            blockBuffer.append(nextLine);
            ++blockBufferLines;
            blockBufferToSearch.append(removeLineNumbers(nextLine));
        }

        boolean oneOfStringIsFound = false;
        for (String nextString : stringsToSearch) {
            if(oneOfStringIsFound = blockBufferToSearch.indexOf(nextString) >= 0) {
                break;
            }
        }

        if (blockBufferToSearch.length ()!=0 && (oneOfStringIsFound ^ inverted)) {
//                    result = blockBuffer.toString();
            resultBuffer.append(blockBuffer);
            blockBuffer.setLength(0);
            blockBufferLines = 0;
            blockBufferToSearch.setLength(0);
            if(nextBlockFirstLine!=null) {
                blockBuffer.append(nextBlockFirstLine);
                ++blockBufferLines;
                blockBufferToSearch.append(removeLineNumbers(nextBlockFirstLine));
            }
            return readFilteredLine(source);
        }
        blockBuffer.setLength(0);
        if(blockBufferLines > 0) {
            skippedLines = blockBufferLines-1; //т.к. итак возвращаем SKIP_LINE
            if(foundSkipLine) {
                ++skippedLines;
            }
        }
        blockBufferLines = 0;
        blockBufferToSearch.setLength(0);
        if(nextBlockFirstLine!=null) {
            blockBuffer.append(nextBlockFirstLine);
            ++blockBufferLines;
            blockBufferToSearch.append(removeLineNumbers(nextBlockFirstLine));
        }
        foundSkipLine = false;
        return LogSource.SKIP_LINE;
    }

    @Override
    protected void onReset() {
        blockBuffer.setLength(0);
        blockBufferToSearch.setLength(0);
    }
}
