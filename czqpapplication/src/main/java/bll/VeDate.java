package bll;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hsic.qpmanager.util.json.JSONUtils;

import android.util.Log;

public class VeDate {
	/**
	 * �ж��Ƿ񳬹��´μ������� ���߰�
	 * 
	 * @throws ParseException
	 */
	public static boolean CompareDate(String str) {
		DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = format1.parse(str);

			Log.e("�´μ�������", JSONUtils.toJsonWithGson(date));
			Log.e("��ǰ����", JSONUtils.toJsonWithGson(getNowDate()));
			if (date.before(getNowDate())) {
				// �����´μ�������

				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false; 
		}
	}

	/**
	 * �ж��Ƿ񳬹��´μ������� ���߰�
	 * 
	 * @throws ParseException
	 */
	public static boolean CompareDate2(String str) {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format1.parse(str);
			Log.e("�´μ�������", JSONUtils.toJsonWithGson(date));
			Log.e("��ǰ����", JSONUtils.toJsonWithGson(getNowDate()));
			if (date.before(getNowDate())) {
				// �����´μ�������
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * ��ȡ��ǰʱ��
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getNowDate() throws ParseException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		Date currentTime_2 = formatter.parse(dateString);
		return currentTime_2;
	}
}
