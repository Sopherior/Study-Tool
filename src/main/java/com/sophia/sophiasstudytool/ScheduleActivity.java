package com.sophia.sophiasstudytool;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.sophia.sophiasstudytool.sqlite.DatabaseHelper;

public class ScheduleActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText nameEditText, dateEditText, timeEditText, idEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        databaseHelper = new DatabaseHelper(this);
        nameEditText = findViewById(R.id.name_edit_text);
        dateEditText = findViewById(R.id.date_edit_text);
        timeEditText = findViewById(R.id.time_edit_text);
        idEditText = findViewById(R.id.id_edit_text);

        Button addButton = findViewById(R.id.add_button);
        Button updateButton = findViewById(R.id.update_button);
        Button deleteButton = findViewById(R.id.delete_button);
        Button viewButton = findViewById(R.id.view_button);

        // Add Data Button Listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String time = timeEditText.getText().toString();
                long id = databaseHelper.addAssignment(name, date, time);
                if (id != -1) {
                    Toast.makeText(ScheduleActivity.this, "Record added with ID: " + id, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ScheduleActivity.this, "Addition failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Update Button Listener
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String time = timeEditText.getText().toString();
                int id = Integer.parseInt(idEditText.getText().toString());
                int rowsAffected = databaseHelper.updateAssignment(id, name, date, time);
                if (rowsAffected > 0) {
                    Toast.makeText(ScheduleActivity.this, "Record updated.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ScheduleActivity.this, "Update failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Delete Button Listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(idEditText.getText().toString());
                int rowsDeleted = databaseHelper.deleteAssignment(id);
                if (rowsDeleted > 0) {
                    Toast.makeText(ScheduleActivity.this, "Record deleted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ScheduleActivity.this, "Deletion failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // View All Button Listener
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = databaseHelper.getAllAssignments();
                if (cursor != null && cursor.moveToFirst()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    do {
                        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                        @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
                        @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TIME));
                        stringBuilder.append(id).append(". ").append(name).append(": ").append(date).append(" at ").append(time).append("\n");
                    } while (cursor.moveToNext());
                    cursor.close();
                    Snackbar snackbar = Snackbar.make(v, stringBuilder.toString(), Snackbar.LENGTH_LONG);
                    TextView textView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setMaxLines(10);
                    snackbar.show();
                } else {
                    Toast.makeText(ScheduleActivity.this, "No records found.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.schedule);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int menuItemId =  item.getItemId();

                if (menuItemId ==  R.id.question){
                    startActivity(new Intent(getApplicationContext(),SubjectActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                else if (menuItemId == R.id.schedule){
                    return true;
                }
                else if (menuItemId == R.id.map){
                    startActivity(new Intent(getApplicationContext(),MapActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}
