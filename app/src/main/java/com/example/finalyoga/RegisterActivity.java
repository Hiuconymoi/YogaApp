package com.example.finalyoga;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyoga.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText eUsername, ePassword, eConfirmPass,eEmail;
    Button btnRegister, btnBack;
    DatabaseHelper dbHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        dbHelper=new DatabaseHelper(this);

        eUsername = findViewById(R.id.eUsername);
        ePassword = findViewById(R.id.ePassword);
        eConfirmPass = findViewById(R.id.eConfirmPass);
        eEmail = findViewById(R.id.eEmail);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack=findViewById(R.id.btnBack);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = eUsername.getText().toString();
                String password = ePassword.getText().toString();
                String confirmPassword = eConfirmPass.getText().toString();
                String email = eEmail.getText().toString();

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!username.isEmpty() && !password.isEmpty() && !email.isEmpty()){
                    if (dbHelper.addUser(username,password,email)) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        finish(); // Close the RegisterActivity and return to MainActivity
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Please fill all of the fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
