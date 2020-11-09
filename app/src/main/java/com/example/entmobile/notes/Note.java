package com.example.entmobile.notes;

public class Note {

    private String category;
    private String title;
    private String content;

    public Note(String newNoteCategory, String newNoteTitle, String newNoteContent) {
        this.category = newNoteCategory;
        this.title = newNoteTitle;
        this.content = newNoteContent;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
