package ru.lanit.dibr.utils.core;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:10
 */
public interface Filter {
    public boolean isInverted();
    public Source apply(Source source);
}
