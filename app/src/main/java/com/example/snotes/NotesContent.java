package com.example.snotes;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NotesContent {
    public final List<NoteItem> ITEMS = new ArrayList<NoteItem>();

    public NotesContent(Context context) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<RepositoryEntity> entities = NameDBSingelton.GetDB().getRepoDao().GetAll();

                for (RepositoryEntity entity : entities) {
                    addItem(createNoteItem(entity.ownersName, entity.repositoryName));
                }
            }
        });
    }

    private void addItem(NoteItem item) {
        ITEMS.add(item);
    }

    private NoteItem createNoteItem(String title, String content) {
        return new NoteItem(title, content);
    }

    public static class NoteItem {
        public final String title;
        public final String content;

        public NoteItem(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public static Comparator<NoteItem> NoteTitle = new Comparator<NoteItem>() {
            @Override
            public int compare(NoteItem noteItem, NoteItem t1) {
                return noteItem.title.toLowerCase().compareTo(t1.title.toLowerCase());
            }
        };

        public static Comparator<NoteItem> NoteRepo = new Comparator<NoteItem>() {
            @Override
            public int compare(NoteItem noteItem, NoteItem t1) {
                return noteItem.content.toLowerCase().compareTo(t1.content.toLowerCase());
            }
        };

        @Override
        public String toString() {
            return title;
        }
    }
}