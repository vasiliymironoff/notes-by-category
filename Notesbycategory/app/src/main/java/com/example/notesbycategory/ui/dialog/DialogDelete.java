package com.example.notesbycategory.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.notesbycategory.App;
import com.example.notesbycategory.R;
import com.example.notesbycategory.model.Note;
import com.google.android.material.snackbar.Snackbar;

public class DialogDelete extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext()).setTitle("Удалить").setMessage("Вы уверены, что хотите удалить эту заметку?").setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Note note = App.getInstance().getNotesDAO().getNote(getArguments().getInt("id"));
                App.getInstance().getNotesDAO().deleteNoteById(getArguments().getInt("id"));

                Snackbar.make(getActivity().findViewById(R.id.constaint), "Заметка удалена", Snackbar.LENGTH_LONG)
                        .setAction("Отмена", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                App.getInstance().getNotesDAO().insert(note);
                            }
                        }).show();
            }
        }).setNegativeButton("Отмена", (dialog, which)->{}).create();

    }
}
