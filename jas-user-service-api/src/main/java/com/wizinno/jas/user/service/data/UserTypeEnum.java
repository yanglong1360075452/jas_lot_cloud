package com.wizinno.jas.user.service.data;

/**
 * Created by HP on 2017/8/11.
 */
public enum UserTypeEnum {

    patient(0,"病人"),
    Doctor(1,"康复师"),
    ;
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    UserTypeEnum(Integer _nCode, String _name) {
        this.nCode = _nCode;
        this.name = _name;
    }

    public Integer toCode(){
        return this.nCode;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static UserTypeEnum valueOf(Integer code) {
        for (UserTypeEnum archiveStatus : UserTypeEnum.values()){
            if (archiveStatus.toCode().equals(code)){
                return archiveStatus;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (UserTypeEnum archiveStatus : UserTypeEnum.values()){
            if (archiveStatus.toCode().equals(code)){
                return archiveStatus.toString();
            }
        }
        return null;
    }
}
