package com.example.duan1_nhom5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Base64;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private ArrayList<DienThoai> dsm;
    private Context c;
    DienThoai dienThoai;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private thinhhanh thinhhanh;

    public interface thinhhanh{
        void thinhhanh(DienThoai dienThoai);
    }
    public HomeAdapter(Context c, ArrayList<DienThoai> dsm,thinhhanh thinhhanh1) {
        this.thinhhanh = thinhhanh1;
        this.dsm = dsm;
        this.c = c;
    }


    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.topthinhhanh, parent, false);
        HomeViewHolder viewHolder = new HomeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.HomeViewHolder holder, int position) {
        DienThoai lg = dsm.get(position);
        holder.tendt.setText(""+lg.getTen());
        holder.giadt.setText("Giá : "+lg.getGiaTien());
        byte[] manghinh = Base64.getDecoder().decode(lg.getLinkAnh());
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
        holder.anhdt.setImageBitmap(bm);
        holder.anhdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thinhhanh.thinhhanh(lg);
            }
        });
        holder.tendt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thinhhanh.thinhhanh(lg);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView tendt,giadt;
        ImageView anhdt;
        CardView cardView;

        public HomeViewHolder(View view) {
            super(view);

            tendt =view.findViewById(R.id.tendt);
            giadt = view.findViewById(R.id.giadt2);
            anhdt = view.findViewById(R.id.anhdt);
            cardView = view.findViewById(R.id.cardviewdienthoai);


        }
    }
}
