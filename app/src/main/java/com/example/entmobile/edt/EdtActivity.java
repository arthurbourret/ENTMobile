package com.example.entmobile.edt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.entmobile.R;
import com.example.entmobile.edt.ui.main.EdtFragment;

public class EdtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edt_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, EdtFragment.newInstance())
                    .commitNow();
        }
    }
}