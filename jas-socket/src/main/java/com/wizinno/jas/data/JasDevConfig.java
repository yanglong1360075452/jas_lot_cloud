package com.wizinno.jas.data;

import com.wizinno.jas.unity.GetInt;
import com.wizinno.jas.unity.GetShort;

/**
 * Created by Administrator on 2017/1/13.
 */
public class JasDevConfig {
    public final short  CHUNK_ID  = 1002;
    public final short CHUNK_VER = 0000;
    public final short deviceCode_len = 2;
    public final short devicelevel_len =1;
    public final short train_time_len = 2;
    public final short upload_time_len = 2;
    public final short CHUNK_SIZE = deviceCode_len+devicelevel_len+train_time_len+upload_time_len;
    private String device_code;
    private String device_level;
    private String train_time;
    private String upload_time;

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getDevice_level() {
        return device_level;
    }

    public void setDevice_level(String device_level) {
        this.device_level = device_level;
    }

    public String getTrain_time() {
        return train_time;
    }

    public void setTrain_time(String train_time) {
        this.train_time = train_time;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }
    public String deserialize(byte[]bytes)throws MyException{
        //1.判断 size ,id ,ver ,是否符合规定(bytes为devID的数据包）
        byte[]configsize=new byte[4];
        byte[]configid=new byte[2];
        byte[]configver=new byte[2];
        byte[]configdevice_code=new byte[2];
        byte[]configdevice_level=new byte[1];
        byte[]configtrain_time=new byte[2];
        byte[]configupload_time=new byte[2];
        byte[]configcrc=new byte[4];
        System.arraycopy(bytes,0,configsize,0,4);
        System.arraycopy(bytes,4,configid,0,2);
        System.arraycopy(bytes,6,configver,0,2);
        System.arraycopy(bytes,8,configdevice_code,0,2);
        System.arraycopy(bytes,10,configdevice_level,0,1);
        System.arraycopy(bytes,11,configtrain_time,0,2);
        System.arraycopy(bytes,13,configupload_time,0,2);
        System.arraycopy(bytes,15,configcrc,0,4);
        short chunk_size= GetShort.getShort(configsize,0);
        System.out.print("chunk_size:"+chunk_size);
        short chunk_id= GetShort.getShort(configid,0);
        short chunk_ver=GetShort.getShort(configver,0);
        String chunk_deviceCode=new String(configdevice_code).trim();
        String chunk_devicelevel=new String(configdevice_level).trim();
        String chunk_traintime=new String(configtrain_time).trim();
        String chunk_uploadtime=new String(configupload_time).trim();
        int chunk_crc= GetInt.byteArrayToInt(configcrc);
        String msg=null;
        if(chunk_size==7&&chunk_crc==Utility.get_CRC(bytes)){
            msg="{\"chunk_deviceCode\":\"" + chunk_deviceCode + "\",\"chunk_devicelevel\":\"" + chunk_devicelevel + "\",\"chunk_traintime\":\"" + chunk_traintime + "\",\"sendTime\":\"" + chunk_uploadtime + "\"}";
        }else{
            throw new MyException("数据包错误或者丢失");
        }
        return msg;
    }
}
