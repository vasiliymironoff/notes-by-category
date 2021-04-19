package com.example.notesbycategory.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notesbycategory.model.Category;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Update
    void updateCategory(Category category);

    @Query(" SELECT * FROM Category ")
    LiveData<List<Category>> getCategory();

    @Insert
    void insert(Category category);
}
