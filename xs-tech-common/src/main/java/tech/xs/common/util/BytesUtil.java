package tech.xs.common.util;

import java.io.ByteArrayOutputStream;

/**
 * Bytes工具
 *
 * @author 沈家文
 * @date 2021/5/13 18:31
 */
public class BytesUtil {

    public static byte[] readZenoEnd(byte[] data, int startIndex) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (int i = startIndex; i < data.length; i++) {
                if (data[i] != 0) {
                    out.write(data[i]);
                } else {
                    break;
                }
            }
            out.close();
            out.flush();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] sub(byte[] data, int startIndex, int endIndex) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (int i = startIndex; i < endIndex; i++) {
                out.write(data[i]);
            }
            out.close();
            out.flush();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int toInt(byte[] b, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int n = ((int) b[i]) & 0xff;
            n <<= (--len) * 8;
            sum += n;
        }
        return sum;
    }

    public static int toInt(byte[] b) {
        int sum = 0;
        int len = b.length;
        int end = 0 + len;
        for (int i = 0; i < end; i++) {
            int n = ((int) b[i]) & 0xff;
            n <<= (--len) * 8;
            sum += n;
        }
        return sum;
    }

}
