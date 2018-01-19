package com.wizinno.jas.exercise.service.dto;

import java.util.Date;

/**
 * Created by HP on 2017/8/21.
 */
public class DataDto {
    private Date time;
    private String timeDesc;
    private int angle;
    private String event;
    private int level;

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
