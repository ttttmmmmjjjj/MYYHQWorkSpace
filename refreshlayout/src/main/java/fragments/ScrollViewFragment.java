package fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hsic.sy.refreshlayout.R;
import com.ypx.refreshlayout.simple.qq.YPXQQRefreshView;

/**
 * ScrollView
 * Created by yangpeixing on 17/1/17.
 */
public class ScrollViewFragment extends Fragment{
    YPXQQRefreshView refreshableView;
    LinearLayout layout;
    final int SUCCESS = 1;
    final int FAILED = 0;
    View view;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    refreshableView.finishRefresh(true);
                    TextView textView = new TextView(getActivity());
                    textView.setTextColor(Color.parseColor("#666666"));
                    textView.setTextSize(18);
                    textView.setText("这是刷新的文本");
                    textView.setPadding(dp(15),dp(10),dp(15),dp(10));
                    layout.addView(textView,0);
                    break;
                case FAILED:
                    refreshableView.finishRefresh(false);
                    break;
                default:
                    break;
            }
        };
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_scrollview,null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        refreshableView = (YPXQQRefreshView) view.findViewById(R.id.refreshableView1);
        layout = (LinearLayout) view.findViewById(R.id.ll_layout);
        //设置是否可以刷新,默认可以刷新
        refreshableView.setRefreshEnabled(true);
        //设置刷新头的高度,此高度会决定小球的默认半径和坐标
    /*   refreshableView.setRefreshViewHeight(refreshableView.dp(120));
        //设置刷新颜色,默认颜色值#999999
		refreshableView.setRefreshColor(Color.parseColor("#26B8F2"));
		//设置刷新图标,默认刷新图标
		refreshableView.setRefreshIcon(R.mipmap.ic_launcher);
		//设置刷新球最大拉伸距离,默认为刷新头部高度
		refreshableView.setRefreshMaxHeight(refreshableView.dp(150));
		//设置刷新球半径,默认15dp
		refreshableView.setTopCircleRadius(refreshableView.dp(30));
		//设置刷新球圆心X值,默认屏宽一半
		refreshableView.setTopCircleX(refreshableView.dp(50));
		//设置刷新球圆心Y值,默认30dp
		refreshableView.setTopCircleY(refreshableView.dp(30));  */
    }

    private void initData() {
        layout.removeAllViews();
        for (int i = 0; i < 50; i++) {
            final TextView textView = new TextView(getActivity());
            textView.setTextColor(Color.parseColor("#666666"));
            textView.setTextSize(18);
            textView.setPadding(dp(15),dp(10),dp(15),dp(10));
            textView.setText("这是第" + i + "个文本");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),textView.getText(),Toast.LENGTH_SHORT).show();
                }
            });
            layout.addView(textView);
        }
        refreshableView.setRefreshListener(new YPXQQRefreshView.RefreshListener() {

            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        handler.sendEmptyMessage(SUCCESS);

                    }
                }, 500);
            }
        });
    }

    public int dp(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp,getResources().getDisplayMetrics());
    }
}
