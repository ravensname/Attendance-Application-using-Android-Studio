package com.example.projectabsent;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.util.TypedValue;
import android.content.Intent;

import java.util.Objects;

public class ListHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_history);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the navigation icon
        toolbar.setNavigationIcon(R.drawable.ic_back);

        // Set the title
        Objects.requireNonNull(getSupportActionBar()).setTitle("History of Attendance");

        // Set the click listener for the navigation icon
        toolbar.setNavigationOnClickListener(v -> goToMainPage());

        try {
            // Retrieve data from Intent
            Intent intent = getIntent();
            if (intent != null) {
                String name = intent.getStringExtra("name");
                String date = intent.getStringExtra("date");
                String position = intent.getStringExtra("position");
                String info = intent.getStringExtra("info");

                // Display data in TextViews or any other desired views
                TextView tvName = findViewById(R.id.tvName);
                TextView tvDate = findViewById(R.id.tvAbsentTime);
                TextView tvPosition = findViewById(R.id.tvLocation);
                TextView tvInfo = findViewById(R.id.tvAbsentInfo);

                tvName.setText(name);
                tvDate.setText(date);
                tvPosition.setText(position);
                tvInfo.setText(info);

                // Dynamically populate history data
                LinearLayout historyContainer = findViewById(R.id.historyContainer);
                addHistoryData(historyContainer, name, date, position, info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addHistoryData(LinearLayout historyContainer, String name, String date, String position, String info) {
        // Create a new CardView to hold history data
        CardView cardView = new CardView(this);
        cardView.setLayoutParams(new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        ));

        // Create a new LinearLayout to hold details in a vertical orientation
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(
                getResources().getDimensionPixelSize(R.dimen.card_padding_horizontal),
                getResources().getDimensionPixelSize(R.dimen.card_padding_vertical),
                getResources().getDimensionPixelSize(R.dimen.card_padding_horizontal),
                getResources().getDimensionPixelSize(R.dimen.card_padding_vertical)
        );

        // Add TextViews for each detail
        addTextView(linearLayout, "Name", name);
        addTextView(linearLayout, "Date", date);
        addTextView(linearLayout, "Position", position);
        addTextView(linearLayout, "Info", info);

        // Add the LinearLayout to the CardView
        cardView.addView(linearLayout);

        // Add the CardView to the history container
        historyContainer.addView(cardView);
    }

    private void addTextView(LinearLayout parentLayout, String label, String value) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Set text size in scaled pixels
        textView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Set text color
        textView.setText(String.format("%s: %s", label, value));
        parentLayout.addView(textView);
    }

    // Handle the back button click
    public void goToMainPage() {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
        finish(); // Optional: Finish the current activity to remove it from the back stack
    }
}
