package com.wizinno.jas.data;

import com.wizinno.jas.unity.GetInt;
import com.wizinno.jas.unity.GetShort;

/**
 * Created by Administrator on 2017/1/13.
 */
public class JasDevCalib {
    public final short  CHUNK_ID  = 1004;
    public final short CHUNK_VER = 0000;
    public final short calibdata_LEN = 16;
    public final short CHUNK_SIZE = calibdata_LEN;
    private String calib_data;

    public String getCalib_data() {
        return calib_data;
    }

    public void setCalib_data(String calib_data) {
        this.calib_data = calib_data;
    }
    public String deserialize(byte[]bytes)throws MyException{
        byte[]size=new byte[4];
        byte[]id=new byte[2];
        byte[]ver=new byte[2];
        byte[]calibdata=new byte[16];
        byte[]crc=new byte[4];
        System.arraycopy(bytes,0,size,0,4);
        System.arraycopy(bytes,4,id,0,2);
        System.arraycopy(bytes,6,ver,0,2);
        System.arraycopy(bytes,8,calibdata,0,16);
        System.arraycopy(bytes,24,crc,0,4);
        short chunk_size= GetShort.getShort(size,0);
        short chunk_id=GetShort.getShort(id,0);
        short chunk_ver=GetShort.getShort(ver,0);
        String chunk_data=new String(calibdata).trim();
        int chunk_crc= GetInt.byteArrayToInt(crc);
        String msg=null;
        if(chunk_size==16&&chunk_crc==Utility.get_CRC(bytes)){
            msg="{\"chunk_data\":\"" + chunk_data + "\"}";
        }else{
            throw new MyException("数据包错误或者丢失");
        }

        return msg;


    }
}
