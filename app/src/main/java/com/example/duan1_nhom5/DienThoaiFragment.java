package com.example.duan1_nhom5;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DienThoaiFragment extends Fragment {

    DienThoai dienThoai;
    ArrayList<DienThoai> dsls = new ArrayList<DienThoai>();
    DienThoaiAdapter adapter;
    DatabaseReference databaseReference;
    TextView tao;
    EditText nhaptendt,nhapgiadt,nhapchitiet;
    Button regdt, huy;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DienThoaiFragment() {
        // Required empty public constructor
    }

    public static DienThoaiFragment newInstance(String param1, String param2) {
        DienThoaiFragment fragment = new DienThoaiFragment();
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
        RecyclerView recyclerView = view.findViewById(R.id.reviewdt);
        FloatingActionButton button = view.findViewById(R.id.floating);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        getlist();

        adapter = new DienThoaiAdapter(getContext(), dsls, new DienThoaiAdapter.hienChiTiet() {

            @Override
            public void chuyenFragment(DienThoai dienThoai) {
                Fragment fragment = new ChiTietDienThoaiFragment();
                FragmentManager fmgr =getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("name",dienThoai.getTen());
                bundle.putDouble("gia",dienThoai.getGiaTien());
                bundle.putString("chitiet",dienThoai.getChiTiet());
                fragment.setArguments(bundle);
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }

        });
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogAdd();
            }
        });

    }
    public void openDialogAdd () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View v1 = layoutInflater.inflate(R.layout.adddienthoai, null);
        builder.setView(v1);
        AlertDialog dialog = builder.create();
        dialog.show();
        //khai bao
        tao = v1.findViewById(R.id.tvadd);
        nhaptendt = v1.findViewById(R.id.nhaptendt);
        nhapgiadt = v1.findViewById(R.id.nhapgiadt);
        nhapchitiet = v1.findViewById(R.id.nhapctdt);
        regdt = v1.findViewById(R.id.adddt);
        huy = v1.findViewById(R.id.huyadddt);

        //xử lý click
        regdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tendt = nhaptendt.getText().toString();
                Double giadt = Double.valueOf(nhapgiadt.getText().toString());
                String chitiet = nhapchitiet.getText().toString();
                String anh = "anh";
                int id = dsls.size()+1;
                DienThoai dienThoai = new DienThoai(id,tendt,chitiet,giadt,anh);
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("DienThoai").child(String.valueOf(id)).setValue(dienThoai);
                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Toast.makeText(getContext(), "Thêm Điện Thoại Thành Công", Toast.LENGTH_SHORT).show();
                        getlist();
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                getlist();
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void getlist(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("DienThoai");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DienThoai dienThoai = dataSnapshot.getValue(DienThoai.class);
                    dsls.add(dienThoai);
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
        return inflater.inflate(R.layout.fragment_dien_thoai, container, false);
    }
}