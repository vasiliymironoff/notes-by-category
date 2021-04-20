package com.example.notesbycategory.ui.initial;

import android.icu.text.MessagePattern;
import android.renderscript.ScriptGroup;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesbycategory.R;
import com.example.notesbycategory.databinding.ItemEditcategoryBinding;
import com.example.notesbycategory.model.Category;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Handler;

public class InitialAdapter extends RecyclerView.Adapter<InitialAdapter.CategoryHolder> {

    public static InitialViewModel model;


    public InitialAdapter(InitialViewModel model){
        this.model = model;
    }
    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEditcategoryBinding holder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_editcategory, parent, false);

        return new CategoryHolder(holder);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        if(model.category.size() <= position){
            model.category.add(new Category(position, ""));
        }

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return model.count.get();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder{
        ItemEditcategoryBinding binding;
        public CategoryHolder(@NonNull ItemEditcategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.executePendingBindings();
        }
        public void bind(int position){
            binding.setCategory(model.category.get(position));
        }
    }


}
