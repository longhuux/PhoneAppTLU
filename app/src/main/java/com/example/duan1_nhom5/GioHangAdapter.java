package com.example.duan1_nhom5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DecimalFormat;
import java.util.Base64;

public class GioHangAdapter extends FirebaseRecyclerAdapter<GioHang,GioHangAdapter.GioHangViewHolder> {
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    public GioHangAdapter(FirebaseRecyclerOptions<GioHang> options) {
       //this.truyenDuLieu = truyenDuLieu;
        super(options);
    }

    @Override
    protected void onBindViewHolder( GioHangViewHolder holder, int i,GioHang gioHang) {
        int tinhtong = gioHang.getGiaGioHang() * gioHang.getSoLuong();
        holder.ten.setText(""+gioHang.getTenGioHang());
        holder.gia.setText(""+formatter.format(tinhtong));
        holder.soluong.setText(""+gioHang.getSoLuong());
        byte[] manghinh = Base64.getDecoder().decode(gioHang.getAnhGioHang());
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
        holder.anh.setImageBitmap(bm);
        if(holder.select.isChecked()){
            Fragment fragment = new ThongTinDonHangFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tengh",gioHang.getTenGioHang());
            bundle.putInt("giagh",gioHang.getGiaGioHang());
            bundle.putString("soluong", String.valueOf(gioHang.getSoLuong()));
            bundle.putString("anhgh",gioHang.getAnhGioHang());
            fragment.setArguments(bundle);

        }else {

        }


    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new GioHangAdapter.GioHangViewHolder(view);
    }

      static class GioHangViewHolder extends RecyclerView.ViewHolder {
        TextView ten,gia,soluong;
        ImageView anh;
        ImageView cong,tru;
          CheckBox select;
        int so=1;

        public GioHangViewHolder(View view) {
            super(view);

            ten =view.findViewById(R.id.tv_name_giohang);
            gia = view.findViewById(R.id.tv_gia_giohang);
            anh = view.findViewById(R.id.img_giohang);
            soluong = view.findViewById(R.id.soluonggio);
            cong = view.findViewById(R.id.conggio);
            tru = view.findViewById(R.id.trugio);
            select = view.findViewById(R.id.select111);

            cong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    so = so+1;
                    soluong.setText(so+"");
                }
            });
            tru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    so = so-1;
                    soluong.setText(so+"");
                }
            });




        }
    }
}
