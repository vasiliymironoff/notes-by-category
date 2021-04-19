package com.example.notesbycategory.ui.main;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.notesbycategory.App;
import com.example.notesbycategory.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {

    static List<Category> title = new ArrayList<>();
    static int ncount;



    public MainPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return NotesFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return ncount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        for(Category c: title){
            if(c.getId() == position){
                return c.getName();
            }
        }
        return "Пусто";

    }
}
