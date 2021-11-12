package com.example.duan1_nhom5;

public class ThongTinDonHang {
    private String TenNguoiNhan;
    private String DiaChi;
    private int Sdt;
    private String TenSP;
    private Double GiaSP;
    private int SoLuong;
    private String AnhSP;

    public ThongTinDonHang() {
    }

    public ThongTinDonHang(String tenNguoiNhan, String diaChi, int sdt, String tenSP, Double giaSP, int soLuong, String anhSP) {
        TenNguoiNhan = tenNguoiNhan;
        DiaChi = diaChi;
        Sdt = sdt;
        TenSP = tenSP;
        GiaSP = giaSP;
        SoLuong = soLuong;
        AnhSP = anhSP;
    }

    public String getTenNguoiNhan() {
        return TenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        TenNguoiNhan = tenNguoiNhan;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public int getSdt() {
        return Sdt;
    }

    public void setSdt(int sdt) {
        Sdt = sdt;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public Double getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(Double giaSP) {
        GiaSP = giaSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getAnhSP() {
        return AnhSP;
    }

    public void setAnhSP(String anhSP) {
        AnhSP = anhSP;
    }
}
