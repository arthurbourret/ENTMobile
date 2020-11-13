package com.example.entmobile.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entmobile.R;

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

    RecyclerView note_recycler_view;

    NotesAdapter notesAdapter;

    final int NOTE_EDITION_DONE = 1;

    private List<Note> noteList = new ArrayList<Note>();
    private List<Category> categoriesList = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //Finds the object's IDs and initializes local variables
        notes_counter = findViewById(R.id.notes_counter);
        add_note_button = findViewById(R.id.save_note_button);
        notes_settings_button = findViewById(R.id.notes_settings_button);
        note_recycler_view = findViewById(R.id.note_recycler_view);

        //Sets up the custom feedback for the buttons that need it
        //setupButtonsFeedback();

        //Loads the data from the Shared Preferences
        loadNotesFromSharedPreferences();

        loadCategoriesFromSharedPreferences();

        //Loads the data in the recycler view
        reloadRecycleView();

        //Sets up the buttons' listeners
        setupButtonsListeners();

    }

    private void reloadRecycleView() {
        note_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        notesAdapter = new NotesAdapter(this, noteList);
        note_recycler_view.setAdapter(notesAdapter);
    }

    /**
     * Method used to set up the buttons' listeners.
     */
    private void setupButtonsListeners() {
        //Set a listener on the Add Note button
        add_note_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNote();
            }
        });

        //Set a listener on the Settings button
        notes_settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowNotesSettingsDialog();
            }
        });

        //Set a listener on each of the notes
        notesAdapter.setClickListener(new NotesAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                editNote(position+1);
            }
        });
    }

    /**
     * Method used to create a new note. Has to be implemented.
     * TODO
     */
    private void newNote() {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        startActivityForResult(intent, NOTE_EDITION_DONE);
    }

    /**
     * Opens the Note Editor and passes through the Extras the number of the Note that will be edited.
     * @param pos String containing the number of the note
     */
    private void editNote(int pos) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("note_edit", pos);
        startActivityForResult(intent, NOTE_EDITION_DONE);
    }

    /**
     * Method used to open the notes settings. Has to be implemented.
     * TODO
     */
    private void ShowNotesSettingsDialog() {
        //Setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Sets the dialog's title
        builder.setTitle("Displayed categories");// add a checkbox list

        //Initializes the tables
        String[] categoriesNames = new String[categoriesList.size()];
        boolean[] checkedItems = new boolean[categoriesList.size()];

        //Sets up the tables for them to be displayed in the MultiChoiceItems dialog
        for (int i=0;i<categoriesList.size();i++) {
            categoriesNames[i] = categoriesList.get(i).getName();
            checkedItems[i] = categoriesList.get(i).isDisplayed();
        }

        //Fills the dialog with CheckBoxes
        builder.setMultiChoiceItems(categoriesNames, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                categoriesList.get(which).setDisplayed(isChecked);
            }
        });

        //When the OK button is pressed
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveCategoriesInSharedPreferences();
            }
        });

        //When the Add button is pressed
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShowAddNewCategoriesDialog();
            }
        });

        //When the Remove button is pressed
        builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!categoriesList.isEmpty()) {
                    ShowRemoveCategoriesDialog();
                }
                else {
                    ShowAlertNoCategories();
                }
            }
        });

        //Creates and shows the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ShowAlertNoCategories() {
        Toast.makeText(this, "There are no categories", Toast.LENGTH_LONG).show();
    }

    private void ShowRemoveCategoriesDialog() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove category");// add a radio button list

        //Initializes the tables
        int checkedItem[] = {0};

        String[] categoriesNames = new String[categoriesList.size()];

        if (!categoriesList.isEmpty()) {
            for (int i=0; i<categoriesList.size(); i++) {
                categoriesNames[i] = categoriesList.get(i).getName();
            }
        }

        builder.setSingleChoiceItems(categoriesNames, checkedItem[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkedItem[0] = which;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for (int i=0; i<noteList.size(); i++) {
                    if (noteList.get(i).getCategory().matches(categoriesNames[checkedItem[0]])) {
                        noteList.get(i).setCategory("None");
                    }
                }
                categoriesList.remove(checkedItem[0]);
                saveCategoriesInSharedPreferences();
                saveNotesInSharedPreferences();
                reloadRecycleView();
                setupButtonsListeners();
            }
        });

        builder.setNegativeButton("Cancel", null);// create and show the alert dialog

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ShowAddNewCategoriesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New category");

        // set the custom layout
        final View categories_display = getLayoutInflater().inflate(R.layout.categories_display, null);

        builder.setView(categories_display);

        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                EditText editText = categories_display.findViewById(R.id.editText);
                addNewCategory(editText.getText().toString());
            }
        });

        //When the Cancel button is pressed
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addNewCategory(String name) {
        Category newCategory = new Category(name, true);
        categoriesList.add(newCategory);
        saveCategoriesInSharedPreferences();
    }

    /**
     * This method is used to recreate all of the Note objects contained in the SharedPreferences and then stores them in the noteList Array List.
     */
    private void loadNotesFromSharedPreferences() {
        //Initializes the SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); //Initializes the SharedPreferences

        //Retrieves the number of notes from the SharedPreferences
        int nb_notes = preferences.getInt("nb_notes", 0); //Gets the amount of notes saved in the SharedPreferences

        //Updates the notes_counter EditText with the current amount of notes
        notes_counter.setText(Integer.toString(nb_notes));

        //Clears the current noteList
        noteList.clear();

        for (int i=1; i<=nb_notes; i++) {
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
     * This method is used to recreate all of the Note objects contained in the SharedPreferences and then stores them in the noteList Array List.
     */
    private void loadCategoriesFromSharedPreferences() {
        //Initializes the SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); //Initializes the SharedPreferences

        //Retrieves the number of notes from the SharedPreferences
        int nb_categories = preferences.getInt("nb_categories", 0); //Gets the amount of notes saved in the SharedPreferences

        //Clears the current noteList
        categoriesList.clear();

        for (int i=1; i<=nb_categories; i++) {
            //Prepares the Keys that will be used to retrieve the Note's attributes
            String categoryNameKey = "note_category_" + Integer.toString(i) + "_name";
            String categoryDisplayedKey = "note_category_" + Integer.toString(i) + "_displayed";

            //Retrieves each of the Note's attributes from the SharedPreferences
            String categoryNameValue = preferences.getString(categoryNameKey, "error"); //Gets the amount of notes saved in the SharedPreferences
            Boolean categoryDisplayedValue = preferences.getBoolean(categoryDisplayedKey, true); //Gets the amount of notes saved in the SharedPreferences

            //Creates a new Note using the data retrieved from the SharedPreferences
            Category newCategory = new Category(categoryNameValue, categoryDisplayedValue);

            //Adds that note to the noteList Array List
            categoriesList.add(newCategory);
        }
    }

    private void saveNotesInSharedPreferences() {
        //Initializes the SharedPreferences and initializes the SharedPreferences' editor
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        // If the noteList Array List isn't empty
        if (!noteList.isEmpty()) {

            int nb_notes = noteList.size();

            editor.putInt("nb_notes", nb_notes);

            //For each Note in the noteList
            for (int i=0; i<noteList.size(); i++) {
                //Prepares the Keys that will be used to save the Note's attributes

                String categoryKey = "note_" + Integer.toString(i+1) + "_category";
                String titleKey = "note_" + Integer.toString(i+1) + "_title";
                String contentKey = "note_" + Integer.toString(i+1) + "_content";

                //Prepares the Values that will be used to save the Note's attributes
                String noteCategory = noteList.get(i).getCategory();
                String noteTitle = noteList.get(i).getTitle();
                String noteContent = noteList.get(i).getContent();

                //Saves each of the Note's attributes in the SharedPreferences
                editor.putString(categoryKey, noteCategory);
                editor.putString(titleKey, noteTitle);
                editor.putString(contentKey, noteContent);
            }
        }

        //Applies the changes
        editor.apply();
    }

    private void saveCategoriesInSharedPreferences() {
        //Initializes the SharedPreferences and initializes the SharedPreferences' editor
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        // If the noteList Array List isn't empty
        if (!categoriesList.isEmpty()) {

            int nb_categories = categoriesList.size();

            editor.putInt("nb_categories", nb_categories);

            //For each Note in the noteList
            for (int i=0; i<categoriesList.size(); i++) {
                //Prepares the Keys that will be used to save the Note's attributes

                String categoryNameKey = "note_category_" + Integer.toString(i+1) + "_name";
                String categoryDisplayedKey = "note_category_" + Integer.toString(i+1) + "_displayed";

                //Prepares the Values that will be used to save the Note's attributes
                String categoryNameValue = categoriesList.get(i).getName();
                Boolean categoryDisplayedValue = categoriesList.get(i).isDisplayed();

                //Saves each of the Note's attributes in the SharedPreferences
                editor.putString(categoryNameKey, categoryNameValue);
                editor.putBoolean(categoryDisplayedKey, categoryDisplayedValue);
            }
        }

        //Applies the changes
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOTE_EDITION_DONE) {
            loadNotesFromSharedPreferences();
            loadCategoriesFromSharedPreferences();
            reloadRecycleView();
            setupButtonsListeners();
        }
    }

}