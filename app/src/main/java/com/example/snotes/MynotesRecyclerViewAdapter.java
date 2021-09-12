package com.example.snotes;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MynotesRecyclerViewAdapter extends RecyclerView.Adapter<MynotesRecyclerViewAdapter.ViewHolder> {

    private final List<NotesContent.NoteItem> mValues;
    private CountDownLatch m_deleteSyncLatch;
    private  OnNoteListener mOnNoteListener;

    public MynotesRecyclerViewAdapter(List<NotesContent.NoteItem> items, OnNoteListener onNoteListener) {
        mValues = items;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
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

        holder.m_deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getBindingAdapterPosition();
                m_deleteSyncLatch = new CountDownLatch(1);

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        NameDBSingleton.GetDB().getRepoDao().DeleteEntity(
                                mValues.get(pos).title,
                                mValues.get(pos).content
                        );

                        m_deleteSyncLatch.countDown();
                    }
                });

                try {
                    m_deleteSyncLatch.await();
                }
                catch (InterruptedException e) {}

                mValues.remove(pos);
                notifyItemRemoved(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public  final Button m_deleteButton;
        public NotesContent.NoteItem mItem;
        OnNoteListener onNoteListener;

        public ViewHolder(View view, OnNoteListener onNoteListener) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            m_deleteButton = (Button) view.findViewById(R.id.delete_btn);
            this.onNoteListener = onNoteListener;
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getBindingAdapterPosition());
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