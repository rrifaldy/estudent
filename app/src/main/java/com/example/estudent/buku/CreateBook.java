package com.example.estudent.buku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.estudent.R;
import com.example.estudent.mahasiswa.CreateActivity;
import com.example.estudent.model.Book;
import com.example.estudent.model.Mahasiswa;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateBook extends AppCompatActivity implements View.OnClickListener{

    private EditText editJudul, editPenulis;
    private Button btnSave;

    private Book book;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        editJudul = findViewById(R.id.edit_judul);
        editPenulis = findViewById(R.id.edit_penulis);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(this);

        book = new Book();
    }

        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.btn_save) {
                saveBook();
            }

        }

        private void saveBook()
        {
            String judul = editJudul.getText().toString().trim();
            String penulis = editPenulis.getText().toString().trim();

            boolean isEmptyFields = false;

            if (TextUtils.isEmpty(judul)) {
                isEmptyFields = true;
                editJudul.setError("Field Cannot Be Empty");
            }

            if (TextUtils.isEmpty(penulis)) {
                isEmptyFields = true;
                editPenulis.setError("Field Cannot Be Empty");
            }

            if (! isEmptyFields) {

                Toast.makeText(CreateBook.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();

                DatabaseReference dbBook = mDatabase.child("book");

                String id = dbBook.push().getKey();
                book.setId(id);
                book.setJudul(judul);
                book.setPenulis(penulis);
                book.setPhoto("");

                //insert data
                dbBook.child(id).setValue(book);

                finish();

            }
        }
}