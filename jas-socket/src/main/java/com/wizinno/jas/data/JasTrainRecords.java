package com.wizinno.jas.data;

import com.wizinno.jas.unity.GetInt;
import com.wizinno.jas.unity.GetShort;

/**
 * Created by Administrator on 2017/1/13.
 */
public class JasTrainRecords {
    int n;
    public final short  CHUNK_ID  = 1003;
    public final short CHUNK_VER = 0000;
    private String timestamp;
    String[] jasTrainData;

    public String[] getJasTrainData() {
        return jasTrainData;
    }

    public void setJasTrainData(String[] jasTrainData) {
        this.jasTrainData = jasTrainData;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String  deserialize(byte[]bytes)throws MyException{
        byte[]size=new byte[4];
        System.arraycopy(bytes,0,size,0,4);
        int chunk_size= GetInt.byte2int(size);
        n=(chunk_size-4)/4;
        byte[]id=new byte[2];
        byte[]ver=new byte[2];
        byte[]timestamp=new byte[4];
        byte[]JasTrainData=new byte[4*n];
        byte[]crc=new byte[4];
        System.arraycopy(bytes,4,id,0,2);
        System.arraycopy(bytes,6,ver,0,2);
        System.arraycopy(bytes,8,timestamp,0,4);
        System.arraycopy(bytes,12,JasTrainData,0,4*n);
        System.arraycopy(bytes,12+4*n,crc,0,4);
        short chunk_id=GetShort.getShort(id,0);
        short chunk_ver= GetShort.getShort(ver,0);
        short chunk_timestamp=GetShort.getShort(timestamp,0);
        short []chunk_trainData=new short[n];
        byte[]str=new byte[4];
        int total=0;
        byte[]timestamp2=new byte[2];
        byte[]value=new  byte[2];
       for(int i=1;i<n+1;i++){
           System.arraycopy(JasTrainData,total,str,0,4);
           System.arraycopy(str,0,timestamp2,0,2);
           System.arraycopy(value,2,timestamp2,0,2);
           chunk_trainData[3*(i-1)]=GetShort.getShort(timestamp2,0);
           int values=GetShort.getShort(value,0);
           int dangwei=values>>12&0xffff;
           int dangweiValues=values<<4>>4&0xffff;
           chunk_trainData[3*(i-1)+1]=(short)dangwei;
           chunk_trainData[3*(i-1)+2]=(short)dangweiValues;
           total=total+4;
       }
        int chunk_crc= GetInt.byteArrayToInt(crc);
        String msg=null;
        if(chunk_crc==Utility.get_CRC(bytes)){
            msg="{\"timestamp\":\"" + chunk_timestamp + "\",\"trainData\":\"" + chunk_trainData + "\"}";
        }else{
            throw new MyException("数据包错误或者丢失");
        }
        return msg;
    }
}
