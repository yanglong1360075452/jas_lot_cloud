package com.wizinno.jas.data;

/**
 * Created by Administrator on 2017/1/13.
 * 冗余校验
 */

public class Utility {
//    public static int get_CRC(String pBuf, int index, int len) {
//        //TODO: implement crc
//        int crc = 0;
//        char[] cs = pBuf.toCharArray();
//        byte i;
//        for (i = 0; i < cs.length; i++) {
//            cs[i] &= 0xff;
//        }
//        int j = 0;
//        while (len-- > 0) {
//            for (int i = 0x80; i != 0; i = i >> 1) {
//                if ((crc & 0x8000) != 0) {
//                    crc = crc << 1;
//                    crc = crc ^ 0x1021;
//                } else {
//                    crc = crc << 1;
//                }
//                if ((cs[j] & i) != 0) {
//                    crc = crc ^ 0x1021;
//                }
//            }
//            j++;
//        }
//        return crc;
//    }


//    public static char[] intToChar(int i) {
//        if (i <= 0xff) {
//            return new char[]{(char) i};
//        }
//        char[] cs = new char[2];
//        cs[1] = (char) (i / (1 << 8));
//        cs[0] = (char) (i - cs[1] * (1 << 8));
//        return cs;
//    }
public static int get_CRC(byte[] cs) {
    int crc = 0, i, len = cs.length-12, index = 0;
    //TODO: implement crc
    while( (len--) != 0) {
        for(i = 0x80; i != 0; i = i >> 1) {
            if((crc & 0x8000) != 0) {
                crc = crc << 1;
                crc = crc ^ 0x1021;
            }
            else {
                crc = crc << 1;
            }
            if((cs[index+8] & i) != 0) {
                crc = crc ^ 0x1021;
            }
        }
        index++;
    }
    return crc;

}


}





