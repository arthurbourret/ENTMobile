package com.example.entmobile.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.entmobile.R;
import com.example.entmobile.schedule.ui.main.ScheduleFragment;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarParser;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.component.CalendarComponent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

public class Schedule extends AppCompatActivity {
    private Calendar calendar;
    private String[] months;

    private TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);

        calendar = Calendar.getInstance();
        months = getResources().getStringArray(R.array.months);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ScheduleFragment.newInstance())
                    .commitNow();
        }

        dateView = findViewById(R.id.dateView);

        changeDate(0);

        ImageButton button_previous = findViewById(R.id.button_previousDate);
        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousDate();
            }
        });

        ImageButton button_next = findViewById(R.id.button_nextDate);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextDate();
            }
        });

        openScheduleData();
    }

    private void openScheduleData() {
        //TODO recuperer le lien url donner sur l'ent
        try {
            InputStream file = getAssets().open("ADECal.ics");
            net.fortuna.ical4j.model.Calendar schedule = (new CalendarBuilder()).build(file);

            for (CalendarComponent component : schedule.getComponents())
                Log.i("prompt", component.toString());

        } catch (IOException | ParserException e) {
            e.printStackTrace();
        }
    }

    private void previousDate() {
        changeDate(-1);
    }

    private void nextDate() {
        changeDate(1);
    }

    private String changeDate(int amountOfDay) {
        calendar.add(Calendar.DAY_OF_YEAR, amountOfDay);
        Date date = calendar.getTime();
        String dateText = date.getDate() + " " + months[date.getMonth()] + " " + (date.getYear() + 1900);
        dateView.setText(dateText);
        return dateText;
    }

}
