package com.example.duan1_nhom5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;

public class ChiTietDienThoaiFragment extends Fragment {

DienThoai dienThoai;
    TextView ten,gia,chitiet,sotien,noidungchitiet,soluong;
    ImageView anhct,cong,tru;
    Button muahang;
    int id;
    int so = 1;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
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
        sotien = v1.findViewById(R.id.tv_tien_chitiet_dienthoai);
        noidungchitiet = v1.findViewById(R.id.tv_chitiet_dienthoai);
        muahang = v1.findViewById(R.id.btn_muangay);
        soluong = v1.findViewById(R.id.soluong);
        cong = v1.findViewById(R.id.cong);
         TextView tv_log = (TextView) view.findViewById(R.id.tv_chitiet_dienthoai);
        tv_log.setMovementMethod(new ScrollingMovementMethod());

        tru = v1.findViewById(R.id.tru);

        anhct=v1.findViewById(R.id.img_chitiet_dienthoai);

        Bundle bundle = this.getArguments();
        String ten1 = bundle.getString("name");
        int gia = bundle.getInt("gia");
        String cht = bundle.getString("chitiet");
        String anh = bundle.getString("anh");
        int daban = bundle.getInt("daban");
        String key = bundle.getString("keydt");
        byte[] manghinh = Base64.getDecoder().decode(anh);
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
        anhct.setImageBitmap(bm);
        sotien.setText(""+gia);
        ten.setText(ten1);

        noidungchitiet.setText(cht);


        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                so = so+1;
                soluong.setText(so+"");
                int sl = Integer.parseInt(soluong.getText().toString());
                int tinhtong = gia * sl;
                sotien.setText(tinhtong+"");
            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                so = so-1;
                soluong.setText(""+so);
                int sl = Integer.parseInt(soluong.getText().toString());
                int tinhtong = gia * sl;
                sotien.setText(tinhtong+"");
            }
        });


        muahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tengh = ten.getText().toString();
                int giagh = Integer.parseInt(sotien.getText().toString());
                int sogh = Integer.parseInt(soluong.getText().toString());
                byte[] anh=ImageView_To_Byte(anhct);

                String chuoianh = Base64.getEncoder().encodeToString(anh);
                GioHang gioHang = new GioHang(tengh,giagh,sogh,chuoianh,gia,key,daban);

                String uid = firebaseAuth.getInstance().getCurrentUser().getUid();

                databaseReference = FirebaseDatabase.getInstance().getReference();

                databaseReference.child("GioHang").child(uid).push().setValue(gioHang);
                Fragment fragment = new GioHangFragment();
                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                fragment.setArguments(bundle);
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
    public byte[] ImageView_To_Byte(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sanpham, container, false);
    }
}