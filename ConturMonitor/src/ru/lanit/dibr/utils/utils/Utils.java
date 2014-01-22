package ru.lanit.dibr.utils.utils;

import java.io.InputStream;

/**
 * User: Vova
 * Date: 13.12.12
 * Time: 1:18
 */
public class Utils {

    public static String inputStreamToString(InputStream is){
        return "";
    }

    public static int indexOf(String context, boolean caseSensitive, int from, String toFind) {
        if(from >= context.length()) {
            from = context.length()-1;
        }
        if (from < 0) {
            from = 0;
        }
        int toFindIndex = 0;
        char l = toFind.charAt(toFindIndex); //char to match now
        if (!caseSensitive) {
            l = Character.toLowerCase(l);
        }
        for (int i = from; i < context.length(); i++) {
            char c = context.charAt(i);
            if (!caseSensitive) {
                c = Character.toLowerCase(c);
            }
            if (c != l) {
                toFindIndex = 0;
            } else if (toFindIndex + 1 == toFind.length()) {
                return i - toFind.length() + 1;
                // all characters have been found, return index of match
            } else {
                ++toFindIndex;
            }
            l = toFind.charAt(toFindIndex);// set character to find
            if (!caseSensitive) {
                l = Character.toLowerCase(l);
            }
        }
        return -1;
    }

    public static int lastIndexOf(String context, boolean caseSensitive, int from, String toFind) {
        if(from >= context.length()) {
            from = context.length()-1;
        }
        if (from < 0) {
            from = 0;
        }
        int toFindIndex = toFind.length()-1;
        char l = toFind.charAt(toFindIndex);//char to match now
        if (!caseSensitive) {
            l = Character.toLowerCase(l);
        }
        for (int i = from; i >= 0; i--) {
            char c = context.charAt(i);
            if (!caseSensitive) {
                c = Character.toLowerCase(c);
            }
            if (c != l) {
                toFindIndex = toFind.length()-1;
            } else if (toFindIndex == 0) {
                return i;
                // all characters have been found, return index of match
            } else {
                --toFindIndex;
            }
            l = toFind.charAt(toFindIndex);// set character to find
            if (!caseSensitive) {
                l = Character.toLowerCase(l);
            }
        }
        return -1;
    }
}
