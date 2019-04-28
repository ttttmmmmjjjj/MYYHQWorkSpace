package com.hsic.bll;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/1/7.
 */

public class ViewUtil {
    /**
     * 判断是否是停止供气
     * @param stop
     * @return
     */
    public static  boolean judgeText(TextView stop) {
        boolean flag = false;
        if (stop.getText().toString().trim().equals("安全巡检不合格，停止供气，交易结束")) {
            flag = true;
        }
        return flag;
    }
    /**
     * 判断气瓶的数量
     * @param
     * @param
     * @param goodscount
     * @return
     */
    public static boolean judgeGoodsCount(TextView qiPing,String goodscount) {
        boolean flag = false;
        String text = qiPing.getText().toString();
        int goodsCount = Integer.parseInt(goodscount);
        int Count=(text.length() + 1) / 9;
        if (Count== goodsCount) {
            flag = true;
        }
        return flag;
    }
    /**
     *
     * @param data
     * @param blow
     * @param relativeLayout
     * @return
     * 更新巡检停止供气界面
     */
    public static String reflashUiStop(String data, TextView blow,
                                RelativeLayout relativeLayout) {
        if (data.length() > 0) {
            relativeLayout.setVisibility(View.GONE);
            blow.setVisibility(View.VISIBLE);
            blow.setTextColor(Color.RED);
            blow.setText("安全巡检不合格，停止供气，交易结束");
        }
        return data;
    }
    /**
     *
     * @param data
     * @param text
     * 更新查看标签号
     */
    public static void reflashUiCheckLook(Intent data, TextView text) {
        Bundle bundle1 = data.getBundleExtra("bundle");
        ArrayList<String> list = bundle1.getStringArrayList("list");
        text.setText("");
        if (list.size() == 0) {
            text.setText("");
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (text.getText().toString().length() == 0) {
                    text.append(list.get(i));
                } else {
                    text.append("," + list.get(i));
                }
            }
        }
    }
    /**
     *
     * @param user
     * @param text 要标记的字
     * 做特殊标记的方法
     */
    public static void reminderState(TextView user,String text) {
        String htmlLinkText="";
        htmlLinkText=user.getText().toString();
        int start = htmlLinkText.indexOf(text);
        int end = htmlLinkText.length() - 1;
        SpannableStringBuilder style = new SpannableStringBuilder(htmlLinkText);
        style.setSpan(new URLSpanNoUnderline(text), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        user.setText(style);
    }
    public static void setWordsColor(TextView user,String text,String txt){
        String htmlLinkText="";
        htmlLinkText=user.getText().toString();
        int startr = htmlLinkText.indexOf(text);
        int endr= startr+text.length();
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        int startb=htmlLinkText.indexOf(txt);
        int endb=htmlLinkText.length() - 1;
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);
        SpannableStringBuilder builder = new SpannableStringBuilder(htmlLinkText);
        builder.setSpan(redSpan, startr, endr,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(blueSpan, startb, endb,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        user.setText(builder);

    }
    //
    public static void setTextViewColor(ForegroundColorSpan color,TextView user,String text) {
        String htmlLinkText="";
        htmlLinkText=user.getText().toString();
        int start = htmlLinkText.indexOf(text);
        int end = htmlLinkText.length() - 1;
        SpannableStringBuilder style = new SpannableStringBuilder(htmlLinkText);
        style.setSpan(color, start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        user.setText(style);
    }

    /**
     * 是否赠送橡皮管
     * @param presentText
     * @param color
     * @param text
     * @param sale
     * @param present
     * @param presentButton
     */
    public static void upData(String presentText, int color, String text,
                       String sale,TextView present, Button presentButton) {
        if (sale != null && !sale.equals("")) {
            present.setText(presentText);
            present.setTextColor(color);
            presentButton.setText(text);

        }
    }
    /**
     *
     * @param checkUserinfos 后台满瓶号
     * @param s 读到的标签号
     * @param goodsCount 商品数量
     * @param textView
     * @param context
     * @param tishi 提示语
     * 校验瓶号
     */
    public static void judgeCount(String checkUserinfos, String s, String goodsCount,
                           TextView textView, Context context, String tishi) {
        if (checkUserinfos.contains(s)) {
            int i=0;
            i=(textView.getText().toString().length() + 1) / 9;
            if (i < Integer.parseInt(goodsCount)) {
                if (textView.getText().toString().equals("")) {
                    textView.append(s);
                } else {
                    if (!textView.getText().toString().contains(s)) {
                        textView.append("," + s);
                    } else {
                        Toast.makeText(context, "该瓶已扫描，请扫描其他瓶号",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(context, tishi, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static class URLSpanNoUnderline extends ClickableSpan {

        public String text;

        public  URLSpanNoUnderline(String s) {
            this.text = s;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false); // 取消下划线
            ds.setColor(0xffCC3300); // 指定文字颜色
        }

        @Override
        public void onClick(View widget) {
            // TODO Auto-generated method stub

        }

    }
}
