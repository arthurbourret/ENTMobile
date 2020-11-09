package com.example.entmobile.schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ParseWebData {

    public ParseWebData() {
    }

    public ArrayList<Course> loadFromHtml() {
        InputStream file = null;

        try {
            String html = "http://planning.univ-lemans.fr/jsp/custom/modules/plannings/anonymous_cal.jsp?resources=2668&projectId=3&calType=ical&nbWeeks=4";
            html = "https://www.qwant.com/?q=android%20studio%20get%20data%20from%20download%20website&t=web&client=brz-moz";
            URL url = new URL(html);
            file = url.openStream();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return openScheduleData(file);
    }

    /**
     * Method that read the calendar from the ent
     * It should be called only once when the file is downloaded because it takes some times to read the data
     */
    public static ArrayList<Course> openScheduleData(InputStream file) {
        ArrayList<Course> coursesFromData = new ArrayList<>();

        if (file != null)
            try {
                // InputStream file = context.getAssets().open("ADECal.ics"); // temporary way of getting the calendar from the ent
                BufferedReader br = new BufferedReader(new InputStreamReader(file)); // parsing the file
                String data = "", // initialisation of the data
                        line;

                boolean isEvent = false;
                while ((line = br.readLine()) != null) { // if there is a line
                   /*
                    Log.i("prompt", line);

                    if (isEvent) // if the line is part of an event
                        data += line + "\n"; // get the line

                    if (line.contains("BEGIN:VEVENT")) // if the line is the start of an event
                        isEvent = true;

                    if (line.contains("END:VEVENT")) { // if the line is the end of an event
                        coursesFromData.add(Course.readData(data)); // extract the data of the text end ad it to courses

                        isEvent = false;
                        data = ""; // reset the data
                    }

                    */
                }

                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return coursesFromData;
    }


}
