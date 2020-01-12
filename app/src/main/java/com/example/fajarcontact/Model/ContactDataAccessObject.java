package com.example.fajarcontact.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDataAccessObject {

    @Insert
    void insert(Contact contact);
    @Update
    void update(Contact contact);
    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAllContact();

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    LiveData<List<Contact>> getAllContact();
}
