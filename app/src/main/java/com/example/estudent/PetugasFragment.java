package com.example.estudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.estudent.adapter.PetugasAdapter;
import com.example.estudent.model.Petugas;
import com.example.estudent.petugas.CreatePetugas;
import com.example.estudent.petugas.UpdatePetugas;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PetugasFragment extends AppCompatActivity implements View.OnClickListener{
    private ListView listView;
    private PetugasAdapter adapter;
    private ArrayList<Petugas> petugasList;
    private Button btnAdd;
    DatabaseReference dbPetugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_petugas);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_petugas);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_book:
                        startActivity(new Intent(getApplicationContext(), BookFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_petugas:
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        dbPetugas = FirebaseDatabase.getInstance().getReference("petugas");

        listView = findViewById(R.id.lv_list);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        petugasList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PetugasFragment.this, UpdatePetugas.class);
                intent.putExtra(UpdatePetugas.EXTRA_PETUGAS, petugasList.get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbPetugas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                petugasList.clear();

                for (DataSnapshot petugasSnapshot : dataSnapshot.getChildren()) {
                    Petugas petugas = petugasSnapshot.getValue(Petugas.class);
                    petugasList.add(petugas);
                }

                PetugasAdapter adapter = new PetugasAdapter(PetugasFragment.this);
                adapter.setPetugasList(petugasList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PetugasFragment.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add) {
            Intent intent = new Intent(PetugasFragment.this, CreatePetugas.class);
            startActivity(intent);
        }
    }
}