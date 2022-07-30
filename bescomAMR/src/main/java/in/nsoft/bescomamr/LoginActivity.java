package in.nsoft.bescomamr;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author nsoft.user
 * This activity is for login 
 */
public class LoginActivity extends Activity implements AlertInterface {

	private android.os.Handler handler;//=new android.os.Handler();
	private EditText txtUserName, txtPassword;
	private TextView lblSBBlinkText;
	private Button btnLogin,buttonsync;	
	DatabaseHelper db = new DatabaseHelper(this);
	//Tamilselvan on 28-03-2014
	private static final String TAG = LoginActivity.class.getName();
	static String IMEINumber = "";
	static String SimNumber = "";
	private boolean isInternetConnected = false;
	Thread th;
	static volatile boolean contTh = false;
	static volatile boolean GPSThread = false;
	long diff = 0;

	static  int faceRecog_Count = 0;
	//Nitish 30-06-2014
	static GPSTracker gpsTracker ;
	//Nitish 30-06-2014

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		handler = new android.os.Handler();
		//Modified Nitish 24-02-2014
		txtUserName = (EditText) findViewById(R.id.txtUserName);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		buttonsync = (Button) findViewById(R.id.buttonsync);		
		TextView lblSBBlinkText = (TextView) findViewById(R.id.lblSBBlinkText);


		buttonsync.setVisibility(View.GONE);
		if(gpsTracker == null)
			gpsTracker = new GPSTracker(LoginActivity.this);

		LoginActivity.faceRecog_Count = 0;
		if(LoginActivity.faceRecog_Count >= 5)
		{			
			Intent intentLogout = new Intent(Intent.ACTION_MAIN);			
			intentLogout.addCategory(Intent.CATEGORY_HOME);			
			intentLogout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intentLogout);			
		}
		//btnLogin.setEnabled(false);
		//#################livetest TextView  for blinking  and color#########################
		lblSBBlinkText.setTextColor(Color.parseColor("#FF0000")); //red color
		lblSBBlinkText.setTextSize(14);
		Animation anim=new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(80); //blinkig duration
		anim.setStartOffset(40);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		lblSBBlinkText.startAnimation(anim);
		//#####Live and Test  Validation ########
		if(ConstantClass.IPAddress.equals("http://123.201.131.113:8112/service.asmx") )
		{
			lblSBBlinkText.setText(ConstantClass.mTest+ ConstantClass.AppVersion);
		}
		else
		{
			lblSBBlinkText.setText(ConstantClass.mLive+ ConstantClass.AppVersion);
		}




		//###############################################################################################
		//Get Mobile Details ==============================================================

		IMEINumber = CommonFunction.getDeviceNo(this);

		//END Get Mobile Details ==========================================================
		ConnectionDetector conn = new ConnectionDetector(this);
		if(!isInternetConnected)
		{
			if(conn.isConnectedToInternet())
			{
				isInternetConnected = true;
			}
			else
			{
				isInternetConnected = false;
			}
		}
		//Tamilselvan on 28-03-2014
		//At First Time Installation 
		SharedPreferences shpre = PreferenceManager.getDefaultSharedPreferences(this);
		boolean isfirstRun = shpre.getBoolean("FirstRun", true);
		if(isfirstRun)//If it True, then first time installation of App.
		{
			InputStream myInput =null;
			try
			{
				db.VerifyUser("", "");
			}
			catch (Exception e)
			{
				Log.d("btnTest", e.toString());
			}
			try
			{
				//If first run, Push database (sqlite) to root(Android os folder) folder of spotbilling
				myInput = this.getAssets().open("Bescom_AMR.dat");					
				OutputStream myOutput = new FileOutputStream("/data/data/in.nsoft.bescomamr/databases/Bescom_AMR.dat");
				//OutputStream myOutputBackup = new FileOutputStream("/data/data/in.nsoft.bescomamr/databases/Bescom_AMR_Bak.dat");
				if(!new File("/data/data/in.nsoft.bescomamr/databases").exists())
				{
					new File("/data/data/in.nsoft.bescomamr/databases").mkdirs();
				}
				byte[] buffer = new byte[1024];
				int length;
				while((length = myInput.read(buffer))>0)
				{
					myOutput.write(buffer, 0, length);
					//myOutputBackup.write(buffer, 0, length);
				}							
				myOutput.flush();
				myOutput.close();
				//myOutputBackup.flush();
				//myOutputBackup.close();
				myInput.close();/**/
				//Toast.makeText(this, "First Run", Toast.LENGTH_SHORT).show();
				SharedPreferences.Editor edt = shpre.edit();                            
				edt.putBoolean("FirstRun", false);
				edt.commit();				


			}
			catch (Exception e)
			{
				Log.d(TAG, e.toString());
			}
			finally
			{
				if(myInput != null)
				{
					try {
						myInput.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}/**/
				}
			}
		}




		if(!contTh)
			threadforSendtoserver();
		//END THREAD for Sending Data to Server and Sending Time ==================================================================




		Intent i = new Intent(LoginActivity.this, HomePage.class);//Redirect to Home Page 
		startActivity(i);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return OptionsMenu.navigate(item, LoginActivity.this);			
	}

	//Tamilselvan on 13-03-2014
	//Code for closing app
	@Override	
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);			
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);			
		return;
	}/**/

	/**
	 * Tamilselvan on 29-03-2014
	 * verification of IMEI number and SIM serial number
	 */
	@Override
	public void performAction(boolean alertResult, int functionality) {
		// TODO Auto-generated method stub

	}
	//Nitish 04-07-2014
	/*public void GPRS_Sync()
	{
		final Context ctx = LoginActivity.this;
		try
		{	
			gpsTracker = new GPSTracker(LoginActivity.this);

			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try
					{
						GPSThread = true;
						while(true)//While Loop start==============================================
						{
							if(db.GetStatusMasterDetailsByID("7") == 1)//if not equal to 1, SBM File is not Loaded	 
							{
								if(!LoginActivity.gpsTracker.isGPSConnected() || LoginActivity.gpsTracker.latitude == 0 )  //If GPS Location not obtained
								{									
									Thread.sleep(60000);//1 min
								}
								else  //On Getting GPS Information
								{
									try
									{
										String currenthour = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime()); //Get Current Hour
										int hour = Integer.parseInt(currenthour);
										if(hour > 5 && hour < 20) //6:00 to 19:00 
										{
											String currentdate = new SimpleDateFormat("ddMMyyyyHHmm").format(Calendar.getInstance().getTime());
											GPSDetailsObject Gp = new GPSDetailsObject();
											Gp.setmIMEINo(IMEINumber);
											Gp.setmSIMNo(SimNumber);
											Gp.setmDateTime(currentdate);
											Gp.setmMRID(db.GetMRNameFromSBMBillCollData().trim());
											Gp.setmLatitude(String.valueOf(LoginActivity.gpsTracker.latitude));
											Gp.setmLongitude(String.valueOf(LoginActivity.gpsTracker.longitude));
											Gp.setmLocationCode(db.GetLocationCode().trim());
											int result = db.GPSDetailsSave(Gp);
											if(result == 1)
											{
												Thread.sleep(300000);//Send Every 5min-
											}
										}
										else if(hour > 20) //
										{
											//check any rows is there are not
											if(db.GetCountforGPSDetails() > 0)
											{
												if(db.GetCountforGPSDetails() == db.GetCountGPSDetailsSent())//If all LatLongData Sent
												{
													try
													{
														db.DropCreateGPSTable(); //Drop and Recreate GPS Table
													}
													catch(Exception e)
													{
														Log.d(TAG, e.toString());
													}				
												}
											}

										}
									}
									catch(Exception e)
									{
										Thread.sleep(60000);//1 min
										Log.d(TAG, e.toString());
									}
								}
							}
							else
							{
								Thread.sleep(60000);//1 min
							}
						}//While Loop END==========================================================================================================
						//1000 ->1s  10000->10s  60000 -> 1min   600000 ->10min
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
	//End Nitish 04-07-2014
	/**
	 * Thread for sending billed data to server
	 */
	public void threadforSendtoserver()
	{             
		final Context ctx = LoginActivity.this;
		try
		{
			if(contTh)
			{
				return;
			}
			th = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try
					{
						contTh = true;
						DatabaseHelper db = new DatabaseHelper(ctx);
						th.setName(String.valueOf(R.string.ThreadName));
						//while( new ConnectionDetector(ctx).isConnectedToInternet())
						/*if(new ConnectionDetector(ctx).isConnectedToInternet())
						{*/
						while(true)
						{                  
							if(new ConnectionDetector(ctx).isConnectedToInternet())
							{
								//Billed Connection send to server.====================================================================
								ArrayList<String> alStr = db.GetSurveyDataSendToServer();
								for(String str :alStr)
								{
									try
									{
										String BillingData = str;//get string
										HttpClient httpclt = new DefaultHttpClient();//object for HttpClient
										String ImgName = BillingData.split(",")[19];
										/*HttpParams httpParams = new BasicHttpParams();
										int timeoutConn = 3000;
										int socketTimeoutConn = 5000;
										HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConn);
										HttpConnectionParams.setSoTimeout(httpParams, socketTimeoutConn);*/

										HttpPost httpPostImage = new HttpPost(ConstantClass.IPAddress + "/Mathura_Photo_Transfer");
										List<NameValuePair> lvpImage = new ArrayList<NameValuePair>(1);
										lvpImage.add(new BasicNameValuePair("photo", CommonFunction.getPhotoStream(ImgName)));
										lvpImage.add(new BasicNameValuePair("FileName", ImgName));
										httpPostImage.setEntity((new UrlEncodedFormEntity(lvpImage)));
										httpPostImage.setHeader("Content-Type","application/x-www-form-urlencoded");	
										//httpPostImage.setParams(httpParams);
										HttpResponse resImg = httpclt.execute(httpPostImage);
										HttpEntity entImg = resImg.getEntity();
										if(entImg != null)
										{
											String rtrImgValue = EntityUtils.toString(entImg);
											//if(rtrImgValue.equals("ACK") || rtrImgValue.equals("NACK"))
											{

												//Sending billing data to server 
												HttpPost httpPost = new HttpPost(ConstantClass.IPAddress + "/Mathura_GPRS_Data_Android_Survey_General");
												List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);
												lvp.add(new BasicNameValuePair("Flag", "1"));
												lvp.add(new BasicNameValuePair("IMEINO", IMEINumber));
												lvp.add(new BasicNameValuePair("RRNo", BillingData.split(",")[0].length()==0? "0" : BillingData.split(",")[0]));
												lvp.add(new BasicNameValuePair("ConnectionId", BillingData.split(",")[1].length()==0? "0" : BillingData.split(",")[1]));
												lvp.add(new BasicNameValuePair("ConnectionType", BillingData.split(",")[2].length()==0? "0" : BillingData.split(",")[2]));
												lvp.add(new BasicNameValuePair("Phase", BillingData.split(",")[3].length()==0? "0" : BillingData.split(",")[3]));
												lvp.add(new BasicNameValuePair("MeterMake", BillingData.split(",")[4].length()==0? "0" : BillingData.split(",")[4]));
												lvp.add(new BasicNameValuePair("Model", BillingData.split(",")[5].length()==0? "0" : BillingData.split(",")[5]));
												lvp.add(new BasicNameValuePair("MeterType", BillingData.split(",")[6].length()==0? "0" : BillingData.split(",")[6]));
												lvp.add(new BasicNameValuePair("MeterBoxAvailability", BillingData.split(",")[7].length()==0? "0" : BillingData.split(",")[7]));
												lvp.add(new BasicNameValuePair("TypeOfBox", BillingData.split(",")[8].length()==0? "0" : BillingData.split(",")[8]));
												lvp.add(new BasicNameValuePair("MeterPlacement", BillingData.split(",")[9].length()==0? "0" : BillingData.split(",")[9]));
												//lvp.add(new BasicNameValuePair("HeightOfMeter", BillingData.split(",")[10].length()==0? "0" : BillingData.split(",")[10]));
												lvp.add(new BasicNameValuePair("HeightOfMeter", "0"));

												lvp.add(new BasicNameValuePair("AvailabilityOfLineOfSight", BillingData.split(",")[11].length()==0? "0" : BillingData.split(",")[11]));
												lvp.add(new BasicNameValuePair("Floor", BillingData.split(",")[12].length()==0? "0" : BillingData.split(",")[12]));
												lvp.add(new BasicNameValuePair("NoOfMetersAvailable", BillingData.split(",")[13].length()==0? "0" : BillingData.split(",")[13]));
												//lvp.add(new BasicNameValuePair("MeterDimension", BillingData.split(",")[14].length()==0? "0" : BillingData.split(",")[14]));
												lvp.add(new BasicNameValuePair("MeterDimension", "0"));


												lvp.add(new BasicNameValuePair("Remarks", BillingData.split(",")[15].length()==0? "0" : BillingData.split(",")[15]));
												lvp.add(new BasicNameValuePair("Latitude", BillingData.split(",")[16].length()==0? "0" : BillingData.split(",")[16]));
												lvp.add(new BasicNameValuePair("Longitude", BillingData.split(",")[17].length()==0? "0" : BillingData.split(",")[17]));
												//lvp.add(new BasicNameValuePair("SlaveId", BillingData.split(",")[20].length()==0? "0" : BillingData.split(",")[20]));
												lvp.add(new BasicNameValuePair("SlaveId", "0"));
												lvp.add(new BasicNameValuePair("ComRJ11", BillingData.split(",")[21].length()==0? "0" : BillingData.split(",")[21]));
												lvp.add(new BasicNameValuePair("Optical", BillingData.split(",")[22].length()==0? "0" : BillingData.split(",")[22]));
												lvp.add(new BasicNameValuePair("Protocol", BillingData.split(",")[23].length()==0? "0" : BillingData.split(",")[23]));
												lvp.add(new BasicNameValuePair("OpticalReading", BillingData.split(",")[24].length()==0? "0" : BillingData.split(",")[24]));

												lvp.add(new BasicNameValuePair("Transformer", BillingData.split(",")[25].length()==0? "0" : BillingData.split(",")[25]));
												lvp.add(new BasicNameValuePair("YearOfManufacture", BillingData.split(",")[26].length()==0? "0" : BillingData.split(",")[26]));
												lvp.add(new BasicNameValuePair("MeterSlNo", BillingData.split(",")[27].length()==0? "0" : BillingData.split(",")[27]));




												httpPost.setEntity((new UrlEncodedFormEntity(lvp)));
												httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");							
												HttpResponse res = httpclt.execute(httpPost);
												HttpEntity ent = res.getEntity();
												if(ent != null)
												{
													String rValue = EntityUtils.toString(ent);
													Log.d(TAG, rValue);
													if(rValue.equals("ACK"))
													{
														db.UpdateStatusSurveyByID(BillingData.split(",")[1], "1",BillingData.split(",")[0]);

													}
													else
													{
														try
														{

														}
														catch(Exception e)
														{
															Log.d(TAG, e.toString());
														}
													}
													//}//End of Sending billing data to server 
													//}
												}
												//End Sending MtrPhoto to server

											}
										}
									}
									catch(Exception e)
									{
										Log.d(TAG, e.toString());
									}
								}//END Billed Connection send to server.================================================================

								//Water Survey send to server.====================================================================
								/*ArrayList<SurveyDetails> alStrWater = db.GetWaterSurveyDataSendToServer();
								for(SurveyDetails sd :alStrWater)
								{
									try
									{

										HttpClient httpclt = new DefaultHttpClient();//object for HttpClient
										String ImgName = sd.getImagePath();


										HttpPost httpPostImage = new HttpPost(ConstantClass.IPAddress + "/Mathura_Photo_Transfer");
										List<NameValuePair> lvpImage = new ArrayList<NameValuePair>(1);
										lvpImage.add(new BasicNameValuePair("photo", CommonFunction.getPhotoStream(ImgName)));
										lvpImage.add(new BasicNameValuePair("FileName", ImgName));
										httpPostImage.setEntity((new UrlEncodedFormEntity(lvpImage)));
										httpPostImage.setHeader("Content-Type","application/x-www-form-urlencoded");	
										//httpPostImage.setParams(httpParams);
										HttpResponse resImg = httpclt.execute(httpPostImage);
										HttpEntity entImg = resImg.getEntity();
										if(entImg != null)
										{
											String rtrImgValue = EntityUtils.toString(entImg);
											//if(rtrImgValue.equals("ACK") || rtrImgValue.equals("NACK"))
											{

												//Sending billing data to server 
												HttpPost httpPost = new HttpPost(ConstantClass.IPAddress + "/Android_insertWaterSurvey");
												List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);
												lvp.add(new BasicNameValuePair("Flag", "1"));
												lvp.add(new BasicNameValuePair("IMEINO", IMEINumber));
												lvp.add(new BasicNameValuePair("RRNo", sd.getRRNo().trim().length()==0? "0" : sd.getRRNo().trim()));
												lvp.add(new BasicNameValuePair("ConnectionId", sd.getConnectionId().trim().length()==0? "0" : sd.getConnectionId().trim()));
												lvp.add(new BasicNameValuePair("DistrictId", sd.getDistrictId().trim().length()==0? "0" : sd.getDistrictId().trim()));
												lvp.add(new BasicNameValuePair("TalukId", sd.getTalukId().trim().length()==0? "0" : sd.getTalukId().trim()));
												lvp.add(new BasicNameValuePair("GPId", sd.getGPId().trim().length()==0? "0" : sd.getGPId().trim()));
												lvp.add(new BasicNameValuePair("VillageId", sd.getVillageId().trim().length()==0? "0" : sd.getVillageId().trim()));
												lvp.add(new BasicNameValuePair("TankId", "0"));
												lvp.add(new BasicNameValuePair("WaterSource", sd.getWaterSource().trim().length()==0? "0" : sd.getWaterSource().trim()));
												lvp.add(new BasicNameValuePair("MeterStatus", sd.getMeterStatus().trim().length()==0? "0" : sd.getMeterStatus().trim()));
												lvp.add(new BasicNameValuePair("MeterSlNo", sd.getMeterSlNo().trim().length()==0? "0" : sd.getMeterSlNo().trim()));
												lvp.add(new BasicNameValuePair("MeterMake", sd.getMeterMake().trim().length()==0? "0" : sd.getMeterMake().trim()));
												lvp.add(new BasicNameValuePair("MeterPhase", sd.getMeterPhase().trim().length()==0? "0" : sd.getMeterPhase().trim()));
												lvp.add(new BasicNameValuePair("MeterType", sd.getMeterType().trim().length()==0? "0" : sd.getMeterType().trim()));
												lvp.add(new BasicNameValuePair("Borewell", sd.getBorewell().trim().length()==0? "0" : sd.getBorewell().trim()));
												lvp.add(new BasicNameValuePair("ConnectionStatus", sd.getConnectionStatus().trim().length()==0? "0" : sd.getConnectionStatus().trim()));
												lvp.add(new BasicNameValuePair("SancLoad", sd.getSancLoad().trim().length()==0? "0" : sd.getSancLoad().trim()));
												lvp.add(new BasicNameValuePair("PumpCapacity", sd.getPumpCapacity().trim().length()==0? "0" : sd.getPumpCapacity().trim()));
												lvp.add(new BasicNameValuePair("Depth", sd.getDepth().trim().length()==0? "0" : sd.getDepth().trim()));
												lvp.add(new BasicNameValuePair("PipeDimension", sd.getPipeDimension().trim().length()==0? "0" : sd.getPipeDimension().trim()));
												lvp.add(new BasicNameValuePair("TypeOfSupply", sd.getTypeOfSupply().trim().length()==0? "0" : sd.getTypeOfSupply().trim()));
												lvp.add(new BasicNameValuePair("NoOfOutlets", sd.getNoOfOutlets().trim().length()==0? "0" : sd.getNoOfOutlets().trim()));
												lvp.add(new BasicNameValuePair("ControlValve", sd.getControlValve().trim().length()==0? "0" : sd.getControlValve().trim()));
												lvp.add(new BasicNameValuePair("TankDimensionLength", sd.getTankDimensionsLength().trim().length()==0? "0" : sd.getTankDimensionsLength().trim()));
												lvp.add(new BasicNameValuePair("TankDimensionDiameter", sd.getTankDimensionsDiameter().trim().length()==0? "0" : sd.getTankDimensionsDiameter().trim()));
												lvp.add(new BasicNameValuePair("TankCapacity", sd.getTankCapacity().trim().length()==0? "0" : sd.getTankCapacity().trim()));
												lvp.add(new BasicNameValuePair("Distance", sd.getDistance().trim().length()==0? "0" : sd.getDistance().trim()));
												lvp.add(new BasicNameValuePair("TypeOfPipeBorewell", sd.getPipeTypeBorewell().trim().length()==0? "0" : sd.getPipeTypeBorewell().trim()));
												lvp.add(new BasicNameValuePair("TypeOfPipeOutlet", sd.getPipeTypeOutlet().trim().length()==0? "0" : sd.getPipeTypeOutlet().trim()));
												lvp.add(new BasicNameValuePair("PreferredSIM", sd.getTypeOfSim().trim().length()==0? "0" : sd.getTypeOfSim().trim()));
												lvp.add(new BasicNameValuePair("NSP", sd.getNSP().trim().length()==0? "0" : sd.getNSP().trim()));
												lvp.add(new BasicNameValuePair("WaterManName", sd.getWaterManName().trim().length()==0? "0" : sd.getWaterManName().trim()));
												lvp.add(new BasicNameValuePair("ContactNo", sd.getContactNo().trim().length()==0? "0" : sd.getContactNo().trim()));
												lvp.add(new BasicNameValuePair("Remarks", sd.getRemarks().trim().length()==0? "0" : sd.getRemarks().trim()));
												lvp.add(new BasicNameValuePair("Latitude", sd.getLattitude().trim().length()==0? "0" : sd.getLattitude().trim()));
												lvp.add(new BasicNameValuePair("Longitude", sd.getLongitude().trim().length()==0? "0" : sd.getLongitude().trim()));
												lvp.add(new BasicNameValuePair("Param1", sd.getTankId().trim().length()==0? "0" : sd.getTankId().trim()));
												lvp.add(new BasicNameValuePair("Param2", sd.getParam2().trim().length()==0? "0" : sd.getParam2().trim()));
												lvp.add(new BasicNameValuePair("Param3", sd.getParam3().trim().length()==0? "0" : sd.getParam3().trim()));
												lvp.add(new BasicNameValuePair("Param4", sd.getParam4().trim().length()==0? "0" : sd.getParam4().trim()));
												lvp.add(new BasicNameValuePair("Param5", sd.getParam5().trim().length()==0? "0" : sd.getParam5().trim()));

												httpPost.setEntity((new UrlEncodedFormEntity(lvp)));
												httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");							
												HttpResponse res = httpclt.execute(httpPost);
												HttpEntity ent = res.getEntity();
												if(ent != null)
												{
													String rValue = EntityUtils.toString(ent);
													ReadServerResponse rfsr = new ReadServerResponse();
													ArrayList<String> lststr =  rfsr.Readfilename(rValue);
													Log.d(TAG, rValue);

													if(lststr.get(0).toString().contains("NACK"))
													{
														db.UpdateStatusWaterSurveyByID(sd.getConnectionId(), "0",sd.getRRNo());

													}
													else if(lststr.get(0).toString().contains("ACK"))
													{
														db.UpdateStatusWaterSurveyByID(sd.getConnectionId(), "1",sd.getRRNo());
													}
													//}//End of Sending billing data to server 
													//}
												}
												//End Sending MtrPhoto to server

											}
										}
									}
									catch(Exception e)
									{
										Log.d(TAG, e.toString());
									}
								}//End Water Survey send to server.====================================================================
								 */
								//Water Survey 4 Images with Data
								ArrayList<SurveyDetails> alStrWater = db.GetWaterSurveyDataSendToServer();
								for(SurveyDetails sd :alStrWater)
								{
									try
									{

										HttpClient httpclt = new DefaultHttpClient();//object for HttpClient
										
										//Sending billing data to server 
										HttpPost httpPost = new HttpPost(ConstantClass.IPAddress + "/Android_insertWaterSurvey");
										List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);
										lvp.add(new BasicNameValuePair("Flag", "1"));
										lvp.add(new BasicNameValuePair("IMEINO", IMEINumber));
										lvp.add(new BasicNameValuePair("RRNo", sd.getRRNo().trim().length()==0? "0" : sd.getRRNo().trim()));
										lvp.add(new BasicNameValuePair("ConnectionId", sd.getConnectionId().trim().length()==0? "0" : sd.getConnectionId().trim()));
										lvp.add(new BasicNameValuePair("DistrictId", sd.getDistrictId().trim().length()==0? "0" : sd.getDistrictId().trim()));
										lvp.add(new BasicNameValuePair("TalukId", sd.getTalukId().trim().length()==0? "0" : sd.getTalukId().trim()));
										lvp.add(new BasicNameValuePair("GPId", sd.getGPId().trim().length()==0? "0" : sd.getGPId().trim()));
										lvp.add(new BasicNameValuePair("VillageId", sd.getVillageId().trim().length()==0? "0" : sd.getVillageId().trim()));
										lvp.add(new BasicNameValuePair("TankId", "0"));
										lvp.add(new BasicNameValuePair("WaterSource", sd.getWaterSource().trim().length()==0? "0" : sd.getWaterSource().trim()));
										lvp.add(new BasicNameValuePair("MeterStatus", sd.getMeterStatus().trim().length()==0? "0" : sd.getMeterStatus().trim()));
										lvp.add(new BasicNameValuePair("MeterSlNo", sd.getMeterSlNo().trim().length()==0? "0" : sd.getMeterSlNo().trim()));
										lvp.add(new BasicNameValuePair("MeterMake", sd.getMeterMake().trim().length()==0? "0" : sd.getMeterMake().trim()));
										lvp.add(new BasicNameValuePair("MeterPhase", sd.getMeterPhase().trim().length()==0? "0" : sd.getMeterPhase().trim()));
										lvp.add(new BasicNameValuePair("MeterType", sd.getMeterType().trim().length()==0? "0" : sd.getMeterType().trim()));
										lvp.add(new BasicNameValuePair("Borewell", sd.getBorewell().trim().length()==0? "0" : sd.getBorewell().trim()));
										lvp.add(new BasicNameValuePair("ConnectionStatus", sd.getConnectionStatus().trim().length()==0? "0" : sd.getConnectionStatus().trim()));
										lvp.add(new BasicNameValuePair("SancLoad", sd.getSancLoad().trim().length()==0? "0" : sd.getSancLoad().trim()));
										lvp.add(new BasicNameValuePair("PumpCapacity", sd.getPumpCapacity().trim().length()==0? "0" : sd.getPumpCapacity().trim()));
										lvp.add(new BasicNameValuePair("Depth", sd.getDepth().trim().length()==0? "0" : sd.getDepth().trim()));
										lvp.add(new BasicNameValuePair("PipeDimension", sd.getPipeDimension().trim().length()==0? "0" : sd.getPipeDimension().trim()));
										lvp.add(new BasicNameValuePair("TypeOfSupply", sd.getTypeOfSupply().trim().length()==0? "0" : sd.getTypeOfSupply().trim()));
										lvp.add(new BasicNameValuePair("NoOfOutlets", sd.getNoOfOutlets().trim().length()==0? "0" : sd.getNoOfOutlets().trim()));
										lvp.add(new BasicNameValuePair("ControlValve", sd.getControlValve().trim().length()==0? "0" : sd.getControlValve().trim()));
										lvp.add(new BasicNameValuePair("TankDimensionLength", sd.getTankDimensionsLength().trim().length()==0? "0" : sd.getTankDimensionsLength().trim()));
										lvp.add(new BasicNameValuePair("TankDimensionDiameter", sd.getTankDimensionsDiameter().trim().length()==0? "0" : sd.getTankDimensionsDiameter().trim()));
										lvp.add(new BasicNameValuePair("TankCapacity", sd.getTankCapacity().trim().length()==0? "0" : sd.getTankCapacity().trim()));
										lvp.add(new BasicNameValuePair("Distance", sd.getDistance().trim().length()==0? "0" : sd.getDistance().trim()));
										lvp.add(new BasicNameValuePair("TypeOfPipeBorewell", sd.getPipeTypeBorewell().trim().length()==0? "0" : sd.getPipeTypeBorewell().trim()));
										lvp.add(new BasicNameValuePair("TypeOfPipeOutlet", sd.getPipeTypeOutlet().trim().length()==0? "0" : sd.getPipeTypeOutlet().trim()));
										lvp.add(new BasicNameValuePair("PreferredSIM", sd.getTypeOfSim().trim().length()==0? "0" : sd.getTypeOfSim().trim()));
										lvp.add(new BasicNameValuePair("NSP", sd.getNSP().trim().length()==0? "0" : sd.getNSP().trim()));
										lvp.add(new BasicNameValuePair("WaterManName", sd.getWaterManName().trim().length()==0? "0" : sd.getWaterManName().trim()));
										lvp.add(new BasicNameValuePair("ContactNo", sd.getContactNo().trim().length()==0? "0" : sd.getContactNo().trim()));
										lvp.add(new BasicNameValuePair("Remarks", sd.getRemarks().trim().length()==0? "0" : sd.getRemarks().trim()));
										lvp.add(new BasicNameValuePair("Latitude", sd.getLattitude().trim().length()==0? "0" : sd.getLattitude().trim()));
										lvp.add(new BasicNameValuePair("Longitude", sd.getLongitude().trim().length()==0? "0" : sd.getLongitude().trim()));
										lvp.add(new BasicNameValuePair("Param1", sd.getTankId().trim().length()==0? "0" : sd.getTankId().trim()));
										lvp.add(new BasicNameValuePair("Param2", sd.getParam2().trim().length()==0? "0" : sd.getParam2().trim()));
										lvp.add(new BasicNameValuePair("Param3", sd.getParam3().trim().length()==0? "0" : sd.getParam3().trim()));
										lvp.add(new BasicNameValuePair("Param4", sd.getParam4().trim().length()==0? "0" : sd.getParam4().trim()));
										lvp.add(new BasicNameValuePair("Param5", sd.getParam5().trim().length()==0? "0" : sd.getParam5().trim()));

										httpPost.setEntity((new UrlEncodedFormEntity(lvp)));
										httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");							
										HttpResponse res = httpclt.execute(httpPost);
										HttpEntity ent = res.getEntity();
										if(ent != null)
										{
											String rValue = EntityUtils.toString(ent);
											ReadServerResponse rfsr = new ReadServerResponse();
											ArrayList<String> lststr =  rfsr.Readfilename(rValue);
											Log.d(TAG, rValue);

											if(lststr.get(0).toString().contains("NACK"))
											{
												db.UpdateStatusWaterSurveyByID(sd.getConnectionId(), "0",sd.getRRNo());
												
												
											}
											else if(lststr.get(0).toString().contains("ACK"))
											{
												db.UpdateStatusWaterSurveyByID(sd.getConnectionId(), "1",sd.getRRNo());
												
												
												//Send Image Data
												String ImgName1 = sd.getImageName1();
												String ImgName2 = sd.getImageName2();
												String ImgName3 = sd.getImageName3();
												String ImgName4 = sd.getImageName4();

												HttpPost httpPostImage1 = new HttpPost(ConstantClass.IPAddress + "/Android_Photo_Transfer_WaterSurvey");
												List<NameValuePair> lvpImage1 = new ArrayList<NameValuePair>(1);
												lvpImage1.add(new BasicNameValuePair("photo", CommonFunction.getPhotoStream(ImgName1)));
												lvpImage1.add(new BasicNameValuePair("FileName", ImgName1));
												lvpImage1.add(new BasicNameValuePair("Id", sd.getRRNo()));
												lvpImage1.add(new BasicNameValuePair("IMEINO", LoginActivity.IMEINumber));
												httpPostImage1.setEntity((new UrlEncodedFormEntity(lvpImage1)));
												httpPostImage1.setHeader("Content-Type","application/x-www-form-urlencoded");	
												//httpPostImage.setParams(httpParams);
												HttpResponse resImg1 = httpclt.execute(httpPostImage1);
												HttpEntity entImg1 = resImg1.getEntity();


												HttpPost httpPostImage2 = new HttpPost(ConstantClass.IPAddress + "/Android_Photo_Transfer_WaterSurvey");
												List<NameValuePair> lvpImage2 = new ArrayList<NameValuePair>(1);
												lvpImage2.add(new BasicNameValuePair("photo", CommonFunction.getPhotoStream(ImgName2)));
												lvpImage2.add(new BasicNameValuePair("FileName", ImgName2));
												lvpImage2.add(new BasicNameValuePair("Id", sd.getRRNo()));
												lvpImage2.add(new BasicNameValuePair("IMEINO", LoginActivity.IMEINumber));
												httpPostImage2.setEntity((new UrlEncodedFormEntity(lvpImage2)));
												httpPostImage2.setHeader("Content-Type","application/x-www-form-urlencoded");	
												//httpPostImage.setParams(httpParams);
												HttpResponse resImg2 = httpclt.execute(httpPostImage2);
												HttpEntity entImg2 = resImg2.getEntity();


												HttpPost httpPostImage3 = new HttpPost(ConstantClass.IPAddress + "/Android_Photo_Transfer_WaterSurvey");
												List<NameValuePair> lvpImage3 = new ArrayList<NameValuePair>(1);
												lvpImage3.add(new BasicNameValuePair("photo", CommonFunction.getPhotoStream(ImgName3)));
												lvpImage3.add(new BasicNameValuePair("FileName", ImgName3));
												lvpImage3.add(new BasicNameValuePair("Id", sd.getRRNo()));
												lvpImage3.add(new BasicNameValuePair("IMEINO", LoginActivity.IMEINumber));
												httpPostImage3.setEntity((new UrlEncodedFormEntity(lvpImage3)));
												httpPostImage3.setHeader("Content-Type","application/x-www-form-urlencoded");	
												//httpPostImage.setParams(httpParams);
												HttpResponse resImg3 = httpclt.execute(httpPostImage3);
												HttpEntity entImg3 = resImg3.getEntity();

												HttpPost httpPostImage4 = new HttpPost(ConstantClass.IPAddress + "/Android_Photo_Transfer_WaterSurvey");
												List<NameValuePair> lvpImage4 = new ArrayList<NameValuePair>(1);
												lvpImage4.add(new BasicNameValuePair("photo", CommonFunction.getPhotoStream(ImgName4)));
												lvpImage4.add(new BasicNameValuePair("FileName", ImgName4));
												lvpImage4.add(new BasicNameValuePair("Id", sd.getRRNo()));
												lvpImage4.add(new BasicNameValuePair("IMEINO", LoginActivity.IMEINumber));
												httpPostImage4.setEntity((new UrlEncodedFormEntity(lvpImage4)));
												httpPostImage4.setHeader("Content-Type","application/x-www-form-urlencoded");	
												//httpPostImage.setParams(httpParams);
												HttpResponse resImg4 = httpclt.execute(httpPostImage4);
												HttpEntity entImg4 = resImg4.getEntity();
											
											}
											
										}
										//End Sending MtrData to server
										
										
										



										


									}
									catch(Exception e)
									{
										Log.d(TAG, e.toString());
									}
								}
								//End Water Survey 4 Images with Data.====================================================================



								//Collection Data send to Server========================================================================
								/*ArrayList<String> alStrColl = db.GetDataSendToServerColl();
								for(String str :alStrColl) 
								{
									try
									{
										String CollData = str;//get string
										HttpClient httpclt = new DefaultHttpClient();//object for HttpClient								
										HttpPost httpPost = new HttpPost(ConstantClass.IPAddress + "/Hescom_GPRS_ReceiptData_Android");//Testing
										List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);
										lvp.add(new BasicNameValuePair("ReceiptData", CollData));
										httpPost.setEntity((new UrlEncodedFormEntity(lvp)));
										httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");							
										HttpResponse res = httpclt.execute(httpPost);
										HttpEntity ent = res.getEntity();
										if(ent != null)
										{
											//db.UpdateRcptCnt(CollData.substring(1, 8)+"   ",CollData.substring(33,38));
											String rValue = EntityUtils.toString(ent);
											Log.d(TAG, rValue);
											if(rValue.equals("ACK"))
											{
												//Nitish 08-05-2014
												CommonFunction cf = new CommonFunction();
												//Add Spaces towards right to ConnectionNo so that its length is 10
												String updateConnId = cf.addSpaceRight(CollData.substring(1, 8).trim() ,10 - CollData.substring(1, 8).trim().length() , ' ');
												String value = db.UpdateRcptCnt(updateConnId ,CollData.substring(40, 45));// ConnectionNo and ReceiptNo are parameters												
												String[] splitvalue = value.split("/");
												if(!splitvalue[0].equals(splitvalue[1])) //If GPRS not sent
												{
													db.UpdateStatusMasterDetailsByID("13", "1", value);
												}
												else if(splitvalue[0].equals(splitvalue[1])) //If all Collection GPRS sent
												{
													db.UpdateStatusMasterDetailsByID("13", "0", value);
												}
											}
											else
											{
												try
												{
													CommonFunction cf = new CommonFunction();
													//Add Spaces towards right to ConnectionNo so that its length is 10
													String updateConnId = cf.addSpaceRight(CollData.substring(1, 8).trim() ,10 - CollData.substring(1, 8).trim().length() , ' ');
													db.UpdateGPRSStausColl(updateConnId,CollData.substring(40, 45), rValue); // ConnectionNo,ReceiptNo,Status as parameters
												}
												catch(Exception e)
												{
													Log.d(TAG, e.toString());
												}
											}

										}
									}									
									catch(Exception e)
									{
										Log.d(TAG, e.toString());
									}
								}*///END Collection Data send to Server====================================================================


								/*ArrayList<String> alStrGPS = db.GetGPSDataSendToServer();
								for(String str : alStrGPS)
								{												
									try
									{
										String GPSData = str;//get string
										HttpClient httpclt = new DefaultHttpClient();//object for HttpClient								
										HttpPost httpPost = new HttpPost(ConstantClass.IPAddress +"/Identify_LatLong");//Testing
										List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);
										lvp.add(new BasicNameValuePair("LatLongData", GPSData));
										httpPost.setEntity((new UrlEncodedFormEntity(lvp)));
										httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");							
										HttpResponse res = httpclt.execute(httpPost);
										HttpEntity ent = res.getEntity();
										if(ent != null)
										{
											//db.UpdateRcptCnt(CollData.substring(1, 8)+"   ",CollData.substring(33,38));
											String rValue = EntityUtils.toString(ent);
											Log.d(TAG, rValue);
											if(rValue.equals("ACK"))
											{															
												db.UpdateGPSDetails(GPSData.substring(35, 47));//Send DateTime as parameter
											}
										}
									}
									catch(Exception e)
									{
										Log.d(TAG, e.toString());
									}

									//db.UpdateGPSDetails(str.substring(35, 49));//Send DateTime as parameter
								}*/
								//FaceDetection Data Send To Server
								/*ArrayList<String> alStrFac = db.GetDataSendToServerFaceRecognition();
								for(String str :alStrFac) 
								{
									try
									{
										String FaceData[] = str.split("##");//get string
										//FaceData[0] = Id,FaceData[1] = CreatedDate,FaceData[2] = PhotoName
										HttpClient httpclt = new DefaultHttpClient();//object for HttpClient	
										HttpPost httpPostImage = new HttpPost(ConstantClass.IPAddress + "/Hescom_Photo_Transfer");
										List<NameValuePair> lvpImage = new ArrayList<NameValuePair>(1);
										lvpImage.add(new BasicNameValuePair("photo", CommonFunction.getPhotoStreamFaceRecognition(FaceData[1].toString(),FaceData[2].toString()))); //CreatedDate and PhotoName
										lvpImage.add(new BasicNameValuePair("FileName", FaceData[2].toString())); //Photoname

										httpPostImage.setEntity((new UrlEncodedFormEntity(lvpImage)));
										httpPostImage.setHeader("Content-Type","application/x-www-form-urlencoded");	
										//httpPostImage.setParams(httpParams);
										HttpResponse resImg = httpclt.execute(httpPostImage);
										HttpEntity entImg = resImg.getEntity();
										if(entImg != null)
										{
											String rtrImgValue = EntityUtils.toString(entImg);
											if(rtrImgValue.equals("ACK"))
											{
												db.UpdateGPRSFlagFaceRecognition(FaceData[0].toString());//Send id as parameter
											}

										}
									}

									catch(Exception e)
									{
										Log.d(TAG, e.toString());
									}
								}*/
								//END FaceDetection Data send to Server====================================================================
								//start EventLog Data send to server 
								/*ArrayList<String> alStrColeventlog=db.getEventLogstatus();
								for(String str :alStrColeventlog)
								{									
									try
									{
										String sData[] = str.split("##");//get string
										HttpClient httpclt = new DefaultHttpClient();//object for HttpClient								
										HttpPost httpPost = new HttpPost(ConstantClass.IPAddress + "/saveEventLog_Android");//Testing
										List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);
										lvp.add(new BasicNameValuePair("Flag","1"));
										lvp.add(new BasicNameValuePair("IMEINO", sData[1].toString()));
										lvp.add(new BasicNameValuePair("SIMNO", sData[2].toString()));
										lvp.add(new BasicNameValuePair("EventDesc",sData[3].toString()));
										lvp.add(new BasicNameValuePair("EventID", sData[4].toString()));
										lvp.add(new BasicNameValuePair("CapturedDateTime",sData[5].toString()));

										httpPost.setEntity((new UrlEncodedFormEntity(lvp)));
										httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");							
										HttpResponse res = httpclt.execute(httpPost);
										HttpEntity ent = res.getEntity();
										if(ent != null)
										{
											//db.UpdateRcptCnt(CollData.substring(1, 8)+"   ",CollData.substring(33,38));
											String rValue = EntityUtils.toString(ent);
											ReadFileServerResponse rd=new ReadFileServerResponse();
											//rd.ReadString(rValue);
											Log.d(TAG, rValue);
											if(rd.ReadString(rValue).equals("ACK"))
											{
												db.UpdateEventLogStatus(sData[0].toString());
											}
											else
											{

											}
										}
									}									
									catch(Exception e)
									{
										Log.d(TAG, e.toString());
									}
								}*/ 
								Thread.sleep(30000);
							}
							else
							{
								Thread.sleep(30000);
							}
						}
						/*else
						{
							Thread.sleep(30000);
						}*/

					}
					catch(Exception e)
					{
						Log.d(TAG, e.toString());
					}
				}
			});
			th.setPriority(Thread.NORM_PRIORITY);
			th.start();

		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
	}



}
