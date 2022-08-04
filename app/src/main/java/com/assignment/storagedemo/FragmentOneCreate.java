package com.assignment.storagedemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

public class FragmentOneCreate extends Fragment {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText program;
    EditText yearOfStudy;
    Button saveLocally;
    private StudentViewModel mStudentViewModel;

    // FragmentManager
    FragmentManager fragmentManager;
    PlaceHolderFragment placeHolderFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one_create, container, false);
        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        email = view.findViewById(R.id.student_email);
        program = view.findViewById(R.id.student_program);
        yearOfStudy = view.findViewById(R.id.student_year_of_study);
        saveLocally = view.findViewById(R.id.save_locally);

        // init ViewModel
        mStudentViewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);

        // Init the Fragment
        placeHolderFragment = new PlaceHolderFragment();

        // Init the FragmentManager
        fragmentManager = requireActivity().getSupportFragmentManager();

        saveLocally.setOnClickListener(v -> {
            insertDataToDatabase();
            // Navigate back
            fragmentManager.popBackStack();
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
}