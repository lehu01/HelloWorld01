package com.ccd.lib.print.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/21 11:11
 *     desc  : byte[]
 * </pre>
 */
public class ByteUtils {

    public static byte[] readFile(File file) throws FileNotFoundException {
        InputStream in = new FileInputStream(file);
        ByteArrayOutputStream output = null;
        byte[] bs = new byte[1024];
        int len = 0;
        output = new ByteArrayOutputStream();
        try {
            while ((len = in.read(bs)) != -1) {
                output.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = output.toByteArray();
        return data;
    }

    public static byte[] readByStream(InputStream in) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int read;
        byte[] data = new byte[1024];
        try {
            while ((read = in.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toByteArray();
    }
}
