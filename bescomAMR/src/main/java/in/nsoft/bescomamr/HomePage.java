//Nitish 24-02-2014
package in.nsoft.bescomamr;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends Activity {
	private Button btnHomeBilling, btnHomeCollections, btnHomeReports, btnHomeOthers;
	//static GPSTracker gpsTracker ;
	GridView gridView;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	CustomGridViewAdapter mCustomGridViewAdapter;
	DatabaseHelper mDb = new DatabaseHelper(this);
	private TextView lblSBBlinkText,lbl1,lbl2;

	Handler mainThreadHandler;
	PackageInfo pkgInfo;
	Handler dh;
	ProgressDialog ringProgress;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_home_page);
		setContentView(R.layout.gridhome);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		dh = new Handler();
		try {
			pkgInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);

			lbl1  = (TextView) findViewById(R.id.lbl1);
			lbl2  = (TextView) findViewById(R.id.lbl2);
			lblSBBlinkText = (TextView) findViewById(R.id.lblSBBlinkText);








			//#####Live and Test  Validation ########
			if(ConstantClass.IPAddress.equals("http://123.201.131.113:8112/service.asmx") )
			{
				lblSBBlinkText.setText(ConstantClass.mTest+ ConstantClass.AppVersion);
			}
			else
			{
				lblSBBlinkText.setText(ConstantClass.mLive+ ConstantClass.AppVersion);
			}



		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		mainThreadHandler = new Handler();
		/*if(gpsTracker == null)
			gpsTracker = new GPSTracker(HomePage.this);*/
		Bitmap sync = BitmapFactory.decodeResource(this.getResources(), R.drawable.syncnew);//collection
		Bitmap billing = BitmapFactory.decodeResource(this.getResources(), R.drawable.surveyn);//billing

		Bitmap report = BitmapFactory.decodeResource(this.getResources(), R.drawable.reportnew);//report
		Bitmap others = BitmapFactory.decodeResource(this.getResources(), R.drawable.othernew);//Other
		//gridArray.add(new Item(sync, "Map"));
		gridArray.add(new Item(billing, "Survey"));
		gridArray.add(new Item(report, "Reports"));
		gridArray.add(new Item(others, "Others"));

		//#################livetest TextView  for blinking  and color#########################
		lblSBBlinkText.setTextColor(Color.parseColor("#FF0000")); //red color		lblSBBlinkText.setTextSize(14);
		lblSBBlinkText.setTextSize(14);
		if(ConstantClass.mSurveyType.equals("2")) //Water Survey
		{
			lbl1.setText(ConstantClass.mSurvryName + "(Water)");
			gridArray.add(new Item(sync, "Sync"));

		}
		else
		{
			lbl1.setText(ConstantClass.mSurvryName+ "(Electricity)");

		}


		gridView = (GridView)findViewById(R.id.grdHomePage);
		mCustomGridViewAdapter = new CustomGridViewAdapter(HomePage.this, R.layout.row_grid, gridArray);
		gridView.setAdapter(mCustomGridViewAdapter);

		try
		{

			new Thread(new Runnable() {
				@Override
				public void run() {

					while(true){
						try {

							dh.post(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub

									if(LoginActivity.gpsTracker.isGPSConnected())
									{
										if(String.valueOf(LoginActivity.gpsTracker.latitude).equals("0.0"))
										{
											lbl2.setText("GPS Details Not Obtained");
											lbl2.setTextColor(getResources().getColor(R.color.red));

										}
										else
										{
											lbl2.setText("GPS Details Obtained");
											lbl2.setTextColor(getResources().getColor(R.color.green1));
										}
									}
									else
									{
										lbl2.setText("GPS Location Disabled");
										lbl2.setTextColor(getResources().getColor(R.color.red));
									}


								}
							});
							Thread.sleep(4000);

						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				}
			}).start();
		}
		catch(Exception e)
		{
			Log.d("a","a");
		}


		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String Name = gridArray.get(arg2).Name;
				mDb.CreateIfNotExistsGPWaterSurveyTable();
				if(Name.equals("Survey"))
				{   

					if(LoginActivity.gpsTracker.isGPSConnected())
					{

						try
						{	
							//23-06-2021
							if(String.valueOf(LoginActivity.gpsTracker.latitude).equals("0.0"))
							{
								try {
									CustomToast.makeText(HomePage.this, "GPS Cordinates Not Obtained.",  Toast.LENGTH_SHORT);
									return;

								} catch (Exception e) {
									// TODO: handle exception
								}
							}
							else
							{
								Intent i = new Intent(HomePage.this, Billing.class);		
								startActivity(i);	
							}

						}
						catch(Exception e)
						{
							CustomToast.makeText(HomePage.this, "Please Load File and Proceed.", Toast.LENGTH_SHORT);
						}


					}
					else
					{
						CustomToast.makeText(HomePage.this, "GPS disabled. Check GPS settings.", Toast.LENGTH_SHORT);
					}					
				}
				else if(Name.equals("Map"))
				{					
					//23-12-2017
					//getDetails();

					/*ArrayList<String> lststr=new ArrayList<String>();
					String str="a,b,c";
					lststr.add(str);

					int val=mDb.insertSlaveMaster(lststr);
					int val1=mDb.insertDCUMaster(lststr);

					try {
						mDb.getDcuMaster();
						mDb.getslaveMaster("a");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/					
					//CustomToast.makeText(HomePage.this, "Data does not exist.", Toast.LENGTH_SHORT);
				}
				else if(Name.equals("Reports"))
				{
					try {			
						Intent i = new Intent(HomePage.this,ReportListActivity.class);
						startActivity(i);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						Toast toast = Toast.makeText(HomePage.this, "Reports Error", Toast.LENGTH_SHORT);
						toast.show();
						e.printStackTrace();
					}
				}
				else if(Name.equals("Others"))
				{
					try {			
						Intent i = new Intent(HomePage.this,OtherListActivity.class);
						startActivity(i);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						Toast toast = Toast.makeText(HomePage.this, "Others Error", Toast.LENGTH_SHORT);
						toast.show();
						e.printStackTrace();
					}	
				}

				else if(Name.equals("Sync"))
				{
					try {			
						if((new ConnectionDetector(HomePage.this)).isConnectedToInternet())
						{
							ringProgress = ProgressDialog.show(HomePage.this, "Please wait..", "Loading...",true);
							new DistrictMaster().execute();
						}
						else
						{
							Toast toast = Toast.makeText(HomePage.this, "Please Enable Data Connection.", Toast.LENGTH_SHORT);
							toast.show();
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						Toast toast = Toast.makeText(HomePage.this, "Others Error", Toast.LENGTH_SHORT);
						toast.show();
						e.printStackTrace();
					}	
				}
			}
		});
	}
	//Modified Nitish 26-02-2014
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spotbillingmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return OptionsMenu.navigate(item,HomePage.this);		
	}
	//End Nitish 26-02-2014	
	@Override
	public void onBackPressed() {		
		//super.onBackPressed();
		CustomToast.makeText(HomePage.this, "Back Button Disabled in this Screen.", Toast.LENGTH_SHORT);
		return;
	}


	////////////////District Master//////////////////
	private class DistrictMaster extends AsyncTask<Void, Void, Void> {
		//final ProgressDialog ringProgress =	ProgressDialog.show(HomePage.this, "Please wait..","Loading Data...",true);//Spinner
		String rValue = "";
		ArrayList<String> lststr;
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpClient httpclt = new DefaultHttpClient();//object for HttpClient	
				HttpPost httpPost = new HttpPost(ConstantClass.IPAddress + "/Android_GetWaterSurvey");//Live	
				List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);	
				lvp.add(new BasicNameValuePair("Flag", "1"));								
				lvp.add(new BasicNameValuePair("IMEINO", LoginActivity.IMEINumber));	
				lvp.add(new BasicNameValuePair("Id", "0"));
				lvp.add(new BasicNameValuePair("RRNO", "0"));	
				lvp.add(new BasicNameValuePair("Name", "0"));
				lvp.add(new BasicNameValuePair("DistrictId", "0"));
				lvp.add(new BasicNameValuePair("TalukId", "0"));			
				lvp.add(new BasicNameValuePair("GPId", "0"));
				lvp.add(new BasicNameValuePair("VillageId", "0"));	
				lvp.add(new BasicNameValuePair("TankId", "0"));
				lvp.add(new BasicNameValuePair("Remarks", "0"));
				lvp.add(new BasicNameValuePair("Param1", "0"));
				lvp.add(new BasicNameValuePair("Param2", "0"));	
				lvp.add(new BasicNameValuePair("Param3", "0"));
				lvp.add(new BasicNameValuePair("Param4", "0"));
				lvp.add(new BasicNameValuePair("Param5", "0"));					

				httpPost.setEntity((new UrlEncodedFormEntity(lvp)));
				httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");						

				HttpResponse res = httpclt.execute(httpPost);
				HttpEntity ent = res.getEntity();
				if (ent != null) {
					rValue = EntityUtils.toString(ent);
					ReadServerResponse rfsr = new ReadServerResponse();
					lststr =  rfsr.Readfilename(rValue);
				}
			} catch (Exception e) {
				cancel(true);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//ringProgress.setCancelable(false);
			//ringProgress.dismiss();
			try
			{			
				if(lststr.get(0).toString().contains("NACK"))
				{
					ringProgress.setCancelable(false);
					ringProgress.dismiss();
					CustomToast.makeText(HomePage.this, "Could Not Sync District.", Toast.LENGTH_SHORT);

				}
				else if(lststr.get(0).toString().contains("ACK"))
				{
					try 
					{
						int ret=  mDb.insertMasterData(lststr,1);					
						if (ret > 0)
						{
							new TalukMaster().execute();

						}
						else
						{

							CustomToast.makeText(HomePage.this, "Could Not Sync District Master.", Toast.LENGTH_SHORT);
							ringProgress.setCancelable(false);
							ringProgress.dismiss();
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						ringProgress.setCancelable(false);
						ringProgress.dismiss();
						e.printStackTrace();
					}
				}												
				else
				{
					ringProgress.setCancelable(false);
					ringProgress.dismiss();
					CustomToast.makeText(HomePage.this, "Error.", Toast.LENGTH_SHORT);

				}	
			}
			catch(Exception e)
			{
				ringProgress.setCancelable(false);
				ringProgress.dismiss();
				CustomToast.makeText(HomePage.this, "Error Occured.", Toast.LENGTH_SHORT);
			}
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			ringProgress.setCancelable(false); 
			ringProgress.dismiss();	 
			CustomToast.makeText(HomePage.this,"Error Occured/No Response from server", Toast.LENGTH_LONG);

		}
	}
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////Taluk Master////////////////////////////////////////////
	private class TalukMaster extends AsyncTask<Void, Void, Void> {
		//final ProgressDialog ringProgress =	ProgressDialog.show(HomePage.this, "Please wait..","Loading Data...",true);//Spinner
		String rValue = "";
		ArrayList<String> lststr;
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpClient httpclt = new DefaultHttpClient();//object for HttpClient	
				HttpPost httpPost = new HttpPost(ConstantClass.IPAddress + "/Android_GetWaterSurvey");//Live	
				List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);	
				lvp.add(new BasicNameValuePair("Flag", "2"));								
				lvp.add(new BasicNameValuePair("IMEINO", LoginActivity.IMEINumber));	
				lvp.add(new BasicNameValuePair("Id", "0"));
				lvp.add(new BasicNameValuePair("RRNO", "0"));	
				lvp.add(new BasicNameValuePair("Name", "0"));
				lvp.add(new BasicNameValuePair("DistrictId", "0"));
				lvp.add(new BasicNameValuePair("TalukId", "0"));			
				lvp.add(new BasicNameValuePair("GPId", "0"));
				lvp.add(new BasicNameValuePair("VillageId", "0"));	
				lvp.add(new BasicNameValuePair("TankId", "0"));
				lvp.add(new BasicNameValuePair("Remarks", "0"));
				lvp.add(new BasicNameValuePair("Param1", "0"));
				lvp.add(new BasicNameValuePair("Param2", "0"));	
				lvp.add(new BasicNameValuePair("Param3", "0"));
				lvp.add(new BasicNameValuePair("Param4", "0"));
				lvp.add(new BasicNameValuePair("Param5", "0"));	
				httpPost.setEntity((new UrlEncodedFormEntity(lvp)));
				httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");						

				HttpResponse res = httpclt.execute(httpPost);
				HttpEntity ent = res.getEntity();
				if (ent != null) {
					rValue = EntityUtils.toString(ent);
					ReadServerResponse rfsr = new ReadServerResponse();
					lststr =  rfsr.Readfilename(rValue);
				}
			} catch (Exception e) {
				cancel(true);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//ringProgress.setCancelable(false);
			//ringProgress.dismiss();
			try
			{			
				if(lststr.get(0).toString().contains("NACK"))
				{
					ringProgress.setCancelable(false);
					ringProgress.dismiss();
					CustomToast.makeText(HomePage.this, "Could Not Sync Taluk .", Toast.LENGTH_SHORT);

				}
				else if(lststr.get(0).toString().contains("ACK"))
				{
					try 
					{
						int ret=  mDb.insertMasterData(lststr,2);					
						if (ret > 0)
						{
							new PanchayatMaster().execute();

						}
						else
						{

							CustomToast.makeText(HomePage.this, "Could Not Sync Taluk Master.", Toast.LENGTH_SHORT);
							ringProgress.setCancelable(false);
							ringProgress.dismiss();
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						ringProgress.setCancelable(false);
						ringProgress.dismiss();
						e.printStackTrace();
					}
				}												
				else
				{
					ringProgress.setCancelable(false);
					ringProgress.dismiss();
					CustomToast.makeText(HomePage.this, "Error.", Toast.LENGTH_SHORT);

				}	
			}
			catch(Exception e)
			{
				ringProgress.setCancelable(false);
				ringProgress.dismiss();
				CustomToast.makeText(HomePage.this, "Error Occured.", Toast.LENGTH_SHORT);
			}
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			ringProgress.setCancelable(false); 
			ringProgress.dismiss();	 
			CustomToast.makeText(HomePage.this,"Error Occured/No Response from server", Toast.LENGTH_LONG);

		}
	}
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////GramPanchayat Master////////////////////////////////////////////
	private class PanchayatMaster extends AsyncTask<Void, Void, Void> {
		//final ProgressDialog ringProgress =	ProgressDialog.show(HomePage.this, "Please wait..","Loading Data...",true);//Spinner
		String rValue = "";
		ArrayList<String> lststr;
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpClient httpclt = new DefaultHttpClient();//object for HttpClient	
				HttpPost httpPost = new HttpPost(ConstantClass.IPAddress + "/Android_GetWaterSurvey");//Live	
				List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);	
				lvp.add(new BasicNameValuePair("Flag", "3"));								
				lvp.add(new BasicNameValuePair("IMEINO", LoginActivity.IMEINumber));	
				lvp.add(new BasicNameValuePair("Id", "0"));
				lvp.add(new BasicNameValuePair("RRNO", "0"));	
				lvp.add(new BasicNameValuePair("Name", "0"));
				lvp.add(new BasicNameValuePair("DistrictId", "0"));
				lvp.add(new BasicNameValuePair("TalukId", "0"));			
				lvp.add(new BasicNameValuePair("GPId", "0"));
				lvp.add(new BasicNameValuePair("VillageId", "0"));	
				lvp.add(new BasicNameValuePair("TankId", "0"));
				lvp.add(new BasicNameValuePair("Remarks", "0"));
				lvp.add(new BasicNameValuePair("Param1", "0"));
				lvp.add(new BasicNameValuePair("Param2", "0"));	
				lvp.add(new BasicNameValuePair("Param3", "0"));
				lvp.add(new BasicNameValuePair("Param4", "0"));
				lvp.add(new BasicNameValuePair("Param5", "0"));	
				httpPost.setEntity((new UrlEncodedFormEntity(lvp)));
				httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");						

				HttpResponse res = httpclt.execute(httpPost);
				HttpEntity ent = res.getEntity();
				if (ent != null) {
					rValue = EntityUtils.toString(ent);
					ReadServerResponse rfsr = new ReadServerResponse();
					lststr =  rfsr.Readfilename(rValue);
				}
			} catch (Exception e) {
				cancel(true);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//ringProgress.setCancelable(false);
			//ringProgress.dismiss();
			try
			{			
				if(lststr.get(0).toString().contains("NACK"))
				{
					ringProgress.setCancelable(false);
					ringProgress.dismiss();
					CustomToast.makeText(HomePage.this, "Could Not Sync Panchayat .", Toast.LENGTH_SHORT);

				}
				else if(lststr.get(0).toString().contains("ACK"))
				{
					try 
					{
						int ret=  mDb.insertMasterData(lststr,3);					
						if (ret > 0)
						{

							new VillageMaster().execute();

						}
						else
						{

							CustomToast.makeText(HomePage.this, "Could Not Sync Panchayat Master.", Toast.LENGTH_SHORT);
							ringProgress.setCancelable(false);
							ringProgress.dismiss();
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						ringProgress.setCancelable(false);
						ringProgress.dismiss();
						e.printStackTrace();
					}
				}												
				else
				{
					ringProgress.setCancelable(false);
					ringProgress.dismiss();
					CustomToast.makeText(HomePage.this, "Error.", Toast.LENGTH_SHORT);

				}	
			}
			catch(Exception e)
			{
				ringProgress.setCancelable(false);
				ringProgress.dismiss();
				CustomToast.makeText(HomePage.this, "Error Occured.", Toast.LENGTH_SHORT);
			}
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			ringProgress.setCancelable(false); 
			ringProgress.dismiss();	 
			CustomToast.makeText(HomePage.this,"Error Occured/No Response from server", Toast.LENGTH_LONG);

		}
	}
	/////////////////////////////////////////////////////////////////////////////////
	//////////////////////////Village Master/////////////////////////////////////
	private class VillageMaster extends AsyncTask<Void, Void, Void> {
		//final ProgressDialog ringProgress =	ProgressDialog.show(HomePage.this, "Please wait..","Loading Data...",true);//Spinner
		String rValue = "";
		ArrayList<String> lststr;
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpClient httpclt = new DefaultHttpClient();//object for HttpClient	
				HttpPost httpPost = new HttpPost(ConstantClass.IPAddress + "/Android_GetWaterSurvey");//Live	
				List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);	
				lvp.add(new BasicNameValuePair("Flag", "4"));								
				lvp.add(new BasicNameValuePair("IMEINO", LoginActivity.IMEINumber));	
				lvp.add(new BasicNameValuePair("Id", "0"));
				lvp.add(new BasicNameValuePair("RRNO", "0"));	
				lvp.add(new BasicNameValuePair("Name", "0"));
				lvp.add(new BasicNameValuePair("DistrictId", "0"));
				lvp.add(new BasicNameValuePair("TalukId", "0"));			
				lvp.add(new BasicNameValuePair("GPId", "0"));
				lvp.add(new BasicNameValuePair("VillageId", "0"));	
				lvp.add(new BasicNameValuePair("TankId", "0"));
				lvp.add(new BasicNameValuePair("Remarks", "0"));
				lvp.add(new BasicNameValuePair("Param1", "0"));
				lvp.add(new BasicNameValuePair("Param2", "0"));	
				lvp.add(new BasicNameValuePair("Param3", "0"));
				lvp.add(new BasicNameValuePair("Param4", "0"));
				lvp.add(new BasicNameValuePair("Param5", "0"));			

				httpPost.setEntity((new UrlEncodedFormEntity(lvp)));
				httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");						

				HttpResponse res = httpclt.execute(httpPost);
				HttpEntity ent = res.getEntity();
				if (ent != null) {
					rValue = EntityUtils.toString(ent);
					ReadServerResponse rfsr = new ReadServerResponse();
					lststr =  rfsr.Readfilename(rValue);
				}
			} catch (Exception e) {
				cancel(true);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//ringProgress.setCancelable(false);
			//ringProgress.dismiss();
			try
			{			
				if(lststr.get(0).toString().contains("NACK"))
				{
					ringProgress.setCancelable(false);
					ringProgress.dismiss();
					CustomToast.makeText(HomePage.this, "Could Not Sync Village .", Toast.LENGTH_SHORT);
					

				}
				else if(lststr.get(0).toString().contains("ACK"))
				{
					try 
					{
						int ret=  mDb.insertMasterData(lststr,4);					
						if (ret > 0)
						{

							new TankMaster().execute();

						}
						else
						{

							CustomToast.makeText(HomePage.this, "Could Not Sync Village Master.", Toast.LENGTH_SHORT);
							ringProgress.setCancelable(false);
							ringProgress.dismiss();
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						ringProgress.setCancelable(false);
						ringProgress.dismiss();
						e.printStackTrace();
					}
				}												
				else
				{
					ringProgress.setCancelable(false);
					ringProgress.dismiss();
					CustomToast.makeText(HomePage.this, "Error.", Toast.LENGTH_SHORT);

				}	
			}
			catch(Exception e)
			{
				ringProgress.setCancelable(false);
				ringProgress.dismiss();
				CustomToast.makeText(HomePage.this, "Error Occured.", Toast.LENGTH_SHORT);
			}
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			ringProgress.setCancelable(false); 
			ringProgress.dismiss();	 
			CustomToast.makeText(HomePage.this,"Error Occured/No Response from server", Toast.LENGTH_LONG);

		}
	}

	//////////////////////////Village Master/////////////////////////////////////
	private class TankMaster extends AsyncTask<Void, Void, Void> {
		//final ProgressDialog ringProgress =	ProgressDialog.show(HomePage.this, "Please wait..","Loading Data...",true);//Spinner
		String rValue = "";
		ArrayList<String> lststr;
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpClient httpclt = new DefaultHttpClient();//object for HttpClient	
				HttpPost httpPost = new HttpPost(ConstantClass.IPAddress + "/Android_GetWaterSurvey");//Live	
				List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);	
				lvp.add(new BasicNameValuePair("Flag", "5"));								
				lvp.add(new BasicNameValuePair("IMEINO", LoginActivity.IMEINumber));	
				lvp.add(new BasicNameValuePair("Id", "0"));
				lvp.add(new BasicNameValuePair("RRNO", "0"));	
				lvp.add(new BasicNameValuePair("Name", "0"));
				lvp.add(new BasicNameValuePair("DistrictId", "0"));
				lvp.add(new BasicNameValuePair("TalukId", "0"));			
				lvp.add(new BasicNameValuePair("GPId", "0"));
				lvp.add(new BasicNameValuePair("VillageId", "0"));	
				lvp.add(new BasicNameValuePair("TankId", "0"));
				lvp.add(new BasicNameValuePair("Remarks", "0"));
				lvp.add(new BasicNameValuePair("Param1", "0"));
				lvp.add(new BasicNameValuePair("Param2", "0"));	
				lvp.add(new BasicNameValuePair("Param3", "0"));
				lvp.add(new BasicNameValuePair("Param4", "0"));
				lvp.add(new BasicNameValuePair("Param5", "0"));	
				httpPost.setEntity((new UrlEncodedFormEntity(lvp)));
				httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");						

				HttpResponse res = httpclt.execute(httpPost);
				HttpEntity ent = res.getEntity();
				if (ent != null) {
					rValue = EntityUtils.toString(ent);
					ReadServerResponse rfsr = new ReadServerResponse();
					lststr =  rfsr.Readfilename(rValue);
				}
			} catch (Exception e) {
				cancel(true);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//ringProgress.setCancelable(false);
			//ringProgress.dismiss();
			try
			{			
				if(lststr.get(0).toString().contains("NACK"))
				{
					ringProgress.setCancelable(false);
					ringProgress.dismiss();
					CustomToast.makeText(HomePage.this, "Could Not Sync Tank .", Toast.LENGTH_SHORT);

				}
				else if(lststr.get(0).toString().contains("ACK"))
				{
					try 
					{
						int ret=  mDb.insertMasterData(lststr,5);					
						if (ret > 0)
						{

							CustomToast.makeText(HomePage.this, "Sync Completed.", Toast.LENGTH_SHORT);
							ringProgress.setCancelable(false);
							ringProgress.dismiss();
							//Intent i  = new Intent(SplashScreenActivity.this,HomeActivity.class);
							//startActivity(i);

						}
						else
						{

							CustomToast.makeText(HomePage.this, "Could Not Sync Tank Master.", Toast.LENGTH_SHORT);
							ringProgress.setCancelable(false);
							ringProgress.dismiss();
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						ringProgress.setCancelable(false);
						ringProgress.dismiss();
						e.printStackTrace();
					}
				}												
				else
				{
					ringProgress.setCancelable(false);
					ringProgress.dismiss();
					CustomToast.makeText(HomePage.this, "Error.", Toast.LENGTH_SHORT);

				}	
			}
			catch(Exception e)
			{
				ringProgress.setCancelable(false);
				ringProgress.dismiss();
				CustomToast.makeText(HomePage.this, "Error Occured.", Toast.LENGTH_SHORT);
			}
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			ringProgress.setCancelable(false); 
			ringProgress.dismiss();	 
			CustomToast.makeText(HomePage.this,"Error Occured/No Response from server", Toast.LENGTH_LONG);

		}
	}
}
