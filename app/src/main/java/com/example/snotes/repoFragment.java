package com.example.snotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snotes.databinding.RepoItemListBinding;

import java.util.List;

public class repoFragment extends Fragment implements RepoDetailsRecyclerViewAdapter.OnNoteListener {
    private RepoItemListBinding binding;
    public static final String TAG = "com.example.snotes.MOISTUREING";
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        Intent intent = new Intent(getActivity(), ReaderActivity.class);
        String fileName = "";
        intent.putExtra(TAG, fileName);
        startActivity(intent);
    }

    public void ChangeItemList(List<String> fileNames) {
        for(String fileName : fileNames)
            repoContent.addItem(new RepoContent.RepoItem(fileName));

        myAdapter.ChangeValues(repoContent.ITEMS);
    }
}