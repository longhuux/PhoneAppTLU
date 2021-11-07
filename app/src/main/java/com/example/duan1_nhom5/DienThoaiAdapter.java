package com.example.duan1_nhom5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Base64;

public class DienThoaiAdapter extends RecyclerView.Adapter<DienThoaiAdapter.DienThoaiViewHolder> {
    private ArrayList<DienThoai> dsm;
    private Context c;
    DienThoai dienThoai;
    DatabaseReference databaseReference;
    private FragmentTransaction fragmentTransaction;
    private hienChiTiet hienChiTiet;

    public DienThoaiAdapter(Context context, ArrayList<DienThoai> dsls, DienThoaiAdapter.hienChiTiet aTrue) {
        this.dsm = dsls;
        this.c = context;
        this.hienChiTiet = aTrue;
    }


    public interface hienChiTiet{
        void chuyenFragment(DienThoai dienThoai);

    }


    @Override
    public DienThoaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.dienthoai_item, parent, false);
        DienThoaiViewHolder viewHolder = new DienThoaiViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DienThoaiViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DienThoai lg = dsm.get(position);
        holder.tendt.setText(""+lg.getTen());
        holder.giadt.setText("Gia : "+lg.getGiaTien());

        byte[] manghinh = Base64.getDecoder().decode(lg.getLinkAnh());
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
        holder.anhdt.setImageBitmap(bm);
        holder.anhdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hienChiTiet.chuyenFragment(lg);

            }
        });





    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }

    public class DienThoaiViewHolder extends RecyclerView.ViewHolder {
        TextView tendt,giadt;
        ImageView anhdt;
        CardView cardView;

        public DienThoaiViewHolder(View view) {
            super(view);

            tendt =view.findViewById(R.id.tendt1);
            giadt = view.findViewById(R.id.giadt1);
            anhdt = view.findViewById(R.id.anhdt1);
            cardView = view.findViewById(R.id.cardviewdienthoai1);


        }
    }


    private void getlist(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("DienThoai");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DienThoai dienThoai = dataSnapshot.getValue(DienThoai.class);
                    dsm.add(dienThoai);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }
