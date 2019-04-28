package com.hsic.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hsic.bean.SaleAll;
import com.hsic.bean.SaleDetail;
import com.hsic.fxqpmanager.R;
import com.hsic.qpmanager.util.json.JSONUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/27.
 */

public class SaleListAdapter extends BaseAdapter {
    Context context;
    List<Map<String, String>> data ;
    public SaleListAdapter(Context context,List<Map<String, String>> data) {
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
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        long start=System.currentTimeMillis();
        View view = null;
        try{
            TextView RowID = null, UserName = null, HandPhone3 = null, TelePhone = null, Address = null, GoodsName = null,
                    userid=null,saleID=null,saleID_View=null,userCardID_View=null,userCardID=null,CallingTelephone=null;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.sale_list,
                        null);

            } else {
                view = convertView;
            }
            if (view != null && data.size() > 0) {
                userCardID_View=(TextView) view.findViewById(R.id.userCardID_View);
                userCardID_View.setVisibility(View.VISIBLE);
                userCardID=(TextView) view.findViewById(R.id.userCardID);
                userCardID.setVisibility(View.VISIBLE);
                RowID = (TextView) view.findViewById(R.id.xuhaotask);//任务序号
                UserName = (TextView) view.findViewById(R.id.username);//用户姓名
                UserName.setText("");
                HandPhone3 = (TextView) view.findViewById(R.id.handPhone3);
                HandPhone3.setText("");
                TelePhone = (TextView) view.findViewById(R.id.telephone);//电话
                TelePhone.setText("");
                Address = (TextView) view.findViewById(R.id.address);
                Address.setText("");
                GoodsName = (TextView) view.findViewById(R.id.goodsname);
                GoodsName.setText("");
                userid=(TextView) view.findViewById(R.id.userid);
                userid.setText("");
                saleID=(TextView) view.findViewById(R.id.saleID);
                saleID.setText("");
                CallingTelephone=(TextView) view.findViewById(R.id.CallingTelephone);
                CallingTelephone.setText("");
                saleID_View=(TextView) view.findViewById(R.id.saleID_View);
//                saleID_View.setText("");
                int i=position+1;
                RowID.setText("");
                RowID.setText(String.valueOf(i));
                UserName.setText(data.get(position).get("CustomerName"));
                HandPhone3.setText(data.get(position).get("Telephone"));//联系电话
                CallingTelephone.setText(data.get(position).get("CallingTelephone"));;////来电电话
//                TelePhone.setText(data.get(position).getCustomerInfo().getTelphone());//电话
                Address.setText(data.get(position).get("Address"));
                GoodsName.setText(data.get(position).get("goodsInfo"));//商品
                userid.setText(data.get(position).get("UserID"));
                saleID.setText(data.get(position).get("SaleID"));
//                saleID.setVisibility(View.GONE);
//                saleID_View.setVisibility(View.GONE);//控件隐藏调
                userCardID.setText(data.get(position).get("CustomerCardID"));
            }

        }catch(Exception ex){
            ex.toString();
            ex.printStackTrace();
//            Log.e("设置适配器花费时间yx","="+ ex.toString());
        }
        long end = System.currentTimeMillis();
//        Log.e("设置适配器花费时间","="+(end -start));
        return view;
    }
    @Override
    public void notifyDataSetChanged() {
        // TODO Auto-generated method stub
        super.notifyDataSetChanged();
    }
}
