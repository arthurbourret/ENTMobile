package com.example.entmobile.schedule;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.entmobile.R;

/**
 * A class that holds the schedule_card_view.xml layout
 */
public class ContentViewHolder extends RecyclerView.ViewHolder {
    private View view;
    private Course course;

    ContentViewHolder(View view) {
        super(view);
        this.view = view;
    }

    /**
     * Initialize the text views with the info in course
     *
     * @param course The course to display
     */
    public void initContentViewHolder(Course course) {
        if (course != null) {
            this.course = course;

            String[] infos = course.getInfos(); // infos about the course
            int[] infoViewsId = {R.id.start_view, // the start hour view
                    R.id.end_view, // the end hour view
                    R.id.name_view, // the name view
                    R.id.room_view}; // the room view

            for (int i = 0; i < infos.length; i++)
                ((TextView) view.findViewById(infoViewsId[i])).setText(infos[i]);
        }
    }

}