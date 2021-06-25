package com.midterm.shopapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.midterm.shopapp.R;
import com.midterm.shopapp.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayLaptop;

    public LaptopAdapter(Context context, ArrayList<Sanpham> arrayLaptop) {
        this.context = context;
        this.arrayLaptop = arrayLaptop;
    }

    @Override
    public int getCount() {
        return arrayLaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayLaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenLaptop,txtGiaLaptop,txtMoTa;
        public ImageView imageViewLaptop;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_laptop,null);
            viewHolder.txtGiaLaptop = (TextView) convertView.findViewById(R.id.textviewgialaptop);
            viewHolder.txtMoTa = (TextView) convertView.findViewById(R.id.textviewmotalaptop);
            viewHolder.txtTenLaptop = (TextView) convertView.findViewById(R.id.textviewlaptop);
            viewHolder.imageViewLaptop = convertView.findViewById(R.id.imageviewlaptop);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txtTenLaptop.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,####");
        viewHolder.txtGiaLaptop.setText("Gía sản phẩm:" + decimalFormat.format(sanpham.getGiasanpham()) + "Đ");
        viewHolder.txtMoTa.setMaxLines(2);
        viewHolder.txtMoTa.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTa.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.image)
                .error(R.drawable.no_image)
                .into(viewHolder.imageViewLaptop);
        return convertView;
    }
}
