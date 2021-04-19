package com.example.notesbycategory.ui.initial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        startMainActivity();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_initial);



        initToolbar();
        model = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(InitialViewModel.class);
        initRecycler();
        seekBar = findViewById(R.id.seekbar);
        model.setCount(seekBar.getProgress());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                model.setCount(seekBar.getProgress());
            }
        });

        model.count.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                adapter.count = integer;
                adapter.editTexts.clear();
                adapter.notifyDataSetChanged();

            }
        });


    }
    private void initToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Начальная настройка");
    }

    private void initRecycler(){
        adapter = new InitialAdapter();
        recyclerView = findViewById(R.id.recycler);
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
            List<String> title = new ArrayList<>();
            int count = 0;
            for(String s: adapter.getStringList()){
                count++;
                if(s.equals("")){
                    title.add("Категория " + count);
                } else {
                    title.add(s);
                }
            }
            for(int i=0; i<count; i++){
                App.getInstance().getCategoryDAO().insert(new Category(i, title.get(i)));
            }

            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("first",false).apply();
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("count", count).apply();
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