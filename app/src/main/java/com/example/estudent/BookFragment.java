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

import com.example.estudent.adapter.BookAdapter;
import com.example.estudent.buku.CreateBook;
import com.example.estudent.buku.UpdateBook;
import com.example.estudent.model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookFragment extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    private BookAdapter adapter;
    private ArrayList<Book> bookList;
    private Button btnAdd;
    DatabaseReference dbBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_book);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_book);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_book:
                        return true;
                    case R.id.nav_petugas:
                        startActivity(new Intent(getApplicationContext(), PetugasFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    dbBook = FirebaseDatabase.getInstance().getReference("book");

    listView = findViewById(R.id.lv_list);
    btnAdd = findViewById(R.id.btn_add);
    btnAdd.setOnClickListener(this);

    bookList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(BookFragment.this, UpdateBook.class);
            intent.putExtra(UpdateBook.EXTRA_BOOK, bookList.get(i));

            startActivity(intent);
        }
    });
}

    @Override
    protected void onStart() {
        super.onStart();

        dbBook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookList.clear();

                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                    Book book = bookSnapshot.getValue(Book.class);
                    bookList.add(book);
                }

                BookAdapter adapter = new BookAdapter(BookFragment.this);
                adapter.setBookList(bookList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BookFragment.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add) {
            Intent intent = new Intent(BookFragment.this, CreateBook.class);
            startActivity(intent);
        }
    }
}