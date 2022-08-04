package com.assignment.storagedemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

public class FragmentTwoCreate extends Fragment {
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText program;
    EditText yearOfStudy;
    Button saveRemotely;
    FragmentManager fragmentManager;
    PlaceHolderFragment placeHolderFragment;

    // Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two_create, container, false);

        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        email = view.findViewById(R.id.student_email);
        program = view.findViewById(R.id.student_program);
        yearOfStudy = view.findViewById(R.id.student_year_of_study);
        saveRemotely = view.findViewById(R.id.save_remotely);

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Students"); // Database name

        // Init the Fragment
        placeHolderFragment = new PlaceHolderFragment();

        // Init the FragmentManager
        fragmentManager = requireActivity().getSupportFragmentManager();

        saveRemotely.setOnClickListener(v -> {
            saveRemotely();
            fragmentManager.popBackStack();
        });

        return view;
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