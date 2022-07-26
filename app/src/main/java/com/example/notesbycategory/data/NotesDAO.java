package com.example.notesbycategory.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notesbycategory.model.Note;

import java.util.List;


@Dao
public interface NotesDAO {

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> loadAll();

    @Query("SELECT * FROM Note WHERE category = :category ")
    LiveData<List<Note>> loadAllNoteById(int category);

    @Query("SELECT * FROM Note WHERE category = :category ")
    List<Note> loadAllNoteByIdNotLiveData(int category);

    @Query("SELECT * FROM Note WHERE mid = :id ")
    Note getNote(long id);

    @Query("DELETE FROM Note WHERE category = :category")
    void deleteAllByCategory(int category);

    @Query("DELETE FROM Note WHERE mid=:id")
    void deleteNoteById(int id);

    @Query("DELETE FROM Note")
    void deleteAll();

    @Query("DELETE FROM Note WHERE category= :category and done = :done")
    void deleteNotesByCategoryAndDone(int category, boolean done);
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
