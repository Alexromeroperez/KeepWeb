package com.arp.keeps.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alex on 09/02/2016.
 */
public class Usuario implements Parcelable{
    private String email,pass;

    public Usuario(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public Usuario() {
        this("","");
    }

    protected Usuario(Parcel in) {
        email = in.readString();
        pass = in.readString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(pass);
    }
}
