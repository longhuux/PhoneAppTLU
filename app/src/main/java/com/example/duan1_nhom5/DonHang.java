package com.example.duan1_nhom5;

public class DonHang {
    private String Tennguoimua;
    private String Diachi;
    private int Sdt;
    private String Tensanpham;
    private Double GiaDonHang;
    private int SoLuong;
    private String AnhDonHang;
    private String Trangthai;

    public DonHang() {
    }

    public DonHang(String tennguoimua, String diachi, int sdt, String tensanpham, Double giaDonHang, int soLuong, String anhDonHang, String trangthai) {
        Tennguoimua = tennguoimua;
        Diachi = diachi;
        Sdt = sdt;
        Tensanpham = tensanpham;
        GiaDonHang = giaDonHang;
        SoLuong = soLuong;
        AnhDonHang = anhDonHang;
        Trangthai = trangthai;
    }

    public String getTennguoimua() {
        return Tennguoimua;
    }

    public void setTennguoimua(String tennguoimua) {
        Tennguoimua = tennguoimua;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public int getSdt() {
        return Sdt;
    }

    public void setSdt(int sdt) {
        Sdt = sdt;
    }

    public String getTensanpham() {
        return Tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        Tensanpham = tensanpham;
    }

    public Double getGiaDonHang() {
        return GiaDonHang;
    }

    public void setGiaDonHang(Double giaDonHang) {
        GiaDonHang = giaDonHang;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getAnhDonHang() {
        return AnhDonHang;
    }

    public void setAnhDonHang(String anhDonHang) {
        AnhDonHang = anhDonHang;
    }

    public String getTrangthai() {
        return Trangthai;
    }

    public void setTrangthai(String trangthai) {
        Trangthai = trangthai;
    }
}

