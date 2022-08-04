package com.example.estudent.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Petugas implements Parcelable{

    private String id;
    private String username;
    private String password;
    private String photo;

    public Petugas(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.photo);
    }

    protected Petugas(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.photo = in.readString();
    }

    public static final Parcelable.Creator<Petugas> CREATOR = new Parcelable.Creator<Petugas>() {
        @Override
        public Petugas createFromParcel(Parcel source) {
            return new Petugas(source);
        }

        @Override
        public Petugas[] newArray(int size) {
            return new Petugas[size];
        }
    };
}