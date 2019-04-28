package com.hsic.sy.bll;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import com.hsic.sy.supplystationmanager.AdvConfigActivity;

public class ActivityUtil {
	public static void JumpToAdvConfig(Context context) {
		Intent intent = new Intent(context, AdvConfigActivity.class);
		context.startActivity(intent);
	}
}
