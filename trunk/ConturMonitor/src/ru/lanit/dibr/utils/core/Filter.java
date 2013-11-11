package ru.lanit.dibr.utils.core;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:10
 */
public interface Filter {
    public Filter addStringToSearch(String str);
    public Source apply(Source source);
    public void invalidate();
    public boolean isValid();
}
