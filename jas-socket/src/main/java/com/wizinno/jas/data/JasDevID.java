package com.wizinno.jas.data;

import com.wizinno.jas.unity.GetInt;
import com.wizinno.jas.unity.GetShort;

/**
 * Created by Administrator on 2017/1/11.
 */
public class JasDevID  {
    public final short  CHUNK_ID  = 1001;
    public final short CHUNK_VER = 0000;
    public final short GUID_LEN = 32;
    public final short PN_LEN = 2;
    public final short SN_LEN = 8;
    public final short CHUNK_SIZE = GUID_LEN + PN_LEN + SN_LEN;
    private String guid;
    private String pn;
    private String sn;
    private String cmd;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String deserialize(byte[]bytes)throws MyException{
        //1.判断 size ,id ,ver ,是否符合规定(bytes为devID的数据包）
         byte[]bytesize=new byte[4];
         byte[]byteid=new byte[2];
         byte[]bytever=new byte[2];
         byte[]byteguid=new byte[32];
         byte[]bytepn=new byte[2];
         byte[]bytesn=new byte[8];
         byte[]bytecmd=new byte[1];
         byte[]bytecrc=new byte[4];
        System.arraycopy(bytes,0,bytesize,0,4);
        System.arraycopy(bytes,4,byteid,0,2);
        System.arraycopy(bytes,6,bytever,0,2);
        System.arraycopy(bytes,8,byteguid,0,32);
        System.arraycopy(bytes,40,bytepn,0,2);
        System.arraycopy(bytes,42,bytesn,0,8);
        System.arraycopy(bytes,50,bytecmd,0,1);
        System.arraycopy(bytes,51,bytecrc,0,4);
        short chunk_size= GetShort.getShort(bytesize,0);
        System.out.print("chunk_size:" +chunk_size);
        short chunk_id=GetShort.getShort(byteid,0);
        short chunk_ver=GetShort.getShort(bytever,0);
        String chunk_guid=new String(byteguid).trim();
        String chunk_pn=new String(bytepn).trim();
        String chunk_sn=new String(bytesn).trim();
        String chunk_cmd=new String(bytecmd).trim();
        int chunk_crc= GetInt.byteArrayToInt(bytecrc);
        String msg = null;
        if(chunk_size==43){
             msg="{\"juid\":\"" + chunk_guid + "\",\"pn\":\"" + chunk_pn + "\",\"sn\":\"" + chunk_sn + "\"}";
            System.out.println("juid是:"+chunk_guid);
            System.out.println("pn是:"+chunk_pn);
            System.out.println("sn是:"+chunk_sn);
            System.out.println("cmd是:"+chunk_cmd);
            System.out.println((chunk_crc));
        }else{
            throw new MyException("数据包错误或者丢失");


        }



        return msg;
    }
};

