package com.example.duan1_nhom5;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.TaiKhoanViewHolder>{
private List<DangKy> dsm;
FirebaseAuth firebaseAuth;
DatabaseReference databaseReference;
private Context c;
private chuyenFrag chuyenFrag;
public interface chuyenFrag{
    void chuyenFrag();
}
    public TaiKhoanAdapter(Context c, ArrayList<DangKy> dsm,chuyenFrag chuyenFrag) {
        this.chuyenFrag = chuyenFrag;
        this.dsm = dsm;
        this.c = c;
    }
    @NonNull
    @Override
    public TaiKhoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.thongtin, parent, false);
        TaiKhoanViewHolder viewHolder = new TaiKhoanViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaiKhoanViewHolder holder, int position) {

        DangKy dangKy = dsm.get(position);
        holder.tennd.setText(dangKy.getHoTen());
        holder.editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(c);
                        LayoutInflater layoutInflater = ((Activity)c).getLayoutInflater();
                        View v2 = layoutInflater.inflate(R.layout.doisdt,null);
                        builder.setView(v2);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        EditText dtcu , dtmoi, lsm , diachimoi;
                        Button suaa ,xoaa;
                        dtcu =v2.findViewById(R.id.sdtcu);
                        dtmoi = v2.findViewById(R.id.sdtmoi);
                        lsm = v2.findViewById(R.id.nhaplaisdt);
                        diachimoi = v2.findViewById(R.id.doiiachi);
                        suaa = v2.findViewById(R.id.capnhatls);
                        xoaa = v2.findViewById(R.id.huyls);

                        dtcu.setText(dangKy.getEmail());
                        dtmoi.setText(""+dangKy.getSDT());
                        lsm.setText(dangKy.getHoTen());
                        diachimoi.setText(dangKy.getDiaChi());
                        suaa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dangKy.setEmail(dtcu.getText().toString());
                                dangKy.setSDT(Integer.parseInt(dtmoi.getText().toString()));
                                dangKy.setHoTen(lsm.getText().toString());
                                dangKy.setDiaChi(diachimoi.getText().toString());
                                String layemail = dtcu.getText().toString();

                                databaseReference = FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("NguoiDung").child(dangKy.getId() ).updateChildren(dangKy.toMap(), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        user.updateEmail(layemail);
                                        Toast.makeText(c, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });


                            }
                        });
                        xoaa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                    }
                });

        holder.doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                LayoutInflater layoutInflater = ((Activity) c).getLayoutInflater();
                View v1 = layoutInflater.inflate(R.layout.doimatkhau, null);
                builder.setView(v1);
                AlertDialog dialog = builder.create();
                dialog.show();
                EditText  matkhaucu , matkhaumoi ,nhaplaimkmoi;
                Button capnhat , huy ;
                matkhaumoi = v1.findViewById(R.id.mkmoi);
                matkhaucu = v1.findViewById(R.id.mkcu);
                nhaplaimkmoi = v1.findViewById(R.id.nhaplaimkmoi);
                capnhat=v1.findViewById(R.id.capnhatmkmoi);
                huy = v1.findViewById(R.id.huydoimk);
                SharedPreferences sharedPref = c.getSharedPreferences("ThongTin", MODE_PRIVATE);
                String  laymkcu=sharedPref.getString("pass","");
                capnhat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mkmoi=matkhaumoi.getText().toString();
                        String nlmk=nhaplaimkmoi.getText().toString();
                        if (laymkcu.equalsIgnoreCase(matkhaucu.getText().toString())){
                            if (mkmoi.equalsIgnoreCase(nlmk)){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updatePassword(mkmoi);


                                databaseReference = FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("NguoiDung").child(dangKy.getId()).child("password").setValue(matkhaumoi.getText().toString() );
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Toast.makeText(c , "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                            else {
                                Toast.makeText(c, "Mật khẩu không trùng", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(c, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        }
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

        holder.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.getInstance().signOut();
                chuyenFrag.chuyenFrag();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }

    public class TaiKhoanViewHolder extends RecyclerView.ViewHolder {
        TextView logout,editprofile,tennd,doimk;
        public TaiKhoanViewHolder(View view) {
            super(view);
            tennd = view.findViewById(R.id.tennd);
            logout = view.findViewById(R.id.tv_logout);
            doimk = view.findViewById(R.id.tv_doimk);
            editprofile = view.findViewById(R.id.tv_editpro);
        }
    }



}
