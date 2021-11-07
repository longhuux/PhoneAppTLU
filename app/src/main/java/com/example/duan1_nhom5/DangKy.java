package com.example.duan1_nhom5;

import java.util.HashMap;
import java.util.Map;

public class DangKy {
    private int Id;
    private String HoTen;
    private String Email;
    private String Password;
    private DangKy() {
    }

///ngo thanh thong

    public DangKy(int id, String hoten, String email, String password) {
        Id = id;
        HoTen=hoten;
        Email = email;
        Password = password;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("email",Email);
        result.put("hoTen",HoTen);
        result.put("password",Password);

        return result;
    }
}
