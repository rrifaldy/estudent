package com.example.estudent.petugas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.estudent.R;
import com.example.estudent.mahasiswa.CreateActivity;
import com.example.estudent.model.Mahasiswa;
import com.example.estudent.model.Petugas;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePetugas extends AppCompatActivity implements View.OnClickListener{

    private EditText editUser, editPass;
    private Button btnSave;
    private Petugas petugas;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_petugas);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        editUser = findViewById(R.id.edit_username);
        editPass = findViewById(R.id.edit_pass);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        petugas = new Petugas();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_save) {
            savePetugas();
        }

    }

    private void savePetugas()
    {
        String username = editUser.getText().toString().trim();
        String password = editPass.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(username)) {
            isEmptyFields = true;
            editUser.setError("Field Cannot Be Empty");
        }

        if (TextUtils.isEmpty(password)) {
            isEmptyFields = true;
            editPass.setError("Field Cannot Be Empty");
        }

        if (! isEmptyFields) {

            Toast.makeText(CreatePetugas.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();

            DatabaseReference dbPetugas = mDatabase.child("petugas");

            String id = dbPetugas.push().getKey();
            petugas.setId(id);
            petugas.setUsername(username);
            petugas.setPassword(password);
            petugas.setPhoto("");
            dbPetugas.child(id).setValue(petugas);
            finish();
        }
    }
}