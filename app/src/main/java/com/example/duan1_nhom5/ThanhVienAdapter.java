package com.example.duan1_nhom5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    private ArrayList<DangKy> dsm;
    private Context c;
    DangKy dangKy;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    public ThanhVienAdapter(Context c, ArrayList<DangKy> dsm) {
        this.dsm = dsm;
        this.c = c;
    }


    @Override
    public ThanhVienViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.two_item, parent, false);
        ThanhVienViewHolder viewHolder = new ThanhVienViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ThanhVienViewHolder holder, int position) {
        DangKy lg = dsm.get(position);
        holder.usern.setText("Tên : "+lg.getHoTen());
        holder.pquyen.setText("Email : "+lg.getEmail());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openDialogUpdate(lg);

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(lg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }

    public class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        TextView usern,pquyen;
        ImageView edit,delete;
        CardView cardView;

        public ThanhVienViewHolder(View view) {
            super(view);

            usern =view.findViewById(R.id.idls);
            pquyen = view.findViewById(R.id.tenls);
            edit = view.findViewById(R.id.editls);
            delete = view.findViewById(R.id.deletels);
            cardView = view.findViewById(R.id.cardviewloai2);


        }
    }

    public void openDialogUpdate(DangKy dangKy){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater layoutInflater = ((Activity)c).getLayoutInflater();
        View v1 = layoutInflater.inflate(R.layout.updatetv,null);
        builder.setView(v1);
        AlertDialog dialog = builder.create();
        dialog.show();
        TextView tvls;
        EditText nhaptenls,nhapemail,nhapmk;

        Button capnhat,huy;
        tvls = v1.findViewById(R.id.tvudls);
        nhaptenls = v1.findViewById(R.id.nhaptenls);
        nhapemail = v1.findViewById(R.id.nhapemaills);
        nhapmk = v1.findViewById(R.id.nhapmk);
        capnhat = v1.findViewById(R.id.capnhatls);
        huy=v1.findViewById(R.id.huyls);
        nhaptenls.setText(dangKy.getHoTen());
        nhapemail.setText(dangKy.getEmail());
        nhapmk.setText(dangKy.getPassword());

        //update du lieu tu dialog
        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKy.setHoTen(nhaptenls.getText().toString());
                dangKy.setEmail(nhapemail.getText().toString());
                dangKy.setPassword(nhapmk.getText().toString());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.updateEmail(nhapemail.getText().toString());
                user.updatePassword(nhapmk.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("NguoiDung").child(String.valueOf(dangKy.getId())).updateChildren(dangKy.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(v1.getContext(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                        dsm.clear();
                        getlist();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void delete(DangKy dangKy){
        AlertDialog.Builder builder1=new AlertDialog.Builder(c);
        builder1.setMessage("Bạn Có Chắc Chắn Muốn Xóa?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("NguoiDung").child(String.valueOf(dangKy.getId())).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(builder1.getContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();

                        dsm.clear();
                        getlist();
                        dialog.dismiss();
                    }
                });

            }
        });
        builder1.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder1.create();
        alertDialog.show();
    }

    private void getlist(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("NguoiDung");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DangKy dangKy = dataSnapshot.getValue(DangKy.class);
                    dsm.add(dangKy);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }
