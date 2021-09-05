package com.example.snotes;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RepositoryEntity.class}, version = 1)
public abstract class NameDB extends RoomDatabase {
    public abstract RepoDao getRepoDao();
}
