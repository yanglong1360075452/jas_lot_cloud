package com.wizinno.jas.user.service;


import com.wizinno.jas.common.data.PageData;
import com.wizinno.jas.user.service.dto.UserDoctorsDto;
import com.wizinno.jas.user.service.dto.UserDto;

import java.util.List;


/**
 * Created by LiuMei on 2017-07-26.
 */
public interface UserService {

    UserDto getUserByOpenId(String openId);
    UserDto getUserByUserId(Long userId);
    int saveUser(UserDto userDto);
    int updateUser(UserDto userDto);
    UserDto getUserByPhone(String phone);
    UserDoctorsDto selectUdoctorById(Long id);
    List<UserDto> findAll(PageData filter);
    List<UserDoctorsDto> getUserExamineList(PageData pageData);
    //删除用户
    void deleteUserById(Long id);
    //后台管理调用接口
    List<UserDto>getUserListByName(String name);

}
