package com.example.notesbycategory.ui.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesbycategory.App;
import com.example.notesbycategory.R;
import com.example.notesbycategory.model.Category;
import com.example.notesbycategory.model.Note;
import com.example.notesbycategory.ui.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";
    Note note;

    List<Category> categoryForSpinner = new ArrayList<>();
    Spinner spinner;
    EditText editText;

    ArrayAdapter<String> spinnerAdapter;
    DetailViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = findViewById(R.id.edittext);
        spinner = findViewById(R.id.spinner);

        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(DetailViewModel.class);

        List<String> titles = new ArrayList<>();
        model.category.observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryForSpinner.addAll(categories);
                for(Category c: categoryForSpinner) {
                    if (c.getId() < model.count) titles.add(c.getName());
                }
                spinnerAdapter.notifyDataSetChanged();

            }
        });

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titles);
        spinner.setAdapter(spinnerAdapter);
        spinner.setPrompt("Категории");

        if(getIntent().getLongExtra(EXTRA_ID, -1) == -1){
            getSupportActionBar().setTitle("Новая заметка");

            note = new Note();
            note.setCategory(getIntent().getIntExtra(EXTRA_CATEGORY, 0));

        } else {
            getSupportActionBar().setTitle("Изменить заметку");

            note = App.getInstance().getNotesDAO().getNote(getIntent().getLongExtra(EXTRA_ID, -1));
            editText.setText(note.getText());
        }
        spinner.setSelection(note.getCategory());


    }

    public static void start(Activity caller, long id, int category){
        Intent intent = new Intent(caller, DetailActivity.class);
        intent.putExtra(EXTRA_CATEGORY, category);
        if(id != -1){
            intent.putExtra(EXTRA_ID, id);
        }
        caller.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_note , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                if(editText.getText().toString().equals("")){
                    Toast.makeText(this, "Вы ничего не написали",Toast.LENGTH_SHORT).show();
                } else {
                    note.setText(editText.getText().toString());
                    note.setTime(System.currentTimeMillis());

                    note.setCategory(spinner.getSelectedItemPosition());
                    if (getIntent().getLongExtra(EXTRA_ID, -1) != -1) {
                        App.getInstance().getNotesDAO().update(note);
                    } else {
                        App.getInstance().getNotesDAO().insert(note);
                    }
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}