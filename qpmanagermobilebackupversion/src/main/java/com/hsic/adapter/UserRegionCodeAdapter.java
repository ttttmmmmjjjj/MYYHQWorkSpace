package com.hsic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hsic.activity.qpmanagermobilebackupversion.R;
import com.hsic.bean.UserReginCode;

import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 */

public class UserRegionCodeAdapter extends BaseAdapter {
    List<UserReginCode> UserReginCode_List;
    private Context context;
    public UserRegionCodeAdapter(Context context, List<UserReginCode> UserReginCode_List){
        this.context=context;
        this.UserReginCode_List=UserReginCode_List;
    }
    @Override
    public int getCount() {
        if(UserReginCode_List.size()>0){
            return UserReginCode_List.size();
        }
        return 0;

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = null;
        TextView txt_ID=null,txt_GoodsName=null,txt_GoodsCount=null;
        CheckBox chcb_isChecked=null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods_info_layout,
                    null);
        }else{
            convertView=view;
        }
        if(convertView!=null&&UserReginCode_List.size()>0){
            txt_ID=convertView.findViewById(R.id.txt_ID);
            txt_GoodsName=convertView.findViewById(R.id.txt_GoodsName);
            txt_GoodsCount=convertView.findViewById(R.id.txt_GoodsCount);
            chcb_isChecked=convertView.findViewById(R.id.cb_isChecked);
            chcb_isChecked.setVisibility(View.GONE);
            int id=UserReginCode_List.size()-i;
            txt_ID.setText(String.valueOf(id));
            txt_GoodsName.setText(UserReginCode_List.get(i).getUserRegionCode());
        }
        return convertView;
    }
}
