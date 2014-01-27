package ru.btc.utils.excel2csv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Vova
 * Date: 28.01.14
 * Time: 2:38
 */


public class Test {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("#(\\w+)");
        String str = "#A,#B,#E";
        Matcher m = pattern.matcher(str);
        while (m.find()) {
            System.out.println(m.group(1));
            System.out.println(m.start() +  ":" + m.end());
        }
    }
}
