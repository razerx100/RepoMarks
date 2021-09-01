package com.example.snotes;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NotesContent {
    public final List<NoteItem> ITEMS = new ArrayList<NoteItem>();

    //Update with Date/Alphabetize name sorting functions

    public NotesContent(Context context){
        List<String> names = DataManager.get_all_files_name(context);
        for (String name : names) {
            addItem(createNoteItem(name, DataManager.get_data(name, context), DataManager.get_date_in_ms(name, context)));
        }
    }

    private void addItem(NoteItem item) {
        ITEMS.add(item);
    }

    private NoteItem createNoteItem(String title, String content, Long date) {
        return new NoteItem(title, content, date);
    }

    public static class NoteItem {
        public final String title;
        public final String content;
        public final Long date;

        public NoteItem(String title, String content, Long date) {
            this.title = title;
            this.content = content;
            this.date = date;
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