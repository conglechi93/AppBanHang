package com.midterm.shopapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.midterm.shopapp.R;
import com.midterm.shopapp.adapter.GioHangAdapter;
import com.midterm.shopapp.ulti.CheckConnection;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {

    ListView lvgiohang;
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan,btnTiepTucMua;
    Toolbar toolbargiohang;
    GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        ActionToolBar();
        CheckData();
        EvenUltil();
        EventButton();
    }

    private void EventButton() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(),Thongtinkhachhang.class);
                    startActivity(intent);
                }
                else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Giỏ hàng của bạn còn trống");
                }
            }
        });
    }


    public static void EvenUltil() {
        long tongtien = 0;
        for(int i = 0; i< MainActivity.manggiohang.size();i++){
            tongtien+=MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongtien) + " VNĐ");
    }

    private void CheckData() {
        if(MainActivity.manggiohang.size() <= 0){
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }
        else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        toolbargiohang = (Toolbar) findViewById(R.id.idtoolbargiohang);
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void AnhXa() {
        lvgiohang = (ListView) findViewById(R.id.listviewgiohang);
        txtThongBao = (TextView) findViewById(R.id.textviewthongbao);
        txtTongTien= (TextView) findViewById(R.id.textviewtongtien);
        btnThanhToan = (Button) findViewById(R.id.buttonthanhtoangiohang);
        btnTiepTucMua = (Button) findViewById(R.id.buttontieptucmuahang);
        gioHangAdapter = new GioHangAdapter(GioHang.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(gioHangAdapter);


        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này ?");
                builder.setPositiveButton("Có", (dialog, which) -> {
                    if(MainActivity.manggiohang.size() <= 0){
                        txtThongBao.setVisibility(View.VISIBLE);
                    }
                    else {
                        MainActivity.manggiohang.remove(position);
                        gioHangAdapter.notifyDataSetChanged();
                        EvenUltil();
                        if(MainActivity.manggiohang.size() <= 0) {
                            txtThongBao.setVisibility(View.VISIBLE);
                        }
                        else {
                            txtThongBao.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                builder.setNegativeButton("Không", (dialog, which) -> {
                    gioHangAdapter.notifyDataSetChanged();
                    EvenUltil();
                });
                builder.show();
                return true;
            }
        });
    }
}