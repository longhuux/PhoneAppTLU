package com.example.duan1_nhom5;

public class GioHang {
    private String TenGioHang;
    private int GiaGioHang;
    private int SoLuong;
    private String AnhGioHang;
    private int GiaDT;

    public GioHang() {
    }

    public GioHang(String tenGioHang, int giaGioHang, int soLuong, String anhGioHang, int giaDT) {
        TenGioHang = tenGioHang;
        GiaGioHang = giaGioHang;
        SoLuong = soLuong;
        AnhGioHang = anhGioHang;
        GiaDT = giaDT;
    }

    public String getTenGioHang() {
        return TenGioHang;
    }

    public void setTenGioHang(String tenGioHang) {
        TenGioHang = tenGioHang;
    }

    public int getGiaGioHang() {
        return GiaGioHang;
    }

    public void setGiaGioHang(int giaGioHang) {
        GiaGioHang = giaGioHang;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getAnhGioHang() {
        return AnhGioHang;
    }

    public void setAnhGioHang(String anhGioHang) {
        AnhGioHang = anhGioHang;
    }

    public int getGiaDT() {
        return GiaDT;
    }

    public void setGiaDT(int giaDT) {
        GiaDT = giaDT;
    }
}
