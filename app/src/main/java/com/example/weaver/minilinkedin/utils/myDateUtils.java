package com.example.weaver.minilinkedin.utils;

import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Weaver on 2016/8/25.
 */

public class myDateUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy", Locale.getDefault());

    public static String dateToString(@Nullable Date date) {
        return date == null ? "" : sdf.format(date);
    }
    public static Date stringToDate(String dateString){
        try{
            return sdf.parse(dateString);
        }
        catch (ParseException e){
            e.printStackTrace();
            return new Date(0);
        }
    }
}
