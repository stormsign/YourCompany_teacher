package com.miuhouse.yourcompany.teacher.presenter.interf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kings on 7/13/2016.
 */
public interface IUserInformationPresenter {
    void doChangeUserInformation(String teacherId, ArrayList<String> images, String tName, int sex, String college, String profession, int education, int grade, List<Integer> pbxType, String introduction, String headUrl);

    void getUserInfo(String teacherId);
}
