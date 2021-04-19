package com.example.notesbycategory.ui.initial;

import android.widget.EditText;

import java.util.List;

public interface ObservableForEditText {
    void addEditText(EditText editText);
    List<String> getStringList();
    void removeEditText(EditText editText);
}
