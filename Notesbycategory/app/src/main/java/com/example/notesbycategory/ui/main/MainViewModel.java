package com.example.notesbycategory.ui.main;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.notesbycategory.App;
import com.example.notesbycategory.SingleLiveEvent;
import com.example.notesbycategory.model.Category;
import com.example.notesbycategory.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    int count;

    public List<ObservableList<Note>> notes = new ArrayList<>();
    public List<ObservableBoolean> isEmpty = new ArrayList<ObservableBoolean>();

    List<LiveData<List<Note>>> noteByCategory = new ArrayList<>();

    LiveData<List<Category>> category = App.getInstance().getCategoryDAO().getCategory();


    SingleLiveEvent<Long> startDetail = new SingleLiveEvent<>();
    SingleLiveEvent<Long> startDeleteDialog = new SingleLiveEvent<>();


    public MainViewModel(){
        count = App.getInstance().getCategoryDAO().getCategoryNoLiveData().size();
        for(int i=0; i<count; i++){
            noteByCategory.add(loadNotesByCategory(i));
            notes.add(new ObservableArrayList<>());
            ObservableBoolean observableBoolean = new ObservableBoolean();
            //observableBoolean.set(App.getInstance().getNotesDAO().loadAllNoteByIdNotLiveData(i).isEmpty());
            isEmpty.add(observableBoolean);
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
