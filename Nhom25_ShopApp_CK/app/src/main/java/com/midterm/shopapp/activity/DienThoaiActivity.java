package com.midterm.shopapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.midterm.shopapp.R;
import com.midterm.shopapp.adapter.DienThoaiAdapter;
import com.midterm.shopapp.model.Sanpham;
import com.midterm.shopapp.ulti.CheckConnection;
import com.midterm.shopapp.ulti.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {

    Toolbar toolbardt;
    ListView lvdt;
    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<Sanpham> mangdt;
    View footerview;
    int iddt = 0;
    int page = 1;
    boolean isLoading = false;
    mHandler mHandler;
    boolean limitdata = false;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        AnhXa();
        if(CheckConnection.haveNetWorkConnecttion(getApplicationContext()))
        {
            GetIDloaisp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }
        else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra kết nối");
            finish();
        }


    }

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

    private void LoadMoreData() {
        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangdt.get(position));
                startActivity(intent);

            }
        });
        lvdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount!=0 && isLoading == false && limitdata == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();


                }
            }
        });
    }

    private void GetData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.dienthoai + String.valueOf(page);
        Log.d("a",duongdan);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tendt = "";
                int Giadt = 0;
                String Hinhanhdt = "";
                String Mota = "";
                int Idspdt = 0;
                if(response != null && response.length() != 2){
                    lvdt.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0 ; i< jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Log.d("id11", String.valueOf(id));
                            Tendt = jsonObject.getString("tensp");
                            Log.d("id12", String.valueOf(Tendt));
                            Giadt = jsonObject.getInt("giasp");
                            Hinhanhdt = jsonObject.getString("hinhanhsp");
                            Mota = jsonObject.getString("motasp");
                            Idspdt = jsonObject.getInt("idsanpham");
                            Log.d("test", Giadt+".....................");
                            mangdt.add(new Sanpham(id,Tendt,Giadt,Hinhanhdt,Mota,Idspdt));
                            dienThoaiAdapter.notifyDataSetChanged();
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                else {
                    limitdata = true;
                    lvdt.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(iddt));
                Log.d("id",String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        toolbardt = (Toolbar) findViewById(R.id.toolbardienthoai);
        lvdt = findViewById(R.id.listviewdienthoai);
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIDloaisp() {
        iddt = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham", iddt + "");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void AnhXa() {
        lvdt = (ListView) findViewById(R.id.listviewdienthoai);
        mangdt = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(), mangdt);
        lvdt.setAdapter(dienThoaiAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvdt.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

}