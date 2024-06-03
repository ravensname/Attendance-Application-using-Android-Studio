package com.example.projectabsent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AttendancePage extends AppCompatActivity {

    private EditText inputName, inputDate, inputPosition, inputInfo;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_page);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the navigation icon
        toolbar.setNavigationIcon(R.drawable.ic_back);

        // Set the title
        getSupportActionBar().setTitle("Attendance");

        // Set the click listener for the navigation icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainPage();
            }
        });

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("attendance");

        // Initialize UI components
        inputName = findViewById(R.id.inputName);
        inputDate = findViewById(R.id.inputDate);
        inputPosition = findViewById(R.id.inputPosition);
        inputInfo = findViewById(R.id.inputInfo);
        Button btnAbsent = findViewById(R.id.btnAbsent);

        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        // Set click listener for the Submit button
        btnAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAttendance();
            }
        });
    }

    private void showDatePicker() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Set the selected date to the EditText
                inputDate.setText(String.format("%02d/%02d/%d", month + 1, dayOfMonth, year));
                // After selecting the date, show the TimePickerDialog
                showTimePicker();
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        // Get the current time
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create and show TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Append the selected time to the existing date
                String selectedDateTime = inputDate.getText().toString() +
                        String.format(" %02d:%02d", hourOfDay, minute);
                // Set the formatted date and time to the EditText
                inputDate.setText(selectedDateTime);
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void submitAttendance() {
        // Get user inputs
        String name = inputName.getText().toString().trim();
        String date = inputDate.getText().toString().trim();
        String position = inputPosition.getText().toString().trim();
        String info = inputInfo.getText().toString().trim();

        // Check if the inputs are not empty
        if (name.isEmpty() || date.isEmpty() || position.isEmpty() || info.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a request queue using Volley library
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Define the URL of your PHP script on the XAMPP server
        String url = "http://192.168.56.1/wmpbrot/android.php";

        // Create a StringRequest to make a POST request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display a success message
                        Toast.makeText(AttendancePage.this, "Attendance submitted successfully", Toast.LENGTH_SHORT).show();

                        // Clear the input fields
                        inputName.getText().clear();
                        inputDate.getText().clear();
                        inputPosition.getText().clear();
                        inputInfo.getText().clear();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Display an error message
                        Toast.makeText(AttendancePage.this, "Error submitting attendance", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Set the POST parameters
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("date", date);
                params.put("position", position);
                params.put("info", info);
                return params;
            }
        };

        // Add the request to the request queue
        requestQueue.add(stringRequest);

        // Create an Intent to start the ListHistory activity
        Intent intent = new Intent(AttendancePage.this, ListHistory.class);

        // Pass the data to the ListHistory activity using Intent extras
        intent.putExtra("name", name);
        intent.putExtra("date", date);
        intent.putExtra("position", position);
        intent.putExtra("info", info);

        // Start the ListHistory activity
        startActivity(intent);
    }

    // Handle the back button click
    public void goToMainPage() {
        // Implement your navigation logic here
        // For example, you can use Intent to navigate to the main page
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
        finish(); // Optional: Finish the current activity to remove it from the back stack
    }
}