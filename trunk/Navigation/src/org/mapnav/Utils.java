package org.mapnav;

import java.io.InputStream;
import java.io.IOException;

/**
 * Date: 19.08.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class Utils {

    public static byte[] longToBytes(long data) {
        long l = data;
        byte[] bytes = new byte[4];
        for (int i = 0; i < bytes.length; i++) {
            bytes[3-i] = (byte) l;
            l=l>>8;
        }
        return bytes;
    }

    public static long readLong(InputStream is) throws IOException {
        long longValue = 0;
        longValue+=((long)is.read())<<24;
        longValue+=((long)is.read())<<16;
        longValue+=((long)is.read())<<8;
        longValue+=(long)is.read();
        return longValue;
    }
}
