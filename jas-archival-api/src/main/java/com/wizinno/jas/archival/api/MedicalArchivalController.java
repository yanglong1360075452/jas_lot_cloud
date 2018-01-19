package com.wizinno.jas.archival.api;

import com.wizinno.jas.archival.api.vo.ClinicalDataVO;
import com.wizinno.jas.archival.api.vo.MedicalArchivalVO;
import com.wizinno.jas.archival.service.ClinicalDataService;
import com.wizinno.jas.archival.service.MedicalArchivalService;
import com.wizinno.jas.archival.service.dto.ClinicalDataDto;
import com.wizinno.jas.archival.service.dto.MedicalArchivalDto;
import com.wizinno.jas.common.controller.BaseController;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.common.util.FileUtil;
import com.wizinno.jas.common.util.WechatGetImgUtil;
import com.wizinno.jas.common.wechat.AccessToken;
import com.wizinno.jas.common.wechat.WeChatUtil;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.PatientDto;
import com.wizinno.jas.user.service.dto.UserDto;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created by HP on 2017/8/15.
 */
@RestController
@RequestMapping("/api/wechat/ma")
public class MedicalArchivalController extends BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MedicalArchivalService medicalArchivalService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClinicalDataService clinicalDataService;


  /*  String path = "D:\\javasoftware\\downloads\\apache-tomcat-9.0.0.M10\\img\\";*/// 文件路径
    String path = "D:";
 /*  String path2 = "localhost:8080\\img\\";*/

    //创建病历档案
    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO createMArchival(@RequestParam("patientId")Long patientId,@RequestBody MedicalArchivalVO medicalArchivalVO){
        Date date = new Date();
        PatientDto patientDto = new PatientDto();
        if (patientId == null){
            String openId = (String) request.getAttribute("openId");
            UserDto user = userService.getUserByOpenId(openId);
            patientDto = patientService.getPatientByUserID(user.getId());
            if (patientDto == null){
                throw new CustomException(CustomExceptionCode.ErrorParam);
            }else {
                medicalArchivalVO.setPatientId(patientDto.getId());
            }
        }else {
            medicalArchivalVO.setPatientId(patientId);
        }
        Long pId = medicalArchivalVO.getPatientId();
        Integer illnessState = medicalArchivalVO.getIllnessState();
        Integer activity = medicalArchivalVO.getActivity();
        Integer process = medicalArchivalVO.getProcess();
        medicalArchivalVO.setCreateTime(date);
        medicalArchivalVO.setUpdateTime(date);

        if (pId == null && illnessState == null && activity == null && process == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        MedicalArchivalDto medicalArchivalDto = new MedicalArchivalDto();

        BeanUtils.copyProperties(medicalArchivalVO,medicalArchivalDto);
        MedicalArchivalDto mArchival = new MedicalArchivalDto();
        mArchival = medicalArchivalService.createMArchival(medicalArchivalDto);
        if(null !=medicalArchivalVO.getClinicalDataDtoList() && medicalArchivalVO.getClinicalDataDtoList().size()>0){//判断是否上传图片
            AccessToken accessToken = WeChatUtil.getAccessToken();
            for(ClinicalDataDto clinicalDataDto:medicalArchivalVO.getClinicalDataDtoList()) {//遍历上传的图片信息
                try {
                    //根据返回图片id获取微信图片并保存本地
                    if(!"".equals(clinicalDataDto.getSrc()) && null !=clinicalDataDto.getSrc()) {
                        String imgUrl = WechatGetImgUtil.savePatientImageToDisk(accessToken.getToken(), clinicalDataDto.getSrc(), clinicalDataDto.getSrc(), path + "//clinicalImg//");
                        ClinicalDataDto cd = new ClinicalDataDto();
                        cd.setMaId(mArchival.getId());
                        cd.setTime(clinicalDataDto.getTime());
                        cd.setIndexType(clinicalDataDto.getIndexType());
                        cd.setContent(clinicalDataDto.getContent());
                        cd.setType(clinicalDataDto.getType());
                        cd.setSrc(imgUrl);
                        cd.setCreateTime(new Date());
                        clinicalDataService.createClinical(cd);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseVO(mArchival);

    }

    //获取病人的所有病历档案
    @RequestMapping(method = RequestMethod.GET)
    public ResponseVO getMArchivalByPatientId(@RequestParam("patientId") Long patientId){
        List<MedicalArchivalDto> dtos=null;
        ResponseEntity responseEntity = null;
        if (patientId != null){
            dtos=medicalArchivalService.getMArchivalByPatientId(patientId);
        }else {
            String openId = (String) request.getAttribute("openId");
            UserDto user = userService.getUserByOpenId(openId);
            PatientDto patientDto = patientService.getPatientByUserID(user.getId());
             dtos=medicalArchivalService.getMArchivalByPatientId(patientDto.getId());
        }
        if(dtos.size()>0){
            for (MedicalArchivalDto medicalArchivalDto:dtos){
                List<ClinicalDataDto> clinicalDataDtoList=clinicalDataService.getImgByMaId(medicalArchivalDto.getId());
                if(clinicalDataDtoList.size()>0){
                    for(ClinicalDataDto clinicalDataDto:clinicalDataDtoList){
                        if(!"".equals(clinicalDataDto.getSrc()) && null !=clinicalDataDto.getSrc()) {
                            File file = new File(path + clinicalDataDto.getSrc());
                            byte[] bytes = new byte[0];
                            try {
                                bytes = FileUtils.readFileToByteArray(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            HttpHeaders httpHeaders = new HttpHeaders();
                            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                            responseEntity = new ResponseEntity(bytes, httpHeaders, HttpStatus.CREATED);
                            clinicalDataDto.setResponseEntity(responseEntity);
                        }
                    }
                    medicalArchivalDto.setClinicalDataDtoList(clinicalDataDtoList);
                }
            }
        }
        return new ResponseVO(dtos);
    }




    //编辑病历档案
    @RequestMapping(value="/{maId}",method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO updateMArchival(@PathVariable("maId")Long maId,@RequestBody MedicalArchivalVO medicalArchivalVO){
        Date date = new Date();
        Long patientId = medicalArchivalVO.getPatientId();
        Integer illnessState = medicalArchivalVO.getIllnessState();
        Integer activity = medicalArchivalVO.getActivity();
        Integer process = medicalArchivalVO.getProcess();
        Date startTime = medicalArchivalVO.getStartTime();
        Integer hospital = medicalArchivalVO.getHospital();
        Integer department = medicalArchivalVO.getDepartment();
        String doctorName = medicalArchivalVO.getDoctorName();

        if (patientId == null && illnessState == null && activity == null && process == null && startTime == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        MedicalArchivalDto dto=medicalArchivalService.getMArchivalByMAId(maId);
        dto.setActivity(activity);
        if (medicalArchivalVO.getContent() != null){
            dto.setContent(medicalArchivalVO.getContent());
        }
        dto.setIllnessState(illnessState);
        dto.setActivity(activity);
        dto.setProcess(process);
        dto.setStartTime(startTime);
        if (hospital != null){
            dto.setHospital(hospital);
        }
        if (doctorName != null){
            dto.setDoctorName(doctorName);
        }
        if (department != null){
            dto.setDepartment(department);
        }
        dto.setUpdateTime(date);
        if(medicalArchivalVO.getAedClinicalDataDtoList()!= null && medicalArchivalVO.getAedClinicalDataDtoList().length>0){//判断是否上传图片
            AccessToken accessToken = WeChatUtil.getAccessToken();
            ClinicalDataDto[] addClinicalDataDtosList = null;
            ClinicalDataDto[] editClinicalDataDtosList = null;
            ClinicalDataDto[] delClinicalDataDtosList = null;
            addClinicalDataDtosList = medicalArchivalVO.getAedClinicalDataDtoList()[0];//添加图片
            editClinicalDataDtosList = medicalArchivalVO.getAedClinicalDataDtoList()[1];//修改图片
            delClinicalDataDtosList = medicalArchivalVO.getAedClinicalDataDtoList()[2];//删除图片
            if(addClinicalDataDtosList.length>0) {//添加图片
                for (ClinicalDataDto clinicalDataDto : addClinicalDataDtosList) {//遍历上传的图片信息
                    try {
                        //根据返回图片id获取微信图片并保存本地
                        if (!"".equals(clinicalDataDto.getSrc()) && null != clinicalDataDto.getSrc()) {
                            String imgUrl = WechatGetImgUtil.savePatientImageToDisk(accessToken.getToken(), clinicalDataDto.getSrc(), clinicalDataDto.getSrc(), path + "//clinicalImg//");
                            ClinicalDataDto cd = new ClinicalDataDto();
                            cd.setMaId(dto.getId());
                            cd.setTime(clinicalDataDto.getTime());
                            cd.setIndexType(clinicalDataDto.getIndexType());
                            cd.setContent(clinicalDataDto.getContent());
                            cd.setType(clinicalDataDto.getType());
                            cd.setSrc(imgUrl);
                            cd.setCreateTime(new Date());
                            clinicalDataService.createClinical(cd);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(editClinicalDataDtosList.length>0) {//修改图片
                for (ClinicalDataDto clinicalDataDto : editClinicalDataDtosList) {//遍历图片信息
                    ClinicalDataDto cd= clinicalDataService.getImgById(clinicalDataDto.getId());
                    if (cd == null){
                        throw new CustomException(CustomExceptionCode.ErrorParam);
                    }
                    String content = clinicalDataDto.getContent();
                    Date time = clinicalDataDto.getTime();
                    String type = clinicalDataDto.getType();
                    int indexType = clinicalDataDto.getIndexType();
                    if (content != null){
                        cd.setContent(content);
                    }
                    if(time != null){
                        cd.setTime(time);
                    }
                    if (type != null){
                        cd.setType(type);
                    }
                    cd.setIndexType(indexType);
                    cd.setUpdateTime(date);
                    clinicalDataService.updateImg(cd);
                }
            }
            if(delClinicalDataDtosList.length>0) {//删除图片
                for (ClinicalDataDto clinicalDataDto : delClinicalDataDtosList) {//遍历图片信息
                    ClinicalDataDto cdd = clinicalDataService.getImgById(clinicalDataDto.getId());
                    if (cdd == null) {
                        throw new CustomException(CustomExceptionCode.ErrorParam);
                    }
                    if (!"".equals(cdd.getSrc())) {
                        new File("D:" + cdd.getSrc()).delete();
                    }
                    clinicalDataService.deleteImg(cdd.getId());
                }
            }
        }
        medicalArchivalService.updateMArchival(dto);
        return new ResponseVO();
    }

    //删除病历档案
    @RequestMapping(value = "/{maId}",method = RequestMethod.DELETE)
    public ResponseVO deleteMArchival(@PathVariable("maId") Long maId){
        if (maId < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        medicalArchivalService.deleteMArchival(maId);
        List<ClinicalDataDto> imgByMaId = clinicalDataService.getImgByMaId(maId);
        List<ClinicalDataDto> imgByImgName = getImgByImgName(imgByMaId);
        if (imgByImgName.size() > 0){
            for (ClinicalDataDto dto:imgByImgName){
                if (dto != null){
                    if(!"".equals(dto.getSrc())) {
                        new File("D:"+dto.getSrc()).delete();
                    }
                    clinicalDataService.deleteImg(dto.getId());
                }
            }
        }
        return new ResponseVO();

    }

    //通过病历档案id查询病例档案详细信息
/*    @RequestMapping(value = "/{maId}",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO getMArchivalDetailsByMAId(@PathVariable("maId") Long maId){
        MedicalArchivalDto dto= medicalArchivalService.getMArchivalDetailsByMAId(maId);
        List<ClinicalDataDto> imgByMaId = clinicalDataService.getImgByMaId(maId);
        //图片名字获取图片
        List<ClinicalDataDto> imgByImgName = getImgByImgName(imgByMaId);
        Map<String, Object> map = new HashedMap();
        map.put("MedicalArchival",dto);
        map.put("img",imgByImgName);

        return new ResponseVO(map);
    }*/
    //删除单张图片
    @RequestMapping(value = "/img/{id}",method = RequestMethod.DELETE)
    public ResponseVO deleteImg(@PathVariable("id") Long id){

        ClinicalDataDto dto = clinicalDataService.getImgById(id);
        if (dto == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        if(!"".equals(dto.getSrc())) {
            new File("D:"+dto.getSrc()).delete();
        }
        clinicalDataService.deleteImg(id);

        return new ResponseVO();
    }

//    //删除多张图片
//    @RequestMapping(value = "/imgs/{id}",method = RequestMethod.DELETE)
//    public ResponseVO deleteImgs(@PathVariable("id") Long id){
//        ClinicalDataDto dto = clinicalDataService.getImgByMaId()
//        if (dto == null){
//            throw new CustomException(CustomExceptionCode.ErrorParam);
//        }
//        if(!"".equals(dto.getPicture())) {
//            new File("D:"+dto.getPicture()).delete();
//        }
//        clinicalDataService.deleteImg(id);
//
//        return new ResponseVO();
//    }

    //更新单张图片
    @RequestMapping(value = "/img/{id}",method = RequestMethod.PUT,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseVO updateImg(@PathVariable("id") Long id, @RequestBody ClinicalDataVO clinicalDataVO){
        ClinicalDataDto dto= clinicalDataService.getImgById(id);
        if (dto == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        Date date = new Date();
        String content = clinicalDataVO.getContent();
        Date time = clinicalDataVO.getTime();
        String type = clinicalDataVO.getType();
        int indexType = clinicalDataVO.getIndexType();
        if (content != null){
            dto.setContent(content);
        }
        if(time != null){
            dto.setTime(time);
        }
        if (type != null){
            dto.setType(type);
        }
//        if (indexType != null){
//            dto.setType(type);
//        }
        //dto.setIndexType();
        dto.setUpdateTime(date);
        clinicalDataService.updateImg(dto);
        return new ResponseVO();
    }

    public  List<ClinicalDataDto> getImgByImgName(List<ClinicalDataDto> dtos){
        List<ClinicalDataDto> lists = new ArrayList<>();
        for (ClinicalDataDto dto:dtos){
            String picture = dto.getSrc();
            File file = new File(path + dto.getSrc());
            dto.setFile(file);
            lists.add(dto);
        }
        return lists;

    }
}
