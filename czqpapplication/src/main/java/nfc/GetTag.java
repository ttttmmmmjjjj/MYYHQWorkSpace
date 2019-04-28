package nfc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.util.Log;

public class GetTag {
	Tag tagFromIntent;
	public String  getTag(Intent intent,String DeviceType){
		String Ret="";
		try{
			Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			try{
				NfcV nfcV = NfcV.get(tagFromIntent);
				nfcV.connect();
				NfcVUtil mNfcVutil = new NfcVUtil(nfcV);
				mNfcVutil.getUID();
				String uid = mNfcVutil.getUID();
				if(DeviceType.equals("Android Handheld Terminal")){
//					Ret=mNfcVutil.readBlocksNormal(0, 64);
					Ret=mNfcVutil.readOneBlockBySYDevice(3);
//					Ret=mNfcVutil.readBlocks(0, 64);//��ʱ������ɫ�ֻ�
				}else{
					Ret=mNfcVutil.readOneBlockByNormal(3);
				}
				
			}catch(Exception ex){
				ex.toString();
			}	
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return Ret;
	}
	public String[]  getTag2(Intent intent,String DeviceType,int Count){
		String[] ret=new String[3];
		String read="";
		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		try{
			NfcV nfcV = NfcV.get(tagFromIntent);
			nfcV.connect();
			NfcVUtil mNfcVutil = new NfcVUtil(nfcV);
			mNfcVutil.getUID();
			ret[0] = mNfcVutil.getUID();
			if(DeviceType.equals("Android Handheld Terminal")){
				mNfcVutil.ReadBlockBySYDevice(0,Count);
				Log.e("读到的结果 Terminal","="+read);
			}else{
				ret[1]=mNfcVutil.ReadBlocksByNormal(0,Count);
				read=mNfcVutil.ReadBlocksByNormal(0,Count);;
				Log.e("读到的结果","="+read);
			}
			
		}catch(Exception ex){
			
		}
		return ret;
	}
	/**
	 * 
	 * @param intent
	 * @param startBlock ��ʼ��
	 * @param Count	����
	 * @return
	 */
	public String[]  getTag(Intent intent,int startBlock,int Count ){
		String[] ret=new String[3];
		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		try{
			NfcV nfcV = NfcV.get(tagFromIntent);
			nfcV.connect();
			NfcVUtil mNfcVutil = new NfcVUtil(nfcV);
			mNfcVutil.getUID();
			ret[0] = mNfcVutil.getUID();
			ret[1]=mNfcVutil.ReadBlocksByNormal(0,Count);

			
		}catch(Exception ex){
			ret=null;
		}
		return ret;
	}
	public String[] GetStuffID(Intent intent){
		String[] ret=new String[2];
		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		try{
			NfcV nfcV = NfcV.get(tagFromIntent);
			nfcV.connect();
			NfcVUtil mNfcVutil = new NfcVUtil(nfcV);
			mNfcVutil.getUID();
			ret[0] = mNfcVutil.getUID();
			ret[1]=mNfcVutil.ReadBlocksByNormal(0,3);
			
		}catch(Exception ex){
			
		}
		return ret;
	}
	
}
