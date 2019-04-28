package com.hsic.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hsic.activity.qpmanagermobilebackupversion.R;
import com.hsic.bean.SaleAll;
import com.hsic.bean.SaleDetail;
import com.hsic.qpmanager.util.json.JSONUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/1/7.
 */

public class SaleListAdapter extends BaseAdapter {
    Context context;
    List<SaleAll> data;
    public SaleListAdapter(Context context, List<SaleAll> data) {
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
        View view = null;
        try{

            TextView RowID = null, UserName = null, HandPhone3 = null, TelePhone = null, Address = null, GoodsName = null,
                    userid=null,saleID=null,saleID_View=null;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.sale_list,
                        null);

            } else {
                view = convertView;
            }
            if (view != null && data.size() > 0) {
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
                saleID_View=(TextView) view.findViewById(R.id.saleID_View);
//                saleID_View.setText("");
                int i=position+1;
                RowID.setText("");
                RowID.setText(String.valueOf(i));
                UserName.setText(data.get(position).getCustomerInfo().getCustomerName());
                HandPhone3.setText(data.get(position).getSale().getTelphone());//来电电话
                TelePhone.setText(data.get(position).getCustomerInfo().getTelphone());//电话
                Address.setText(data.get(position).getSale().getSaleAddress());
                List<SaleDetail> SaleDetail=data.get(position).getSaleDetail();
//                Log.e("SaleDetail", JSONUtils.toJsonWithGson(SaleDetail));
                int size=SaleDetail.size();
                if(size>0){
                    StringBuffer goodsInfo=new StringBuffer();
                    for(int j=0;j<size;j++){
                        goodsInfo.append(SaleDetail.get(j).getQPName()+"/"+SaleDetail.get(j).getPlanSendNum()+"，");
                    }
                    String tmp=goodsInfo.toString();
                    tmp=tmp.substring(0,tmp.length()-1);
                    GoodsName.setText(tmp);//商品
                }

                userid.setText(data.get(position).getSale().getCustomerID());
                saleID.setText(data.get(position).getSale().getSaleID());
//                saleID.setVisibility(View.GONE);
//                saleID_View.setVisibility(View.GONE);//控件隐藏调

            }

        }catch(Exception ex){
            ex.toString();
            ex.printStackTrace();
        }
        return view;
    }
    @Override
    public void notifyDataSetChanged() {
        // TODO Auto-generated method stub
        super.notifyDataSetChanged();
    }
}
