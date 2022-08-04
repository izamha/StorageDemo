package com.assignment.storagedemo.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.assignment.storagedemo.model.Student;

import java.util.List;

public class StudentRepo {
    private StudentDao mStudentDao;
    public LiveData<List<Student>> mAllStudents;

    public StudentRepo(Application application) {
        StudentDatabase db = StudentDatabase.getDatabase(application);
        mStudentDao = db.studentDao();
        mAllStudents = mStudentDao.readAllData();
    }

    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Student>> getAllShoes() {
        return mAllStudents;
    }

    public void insert(Student student) {
        StudentDatabase.databaseWriteExecutor.execute(() -> {
            mStudentDao.insert(student);
        });
    }
}

