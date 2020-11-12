package com.example.entmobile.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.entmobile.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class NoteEditorActivity extends AppCompatActivity {

    Button save_note_button;
    Button cancel_note_button;

    ImageButton tasks_button;
    ImageButton category_button;

    EditText note_title_edit_text;
    EditText note_content_edit_text;
    String note_category_string = "Misc";
    TextView textView;

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
        textView = findViewById(R.id.textView);

        if (isEditedNote()) {
            setupEditedNote(getEditedNote());
        }

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

        category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoriesSelector();
            }
        });
    }

    private boolean isEditedNote() {
        Intent intent = getIntent();
        int edit_note = intent.getIntExtra("note_edit", -1);

        if (edit_note != -1) {
            return true;
        }
        else {
            return false;
        }
    }

    private int getEditedNote() {
        Intent intent = getIntent();

        return intent.getIntExtra("note_edit", -1);
    }

    private void setupEditedNote(int edit_note) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); //Initializes the SharedPreferences

        String categoryKey = "note_" + Integer.toString(edit_note) + "_category";
        String titleKey = "note_" + Integer.toString(edit_note) + "_title";
        String contentKey = "note_" + Integer.toString(edit_note) + "_content";

        //Retrieves each of the Note's attributes from the SharedPreferences
        String editedNoteCategory = preferences.getString(categoryKey, ""); //Gets the amount of notes saved in the SharedPreferences
        String editedNoteTitle = preferences.getString(titleKey, ""); //Gets the amount of notes saved in the SharedPreferences
        String editedNoteContent = preferences.getString(contentKey, ""); //Gets the amount of notes saved in the SharedPreferences

        note_title_edit_text.setText(editedNoteTitle);
        note_content_edit_text.setText(editedNoteContent);
        textView.setText(editedNoteCategory);
        note_category_string = editedNoteCategory;
    }

    private void showCategoriesSelector() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a category");// add a radio button list

        String[] categories = {"Homework", "Appointment", "Misc"};

        final String[] selectedCategoryName = {note_category_string};
        int checkedItem = 2; // Misc

        builder.setSingleChoiceItems(categories, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selectedCategory) {
                selectedCategoryName[0] = categories[selectedCategory];
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selectedCategory) {
                dialog.dismiss();
                textView.setText(selectedCategoryName[0]);
            }
        });

        builder.setNegativeButton("Cancel", null);// create and show the alert dialog

        AlertDialog dialog = builder.create();
        dialog.show();
    }

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
                String categoryValue = note_category_string;
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