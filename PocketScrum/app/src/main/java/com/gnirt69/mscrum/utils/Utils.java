package com.gnirt69.mscrum.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.widget.DatePicker;
import android.widget.ListView;

import com.gnirt69.mscrum.constant.MSConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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


    public static boolean[] checkBoxDialog(List<CharSequence> list, Activity activity) {
        // Intialize  readable sequence of char values
        final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(activity);
        builderDialog.setTitle("Select Item");
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count]; // set is_checked boolean false;

        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setMultiChoiceItems(dialogList, is_checked,
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton, boolean isChecked) {
                    }
                });

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                stringBuilder.append(list.getItemAtPosition(i));

                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder.toString().trim().equals("")) {

//                            ((TextView) findViewById(R.id.text)).setText("Click here to open Dialog");
                            stringBuilder.setLength(0);

                        } else {

//                            ((TextView) findViewById(R.id.text)).setText(stringBuilder);
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        ((TextView) findViewById(R.id.text)).setText("Click here to open Dialog");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

        return is_checked;
    }



}
