package com.example.duan1_nhom5;

import android.app.TaskStackBuilder;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.example.duan1_nhom5.databinding.AppBarMainBinding;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_nhom5.databinding.ActivityMain2Binding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;
    EditText formsearch1;
    ImageView nutsearch;
    ArrayList<DienThoai> dsls = new ArrayList<DienThoai>();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        formsearch1 = binding.appBarMain.toolbar.findViewById(R.id.formsearch);
        nutsearch = binding.appBarMain.toolbar.findViewById(R.id.nutsearch);
        formsearch1.setVisibility(View.INVISIBLE);
        nutsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formsearch1.setVisibility(View.VISIBLE);
                nutsearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        seach(formsearch1.getText().toString());
                        formsearch1.setVisibility(View.INVISIBLE);
                        Fragment mFragment = new DienThoaiFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mFragment).commit();
                    }
                });
            }
        });
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_qlnguoidung,R.id.nav_qldienthoai)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);

//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.home:
//                        Fragment mFragment = new HomeFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mFragment).commit();
//                        break;
//                    case R.id.sanpham:
//                        Fragment mFragment1 = new DienThoaiFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mFragment1).commit();
//                        break;
//                    case R.id.giohang:
//                        Fragment mFragment2 = new GioHangFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mFragment2).commit();
//                        break;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                        Fragment mFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mFragment).commit();
                        break;
                        case R.id.sanpham1:
                        Fragment mFragment1 = new DienThoaiFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mFragment1).commit();
                        break;
                    case R.id.giohang1:
                        Fragment mFragment2 = new GioHangFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mFragment2).commit();
                        break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void seach(String keyword){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("DienThoai");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DienThoai dienThoai = dataSnapshot.getValue(DienThoai.class);
                    if (dienThoai !=null){
                        if (dienThoai.getTen().contains(keyword)){
                            dsls.clear();
                            dsls.add(dienThoai);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}