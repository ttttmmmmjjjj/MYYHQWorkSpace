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

public class QueryGridAdapter extends BaseAdapter {
    private Context mContext;

    public String[] img_text = { "车次查询", "扫描统计", "满瓶查询", "钢瓶充装统计", "钢瓶配送统计","站点库存统计"};
    public int[] imgs = { R.drawable.truck, R.drawable.order,
            R.drawable.gas, R.drawable.count,
            R.drawable.i, R.drawable.j};

    public QueryGridAdapter(Context mContext) {
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
