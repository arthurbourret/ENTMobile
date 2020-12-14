package com.example.entmobile.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entmobile.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    public List<Note> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;


    // data is passed into the constructor
    NotesAdapter(Context context, List<Note> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType") View view = mInflater.inflate(R.layout.note_display, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Note note = mData.get(position);
        holder.note_title.setText(note.getTitle());
        holder.note_cat.setText(note.getCategory());
        holder.note_cont.setText(note.getContent());

        String category = mData.get(position).getCategory();

        putColorOnCategrory(holder, category);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View viewForeground;
        View viewBackground;
        TextView note_title;
        TextView note_cat;
        TextView note_cont;

        ViewHolder(View itemView) {
            super(itemView);
            note_title = itemView.findViewById(R.id.note_title);
            note_cat = itemView.findViewById(R.id.note_category);
            note_cont = itemView.findViewById(R.id.note_content);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Note getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void removeItem(int position) {

        if (mData == null || mData.isEmpty()) {
            return;
        }
        else if (position > -1) {
            mData.remove(position);

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mData.size());
        }
        Log.e("position", String.valueOf(position));

        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()

        //notifyItemRemoved(position);
    }

    public void restoreItem(Note note, int position) {
        mData.add(position, note);
        // notify item added by position
        notifyItemInserted(position);
    }


    public int findColorCategory(String categoryToFind){

        //Initializes the SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context); //Initializes the SharedPreferences

        //Retrieves the number of notes from the SharedPreferences
        int nb_categories = preferences.getInt("nb_categories", 0); //Gets the amount of notes saved in the SharedPreferences

        int colorFound = 0;
        
        for (int i=1; i<=nb_categories; i++) {
            //Prepares the Keys that will be used to retrieve the Note's attributes
            String categoryNameKey = "note_category_" + Integer.toString(i) + "_name";
            String categoryColor = "note_category_" + Integer.toString(i) + "_color";


            //Retrieves each of the Note's attributes from the SharedPreferences
            String categoryNameValue = preferences.getString(categoryNameKey, "error"); //Gets the amount of notes saved in the SharedPreferences
            int categoryColorValue = preferences.getInt(categoryColor, 0);

            Log.e("findCat", categoryNameValue);

            if (categoryToFind == categoryNameValue){
                colorFound =  categoryColorValue;
            }
        }
        return  colorFound;
    }

    public void putColorOnCategrory(ViewHolder holder, String category){

        int colorCategory = findColorCategory(category);

        Log.e("category", category);

        GradientDrawable foreGround_Background = new GradientDrawable();
        foreGround_Background.setColor(Color.WHITE);
        foreGround_Background.setCornerRadius(20);
        foreGround_Background.mutate();
        foreGround_Background.setStroke(10, colorCategory);

        holder.viewForeground.setBackground(foreGround_Background);

        GradientDrawable backGround_Background = new GradientDrawable();
        backGround_Background.setColor(Color.RED);
        backGround_Background.setCornerRadius(20);
        foreGround_Background.mutate();
        //backGround_Background.setStroke(10, Color.RED);
        holder.viewBackground.setBackground(backGround_Background);
    }
}
