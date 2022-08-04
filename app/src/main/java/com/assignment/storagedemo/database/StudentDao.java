package com.assignment.storagedemo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.assignment.storagedemo.model.Student;

import java.util.List;

@Dao
public interface StudentDao {
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Student student);

    // Retrieve all the shoes from db
    @Query("SELECT * FROM std_table ORDER BY studentRegNumber ASC")
    LiveData<List<Student>> readAllData();
}
