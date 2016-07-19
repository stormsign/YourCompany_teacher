package com.miuhouse.yourcompany.teacher.presenter.interf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kings on 7/13/2016.
 */
public interface IUserInformationPresenter {
    void doChangeUserInformation(String teacherId, ArrayList<String> images, String tName, String sex, String college, String profession, String education, String grade, ArrayList<String> pbxType, String introduction, String headUrl);

    void getUserInfo(String teacherId);
}
