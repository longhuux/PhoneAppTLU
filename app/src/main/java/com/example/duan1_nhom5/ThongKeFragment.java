package com.example.duan1_nhom5;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ThongKeFragment extends Fragment {

    DatabaseReference databaseReference;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    TextView tongdoanhthu,tongdonhang,ngaybd,ngaykt,tongtv,tongsp,tinhtong,sobl;
    SimpleDateFormat spfm1 = new SimpleDateFormat("ddMMyyyy");
    int mYear;
    int mMonth;
    int mDay;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongKeFragment() {
        // Required empty public constructor
    }

    public static ThongKeFragment newInstance(String param1, String param2) {
        ThongKeFragment fragment = new ThongKeFragment();
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
        tongdoanhthu = view.findViewById(R.id.tongdoanhthu);
        tongdonhang = view.findViewById(R.id.tongdonhang);
        tongtv = view.findViewById(R.id.tongthanhvien);
        sobl = view.findViewById(R.id.sobinhluan);
        tinhtong = view.findViewById(R.id.tinhtong);
        tongsp = view.findViewById(R.id.tongsanpham);
        ngaybd = view.findViewById(R.id.ngaybd);
        ngaykt = view.findViewById(R.id.ngaykt);

        getDuLieu();
        getthanhvien();
        getsanpham();
        getbinhluan();

        DatePickerDialog.OnDateSetListener mDatetungay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                mDay = day;
                mMonth = month;
                mYear = year;
                GregorianCalendar c = new GregorianCalendar(mDay,mMonth,mYear);
                ngaybd.setText(spfm1.format(c.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener mDatedenngay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                mDay = day;
                mMonth = month;
                mYear = year;
                GregorianCalendar c = new GregorianCalendar(mDay,mMonth,mYear);
                ngaykt.setText(spfm1.format(c.getTime()));
            }
        };

        ngaybd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0,mDatetungay, mDay,mMonth,mYear);
                d.show();
            }
        });
        ngaykt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0,mDatedenngay, mDay,mMonth,mYear);
                d.show();


            }
        });
        tinhtong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinhTheoNgay();
            }
        });


        ValueLineChart mCubicValueLineChart = (ValueLineChart) view.findViewById(R.id.cubiclinechart);

        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);

        series.addPoint(new ValueLinePoint("1", 50000));
        series.addPoint(new ValueLinePoint("2", 3.4f));
        series.addPoint(new ValueLinePoint("3", .4f));
        series.addPoint(new ValueLinePoint("4", 1.2f));
        series.addPoint(new ValueLinePoint("5", 2.6f));
        series.addPoint(new ValueLinePoint("6", 1.0f));
        series.addPoint(new ValueLinePoint("̀7", 3.5f));
        series.addPoint(new ValueLinePoint("8", 2.4f));
        series.addPoint(new ValueLinePoint("9", 2.4f));
        series.addPoint(new ValueLinePoint("10", 3.4f));
        series.addPoint(new ValueLinePoint("11", .4f));
        series.addPoint(new ValueLinePoint("12", 1.3f));

        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }


    private void getDuLieu(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("ThongTinDonHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int tong = 0;
                for (DataSnapshot dataSnapshot:snapshot.getChildren() ){
                    tongdonhang.setText(""+dataSnapshot.getChildrenCount()+" Đơn Hàng");
                    for (DataSnapshot snapshot1:dataSnapshot.getChildren() ) {
                        ThongTinDonHang donHang = snapshot1.getValue(ThongTinDonHang.class);
                        tong += donHang.getGiaSP();
                        Log.d("Tong Doanh Thu", String.valueOf(tong));
                        tongdoanhthu.setText("" + formatter.format(tong) + " VNĐ");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void tinhTheoNgay(){
        String tungay1 = ngaybd.getText().toString().trim();
        String denngay1 = ngaykt.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("ThongTinDonHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    for (DataSnapshot snapshot1:dataSnapshot.getChildren()){
                        Query query = databaseReference.orderByChild("ngaydat").startAt(tungay1).endAt(denngay1);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot12) {
                                int tong = 0;
                                ThongTinDonHang donHang = snapshot1.getValue(ThongTinDonHang.class);
                                    tong += donHang.getGiaSP();
                                    Log.d("Tong Doanh Thu Theo Ngay", String.valueOf(tong));
                                    tongdoanhthu.setText("" + formatter.format(tong) + " VNĐ");

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getthanhvien(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("NguoiDung").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tongtv.setText(""+snapshot.getChildrenCount()+" Thành Viên");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getsanpham(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("DienThoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tongsp.setText(""+snapshot.getChildrenCount()+" Sản Phẩm");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getbinhluan(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("DienThoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    for (DataSnapshot snapshot1:dataSnapshot.getChildren()){
                        sobl.setText("Lượt Đánh Giá: "+snapshot1.getChildrenCount());

                    }
                }

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
        return inflater.inflate(R.layout.fragment_thongke, container, false);
    }
}