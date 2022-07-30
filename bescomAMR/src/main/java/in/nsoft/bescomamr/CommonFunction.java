package in.nsoft.bescomamr;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
/**
 * 
 * @author Tamilselvan on 18-03-2014
 * General functions
 */
public class CommonFunction {

	private static final String TAG = CommonFunction.class.getName();

	/**
	 * Tamilselvan on 12-02-2014
	 * @Description Input date like yyyy-MM-dd and Output dd-MM-yyyy
	 * @param date 
	 * @return date like dd-MM-yyyy
	 */
	public String DateConvert(String date)//yyyy-MM-dd
	{
		String sp[] = date.split("-");
		String NewFormat = sp[2] + "-" + sp[1] + "-" + sp[0];
		return NewFormat;//dd-MM-yyyy
	}
	/**
	 * Tamilselvan on 25-02-2014
	 * 
	 * @param date (ddMMyyyy)
	 * @param SpacingChar (- or / or :) any char
	 * @return get like dd-MM-yyyy based on SpacingChar
	 */
	//Created by Tamilselvan on 25-02-2014
	public String DateConvertAddChar(String date, String SpacingChar)//ddMMyyyy
	{
		String NewFormat = date.substring(0, 2)+SpacingChar+ date.substring(2, 4)+SpacingChar+ date.substring(4, 8);
		return NewFormat;//dd-MM-yyyy
	}

	/**
	 * get current month of the year
	 */
	public static String getCurrentDateByPara(String str)
	{
		String strValue = "";
		try
		{
			String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());			
			if(str.equals("Date"))
			{
				strValue = currentTime.substring(0, 2);
			}
			else if(str.equals("Month"))
			{
				strValue = currentTime.substring(3, 5);
			}
			else if(str.equals("Year"))
			{
				strValue = currentTime.substring(6, 10);
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return strValue;
	}


	//Tamilselvan on 18-03-2014
	public String SplitTarifString(String s)
	{
		String sStart[] = s.split("%%");
		String sEnd[] = sStart[1].split("#");
		return sEnd[0];		

	}
	

	/**
	 * Tamilselvan on 24-03-2014
	 * based on SanctionLoad load concat SanctionLoad with HP or KW
	 * @param SanctionLoad 
	 * @return
	 */
	public String GetSancLoadWithHPKW(ReadSlabNTarifSbmBillCollection sbc)//String SanctionLoad)
	{
		String sLoadUnitKW = "KW"; 
		String sLoadUnitHP = "HP"; 
		BigDecimal  sancLoad;
		String SanctionLoad = "";

		//Modified by Tamilselvan on 22-04-2014 (reference by Gotham and Naveen)
		//Compare SancKW and SamcHP Which one is greater take that one
		if(sbc.getmSancHp().compareTo(new BigDecimal(0.00)) > 0)
		{
			sancLoad = sbc.getmSancHp().setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		else if(sbc.getmSancKw().compareTo(new BigDecimal(0.00)) > 0)
		{
			sancLoad = sbc.getmSancKw().setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		else
		{
			sancLoad = sbc.getmSancKw().setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		/*if(sbc.getmSancKw().compareTo(sbc.getmSancHp()) > 0)
		{
			sancLoad = sbc.getmSancKw().setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		else
		{
			sancLoad = sbc.getmSancHp().setScale(2, BigDecimal.ROUND_HALF_UP);
		}*///END Modified by Tamilselvan on 22-04-2014 (reference by Gotham and Naveen)
		if(sbc.getmTarifString().indexOf("LT-1") != -1 || sbc.getmTarifString().indexOf("LT-2") != -1 || sbc.getmTarifString().indexOf("LT-3") != -1 || BillingObject.GetBillingObject().getmTarifString().indexOf("LT-7") != -1)
		{
			SanctionLoad = sancLoad + sLoadUnitKW;
		}
		else if(sbc.getmTarifString().indexOf("LT-4") != -1 || sbc.getmTarifString().indexOf("LT-5") != -1)
		{
			//SanctionLoad = sancLoad + sLoadUnitHP;
			//23-08-2016
			if(sbc.getmTariffCode()  == 94 || sbc.getmTariffCode() == 95 || sbc.getmTariffCode() == 126 || sbc.getmTariffCode() == 127 || sbc.getmTariffCode() == 164 || sbc.getmTariffCode() == 165)//LT-5(A)DBT and LT-5(B)DBT
			{
				SanctionLoad = sancLoad + sLoadUnitKW;
			}
			else
			{
				SanctionLoad = sancLoad + sLoadUnitHP;
			}
		}
		else if(sbc.getmTarifString().indexOf("LT-6") != -1)
		{
			if(sbc.getmTariffCode() == 101)//101 for LT-6 STLT(Street Light)
			{
				SanctionLoad = sancLoad + sLoadUnitKW;
			}
			else if(sbc.getmTariffCode() == 100)//100 for LT-6 WW
			{
				SanctionLoad = sancLoad + sLoadUnitHP;
			}
		}
		//return sancLoad.equals(BigDecimal.ZERO) ? (sancLoad + sLoadUnitHP) : (sancLoad + sLoadUnitKW);
		return SanctionLoad;
	}

	/**
	 * Tamilselvan on 25-03-2014
	 * 
	 * @param str
	 * @return value with 2 decimal places
	 */
	public String GetTwoDecimalPlace(String str)
	{
		if(str == null)
		{
			str = "0.00";
		}
		if(str.trim().length() > 0)
		{
			if(str.indexOf(".") == -1)//if . not there then add .00 to the string
			{
				str = str + ".00";
			}
			else
			{
				String[] dotStr = str.split("\\.");
				if(dotStr.length > 0)
				{
					if(dotStr[1].length() == 0)
					{
						str = str + "00";
					}
					else if(dotStr[1].length() == 1)
					{
						str = str + "0";
					}
					else if(dotStr[1].length() > 2)
					{
						str = dotStr[0] + "." + dotStr[1].substring(0, 2);
					}
				}
			}
		}
		else
		{
			str = "0.00";
		}
		return str;
	}
	// punit 24032014 start
	public String statusAsName(int s)
	{   
		String statusName;
		switch (s) {
		case 1:statusName="DL";
		break;
		case 2:statusName="MNR";
		break;
		case 3:statusName="DO";
		break;
		case 4:statusName="MCH";
		break;
		case 5:statusName="RNF";
		break;
		case 6:statusName="VA";
		break;
		case 7:statusName="MR";
		break;
		case 8:statusName="DIS";
		break;
		case 9:statusName="MS";
		break;
		case 10:statusName="DIR";
		break;
		case 14:statusName="MB";
		break;
		case 15:statusName="NT";
		break;

		default:statusName="Invalid Status";
		break;
		}
		return statusName;


	}
	//punit 24032014 stop

	/**
	 * Tamilselvan on 22-04-2014
	 * 
	 * @return
	 */
	public static String GetTimeStampName()
	{
		String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		currentTime = currentTime.replace(" ", "_").replace(":", "");
		return currentTime;
	}

	/**
	 * Tamilselvan on 26-03-2014
	 * @return current date and time 
	 */
	public static String GetCurrentDateTime()
	{
		String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		return currentTime;
	}

	/**
	 * Tamilselvan on 26-03-2014
	 * @return current date and time 
	 */
	public String GetCurrentTime()
	{
		String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		return currentTime;
	}
	//Tamilselvan on 26-03-2014
	public String GetCurrentTimeWOSPChar()
	{		
		String currentTime = GetCurrentTime();		 
		currentTime = currentTime.replace("-20", "");
		currentTime = currentTime.replace("-", "");
		currentTime = currentTime.replace(":", "");
		currentTime = currentTime.replace(" ", "");
		//currentTime = currentTime.trim();
		return currentTime;
	}

	/**
	 * Tamilselvan on 26-03-2014
	 * @param date ddMMyyHHmmss
	 * @return date and time in this format dd-MM-yyyy HH:mm:ss
	 */
	public String GetCurrentTimeWSPChar(String date)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(date);
		sb.insert(2, '-');
		sb.insert(5, "-20");
		sb.insert(10, " ");
		sb.insert(13, ':');
		sb.insert(16, ':');
		return sb.toString();
	}
	public static String getPhotoStream(String PhotoName)
	{
		FileInputStream myInput = null;
		//OutputStream myOutput = null;
		byte[] bufferFinal = null;
		String encImage = "";
		try
		{
			//String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
			String timeStamp = "";
			if(PhotoName != "")
			{
				//IMEINO_CONNID_Date_Time
				String[] getSplitStr = PhotoName.split("_");
				if(getSplitStr.length >= 4) //08-07-2021
				{
					timeStamp = getSplitStr[2].substring(0, 2) + "-" + getSplitStr[2].substring(2, 4) + "-" + getSplitStr[2].substring(4, 8);
				}
			}

			String root = Environment.getExternalStorageDirectory().getPath();//get the sd Card Path			
			File f = new File(root+"/AMRSurvey/"+timeStamp+"/Photos", PhotoName);
			myInput = new FileInputStream(f);
			Bitmap bn = BitmapFactory.decodeStream(myInput);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Bitmap bm = Bitmap.createScaledBitmap(bn, 1280, 720, false); //20-09-2021
			//Bitmap bm = Bitmap.createScaledBitmap(bn, 640, 360, false); //20-09-2021
			bm.compress(Bitmap.CompressFormat.JPEG, 15, baos); //20-09-2021 Compress to 15%
			byte[] b = baos.toByteArray();
			encImage = Base64.encodeToString(b, Base64.DEFAULT);
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
			bufferFinal = null;
			if(myInput != null)
			{
				try {
					myInput.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return encImage;
	}
	//Created by Nitish on 08-04-2014
	public String DateConvertRemoveChar(String date)//dd-MM-yyyy
	{
		String NewFormat = date.substring(0, 2)+ date.substring(3, 5) + date.substring(8, 10);
		return NewFormat;//ddMMyy
	}
	//Created by Nitish on 08-04-2014
	public String DateConvertFullYear(String date)//dd-MM-yyyy
	{
		String NewFormat = date.substring(0, 2)+ date.substring(3, 5) + date.substring(6, 10);
		return NewFormat;//ddMMyyyy
	}
	//Created by Nitish on 21-04-2014
	public String TimeConvertAddChar(String time, String SpacingChar)//hhmm
	{
		String NewFormat = time.substring(0, 2)+SpacingChar+ time.substring(2, 4);
		return NewFormat;//hh:mm
	}
	
	//Created by Nitish on 08-05-2014
	public String addSpace(String str, int count, char spacingChar) 
	{
		int k = 0;
		while(k < count)
		{
			//str = str + spacingChar;
			str = spacingChar + str;
			k++;
		}
		return str;
	}
	//Created by Nitish on 08-05-2014
	public String addSpaceRight(String str, int count, char spacingChar) 
	{
		int k = 0;
		while(k < count)
		{
			//str = str + spacingChar;
			str =  str + spacingChar;
			k++;
		}
		return str;
	}
	public String GetConnectionIdPadding(String conid)
	{
		String zero = "0";
		while(conid.length() < 7)
		{
			conid = zero + conid;
		}		
		return conid;
	}
	//Created by Nitish on 29-05-2014
	public String GetReceiptNoPadding(String recno)//String SanctionLoad)
	{
		String zero = "0";
		while(recno.length() < 5)
		{
			recno = zero + recno;
		}		
		return recno;
	}
	//Created by Nitish on 10-06-2014
	public String YearMMDDReversal(String date,String SpacingChar)//YYYYMMDD
	{
		String NewFormat = date.substring(6,8)+SpacingChar+ date.substring(4, 6)+SpacingChar+ date.substring(0, 4);
		return NewFormat;//dd-MM-yyyy
	}

	//Created by Nitish on 19-08-2014
	public String GetBalanceForMISCReceipt(String rem) // #EXAMPLE = Bal;90
	{
		/*String zero = "0";
		if(rem.trim().equals(""))
		{
			return (zero + ConstantClass.sPaise); //Balance  =0.00
		}
		else
		{
			String Remarks[] = rem.trim().split(";");  // #EXAMPLE = Bal;90
			return (Remarks[1].trim() + ConstantClass.sPaise);  //Balance = 90
		}*/		
		return rem.trim();
	}

	//Nitish 30-06-2015
	public static String getPhotoStreamFaceRecognition(String CreatedDateTime,String PhotoName)
	{
		//CreatedDateTime = dd-mm-yyyy HH:mm:ss
		//PhotoName = IMEINumber_Similarity
		FileInputStream myInput = null;
		//OutputStream myOutput = null;
		byte[] bufferFinal = null;
		String encImage = "";
		try
		{		
			String CreatedDate = "";
			if(CreatedDateTime != "")	
			{
				CreatedDate=CreatedDateTime.substring(0,10);
			}

			String root = Environment.getExternalStorageDirectory().getPath()+"/FaceDetectionImage/" +CreatedDate;
			File f = new File(root, PhotoName);
			myInput = new FileInputStream(f);
			Bitmap bn = BitmapFactory.decodeStream(myInput);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Bitmap bm = Bitmap.createScaledBitmap(bn, 250, 250, false); //08-06-2016
			bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] b = baos.toByteArray();
			encImage = Base64.encodeToString(b, Base64.DEFAULT);
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
			bufferFinal = null;
			if(myInput != null)
			{
				try {
					myInput.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return encImage;
	}
	public static String getDeviceNo(Context context)
	{
		String DeviceId = "";
		try
		{
			
			if(Build.VERSION.SDK_INT >= 29)	
			{
				DeviceId = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
				if(DeviceId.length()>15)
					DeviceId = DeviceId.substring(DeviceId.length()-15, DeviceId.length());
				
			}
			
			else
			{
				TelephonyManager mgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
				DeviceId = mgr.getDeviceId();
				
			}			
			
			
		}
		catch(Exception e)
		{
			Log.d("", "");		
		}
		return DeviceId;

	}
	 public static String getSignalStrength(Context context) throws SecurityException {
		    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		    String strength = null;
		    List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();   //This will give info of all sims present inside your mobile
		    if(cellInfos != null) {
		        for (int i = 0 ; i < cellInfos.size() ; i++) {
		            if (cellInfos.get(i).isRegistered()) {
		                if (cellInfos.get(i) instanceof CellInfoWcdma) {
		                    CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfos.get(i);
		                    CellSignalStrengthWcdma cellSignalStrengthWcdma = cellInfoWcdma.getCellSignalStrength();
		                    strength = String.valueOf(cellSignalStrengthWcdma.getDbm());
		                } else if (cellInfos.get(i) instanceof CellInfoGsm) {
		                    CellInfoGsm cellInfogsm = (CellInfoGsm) cellInfos.get(i);
		                    CellSignalStrengthGsm cellSignalStrengthGsm = cellInfogsm.getCellSignalStrength();
		                    strength = String.valueOf(cellSignalStrengthGsm.getDbm());
		                } else if (cellInfos.get(i) instanceof CellInfoLte) {
		                    CellInfoLte cellInfoLte = (CellInfoLte) cellInfos.get(i);
		                    CellSignalStrengthLte cellSignalStrengthLte = cellInfoLte.getCellSignalStrength();
		                    strength = String.valueOf(cellSignalStrengthLte.getDbm());
		                } else if (cellInfos.get(i) instanceof CellInfoCdma) {
		                    CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfos.get(i);
		                    CellSignalStrengthCdma cellSignalStrengthCdma = cellInfoCdma.getCellSignalStrength();
		                    strength = String.valueOf(cellSignalStrengthCdma.getDbm());
		                }
		            }
		        }
		    }
		    return strength;
		}
	

}
