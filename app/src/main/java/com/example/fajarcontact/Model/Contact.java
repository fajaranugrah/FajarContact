package com.example.fajarcontact.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String nomor_telepon;
    private String email;
    private String alamat;

    public Contact(String name, String nomor_telepon, String email, String alamat){
        this.name = name;
        this.nomor_telepon = nomor_telepon;
        this.email = email;
        this.alamat = alamat;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNomor_telepon() {
        return nomor_telepon;
    }

    public String getEmail() {
        return email;
    }

    public String getAlamat() {
        return alamat;
    }
}
