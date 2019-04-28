package com.hsic.sy.adapter;

import com.hsic.sy.bll.MyLog;
import com.hsic.sy.supplystationmanager.R;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QueryFullBottleAdapter extends BaseAdapter{
	Context context;
	List<String> data;
	public QueryFullBottleAdapter(Context context,List<String> data) {
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
			
			TextView RowID = null, FullBottele = null;
			if (convertView == null) {
				view = LayoutInflater.from(context).inflate(R.layout.fullbottle_item,
						null);

			} else {
				view = convertView;
			}
			if (view != null && data.size() > 0) {
				RowID=(TextView)view.findViewById(R.id.id);
				FullBottele=(TextView)view.findViewById(R.id.fullBottle);
				int i=data.size()-position;
				RowID.setText(String.valueOf(i));
				FullBottele.setText(data.get(position));
			}
			
		}catch(Exception ex){
			MyLog.e("getView", ex.toString());
		}
		return view;
	}
}
