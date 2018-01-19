package com.wizinno.jas.controller;

import net.sf.json.JSONObject;

/**
 * User:
 * Date:
 * Time:
 */
public class Test {
    public static void main(String[] args) {
        String a ="";
        String b="A0";
        String a1="05d6ff37-33354348-43125711      ";
        String b1="00000000";
        String msg="{\"juid\":\"" + a1 + "\",\"pn\":\"" + b + "\",\"sn\":\"" + b1 + "\",\"cmd\":\"" + a + "\"}";
        JSONObject jsonObject= JSONObject.fromObject(msg);
       System.out.print(jsonObject.getString("juid"));

    }
    public static int byteToInt2(byte[] b) {
        int mask=0xff;
        int temp=0;
        int n=0;
        for(int i=0;i<4;i++){
            n<<=8;
            temp=b[i]&mask;
            n|=temp;
        }
        return n;
    }
    public static short getShort(byte[] b, int index) {
        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
    }
}
