package com.example.entmobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.entmobile.edt.EdtActivity;

public class MainMenu extends AppCompatActivity {

    private final static String Url_ENT = "http://mon-ent-etudiant.univ-lemans.fr/fr/index.html"; //String containing the URL to the ENT website
    private final static String Url_UMTICE = "http://umtice.univ-lemans.fr/my/"; //String containing the URL to the UMTICE website

    private Button button_log_off; //Button used to launch the "openLogInMenu()" method.
    private Button button_umtice; //Button used to launch the "openENTinBrowser()" method.
    private Button button_ent; //Button used to launch the "openUMTICEinBrowser()" method.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Finds the object's IDs and initializes local variables
        button_log_off = findViewById(R.id.button_log_off);
        Button button_settings = findViewById(R.id.button_settings);
        Button button_shedule = findViewById(R.id.button_schedule);
        Button button_mails = findViewById(R.id.button_mails);
        Button button_notes = findViewById(R.id.button_notes);
        Button button_results = findViewById(R.id.button_results);
        button_umtice = findViewById(R.id.button_umtice);
        button_ent = findViewById(R.id.button_ent);

        //Set a listener on the Log-Off button
        button_log_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogInMenu();
            }
        });

        //Set a listener on the Settings button
        button_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        //Set a listener on the Schedule button
        button_shedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSchedule();
            }
        });

        //Set a listener on the Mail button
        button_mails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMails();
            }
        });

        //Set a listener on the Notes button
        button_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotes();
            }
        });

        //Set a listener on the Results button
        button_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openResults();
            }
        });

        //Set a listener on the ENT button
        button_umtice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUMTICEinBrowser();
            }
        });

        //Set a listener on the UMTICE button
        button_ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openENTinBrowser();
            }
        });
    }

    /**
     * Method used to open the Login page.
     */
    private void openLogInMenu() {
        startActivity(new Intent(this, Login.class)); //Starts a new activity with the intent
    }

    /**
     * Method used to open the Settings page.
     */
    private void openSettings() {
        notImplemented();
    }

    /**
     * Method used to open the Schedule page
     */
    private void openSchedule() {
        startActivity(new Intent(this, EdtActivity.class));
    }

    /**
     * Method used to open the Mail page
     */
    private void openMails() {
        notImplemented();
    }

    /**
     * Method used to open the Note page
     */
    private void openNotes() {
        notImplemented();
    }

    /**
     * Method used to open the Results page
     */
    private void openResults() {
        notImplemented();
    }

    /**
     * Method used to open a browser intent to the ENT website.
     */
    private void openENTinBrowser() {
        openInBrowser(Url_ENT);
    }

    /**
     * Method used to open a browser intent to the UMTICE website.
     */
    private void openUMTICEinBrowser() {
        openInBrowser(Url_UMTICE);
    }

    /**
     * This method takes an url and open it in a web browser
     * @param url The string url of the web page
     */
    private void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW); //Creates a new intent of type Action_View
        intent.setData(Uri.parse(url)); //Set the intent's data to the parsed UMTICE URL
        startActivity(intent); //Starts a new activity with the intent
    }

    /**
     * To remove at the end of the project
     * Is a toast signaling a part not implemented yet
     */
    private void notImplemented() {
        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
    }

}