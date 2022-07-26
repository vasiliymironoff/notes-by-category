package com.example.notesbycategory.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import com.example.notesbycategory.model.Category;
import com.example.notesbycategory.model.Note;

@Database(entities = {Note.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NotesDAO getNotesDAO();
    public abstract CategoryDAO getCategoryDAO();
}
