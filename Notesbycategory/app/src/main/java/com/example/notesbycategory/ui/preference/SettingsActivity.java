package com.example.notesbycategory.ui.preference;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.example.notesbycategory.App;
import com.example.notesbycategory.R;
import com.example.notesbycategory.ui.initial.InitialActivity;

import java.util.prefs.PreferenceChangeEvent;

public class SettingsActivity extends AppCompatActivity {

    SettingsViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(SettingsViewModel.class);
        model.getReset().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    App.getInstance().getCategoryDAO().deleteAll();
                    App.getInstance().getNotesDAO().deleteAll();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("first", true).apply();

                    Intent intent = new Intent(SettingsActivity.this, InitialActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            }
        });

        model.getRename().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("rename", true).apply();
                    Intent intent = new Intent(SettingsActivity.this, InitialActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            }
        });
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        SettingsViewModel model;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            model = new ViewModelProvider(getActivity(), new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(SettingsViewModel.class);

            Preference rename = findPreference("rename");

            rename.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    model.setRename(true);
                    return false;
                }
            });

            Preference deleteNote = findPreference("deleteNotes");
            deleteNote.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    App.getInstance().getNotesDAO().deleteAll();
                    Toast.makeText(getContext(), "Заметки удалены", Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            Preference reset = findPreference("reset");

            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    model.setReset(true);
                    return false;
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}