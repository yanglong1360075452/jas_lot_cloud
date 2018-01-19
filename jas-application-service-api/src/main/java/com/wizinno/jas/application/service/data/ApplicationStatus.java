package com.wizinno.jas.application.service.data;

/**
 * Created by HP on 2017/8/22.
 */
public enum ApplicationStatus {

    NoVerify(0,"未验证"),
    Verify(1,"已验证"),
    reject(-1,"已拒绝"),
    ;
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    ApplicationStatus(Integer _nCode, String _name) {
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

    public static ApplicationStatus valueOf(Integer code) {
        for (ApplicationStatus archiveStatus : ApplicationStatus.values()){
            if (archiveStatus.toCode().equals(code)){
                return archiveStatus;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (ApplicationStatus archiveStatus : ApplicationStatus.values()){
            if (archiveStatus.toCode().equals(code)){
                return archiveStatus.toString();
            }
        }
        return null;
    }
}


