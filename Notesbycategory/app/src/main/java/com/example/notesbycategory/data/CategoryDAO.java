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

    @Query(" SELECT * FROM Category")
    List<Category> getCategoryNoLiveData();

    @Query(" SELECT * FROM Category WHERE id= :id")
    Category getCategoryById(int id);
    @Insert
    void insert(Category category);

    @Query("DELETE FROM category")
    void deleteAll();

    @Query("DELETE FROM category WHERE id = :id")
    void deleteCategoryById(int id);
}
