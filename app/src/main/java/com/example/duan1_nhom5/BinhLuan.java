package com.example.duan1_nhom5;

import java.util.HashMap;
import java.util.Map;

public class BinhLuan {
    private String Id;
    private String Username;
    private String NoiDung;
    private String Ngay;

    public BinhLuan() {
    }

    public BinhLuan(String id, String username, String noiDung, String ngay) {
        Id = id;
        Username = username;
        NoiDung = noiDung;
        Ngay = ngay;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("id",Id);
        result.put("username",Username);
        result.put("noiDung",NoiDung);
        result.put("ngay",Ngay);

        return result;
    }
}
