package com.example.notesbycategory.ui.initial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;

import com.example.notesbycategory.App;
import com.example.notesbycategory.R;
import com.example.notesbycategory.model.Category;
import com.example.notesbycategory.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class InitialActivity extends AppCompatActivity {

    InitialViewModel model;
    RecyclerView recyclerView;
    InitialAdapter adapter;
    SeekBar seekBar;

    com.example.notesbycategory.databinding.ActivityInitialBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startMainActivity();
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_initial);
        initToolbar();
        model = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(InitialViewModel.class);
        binding.setModel(model);
        initRecycler();
        seekBar = findViewById(R.id.seekbar);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void initToolbar(){
        Toolbar toolbar = binding.getRoot().findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Начальная настройка");
    }

    private void initRecycler(){
        adapter = new InitialAdapter(model);
        recyclerView = binding.getRoot().findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.initial, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.accept){
            //Сохраняем изменения в базу данных + переходим в MainActivity
            List<Category> categoryList = model.category;
            for(int i=0; i<model.count.get(); i++){
                Category c = model.category.get(i);

                if(c.getName().equals("")){
                    c.setName("Категория " + (c.getId()+1));
                }

                if(App.getInstance().getCategoryDAO().getCategoryById((int)c.getId()) == null){
                    App.getInstance().getCategoryDAO().insert(c);
                } else {
                    App.getInstance().getCategoryDAO().updateCategory(c);
                }
            }


            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("first",false).apply();
            startMainActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    public void startMainActivity(){
        if (!PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("first", true)){
            Intent intent = new Intent(InitialActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}