package com.example.finalyoga;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyoga.database.DatabaseHelper;


public class MainActivity extends AppCompatActivity {

    EditText editUs, editPw;
    Button btnLogin, btnResgis;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper=new DatabaseHelper(MainActivity.this);
        editUs = findViewById(R.id.editUs);
        editPw = findViewById(R.id.editPw);
        btnLogin = findViewById(R.id.btnLogin);
        btnResgis = findViewById(R.id.btnRegis);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUs.getText().toString();
                String password = editPw.getText().toString();
                if (dbHelper.checkLogin(username, password)) {
                    Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                    startActivity(intent);
                }
                ;
            }
        });

        btnResgis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}