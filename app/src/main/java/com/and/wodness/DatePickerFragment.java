package com.and.wodness;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;
import android.app.Dialog;
import java.util.Calendar;
/**
 * Created by Ivan on 9/1/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private static int startYear, endYear;
    String selectdate;
    public static int resYear = 0;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors - Here we uses first one
            public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
         */
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
        TextView tv = (CustomTextView) getActivity().findViewById(R.id.profile_ddmmyy_edittext);
        if (month < 9) {
            if (day <= 9) {
//                selectdate = year + "/0"
//                        + (month + 1) + "/" + "0" + day;
                selectdate = "0" + day +  "/0" + (month + 1) +  year;
                tv.setText(selectdate);
            } else {
//                selectdate = year + "/0"
//                        + (month + 1) + "/" + day;
                selectdate = day + "/" + (month + 1) + "/0" + year;
                tv.setText(selectdate);
            }


        } else {
            if (day <= 9) {
//                selectdate = year + "/"
//                        + (month + 1) + "/" + "0" + day;
                selectdate = "0" + day + "/" + (month + 1) + "/" + year;

                tv.setText(selectdate);

            } else {
//                selectdate = year + "/"
//                        + (month + 1) + "/" + day;
                selectdate = day + "/" + (month + 1) + "/" + year;
                tv.setText(selectdate);

            }
        }
    }
    public int getCurrentDate()
    {
        Calendar c=Calendar.getInstance();
        endYear=c.get(Calendar.YEAR);
        return endYear;
    }
    public int setDateOfBirth(int sYear, int sMonth, int sDay)
    {
        startYear=sYear;
        return startYear;
    }
    public static int calcualteYear()
    {
        resYear=endYear-startYear;
        return resYear;
    }
 }

