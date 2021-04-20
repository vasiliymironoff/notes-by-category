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
import com.example.notesbycategory.model.Category;
import com.example.notesbycategory.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    int count;
    List<LiveData<List<Note>>> noteByCategory = new ArrayList<>();

    LiveData<List<Category>> category = App.getInstance().getCategoryDAO().getCategory();

    MutableLiveData<Long> startDetail = new MutableLiveData<>();
    MutableLiveData<Long> startDeleteDialog = new MutableLiveData<>();


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

    public LiveData<Long> getStartDetail() {
        return startDetail;
    }

    public void setupNoteForStartDeleteDialog(long id){
        startDeleteDialog.setValue(id);
    }

    public LiveData<Long> getDeleteDialog(){
        return startDeleteDialog;
    }

}
