package com.example.duan1_nhom5;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Base64;

public class GioHangFragment extends Fragment {

    FirebaseRecyclerAdapter<GioHang,GioHangAdapter.GioHangViewHolder> mFirebaseAdapter;
    RecyclerView recyclerView;
    Button btn_thanhtoan;
    private LinearLayoutManager manager;
    //GioHangAdapter adapter;
    DatabaseReference databaseReference;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GioHangFragment() {
        // Required empty public constructor
    }

    public static GioHangFragment newInstance(String param1, String param2) {
        GioHangFragment fragment = new GioHangFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         btn_thanhtoan = view.findViewById(R.id.btn_thanhtoan);
         recyclerView = view.findViewById(R.id.lv_giohang);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<GioHang> options =
                new FirebaseRecyclerOptions.Builder<GioHang>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("GioHang"), GioHang.class)
                        .build();


        FirebaseRecyclerAdapter<GioHang, GioHangViewHolder> adapter =
                new FirebaseRecyclerAdapter<GioHang, GioHangViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull GioHangViewHolder holder, @SuppressLint("RecyclerView") int i, @NonNull GioHang gioHang) {
                        Double tinhtong = gioHang.getGiaGioHang() * gioHang.getSoLuong();
                        holder.ten.setText(""+gioHang.getTenGioHang());
                        holder.gia.setText("Giá : "+tinhtong);
                        holder.soluong.setText(""+gioHang.getSoLuong());
                        byte[] manghinh = Base64.getDecoder().decode(gioHang.getAnhGioHang());
                        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
                        holder.anh.setImageBitmap(bm);
                        holder.select.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               holder.select.setImageResource(R.drawable.radio_checked);
                               Toast.makeText(getContext(), "Đã Chọn", Toast.LENGTH_SHORT).show();
                               Fragment fragment = new ThongTinDonHangFragment();
                               FragmentManager fmgr =getActivity().getSupportFragmentManager();
                               Bundle bundle = new Bundle();
                               bundle.putString("tengh",gioHang.getTenGioHang());
                               bundle.putDouble("giagh",gioHang.getGiaGioHang());
                               bundle.putString("soluong", String.valueOf(gioHang.getSoLuong()));
                               bundle.putString("anhgh",gioHang.getAnhGioHang());
                               fragment.setArguments(bundle);
                               btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       //Xóa dữ liệu đã chọn trong bảng đã thanh toán
                                       databaseReference = FirebaseDatabase.getInstance().getReference();
                                       databaseReference.child("GioHang").child(getRef(i).getKey()).removeValue();

                                       FragmentTransaction ft = fmgr.beginTransaction();
                                       ft.replace(R.id.nav_host_fragment_content_main, fragment);
                                       ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                       ft.addToBackStack(null);
                                       ft.commit();
                                   }
                               });

                           }
                       });



                    }

                    @NonNull
                    @Override
                    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
                        GioHangViewHolder viewHolder = new GioHangViewHolder(view);
                        return viewHolder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    public static class GioHangViewHolder extends RecyclerView.ViewHolder {
        TextView ten,gia,soluong;
        ImageView anh,select;
        ImageView cong,tru;
        int so=1;

        public GioHangViewHolder(View view) {
            super(view);

            ten =view.findViewById(R.id.tv_name_giohang);
            gia = view.findViewById(R.id.tv_gia_giohang);
            anh = view.findViewById(R.id.img_giohang);
            soluong = view.findViewById(R.id.soluonggio);
            cong = view.findViewById(R.id.conggio);
            tru = view.findViewById(R.id.trugio);
            select = view.findViewById(R.id.select111);

            cong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    so = so+1;
                    soluong.setText(so+"");
                }
            });
            tru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    so = so-1;
                    soluong.setText(so+"");
                }
            });




        }
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gio_hang, container, false);
    }
}