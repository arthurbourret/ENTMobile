package com.example.entmobile.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.entmobile.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.function.Consumer;

/**
 * The main class for the schedule module
 */
public class Schedule extends AppCompatActivity {
    RecyclerView scheduleView;

    ArrayList<Course> courses;

    private Calendar calendar;
    private static String[] MONTHS;

    private TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);

        // instantiation
        calendar = Calendar.getInstance();
        MONTHS = getResources().getStringArray(R.array.months);
        courses = openScheduleData(); // extract the courses

        scheduleView = (RecyclerView) findViewById(R.id.scheduleView); // instantiate the recycler
        RecyclerView.LayoutManager recyclerManager = new LinearLayoutManager(this);
        scheduleView.setLayoutManager(recyclerManager); // set a manager for the recycler
        scheduleView.setHasFixedSize(true);

        dateView = findViewById(R.id.dateView); // instantiate the view of the date

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

        changeDate(0); // instantiate the recycler and the date view
    }

    /**
     * Change the day in the calendar to the amount of day computed in the argument.
     * It set the dateView to the current date of the calendar and it show the courses of the day
     *
     * @param amountOfDay The amount of days to add to the calendar
     * @return the string value of the date
     */
    private String changeDate(int amountOfDay) {
        calendar.add(Calendar.DAY_OF_YEAR, amountOfDay); // change the day in the calendar
        String dateText = getDateFormated(calendar.getTime()); // get a string from the date
        dateView.setText(dateText); // set dateView to this string

        //scheduleView.removeAllViews(); // reset the schedules
        ArrayList<Course> thisDayCourses = getCoursesOfTheDay();
        showCourses(thisDayCourses);

        return dateText;
    }

    /**
     * Method that read the calendar from the ent
     * It should be called only once when the file is downloaded because it takes some times to read the data
     */
    private ArrayList<Course> openScheduleData() {
        ArrayList<Course> coursesFromData = new ArrayList<>();
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
                    coursesFromData.add(Course.readData(data)); // extract the data of the text end ad it to courses

                    isEvent = false;
                    data = ""; // reset the data
                }
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return coursesFromData;
    }

    /**
     * Show the givens course in the page on a view. It shows there start and end time (in hour)
     * , there name and their location
     *
     * @param courseToBeShown The courses to be shown on the page
     */
    private void showCourses(ArrayList<Course> courseToBeShown) {
        Collections.sort(courseToBeShown, Course.compareStartHour); // sort the course by their starting hour

        // put the courses to be shown in the recycler view
        RecyclerView.Adapter recyclerAdapter = new CourseAdaptator(courseToBeShown);
        scheduleView.setAdapter(recyclerAdapter); // adapt the recycler view
    }

    /**
     * Gives the courses of the day
     *
     * @return All the lessons happening the same day as the same day of the current day in calendar
     */
    private ArrayList<Course> getCoursesOfTheDay() {
        ArrayList<Course> coursesOfDay = new ArrayList<>();
        Date currentDate = calendar.getTime(); // get the current date

        for (Course course : courses) {
            Date cDate = course.getStartDate();
            if (currentDate.getYear() == cDate.getYear() &&
                    currentDate.getMonth() == cDate.getMonth() &&
                    currentDate.getDate() == cDate.getDate()) {
                // if same year, month, day
                coursesOfDay.add(course); // add to the list
            }
        }

        return coursesOfDay;
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
        return date.getHours() + "h" + ((date.getMinutes()>0)?date.getMinutes():"");
    }

}
