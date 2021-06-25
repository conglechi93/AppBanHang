package com.midterm.shopapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.midterm.shopapp.R;
import com.midterm.shopapp.adapter.LoaispAdapter;
import com.midterm.shopapp.adapter.SanphamAdapter;
import com.midterm.shopapp.model.GioHang;
import com.midterm.shopapp.model.LoaiSP;
import com.midterm.shopapp.model.Sanpham;
import com.midterm.shopapp.ulti.CheckConnection;
import com.midterm.shopapp.ulti.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textViewT;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<LoaiSP> mangloaisp;
    ArrayList<Sanpham> mangsanpham;
    LoaispAdapter loaispAdapter;
    SanphamAdapter sanphamAdapter;
    int ID = 0;
    String tenloaiSP = "";
    String hinhanhloaiSP = "";
    //recycle
    int IDsp = 0 ;
    String Tensanpham = "";
    Integer Giasanpham = 0;
    String Hinhanhsanpham = "";
    String Motasanpham = "";
    int IDsanpham = 0;
    public static ArrayList<GioHang> manggiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        if(CheckConnection.haveNetWorkConnecttion(getApplicationContext())){
            ActionBar();
            ActionViewFilper();
            GetDuLieuLoaiSP();
            GetDuLieuSPMoiNhat();
            CatchOnItemListView();
        }
        else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn  hãy kiểm tra kết nối");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), com.midterm.shopapp.activity.GioHang.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void CatchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0:
                            if(CheckConnection.haveNetWorkConnecttion(getApplicationContext())){
                                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                startActivity(intent);
                                drawerLayout.closeDrawer(GravityCompat.START);

                            }
                            else CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra kết nối");
                            break;
                        case 1:
                            if(CheckConnection.haveNetWorkConnecttion(getApplicationContext())){
                                Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                                intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                                startActivity(intent);
                                drawerLayout.closeDrawer(GravityCompat.START);

                            }
                            else CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra kết nối");
                            break;
                        case 2:
                            if(CheckConnection.haveNetWorkConnecttion(getApplicationContext())){
                                Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                                intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                                startActivity(intent);
                                drawerLayout.closeDrawer(GravityCompat.START);

                            }
                            else CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra kết nối");
                            break;
                        case 3:
                            if(CheckConnection.haveNetWorkConnecttion(getApplicationContext())){
                                Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                                startActivity(intent);
                                drawerLayout.closeDrawer(GravityCompat.START);

                            }
                            else CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra kết nối");
                            break;
                        case 4:
                            if(CheckConnection.haveNetWorkConnecttion(getApplicationContext())){
                                Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
                                startActivity(intent);
                                drawerLayout.closeDrawer(GravityCompat.START);

                            }
                            else CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra kết nối");
                            break;
                    }
            }
        });
    }

    private void GetDuLieuSPMoiNhat() {
        Log.d("GetDL","OK");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdansanphammoinhat, response -> {
            if(response != null){
                for(int i=0; i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        IDsp = jsonObject.getInt("id");
                        Tensanpham = jsonObject.getString("tensp");
                        Giasanpham = jsonObject.getInt("giasp");
                        Hinhanhsanpham = jsonObject.getString("hinhanhsp");
                        Motasanpham = jsonObject.getString("motasp");
                        IDsanpham = jsonObject.getInt("idsanpham");
                        mangsanpham.add(new Sanpham(IDsp,Tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                        sanphamAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuLoaiSP() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanloaiSP, response -> {
            if(response != null){
                // đây là dùng json
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        ID = jsonObject.getInt("id");
                        tenloaiSP = jsonObject.getString("tenloaisp");
                        hinhanhloaiSP = jsonObject.getString("hinhanhloaisp");
                        mangloaisp.add(new LoaiSP(ID,tenloaiSP,hinhanhloaiSP));
                        loaispAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                CheckConnection.ShowToast_Short(getApplicationContext(),tenloaiSP);
                mangloaisp.add(new LoaiSP(0,"Liên hệ","https://i.pinimg.com/originals/88/7a/f8/887af80b0de39706fbcafc07b91869c9.png"));
                mangloaisp.add(new LoaiSP(0,"Thông tin","https://www.digopaul.com/wp-content/uploads/related_images/2015/09/09/information_2.jpg"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFilper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://photo-cms-viettimes.zadn.vn/w666/Uploaded/2021/aohuooh/2018_06_28/1_ivdd.jpg");
        mangquangcao.add("https://static.chotot.com/storage/chotot-kinhnghiem/c2c/2015/05/5-tieu-chi-quan-trong-de-chon-dien-thoai-chup-anh-dep-3.jpg");
        mangquangcao.add("https://tinhte.cdnforo.com/store/2014/08/2572609_Hinh_2.jpg");
        mangquangcao.add("http://i.imgur.com/DvpvklR.png");
        for(int i = 0; i <mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in  = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out  = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {
        textViewT = (TextView) findViewById(R.id.tv_SPP);

        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listViewmanhinhchinh = (ListView) findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(new LoaiSP(0,"Trang chính","https://smartapp.edu.vn/wp-content/uploads/2018/07/Small-icon.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaispAdapter);


        mangsanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),mangsanpham);

        recyclerViewmanhinhchinh = (RecyclerView) findViewById(R.id.recycleview);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);

        if(manggiohang != null) {

        }
        else {
            manggiohang = new ArrayList<>();
        }
    }
}