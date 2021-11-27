package com.example.duan1_nhom5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.TaiKhoanViewHolder>{
private List<DangKy> dsm;
FirebaseAuth firebaseAuth;
private Context c;
    public TaiKhoanAdapter(Context c, ArrayList<DangKy> dsm) {
        this.dsm = dsm;
        this.c = c;
    }
    @NonNull
    @Override
    public TaiKhoanAdapter.TaiKhoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_settings, parent, false);
        TaiKhoanAdapter.TaiKhoanViewHolder viewHolder = new TaiKhoanAdapter.TaiKhoanViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaiKhoanAdapter.TaiKhoanViewHolder holder, int position) {
        DangKy dangKy = dsm.get(position);
        holder.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.getInstance().signOut();
            }
        });
        holder.mail.setText(dangKy.getEmail());
        holder.tennd.setText(dangKy.getHoTen());
        holder.diachi1.setText(dangKy.getDiaChi());
        holder.sdtnd.setText(""+dangKy.getSDT());
        holder.quyennd.setText(""+dangKy.getPhanQuyen());
    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }

    public class TaiKhoanViewHolder extends RecyclerView.ViewHolder {
        TextView logout;
        MaterialTextView mail,diachi1,sdtnd,tennd,quyennd;

        public TaiKhoanViewHolder(View view) {
            super(view);

             logout = view.findViewById(R.id.logout);
             mail = view.findViewById(R.id.emailnd);
             diachi1 = view.findViewById(R.id.diachind);
             sdtnd = view.findViewById(R.id.sdtnd);
             tennd = view.findViewById(R.id.tennd);
            quyennd = view.findViewById(R.id.quyennd);

        }
    }

}
