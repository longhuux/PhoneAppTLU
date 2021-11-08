package com.example.duan1_nhom5;

import android.annotation.SuppressLint;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MemberAdapter extends FirebaseRecyclerAdapter<DangKy,MemberAdapter.ThanhVienViewHolder> {
    private ArrayList<DangKy> dsm;
    DangKy dangKy;
    Context c;
    DatabaseReference databaseReference;

    public MemberAdapter(FirebaseRecyclerOptions<DangKy> options) {
        super(options);
    }
    class ThanhVienViewHolder extends RecyclerView.ViewHolder {
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
    @Override
    protected void onBindViewHolder(ThanhVienViewHolder holder, @SuppressLint("RecyclerView") int i, DangKy dangKy) {
        holder.usern.setText("Tên : "+dangKy.getHoTen());
        holder.pquyen.setText("Email : "+dangKy.getEmail());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(dangKy.getEmail());
        user.updatePassword(dangKy.getPassword());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater layoutInflater = ((Activity)v.getContext()).getLayoutInflater();
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

                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("NguoiDung").child(getRef(i).getKey()).updateChildren(dangKy.toMap(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(v1.getContext(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
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
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1=new AlertDialog.Builder(v.getContext());
                builder1.setMessage("Bạn Có Chắc Chắn Muốn Xóa?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("NguoiDung").child(getRef(i).getKey()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(builder1.getContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
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
        });
    }

    @Override
    public ThanhVienViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.two_item, parent, false);
        return new ThanhVienViewHolder(view);
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
