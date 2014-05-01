package ru.lanit.dibr.utils.core;

import java.util.List;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:10
 */
public interface Filter {
    public Filter addStringToSearch(String str);
    public List<String> getStringsToSearch();
    public void removeStringFromSearch(String str);
    public Source apply(Source source);
    public void disable();
    public boolean isActive();
}
