package com.assignment.storagedemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.storagedemo.database.StudentViewModel;
import com.assignment.storagedemo.model.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// THIS FILE IS NOT NECESSARY
// JUST LAZY TO GET RID OF IT.

public class AddFragment extends Fragment {
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText program;
    EditText yearOfStudy;
    TextView letsRecordTitle;
    Button saveLocally;
    Button saveRemotely;
    private StudentViewModel mStudentViewModel;

    // Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        email = view.findViewById(R.id.student_email);
        program = view.findViewById(R.id.student_program);
        yearOfStudy = view.findViewById(R.id.student_year_of_study);
        saveLocally = view.findViewById(R.id.save_locally);
        saveRemotely = view.findViewById(R.id.save_remotely);

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Students"); // Database name

        // init ViewModel
        mStudentViewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);


        saveLocally.setOnClickListener(v -> {
            insertDataToDatabase();
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_addFragment_to_listFragment);
        });

        saveRemotely.setOnClickListener(v -> {
            saveRemotely();
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_addFragment_to_listFragment);
        });

        return view;
    }

    public void insertDataToDatabase() {
        // You need to validate the inputFields
        // if inputField.isEmpty()
        // If they are empty, say something.
        // Isn't it Strange?
        Student student = new Student(
                email.getText().toString(),
                firstName.getText().toString(),
                lastName.getText().toString(),
                program.getText().toString(),
                219000L, // Remember to change this constant value
                Long.valueOf(yearOfStudy.getText().toString()));

        // INSERT DATA TO THE DATABASE
        mStudentViewModel.insert(student);
        Toast.makeText(getActivity(), "Successfully sent to Database",
                Toast.LENGTH_LONG).show();

    }

    public void saveRemotely() {
        // You need to validate the inputFields
        // if inputField.isEmpty()
        // If they are empty, say something.
        // Isn't it Strange?

        Student student = new Student(
                email.getText().toString(),
                firstName.getText().toString(),
                lastName.getText().toString(),
                program.getText().toString(),
                219000L, // Remember to change this constant value
                Long.valueOf(yearOfStudy.getText().toString()));
        databaseReference.push().setValue(student);
        Toast.makeText(getActivity(), "Data successfully added to online database",
                Toast.LENGTH_LONG).show();
    }
}