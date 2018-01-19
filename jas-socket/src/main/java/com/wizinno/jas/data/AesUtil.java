package com.wizinno.jas.data;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Administrator on 2017/1/18.
 */
public class AesUtil {
//    private static final Logger logger = Logger.getLogger(AesUtil.class);

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param password  加密密码
     * @return
     */
    public static byte[] encrypt2(String content, String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param byteContent 需要加密的内容
     * @param password  加密密码
     * @return
     */
    public static byte[] encrypt(byte[]byteContent, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] content, String password) {
        try {
//		                 KeyGenerator kgen = KeyGenerator.getInstance("AES");
//		                 kgen.init(128, new SecureRandom(password.getBytes()));
//		                 SecretKey secretKey = kgen.generateKey();
//		                 byte[] enCodeFormat = secretKey.getEncoded();
//		                 SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//		                 Cipher cipher = Cipher.getInstance("AES");// 创建密码器
//		                cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
//		                byte[] result = cipher.doFinal(content);
//		                return result; // 加密
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
//                    byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
       // String content = "{\"pn\":\"12\",\"sn\":\"3\",\"power\":\"xx3\",\"send_time\":\"29\",\"data\":[{\"time\":\"1466577029\",\"angle\":\"xxxxx\"},{\"time\":\"1466577029\",\"angle\":\"xxxxx\"}],\"level\":\"xxdsd\"}";
       // String content="{\"chunk_size\":\"42\",\"chunk_id\":\"1001\",\"chunk_ver\":\"0000\",\"guid\":\"xxxxxxxx12\"" +
             //  ",\"pn\":\"xxxxxxxx15\",\"sn\":\"xxxxxxxx14\"}";
//        String content="{\"chunk_size\":\"7\",\"chunk_id\":\"1002\",\"chunk_ver\":\"0000\",\"device_code\":\"12\"" +
//                ",\"device_level\":\"15\",\"train_time\":\"14\"}";
        //String con="2A 00 00 00 E9 03 00 00 30 30 30 30 30 30 30 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 41 30  30 30 30 30 30 30 30 30 00 3C D1 BC";
        //byte[] content=con.getBytes();
        byte []content={(byte)0x2A,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0xE9,(byte)0x03,(byte)0x00,(byte)0x00,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x41,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x30,(byte)0x00,(byte)0x3C,(byte)0xD1,(byte)0xBC
        ,0,0,0,0,0,0,0,0};
        System.out.println("加密前");
        System.out.println("加密前个数"+content.length);
        for(int i=0;i<content.length;i++){
            System.out.print(Integer.toHexString(content[i])+" ");
        }

        String key = "Owen_AesTest_Key";
        byte[] encryptResult = encrypt(content, key);
        System.out.println("加密后");
        System.out.println("加密后的个数"+encryptResult.length);
        for(int i=0;i<encryptResult.length;i++){
            System.out.print(Integer.toHexString(encryptResult[i])+" ");
        }
        //String encryptResultStr = parseByte2HexStr(encryptResult);
        //解密
       // byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
        byte []bytes={(byte)0x6E,(byte)0xEA,(byte)0x12,(byte)0x23,(byte)0x23,(byte)0x81,(byte)0x05,(byte)0x2B,(byte)0xB9,(byte)0x6E,(byte)0x2A,(byte)0xDA,(byte)0x95,(byte)0x36,(byte)0xF8,(byte)0xB8,(byte)0xA9,(byte)0x16,(byte)0xBE,(byte)0x4F,(byte)0x24,(byte)0xE6,(byte)0xA3,(byte)0xF2,(byte)0xB0,(byte)0x6C,(byte)0x4F,(byte)0x5C,(byte)0x6D,(byte)0x00,(byte)0x38,(byte)0xD0,(byte)0xA2,(byte)0x48,(byte)0xF0,(byte)0x74,(byte)0xEA,(byte)0x86,(byte)0x7A,(byte)0xE5,(byte)0xC6,(byte)0xE8,(byte)0x49,(byte)0x75,(byte)0xD8,(byte)0x2E,(byte)0xCF,(byte)0x32,(byte)0xA8,(byte)0xC8,(byte)0x5B,(byte)0x56,(byte)0x2F,(byte)0xF6,(byte)0x4C,(byte)0xF3,(byte)0x94,(byte)0xDD,(byte)0xF0,(byte)0x01,(byte)0x25,(byte)0x18,(byte)0x51,(byte)0x82
        };

        byte[] decryptResult = decrypt(bytes,key);
        System.out.println("解密后");
        System.out.println("解密后的个数"+decryptResult.length);
        for(int i=0;i<decryptResult.length;i++){
            System.out.print(Integer.toHexString(decryptResult[i])+" ");
        }


    }


}
