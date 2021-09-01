package com.example.snotes;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public class MynotesRecyclerViewAdapter extends RecyclerView.Adapter<MynotesRecyclerViewAdapter.ViewHolder> {

    private final List<NotesContent.NoteItem> mValues;
    private  OnNoteListener mOnNoteListener;

    public MynotesRecyclerViewAdapter(List<NotesContent.NoteItem> items, OnNoteListener onNoteListener) {
        mValues = items;
        this.mOnNoteListener = onNoteListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).title);
        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public NotesContent.NoteItem mItem;
        OnNoteListener onNoteListener;

        public ViewHolder(View view, OnNoteListener onNoteListener) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            this.onNoteListener = onNoteListener;
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
    public void sort_names(int mode){
        if(mode == 0){
            Collections.sort(mValues, NotesContent.NoteItem.NoteTitle);
        }
        else{
            Collections.sort(mValues, NotesContent.NoteItem.NoteRepo);
        }
    }
}