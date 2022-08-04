package com.assignment.storagedemo.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.assignment.storagedemo.model.Student;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {
    private final StudentRepo mRepository;
    private final LiveData<List<Student>> mAllStudents;

    public StudentViewModel(Application application) {
        super(application);
        mRepository = new StudentRepo(application);
        mAllStudents = mRepository.getAllShoes();
    }

    public LiveData<List<Student>> getAllStudents() {
        return mAllStudents;
    }

    public void insert(Student student) {
        mRepository.insert(student);
    }
}
