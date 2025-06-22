package com.s23010804.findmyplayground;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginScreen extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    Button loginButton, registerButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        db = new DatabaseHelper(this);

        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginScreen.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean valid = db.checkUser(username, password);
                if (valid) {
                    Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    // TODO: Navigate to the dashboard or main screen
                    Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginScreen.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, SignupScreen.class);
            startActivity(intent);
        });
    }
}