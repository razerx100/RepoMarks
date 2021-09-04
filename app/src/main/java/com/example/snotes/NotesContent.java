package com.example.snotes;

import android.content.Context;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NotesContent {
    public final List<NoteItem> ITEMS = new ArrayList<NoteItem>();

    //Update with Date/Alphabetize name sorting functions

    public NotesContent(Context context){
        List<String> names = DataManager.get_all_files_name(context);
        for (String name : names) {
            addItem(createNoteItem(name, DataManager.get_data(name, context)));
        }
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