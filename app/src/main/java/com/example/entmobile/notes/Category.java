package com.example.entmobile.notes;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Category {

    private String name;
    private boolean displayed = false;

    public Category(String name, Boolean isDisplayed) {

        this.name = name;
        this.displayed = isDisplayed;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
}
