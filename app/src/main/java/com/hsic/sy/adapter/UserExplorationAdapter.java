package com.hsic.sy.adapter;

import com.hsic.sy.bean.UserExploration;
import com.hsic.sy.supplystationmanager.R;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserExplorationAdapter extends BaseAdapter{
	private Context mContext;
	private List<UserExploration> data=null;
	public UserExplorationAdapter(Context context,List<UserExploration> list){
		this.mContext=context;
		this.data=list;
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
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if (data != null) {
			return data.get(arg0);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (data != null && data.size() > 0) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_user_exploration, null);
			// <!-- 用户编号,姓名,备注,地址,用户类型 -->
			TextView rowId= (TextView) convertView.findViewById(R.id.rowid);
			TextView userId= (TextView) convertView.findViewById(R.id.userId);
			TextView username= (TextView) convertView.findViewById(R.id.username);
			TextView address= (TextView) convertView.findViewById(R.id.address);
			TextView remark= (TextView) convertView.findViewById(R.id.remark);

			int i=data.size();
			int ic=i-position;
			String customType;
			if(data.get(position).getCustom_type().equals("CT03")||data.get(position).getCustom_type().equals("CT04")){
				customType="[商业用户]";
			}else{
				customType="[居民用户]";
			}
			rowId.setText(String.valueOf(i-position));
			userId.setVisibility(View.VISIBLE);
			userId.setText(data.get(position).getUserid()+customType);
			username.setText("用户名:"+data.get(position).getUsername());
			address.setText("地址:"+data.get(position).getDeliveraddress());
			String Remark=data.get(position).getRemark()!=null?data.get(position).getRemark():"";
			remark.setText("备注:"+Remark);
			return convertView;
		}else{
			return null;
		}

	}

}
