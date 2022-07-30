package com.ansarbank.room.Util;

import android.support.constraint.ConstraintLayout;
import android.widget.ImageButton;

import com.ansarbank.room.R;

import java.util.ArrayList;

public class Const {

    public static final int REQUEST = 2;
    public static final String SCAN_RESULT = "finalResult";
    public static int number = 0;
    public static int ID;
    public static String AppName = "AppName";
    public static String Tag = "Tag";
    public static String Phone = "Phone";
    public static ArrayList<String> selectCards = new ArrayList<>();
    public static ArrayList<ConstraintLayout> selectView = new ArrayList<>();
    public static ArrayList<ImageButton> cancels = new ArrayList<>();
    public static boolean correct;
    public static boolean correct2;
    public static boolean correct3;
    public static boolean correct4;
    public static boolean correct5;

    public static ArrayList<Row> getList() {

        ArrayList<Row> rows = new ArrayList<>();

        Row row2 = new Row("حساب کاربری", R.drawable.profile2);
        Row row3 = new Row("باشگاه مشتریان", R.drawable.bashgah);
        Row row4 = new Row("اخبار و اطلاعیه ها", R.drawable.news);
        Row row6 = new Row("خدمات بانکی", R.drawable.bankbuilding);
        Row row7 = new Row("خروج از حساب کاربری", R.drawable.exit);

        rows.add(row2);
        rows.add(row6);
        rows.add(row3);
        rows.add(row4);
        //  rows.add(row5);
        rows.add(row7);

        return rows;
    }

}
