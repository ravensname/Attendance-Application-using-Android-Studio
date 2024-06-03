package com.example.projectabsent;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    EditText usernameEditText, emailEditText, passwordEditText;
    Button signupButton;
    TextView loginButton;
    RequestQueue requestQueue;
    String signupUrl = "http://192.168.56.1/wmpbrot/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameEditText = findViewById(R.id.Username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        signupButton = findViewById(R.id.signup);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        loginButton = findViewById(R.id.loginnow);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signup() {
        final String username = usernameEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, signupUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the response to determine signup success or failure
                        if (response.equals("Sign up successful")) {
                            // Signup successful
                            Toast.makeText(getApplicationContext(), "Sign up successful", Toast.LENGTH_SHORT).show();
                            // Handle successful signup, such as navigating to another activity
                            Intent intent = new Intent(signup.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Signup failed
                            Toast.makeText(getApplicationContext(), "Sign up failed", Toast.LENGTH_SHORT).show();
                            // Handle failed signup, such as displaying an error message
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error in signup request
                        Toast.makeText(getApplicationContext(), "Error in signup. Please try again later.", Toast.LENGTH_SHORT).show();
                        Log.e("Signup Error", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Set the request parameters
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);

                // Log the parameters
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    Log.d("SignupRequest", "Key: " + entry.getKey() + ", Value: " + entry.getValue());
                }

                return params;
            }
        };

        // Add the request to the Volley request queue
        requestQueue.add(stringRequest);
    }
}


