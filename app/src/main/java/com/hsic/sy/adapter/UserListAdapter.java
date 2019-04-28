package com.hsic.sy.adapter;


import com.hsic.sy.bll.MyLog;
import com.hsic.sy.supplystationmanager.R;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserListAdapter extends BaseAdapter{
	Context context;
	List<Map<String, String>> data;
	public UserListAdapter(Context context,List<Map<String, String>> data) {
		this.context = context;
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
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		try{
			
			TextView RowID = null, UserName = null,  Address = null, userid=null,telePhone=null;
			if (convertView == null) {
				view = LayoutInflater.from(context).inflate(R.layout.userlist,
						null);

			} else {
				view = convertView;
			}
			if (view != null && data.size() > 0) {
				RowID=(TextView) view.findViewById(R.id.rowId);
				UserName=(TextView) view.findViewById(R.id.username);
				Address=(TextView) view.findViewById(R.id.address);
				userid=(TextView) view.findViewById(R.id.userid);
				telePhone=(TextView) view.findViewById(R.id.tel);
				int i=position+1;
				RowID.setText(String.valueOf(i));

				UserName.setText(data.get(position).get("username"));

				Address.setText(data.get(position).get("deliveraddress"));

				userid.setText(data.get(position).get("userid"));

				telePhone.setText(data.get(position).get("telephone"));
				
			}
			
		}catch(Exception ex){
			MyLog.e("getView", ex.toString());
		}
		return view;
	}

}
