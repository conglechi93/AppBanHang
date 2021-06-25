package com.midterm.shopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.midterm.shopapp.R;
import com.midterm.shopapp.model.LoaiSP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<LoaiSP> arrayListloaisp;
    Context context;

    public LoaispAdapter(ArrayList<LoaiSP> arrayListloaisp, Context context) {
        this.arrayListloaisp = arrayListloaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListloaisp.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListloaisp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView txttenloaisp;
        ImageView imgloaisp;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if( convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.txttenloaisp = convertView.findViewById(R.id.textviewloaisp);
            viewHolder.imgloaisp = convertView.findViewById(R.id.imageviewloaisp);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        LoaiSP loaiSP = (LoaiSP) getItem(position);
        viewHolder.txttenloaisp.setText(loaiSP.getTenloaisp());
        Picasso.with(context).load(loaiSP.getHinhanhloaisp())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.image)
                .into(viewHolder.imgloaisp);
        return convertView;
    }
}
