package com.example.duan1_nhom5;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;

public class DienThoaiFragment extends Fragment {

    DienThoai dienThoai;
    ArrayList<DienThoai> dsls = new ArrayList<DienThoai>();
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String ttt ;
    TextView tao;
    boolean check=false;
    EditText nhaptendt,nhapgiadt,nhapchitiet;
    Button regdt, huy;
    ImageView themanh;
    RecyclerView recyclerView;

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
        dsls.clear();

        recyclerView = view.findViewById(R.id.reviewdt);
        FloatingActionButton button = view.findViewById(R.id.floating);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogAdd();
            }
        });



    }
    public void openDialogAdd () {
      DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
              .setContentHolder(new ViewHolder(R.layout.adddienthoai))
              .setExpanded(true,1200)
              .create();
      View v1 = dialogPlus.getHolderView();
        dialogPlus.show();
        //khai bao
        tao = v1.findViewById(R.id.tvadd);
        nhaptendt = v1.findViewById(R.id.nhaptendt);
        nhapgiadt = v1.findViewById(R.id.nhapgiadt);
        nhapchitiet = v1.findViewById(R.id.nhapctdt);
        regdt = v1.findViewById(R.id.adddt);
        themanh = v1.findViewById(R.id.themanh);
        huy = v1.findViewById(R.id.huyadddt);

        themanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pick=new Intent(Intent.ACTION_GET_CONTENT);
                pick.setType("image/*");
                Intent pho=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent chosser=Intent.createChooser(pick, "Lựa Chọn");
                chosser.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{pho});
                startActivityForResult(chosser, 999);
            }
        });
        //xử lý click
        regdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tendt = nhaptendt.getText().toString();
                int giadt = Integer.parseInt(nhapgiadt.getText().toString());
                String chitiet = nhapchitiet.getText().toString();
                byte[] anh=ImageView_To_Byte(themanh);
                String chuoianh = Base64.getEncoder().encodeToString(anh);
                int id = dsls.size()+1;
                int DaBan = 0;
                int SoLike = 0;
                DienThoai dienThoai = new DienThoai(id,tendt,chitiet,giadt,chuoianh,DaBan,SoLike);
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("DienThoai").push().setValue(dienThoai);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Toast.makeText(getContext(), "Thêm Điện Thoại Thành Công", Toast.LENGTH_SHORT).show();

                        dialogPlus.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPlus.dismiss();
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {

            if (data.getExtras() != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                themanh.setImageBitmap(imageBitmap);
            } else {
                Uri uri = data.getData();
                themanh.setImageURI(uri);
            }

        }
    }

    @Override
    public void onStart() {
        FirebaseRecyclerOptions<DienThoai> options =
                new FirebaseRecyclerOptions.Builder<DienThoai>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("DienThoai")
                                , DienThoai.class)
                        .build();


        FirebaseRecyclerAdapter<DienThoai, DienThoaiFragment.DienThoaiViewHolder> adapter =
                new FirebaseRecyclerAdapter<DienThoai, DienThoaiFragment.DienThoaiViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull DienThoaiFragment.DienThoaiViewHolder holder, @SuppressLint("RecyclerView") int i, @NonNull DienThoai dienThoai) {

                        holder.tendt.setText("" + dienThoai.getTen());
                        holder.giadt.setText("Giá : " + dienThoai.getGiaTien());
                        holder.ct.setText("" + dienThoai.getChiTiet());
                        holder.solike.setText("" + dienThoai.getSoLike());

                        byte[] manghinh = Base64.getDecoder().decode(dienThoai.getLinkAnh());
                        Bitmap bm = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
                        holder.anhdt.setImageBitmap(bm);
                        holder.anhdt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment fragment = new ChiTietDienThoaiFragment();
                                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                                Bundle bundle = new Bundle();
                                bundle.putString("name", dienThoai.getTen());
                                bundle.putInt("gia", dienThoai.getGiaTien());
                                bundle.putString("chitiet", dienThoai.getChiTiet());
                                bundle.putString("anh", dienThoai.getLinkAnh());
                                bundle.putInt("tim", dienThoai.getSoLike());
                                bundle.putInt("daban", dienThoai.getDaBan());
                                bundle.putString("keydt",getRef(i).getKey());
                                fragment.setArguments(bundle);
                                FragmentTransaction ft = fmgr.beginTransaction();
                                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        });


                        holder.tim.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int conglike = dienThoai.getSoLike() + 1;
                                holder.tim.setEnabled(false);
                                holder.tim.setImageResource(R.drawable.heartred);
                                databaseReference = FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("DienThoai").child(getRef(i).getKey()).child("soLike").setValue(conglike);
                                holder.solike.setText("" + conglike);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public DienThoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dienthoai, parent, false);
                        return new DienThoaiViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
        super.onStart();
        onResume();
        onPause();


    }

    public static class DienThoaiViewHolder extends RecyclerView.ViewHolder {
        TextView tendt,giadt,ct,solike;
        ImageView anhdt,tim;

        public DienThoaiViewHolder(View view) {
            super(view);

            tendt =view.findViewById(R.id.itemten);
            ct =view.findViewById(R.id.itemct);
            giadt = view.findViewById(R.id.itemgia);
            anhdt = view.findViewById(R.id.itemsp);
            solike = view.findViewById(R.id.sotim);
            tim = view.findViewById(R.id.tim);


        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dien_thoai, container, false);
    }
}