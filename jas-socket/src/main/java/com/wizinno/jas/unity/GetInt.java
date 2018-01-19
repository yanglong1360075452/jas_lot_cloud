package com.wizinno.jas.unity;

/**
 * User:
 * Date:
 * Time:
 */
public class GetInt {
    public static int byte2int(byte[] res) {
// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

        int targets = (res[3] & 0xff) | ((res[2] << 8) & 0xff00) // | 表示安位或
                | ((res[1] << 24) >> 8) | (res[0] << 24);
        return targets;
    }
    public static int byteArrayToInt(byte[] b) {
        return   b[0] & 0xFF |
                (b[1] & 0xFF) << 8 |
                (b[2] & 0xFF) << 16 |
                (b[3] & 0xFF) << 24;
    }

    public static void main(String[] args) {
        short a=8143;
        byte[]byte1=new byte[2];
        byte[]byte2=new byte[2];
        byte1[0]= (byte)(int)(a>>0*8& 0xff);
        byte1[1]=(byte)(int)(a>>1*8& 0xff);
       byte2[0]= (byte)(int)(a>>0*12& 0xfff);
        byte2[1]=(byte)(int)(a>>1*4& 0xf);
        System.out.println("转成二进制后"+byte1[0]+","+byte1[1]);
        System.out.println("分别取位后"+byte2[0]+","+byte2[1]);
    }
}
