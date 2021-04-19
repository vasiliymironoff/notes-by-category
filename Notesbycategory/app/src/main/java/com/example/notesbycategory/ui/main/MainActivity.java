package com.example.notesbycategory.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.notesbycategory.App;
import com.example.notesbycategory.R;
import com.example.notesbycategory.model.Category;
import com.example.notesbycategory.ui.detail.DetailActivity;
import com.example.notesbycategory.ui.preference.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    MainViewModel model;
    MainPagerAdapter adapter;

    ViewPager pager;
    TabLayout tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("theme", false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pref.registerOnSharedPreferenceChangeListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Список дел");


        pager = findViewById(R.id.viewpager);
        tablayout = findViewById(R.id.tabLayout);

        model = new ViewModelProvider(this.getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(MainViewModel.class);


        adapter = new MainPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        model.category.observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                MainPagerAdapter.title = categories;
                adapter.notifyDataSetChanged();
            }
        });

        model.count.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                MainPagerAdapter.ncount = integer;
                adapter.notifyDataSetChanged();
            }
        });


        pager.setAdapter(adapter);
        tablayout.setupWithViewPager(pager);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> DetailActivity.start(MainActivity.this, -1, pager.getCurrentItem()));

        model.getStartDetail().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {

                DetailActivity.start(MainActivity.this, aLong, pager.getCurrentItem());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.deleteByCategory:
                App.getInstance().getNotesDAO().deleteAllByCategory(pager.getCurrentItem());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("theme")) {
            if (sharedPreferences.getBoolean("theme", false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
        if (key.equals("count")) {
            model.restartDataInModel();
            adapter.notifyDataSetChanged();
        }
    }

}