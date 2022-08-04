package com.assignment.storagedemo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.assignment.storagedemo.model.Student;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Student.class}, version = 1)
public abstract class StudentDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();

    private static volatile StudentDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static StudentDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StudentDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    StudentDatabase.class, "shoe_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
