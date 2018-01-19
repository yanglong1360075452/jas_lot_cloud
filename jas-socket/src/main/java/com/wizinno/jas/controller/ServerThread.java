package com.wizinno.jas.controller;
import com.wizinno.jas.data.AesUtil;
import com.wizinno.jas.data.MyException;
import com.wizinno.jas.unity.GetShort;
import com.wizinno.jas.unity.Off;
import com.wizinno.jas.unity.Switch;

import java.io.*;
import java.net.Socket;
/**
 * Created by Administrator on 2017/8/17.
 */
public class ServerThread extends Thread {
    Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    //线程执行的操作，响应客户端的请求
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            //获取输入流，并读取客户端信息
            is = socket.getInputStream();
//          测试方式；
                byte[] bufs = new byte[1024];
                int lens = is.read(bufs);
                if(lens>0){
                    while(true){
                        String text = new String(bufs);
                        byte[]  buf = new byte[lens - 12];
                        System.arraycopy(bufs, 0, buf, 0, lens - 12);
                        int len = buf.length;
                        System.out.println("len的长度是：" + len);
                        byte[] buff = new byte[len];
                        for (int i = 0; i < len; i++) {
                            buff[i] = buf[i];
                        }
                        System.out.println("原始数据");
                        String key = "Owen_AesTest_Key";
                        //AES解密
                        byte[] bytes = AesUtil.decrypt(buff, key);
                        String a = new String(bytes, "utf-8");
                        Switch s = new Switch();
                        int total = 0;
                        for (int i = 1; i < 6; i++) {
                            byte[] idi = new byte[2];
                            System.arraycopy(bytes, 4 + total, idi, 0, 2);
                            short chunk_idi = GetShort.getShort(idi, 0);
                            int sizei = 0;
                            try {
                                sizei = s.doBag(bytes, chunk_idi, 0 + total);
                            } catch (MyException e) {
                                e.printStackTrace();
                            }
                            total += sizei;
                            if (Off.getOff(total) == bytes.length) {
                                break;

                            }
                        }
                        socket.shutdownInput();//关闭输入流
                        //获取输出流，响应客户端的请求
                        os = socket.getOutputStream();
                        pw = new PrintWriter(os);
                        pw.write("recieve_ok");
                        pw.flush();//调用flush()方法将缓冲输出
                    }
                    }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            //关闭资源
            try {
                if (pw != null)
                    pw.close();
                if (os != null)
                    os.close();
                if (br != null)
                    br.close();
                if (isr != null)
                    isr.close();
                if (is != null)
                    is.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
