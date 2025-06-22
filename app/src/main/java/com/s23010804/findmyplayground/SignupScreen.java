package com.s23010804.findmyplayground;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupScreen extends AppCompatActivity {

    EditText uNameInput, contactNumberInput, passwordInput, confirmPasswordInput;
    Button registerButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        uNameInput = findViewById(R.id.uNameInput);
        contactNumberInput = findViewById(R.id.contactNumberInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        registerButton = findViewById(R.id.registerButton);

        db = new DatabaseHelper(this);

        registerButton.setOnClickListener(v -> {
            String ownerName = uNameInput.getText().toString().trim();
            String contactNumber = contactNumberInput.getText().toString().trim();
            String password = passwordInput.getText().toString();
            String confirmPassword = confirmPasswordInput.getText().toString();

            if (TextUtils.isEmpty(ownerName) ||
                    TextUtils.isEmpty(contactNumber) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(SignupScreen.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(SignupScreen.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = db.insertUser(ownerName, contactNumber, password);
            if (inserted) {
                Toast.makeText(SignupScreen.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                finish(); // Close register and return to login
            } else {
                Toast.makeText(SignupScreen.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}