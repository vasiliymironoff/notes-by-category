package com.example.notesbycategory;

import android.app.Application;

import androidx.preference.PreferenceManager;
import androidx.room.Room;

import com.example.notesbycategory.data.AppDatabase;
import com.example.notesbycategory.data.CategoryDAO;
import com.example.notesbycategory.data.NotesDAO;
import com.example.notesbycategory.model.Category;

public class App extends Application {

    public static App instance;
    AppDatabase database;
    NotesDAO notesDAO;
    CategoryDAO categoryDAO;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;


        database = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class, "db-name")
                    .allowMainThreadQueries()
                    .build();

        notesDAO = database.getNotesDAO();
        categoryDAO = database.getCategoryDAO();
        
    }

    public static App getInstance() {
        return instance;
    }

    public NotesDAO getNotesDAO(){
        return notesDAO;
    }

    public CategoryDAO getCategoryDAO(){
        return categoryDAO;
    }
}
