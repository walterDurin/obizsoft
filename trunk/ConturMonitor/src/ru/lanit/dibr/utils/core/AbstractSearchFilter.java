package ru.lanit.dibr.utils.core;

import java.io.IOException;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:43
 */
public abstract class AbstractSearchFilter extends AbstractFilter {
    protected String pattern;
    protected boolean inverted = false;

    public AbstractSearchFilter(String pattern, boolean inverted) {
        this.pattern = pattern;
        this.inverted = inverted;
    }

}
