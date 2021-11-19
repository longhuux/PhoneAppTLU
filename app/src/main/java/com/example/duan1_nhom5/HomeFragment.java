package com.example.duan1_nhom5;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    DatabaseReference databaseReference;
    ArrayList<DienThoai> dsls = new ArrayList<DienThoai>();
    HomeAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        ImageView anhslide = view.findViewById(R.id.anhslide);
        anhslide.setBackgroundResource(R.drawable.slide);
        RecyclerView recyclerView = view.findViewById(R.id.rvtop);
        RecyclerView recyclerView1 = view.findViewById(R.id.rvbanchay);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setLayoutManager(layoutManager);
        dsls.clear();
        getlist();

        adapter = new HomeAdapter(getContext(), dsls);
        recyclerView.setAdapter(adapter);
        recyclerView1.setAdapter(adapter);

        AnimationDrawable drawable1 = (AnimationDrawable) anhslide.getBackground();
            drawable1.start();
        super.onViewCreated(view, savedInstanceState);
    }
    private void getlist(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("DienThoai");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    databaseReference.child(dataSnapshot.getKey()).orderByChild( "daBan").limitToFirst(10);
                    DienThoai dienThoai = dataSnapshot.getValue(DienThoai.class);

                    dsls.add(dienThoai);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home2, container, false);
    }

}
