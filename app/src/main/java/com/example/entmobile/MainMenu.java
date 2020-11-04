package com.example.entmobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    private final String Url_ENT = "http://mon-ent-etudiant.univ-lemans.fr/fr/index.html"; //String containing the URL to the ENT website
    private final String Url_UMTICE = "http://umtice.univ-lemans.fr/my/"; //String containing the URL to the UMTICE website

    private Button button_log_off; //Button used to launch the "openLogInMenu()" method.
    private Button button_umtice; //Button used to launch the "openENTinBrowser()" method.
    private Button button_ent; //Button used to launch the "openUMTICEinBrowser()" method.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Finds the object's IDs and initializes local variables
        button_log_off = findViewById(R.id.button_log_off);
        button_umtice = findViewById(R.id.button_umtice);
        button_ent = findViewById(R.id.button_ent);

        //Set a listener on the Log-Off button
        button_log_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogInMenu();
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
     * Method used to open the Main Menu.
     */
    private void openLogInMenu() {
        Intent intent = new Intent(this, Login.class); //Creates a new Login intent
        startActivity(intent); //Starts a new activity with the intent
    }

    /**
     * Method used to open a browser intent to the ENT website.
     */
    private void openENTinBrowser() {
        Intent i = new Intent(Intent.ACTION_VIEW); //Creates a new intent of type Action_View
        i.setData(Uri.parse(Url_ENT)); //Set the intent's data to the parsed ENT URL
        startActivity(i); //Starts a new activity with the intent
    }

    /**
     * Method used to open a browser intent to the UMTICE website.
     */
    private void openUMTICEinBrowser() {
        Intent i = new Intent(Intent.ACTION_VIEW); //Creates a new intent of type Action_View
        i.setData(Uri.parse(Url_UMTICE)); //Set the intent's data to the parsed UMTICE URL
        startActivity(i); //Starts a new activity with the intent
    }
}