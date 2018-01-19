package com.wizinno.jas.exercise;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wizinno.jas.dict.service.DataDictService;
import com.wizinno.jas.dict.service.data.ParamKey;
import com.wizinno.jas.dict.service.dto.ArthroDto;
import com.wizinno.jas.dict.service.dto.DataDictDto;
import com.wizinno.jas.exercise.domain.ExerciseRecordMapper;
import com.wizinno.jas.exercise.domain.model.ExerciseRecord;
import com.wizinno.jas.exercise.service.ExerciseRecordService;
import com.wizinno.jas.exercise.service.dto.DataDto;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessDto;
import com.wizinno.jas.exercise.service.dto.ExerciseRecordDto;
import net.sf.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by HP on 2017/8/18.
 */
@Service
public class ExerciseRecordServiceImpl implements ExerciseRecordService {

    @Autowired
    private ExerciseRecordMapper exerciseRecordMapper;
    @Autowired
    private DataDictService dataDictService;

    private Gson gson = new Gson();

    @Override
    public void createExerciseRecord(ExerciseRecordDto dto) {

        ExerciseRecord exerciseRecord = new ExerciseRecord();
        BeanUtils.copyProperties(dto, exerciseRecord);
        exerciseRecordMapper.insert(exerciseRecord);

    }

    @Override
    public List<ExerciseRecordDto> getExerciseRecord(Long id) {
        List<ExerciseRecord> exerciseRecords = exerciseRecordMapper.getExerciseRecord(id);
        List<ExerciseRecordDto> lists = new ArrayList<>();
        ExerciseRecordDto exerciseRecordDto = null;
        for (ExerciseRecord exerciseRecord : exerciseRecords) {
            exerciseRecordDto = new ExerciseRecordDto();
            BeanUtils.copyProperties(exerciseRecord, exerciseRecordDto);
            lists.add(exerciseRecordDto);
        }

        return lists;
    }

    //单次训练记录及训练详细信息
    @Override
    public ExerciseRecordDto getExerciseRecordById(Long id) {
        ExerciseRecord exerciseRecords = exerciseRecordMapper.getExerciseRecordById(id);//显示今天最新的一条训练记录
        List<ExerciseRecordDto> lists = new ArrayList<>();
        ExerciseRecordDto exerciseRecordDto = null;
        exerciseRecordDto = new ExerciseRecordDto();
        BeanUtils.copyProperties(exerciseRecords, exerciseRecordDto);
        List<DataDto> dataDtoList = null;
        List list = new ArrayList();
        dataDtoList = gson.fromJson(exerciseRecordDto.getRecordData(), new TypeToken<List<DataDto>>() {
        }.getType());
        for(int i=0;i<dataDtoList.size();i++){
            Date date = null;
            try {
                date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(i).getTime()));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(date);
                dataDtoList.get(i).setTimeDesc(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        JSONArray array = JSONArray.fromObject(dataDtoList);
        //exerciseRecordDto.setDataDtoList(dataDtoList);//图表数据
        try {
            Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(0).getTime()));
            Date dateo = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(dataDtoList.size() - 1).getTime()));
            Date begin = null;
            Date end = null;
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMdd HHmmss");
            String sDate = dfs.format(date);
            begin = dfs.parse(String.valueOf(dfs.format(date)));
            end = dfs.parse(String.valueOf(dfs.format(dateo)));
            long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
            long minus = between / 60;//今天一次训练记录的训练时长
            exerciseRecordDto.setJsonArray(array);
            exerciseRecordDto.setTrainingLength(new Long(minus).intValue());
            //算出角度变化（伸展还是收缩）
            int first = dataDtoList.get(0).getAngle();
            int last = dataDtoList.get(dataDtoList.size() - 2).getAngle();
            int angle = last - first;
            exerciseRecordDto.setAngleChange(angle);//算出角度变化
            System.out.print("开始角度" + first);
            System.out.print("结束角度" + last);
            if (angle > 0) {
                int oangle;
                oangle = dataDtoList.get(0).getAngle();
                for (int i = 0; i < dataDtoList.size(); i++) {
                    if (dataDtoList.get(i).getAngle() > 0 && dataDtoList.get(i).getEvent() != "pause") {
                        // 判断是否是sr上升 算出所有sr 的最长、最短、平均时间
                        if (oangle < dataDtoList.get(i).getAngle()) {
                            oangle = dataDtoList.get(i).getAngle();
                            Date s = null;
                            Date e = null;
                            if (i == 1) {
                                s = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(0).getTime()));
                                e = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(i).getTime()));
                            } else {
                                s = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(i).getTime()));
                                if (!"end".equals(dataDtoList.get(i + 1).getEvent())) {
                                    e = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(i + 1).getTime()));
                                } else {
                                    e = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(dataDtoList.size() - 1).getTime()));
                                }
                            }
                            SimpleDateFormat dfs1 = new SimpleDateFormat("yyyyMMdd HHmmss");
                            Date begins = dfs1.parse(String.valueOf(dfs1.format(s)));
                            Date ends = dfs1.parse(String.valueOf(dfs1.format(e)));
                            long result = (ends.getTime() - begins.getTime()) / 1000;//除以1000是为了转换成秒
                            long minuso = result / 60;//每一次伸展或收缩所坚持的时长
                            list.add(minuso);
                        }
                    }
                }
                System.out.print("伸展");
            } else {
                int oangle;
                oangle = dataDtoList.get(0).getAngle();
                for (int i = 0; i < dataDtoList.size(); i++) {
                    if (dataDtoList.get(i).getAngle() > 0 && dataDtoList.get(i).getEvent() != "pause") {
                        // 判断是否是sr上升
                        if (oangle > dataDtoList.get(i).getAngle()) {
                            oangle = dataDtoList.get(i).getAngle();
                            Date s = null;
                            Date e = null;
                            if (i == 1) {
                                s = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(0).getTime()));
                                e = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(i).getTime()));
                            } else {
                                s = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(i).getTime()));
                                if (!"end".equals(dataDtoList.get(i + 1).getEvent())) {
                                    e = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(i + 1).getTime()));
                                } else {
                                    e = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(dataDtoList.size() - 1).getTime()));
                                }
                            }
                            SimpleDateFormat dfs1 = new SimpleDateFormat("yyyyMMdd HHmmss");
                            Date begins = dfs1.parse(String.valueOf(dfs1.format(s)));
                            Date ends = dfs1.parse(String.valueOf(dfs1.format(e)));
                            long result = (ends.getTime() - begins.getTime()) / 1000;//除以1000是为了转换成秒
                            long minuso = result / 60;//每一次伸展或收缩所坚持的时长
                            list.add(minuso);
                        }
                    }
                }
                System.out.print("收缩");
            }
            System.out.print("角度变化" + angle);
            int minTime, maxTime, vagTime = 0;
            minTime = maxTime = new Long((Long) list.get(0)).intValue();
            for (int i = 0; i < list.size(); i++) {
                if (new Long((Long) list.get(i)).intValue() > maxTime)   // 判断最大角度
                    maxTime = new Long((Long) list.get(i)).intValue();
                if (new Long((Long) list.get(i)).intValue() < minTime)   // 判断最小角度
                    minTime = new Long((Long) list.get(i)).intValue();
                vagTime += new Long((Long) list.get(i)).intValue();
            }
            exerciseRecordDto.setMinimumTime(minTime);//最短时间
            exerciseRecordDto.setMaximumTime(maxTime);//最长时间
            exerciseRecordDto.setAverageTime(vagTime / exerciseRecordDto.getSr());//平均时间
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return exerciseRecordDto;
    }

    //主页单次训练数据
    @Override
    public List<ExerciseRecordDto> getExerciseRecord(ExerciseRecordDto dto)   {
        try {
            if(dto.getStartAngle()<=0 && dto.getEndAngle()<=0 && null ==dto.getSr())
            Calculation(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExerciseRecord exerciseRecordO = new ExerciseRecord();
        BeanUtils.copyProperties(dto, exerciseRecordO);
        List<ExerciseRecordDto> exerciseRecordDtoList = new ArrayList<>();
        List<DataDto> dataDtoList = null;
        List<ExerciseRecord> exerciseRecordList = exerciseRecordMapper.getExerciseRecords(exerciseRecordO);
        if(null !=exerciseRecordList && exerciseRecordList.size()>0) {
            dataDtoList = gson.fromJson(exerciseRecordList.get(0).getRecordData(), new TypeToken<List<DataDto>>() {
            }.getType());
            for (int i = 0; i < dataDtoList.size(); i++) {
                Date date = null;
                try {
                    date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(i).getTime()));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(date);
                    dataDtoList.get(i).setTimeDesc(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            JSONArray array = JSONArray.fromObject(dataDtoList);
            ExerciseRecordDto exerciseRecordDto = null;
            for (ExerciseRecord exerciseRecord : exerciseRecordList) {
                exerciseRecordDto = new ExerciseRecordDto();
                exerciseRecordDto.setJsonArray(array);
                BeanUtils.copyProperties(exerciseRecord, exerciseRecordDto);
                exerciseRecordDtoList.add(exerciseRecordDto);
            }
        }
        return exerciseRecordDtoList;
    }

    @Override
    public List<ExerciseRecordDto> getExerciseRecordTimes(ExerciseRecordDto dto) {
        ExerciseRecord exerciseRecordO = new ExerciseRecord();
        BeanUtils.copyProperties(dto, exerciseRecordO);
        List<ExerciseRecordDto> exerciseRecordDtoList = new ArrayList<>();
        ExerciseRecordDto exerciseRecordDto = null;
        List<DataDto> dataDtoList = null;
        List<ExerciseRecord> exerciseRecordList = exerciseRecordMapper.getExerciseRecordTimes(exerciseRecordO);
        JSONArray array = null;
        for (ExerciseRecord exerciseRecord : exerciseRecordList) {
            dataDtoList = gson.fromJson(exerciseRecord.getRecordData(), new TypeToken<List<DataDto>>() {}.getType());
            for(int i=0;i<dataDtoList.size();i++){
                Date date = null;
                try {
                    date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(i).getTime()));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(date);
                    dataDtoList.get(i).setTimeDesc(dateString);
                    array = JSONArray.fromObject(dataDtoList);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            exerciseRecordDto = new ExerciseRecordDto();
            exerciseRecordDto.setJsonArray(array);
            BeanUtils.copyProperties(exerciseRecord, exerciseRecordDto);
            exerciseRecordDtoList.add(exerciseRecordDto);
        }

        return exerciseRecordDtoList;
    }

    //周、月柱形图
    @Override
    public JSONArray getExerciseRecordWeekMonth(ExerciseRecordDto dto) {
        ExerciseRecord exerciseRecordO = new ExerciseRecord();
        BeanUtils.copyProperties(dto, exerciseRecordO);
        List<ExerciseRecordDto> exerciseRecordDtoList = new ArrayList<>();
        List<DataDto> dataDtoList = null;
        JSONArray obj7 = new JSONArray();
        List<ExerciseRecord> exerciseRecordList = exerciseRecordMapper.getExerciseRecordWeekMonth(exerciseRecordO);
        ExerciseRecordDto exerciseRecordDto = null;
        for (ExerciseRecord exerciseRecord : exerciseRecordList) {
            exerciseRecordDto = new ExerciseRecordDto();
            BeanUtils.copyProperties(exerciseRecord, exerciseRecordDto);
            dataDtoList = gson.fromJson(exerciseRecord.getRecordData(), new TypeToken<List<DataDto>>() {
            }.getType());
            try {
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(0).getTime()));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(date);
                JSONObject data = new JSONObject();
                data.put("time", dateString);
                data.put("startAngle", exerciseRecord.getStartAngle());
                data.put("endAngle", exerciseRecord.getEndAngle());
                obj7.add(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //exerciseRecordDtoList.add(exerciseRecordDto);
        }
        return obj7;
    }

    @Override
    public List<ExerciseRecordDto> getExerciseRecordDayByTime(ExerciseRecordDto dto) {
        ExerciseRecord exerciseRecordO = new ExerciseRecord();
        BeanUtils.copyProperties(dto, exerciseRecordO);
        List<ExerciseRecordDto> exerciseRecordDtoList = new ArrayList<>();
        List<DataDto> dataDtoList = null;
        List<ExerciseRecord> exerciseRecordList = exerciseRecordMapper.getExerciseRecordDayByTime(exerciseRecordO);
        for (ExerciseRecord exerciseRecord : exerciseRecordList) {
            ExerciseRecordDto exerciseRecordDto = new ExerciseRecordDto();
            dataDtoList = gson.fromJson(exerciseRecord.getRecordData(), new TypeToken<List<DataDto>>() {
            }.getType());
            Date date = null;
            try {
                date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(0).getTime()));
                Date dateo = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(dataDtoList.size() - 1).getTime()));
                Date begin = null;
                Date end = null;
                SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMdd HHmmss");
                String sDate = dfs.format(date);
                begin = dfs.parse(String.valueOf(dfs.format(date)));
                end = dfs.parse(String.valueOf(dfs.format(dateo)));
                long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
                long minus = between / 60;//今天一次训练记录的训练时长
                exerciseRecordDto.setId(exerciseRecord.getId());
                exerciseRecordDto.setTrainingLengthDesc("训练时长"+minus+"m".toString());
                //算出角度变化（伸展还是收缩）
                int first = dataDtoList.get(0).getAngle();
                int last = dataDtoList.get(dataDtoList.size() - 2).getAngle();
                int angle = last - first;
                exerciseRecordDto.setCreateTime(exerciseRecord.getCreateTime());
                exerciseRecordDto.setAngleChange(angle);//算出角度变化
                exerciseRecordDto.setSr(exerciseRecord.getSr());
                exerciseRecordDtoList.add(exerciseRecordDto);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return exerciseRecordDtoList;
    }

    @Override
    public List<ExerciseRecordDto> getExerciseCount(ExerciseRecordDto exerciseRecordDto) {
        return exerciseRecordMapper.getExerciseCount(exerciseRecordDto);
    }

    @Override
    public List<ExerciseRecordDto> findExerciseRecordList(ExerciseRecordDto exerciseRecordDto) {
        List<ExerciseRecordDto>exerciseRecordDtoList = exerciseRecordMapper.findExerciseRecordList(exerciseRecordDto);
        if(exerciseRecordDtoList.size()>0 && exerciseRecordDtoList!=null) {
            List<ArthroDto> dataDictDtoListA = dataDictService.getContentToListByKey(ParamKey.Arthro.toString());//根据code值查出字典中的名称
            List<DataDictDto> dataDictDtoListD = dataDictService.getContentToListByKey(ParamKey.Direction.toString());
            List<DataDictDto> dataDictDtoListP = dataDictService.getContentToListByKey(ParamKey.Postion.toString());
            List<DataDto> dataDtoList = null;
            for(ExerciseRecordDto er:exerciseRecordDtoList) {
                dataDtoList = gson.fromJson(er.getRecordData(), new TypeToken<List<DataDto>>() {
                }.getType());
                JSONArray array = JSONArray.fromObject(dataDtoList);
                Date date = null;
                try {
                    date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(0).getTime()));
                    Date dateo = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(String.valueOf(dataDtoList.get(dataDtoList.size() - 1).getTime()));
                    Date begin = null;
                    Date end = null;
                    SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMdd HHmmss");
                    String sDate = dfs.format(date);
                    begin = dfs.parse(String.valueOf(dfs.format(date)));
                    end = dfs.parse(String.valueOf(dfs.format(dateo)));
                    long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
                    long minus = between / 60;//今天一次训练记录的训练时长
                    er.setTrainingLength(new Long(minus).intValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (null != er.getPostion()) {
                    for (DataDictDto dataDictDtoP : dataDictDtoListP) {
                        if (dataDictDtoP.getCode().equals(er.getPostion())) {
                            er.setPostionDesc(dataDictDtoP.getName());
                        }
                    }
                }
                if (null != er.getArthro()) {
                    for (ArthroDto dataDictDtoA : dataDictDtoListA) {
                        if (dataDictDtoA.getCode().equals(er.getArthro())) {
                            er.setArthroDesc(dataDictDtoA.getName());
                        }
                    }
                }
                if (null != er.getDirection()) {
                    for (DataDictDto dataDictDtoD : dataDictDtoListD) {
                        if (dataDictDtoD.getCode().equals(er.getDirection())) {
                            er.setDirectionDesc(dataDictDtoD.getName());
                        }
                    }
                }
            }
        }
        return exerciseRecordDtoList;
    }

    //计算sr和方向
    public void Calculation(ExerciseRecordDto dto) throws Exception {
        ExerciseRecord exerciseRecordO = new ExerciseRecord();
        BeanUtils.copyProperties(dto, exerciseRecordO);
        List<ExerciseRecordDto> exerciseRecordDtoList = new ArrayList<>();
        List<DataDto> dataDtoList = null;
        List<ExerciseRecord> exerciseRecordList = exerciseRecordMapper.Calculation(exerciseRecordO);
        ExerciseRecordDto exerciseRecordDto = null;
        for (ExerciseRecord exerciseRecord : exerciseRecordList) {
            exerciseRecordDto = new ExerciseRecordDto();
            BeanUtils.copyProperties(exerciseRecord, exerciseRecordDto);
            dataDtoList = gson.fromJson(exerciseRecord.getRecordData(), new TypeToken<List<DataDto>>() {
            }.getType());
            //算出角度变化（伸展还是收缩）
            int first = dataDtoList.get(0).getAngle();
            int last = dataDtoList.get(dataDtoList.size() - 2).getAngle();
            int angle = last - first;
            //修改开始角度和结束角度
            if (first != exerciseRecord.getStartAngle() && last != exerciseRecord.getEndAngle()) {
                exerciseRecord.setStartAngle(first);
                exerciseRecord.setEndAngle(last);
                if (angle > 0) {
                    exerciseRecord.setDirection(1);//如果是伸展方向值就是1
                    int sr = 0;
                    int oangle;
                    oangle = dataDtoList.get(0).getAngle();
                    for (int i = 0; i < dataDtoList.size(); i++) {
                        if (dataDtoList.get(i).getAngle() > 0 && dataDtoList.get(i).getEvent() != "pause") {
                            // 判断是否是sr上升 算出所有sr 的最长、最短、平均时间
                            if (oangle < dataDtoList.get(i).getAngle()) {
                                sr++;
                                oangle = dataDtoList.get(i).getAngle();
                            }
                        }
                    }
                    exerciseRecord.setSr(sr);
                } else {
                    exerciseRecord.setDirection(2);//如果是收缩方向值就是2
                    int sr=0;
                    int oangle;
                    oangle = dataDtoList.get(0).getAngle();
                    for (int i = 0; i < dataDtoList.size(); i++) {
                        if (dataDtoList.get(i).getAngle() > 0 && dataDtoList.get(i).getEvent() != "pause") {
                            // 判断是否是sr上升
                            if (oangle > dataDtoList.get(i).getAngle()) {
                                oangle = dataDtoList.get(i).getAngle();
                                sr++;
                            }
                        }
                    }
                    exerciseRecord.setSr(sr);
                }
                exerciseRecordMapper.updateByPrimaryKey(exerciseRecord);
            }
        }
    }
}

