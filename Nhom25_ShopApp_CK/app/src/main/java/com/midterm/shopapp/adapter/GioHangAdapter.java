package com.midterm.shopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.midterm.shopapp.R;
import com.midterm.shopapp.activity.MainActivity;
import com.midterm.shopapp.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arraygiohang;

    public GioHangAdapter(Context context, ArrayList<GioHang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenGioHang,txtGiaGioHang,txtValues;
        public ImageView imgGioHang;
        public Button btnminus,btnplus;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_gio_hang,null);
            viewHolder.txtTenGioHang = (TextView) convertView.findViewById(R.id.textviewtengiohang);
            viewHolder.txtGiaGioHang = (TextView) convertView.findViewById(R.id.textviewgiagiohang);
            viewHolder.txtValues = (TextView) convertView.findViewById(R.id.textviewvalues);
            viewHolder.btnminus = (Button) convertView.findViewById(R.id.buttonminus);
            viewHolder.btnplus = (Button) convertView.findViewById(R.id.buttonplus);
            viewHolder.imgGioHang = (ImageView) convertView.findViewById(R.id.imageviewgiohang);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHang gioHang = (GioHang) getItem(position);
        viewHolder.txtTenGioHang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGioHang.setText(decimalFormat.format(gioHang.getGiasp()) + "Đ");
        Picasso.with(context).load(gioHang.getHinhsp())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.image)
                .into(viewHolder.imgGioHang);
        viewHolder.txtValues.setText(gioHang.getSoluong() + "");
        int sl = Integer.parseInt(viewHolder.txtValues.getText().toString());
        if(sl >=10) {
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }
        else if (sl <= 1) {
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }
        else if(sl >=1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }
        ViewHolder finalViewHolder = viewHolder;
        finalViewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.txtValues.getText().toString()) + 1;
                int slht = MainActivity.manggiohang.get(position).getSoluong();
                long giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluong(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giamoinhat) + "Đ");
                com.midterm.shopapp.activity.GioHang.EvenUltil();
                if(slmoinhat > 9){
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.txtValues.setText(String.valueOf(slmoinhat));
                }
                else {
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.txtValues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        finalViewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.txtValues.getText().toString()) - 1;
                int slht = MainActivity.manggiohang.get(position).getSoluong();
                long giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluong(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giamoinhat) + "Đ");
                com.midterm.shopapp.activity.GioHang.EvenUltil();
                if(slmoinhat < 2){
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.txtValues.setText(String.valueOf(slmoinhat));
                }
                else {
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.txtValues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return convertView;
    }
}
