package com.example.duan1_nhom5;

import static android.view.View.VISIBLE;

import android.app.Notification;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.duan1_nhom5.databinding.ContentMainBinding;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;
    ContentMainBinding contentMainBinding ;
    EditText formsearch1;
    ImageView nutsearch;
    ArrayList<DienThoai> ds;
    int count;
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
                formsearch1.setVisibility(VISIBLE);
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

// Add or remove notification for each item
        bottomNavigation.setNotification("7", 1);
        bottomNavigation.setNotification("9", 2);
        bottomNavigation.setNotification("5", 3);

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
                        chuyenFragment(dh);
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
            case R.id.donhang:
                Fragment mFragment3 = new ThongTinDonHangFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mFragment3).commit();
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
                            ds.clear();
                            ds.add(dienThoai);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

       public void getListSanPham() {
           String key = FirebaseDatabase.getInstance().getReference().push().getKey();
           databaseReference = FirebaseDatabase.getInstance().getReference().child("DienThoai").child(key);
           databaseReference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {

                   for (DataSnapshot item : dataSnapshot.getChildren())
                   {
                       count = Math.toIntExact(item.getChildrenCount());
                   }
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

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