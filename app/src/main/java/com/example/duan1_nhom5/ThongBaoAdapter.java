package com.example.duan1_nhom5;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ThongBaoViewHolder> {
    private ArrayList<ThongBao> dsm;
    private Context c;
    public ThongBaoAdapter(Context c, ArrayList<ThongBao> dsm) {
        this.dsm = dsm;
        this.c = c;
    }


    @Override
    public ThongBaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.thongbao_item, parent, false);
        ThongBaoViewHolder viewHolder = new ThongBaoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ThongBaoAdapter.ThongBaoViewHolder holder, int position) {
        ThongBao lg = dsm.get(position);
        holder.noidung.setText(lg.getNoiDung());
        holder.tieude.setText(lg.getTieuDe());

    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }

    public class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        TextView ngay,noidung,tieude;
        CardView cardView;

        public ThongBaoViewHolder(View view) {
            super(view);
            tieude = view.findViewById(R.id.tieudetb);
            noidung = view.findViewById(R.id.noidungtb);
            cardView = view.findViewById(R.id.cardviewloai21);

        }
    }
}
