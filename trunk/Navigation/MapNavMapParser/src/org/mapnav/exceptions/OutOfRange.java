package org.mapnav.exceptions;

/**
 * Date: 18.08.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class OutOfRange extends Exception {
    public OutOfRange(String s) {
        super(s);
    }

    public OutOfRange(Object o) {
        super(o+"");
    }
}
