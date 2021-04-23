package com.example.notesbycategory.ui.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesbycategory.App;
import com.example.notesbycategory.R;
import com.example.notesbycategory.databinding.ActivityDetailBinding;
import com.example.notesbycategory.model.Category;
import com.example.notesbycategory.model.Note;
import com.example.notesbycategory.ui.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final int REQUEST_DETAIL = 938;

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";

    public static final String EXTRA_SNACKBAR = "EXTRA_SNACKBAR";

    private Note startNote;
    DetailViewModel model;
    ActivityDetailBinding binding;

    Spinner spinner;
    List<String> titles;
    ArrayAdapter<String> spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(DetailViewModel.class);
        binding.setModel(model);

        initToolbar();

        initNote();

        initSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case android.R.id.home:

                intent.putExtra(EXTRA_SNACKBAR, 0);

                setResult(REQUEST_DETAIL, intent);
                finish();
                break;
            case R.id.save:
                if (model.note.get().getText() == null) {
                    Toast.makeText(this, "Вы ничего не написали", Toast.LENGTH_SHORT).show();
                } else {
                    if(model.note.get().getText().trim().equals("")){
                        Toast.makeText(this, "Нельзя сохранить строку состоящую из пробелов", Toast.LENGTH_SHORT).show();
                    }else {
                        Note note = model.note.get();
                        note.setTime(System.currentTimeMillis());
                        note.setCategory(spinner.getSelectedItemPosition());
                        intent = new Intent();

                        if (getIntent().getLongExtra(EXTRA_ID, -1) != -1) {
                            App.getInstance().getNotesDAO().update(note);

                            if(note.getCategory() == startNote.getCategory() && note.getText().equals(startNote.getText())){
                                intent.putExtra(EXTRA_SNACKBAR, 0);
                            }else {
                                intent.putExtra(EXTRA_SNACKBAR, 2);
                            }

                        } else {
                            App.getInstance().getNotesDAO().insert(note);
                            intent.putExtra(EXTRA_SNACKBAR, 1);
                        }
                        setResult(REQUEST_DETAIL, intent);
                        finish();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Activity caller, long id, int category) {
        Intent intent = new Intent(caller, DetailActivity.class);
        intent.putExtra(EXTRA_CATEGORY, category);

        if (id != -1) {
            intent.putExtra(EXTRA_ID, id);
        }
        caller.startActivityForResult(intent, REQUEST_DETAIL);
    }

    public void initToolbar() {

        Toolbar toolbar = binding.getRoot().findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void initNote() {
        if (getIntent().getLongExtra(EXTRA_ID, -1) == -1) {
            getSupportActionBar().setTitle("Новая заметка");
            model.note.set(new Note());
            int nCategory = getIntent().getIntExtra(EXTRA_CATEGORY, -1);
            model.note.get().setCategory(nCategory);

        } else {
            getSupportActionBar().setTitle("Изменить заметку");
            model.note.set(App.getInstance().getNotesDAO().getNote(getIntent().getLongExtra(EXTRA_ID, -1)));
            startNote = App.getInstance().getNotesDAO().getNote(getIntent().getLongExtra(EXTRA_ID, -1));
        }
    }

    public void initSpinner() {
        spinner = binding.getRoot().findViewById(R.id.spinner);

        titles = model.loadNameCategory();
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.item_simple_spinner, titles);
        spinner.setAdapter(spinnerAdapter);
        spinner.setPrompt("Категории");
        spinner.setSelection(model.note.get().getCategory());
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra(EXTRA_SNACKBAR, 0);
        setResult(REQUEST_DETAIL, intent);
        finish();
    }
}