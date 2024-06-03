package com.example.projectabsent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainPage extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main_page);

                CardView attendanceCard = findViewById(R.id.Attendance);
                CardView absentCard = findViewById(R.id.Absent);
                CardView permissionCard = findViewById(R.id.Permission);
                CardView historyCard = findViewById(R.id.History);

                attendanceCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                showToast("Attendance Card Clicked");
                                Intent intent = new Intent(getApplicationContext(), AttendancePage.class);
                                startActivity(intent);
                                finish();
                        }
                });

                absentCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                showToast("Absent Card Clicked");
                                Intent intent = new Intent(getApplicationContext(), AbsentPage.class);
                                startActivity(intent);
                                finish();
                        }
                });

                permissionCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                // Handle Permission Card Click
                                showToast("Permission Card Clicked");
                                Intent intent = new Intent(getApplicationContext(), PermitPage.class);
                                startActivity(intent);
                                finish();
                        }
                });

                historyCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                // Handle History Card Click
                                showToast("History Card Clicked");
                                Intent intent = new Intent(getApplicationContext(), ListHistory.class);
                                startActivity(intent);
                                finish();
                        }
                });
        }

        private void showToast(String message) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
}