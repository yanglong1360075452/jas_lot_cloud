package com.wizinno.jas.unity;

/**
 * User:
 * Date:
 * Time:
 */
public class Off {
    public static int getOff (int i){
        // a 除以16整除
        int a=i/16;
        //偏移量off
        int off=16*(a+1);
        return off;
    }

    public static void main(String[] args) {
        System.out.print( getOff(74));

    }
}
