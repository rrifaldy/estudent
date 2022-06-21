package com.example.estudent;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.estudent.adapter.MahasiswaAdapter;
import com.example.estudent.mahasiswa.CreateActivity;
import com.example.estudent.mahasiswa.UpdateActivity;
import com.example.estudent.model.Mahasiswa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private MahasiswaAdapter adapter;
    private ArrayList<Mahasiswa> mahasiswaList;
    private Button btnAdd;

    DatabaseReference dbMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbMahasiswa = FirebaseDatabase.getInstance().getReference("mahasiswa");

        listView = findViewById(R.id.lv_list);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        //list mahasiswa
        mahasiswaList = new ArrayList<>();

        //kode yang ditambahkan
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra(UpdateActivity.EXTRA_MAHASISWA, mahasiswaList.get(i));

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbMahasiswa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mahasiswaList.clear();

                for (DataSnapshot mahasiswaSnapshot : dataSnapshot.getChildren()) {
                    Mahasiswa mahasiswa = mahasiswaSnapshot.getValue(Mahasiswa.class);
                    mahasiswaList.add(mahasiswa);
                }

                MahasiswaAdapter adapter = new MahasiswaAdapter(MainActivity.this);
                adapter.setMahasiswaList(mahasiswaList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add) {
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(intent);
        }
    }
}