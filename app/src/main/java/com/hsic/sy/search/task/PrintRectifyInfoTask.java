package com.hsic.sy.search.task;
import android.content.Context;
import android.os.AsyncTask;

import com.hsic.sy.search.listener.PrintRectifyInfoListener;

public class PrintRectifyInfoTask extends AsyncTask<String[], Void, Boolean>{
	Context context;
	PrintRectifyInfoListener listener;
	public PrintRectifyInfoTask(Context context,PrintRectifyInfoListener listener){
		this.context=context;
		this.listener=listener;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(String[]... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
