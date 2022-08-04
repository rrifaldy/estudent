package com.example.estudent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.estudent.R;
import com.example.estudent.model.Mahasiswa;
import com.example.estudent.model.Petugas;

import java.util.ArrayList;

public class PetugasAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Petugas> petugasList = new ArrayList<>();

    public void setPetugasList(ArrayList<Petugas> petugasList) {
        this.petugasList = petugasList;
    }

    public PetugasAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return petugasList.size();
    }

    @Override
    public Object getItem(int i) {
        return petugasList.get(i);
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
                    .inflate(R.layout.item_petugas, viewGroup, false);
        }
        ViewHolder viewHolder = new ViewHolder(itemView);

        Petugas petugas = (Petugas) getItem(i);
        viewHolder.bind(petugas);
        return itemView;
    }

    private class ViewHolder {
        private TextView txtUsername, txtPass;

        ViewHolder(View view) {
            txtUsername = view.findViewById(R.id.txt_username);
            txtPass = view.findViewById(R.id.txt_pass);
        }

        void bind(Petugas petugas) {
            txtUsername.setText(petugas.getUsername());
            txtPass.setText(petugas.getPassword());
        }
    }
}