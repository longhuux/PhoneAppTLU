package com.example.duan1_nhom5;

public class GioHang {
    private String TenGioHang;
    private Double GiaGioHang;
    private int SoLuong;
    private String AnhGioHang;

    public GioHang() {
    }

    public GioHang(String tenGioHang, Double giaGioHang, int soLuong, String anhGioHang) {
        TenGioHang = tenGioHang;
        GiaGioHang = giaGioHang;
        SoLuong = soLuong;
        AnhGioHang = anhGioHang;
    }

    public String getTenGioHang() {
        return TenGioHang;
    }

    public void setTenGioHang(String tenGioHang) {
        TenGioHang = tenGioHang;
    }

    public Double getGiaGioHang() {
        return GiaGioHang;
    }

    public void setGiaGioHang(Double giaGioHang) {
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
}
