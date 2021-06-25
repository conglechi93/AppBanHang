package com.midterm.shopapp.model;

import android.widget.ImageView;

public class User {
    private String Id,Email,Name,Phone,Password,Image;

    public User(){

    }
    public User(String id,String email, String name, String phone,String pass,String image) {
        Id = id;
        Email = email;
        Name = name;
        Phone = phone;
        Password = pass;
        Image = image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
