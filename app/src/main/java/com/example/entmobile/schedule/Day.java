package com.example.entmobile.schedule;

import java.util.ArrayList;
import java.util.Date;

/**
 * A day usable for the schedule that contains courses
 */
public class Day {
    ArrayList<Course> coursesOfTheDay; // the courses on this day
    private Date date; // the date of the day

    public Day(String dataForCourses, Date date) {
        this.date = date;
        setCoursesOfTheDay(dataForCourses);
    }

    private void setCoursesOfTheDay(String data) {
    }

}

/**
 * Class containing the datas of a course (time, emplacement, people involved, ...)
 */
class Course {
    private String courseName;
    private String room;

    private String teacher;
    private String groupAttending;

    private String duration;
    private String startHour;
    private String endHour;

    public Course(String courseName, String room, String teacher, String groupAttending, String duration, String startHour, String endHour) {
        this.courseName = courseName;
        this.room = room;
        this.teacher = teacher;
        this.groupAttending = groupAttending;
        this.duration = duration;
        this.startHour = startHour;
        this.endHour = endHour;
    }

}
