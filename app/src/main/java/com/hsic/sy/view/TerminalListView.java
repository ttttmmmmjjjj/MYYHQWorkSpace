package com.hsic.sy.view;
import com.hsic.sy.supplystationmanager.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

public class TerminalListView extends ListView{
	private static final String TAG = "CanselListView";

	private LayoutInflater mInflater = null;

	/**
	 * �û���������С����
	 */
	private int touchSlop;
	/**
	 * �Ƿ���Ӧ����
	 */
	private boolean isSliding;
	/**
	 * ��ָ����ʱ��x����
	 */
	private int xDown;
	/**
	 * ��ָ����ʱ��y����
	 */
	private int yDown;
	/**
	 * ��ָ�ƶ�ʱ��x����
	 */
	private int xMove;
	/**
	 * ��ָ�ƶ�ʱ��y����
	 */
	private int yMove;

	/**
	 * ��ǰ��ָ������View
	 */
	private View mCurrentView;
	/**
	 * ��ǩ��ָ������λ��
	 */
	private int mCurrentViewPos;

	/**
	 * Ϊɾ����ť�ṩһ���ص��ӿ�
	 */
	private DelButtonClickListener mDelListener = null;
	private CancelButtonClickListener mCancelListener = null;

	private PopupWindow mPopupWindow = null;
	private Button mDelBtn = null, mCancelBtn = null;

	private int mPopupWindowWidth, mPopupWindowHeight;

	public TerminalListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(context);
		// �û���ָ�ƶ�����С���룬�����ж��Ƿ���Ӧ�����ƶ��¼�
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		View view = mInflater.inflate(R.layout.layout_terminal_btn, null);
		// ɾ����ť
		mDelBtn = (Button) view.findViewById(R.id.id_item_btn);
		// ȡ����ť
//		mCancelBtn = (Button) view.findViewById(R.id.id_item_cancel_btn);
		mPopupWindow = new PopupWindow(view,
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		/**
		 * �ȵ�����measure,�����ò�����͸�
		 */
		mPopupWindow.getContentView().measure(0, 0);
		mPopupWindowHeight = mPopupWindow.getContentView().getMeasuredHeight();
		mPopupWindowWidth = mPopupWindow.getContentView().getMeasuredWidth();
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		int action = ev.getAction();
		int x = (int) ev.getX();
		int y = (int) ev.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// ��ָ����ʱˮƽ����x��λ��
			xDown = x;
			// ��ָ����ʱ��ֱ����y��λ��
			yDown = y;
			/*
			 * �����ǰpopupWindow��ʾ����ֱ�����أ�Ȼ������ListView��Touch�¼����´�
			 */
			if (mPopupWindow.isShowing()) {
				dismissPopWindow();
				return false;
			}
			// ��õ�ǰ��ָ����ʱ��item��λ��
			mCurrentViewPos = pointToPosition(xDown, yDown);
			// ��õ�ǰ��ָ����ʱ��ListView��item��
			mCurrentView = getChildAt(mCurrentViewPos
					- getFirstVisiblePosition());
			break;
		case MotionEvent.ACTION_MOVE:
			// ��ָ�ƶ�ʱx��λ��
			xMove = x;
			// ��ָһ��ʱy��λ��
			yMove = y;
			// ˮƽ�����ľ��루����Ϊ��ֵ��
			int dx = xMove - xDown;
			// ��ֱ�����ľ��루����Ϊ��ֵ��
			int dy = yMove - yDown;
			/*
			 * �ж��Ƿ��Ǵ��ҵ���Ļ���
			 */
			if (xMove < xDown && Math.abs(dx) > touchSlop
					&& Math.abs(dy) < touchSlop) {
				Log.e(TAG, "touchslop = " + touchSlop + " , dx = " + dx
						+ " , dy = " + dy);
				isSliding = true;
			}
			break;
		}

		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		/*
		 * ����Ǵ��ҵ���Ļ�������Ӧ��֮ǰ���� dispatchTouchEvent �л�����Ƿ��Ǵ������󻬶�
		 */
		if (isSliding) {
			switch (action) {
			case MotionEvent.ACTION_MOVE:
				int[] location = new int[2];
				// ��õ�ǰitem��λ��x��y
				mCurrentView.getLocationOnScreen(location);
				// ����PopupWindow�Ķ���
				mPopupWindow
				.setAnimationStyle(R.style.popwindow_delete_btn_anim_style);
				mPopupWindow.update();
				Log.e(TAG, "width location[0]: " + location[0]);
				Log.e(TAG, "height location[1]: " + location[1]);
				Log.e(TAG,
						"mCurrentView.getWidth(): " + mCurrentView.getWidth());
				Log.e(TAG,
						"mCurrentView.getHeight(): " + mCurrentView.getHeight());
				Log.e(TAG, "mPopupWindowHeight: " + mPopupWindowHeight);
				// ���á�ȡ����ע������ɾ������ťPopWindow����ʾλ��
				// ����ڸ��ؼ���λ�ã�����������Gravity.CENTER���·�Gravity.BOTTOM�ȣ�����������ƫ�ƻ���ƫ��
				// ���ĳ���ؼ���λ�ã���ƫ��;xoff��ʾx���ƫ�ƣ���ֵ��ʾ���󣬸�ֵ��ʾ���ң�yoff��ʾ���y���ƫ�ƣ���ֵ�����£���ֵ�����ϣ�
				mPopupWindow.showAtLocation(mCurrentView, Gravity.LEFT
						| Gravity.TOP, location[0] + mCurrentView.getWidth(),
						location[1] + mCurrentView.getHeight() / 2
						- mPopupWindowHeight / 2);
				// ����ɾ����ť�Ļص�
				mDelBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// ͨ���ӿڶ�����õ�ǰItem��ɾ����ť�ĵ������
						mDelListener.onDelClick(mCurrentViewPos);
						mPopupWindow.dismiss();
					}
				});
//				// ����ȡ����ť�Ļص�
//				mCancelBtn.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						// ͨ���ӿڶ�����õ�ǰItem��ȡ����ť�ĵ������
//						mCancelListener.onCancelClick(mCurrentViewPos);
//						mPopupWindow.dismiss();
//					}
//				});
				Log.e(TAG, "mPopupWindow.getHeight()=" + mPopupWindowHeight);
				break;
			case MotionEvent.ACTION_UP:
				// ���ò໬�ر�
				isSliding = false;
				break;
			}
			// ��Ӧ�����ڼ���ĻitemClick�¼������ⷢ����ͻ
			return true;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * ����popupWindow
	 */
	private void dismissPopWindow() {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
	}

	/**
	 * ����ɾ����ť����¼�����
	 * 
	 * @param listener
	 *            DelButtonClickListener ɾ����ť�����ӿڶ���
	 */
	public void setDelButtonClickListener(DelButtonClickListener listener) {
		mDelListener = listener;
	}

	/**
	 * ����ȡ����ť����¼�����
	 * 
	 * @param listener
	 *            CancelButtonClickListener ��ť����¼������ӿڶ���
	 */
	public void setCancelButtonClickListener(CancelButtonClickListener listener) {
		mCancelListener = listener;
	}

	/**
	 * ɾ����ť�����ӿ�
	 */
	public interface DelButtonClickListener {
		void onDelClick(int position);
	}

	/**
	 * ȡ����ť�����ӿ�
	 */
	public interface CancelButtonClickListener {
		void onCancelClick(int position);
	}

}
