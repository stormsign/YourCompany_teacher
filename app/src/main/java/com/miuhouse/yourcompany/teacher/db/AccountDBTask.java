package com.miuhouse.yourcompany.teacher.db;

import android.accounts.Account;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.miuhouse.yourcompany.teacher.model.TeacherInfo;
import com.miuhouse.yourcompany.teacher.utils.L;

/**
 * Created by kings on 7/11/2016.
 */
public class AccountDBTask {
    private AccountDBTask() {

    }

    private static SQLiteDatabase getWsd() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        return databaseHelper.getWritableDatabase();
    }

    private static SQLiteDatabase getRsd() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        return databaseHelper.getReadableDatabase();
    }

    public static void saveUserBean(TeacherInfo teacherInfo) {
        clear();
        if (getWsd().isOpen()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(AccountTable.TEACHER_ID, teacherInfo.getId());
            contentValues.put(AccountTable.TNAME, teacherInfo.gettName());
            contentValues.put(AccountTable.MOBILE, teacherInfo.getMobile());
            contentValues.put(AccountTable.TOKEN, teacherInfo.getToken());
            getWsd().insert(AccountTable.TABLE_NAME, null, contentValues);
        }
    }

    public static TeacherInfo getAccount() {
        String sql = "select * from " + AccountTable.TABLE_NAME;
        Cursor c = getRsd().rawQuery(sql, null);
        if (c.moveToNext()) {
            TeacherInfo account = new TeacherInfo();
            int colid = c.getColumnIndex(AccountTable.TEACHER_ID);
            account.setId(c.getString(colid));

            colid = c.getColumnIndex(AccountTable.TNAME);
            account.settName(c.getString(colid));

            colid = c.getColumnIndex(AccountTable.MOBILE);
            account.setMobile(c.getString(colid));

            colid = c.getColumnIndex(AccountTable.TOKEN);
            account.setToken(c.getString(colid));

            colid = c.getColumnIndex(AccountTable.BALANCE);
            account.setBalance(c.getDouble(colid));
            L.i("TAG", "account=" + account.getId());
            return account;
        }
        c.close();
        return null;
    }

    public static void clear() {
        String sql = "delete from " + AccountTable.TABLE_NAME;

        getWsd().execSQL(sql);
    }

}
