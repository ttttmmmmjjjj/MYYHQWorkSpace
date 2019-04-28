package com.hsic.sy.adapter;

import java.util.List;

import com.hsic.sy.dialoglibrary.AlertView;
import com.hsic.sy.supplystationmanager.R;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hsic.qpmanager.util.json.JSONUtils;
import com.hsic.sy.bean.HsicMessage;
import com.hsic.sy.bean.TruckInfo;
import com.hsic.sy.dialoglibrary.OnDismissListener;
import com.hsic.sy.dialoglibrary.OnItemClickListener;
import com.hsic.sy.dragdellistview.DragDelItem;
import com.hsic.sy.supply.listener.UpdateTruckNoListener;
import com.hsic.sy.supply.task.UpdateTruckNoTask;

public class DragDelListAdaper extends BaseAdapter implements UpdateTruckNoListener,OnItemClickListener, OnDismissListener {
	private List<TruckInfo> mDatas = null;
	Context context;
	String TruckType;
	String TAG;
	String CurrentTruck;
	public DragDelListAdaper(Context context, List<TruckInfo> appList, String TruckType)
	{
		this.context=context;
		mDatas=appList;
		TAG=TruckType;
		this.TruckType=setOperationType(TruckType);

	}
	@Override
	public int getCount() {
		return mDatas.size();
	}	    

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder=null;
		View menuView=null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.swipecontent, null);
			menuView = View.inflate(context,
					R.layout.swipemenu, null);
			convertView = new DragDelItem(convertView,menuView);
			holder=new ViewHolder(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}       
		holder.tv_name.setText(mDatas.get(position).getLicense());
		holder.tv_del.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {			
				//���з��������ϣ��������εȲ���
				String TruckNoID=mDatas.get(position).getTruckNoID();
				TruckInfo truckInfo = new TruckInfo();
//				Log.e("TruckID",JSONUtils.toJsonWithGson(mDatas.get(position).getTruckID()));
				truckInfo.setTruckID(mDatas.get(position).getTruckID());
				truckInfo.setTruckNoID(TruckNoID);
				CurrentTruck=TruckNoID;
				truckInfo.setState(TAG);
				HsicMessage hsicMess = new HsicMessage();
				hsicMess.setRespMsg(JSONUtils.toJsonWithGson(truckInfo));
				String responsedata = JSONUtils.toJsonWithGson(hsicMess);
				task(responsedata);

			}
		});

		return convertView;
	}
	private void task(String responsedata){
		UpdateTruckNoTask up=new UpdateTruckNoTask(context,this);
		up.execute("", responsedata);
	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
//		Log.e("notifyDataSetChanged", "===");
	}

	class ViewHolder {
		TextView tv_name;
		TextView tv_del;
		RelativeLayout relativeLayout;
		public ViewHolder(View view) {
			tv_name = (TextView) view.findViewById(R.id.tv_name);
			tv_del=(TextView)view.findViewById(R.id.tv_del);
			tv_del.setText(TruckType);
			relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_layout);
			//�ı�relativeLayout���
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			int width = wm.getDefaultDisplay().getWidth();
			relativeLayout.setMinimumWidth(width-60);
			view.setTag(this);
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	private String  setOperationType(String type){
		int TYPE=Integer.parseInt(type);
		String ret="";
		switch(TYPE){
			case 1:
				ret="发车";
				break;
			case 2:
				ret="结束";//已发车 且订单全部完成
				break;
			case 3:
				ret="强制结束";//已发车
				break;
			case 4:
				ret="作废";//未发车
				break;
		}
		return ret;
	}
	@Override
	public void UpdateTruckNoListenerEnd(HsicMessage tag) {
		// TODO Auto-generated method stub

		if(tag.getRespCode()==0){
			//更新数据
			int i=mDatas.size();
			for(int a=0;a<i;a++){
				if(CurrentTruck.equals(mDatas.get(a).getTruckNoID())){
					mDatas.remove(a);
					notifyDataSetChanged();
					break;
				}
			}
			new AlertView("提示", TruckType+"成功!", null, new String[] { "确定" },
					null, context, AlertView.Style.Alert, this).show();
		}else{
			new AlertView("提示", tag.getRespMsg(), null, new String[] { "确定" },
					null, context, AlertView.Style.Alert, this).show();
		}
	}
	@Override
	public void onDismiss(Object o) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onItemClick(Object o, int position) {
		// TODO Auto-generated method stub

	}
}


