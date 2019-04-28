package com.hsic.sy.view;

import com.hsic.sy.supplystationmanager.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

public class DropEditText extends EditText implements AdapterView.OnItemClickListener, PopupWindow.OnDismissListener {

    private Drawable mDrawable; // ��ʾ��ͼ
    private PopupWindow mPopupWindow; // ���ͼƬ������popWindow����
    private ListView mPopListView; // popWindow�Ĳ���
    private int mDropDrawableResId; // ����ͼ��
    private int mRiseDrawableResID; // ����ͼ��

    public DropEditText(Context context) {
        this(context, null);
    }

    public DropEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DropEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mPopListView = new ListView(context);
        mDropDrawableResId = R.drawable.drop; // ��������ͼ��
        mRiseDrawableResID = R.drawable.rise; // ��������ͼ��
        showDropDrawable(); // Ĭ����ʾ����ͼ��
        mPopListView.setOnItemClickListener(this);
    }

    /**
     * �����޷�ֱ�Ӹ�EditText���õ���¼���ֻ��ͨ�����µ�λ����ģ�����¼�
     * �����ǰ��µ�λ����ͼ�����ͼ�굽�ؼ��ұߵļ�෶Χ�ھ�����Ч
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                int start = getWidth() - getTotalPaddingRight() + getPaddingRight(); // ��ʼλ��
                int end = getWidth(); // ����λ��
                boolean available = (event.getX() > start) && (event.getX() < end);
                if (available) {
                    closeSoftInput();
                    showPopWindow();
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mPopupWindow = new PopupWindow(mPopListView, getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE)); // ����popWindow������ɫ
            mPopupWindow.setFocusable(true); // ��popWindow��ȡ����
            mPopupWindow.setOnDismissListener(this);
        }
    }

    private void showPopWindow() {
        mPopupWindow.showAsDropDown(this, 0, 5);
        showRiseDrawable();
    }

    private void showDropDrawable() {
        mDrawable = getResources().getDrawable(mDropDrawableResId);
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mDrawable, getCompoundDrawables()[3]);
    }

    private void showRiseDrawable() {
        mDrawable = getResources().getDrawable(mRiseDrawableResID);
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mDrawable, getCompoundDrawables()[3]);
    }

    public void setAdapter(BaseAdapter adapter) {
        mPopListView.setAdapter(adapter);
    }

    private void closeSoftInput() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.setText(mPopListView.getAdapter().getItem(position).toString()); // ������Ҫ����дitem��toString����
        mPopupWindow.dismiss();
    }

    @Override
    public void onDismiss() {
        showDropDrawable(); // ��popWindow��ʧʱ��ʾ����ͼ��
    }
}
