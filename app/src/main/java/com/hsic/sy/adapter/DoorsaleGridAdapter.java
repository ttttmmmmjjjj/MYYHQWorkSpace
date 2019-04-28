package com.hsic.sy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsic.sy.supplystationmanager.R;

/**
 * Created by Administrator on 2018/11/22.
 */

public class DoorsaleGridAdapter extends BaseAdapter {
    private Context mContext;

    public String[] img_text = { "下单", "提货", "作废", "统计", "补交打印","换货",};
    public int[] imgs = { R.drawable.a, R.drawable.iocn_dollar,
            R.drawable.xd, R.drawable.count,
            R.drawable.b, R.drawable.g};
    public DoorsaleGridAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_item, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);

        tv.setText(img_text[position]);
        return convertView;
    }
}
