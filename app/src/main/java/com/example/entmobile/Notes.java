package com.example.entmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Notes extends AppCompatActivity {

    TextView notes_counter;
    ImageButton notes_settings_button;
    ImageButton add_note_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notes_counter = findViewById(R.id.notes_counter);
        add_note_button = findViewById(R.id.add_note_button);
        notes_settings_button = findViewById(R.id.notes_settings_button);

        setupButtonsFeedback();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupButtonsFeedback() {
        add_note_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    add_note_button.setBackgroundResource(R.drawable.rounded_corners_pressed);
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    add_note_button.setBackgroundResource(R.drawable.rounded_corners);
                    return true;
                }
                return false;
            }
        });

        notes_settings_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    notes_settings_button.setBackgroundResource(R.drawable.rounded_corners_pressed);
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    notes_settings_button.setBackgroundResource(R.drawable.rounded_corners);
                    return true;
                }
                return false;
            }
        });
    }
}