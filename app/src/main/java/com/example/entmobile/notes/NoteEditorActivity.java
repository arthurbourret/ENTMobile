package com.example.entmobile.notes;

import androidx.appcompat.app.AppCompatActivity;

import com.example.entmobile.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class NoteEditorActivity extends AppCompatActivity {

    Button save_note_button;
    Button cancel_note_button;

    ImageButton tasks_button;
    ImageButton category_button;

    EditText note_title_edit_text;
    EditText note_content_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        //Finds the object's IDs and initializes local variables
        save_note_button = findViewById(R.id.save_note_button);
        cancel_note_button = findViewById(R.id.cancel_note_button);
        tasks_button = findViewById(R.id.tasks_button);
        category_button = findViewById(R.id.category_button);
        note_title_edit_text = findViewById(R.id.note_title_edit_text);
        note_content_edit_text = findViewById(R.id.note_content_edit_text);

        isEditedNote();

        setupButtonsListeners();
    }

    private void setupButtonsListeners() {
        save_note_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewNote();
            }
        });

        cancel_note_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void isEditedNote() {
        Intent intent = getIntent();
        int edit_note = intent.getIntExtra("note_edit", -1);

        if (edit_note != -1) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); //Initializes the SharedPreferences

            String categoryKey = "note_" + Integer.toString(edit_note) + "_category";
            String titleKey = "note_" + Integer.toString(edit_note) + "_title";
            String contentKey = "note_" + Integer.toString(edit_note) + "_content";

            //Retrieves each of the Note's attributes from the SharedPreferences
            String newNoteCategory = preferences.getString(categoryKey, ""); //Gets the amount of notes saved in the SharedPreferences
            String newNoteTitle = preferences.getString(titleKey, ""); //Gets the amount of notes saved in the SharedPreferences
            String newNoteContent = preferences.getString(contentKey, ""); //Gets the amount of notes saved in the SharedPreferences

            note_title_edit_text.setText(newNoteTitle);
            note_content_edit_text.setText(newNoteContent);
        }
    }

    /*/**
     * This method is used to delete a note.
     * @param position
     */
    /*public void deleteNote(int position) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); //Initializes the SharedPreferences

        String categoryKey = "note_" + Integer.toString(position) + "_category";
        String titleKey = "note_" + Integer.toString(position) + "_title";
        String contentKey = "note_" + Integer.toString(position) + "_content";

        preferences.edit().remove(categoryKey).commit();
        preferences.edit().remove(titleKey).commit();
        preferences.edit().remove(contentKey).commit();
    }*/

    private void saveNewNote() {

        if (!note_title_edit_text.getText().toString().equals("")) {
            if (!note_content_edit_text.getText().toString().equals("")) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); //Initializes the SharedPreferences
                SharedPreferences.Editor editor = preferences.edit(); //Initializes the SharedPreferences' editor

                Intent intent_origin = getIntent();
                int edit_note = intent_origin.getIntExtra("note_edit", -1);

                int new_nb_notes;
                int new_note_pos;

                if (edit_note != -1) {
                    new_nb_notes = preferences.getInt("nb_notes", 0);
                    new_note_pos = intent_origin.getIntExtra("note_edit", -1);
                }
                else {
                    new_nb_notes = preferences.getInt("nb_notes", 0) + 1; //Gets the amount of notes saved in the SharedPreferences and adds one
                    new_note_pos = preferences.getInt("nb_notes", 0) + 1;
                }

                editor.putInt("nb_notes", new_nb_notes);

                //Prepares the Keys that will be used to save the Note's attributes
                String categoryKey = "note_" + Integer.toString(new_note_pos) + "_category";
                String titleKey = "note_" + Integer.toString(new_note_pos) + "_title";
                String contentKey = "note_" + Integer.toString(new_note_pos) + "_content";

                //Prepares the Values that will be used to save the Note's attributes
                String categoryValue = "default";
                String titleValue = note_title_edit_text.getText().toString();
                String contentValue = note_content_edit_text.getText().toString();

                //Saves each of the Note's attributes in the SharedPreferences
                editor.putString(categoryKey, categoryValue);
                editor.putString(titleKey, titleValue);
                editor.putString(contentKey, contentValue);

                editor.apply(); //Applies the changes

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                note_content_edit_text.setError("You need to fill the note."); //Shows an error message indicating that the password isn't correct
            }
        }
        else {
            note_title_edit_text.setError("You need to set a title."); //Shows an error message indicating that the password isn't correct
        }
    }
}