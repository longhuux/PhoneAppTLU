package com.example.duan1_nhom5;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;
    ArrayList<DienThoai> ds = new ArrayList<DienThoai>();
    FirebaseAuth firebaseAuth;
    int count;
    int countgio;
    int countdon;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String getuid = firebaseAuth.getInstance().getCurrentUser().getUid();

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_qlnguoidung, R.id.nav_qldienthoai)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Fragment home = new HomeFragment();
        Fragment gio = new GioHangFragment();
        Fragment sp = new DienThoaiFragment();
        Fragment dh = new DonHangCuaToiFragment();
        Fragment tk = new TaiKhoanFragment();

        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Trang Chủ", R.drawable.trangchu, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Sản Phẩm", R.drawable.smartphone, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Giỏ Hàng", R.drawable.giohang, R.color.white);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Đơn Hàng", R.drawable.lichsu, R.color.white);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Profile", R.drawable.toi, R.color.white);

// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);
        bottomNavigation.setAccentColor(Color.parseColor("#DD82A1"));
        bottomNavigation.setInactiveColor(Color.parseColor("#F8F8F8"));
// Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

// Set current item programmatically
        bottomNavigation.setCurrentItem(0);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        // get count bảng dienthoai
        database.child("DienThoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                count = (int) dataSnapshot.getChildrenCount();
                bottomNavigation.setNotification(count +"", 1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //get count bảng giỏ hàng
        database.child("GioHang").child(getuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {

                countgio =(int)dataSnapshot1.getChildrenCount();
                bottomNavigation.setNotification(countgio +"", 2);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //get count bảng đơn hang
        database.child("ThongTinDonHang").child(getuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {

                countdon = (int)dataSnapshot2.getChildrenCount();
                bottomNavigation.setNotification(countdon +"", 3);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
// Add or remove notificati


// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        chuyenFragment(home);
                        break;
                    case 1:
                        chuyenFragment(sp);
                        break;
                    case 2:
                        chuyenFragment(gio);
                        break;
                    case 3:
                        chuyenFragment(dh);
                        break;
                    case 4:
                        chuyenFragment(tk);
                        break;
                }
                return true;
            }
        });
    }
    private void chuyenFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, fragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()) {
            case R.id.timkiem:
                        item.setVisible(false);
                        Fragment mFragment = new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mFragment).commit();
                        break;
            case R.id.lienhe:
                Fragment mFragment3 = new ThongTinDonHangFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mFragment3).commit();
                break;
            case R.id.danhgia:
                Fragment mFragment4 = new ThongTinDonHangFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mFragment4).commit();
                break;
            case R.id.caidat:
                Fragment mFragment5 = new ThongTinDonHangFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mFragment5).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}