package com.example.notesbycategory.ui.initial;

import android.icu.text.MessagePattern;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesbycategory.R;
import com.example.notesbycategory.model.Category;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class InitialAdapter extends RecyclerView.Adapter<InitialAdapter.HolderCategory> implements ObservableForEditText {

    public int count;
    public List<EditText> editTexts = new ArrayList<>();


    @NonNull
    @Override
    public HolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderCategory(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_editcategory, parent, false), this);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCategory holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public void addEditText(EditText editText) {
        editTexts.add(editText);
    }

    @Override
    public List<String> getStringList() {
        List<String> title = new ArrayList<>();
        for(EditText editText: editTexts){
            title.add(editText.getText().toString());
        }
        return title;
    }

    @Override
    public void removeEditText(EditText editText) {
        editTexts.remove(editText);
    }


    public static class HolderCategory extends RecyclerView.ViewHolder {
        TextInputEditText editText;
        TextInputLayout layout;
        int positionInAdapter;
        public HolderCategory(@NonNull View itemView, ObservableForEditText observable) {
            super(itemView);
            editText = itemView.findViewById(R.id.edittext);
            observable.addEditText(editText);
            layout = itemView.findViewById(R.id.textinput_counter);
        }
        public void bind(int position){
            positionInAdapter = position;
            layout.setHint("Категория " + (position+1));
        }

    }
}
