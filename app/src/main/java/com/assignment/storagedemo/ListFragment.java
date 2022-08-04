package com.assignment.storagedemo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.assignment.storagedemo.adapter.StudentAdapter;
import com.assignment.storagedemo.adapter.SwipeViewAdapter;
import com.assignment.storagedemo.database.StudentViewModel;
import com.assignment.storagedemo.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// THIS FILE IS NOT NECESSARY
// JUST LAZY TO GET RID OF IT.

public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    StudentAdapter studentAdapter;

    SwipeViewAdapter swipeViewAdapter;
    ViewPager2 viewPager;

    ArrayList<Student> firebaseData;
    ArrayList<Student> localData;
    StudentViewModel mStudentViewModel;
    // LinearLayoutManager
    LinearLayoutManager mLinearLayoutManager;
    // Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        fab = view.findViewById(R.id.fab);

        // initialization ya Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Students");
        firebaseData = new ArrayList<>();
        localData = new ArrayList<>(); // so that we can get data from db

        //ViewModel
        mStudentViewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);

        // retrieve data from the LocalDatabase
         // getLocalData();

        // retrieve data from the RemoteDatabase
        // getRemoteData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.pager);
        swipeViewAdapter = new SwipeViewAdapter(this);
        viewPager.setAdapter(swipeViewAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("FRAGMENT " + (position + 1))
        ).attach();
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
        databaseReference.addValueEventListener(new ValueEventListener() {
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