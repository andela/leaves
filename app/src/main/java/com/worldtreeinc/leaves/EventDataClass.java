package com.worldtreeinc.leaves;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by andela on 8/5/15.
 */
public class EventDataClass {
    EditText eventDateEditText;
    Context context;
    public EventDataClass(Context context,EditText eventDateEditText){
        this.context = context;
        this.eventDateEditText = eventDateEditText;

    }

    public void selectDate() {
        int mYear, mMonth, mDay;
        // Process to get Current Date
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Display Selected date in text box
                eventDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }
}
