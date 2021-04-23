package com.example.notesbycategory.ui.main;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notesbycategory.R;
import com.example.notesbycategory.databinding.FragmentNotesBinding;
import com.example.notesbycategory.model.Note;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";
    private int category;
    RecyclerAdapter adapter;
    MainViewModel model;
    FragmentNotesBinding binding;
    public NotesFragment() {

    }

    public static NotesFragment newInstance(int category) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getInt(EXTRA_CATEGORY);
        }

        model = new ViewModelProvider(getActivity().getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(MainViewModel.class);
        adapter = new RecyclerAdapter(model);
        model.noteByCategory.get(category).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                    ObservableList<Note> observableList = new ObservableArrayList<>();
                    observableList.addAll(notes);
                    model.notes.set(category, observableList);

                    ObservableBoolean bool = new ObservableBoolean();
                    bool.set(notes.isEmpty());
                    model.isEmpty.set(category, bool);

                    binding.setIsEmpty(model.isEmpty.get(category));

                    adapter.setNotes(observableList);
                    adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_notes, container, false);
        RecyclerView recycler = binding.getRoot().findViewById(R.id.recycler);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.setIsEmpty(model.isEmpty.get(category));
        return binding.getRoot();
    }


}