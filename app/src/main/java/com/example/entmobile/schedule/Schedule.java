package com.example.entmobile.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.entmobile.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Schedule extends AppCompatActivity {
    ArrayList<Course> courses;

    private Calendar calendar;
    /**
     * Contains the 12 months in strings
     */
    private static String[] MONTHS;

    private TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);

        // instantiation
        dateView = findViewById(R.id.dateView);
        changeDate(0); // instantiate the value of dateView
        calendar = Calendar.getInstance();
        MONTHS = getResources().getStringArray(R.array.months);
        courses = new ArrayList<>();

        // Set a listener on the Previous Date button
        ImageButton button_previous = findViewById(R.id.button_previousDate);
        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousDate();
            }
        });

        // Set a listener on the Next Date button
        ImageButton button_next = findViewById(R.id.button_nextDate);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextDate();
            }
        });

        openScheduleData();
    }

    /**
     * Method that read the calendar from the ent
     * It should be called only once when the file is downloaded because it takes some times to read the data
     */
    private void openScheduleData() {
        //TODO recuperer le lien url donner sur l'ent

        try {
            InputStream file = getAssets().open("ADECal.ics"); // temporary way of getting the calendar from the ent
            BufferedReader br = new BufferedReader(new InputStreamReader(file)); // parsing the file
            String data = "", // initialisation of the data
                    line;

            boolean isEvent = false;
            while ((line = br.readLine()) != null) { // if there is a line
                if (isEvent) // if the line is part of an event
                    data += line + "\n"; // get the line

                if (line.contains("BEGIN:VEVENT")) // if the line is the start of an event
                    isEvent = true;

                if (line.contains("END:VEVENT")) { // if the line is the end of an event
                    courses.add(Course.readData(data)); // extract the data of the text end ad it to courses

                    isEvent = false;
                    data = ""; // reset the data
                }
            }

            br.close();
            for (Course course : courses)
                Log.i("prompt", course.toString()); // primpt all courses in the console

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change the current date of the calendar to the previous one
     */
    private void previousDate() {
        changeDate(-1);
    }

    /**
     * Change the current date of the calendar to the next one
     */
    private void nextDate() {
        changeDate(1);
    }

    /**
     * Change the day in the calendar to the amount of day computed in the argument
     * and set the dateView to the current date of the calendar
     *
     * @param amountOfDay The amount of days to add to the calendar
     * @return the string value of the date
     */
    private String changeDate(int amountOfDay) {
        calendar.add(Calendar.DAY_OF_YEAR, amountOfDay); // change the day in the calendar
        String dateText = getDateFormated(calendar.getTime()); // get a string from the date
        dateView.setText(dateText); // set dateView to this string
        return dateText;
    }

    /**
     * Get the string value of the day
     *
     * @param date Date used for the day
     * @return the string value of the date
     */
    public static String getDateFormated(Date date) {
        return date.getDate() + " " + MONTHS[date.getMonth()] + " " + (date.getYear() + 1900);
    }

    /**
     * Get the string value of the hour
     *
     * @param date Date used for the hour
     * @return the string value of the date
     */
    public static String getHourFormated(Date date) {
        return date.getHours() + ":" + date.getMinutes();
    }

}
