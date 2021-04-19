package com.example.notesbycategory.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    private long mid;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "done")
    private boolean done;

    @ColumnInfo(name="time")
    private long time;

    @ColumnInfo(name = "category")
    private int category;

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return mid == note.mid &&
                done == note.done &&
                time == note.time &&
                category == note.category &&
                Objects.equals(text, note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mid, text, done, time, category);
    }
}
