package com.example.duan1_nhom5;

public class DienThoai {
    private String Id;
    private String Ten;
    private String ChiTiet;
    private int GiaTien;
    private String LinkAnh;
    private int DaBan;
    private int SoLike;

    public DienThoai() {
    }

    public DienThoai(String id, String ten, String chiTiet, int giaTien, String linkAnh, int daBan, int soLike) {
        Id = id;
        Ten = ten;
        ChiTiet = chiTiet;
        GiaTien = giaTien;
        LinkAnh = linkAnh;
        DaBan = daBan;
        SoLike = soLike;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
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

    public int getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(int giaTien) {
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

    public int getSoLike() {
        return SoLike;
    }

    public void setSoLike(int soLike) {
        SoLike = soLike;
    }
}
