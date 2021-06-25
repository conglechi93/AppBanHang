package com.midterm.shopapp.model;

public class GioHang {
    public int idsp;
    public String tensp;
    public long giasp;
    public String hinhsp;
    public int soluong;

    public GioHang(int idsp, String tensp, long giasp, String hinhsp, int soluong) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhsp = hinhsp;
        this.soluong = soluong;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public void setGiasp(long giasp) {
        this.giasp = giasp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getIdsp() {
        return idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public long getGiasp() {
        return giasp;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public int getSoluong() {
        return soluong;
    }
}
