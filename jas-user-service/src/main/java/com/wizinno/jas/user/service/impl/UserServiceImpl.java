package com.wizinno.jas.user.service.impl;


import com.wizinno.jas.common.data.PageData;
import com.wizinno.jas.user.domain.PatientMapper;
import com.wizinno.jas.user.domain.UserMapper;
import com.wizinno.jas.user.domain.model.User;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.UserDoctorsDto;
import com.wizinno.jas.user.service.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMei on 2017-07-26.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PatientMapper patientMapper;

   @Autowired
   private UserMapper userMapper;


    @Override
    public UserDto getUserByOpenId(String openId) {
       UserDto userDto = null;
       User user=userMapper.selectByOpenId(openId);
       if (user != null){
           userDto=new UserDto();
           BeanUtils.copyProperties(user,userDto);
       }
        return userDto;
    }
    @Override
    public UserDto getUserByUserId(Long userId) {
        UserDto userDto = null;
        User user=userMapper.selectByPrimaryKey(userId);
        if (user != null){
            userDto=new UserDto();
            BeanUtils.copyProperties(user,userDto);
        }
        return userDto;
    }

    @Override
    public int saveUser(UserDto userDto) {

           User user = new User();
           BeanUtils.copyProperties(userDto,user);

        return  userMapper.insert(user);
    }

    @Override
    public int updateUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public UserDto getUserByPhone(String phone) {
        UserDto userDto = new UserDto();
        User user = new User();
        user = userMapper.getUserByPhone(phone);
        if(null != user) {
            BeanUtils.copyProperties(user,userDto);
        }else{
            userDto=null;
        }
        return userDto;
    }

    @Override
    public UserDoctorsDto selectUdoctorById(Long id) {
        return userMapper.selectUdoctorById(id);
    }

    @Override
    public List<UserDto> findAll(PageData pageData) {
        List<User> userDtosList = userMapper.selectAlls(pageData);
        List<UserDto> userDtos = new ArrayList<>();
        if (userDtosList != null && userDtosList.size() > 0) {
            UserDto userDto = null;
            for (User user : userDtosList) {
                userDto = new UserDto();
                if(null !=user) {
                    BeanUtils.copyProperties(user, userDto);
                    userDtos.add(userDto);
                }
            }
        }
        return userDtos;
    }

    @Override
    public List<UserDoctorsDto> getUserExamineList(PageData pageData) {
        return userMapper.getUserExamineList(pageData);
    }

    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<UserDto> getUserListByName(String name) {
        return userMapper.getUserListByName(name);
    }
}
