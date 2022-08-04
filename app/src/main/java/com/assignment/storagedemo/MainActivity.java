package com.assignment.storagedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button headToSecondAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Persist data when offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        headToSecondAct = findViewById(R.id.go_to_second_act);

        headToSecondAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}