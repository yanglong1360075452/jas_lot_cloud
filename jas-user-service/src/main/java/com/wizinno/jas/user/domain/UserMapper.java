package com.wizinno.jas.user.domain;

import com.wizinno.jas.common.data.PageData;
import com.wizinno.jas.user.domain.model.User;
import com.wizinno.jas.user.service.dto.UserDoctorsDto;
import com.wizinno.jas.user.service.dto.UserDto;

import java.util.List;

public interface UserMapper{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    int insert(User record);



    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    User selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    List<User> selectAll(PageData filter);
    List<UserDoctorsDto> getUserExamineList(PageData filter);
    List<User> selectAlls(PageData filter);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    int updateByPrimaryKey(User record);

    User selectByOpenId(String openId);
    UserDoctorsDto selectUdoctorById(Long id);
    User getUserByPhone(String phone);
    List<UserDto>getUserListByName(String name);
    Long last();
}