package com.wizinno.jas.util;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/** 将二维码图片转换成二进制数据
 * User:
 * Date:
 * Time:
 */
public class CodeBinary {
    public static byte[] getCodeBinary(){
        byte []bytes=new byte[186];
        int rgb[]=new int[3];
        int k=14;
        int i=7;
        bytes[0]=174>>24;
        bytes[1]=174>>16;
        bytes[2]=174>>8;
        bytes[3]= (byte) 174;
        bytes[4]=1005>>8;
        bytes[5]= (byte) 1005;
        bytes[6]=0;
        bytes[7]=0;
        bytes[8]=37;
        bytes[9]=37;
        //crc校验

        File file=new File("D:\\apache-tomcat-9.0\\img\\1.jpg");
        BufferedImage bi = null;
        try {
             bi=ImageIO.read(file);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width=bi.getWidth()-30;
        int height= bi.getHeight()-30;
        int minX=bi.getMinX()+35;
        int minY=bi.getMinY()+35;
        System.out.println("width="+width+",height="+height+".");
        System.out.println("minx="+minX+",miniy="+minY+".");
        for(int x=minX;x<width;x+=10){
            for(int y=minY;y<height;y+=10){
                int pixel=bi.getRGB(x,y);
                rgb[0] = (pixel & 0xff0000 ) >> 16 ;
                rgb[1] = (pixel & 0xff00 ) >> 8 ;
                rgb[2] = (pixel & 0xff );
                    if(rgb[0]>125&&rgb[1]>125&&rgb[2]>125){
                        bytes[k]|= (byte) (1<<i);

                    }else if(rgb[0]<125&&rgb[1]<125&&rgb[2]<125){
                        bytes[k]&=~(byte) (1<<i);

                    }else{
                        System.out.print("系统错误");
                    }
                    i--;
                    if(i<0){
                       k++;
                        i=7;
                    }




            }


        }
        System.out.print("---------------------------------"+k);
        return bytes;
    }

    public static void main(String args[]) {
        getCodeBinary();
    }

}
