package com.example.entmobile.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entmobile.R;

import java.util.ArrayList;

public class CourseAdaptator extends RecyclerView.Adapter<ContentViewHolder> {
    private ArrayList<Course> courses;

    /**
     * Instantiate the class with the courses to be displayed
     * @param courses
     */
    public CourseAdaptator(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_card_view, parent, false);
        // get the schedule_card_view.xml layout and set it for ContentViewHolder
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        String[] infos = courses.get(position).getInfos(); // get the infos of the course at this position

        for (int i=0; i<holder.infosView.length; i++)
            holder.infosView[i].setText(infos[i]); // set the infos in the texts
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}

/**
 * A class that holds the schedule_card_view.xml layout
 */
class ContentViewHolder extends RecyclerView.ViewHolder {
    TextView[] infosView;

    ContentViewHolder(View view) {
        super(view);

        infosView = new TextView[4];
        infosView[0] = view.findViewById(R.id.start_view); // the start hour of a course
        infosView[1] = view.findViewById(R.id.end_view); // the end hour of a course
        infosView[2] = view.findViewById(R.id.name_view); // the name of a course
        infosView[3] = view.findViewById(R.id.room_view); // the location of a course
    }
}

