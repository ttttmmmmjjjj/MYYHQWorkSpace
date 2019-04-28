package com.hsic.sy.supply.activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hsic.sy.supply.fragment.TruckCanselFragment;
import com.hsic.sy.supply.fragment.TruckDispatchFragment;
import com.hsic.sy.supply.fragment.TruckFnishFragment;
import com.hsic.sy.supply.fragment.TruckForcedFinishFragment;
import com.hsic.sy.supply.fragment.TruckHomeFragment;
import com.hsic.sy.supplystationmanager.R;
public class TruckManager extends FragmentActivity implements OnClickListener{
	private LinearLayout truck_tab_home;
	private LinearLayout truck_tab_dispatch;
	private LinearLayout truck_tab_cansel;
	private LinearLayout truck_tab_finish;
	private LinearLayout truck_tab_forcedfinish;

//	private ImageButton mImgtruck_tab_home;//新增车系
//	private ImageButton mImgtruck_tab_dispatch;//发车
//	private ImageButton mImgtruck_tab_cansel;//取消
//	private ImageButton mImgtruck_tab_finish;//结束
//	private ImageButton mImgtruck_tab_forcedfinish;//强制结束

	private Fragment tab_home;
	private Fragment tab_disatch;
	private Fragment tab_cansel;
	private Fragment tab_finish;
	private Fragment tab_forcedfinish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.truck_manager);
		initView();//初始化所有的view
		initEvents();
		setSelect(0);//默认显示微信聊天界面
	}
	private void initEvents() {
		truck_tab_home.setOnClickListener(this);
		truck_tab_dispatch.setOnClickListener(this);
		truck_tab_cansel.setOnClickListener(this);
		truck_tab_finish.setOnClickListener(this);
		truck_tab_forcedfinish.setOnClickListener(this);
	}
	private void initView() {
		truck_tab_home = (LinearLayout)findViewById(R.id.id_tab_home);
		truck_tab_dispatch = (LinearLayout)findViewById(R.id.id_tab_dispatch);
		truck_tab_cansel=(LinearLayout)findViewById(R.id.id_tab_cansel);
		truck_tab_finish = (LinearLayout)findViewById(R.id.id_tab_finish);
		truck_tab_forcedfinish = (LinearLayout)findViewById(R.id.id_tab_forcedfinish);

//		mImgtruck_tab_home = (ImageButton)findViewById(R.id.id_tab_home_img);
//		mImgtruck_tab_dispatch = (ImageButton)findViewById(R.id.id_tab_dispatch_img);
//		mImgtruck_tab_cansel = (ImageButton)findViewById(R.id.id_tab_cansel_img);
//		mImgtruck_tab_finish = (ImageButton)findViewById(R.id.id_tab_finish_img);
//		mImgtruck_tab_forcedfinish=(ImageButton)findViewById(R.id.id_tab_forcedfinish_img);


	}
	/*
	 * 将图片设置为亮色的；切换显示内容的fragment
	 * */
	private void setSelect(int i) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();//创建一个事务
		hideFragment(transaction);//我们先把所有的Fragment隐藏了，然后下面再开始处理具体要显示的Fragment
		switch (i) {
			case 0:
				if (tab_home == null) {
					tab_home = new TruckHomeFragment();
				/*
				 * 将Fragment添加到活动中，public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
				*containerViewId即为Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
				 * */
					transaction.add(R.id.id_content, tab_home);//将微信聊天界面的Fragment添加到Activity中
				}else {
					transaction.show(tab_home);
				}
//				mImgtruck_tab_home.setImageResource(R.drawable.car);
				break;
			case 1:
				if (tab_disatch == null) {
					tab_disatch = new TruckDispatchFragment();
					transaction.add(R.id.id_content, tab_disatch);
				}else {
					transaction.show(tab_disatch);
				}
//				mImgtruck_tab_dispatch.setImageResource(R.drawable.car);
				break;
			case 2:
				if (tab_cansel == null) {
					tab_cansel = new TruckCanselFragment();
					transaction.add(R.id.id_content, tab_cansel);
				}else {
					transaction.show(tab_cansel);
				}
//				mImgtruck_tab_cansel.setImageResource(R.drawable.car);
				break;
			case 3:
				if (tab_finish == null) {
					tab_finish = new TruckFnishFragment();
					transaction.add(R.id.id_content, tab_finish);
				}else {
					transaction.show(tab_finish);
				}
//				mImgtruck_tab_finish.setImageResource(R.drawable.car);
				break;
			case 4:
				if (tab_forcedfinish == null) {
					tab_forcedfinish = new TruckForcedFinishFragment();
					transaction.add(R.id.id_content, tab_forcedfinish);
				}else {
					transaction.show(tab_forcedfinish);
				}
//				mImgtruck_tab_forcedfinish.setImageResource(R.drawable.car);
				break;
			default:
				break;
		}
		transaction.commit();//提交事务
	}

	/*
	 * 隐藏所有的Fragment
	 * */
	private void hideFragment(FragmentTransaction transaction) {
		if (tab_home != null) {
			transaction.hide(tab_home);
		}
		if (tab_disatch != null) {
			transaction.hide(tab_disatch);
		}
		if (tab_cansel != null) {
			transaction.hide(tab_cansel);
		}
		if (tab_finish != null) {
			transaction.hide(tab_finish);
		}
		if (tab_forcedfinish != null) {
			transaction.hide(tab_forcedfinish);
		}

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		resetImg();
		switch (v.getId()) {
			case R.id.id_tab_home://当点击微信按钮时，切换图片为亮色，切换fragment为微信聊天界面
				setSelect(0);
				break;
			case R.id.id_tab_dispatch:
				setSelect(1);
				break;
			case R.id.id_tab_cansel:
				setSelect(2);
				break;
			case R.id.id_tab_finish:
				setSelect(3);
				break;
			case R.id.id_tab_forcedfinish:
				setSelect(4);
				break;
			default:
				break;
		}
	}

//	private void resetImg() {
//		mImgtruck_tab_home.setImageResource(R.drawable.car);
//		mImgtruck_tab_dispatch.setImageResource(R.drawable.car);
//		mImgtruck_tab_cansel.setImageResource(R.drawable.car);
//		mImgtruck_tab_finish.setImageResource(R.drawable.car);
//		mImgtruck_tab_forcedfinish.setImageResource(R.drawable.car);
//	}
}
