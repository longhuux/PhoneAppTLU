package com.example.duan1_nhom5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Base64;

public class ThongTinDonHangFragment extends Fragment {

    EditText nhapten,nhapdiachi,nhapsdt;
    RadioButton thanhtoan;
    Button xacnhan,trove;
    DatabaseReference databaseReference;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ThongTinDonHangFragment() {
        // Required empty public constructor
    }

    public static ThongTinDonHangFragment newInstance(String param1, String param2) {
        ThongTinDonHangFragment fragment = new ThongTinDonHangFragment();
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
        nhapten = view.findViewById(R.id.nhaptenngmua);
        nhapdiachi = view.findViewById(R.id.nhapdiachi);
        nhapsdt = view.findViewById(R.id.nhapsodt);
        thanhtoan = view.findViewById(R.id.rdthanhtoan);
        xacnhan = view.findViewById(R.id.btn_xacnhan);
        trove = view.findViewById(R.id.btn_trove);

        //lấy thông tin từ bundle
        Bundle bundle = this.getArguments();
        String tensp = bundle.getString("tengh");
        Double giasp = bundle.getDouble("giagh");
        int soluong = Integer.parseInt(bundle.getString("soluong"));
        String anhsp = bundle.getString("anhgh");

        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenngnhan = nhapten.getText().toString();
                String diachi = nhapdiachi.getText().toString();
                int sdt = Integer.parseInt(nhapsdt.getText().toString());
                String trangthai = "Chờ Xác Nhận";
                ThongTinDonHang donHang = new ThongTinDonHang(tenngnhan,diachi,sdt,tensp,giasp,soluong,anhsp,trangthai);
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("ThongTinDonHang").push().setValue(donHang);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Toast.makeText(getContext(), "Thanh toan thanh cong ", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new DonHangCuaToiFragment();
                        FragmentManager fmgr =getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fmgr.beginTransaction();
                        ft.replace(R.id.nav_host_fragment_content_main, fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.addToBackStack(null);
                        ft.commit();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });





        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thongtin, container, false);
    }
}