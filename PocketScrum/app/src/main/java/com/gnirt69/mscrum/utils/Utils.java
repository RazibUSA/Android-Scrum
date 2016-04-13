package com.gnirt69.mscrum.utils;

import android.content.SharedPreferences;

import com.gnirt69.mscrum.constant.MSConstants;

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






}
