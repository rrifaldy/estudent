package com.example.estudent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.estudent.R;
import com.example.estudent.model.Book;

import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Book> bookList = new ArrayList<>();

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public BookAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int i) {
        return bookList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;

        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_book, viewGroup, false);
        }

        ViewHolder viewHolder = new ViewHolder(itemView);

        Book book = (Book) getItem(i);
        viewHolder.bind(book);
        return itemView;
    }

    private class ViewHolder {
        private TextView txtJudul, txtPenulis;

        ViewHolder(View view) {
            txtJudul = view.findViewById(R.id.txt_judul);
            txtPenulis = view.findViewById(R.id.txt_penulis);
        }

        void bind(Book book) {
            txtJudul.setText(book.getJudul());
            txtPenulis.setText(book.getPenulis());
        }
    }
}
