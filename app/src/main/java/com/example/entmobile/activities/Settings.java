package com.example.entmobile.activities;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.example.entmobile.R;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    Button button_tuto_edt;
    Button button_tuto_mails;
    Button button_tuto_notes;
    Button button_tuto_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
        }

        //Finds the object's IDs and initializes local variables
        button_tuto_edt = findViewById(R.id.button_tuto_edt);
        button_tuto_mails = findViewById(R.id.button_tuto_mails);
        button_tuto_notes = findViewById(R.id.button_tuto_notes);
        button_tuto_results = findViewById(R.id.button_tuto_results);

        //Set a listener on the EDT Tutorial button
        button_tuto_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTutorialEDT();
            }
        });

        //Set a listener on the Mails Tutorial button
        button_tuto_mails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTutorialMails();
            }
        });

        //Set a listener on the Notes Tutorial button
        button_tuto_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTutorialNotes();
            }
        });

        //Set a listener on the Results Tutorial button
        button_tuto_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTutorialResults();
            }
        });
    }

    private void openTutorialEDT() {
        //TODO @Flom Ouvrir tuto de l'EDT
        Toast.makeText(this, getString(R.string.ok), Toast.LENGTH_LONG).show();
    }

    private void openTutorialMails() {
        //TODO @Flom Ouvrir tuto des mails
    }

    private void openTutorialNotes() {
        //TODO @Flom Ouvrir tuto des notes
    }

    private void openTutorialResults() {
        //TODO @Flom Ouvrir tuto des résultats
    }

    /**
     * Change the language of the app depending on a language in parameter
     *
     * @param resources The resources used to change the language
     * @param language  The language we want the app to display
     */
    public static void setLocale(Resources resources, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings_ui, rootKey);
        }
    }
}
