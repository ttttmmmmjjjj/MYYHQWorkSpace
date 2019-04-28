package com.hsic.sy.adapter;

import com.hsic.sy.bean.QPInfo;
import com.hsic.sy.supplystationmanager.R;

import java.util.List;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TagAdapter extends BaseAdapter{
	private Context mContext;
	private List<QPInfo> data=null;
	public TagAdapter(Context context,List<QPInfo> list){
		this.mContext = context;
		this.data = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (data != null) {
			return data.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (data != null) {
			return data.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if (data != null) {
			return data.size();
		} else {
			return 0;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 // TODO Auto-generated method stub
        if (data != null && data.size() > 0) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tagitem, null);         
            TextView id= (TextView) convertView.findViewById(R.id.tv_id);           
            TextView yfcode= (TextView) convertView.findViewById(R.id.tv_yfcode);
            int i=data.size(); 
            int ic=i-position;
            id.setText(String.valueOf(ic));
            yfcode.setText(data.get(position).getQPNumber());    

            if(data.get(position).getColorTag().equals("1")){
            	yfcode.setTextColor(Color.GREEN);
            	id.setTextColor(Color.GREEN);
            }else{
            	yfcode.setTextColor(Color.RED);
            	id.setTextColor(Color.RED);
            }
            return convertView;
        }else
            return null;
    
	}


}
