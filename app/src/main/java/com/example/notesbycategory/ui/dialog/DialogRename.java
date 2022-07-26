package com.example.notesbycategory.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.notesbycategory.App;
import com.example.notesbycategory.R;
import com.example.notesbycategory.model.Category;

public class DialogRename extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Переименование категории")
                .setView(R.layout.edit_rename)
                .setMessage("Введите новое имя для категории \"" + App.getInstance().getCategoryDAO().getCategoryById(getArguments().getInt("id")).getName() +"\"")
                .setPositiveButton("Переименовать", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = getDialog().findViewById(R.id.rename);
                        Category category = App.getInstance().getCategoryDAO().getCategoryById(getArguments().getInt("id"));
                        String newTitle = editText.getText().toString();
                        category.setName(newTitle);
                        App.getInstance().getCategoryDAO().updateCategory(category);

                    }
                })
                .setNegativeButton("Отмена", (dialog, which) -> {});


        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((EditText)getDialog().findViewById(R.id.rename)).setText(App.getInstance().getCategoryDAO().getCategoryById(getArguments().getInt("id")).getName());
    }
}
