package com.example.estudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btnLog;
    EditText txtName,txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtName = findViewById(R.id.txtUsername);
        txtPass = findViewById(R.id.txtPassword);

        btnLog = findViewById(R.id.btnLogin);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });
    }
    public void Check(){
        if (txtName.getText().toString().equals("admin") && txtPass.getText().toString().equals("11111")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Username & Password Salah", Toast.LENGTH_SHORT).show();
        }
    }
    public void Login(View view) {
        Check();
    }
}