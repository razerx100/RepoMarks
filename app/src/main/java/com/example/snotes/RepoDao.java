package com.example.snotes;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RepoDao {
    @Query("SELECT * FROM repository_entity WHERE owners_name LIKE :ownersName")
    List<RepositoryEntity> GetByOwnersName(String ownersName);

    @Query("SELECT * FROM repository_entity WHERE repository_name LIKE :repoName")
    List<RepositoryEntity> GetByRepoName(String repoName);

    @Query("SELECT * FROM repository_entity WHERE repository_name LIKE :repoName And " +
            "owners_name LIKE :ownersName")
    List<RepositoryEntity> GetByOwnerAndRepo(String ownersName, String repoName);

    @Query("SELECT * FROM repository_entity")
    List<RepositoryEntity> GetAll();

    @Query("INSERT INTO repository_entity (owners_name, repository_name)" +
            "VALUES (:ownersName, :repoName)")
    void InsertEntity(String ownersName, String repoName);

    @Query("DELETE FROM repository_entity WHERE owners_name LIKE :ownersName AND " +
            "repository_name LIKE :repoName")
    void DeleteEntity(String ownersName, String repoName);
}