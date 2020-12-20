package com.shetty.mvvmwithrecycleview;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String title;
    private final String desc;
    private final int priority;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getPriority() {
        return priority;
    }

    public Note(String title, String desc, int priority) {
        this.title = title;
        this.desc = desc;
        this.priority = priority;
    }
}
