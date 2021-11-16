package com.example.duan1_nhom5;

public class DienThoai {
    private int Id;
    private String Ten;
    private String ChiTiet;
    private int GiaTien;
    private String LinkAnh;
    private int DaBan;

    public DienThoai() {
    }

    public DienThoai(int id, String ten, String chiTiet, Integer giaTien, String linkAnh, int daBan) {
        Id = id;
        Ten = ten;
        ChiTiet = chiTiet;
        GiaTien = giaTien;
        LinkAnh = linkAnh;
        DaBan = daBan;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getChiTiet() {
        return ChiTiet;
    }

    public void setChiTiet(String chiTiet) {
        ChiTiet = chiTiet;
    }

    public Integer getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(Integer giaTien) {
        GiaTien = giaTien;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }

    public int getDaBan() {
        return DaBan;
    }

    public void setDaBan(int daBan) {
        DaBan = daBan;
    }
}
