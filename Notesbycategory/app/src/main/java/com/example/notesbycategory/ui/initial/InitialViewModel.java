package com.example.notesbycategory.ui.initial;

import androidx.cardview.widget.CardView;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import com.example.notesbycategory.App;
import com.example.notesbycategory.model.Category;

import java.util.ArrayList;
import java.util.List;

public class InitialViewModel extends ViewModel {
    public ObservableInt count = new ObservableInt(10);
    ObservableList<Category> category = new ObservableArrayList<>();

    public void initModelForRename(){
        category.clear();
        List<Category> categoryList= App.getInstance().getCategoryDAO().getCategoryNoLiveData();
        category.addAll(categoryList);
    }


}
