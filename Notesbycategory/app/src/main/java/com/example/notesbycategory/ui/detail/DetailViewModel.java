package com.example.notesbycategory.ui.detail;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import com.example.notesbycategory.App;
import com.example.notesbycategory.model.Category;
import com.example.notesbycategory.model.Note;

import java.util.ArrayList;
import java.util.List;

public class DetailViewModel extends ViewModel {
    public ObservableField<Note> note = new ObservableField<>();


    public List<String> loadNameCategory(){
        List<Category> category = App.getInstance().getCategoryDAO().getCategoryNoLiveData();
        List<String> titles = new ArrayList<>();
        for(Category c: category){
            titles.add(c.getName());
        }
        return titles;
    }
}