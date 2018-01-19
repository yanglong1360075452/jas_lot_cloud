package com.wizinno.jas.dict.service.data;

/**
 * Created by HP on 2017/8/14.
 */
public enum ParamKey {

    /**
     * param 代表查询参数
     * key 对应参数设置表key字段
     */
    IllnessState("illnessState","illness_state"),
    NactivityFactor("nactivityFactor","nactivity_factor"),
    BiseaseCourse("diseaseCourse","disease_course"),
    PictureLabel("pictureLabel","picture_label"),
    Arthro("arthro","arthro"),
    Postion("postion","postion"),
    Direction("direction","direction"),
    Hospital("hospital","hospital"),
    Departments("departments","departments"),
    ScoreChange("scoreChange","score_change"),
    Appraisal("appraisal","appraisal"),
    Professiona("professiona","professiona"),
    ;
    // 定义私有变量
    private String param;

    private String key;

    // 构造函数，枚举类型只能为私有
    ParamKey(String param, String key) {
        this.param = param;
        this.key = key;
    }

    public String toParam(){
        return this.param;
    }

    @Override
    public String toString() {
        return this.key;
    }

    public static ParamKey getKeyByParam(String param) {
        for (ParamKey paramKey : ParamKey.values()){
            if (paramKey.toParam().equals(param)){
                return paramKey;
            }
        }
        return null;
    }
}
