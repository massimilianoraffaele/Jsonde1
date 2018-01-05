package com.jsonde.util.io;

import com.jsonde.util.log.Log;

import java.io.Closeable;
import java.io.IOException;
/**
 * 
 * @author admin
 *
 */
public class IO {

    private static final Log log = Log.getLog(IO.class);

    /**
     * 
     * @param closeable
     */
    public static void close(Closeable closeable) {

        final String METHOD_NAME = "close(Closeable)";

        if (null == closeable)
            return;

        try {
            closeable.close();
        } catch (IOException e) {
            log.error(METHOD_NAME, e);
        }

    }

}
