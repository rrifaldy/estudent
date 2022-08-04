package com.example.estudent.petugas;

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
import com.example.estudent.model.Mahasiswa;
import com.example.estudent.model.Petugas;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatePetugas extends AppCompatActivity implements View.OnClickListener{

    private EditText editUser, editPass;
    private Button btnUpdate;

    public static final String EXTRA_PETUGAS = "extra_petugas";
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;

    private Petugas petugas;
    private String petugasId;

    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_petugas);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        editUser = findViewById(R.id.edt_edit_user);
        editPass = findViewById(R.id.edt_edit_pass);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        petugas = getIntent().getParcelableExtra(EXTRA_PETUGAS);

        if (petugas != null) {
            petugasId = petugas.getId();
        } else {
            petugas = new Petugas();
        }

        if (petugas != null) {
            editUser.setText(petugas.getUsername());
            editPass.setText(petugas.getPassword());

        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Data");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update) {
            updatePetugas();
        }
    }

    public void updatePetugas() {
        String user = editUser.getText().toString().trim();
        String pass = editPass.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(user)) {
            isEmptyFields = true;
            editUser.setError("Field Cannot Be Empty");
        }

        if (TextUtils.isEmpty(pass)) {
            isEmptyFields = true;
            editPass.setError("Field Cannot Be Empty");
        }

        if (! isEmptyFields) {

            Toast.makeText(UpdatePetugas.this, "Updating Data Success", Toast.LENGTH_SHORT).show();

            petugas.setUsername(user);
            petugas.setPassword(pass);
            petugas.setPhoto("");

            DatabaseReference dbPetugas = mDatabase.child("petugas");

            dbPetugas.child(petugasId).setValue(petugas);
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
                            DatabaseReference dbPetugas =
                                    mDatabase.child("petugas").child(petugasId);

                            dbPetugas.removeValue();

                            Toast.makeText(UpdatePetugas.this, "Deleting data success",
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