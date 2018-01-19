package com.wizinno.jas.unity;

import com.wizinno.jas.data.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * User:
 * Date:
 * Time:
 */
public class Switch{
    public JasDevID jasDevID=new JasDevID();
    public JasDevConfig jasDevConfig=new JasDevConfig();
    public JasTrainRecords jasTrainRecords=new JasTrainRecords();
    public JasDevCalib jasDevCalib=new JasDevCalib();
    public JasDevStatus jasDevStatus=new JasDevStatus();



    //lens偏移量
    public  int doBag(byte[]bytes,short id,int lens) throws MyException {
        switch (id)
        {
            case 1001:
                byte[]jasdevid=new byte[55];
                System.arraycopy(bytes,lens,jasdevid,0,55);
                String msg1=jasDevID.deserialize(jasdevid);
                System.out.println("msg1 :"+msg1);
                JSONObject jsonObject1=JSONObject.fromObject(msg1);
                jasDevID.setGuid(jsonObject1.getString("juid"));
                jasDevID.setPn(jsonObject1.getString("pn"));
                jasDevID.setSn(jsonObject1.getString("sn"));
                //jasDevID.setCmd(jsonObject1.getString("cmd"));
                lens= 55;
                break;
            case 1002:
                byte[]jasDevconfig=new byte[19];
                System.arraycopy(bytes,lens,jasDevconfig,0,19);
                String mss=jasDevConfig.deserialize(jasDevconfig);
                JSONObject jsonObject2=JSONObject.fromObject(mss);
                jasDevConfig.setUpload_time(jsonObject2.getString("sendTime"));
                jasDevConfig.setTrain_time(jsonObject2.getString("chunk_traintime"));
                jasDevConfig.setDevice_level(jsonObject2.getString("chunk_devicelevel"));
                jasDevConfig.setDevice_code(jsonObject2.getString("chunk_deviceCode"));

                lens= 19;
                break;
            case 1003:
                byte[]size=new byte[4];
                System.arraycopy(bytes,lens,size,0,4);
                short chunk_size=GetShort.getShort(size,0);
                int i=(chunk_size-4)/4;
                int sizes=4*i+16;
                byte[]jasTrainrecords=new byte[sizes];
                System.arraycopy(bytes,lens,jasTrainrecords,0,i);
                String ms= jasTrainRecords.deserialize(jasTrainrecords);
                JSONObject jsonObject3=JSONObject.fromObject(ms);
                jasTrainRecords.setTimestamp(jsonObject3.getString("timestamp"));
                JSONArray trainDatas=jsonObject3.getJSONArray("trainData");
                String[]strings=new String[trainDatas.size()];
                for(int j=0;i<trainDatas.size();j++){
                    strings=new String[trainDatas.size()];
                   strings[j]= (String) trainDatas.get(j);
                }
                jasTrainRecords.setJasTrainData(strings);
                break;
            case 1004:
                byte[]jasDevcalib=new byte[28];
                System.arraycopy(bytes,lens,jasDevcalib,0,28);
               String m= jasDevCalib.deserialize(jasDevcalib);
                JSONObject jsonObject4=JSONObject.fromObject(m);
                jasDevCalib.setCalib_data(jsonObject4.getString("chunk_data"));
                break;
            case 1005:
                byte[]jasDevstatus=new byte[48];
                System.arraycopy(bytes,lens,jasDevstatus,0,48);
                String mm= jasDevStatus.deserialize(jasDevstatus);
                JSONObject jsonObject5=JSONObject.fromObject(mm);
                jasDevStatus.setGps_address(jsonObject5.getString("gps_address"));
                jasDevStatus.setBatt_level(jsonObject5.getString("power"));
                jasDevStatus.setSignal_quality(jsonObject5.getString("strength"));
                jasDevStatus.setRetry_count(jsonObject5.getString("retry_count"));
                lens=28;
                break;
            default:
               throw new MyException("数据包发生错误");
        }
        return  lens;
    }
}
