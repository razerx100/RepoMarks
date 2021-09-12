package com.example.snotes;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RepoContent {
    public List<RepoItem> ITEMS = new ArrayList<RepoItem>();

    public void addItem(RepoItem item) {
        ITEMS.add(item);
    }

    public RepoItem createNoteItem(String fileName) {
        return new RepoItem(fileName);
    }

    public static class RepoItem {
        public final String fileName;

        public RepoItem(String fileName) {
            this.fileName = fileName;
        }

        public static Comparator<RepoItem> NoteTitle = new Comparator<RepoItem>() {
            @Override
            public int compare(RepoItem item0, RepoItem item1) {
                return item0.fileName.toLowerCase().compareTo(item1.fileName.toLowerCase());
            }
        };

        @Override
        public String toString() {
            return fileName;
        }
    }
}