package com.example.duan1_nhom5;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;

public class QuanLyDonHangFragment extends Fragment {
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    ArrayList<ThongTinDonHang> dsdh = new ArrayList<ThongTinDonHang>();
    FirebaseAuth firebaseAuth;
    DataSnapshot dataSnapshot;
    DatabaseReference databaseReference;
    TextView tao;
    EditText nhaptendh,nhapgiadh,nhaptrangthai;
    Button regdt, huy,sua,xoa;
    RecyclerView recyclerView;
    QuanLyDonHangAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public QuanLyDonHangFragment() {
        // Required empty public constructor
    }

    public static QuanLyDonHangFragment newInstance(String param1, String param2) {
        QuanLyDonHangFragment fragment = new QuanLyDonHangFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rvqldh);
        sua = view.findViewById(R.id.suadh);
        xoa = view.findViewById(R.id.xoadh);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        getDuLieu();
        adapter = new QuanLyDonHangAdapter(getContext(),dsdh);
        recyclerView.setAdapter(adapter);

    }

    private void getDuLieu(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("ThongTinDonHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren() ){
                    for (DataSnapshot snapshot1:dataSnapshot.getChildren()){
                        ThongTinDonHang donHang = snapshot1.getValue(ThongTinDonHang.class);
                        dsdh.add(donHang);
                    }
                    adapter.notifyDataSetChanged();
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_ly_don_hang, container, false);
    }
}