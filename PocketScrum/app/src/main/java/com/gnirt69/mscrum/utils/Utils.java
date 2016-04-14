package com.gnirt69.mscrum.utils;

import android.content.SharedPreferences;
import android.widget.DatePicker;

import com.gnirt69.mscrum.constant.MSConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 984834 on 4/12/2016.
 */
public class Utils {

    public static void Save(SharedPreferences sharedpreferences, String token, int userTYpe) {

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(MSConstants.TOKEN, token);
        editor.putInt(MSConstants.USERTYPE, userTYpe);
        editor.commit();
    }


    public static String getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();



        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

//        return calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(calendar.getTime());

        return dateString;
    }



}
