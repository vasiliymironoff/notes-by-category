package com.example.notesbycategory.ui.main;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import com.example.notesbycategory.App;
import com.example.notesbycategory.SingleLiveEvent;
import com.example.notesbycategory.model.Category;
import com.example.notesbycategory.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    int count;
    List<LiveData<List<Note>>> noteByCategory = new ArrayList<>();

    LiveData<List<Category>> category = App.getInstance().getCategoryDAO().getCategory();

    SingleLiveEvent<Long> startDetail = new SingleLiveEvent<>();
    SingleLiveEvent<Long> startDeleteDialog = new SingleLiveEvent<>();


    public MainViewModel(){
        restartDataInModel();
    }

    public void restartDataInModel(){
        count = App.getInstance().getCategoryDAO().getCategoryNoLiveData().size();
        for(int i=0; i<count; i++){
            noteByCategory.add(loadNotesByCategory(i));
        }
    }

    public LiveData<List<Note>> loadNotesByCategory(int category){
        return App.getInstance().getNotesDAO().loadAllNoteById(category);
    }

    public void setupNoteForStartDetail(long id){
        startDetail.setValue(id);
    }

    public SingleLiveEvent<Long> getStartDetail() {
        return startDetail;
    }

    public void setupNoteForStartDeleteDialog(long id){
        startDeleteDialog.setValue(id);
    }

    public SingleLiveEvent<Long> getDeleteDialog(){
        return startDeleteDialog;
    }

}
