package com.example.estudent.buku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.estudent.R;
import com.example.estudent.mahasiswa.UpdateActivity;
import com.example.estudent.model.Book;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateBook extends AppCompatActivity implements View.OnClickListener{

    private EditText editJudul, editPenulis;
    private Button btnUpdate;

    public static final String EXTRA_BOOK = "extra_book";
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;

    private Book book;
    private String bookId;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

    mDatabase = FirebaseDatabase.getInstance().getReference();

    editJudul = findViewById(R.id.edt_edit_judul);
    editPenulis = findViewById(R.id.edt_edit_penulis);
    btnUpdate = findViewById(R.id.btn_update);
    btnUpdate.setOnClickListener(this);

    book = getIntent().getParcelableExtra(EXTRA_BOOK);

        if (book != null) {
        bookId = book.getId();
    } else {
        book = new Book();
    }

        if (book != null) {
        editJudul.setText(book.getJudul());
        editPenulis.setText(book.getPenulis());
    }

        if (getSupportActionBar() != null) {
        getSupportActionBar().setTitle("Edit Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update) {
            updateBook();
        }
    }

    public void updateBook() {
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

            Toast.makeText(UpdateBook.this, "Updating Data Success", Toast.LENGTH_SHORT).show();

            book.setJudul(judul);
            book.setPenulis(penulis);
            book.setPhoto("");

            DatabaseReference dbBook = mDatabase.child("book");

            //update data
            dbBook.child(bookId).setValue(book);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Cancel";
            dialogMessage = "Apakah anda yakin membatalkan perubahan?";
        } else {
            dialogTitle = "Delete Data";
            dialogMessage = "Apakah anda yakin menghapus data ini?";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isDialogClose) {
                            finish();
                        } else {
                            DatabaseReference dbBook =
                                    mDatabase.child("book").child(bookId);

                            dbBook.removeValue();

                            Toast.makeText(UpdateBook.this, "Deleting data success",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}