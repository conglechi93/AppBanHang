package com.midterm.shopapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.midterm.shopapp.R;
import com.midterm.shopapp.model.GioHang;
import com.midterm.shopapp.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPham extends AppCompatActivity {


    Toolbar toolbardetail;
    ImageView imageViewdetail;
    TextView txtName,txtPrice,txtDespri;
    Spinner spinner;
    Button btndatmua;

    int id = 0 ;
    String NameDetail = "";
    int PriceDetail= 0;
    String ImgDetail ="";
    String DespriDetail = "";
    int IdProduct = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolBar();
        GetInfomation();
        CatchEvenSpinner();
        EventButton();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size() > 0){
                    int slgg = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exit = false;
                    for(int i = 0 ; i < MainActivity.manggiohang.size() ; i++){
                        if (MainActivity.manggiohang.get(i).getIdsp() == id){
                            MainActivity.manggiohang.get(i).setSoluong(MainActivity.manggiohang.get(i).getSoluong() + slgg);
                            if (MainActivity.manggiohang.get(i).getSoluong() >=10){
                                MainActivity.manggiohang.get(i).setSoluong(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(PriceDetail*MainActivity.manggiohang.get(i).getSoluong());
                            exit= true;
                        }
                    }
                    if (exit == false){
                        int slg =Integer.parseInt(spinner.getSelectedItem().toString());
                        long Gianew = slg * PriceDetail;
                        MainActivity.manggiohang.add(new GioHang(id,NameDetail,Gianew,ImgDetail,slg));
                    }
                }else {
                    int slg =Integer.parseInt(spinner.getSelectedItem().toString());
                    long Gianew = slg * PriceDetail;
                    MainActivity.manggiohang.add(new GioHang(id,NameDetail,Gianew,ImgDetail,slg));
                }
                Intent intent = new Intent(getApplicationContext(),com.midterm.shopapp.activity.GioHang.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEvenSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
        spinner.setId(0);

    }

    private void GetInfomation() {

        Sanpham product = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = product.getId();
        NameDetail = product.getTensanpham();
        PriceDetail= product.getGiasanpham();
        ImgDetail = product.getHinhanhsanpham();
        DespriDetail = product.getMotasanpham();
        IdProduct = product.getIdloaisanpham();
        txtName.setText(NameDetail);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtPrice.setText(decimalFormat.format(PriceDetail) + "Đ");
        txtDespri.setText(DespriDetail);
        Picasso.with(getApplicationContext()).load(ImgDetail)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.image)
                .into(imageViewdetail);

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbardetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbardetail = findViewById(R.id.toolbarchitietsanpham);
        imageViewdetail = findViewById(R.id.imageviewchitietsanpham);
        txtName = findViewById(R.id.textviewtensanpham);
        txtPrice = findViewById(R.id.textviewgiachitietsanpham);
        txtDespri = findViewById(R.id.textviewmotachitietsanpham);
        spinner = findViewById(R.id.spiner);
        btndatmua = findViewById(R.id.buttondatmua);
    }
}