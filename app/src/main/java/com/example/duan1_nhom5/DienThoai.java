package com.example.duan1_nhom5;

public class DienThoai {
    private int Id;
    private String Ten;
    private String ChiTiet;
    private Double GiaTien;
    private String LinkAnh;

    public DienThoai() {
    }

    public DienThoai(int id, String ten, String chiTiet, Double giaTien, String linkAnh) {
        Id = id;
        Ten = ten;
        ChiTiet = chiTiet;
        GiaTien = giaTien;
        LinkAnh = linkAnh;
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

    public Double getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(Double giaTien) {
        GiaTien = giaTien;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }
}
