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

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayDienthoai;

    public DienThoaiAdapter(Context context, ArrayList<Sanpham> arrayDienthoai) {
        this.context = context;
        this.arrayDienthoai = arrayDienthoai;
    }

    public int Id;
    public String Tensanpham;
    public int Giasanpham;
    public String Hinhanhsanpham;
    public String Motasanpham;
    public int Idloaisanpham;
    @Override
    public int getCount() {
        return arrayDienthoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDienthoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenDienThoai,txtGiaDienThoai,txtMoTa;
        public ImageView imageViewdienthoai;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_dienthoai,null);
            viewHolder.txtGiaDienThoai = (TextView) convertView.findViewById(R.id.textviewgiadienthoai);
            viewHolder.txtMoTa = (TextView) convertView.findViewById(R.id.textviewmotasanpham);
            viewHolder.txtTenDienThoai = (TextView) convertView.findViewById(R.id.textviewdienthoai);
            viewHolder.imageViewdienthoai = convertView.findViewById(R.id.imageviewdienthoai);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txtTenDienThoai.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,####");
        viewHolder.txtGiaDienThoai.setText("Gía sản phẩm:" + decimalFormat.format(sanpham.getGiasanpham()) + "Đ");
        viewHolder.txtMoTa.setMaxLines(2);
        viewHolder.txtMoTa.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTa.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.image)
                .error(R.drawable.no_image)
                .into(viewHolder.imageViewdienthoai);
        return convertView;
    }
}
