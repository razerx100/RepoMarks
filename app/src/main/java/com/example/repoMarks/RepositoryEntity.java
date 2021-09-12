package com.example.repoMarks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "repository_entity")
public class RepositoryEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "owners_name")
    public String ownersName;

    @ColumnInfo(name = "repository_name")
    public String repositoryName;
}