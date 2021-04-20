package com.example.notesbycategory.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.notesbycategory.App;

public class DialogDelete extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext()).setTitle("Удалить").setMessage("Вы уверены, что хотите удалить эту заметку").setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.getInstance().getNotesDAO().deleteNoteById(getArguments().getInt("id"));
            }
        }).setNegativeButton("Отмена", (dialog, which)->{}).create();

    }
}
