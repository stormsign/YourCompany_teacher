package com.miuhouse.yourcompany.teacher.model;

import java.io.Serializable;

/**
 * Created by kings on 7/1/2016.
 */
public class User extends BaseBean implements Serializable {

    private TeacherInfo teacherInfo;

    public TeacherInfo getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(TeacherInfo teacherInfo) {
        this.teacherInfo = teacherInfo;
    }
}
