package com.example.notesbycategory.ui.preference;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    MutableLiveData<Boolean> reset = new MutableLiveData<>();
    MutableLiveData<Boolean> rename = new MutableLiveData<>();

    LiveData<Boolean> getReset(){
        return reset;
    }

    void setReset(boolean b){
        reset.setValue(b);
    }

    LiveData<Boolean> getRename(){
        return rename;
    }

    void setRename(boolean b){
        rename.setValue(b);
    }
}
