package com.example.repoMarks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repoMarks.databinding.RepoItemListBinding;

import java.util.List;

public class repoFragment extends Fragment implements RepoDetailsRecyclerViewAdapter.OnNoteListener {
    private RepoItemListBinding binding;
    public static final String TAG = "RESURRECTION";
    private static final String ARG_COLUMN_COUNT = "column-count-2";
    private int mColumnCount = 1;
    private RepoContent repoContent;
    private RepoDetailsRecyclerViewAdapter myAdapter;

    @SuppressWarnings("unused")
    public static repoFragment newInstance(int columnCount) {
        repoFragment fragment = new repoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = RepoItemListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        repoContent = new RepoContent();
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        myAdapter = new RepoDetailsRecyclerViewAdapter(repoContent.ITEMS, this);
        recyclerView.setAdapter(myAdapter);
        return view;
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent;
        RepoContent.RepoItem item = myAdapter.GetValueAt(position);
        String[] itemData  = {item.repoName, item.fileName};

        if (item.type.equals("dir"))
            intent = new Intent(getActivity(), RepoDetailsActivity.class);
        else {
            intent = new Intent(getActivity(), ReaderActivity.class);
            intent.putExtra(TAG, item.downloadLink);
        }

        intent.putExtra(notesFragment.TAG, itemData);
        startActivity(intent);
    }

    public void ChangeItemList(List<RepoContent.RepoItem> repoData) {
        myAdapter.ChangeValues(repoData);
    }
}