
// Created Nitish 24-02-2014
package in.nsoft.bescomamr;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Billing extends Activity {
	int f;                
	private Button btn, btnprev, btnpnext, btnedit,btnBillingMap,btnNewEntry;	
	private TextView conid, rrNo, tariffCode, load, billed, txtBillingMtd,txtCustomerName,txtAddress,txtMeterSlNo;
	Boolean autoSelected;
	AutoCompleteTextView ConnIdRRNo;
	AutoDDLAdapter conList;
	private final DatabaseHelper mDb =new DatabaseHelper(this);
	private static final String TAG = Billing.class.getName();
	int CharCount = 0;
	Intent indent = null;
	ProgressDialog ringProgress, ringGpsCheck;
	//Bluethooth variables===============================================
	int i = 0;
	Handler dh ;
	//BluetoothPrinting bptr;
	static final int REQUEST_ENABLE_BT = 0;
	//END Bluethooth variables===========================================

	//17-07-2021 for Signal Strength	
	static String signalStrength = "0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_billing);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//AndroidBarcodeQrActivity.scanResults="";
		ConnIdRRNo =(AutoCompleteTextView)findViewById(R.id.autoBillingSearch);	
		txtAddress = (TextView)findViewById(R.id.txtAddress);	
		txtMeterSlNo = (TextView)findViewById(R.id.txtMeterSlNo);	
		final AutoCompleteTextView ConnIdRRNo =(AutoCompleteTextView)findViewById(R.id.autoBillingSearch);	
		btn = (Button)findViewById(R.id.btnBillingOk);
		//btnCollection = (Button)findViewById(R.id.btnBillingCollection);
		conid = (TextView)findViewById(R.id.txtBillingConnectionId);
		rrNo = (TextView)findViewById(R.id.txtBillingRRNo);
		tariffCode =  (TextView)findViewById(R.id.txtBillingTariff);
		load = (TextView)findViewById(R.id.txtBillingLoad);
		txtBillingMtd = (TextView)findViewById(R.id.txtBillingMtd);
		txtCustomerName = (TextView)findViewById(R.id.txtCustomerName);
		txtMeterSlNo = (TextView)findViewById(R.id.txtMeterSlNo);
		billed = (TextView)findViewById(R.id.txtBillingStatus);
		btnprev	 = (Button)findViewById(R.id.btnBillingPrev);
		btnpnext = (Button)findViewById(R.id.btnBillingNext);
		btnedit=(Button)findViewById(R.id.Button01);
		btnNewEntry  = (Button)findViewById(R.id.btnNewEntry);

		btnBillingMap = (Button)findViewById(R.id.btnBillingMap);
		dh = new Handler();
		try 
		{
			//WriteLog.WriteLogError(TAG+" Checking gps tracker.");
			/*if(HomePage.gpsTracker == null || !HomePage.gpsTracker.isGPSConnected())
			{
				Intent i = new Intent(Billing.this, HomePage.class);
				startActivity(i);
				CustomToast.makeText(Billing.this, "GPS Disabled. Please turn on GPS.", Toast.LENGTH_LONG);
				return;
			}
			else
			{
				try
				{
					ringGpsCheck = ProgressDialog.show(Billing.this, "Please wait..", "Locating GPS...",true);//Spinner
					ringGpsCheck.setCancelable(false);
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try
							{
								while(HomePage.gpsTracker.latitude == 0)
								{
									Thread.sleep(100);
								}
								ringGpsCheck.dismiss();								
							}
							catch(Exception e)
							{
								Log.d(TAG, e.toString());	
							}
						}
					}).start();
				}
				catch(Exception e)
				{
					Log.d(TAG, e.toString());	
				}
			}*/
			//Modified By Tamilselvan on 02-04-2014
			//If BillingObject object not contains Connection no Then go and fetch
			//top 1 connection details which is not billed	
			//WriteLog.WriteLogError(TAG+" Loading billing object start.");
			if(BillingObject.GetBillingObject().getmConnectionNo() == null)
			{
				if(mDb.isEveryConnectionBilled())//Check Every connection is billed or not, if yes load 1st Connection
				{             
					mDb.GetPrev("1");
					GetAssignNecessaryFields(BillingObject.GetBillingObject());//Added By Tamilselvan on 02-04-2014
				}
				else//if no load top 1 connection details which is not billed
				{
					mDb.GetValueOnBillingPage();//Get top 1 connection details which is not billed	
					GetAssignNecessaryFields(BillingObject.GetBillingObject());//Added By Tamilselvan on 02-04-2014
				}/**/ 
			}
			else//Based on Previous object(if it is billed) get the next connection details
			{
				int uid = 0;
				if(BillingObject.GetBillingObject().getmBlCnt().equals("1"))//check the Current Object is billed or not 
				{//getmBlCnt is 1 then that connection is billed 
					uid = BillingObject.GetBillingObject().getMmUID() + 1;//get next uid by incrementing 1 
				}
				else//if getmBlCnt is 0 then get the same connection no
				{
					uid = BillingObject.GetBillingObject().getMmUID();
				}
				if(mDb.IsUIDExists(uid))//check uid exists in db or not, if exists then get the billing object
				{
					mDb.GetPrev(String.valueOf(uid));
					GetAssignNecessaryFields(BillingObject.GetBillingObject());//Added By Tamilselvan on 02-04-2014
				}
				else
				{
					mDb.GetPrev(String.valueOf(BillingObject.GetBillingObject().getMmUID()));
					GetAssignNecessaryFields(BillingObject.GetBillingObject());
				}
			}			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try 
		{
			conList = mDb.GetConnIdRRNo();
			ConnIdRRNo.setThreshold(1);	
			ConnIdRRNo.setAdapter(conList);			
			//conList.notifyDataSetChanged();//Added By Tamilselvan on 25-03-2014
			autoSelected=false;
			ConnIdRRNo.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> alist, View arg1,
						int pos, long arg3) {
					//TODO Auto-generated method stub
					try 
					{
						//Nitish  27/02/2014
						DDLItem k = (DDLItem)  alist.getItemAtPosition(pos);					
						mDb.GetAllDatafromDb(k.getId());
						ConnIdRRNo.setText(k.getValue().substring(5).trim());						
						//billObj=	BillingObject.GetBillingObject();	
						GetAssignNecessaryFields(BillingObject.GetBillingObject());//Added By Tamilselvan on 02-04-2014	
						//By Nitish 08-04-2014
						//To Hide Soft KeyBoard
						InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						in.hideSoftInputFromWindow(arg1.getApplicationWindowToken(), 0);
						ConnIdRRNo.setText("");
					} 
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//Billing or Reprint Button --> if the connection is not Billed then this button becomes Billing
		//OR if it is billed then 

		btnedit.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				if(true)
				{
					
					if(BillingObject.GetBillingObject().getmConnectionNo() == null)
					{
						CustomToast.makeText(Billing.this, "No Records.", Toast.LENGTH_SHORT);					
						return;
					}
					
					Intent i = new Intent(Billing.this, CameraActivity.class);					
					startActivity(i);
					
				}
			}
		});
		btnNewEntry.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{

				if(ConnIdRRNo.getText().toString().trim().length()==0)
				{
					CustomToast.makeText(Billing.this, "Enter New Account No/Consumer Name to be Surveyed.", Toast.LENGTH_LONG);					
					return;
				}
				else
				{
					//17-07-2021 for Signal Strength		
					signalStrength = "0";							
					Billing.signalStrength = CommonFunction.getSignalStrength(Billing.this);
					
					//End 17-07-2021			

					if(ConstantClass.mSurveyType.equals("2")) //Water Survey
						BillingObject.GetBillingObject().setmConnectionNo(String.valueOf(mDb.GetMaxWaterSurveyUID()+1)); 
					else //Electricity Survey
						BillingObject.GetBillingObject().setmConnectionNo(String.valueOf(mDb.GetMaxSurveyUID()+1));
						
					BillingObject.GetBillingObject().setmRRNo(ConnIdRRNo.getText().toString().trim());	
					

				/*	Intent i = new Intent(Billing.this, CameraActivity.class);					
					startActivity(i);*/
					
					Intent i = new Intent(Billing.this, ImageCaptureActivity.class);					
					startActivity(i);
					
				}				
			}
		});

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//Nitish 27-02-2014
				try
				{					
					if(btn.getText().equals(ConstantClass.btnOKText))
					{
						/*if(HomePage.gpsTracker.isGPSConnected())
						{*/
						//WriteLog.WriteLogError(TAG+" Billing btn clicked.");
						if(BillingObject.GetBillingObject().getmConnectionNo() == null)
						{
							CustomToast.makeText(Billing.this, "No Records.", Toast.LENGTH_SHORT);					
							return;
						}
						//Added on 09-09-2014 for checking Reading day if Reading day greater than current day
						//should not allow to bill that connection
						/*if(BillingObject.GetBillingObject().getmReadingDay().trim().length() > 0)
						{
							int day = Integer.valueOf(CommonFunction.getCurrentDateByPara("Date"));
							if(Integer.valueOf(BillingObject.GetBillingObject().getmReadingDay().trim()) > day)
							{
								CustomToast.makeText(Billing.this, "Reading day is greater than current day. You can't bill this connection.", Toast.LENGTH_LONG);					
								return;
							}
						}*/
						/*indent = new Intent(Billing.this, CameraActivity.class);
							startActivity(indent);
							Intent i = new Intent(Billing.this, BillingConsumption.class);
							startActivity(i);
							}
						else
						{
							CustomToast.makeText(Billing.this, "GPS Disabled. Please turn on GPS.", Toast.LENGTH_LONG);
						}*/
						//Intent i = new Intent(Billing.this, BillingConsumption.class);
						//startActivity(i);
						//31-10-2015
						//Intent i = new Intent(Billing.this, BillingConsumerDetailsActivity.class);
						//startActivity(i);
						//29-07-2015
						/*Intent i = new Intent(Billing.this, BillingMeterDetailsCaptureActivity.class);
						startActivity(i);*/
						/*Intent i = new Intent(Billing.this, BillingConsumption.class);
						startActivity(i);*/
						Intent i = new Intent(Billing.this, CameraActivity.class);
						//Intent i = new Intent(Billing.this, AndroidBarcodeQrActivity.class); //04-09-2017
						startActivity(i);
						/*Intent i = new Intent(Billing.this, SurveyDetailsActivity.class);					
						startActivity(i);*/
					}
					else if(btn.getText().equals(ConstantClass.btnReprintText))//Re-Print Start
					{
						Intent i = new Intent(Billing.this, ReportSurveyDetailsActivity.class);						
						startActivity(i);
						CustomToast.makeText(Billing.this, "Survey Completed.", Toast.LENGTH_LONG);
					}
				}
				catch(Exception e)
				{
					Log.d(TAG, e.toString());
				}
			}
		});
		btnprev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {					
				int flag=0;
				try 
				{
					//billObjprev = BillingObject.GetBillingObject();
					if(BillingObject.GetBillingObject().getmConnectionNo() == null)
					{
						CustomToast.makeText(Billing.this, "No Records.", Toast.LENGTH_SHORT);						
						return;
					}
					if(flag == 0)
					{
						f = BillingObject.GetBillingObject().getMmUID();//punit 25022014
						flag++;
					}
					f--; 					  
					mDb.GetPrev(String.valueOf(f));					
					while(f != BillingObject.GetBillingObject().getMmUID())
					{
						if(f > 0)
						{
							f--;
							mDb.GetPrev(String.valueOf(f));
						}
						else
						{
							break;
						}
					}					
					ConnIdRRNo.setText("");
					GetAssignNecessaryFields(BillingObject.GetBillingObject());				
				} 
				catch (Exception e) 
				{
					Log.d(TAG, e.toString());
					e.printStackTrace();
				}
			}
		});
		//btnpnext --> get Next Connection in then Billing Object
		btnpnext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {					
				int flag = 0;
				try
				{
					//billObjprev = BillingObject.GetBillingObject();
					if(BillingObject.GetBillingObject().getmConnectionNo() == null)
					{
						CustomToast.makeText(Billing.this, "No Records.", Toast.LENGTH_SHORT);						
						return;
					}
					if(flag == 0)
					{
						f = BillingObject.GetBillingObject().getMmUID();
						flag++;
					}
					f++; 					  
					mDb.GetPrev(String.valueOf(f));					
					int TotalConn = mDb.getCountofRecods();
					while(f != BillingObject.GetBillingObject().getMmUID())
					{
						if(f <= TotalConn)
						{
							f++;
							mDb.GetPrev(String.valueOf(f));
						}
						else
						{
							break;
						}
					}					
					ConnIdRRNo.setText("");
					GetAssignNecessaryFields(BillingObject.GetBillingObject());				
				}
				catch (Exception e) 
				{
					Log.d(TAG, e.toString());
					e.printStackTrace();
				}
			}
		});		
		btnBillingMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{	
				/*if(BillingObject.GetBillingObject().getmGps_Latitude_image().length()==0 ||BillingObject.GetBillingObject().getmGps_Latitude_image().equals(""))
				{
					return;
				}
				else
				{

				}*/
				try
				{
					if(BillingObject.GetBillingObject().getmGps_Latitude_image().trim() == null || BillingObject.GetBillingObject().getmGps_Latitude_image().trim().equals("") ||BillingObject.GetBillingObject().getmGps_Latitude_image().trim() == null || BillingObject.GetBillingObject().getmGps_Longitude_image().trim().equals(""))
					{
						CustomToast.makeText(Billing.this, "Latitude and Longitude not in File.", Toast.LENGTH_LONG);
						return;
					}
					if(LoginActivity.gpsTracker.isGPSConnected())
					{
						Intent i = new Intent(Billing.this,MapActivity.class);
						startActivity(i);
					}
					else
					{
						CustomToast.makeText(Billing.this, "GPS Disabled. Please turn on GPS.", Toast.LENGTH_LONG);
					}
				} 
				catch (Exception e) 
				{
					Log.d("", "");
				}
			}
		});
	}
	//Modified Nitish 27-02-2014
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.		
		getMenuInflater().inflate(R.menu.spotbillingmenu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return OptionsMenu.navigate(item,Billing.this);			
	}		
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		CustomToast.makeText(Billing.this, "Please use menu to navigate.", Toast.LENGTH_SHORT);
		return;
	}
	public void GetAssignNecessaryFields(ReadSlabNTarifSbmBillCollection billObj)
	{
		conid.setText(billObj.getmConnectionNo());
		txtCustomerName.setText(billObj.getmCustomerName());
		rrNo.setText(billObj.getmRRNo());		
		//tariffCode.setText((new CommonFunction()).SplitTarifString(billObj.getmTarifString()));		
		//load.setText((new CommonFunction()).GetSancLoadWithHPKW(billObj));
		try
		{
			load.setText(billObj.getmSancLoad() + " KW/HP");
			tariffCode.setText(billObj.getmBillNo());
			txtAddress.setText(billObj.getmAddress1() + " (MR  Code:" + billObj.getmAddress2().trim() + ")");
			txtMeterSlNo.setText(billObj.getmMeter_serialno().trim());
		}
		catch(Exception e)
		{

		}
		if(billObj.getmBlCnt().equals("0"))//if getmBlCnt is 0 then Connection is not Billed
		{			
			billed.setText(ConstantClass.sNB);//set text as Not Billed
			btn.setText(ConstantClass.btnOKText);//Set Text as Billing
			btn.setEnabled(true);
		}
		else if((billObj.getmBlCnt().equals("1")))//if getmBlCnt is 1 then Connection is Billed
		{
			/*if(billObj.getmBOBillFlag() == 1)
			{
				btn.setEnabled(false);
			}
			else
			{
				btn.setEnabled(true);
			}*/
			btn.setEnabled(true);
			billed.setText(ConstantClass.sBilled);//set text as Billed 
			btn.setText(ConstantClass.btnReprintText);//Set Text as Re-Print
		}
		//Show Connection is Metered or Not Metered
		txtBillingMtd.setText(billObj.getmMtd().equals("1") ? "Metered" : "Not Metered");//getmMtd is 1 then Metered
	}
	
}
