package com.example.repoMarks;

import java.util.ArrayList;
import java.util.List;

public class RepoContent {
    public List<RepoItem> ITEMS = new ArrayList<RepoItem>();

    public static RepoItem createNoteItem(String fileName, String type, String path, String downloadLink) {
        return new RepoItem(fileName, type, path, downloadLink);
    }

    public static class RepoItem {
        public final String fileName;
        public final String type;
        public final String repoName;
        public final String downloadLink;

        public RepoItem(String fileName, String type, String path, String downloadLink) {
            this.fileName = fileName;
            this.type = type;
            repoName = path;
            this.downloadLink = downloadLink;
        }

        @Override
        public String toString() {
            return fileName;
        }
    }
}