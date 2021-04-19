package com.example.notesbycategory.ui.initial;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notesbycategory.model.Category;

import java.util.ArrayList;
import java.util.List;

public class InitialViewModel extends ViewModel {
    MutableLiveData<Integer> count = new MutableLiveData<Integer>();


    public void setCount(int newCount){
        count.postValue(newCount);
    }
    public LiveData<Integer> getCount(){
        return count;
    }


}
