package fragment;


import java.net.NetworkInterface;
import java.util.Enumeration;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.KeyEvent;

import com.hsic.sy.czqpapplication.R;

public class AdvConfigFragment extends PreferenceFragment{
	private SharedPreferences mPreferences;
	SharedPreferences device;
	private String ipStr,portStr;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.config);
		mPreferences = getPreferenceScreen().getSharedPreferences();
		ipStr=getActivity().getResources()
				.getString(R.string.ip_default);
		portStr=getActivity().getResources()
				.getString(R.string.port_default);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		super.getActivity().onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.getActivity().finish();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onStart() {
		super.onStart();	
		findPreference("ip").setSummary(mPreferences.getString("ip", ipStr));
		findPreference("port").setSummary(// ws
				mPreferences.getString("port", portStr));
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		// TODO Auto-generated method stub
		if (preference.getKey().equals("bluetooth")) {
			// ���������������������
//			Log.e("�����﷢�������㲥", "1");
//			Intent i = new Intent(getActivity(), SetBlueToothPrinter.class);
//			startActivity(i);

		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	public String getMac(Context context) {
		try {
			for (Enumeration<NetworkInterface> e = NetworkInterface
					.getNetworkInterfaces(); e.hasMoreElements();) {
				NetworkInterface item = e.nextElement();
				byte[] mac = item.getHardwareAddress();
				if (mac != null && mac.length > 0) {
					return new String(mac);
				}
			}
		} catch (Exception e) {
		}
		return "";
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(
						mOnSharedPreferenceChangeListener);
//		bluetooth = device.getString("BlueToothAdd", "");
//		if (!bluetooth.equals("")) {
//			Log.e("ConfigFragment", bluetooth);
//			findPreference("bluetooth").setSummary(bluetooth);
//		}

	}

	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(
						mOnSharedPreferenceChangeListener);
	}

	private OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener = new OnSharedPreferenceChangeListener() {
		
		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			Preference pref = findPreference(key);
			if (pref instanceof EditTextPreference) {
				EditTextPreference etp = (EditTextPreference) pref;
				pref.setSummary(etp.getText());
			}
			if (pref.getKey().equals("bluetooth")) {
			}
		}
	};

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		getActivity().finish();

	}

}
