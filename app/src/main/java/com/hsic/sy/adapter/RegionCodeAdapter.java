package com.hsic.sy.adapter;


import java.util.List;
import java.util.Map;
import com.hsic.sy.supplystationmanager.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hsic.sy.bean.RegionCodeBean;

public class RegionCodeAdapter extends BaseAdapter{
	Context context;
	List<RegionCodeBean> data;
	public RegionCodeAdapter(Context context,List<RegionCodeBean> data){
		this.context=context;
		this.data=data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(data.size()>0){
			return data.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	final static class ViewHolder {  
        TextView tvLetter;  
        CheckBox  isChecked;  
 
    }


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;  

        if (data != null && data.size() > 0) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_userregioncode, null);
            TextView size= (TextView) convertView.findViewById(R.id.item_size);  
            TextView tvLetter= (TextView) convertView.findViewById(R.id.item_userregioncode);           
            CheckBox isChecked= (CheckBox) convertView.findViewById(R.id.item_userregioncode_checkBox);
            int id=data.size()-position;
            size.setText(""+id);
            tvLetter.setText(data.get(position).getMsg());
            isChecked.setChecked(data.get(position).isChecked());      
            return convertView;
        }else{
        	return null;
        }
            
	}
}
