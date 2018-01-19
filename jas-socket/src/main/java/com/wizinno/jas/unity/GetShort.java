package com.wizinno.jas.unity;

/**
 * User:
 * Date:
 * Time:
 */
public class GetShort {
    public static short getShort(byte[] b, int index) {
        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
    }
}
