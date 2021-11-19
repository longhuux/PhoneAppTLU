package com.example.duan1_nhom5;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThongTinDonHang {
    private String TenNguoiNhan;
    private String DiaChi;
    private int Sdt;
    private String TenSP;
    private int GiaSP;
    private int SoLuong;
    private String AnhSP;
    private String TrangThai;
    private String ngaydat;
    private int GiaDT;
    public ThongTinDonHang() {
    }

    public ThongTinDonHang(String tenNguoiNhan, String diaChi, int sdt, String tenSP, int giaSP, int soLuong, String anhSP, String trangThai, String ngaydat, int giaDT) {
        TenNguoiNhan = tenNguoiNhan;
        DiaChi = diaChi;
        Sdt = sdt;
        TenSP = tenSP;
        GiaSP = giaSP;
        SoLuong = soLuong;
        AnhSP = anhSP;
        TrangThai = trangThai;
        this.ngaydat = ngaydat;
        GiaDT = giaDT;
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

    public int getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(int giaSP) {
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

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public int getGiaDT() {
        return GiaDT;
    }

    public void setGiaDT(int giaDT) {
        GiaDT = giaDT;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("tenNguoiNhan",TenNguoiNhan);
        result.put("diaChi",DiaChi);
        result.put("sdt",Sdt);
        result.put("tenSP",TenSP);
        result.put("giaSP",GiaSP);
        result.put("soLuong",SoLuong);
        result.put("anhSP",AnhSP);
        return result;
    }
}
