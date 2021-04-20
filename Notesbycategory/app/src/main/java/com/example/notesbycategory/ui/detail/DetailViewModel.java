package com.example.notesbycategory.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import com.example.notesbycategory.App;
import com.example.notesbycategory.model.Category;

import java.util.List;

public class DetailViewModel extends ViewModel {
    int count = PreferenceManager.getDefaultSharedPreferences(App.getInstance().getApplicationContext()).getInt("count", 4);
    LiveData<List<Category>> category;

    public DetailViewModel(){
        category = App.getInstance().getCategoryDAO().getCategory();
    }
}
