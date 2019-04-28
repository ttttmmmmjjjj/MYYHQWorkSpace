package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	private static DataBaseHelper mInstance = null;
	/** ���ݿ����� **/
	public static final String DATABASE_NAME = "QPOrigins.db";
	/** ���ݿ�汾�� **/
	private static final int DATABASE_VERSION = 2;

	/** ���ݿ�SQL��� ���һ���� **/
	private static final String T_B_CZDW = "create table if not exists T_B_CZDW(NO TEXT,NAME TEXT)";
	private static final String T_B_MediaInfo = "create table if not exists T_B_MediaInfo(NO TEXT,NAME TEXT)";
	private static final String T_B_GPLX = "create table if not exists T_B_GPLX(NO TEXT,NAME TEXT)";

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/** ����ģʽ **/
	public static synchronized DataBaseHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DataBaseHelper(context);
		}
		return mInstance;
	}
	/**
	 * �����װ��λ
	 * @param db
	 */
	private void insertCZDWTables(SQLiteDatabase db) {
		db.beginTransaction();
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('0301','�����촬(����)�������ι�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('0401','Һ�������Ϻ����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('0701','�Ϻ���ѹ�ҵ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('0702','�Ϻ�����Ժ���������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('0801','Һ���������Ϻ����������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('0901','�Ϻ������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('0902','�Ϻ����������������ι�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1201','�Ϻ��ȼ���ɷ����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1203','�Ϻ��������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1204','�Ϻ��ֵ¶�����̼���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1207','�Ϻ����ͻ�����')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1208','�Ϻ���ũ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1210','�Ϻ���̫������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1211','�Ϻ�Һ��ʯ������Ӫ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1212','�Ϻ��ƾٻ������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1213','�Ϻ����������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1215','�Ϻ��ȼҵ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1217','�Ϻ����ɹ�ҵ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1301','�Ϻ���������������ι�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1302','�Ϻ��������幤ҵ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1303','�Ϻ��������幤ҵ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1304','�Ϻ�����������˹ʵ���������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1305','�Ϻ�����ұ��������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1306','�Ϻ�ڡ�����廯�����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1307','�Ϻ���˹��ҵ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1309','�Ϻ���ŷ�����幤ҵ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1311','�Ϻ�������˧�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1312','�Ϻ���ԿƼ����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1315','�Ϻ����������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1316','�Ϻ������Ƽ���չ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1317','�Ϻ�բ�����峧')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1318','�㶫��Ȼó�����޹�˾�Ϻ��ֹ�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1319','BP(�Ϻ�)Һ��ʯ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1320','�Ϻ���˹����Դ��չ���޹�˾(���д���վ)')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1323','�Ϻ����˻������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1324','�Ϻ�����������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1325','�Ϻ������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1328','�Ϻ����º�ȼ��������չ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1329','ϲ�����Ϻ���Һ��ʯ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1401','�Ϻ�¦�������װ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1402','�Ϻ��ɹ����幤ҵ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1403','�Ϻ����������װ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1404','�Ϻ���﹤ҵ�����װ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1405','�Ϻ������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1407','�Ϻ�����Һ̬�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1410','�Ϻ���ï�����������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1411','�Ϻ��ȼ���Һ�����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1412','�Ϻ��ĺ껯ѧƷ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1414','�Ϻ��ζ�ȼ�����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1415','�Ϻ�ΰ���ֽ������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1416','�Ϻ��������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1419','�Ϻ�ΰ���ֽ��������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1501','�Ϻ�����¡���������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1502','�Ϻ��»���ѧ���峧')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1503','�Ϻ�����-÷���������Ʒ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1504','�Ϻ�������ҵ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1505','�Ϻ��ֶ������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1507','�Ϻ������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1508','�Ϻ����Ÿ�˹ʵҵ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1509','�Ϻ��������������ƽ����������װ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1510','�Ϻ�������˹�ǵ�ʵ���������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1511','�Ϻ�������Դ�ɷ����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1512','�Ϻ���˹����Դ��չ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1513','�Ϻ��ֶ�����ȼ�����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1515','�Ϻ������弼�����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1602','�й�ʯ���Ϻ�ʯ���ɷݹ�����ҵ��˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1603','�Ϻ�ʯ����Դ����ʵҵ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1604','�Ϻ����ʯ�����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1606','�Ϻ���ɽ��ɽ��������')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1607','�Ϻ���ɽҺ������˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1608','�����ʢ(�Ϻ�)��ҵ��չ�ɷ����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1609','�Ϻ����������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1610','�Ϻ���ʱ��ܵ�ȼ����Ӫ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1611','�Ϻ�������ҵ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1612','�Ϻ�ʢ孻������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1613','�Ϻ������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1614','˳�죨�Ϻ�����Դ������չ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1701','�Ϻ������Ȳ����')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1702','�Ϻ����й�ҵ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1703','�Ϻ��������弼�����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1704','�Ϻ������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1705','�Ϻ��ɽ���������վ')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1706','�Ϻ��ɽ�ȼ�����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1707','�Ϻ�����ȼ�����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1708','���޾�Ե���ϣ��Ϻ������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1799','�Ϻ������Ȳ����(�����Ȳ��ƿ��Ȩת��)')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1801','�Ϻ�ɣ�﹤ҵ���幫˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1802','�Ϻ��Ű���ҵ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1803','�Ϻ��������幫˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1804','�Ϻ�����Һ̬�����װ��')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1805','�Ϻ�������ҽ���������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1807','����Τ���������(�й�)���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1808','�Ϻ�������ú��������')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1809','�Ϻ���ŵ���������ɷ����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1810','�Ϻ�������׼�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1901','�Ϻ������ܽ���Ȳ����')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1902','�Ϻ�������ҵ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1903','�Ϻ��ϻ㻯���Ṥ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1906','�Ϻ����ϻ�Һ������˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1907','�Ϻ���˹˼����Դ��չ���޹�˾  �ϻ㶫������վ')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1909','�Ϻ�����Һ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1910','�Ϻ�����ȼ����չ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1911','�Ϻ�������ҵ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1912','�Ϻ��׺���ҵ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('1913','�Ϻ���ʢ��ҵ�������޹�˾����ũ����װ��')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('2001','�Ϻ���ͷ��¡���������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('2002','�Ϻ��ϻ���ҵ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('2003','�Ϻ�����������')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('2004','�Ϻ����ữ�����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('2005','�Ϻ�����ȼ���ɷ����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('2007','�Ϻ���ҫ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('2008','˹�����ǵ���(�Ϻ�)���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('2009','�Ϻ���ѧ��ҵ���ֽ������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('2010','�Ϻ����ͽ�ͨҺ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('2011','�Ϻ��Ѻ͵��ӻ�ѧ�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('3001','�Ϻ�����ʢ��ϸ������')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('3003','�Ϻ��г���������')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('3004','�Ϻ����������')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('3005','�Ϻ��б�ɽ������������')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('3006','�Ϻ���ұ����������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('3007','�Ϻ�ȼ���������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('3008','�Ϻ���̩�������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('3009','�Ϻ��񻪾����������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('3010','�Ϻ������������޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('3011','�Ϻ�Ӣ��ʵҵ���޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('3012','�Ϻ����꽭ȼ�����޹�˾')");
		db.execSQL("insert or ignore into T_B_CZDW(NO,NAME)values('3013','�Ϻ�孺�ȼ���������ι�˾')");

		db.setTransactionSuccessful();
		db.endTransaction();
		Log.e("�����װ��λ", "�ɹ�");
	}
	/**
	 * ����������ʱ�
	 * @param db
	 */
	private void insertMediaInfoTables(SQLiteDatabase db) {
		Log.e("����������ʱ�", "=====");
		db.beginTransaction();
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('00010','�����')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10010','�ܽ���Ȳ')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10020','ѹ������')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10050','��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10060','�(���)')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10061','벻�ϣ����벣�')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10062','�ߴ��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10063','�����')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10110','����')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10130','������̼')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10160','ѹ��һ����̼')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10180','R22������һ�ȼ��飩')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10300','�������� �������飨 R152A��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10400','��������')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10460','��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10490','��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10650','Һ�')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10660','��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10661','����')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10662','�ߴ���')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10720','������ҵ����')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10721','ҽ����')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10722','�ߴ���')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10730','Һ��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10750','Һ��ʯ����')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10770','��ϩ')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10780','Һ�������飨δ�����������壩')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10781','R507')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10783','LBA')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('10800','��������SF6')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('19510','Һ�')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('19620','��ϩ')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('19690','�춡��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('19710','ѹ����Ȼ��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('19720','Һ����Ȼ��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('19760','�˷�������(RC318)')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('19770','Һ��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('19780','����')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('20350','1,1,1-�������飨��������R143a��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('24510','��������NF3')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('31590','1,1,1,2-�ķ�����(��������R134a��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('32960','�߷����飨��������R227��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('32961','IG541�����Ӿ���')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('32962','����������������')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('32963','������飨HFC-245fa��')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('33370','��������R404A')");
		db.execSQL("insert or ignore into T_B_MediaInfo(NO,NAME)values('33400','��������R407C')");
		db.setTransactionSuccessful();
		db.endTransaction();
		Log.e("����������ʱ�", "�ɹ�");
	}
	/*
	 * 
	 * �����ƿ���ͱ�
	 */
	private void insertT_B_GPLXTables(SQLiteDatabase db) {
		db.beginTransaction();
		Log.e("��������ƿ���ͱ�", "===");
		db.execSQL("insert or ignore into T_B_GPLX(NO,NAME)values('01','�����޷���ƿ')");
		db.execSQL("insert or ignore into T_B_GPLX(NO,NAME)values('02','���ʺ�����ƿ')");
		db.execSQL("insert or ignore into T_B_GPLX(NO,NAME)values('03','Һ��ʯ����ƿ')");
		db.execSQL("insert or ignore into T_B_GPLX(NO,NAME)values('04','�ܽ���Ȳ��ƿ')");
		db.execSQL("insert or ignore into T_B_GPLX(NO,NAME)values('05','������ƿ')");
		db.execSQL("insert or ignore into T_B_GPLX(NO,NAME)values('06','���¾�����ƿ')");
		db.execSQL("insert or ignore into T_B_GPLX(NO,NAME)values('07','������ƿ ')");
		
		db.setTransactionSuccessful();
		db.endTransaction();
		Log.e("��������ƿ���ͱ�", "�ɹ�");
	}
	private void creatTables(SQLiteDatabase db) {
		db.execSQL(T_B_CZDW);
		db.execSQL(T_B_MediaInfo);
		db.execSQL(T_B_GPLX);
	}

	private void deleteTables(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS T_B_CZDW");
		db.execSQL("DROP TABLE IF EXISTS T_B_MediaInfo");
		db.execSQL("DROP TABLE IF EXISTS T_B_GPLX");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		creatTables(db);
		insertCZDWTables(db);
		insertMediaInfoTables(db);
		insertT_B_GPLXTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (newVersion > oldVersion) {
			deleteTables(db);
			creatTables(db);
			insertCZDWTables(db);
			insertMediaInfoTables(db);
			insertT_B_GPLXTables(db);
		}
	}

	/**
	 * ɾ�����ݿ�
	 * 
	 * @param context
	 * @return
	 */
	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(DATABASE_NAME);
	}
}
