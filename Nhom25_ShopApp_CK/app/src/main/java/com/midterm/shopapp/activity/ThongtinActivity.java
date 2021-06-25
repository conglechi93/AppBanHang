package com.midterm.shopapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.midterm.shopapp.Fragments.MyMapFragment;
import com.midterm.shopapp.R;

public class ThongtinActivity extends AppCompatActivity {
    private MyMapFragment myMapFragment;
    private Toolbar toolbar;
    ViewFlipper viewFlipper;
    int[] arrayHinh={R.drawable.cnbh,R.drawable.bachkhoashop,R.drawable.bkcpt,R.drawable.bkcpt1,R.drawable.nv1,R.drawable.nv2,R.drawable.nv3,R.drawable.nv4,R.drawable.nv6,R.drawable.bkcare,R.drawable.nv5,R.drawable.bkcare2,R.drawable.pt,R.drawable.pt2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.myMapFragment = (MyMapFragment) fragmentManager.findFragmentById(R.id.fragment_map);

        viewFlipper= (ViewFlipper) findViewById(R.id.viewFlipper);
        for(int i=0;i<arrayHinh.length;i++){
            ImageView imageView=new ImageView(this);

            imageView.setImageResource(arrayHinh[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(1000);
        viewFlipper.setAutoStart(true);

    }
}