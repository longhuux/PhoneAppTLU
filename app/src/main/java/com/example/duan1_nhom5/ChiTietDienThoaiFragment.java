package com.example.duan1_nhom5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChiTietDienThoaiFragment extends Fragment {

DienThoaiAdapter adapter;
DienThoai dienThoai;
    TextView ten,gia,chitiet,sotien,noidungchitiet;
    ImageView anhct;
    Button muahang;
    int vitri ;
    ArrayList<DienThoai> dsm = new ArrayList<DienThoai>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChiTietDienThoaiFragment() {
        // Required empty public constructor
    }

    public static ChiTietDienThoaiFragment newInstance(String param1, String param2) {
        ChiTietDienThoaiFragment fragment = new ChiTietDienThoaiFragment();
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
        View v1=view;
        ten = v1.findViewById(R.id.tv_name_chitiet_dienthoai);
        gia = v1.findViewById(R.id.tv_gia_chitiet_dienthoai);
        chitiet = v1.findViewById(R.id.tv_mota_chitiet_dienthoai);
        sotien = v1.findViewById(R.id.tv_tien_chitiet_dienthoai);
        noidungchitiet = v1.findViewById(R.id.tv_chitiet_dienthoai);
        muahang = v1.findViewById(R.id.btn_muangay);
        anhct=v1.findViewById(R.id.anhdtct);

        Bundle bundle = this.getArguments();
        String ten1 = bundle.getString("name");
        Double gia = bundle.getDouble("gia");
        String cht = bundle.getString("chitiet");

        ten.setText(ten1);
        sotien.setText(gia+"");
        noidungchitiet.setText(cht);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dienthoai_chitiet, container, false);
    }

    public void hien(DienThoai dienThoai) {


    }
}