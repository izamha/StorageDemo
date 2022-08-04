package com.assignment.storagedemo.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "std_table")
public class Student {
    @PrimaryKey()
    @NonNull
    private String studentEmail;
    private String studentFirstName;
    private String studentLastName;
    private String studentProgram;
    private Long studentRegNumber;
    private Long studentYearOfStudy;

    //!\\ Wibuke kongeraho a boolean property

    public Student() {
        // Ntunsibe!
    }

    public Student(@NonNull String studentEmail,
                   String studentFirstName,
                   String studentLastName,
                   String studentProgram,
                   Long studentRegNumber,
                   Long studentYearOfStudy) {
        this.studentEmail = studentEmail;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.studentProgram = studentProgram;
        this.studentRegNumber = studentRegNumber;
        this.studentYearOfStudy = studentYearOfStudy;
    }

    @NonNull
    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(@NonNull String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getStudentProgram() {
        return studentProgram;
    }

    public void setStudentProgram(String studentProgram) {
        this.studentProgram = studentProgram;
    }

    public Long getStudentRegNumber() {
        return studentRegNumber;
    }

    public void setStudentRegNumber(Long studentRegNumber) {
        this.studentRegNumber = studentRegNumber;
    }

    public Long getStudentYearOfStudy() {
        return studentYearOfStudy;
    }

    public void setStudentYearOfStudy(Long studentYearOfStudy) {
        this.studentYearOfStudy = studentYearOfStudy;
    }


}
