package com.assignment.storagedemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.storagedemo.adapter.StudentAdapter;
import com.assignment.storagedemo.database.StudentViewModel;
import com.assignment.storagedemo.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlaceHolderFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    FragmentManager fragmentManager1;
    FragmentManager fragmentManager2;
    FragmentOneCreate fragmentOneCreate;
    FragmentTwoCreate fragmentTwoCreate;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    ArrayList<Student> localData;
    ArrayList<Student> firebaseData;
    StudentViewModel mStudentViewModel;
    // LinearLayoutManager
    LinearLayoutManager mLinearLayoutManager;
    // Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @SuppressLint("NotifyDataSetChanged")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_holder,
                container, false);
        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recyclerview);

        // initialization ya Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Students");
        //
        localData = new ArrayList<>(); // so that we can get data from db
        firebaseData = new ArrayList<>(); // so that we can get data from the remote source

        //ViewModel
        mStudentViewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        // init the fragments
        fragmentOneCreate = new FragmentOneCreate();
        fragmentTwoCreate = new FragmentTwoCreate();


        // retrieve data from the LocalDatabase || Remote
        if (args.getInt(ARG_OBJECT) == 1) {
            getLocalData();
        } else {
            getRemoteData();
        }

        // Navigate to FragmentCreate
        fab.setOnClickListener(v -> {
            if (args.getInt(ARG_OBJECT) == 1) {
                fragmentManager1 = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragmentOneCreate);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                fragmentManager2 = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragmentTwoCreate);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    public void getLocalData() {
        // Create an observer
        final Observer<List<Student>> studentObserver = new Observer<List<Student>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Student> students) {
                localData = (ArrayList<Student>) students;
                studentAdapter = new StudentAdapter(localData);
                studentAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(studentAdapter);

                Log.d("LIST", "onDataChange: " + studentAdapter.getItemCount());
            }
        };

        mStudentViewModel.getAllStudents().observe(requireActivity(), studentObserver);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        // Reverse the recyclerView Content
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
    }


    public void getRemoteData() {
        firebaseData.clear();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Student stdSnapshot = dataSnapshot.getValue(Student.class);
                        firebaseData.add(new Student(
                                stdSnapshot.getStudentEmail(),
                                stdSnapshot.getStudentFirstName(),
                                stdSnapshot.getStudentLastName(),
                                stdSnapshot.getStudentProgram(),
                                stdSnapshot.getStudentRegNumber(),
                                stdSnapshot.getStudentYearOfStudy()
                        ));
                        Log.d("LIST", "onDataChange: " + firebaseData);
                    }
                }
                studentAdapter = new StudentAdapter(firebaseData);
                recyclerView.setAdapter(studentAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Couldn't get the StudentData",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}