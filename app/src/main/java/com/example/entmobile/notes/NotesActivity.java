package com.example.entmobile.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entmobile.R;
import com.example.entmobile.activities.Settings;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    /**
     * TextView used to show the user the amount of notes saved and available
     */
    TextView notes_counter;

    /**
     * ImageButton used to launch the openNoteSettings() method
     */
    ImageButton notes_settings_button;

    /**
     * ImageButton used to launch the createNewNote() method
     */
    ImageButton add_note_button;

    private List<Note> noteList = new ArrayList<Note>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //Finds the object's IDs and initializes local variables
        notes_counter = findViewById(R.id.notes_counter);
        add_note_button = findViewById(R.id.add_note_button);
        notes_settings_button = findViewById(R.id.notes_settings_button);

        //Sets up the custom feedback for the buttons that need it
        //setupButtonsFeedback();

        //Sets up the buttons' listeners
        setupButtonsListeners();
    }

    /**
     * Method used to set up the buttons custom made press feedback.
     * It basically defines a different background color if the button is pressed and returns the original color when the button is unpressed.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setupButtonsFeedback() {
        add_note_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    add_note_button.setBackgroundResource(R.drawable.app_res_bg_rounded_corners_pressed);
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    add_note_button.setBackgroundResource(R.drawable.app_res_bg_rounded_corners);
                    return true;
                }
                return false;
            }
        });

        notes_settings_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    notes_settings_button.setBackgroundResource(R.drawable.app_res_bg_rounded_corners_pressed);
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    notes_settings_button.setBackgroundResource(R.drawable.app_res_bg_rounded_corners);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Method used to set up the buttons' listeners.
     */
    private void setupButtonsListeners() {
        //Set a listener on the Add Note button
        add_note_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewNote();
            }
        });

        //Set a listener on the Settings button
        notes_settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNoteSettings();
            }
        });
    }

    /**
     * Method used to create a new note. Has to be implemented.
     * TODO
     */
    private void createNewNote() {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        startActivity(intent);
    }

    /**
     * Method used to open the notes settings. Has to be implemented.
     * TODO
     */
    private void openNoteSettings() {
        notImplemented();
    }

    /**
     * This method is used to save all of the Note objects contained in the noteList Array List, inside the SharedPreferences.
     */
    private void saveNotesInSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); //Initializes the SharedPreferences
        SharedPreferences.Editor editor = preferences.edit(); //Initializes the SharedPreferences' editor

        // If the noteList Array List isn't empty
        if (!noteList.isEmpty()) {

            int nb_notes = noteList.size();

            editor.putInt("nb_notes", nb_notes);

            //For each Note in the noteList
            for (int i=0; i<noteList.size(); i++) {
                //Prepares the Keys that will be used to save the Note's attributes
                String categoryKey = "note_" + Integer.toString(i) + "_category";
                String titleKey = "note_" + Integer.toString(i) + "_title";
                String contentKey = "note_" + Integer.toString(i) + "_content";

                //Prepares the Values that will be used to save the Note's attributes
                String categoryValue = noteList.get(i).getCategory();
                String titleValue = noteList.get(i).getTitle();
                String contentValue = noteList.get(i).getContent();

                //Saves each of the Note's attributes in the SharedPreferences
                editor.putString(categoryKey, categoryValue);
                editor.putString(titleKey, titleValue);
                editor.putString(contentKey, contentValue);
            }
        }

        editor.apply(); //Applies the changes
    }

    /**
     * This method is used to recreate all of the Note objects contained in the SharedPreferences and then stores them in the noteList Array List.
     */
    private void loadNotesFromSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); //Initializes the SharedPreferences
        int nb_notes = preferences.getInt("nb_notes", 0); //Gets the amount of notes saved in the SharedPreferences

        for (int i=0; i<nb_notes; i++) {
            //Prepares the Keys that will be used to retrieve the Note's attributes
            String categoryKey = "note_" + Integer.toString(i) + "_category";
            String titleKey = "note_" + Integer.toString(i) + "_title";
            String contentKey = "note_" + Integer.toString(i) + "_content";

            //Retrieves each of the Note's attributes from the SharedPreferences
            String newNoteCategory = preferences.getString(categoryKey, ""); //Gets the amount of notes saved in the SharedPreferences
            String newNoteTitle = preferences.getString(titleKey, ""); //Gets the amount of notes saved in the SharedPreferences
            String newNoteContent = preferences.getString(contentKey, ""); //Gets the amount of notes saved in the SharedPreferences

            //Creates a new Note using the data retrieved from the SharedPreferences
            Note newNote = new Note(newNoteCategory, newNoteTitle, newNoteContent);

            //Adds that note to the noteList Array List
            noteList.add(newNote);
        }
    }

    /**
     * To be removed at the end of the project
     * Shows a toast signaling a part of the app that is not implemented yet
     */
    private void notImplemented() {
        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
    }
}