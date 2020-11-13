package com.example.entmobile.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.entmobile.R;

public class Login extends AppCompatActivity {

    private Button button_login; // Button used to launch the "openMainMenu()" method.
    private Button button_createAccount; // Button used to launch the "openCreateAccountMenu()" method.
    private EditText TextIL_username; // Button used to launch the "openMainMenu()" method.
    private EditText TextIL_password; // Button used to launch the "openMainMenu()" method.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Finds the object's IDs and initializes local variables
        button_login = findViewById(R.id.button_login);
        button_createAccount = findViewById(R.id.button_createAccount);
        TextIL_username = findViewById(R.id.TextIL_username);
        TextIL_password = findViewById(R.id.TextIL_password);

        // Set a listener on the Log-In button
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_in();
            }
        });

        // Set a listener on the Create Account button
        button_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openCreateAccountMenu();
            }
        });}

    /**
     * Method used to open check the log-in credentials and opens the Main Menu if they are correct.
     */
    private void log_in() {
        String username = TextIL_username.getText().toString(); //Gets the username typed by the user in the EditText
        String password = TextIL_password.getText().toString(); //Gets the password typed by the user in the EditText

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); //Initializes the SharedPreferences

        String user_username = preferences.getString("username", ""); //Gets the username from the SharedPreferences
        String user_pass = preferences.getString("password", ""); //Gets the password from the SharedPreferences

        // Compares the log-in credentials entered with the ones saved
        if ((username.equals(user_username)) && (!username.equals(""))) { //if the username is correct
            if (password.equals(user_pass) && (!password.equals(""))) { //if the password is correct
                openMainMenu();
            }
            else { //If the password isn't correct
                TextIL_password.setError(getString(R.string.wrong_pass_error)); //Shows an error message indicating that the password isn't correct
            }
        }
        else { //If the username isn't correct
            TextIL_username.setError(getString(R.string.wrong_username_error)); //Shows an error message indicating that the username isn't correct
        }
    }

    /**
     * Method used to open the Main Menu.
     */
    private void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class); //Prepares a new activity
        startActivity(intent); //Opens the new activity
    }

    /**
     * Method used to open the CreateAccount Menu.
     */
    private void openCreateAccountMenu() {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

}