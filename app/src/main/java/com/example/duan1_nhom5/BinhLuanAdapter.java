package com.example.duan1_nhom5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Base64;

public class BinhLuanAdapter extends FirebaseRecyclerAdapter<BinhLuan, BinhLuanAdapter.BinhLuanViewHolder> {

    FirebaseAuth firebaseAuth;
    public BinhLuanAdapter(@NonNull FirebaseRecyclerOptions<BinhLuan> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BinhLuanViewHolder holder, int i, @NonNull BinhLuan binhLuan) {
        holder.ten.setText(""+binhLuan.getUsername());
        holder.nd.setText(""+binhLuan.getNoiDung());
        holder.ngay.setText(""+binhLuan.getNgay());
    }

    @NonNull
    @Override
    public BinhLuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat, parent, false);
        return new BinhLuanAdapter.BinhLuanViewHolder(view);
    }

    class BinhLuanViewHolder extends RecyclerView.ViewHolder {
        TextView ten,nd,ngay;

        public BinhLuanViewHolder(View view) {
            super(view);

            ten =view.findViewById(R.id.tvUser);
            nd = view.findViewById(R.id.tvText);
            ngay = view.findViewById(R.id.tvTime);
        }
    }
}
