package com.wizinno.jas.data;

import com.wizinno.jas.unity.GetInt;
import com.wizinno.jas.unity.GetShort;

/**
 * User:
 * Date:
 * Time:
 */
public class JasDevStatus {
    public final short  CHUNK_ID  = 1001;
    public final short CHUNK_VER = 0000;
    public final short gps_address_len=25;
    public final short signal_quality_len=9;
    public final short retry_count_len=1;
    public final short batt_level_len=1;
    public final short CHUNK_SIZE=gps_address_len+signal_quality_len+retry_count_len+batt_level_len;
    private String gps_address;
    private String signal_quality;
    private String retry_count;
    private String batt_level;

    public String getGps_address() {
        return gps_address;
    }

    public void setGps_address(String gps_address) {
        this.gps_address = gps_address;
    }

    public String getSignal_quality() {
        return signal_quality;
    }

    public void setSignal_quality(String signal_quality) {
        this.signal_quality = signal_quality;
    }

    public String getRetry_count() {
        return retry_count;
    }

    public void setRetry_count(String retry_count) {
        this.retry_count = retry_count;
    }

    public String getBatt_level() {
        return batt_level;
    }

    public void setBatt_level(String batt_level) {
        this.batt_level = batt_level;
    }
    public String deserialize(byte[]bytes)throws MyException{
        byte[]bytesize=new byte[4];
        byte[]byteid=new byte[2];
        byte[]bytever=new byte[2];
        byte[]bytegps_address=new byte[25];
        byte[]bytesignal_quality=new byte[9];
        byte[]byteretry_count=new byte[1];
        byte[]bytebatt_level=new byte[1];
        byte[]bytecrc=new byte[4];
        System.arraycopy(bytes,0,bytesize,0,4);
        System.arraycopy(bytes,4,byteid,0,2);
        System.arraycopy(bytes,6,bytever,0,2);
        System.arraycopy(bytes,8,bytegps_address,0,25);
        System.arraycopy(bytes,33,bytesignal_quality,0,9);
        System.arraycopy(bytes,42,byteretry_count,0,1);
        System.arraycopy(bytes,43,bytebatt_level,0,1);
        System.arraycopy(bytes,44,bytecrc,0,4);
        short chunk_size= GetShort.getShort(bytesize,0);
        short chunk_id=GetShort.getShort(byteid,0);
        short chunk_ver=GetShort.getShort(bytever,0);
        String chunk_gps_address=new String(bytegps_address).trim();
        String chunk_signal_quality=new String(bytesignal_quality).trim();
        String chunk_retry_count=new String(byteretry_count).trim();
        String chunk_batt_level=new String(bytebatt_level).trim();
        int chunk_crc= GetInt.byteArrayToInt(bytecrc);
        String msg=null;
        if(chunk_size==36&&chunk_crc==Utility.get_CRC(bytes)){
            msg="{\"gps_address\":\"" + chunk_gps_address + "\",\"strength\":\"" + chunk_signal_quality + "\",\"retry_count\":\"" + chunk_retry_count + "\",\"power\":\"" + chunk_batt_level + "\"}";
        }else{
            throw new MyException("数据包错误或者丢失");
        }
        return msg;
    }

}
