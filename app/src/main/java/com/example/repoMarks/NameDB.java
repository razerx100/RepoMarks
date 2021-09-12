package com.example.repoMarks;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RepositoryEntity.class}, version = 1, exportSchema = false)
public abstract class NameDB extends RoomDatabase {
    public abstract RepoDao getRepoDao();
}
