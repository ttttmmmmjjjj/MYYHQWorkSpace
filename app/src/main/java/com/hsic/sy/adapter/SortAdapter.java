package com.hsic.sy.adapter;

import com.hsic.sy.bean.SortModel;
import com.hsic.sy.supplystationmanager.R;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SortAdapter extends BaseAdapter implements SectionIndexer{
	private List<SortModel> list = null;  
    private Context mContext;  
      
    public SortAdapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;  
        this.list = list;  
    }  
      
    /** 
     * ��ListView���ݷ����仯ʱ,���ô˷���������ListView 
     * @param list 
     */  
    public void updateListView(List<SortModel> list){  
        this.list = list;  
        notifyDataSetChanged();  
    }  
  
    public int getCount() {  
        return this.list.size();  
    }  
  
    public Object getItem(int position) {  
        return list.get(position);  
    }  
  
    public long getItemId(int position) {  
        return position;  
    }  
  
    public View getView(final int position, View view, ViewGroup arg2) {  
        ViewHolder viewHolder = null;  
        final SortModel mContent = list.get(position);  
        if (view == null) {  
            viewHolder = new ViewHolder();  
            view = LayoutInflater.from(mContext).inflate(R.layout.item, null);  
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);  
//            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);            
            view.setTag(viewHolder);  
        } else {  
            viewHolder = (ViewHolder) view.getTag();  
        }  
          
        //����position��ȡ���������ĸ��char asciiֵ  
//        int section = getSectionForPosition(position);  
//          
//        //�����ǰλ�õ��ڸ÷�������ĸ��Char��λ�� ������Ϊ�ǵ�һ�γ���  
//        if(position == getPositionForSection(section)){  
//            viewHolder.tvLetter.setVisibility(View.VISIBLE);  
//            viewHolder.tvLetter.setText(mContent.getSortLetters());  
//        }else{  
//            viewHolder.tvLetter.setVisibility(View.GONE);  
//        }  
      
        viewHolder.tvTitle.setText(this.list.get(position).getName());  
    
        return view;  
  
    }  
      
  
  
    final static class ViewHolder {  
        TextView tvLetter;  
        TextView tvTitle;  
 
    }  
  
  
    /** 
     * ����ListView�ĵ�ǰλ�û�ȡ���������ĸ��char asciiֵ 
     */  
    public int getSectionForPosition(int position) {  
        return list.get(position).getSortLetters().charAt(0);  
    }  
  
    /** 
     * ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ�� 
     */  
    public int getPositionForSection(int section) {  
        for (int i = 0; i < getCount(); i++) {  
            String sortStr = list.get(i).getSortLetters();  
            char firstChar = sortStr.toUpperCase().charAt(0);  
            if (firstChar == section) {  
                return i;  
            }  
        }  
          
        return -1;  
    }  
      
  
    @Override  
    public Object[] getSections() {  
        return null;  
    }  
}