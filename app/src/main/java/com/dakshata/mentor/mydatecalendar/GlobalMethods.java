package com.dakshata.mentor.mydatecalendar;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by by Umesh Kumar on 5-2-2019.
 */
public class GlobalMethods {

    public static String convertDate(String inputDate, SimpleDateFormat inputFormat, SimpleDateFormat outputFormat) {

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(inputDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getCurrentDate(Context context) {
        Date pdaDate = new Date();
        SimpleDateFormat sdfPDaDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        String fDatePda = sdfPDaDate.format(pdaDate).trim();
        return fDatePda;
    }

}
