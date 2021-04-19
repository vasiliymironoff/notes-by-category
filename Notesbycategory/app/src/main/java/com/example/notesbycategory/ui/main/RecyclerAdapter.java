package com.example.notesbycategory.ui.main;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.notesbycategory.App;
import com.example.notesbycategory.R;
import com.example.notesbycategory.model.Note;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerNoteHolder>{

    SortedList<Note> notes = new SortedList<>(Note.class, new SortedList.Callback<Note>() {
        @Override
        public int compare(Note o1, Note o2) {
            if(o1.isDone() && !o2.isDone()){
                return 1;
            }
            if(!o1.isDone() && o2.isDone()){
                return -1;
            }
            return (int)(o2.getTime() - o1.getTime());
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(Note item1, Note item2) {
            return item1.getMid() == item2.getMid();
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }
    });

    MainViewModel model;
    public RecyclerAdapter(MainViewModel model){
        this.model = model;
    }

    @NonNull
    @Override
    public RecyclerNoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerNoteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false), model);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerNoteHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> list){
        notes.replaceAll(list);
    }




    public static class RecyclerNoteHolder extends RecyclerView.ViewHolder {
        CheckBox box;
        TextView text;
        ImageButton delete;
        MainViewModel model;
        Note note;
        private boolean appearanceChooseBox = true;
        public RecyclerNoteHolder(@NonNull View itemView, MainViewModel model) {
            super(itemView);

            this.model = model;
            box = itemView.findViewById(R.id.check);
            text = itemView.findViewById(R.id.text);
            delete = itemView.findViewById(R.id.delete);


            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(appearanceChooseBox){
                        note.setDone(box.isChecked());
                        App.getInstance().getNotesDAO().update(note);
                        updateStrokeOut();
                    }

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getInstance().getNotesDAO().delete(note);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Setup");
                    model.setupNoteForStartDetail(note.getMid());
                }

            });
        }

        public void bind(Note note){
            this.note = note;
            appearanceChooseBox = false;
            box.setChecked(note.isDone());
            appearanceChooseBox = true;
            text.setText(note.getText());
            updateStrokeOut();
        }

        private void updateStrokeOut() {
            if (note.isDone()) {
                text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                text.setPaintFlags(text.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
