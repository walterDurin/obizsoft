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
public class BlockFilter extends AbstractSearchFilter {

    private StringBuffer blockBufferToSearch = new StringBuffer();
    private StringBuffer blockBuffer = new StringBuffer();
    private int blockBufferLines = 0;
    private int skippedLines = 0;
    private StringBuffer resultBuffer = new StringBuffer();
    private boolean isPatternFoundInCurrentBlock = false;
    private String blockPattern;
    private boolean foundSkipLine = false;

    private List<String> stringsToSearch = new ArrayList<String>();

    public BlockFilter(String blockPattern, String pattern, boolean inverted) {
        super(pattern, inverted);
        stringsToSearch.add(pattern);
        this.blockPattern = blockPattern;
    }

    public BlockFilter(String blockPattern, boolean inverted, String... pattern) {
        super(pattern[0], inverted);
        stringsToSearch.addAll(Arrays.asList(pattern));
        this.blockPattern = blockPattern;
    }

    public void addStringToSearch(String str) {
        stringsToSearch.add(str);
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
//            System.out.println((inverted?"hide":"show")+" block filter(\"" + pattern + "\"):\n" + result);
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
//        result = inverted?hideFilter(source):showFilter(source);
        result = hideFilter(source);
//        System.out.println((inverted?"hide":"show")+" block filter(\"" + pattern + "\"):\n" + result);
        return result;
    }

//    protected String showFilter(Source source) throws IOException {
//        String nextLine = source.readLine();
//        String result = LogSource.SKIP_LINE;
//
//        if(nextLine != null && nextLine!=LogSource.SKIP_LINE) {
//            boolean isNewBlockLine = nextLine.matches(".*" + blockPattern + ".*");
//            if(isPatternFoundInCurrentBlock) {
//                if (isNewBlockLine) {
//                    isPatternFoundInCurrentBlock = false;
//                } else {
//                    result = nextLine;
//                }
//            }
//
//            //нужна повторная проверка, т.к. флаг может поменяться
//            if(!isPatternFoundInCurrentBlock) {
//                if (isNewBlockLine) {
//                    blockBuffer.setLength(0);
//                    blockBufferToSearch.setLength(0);
//                }
//                if(blockBuffer.length()>0) {
//                    blockBuffer.append("\n");
//                    blockBufferToSearch.append("\n");
//                }
//                blockBuffer.append(nextLine);
//                blockBufferToSearch.append(removeLineNumbers(nextLine));
//                if (pattern==null || (blockBufferToSearch.indexOf(pattern) >= 0) ^ inverted) {
//                    isPatternFoundInCurrentBlock = true;
////                    result = blockBuffer.toString();
//                    resultBuffer.append(blockBuffer);
//                    blockBuffer.setLength(0);
//                    blockBufferToSearch.setLength(0);
//                    return readFilteredLine(source);
//                }
//            }
//        }
//        return result;
//    }

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
