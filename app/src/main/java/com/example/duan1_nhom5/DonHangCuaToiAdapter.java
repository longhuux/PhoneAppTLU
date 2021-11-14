package com.example.duan1_nhom5;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Base64;

public class DonHangCuaToiAdapter extends FirebaseRecyclerAdapter<ThongTinDonHang, DonHangCuaToiAdapter.DonHangCuaToiViewHolder> {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String uid = firebaseAuth.getInstance().getCurrentUser().getUid();
    public DonHangCuaToiAdapter(FirebaseRecyclerOptions<ThongTinDonHang> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(DonHangCuaToiViewHolder holder, @SuppressLint("RecyclerView") int i, ThongTinDonHang donHang) {
         holder.tendt.setText(""+donHang.getTenSP());
         holder.gia.setText("Số Tiền: "+donHang.getGiaSP());
         holder.trangthai.setText(""+donHang.getTrangThai());
         holder.tongtien.setText("Tổng Tiền("+donHang.getSoLuong()+" Sản Phẩm): "+donHang.getGiaSP());
        byte[] manghinh = Base64.getDecoder().decode(donHang.getAnhSP());
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
        holder.anh.setImageBitmap(bm);
        if(holder.trangthai.getText().toString().equalsIgnoreCase("Chờ Xác Nhận")) {
            holder.huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                    builder1.setMessage("Bạn Có Chắc Chắn Muốn Hủy?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.trangthai.setText("Đã Hủy");
                            String tt = holder.trangthai.getText().toString();
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.child("ThongTinDonHang").child(uid).child(getRef(i).getKey()).child("trangThai").setValue(tt);

                        }
                    });
                    builder1.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder1.create();
                    alertDialog.show();
                }
            });
        }else {
            holder.huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(holder.itemView.getContext(), "Không Thể Hủy Do Đơn Hàng Này Đã Xác Nhận", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @NonNull
    @Override
    public DonHangCuaToiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new DonHangCuaToiAdapter.DonHangCuaToiViewHolder(view);
    }

      static class DonHangCuaToiViewHolder extends RecyclerView.ViewHolder {
         ImageView anh;
         TextView tendt , gia , trangthai ,tongtien;
         Button huy;


        public DonHangCuaToiViewHolder(View view) {
            super(view);
            anh = view.findViewById(R.id.itemdh);
            tendt = view.findViewById(R.id.itemtendh);
            gia = view.findViewById(R.id.itemgiadh);
            trangthai = view.findViewById(R.id.itemtrangthai);
            tongtien = view.findViewById(R.id.itemtongtien);
            huy = view.findViewById(R.id.btn_huy);



        }
    }
}
