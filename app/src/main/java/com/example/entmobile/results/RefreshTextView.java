package com.example.entmobile.results;

import android.widget.EditText;
import android.widget.TextView;

public class RefreshTextView {

    private float coef_total;
    private float note_total;

    public void refresh1coef1grade(TextView note, TextView coef, EditText note1, EditText coef1) {
        if (!coef1.getText().toString().equals("")) {
            coef_total = Float.parseFloat(coef1.getText().toString());
            coef.setTextSize(20);
            coef.setText(String.valueOf(coef_total));
            if (!note1.getText().toString().equals("")) {
                note_total = ((Float.parseFloat(note1.getText().toString()) * Float.parseFloat(coef1.getText().toString()))) / coef_total;
                note.setTextSize(20);
                note.setText(String.valueOf(Math.floor(note_total*100)/100));
            }
        } else {
            coef.setTextSize(14);
            coef.setText("Valeur\nmanquante");
            note.setTextSize(14);
            note.setText("Valeur\nmanquante");
        }
    }

    public void refresh2coef2grade(TextView note, TextView coef, EditText note1, EditText coef1, EditText note2, EditText coef2) {
        if (!coef1.getText().toString().equals("") && !coef2.getText().toString().equals("")) {
            coef_total = Float.parseFloat(coef1.getText().toString()) + Float.parseFloat(coef2.getText().toString());
            coef.setTextSize(20);
            coef.setText(String.valueOf(coef_total));
            if (!note1.getText().toString().equals("") && !note2.getText().toString().equals("")) {
                note_total = ((Float.parseFloat(note1.getText().toString()) * Float.parseFloat(coef1.getText().toString())) + (Float.parseFloat(note2.getText().toString()) * Float.parseFloat(coef2.getText().toString()))) / coef_total;
                note.setTextSize(20);
                note.setText(String.valueOf(Math.floor(note_total*100)/100));
            }
        } else {
            coef.setTextSize(14);
            coef.setText("Valeur\nmanquante");
            note.setTextSize(14);
            note.setText("Valeur\nmanquante");
        }
    }

    public void refresh3coef3grade(TextView note, TextView coef, EditText note1, EditText coef1, EditText note2, EditText coef2, EditText note3, EditText coef3) {
        if (!coef1.getText().toString().equals("") && !coef2.getText().toString().equals("") && !coef3.getText().toString().equals("")) {
            coef_total = Float.parseFloat(coef1.getText().toString()) + Float.parseFloat(coef2.getText().toString()) + Float.parseFloat(coef3.getText().toString());
            coef.setTextSize(20);
            coef.setText(String.valueOf(coef_total));
            if (!note1.getText().toString().equals("") && !note2.getText().toString().equals("") && !note3.getText().toString().equals("")) {
                note_total = ((Float.parseFloat(note1.getText().toString()) * Float.parseFloat(coef1.getText().toString())) + (Float.parseFloat(note2.getText().toString()) * Float.parseFloat(coef2.getText().toString())) + (Float.parseFloat(note3.getText().toString()) * Float.parseFloat(coef3.getText().toString()))) / coef_total;
                note.setTextSize(20);
                note.setText(String.valueOf(Math.floor(note_total*100)/100));
            }
        } else {
            coef.setTextSize(14);
            coef.setText("Valeur\nmanquante");
            note.setTextSize(14);
            note.setText("Valeur\nmanquante");
        }
    }

}
