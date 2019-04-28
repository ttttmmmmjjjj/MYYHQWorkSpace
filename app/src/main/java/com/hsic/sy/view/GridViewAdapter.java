package com.hsic.sy.view;

import com.hsic.sy.supplystationmanager.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
    private List<Model> mDatas;
    private LayoutInflater inflater;
    /**
     * ҳ���±�,��0��ʼ(��ǰ�ǵڼ�ҳ)
     */
    private int curIndex;
    /**
     * ÿһҳ��ʾ�ĸ���
     */
    private int pageSize;

    public GridViewAdapter(Context context, List<Model> mDatas, int curIndex, int pageSize) {
        inflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.curIndex = curIndex;
        this.pageSize = pageSize;
    }

    /**
     * ���ж����ݼ��Ĵ�С�Ƿ��㹻��ʾ����ҳ��mDatas.size() > (curIndex+1)*pageSize,
     * ���������ֱ�ӷ���ÿһҳ��ʾ�������Ŀ����pageSize,
     * ������������м���ؼ�,(mDatas.size() - curIndex * pageSize);(Ҳ�������һҳ��ʱ�����ʾʣ��item)
     */
    @Override
    public int getCount() {
        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);

    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview_copy_mt, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * �ڸ�View����ʾ������ʱ��������ȷ��position = position + curIndex * pageSize��
         */
        int pos = position + curIndex * pageSize;
        viewHolder.tv.setText(mDatas.get(pos).name);
//        viewHolder.iv.setImageResource(mDatas.get(pos).iconRes);
        return convertView;
    }


    class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }
}