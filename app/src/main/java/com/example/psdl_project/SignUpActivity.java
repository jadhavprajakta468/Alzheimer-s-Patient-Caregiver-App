package com.example.psdl_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword;
    private Button signupButton;
    private TextView loginRedirectText;
    private TextView fillFormText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.username);
        signupPassword = findViewById(R.id.password);
        signupButton = findViewById(R.id.signupButton);
        loginRedirectText = findViewById(R.id.loginredirectText);
        fillFormText = findViewById(R.id.fillFormText);

        // Check if the form is filled
        SharedPreferences preferences = getSharedPreferences("FormPrefs", MODE_PRIVATE);
        boolean isFormCompleted = preferences.getBoolean("isFormCompleted", false);

        // If form is not filled, disable signup fields and show form prompt
        if (!isFormCompleted) {
            signupButton.setEnabled(false); // Disable signup button until form is completed
            signupEmail.setEnabled(false);  // Disable email field
            signupPassword.setEnabled(false); // Disable password field
            fillFormText.setVisibility(View.VISIBLE); // Show option to fill form
        } else {
            // If the form is completed, enable signup fields and hide the form prompt
            signupButton.setEnabled(true);
            signupEmail.setEnabled(true);
            signupPassword.setEnabled(true);
            fillFormText.setVisibility(View.GONE);
        }

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();

                if (user.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                } else if (pass.isEmpty()) {
                    signupPassword.setError("Password cannot be empty");
                } else {
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();

                                // Redirecting to MainActivity
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.putExtra("USERNAME", user); // Passing user email
                                startActivity(intent);
                                finish(); // Close SignUpActivity

                            } else {
                                Toast.makeText(SignUpActivity.this, "SignUp Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        fillFormText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, Form.class);
                startActivity(intent);
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Recheck the form completion status every time the activity resumes
        SharedPreferences preferences = getSharedPreferences("FormPrefs", MODE_PRIVATE);
        boolean isFormCompleted = preferences.getBoolean("isFormCompleted", false);

        // If form is not filled, disable signup fields and show form prompt
        if (!isFormCompleted) {
            signupButton.setEnabled(false); // Disable signup button until form is completed
            signupEmail.setEnabled(false);  // Disable email field
            signupPassword.setEnabled(false); // Disable password field
            fillFormText.setVisibility(View.VISIBLE); // Show option to fill form
        } else {
            // If the form is completed, enable signup fields and hide the form prompt
            signupButton.setEnabled(true);
            signupEmail.setEnabled(true);
            signupPassword.setEnabled(true);
            fillFormText.setVisibility(View.GONE);
        }
    }
}
