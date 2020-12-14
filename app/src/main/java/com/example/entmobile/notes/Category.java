package com.example.entmobile.notes;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Category {

    private String name;
    private boolean displayed = false;
    private int color;

    public Category(String name, Boolean isDisplayed, int color) {

        this.name = name;
        this.displayed = isDisplayed;
        this.color = color;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color){
        this.color = color;
    }


}
