package com.example.snotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RepoDetailsRecyclerViewAdapter extends RecyclerView.Adapter<RepoDetailsRecyclerViewAdapter.ViewHolder> {

    private List<RepoContent.RepoItem> mValues;
    private OnNoteListener mOnNoteListener;

    public RepoDetailsRecyclerViewAdapter(
            List<RepoContent.RepoItem> items, OnNoteListener onNoteListener
    ) {
        mValues = items;
        this.mOnNoteListener = onNoteListener;
    }

    public RepoContent.RepoItem GetValueAt(int index) {
        return mValues.get(index);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_item_frag, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.m_fileNameView.setText(mValues.get(position).fileName);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView m_fileNameView;
        public RepoContent.RepoItem mItem;
        OnNoteListener onNoteListener;

        public ViewHolder(View view, OnNoteListener onNoteListener) {
            super(view);
            mView = view;
            m_fileNameView = (TextView) view.findViewById(R.id.file_name);
            this.onNoteListener = onNoteListener;
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + m_fileNameView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getBindingAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
    public void ChangeValues(List<RepoContent.RepoItem> items) {
        mValues = items;
        notifyDataSetChanged();
    }
}