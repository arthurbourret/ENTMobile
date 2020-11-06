package com.example.entmobile.schedule;

import android.app.AlertDialog;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.entmobile.R;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class DialogChangeDay extends DialogFragment {
    private AlertDialog.Builder builder;

    private Date date;

    private Button button_validate;

    public DialogChangeDay(View view, long time) {
        ViewGroup viewGroup = view.findViewById(android.R.id.content); // create a new ViewGroup
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.schedule_dialog_calendar, viewGroup, false);
        // get the layout for the dialog

        date = new Date(); // instantiate the date with today's date

        button_validate = dialogView.findViewById(R.id.validate_date_dialog);

        CalendarView calendarView = dialogView.findViewById(R.id.dialog_calendar_view);
        calendarView.setDate(time, true, true);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                date.setYear(year - 1900); // set year (getYear add 1900 so we have to minus it)
                date.setMonth(month);
                date.setDate(dayOfMonth);
            }
        });

        buildDialog(dialogView);
    }

    /**
     * Set the dialog's builder
     *
     * @param dialogView The view from where to build the Dialog
     */
    private void buildDialog(View dialogView) {
        builder = new AlertDialog.Builder(dialogView.getContext()); // create a dialog builder
        builder.setView(dialogView)
                .setCancelable(true)
                .setTitle(""); // build the dialog with this view
    }

    /**
     * Show the dialog if it has been initialized
     */
    public void show() {
        if (builder != null)
            builder.create().show(); // show the dialog
    }

    public Date getSelectedDate() {
        return date;
    }

    public Button getButton_validate() {
        return button_validate;
    }

}
