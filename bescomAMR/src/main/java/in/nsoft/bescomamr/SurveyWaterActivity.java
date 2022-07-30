
package in.nsoft.bescomamr;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyWaterActivity extends Activity implements AlertInterface {
	//Android_BescomARMSurvey   BescomLive Data base 

	TextView textConnId,textRRNo;	
	Spinner ddlDistrict,ddlTaluk,ddlGramPanchayat,ddlVillage,ddlWaterSource,ddlmetermake,ddlTank,ddlSimType;
	EditText editBorewellDepth,editSancLoad,editPumpCapacity,editPipeDimension,editNoOfOutlets,editTankCapacity,editDistance,
	editmeterslno,editControlValves,editTankDimensionLength,editTankDimensionDiameter,editWaterMan,editContactNo,editRemarks;

	RadioGroup radioGroupMeterStatus,radioGroupMeterPhase,radioGroupMeterType,radioGroupConnectionStatus,radioGroupBorewell,radioGroupPipeTypeBorewell,radioGroupTypeOfSupply,radioGroupPipeTypeOutlet;

	RadioButton radioMetered,radioUnMetered,radioSingle,radioThreeCT,radioThreeNonCT,radioDLMS,radioNonDLMS,radioActive,radioInActive,radioNotPresent,radioWorking,radioNotWorking,
	radioBorewellMetallic,radioBorewellPVC,radioBorewellCPVC,radioOverhead,radioDirect,radioOutletMetallic,radioOutletPVC,radioOutletCPVC;

	LinearLayout lytOverhead,lytMeterDetails;

	ImageView imgAdd,imgCross;
	TextView txtTankAdd;

	Button btnsurveysave,btnsurveyreset;    
	DatabaseHelper db=new DatabaseHelper(this);
	SurveyDetails sd;


	Handler dh;
	static String districtId,talukId,grmPamId,villageId,tankid,tankname = "0";

	String tId,tName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey_water);

		sd=new SurveyDetails();

		dh=new Handler();

		textConnId = (TextView)findViewById(R.id.textConnId);	
		textRRNo = (TextView)findViewById(R.id.textRRNo);
		ddlDistrict = (Spinner)findViewById(R.id.ddlDistrict);
		ddlTaluk = (Spinner)findViewById(R.id.ddlTaluk);
		ddlGramPanchayat = (Spinner)findViewById(R.id.ddlGramPanchayat);
		ddlVillage  =  (Spinner)findViewById(R.id.ddlVillage);	
		ddlWaterSource = (Spinner)findViewById(R.id.ddlWaterSource);
		ddlmetermake = (Spinner)findViewById(R.id.ddlmetermake);
		ddlTank = (Spinner)findViewById(R.id.ddlTank);
		ddlSimType = (Spinner)findViewById(R.id.ddlSimType);

		editBorewellDepth  =  (EditText)findViewById(R.id.editBorewellDepth);	
		editSancLoad  =  (EditText)findViewById(R.id.editSancLoad);	
		editPumpCapacity  =  (EditText)findViewById(R.id.editPumpCapacity);	
		editPipeDimension  =  (EditText)findViewById(R.id.editPipeDimension);	
		editNoOfOutlets  =  (EditText)findViewById(R.id.editNoOfOutlets);	
		editTankDimensionLength  =  (EditText)findViewById(R.id.editTankDimensionLength);	
		editTankDimensionDiameter = (EditText)findViewById(R.id.editTankDimensionDiameter);	
		editTankCapacity  =  (EditText)findViewById(R.id.editTankCapacity);	
		editDistance  =  (EditText)findViewById(R.id.editDistance);	
		editRemarks  =  (EditText)findViewById(R.id.editRemarks);
		editmeterslno = (EditText)findViewById(R.id.editmeterslno);
		editControlValves = (EditText)findViewById(R.id.editControlValves);
		editWaterMan = (EditText)findViewById(R.id.editWaterMan);
		editContactNo = (EditText)findViewById(R.id.editContactNo);



		radioGroupMeterStatus = (RadioGroup)findViewById(R.id.radioGroupMeterStatus);
		radioGroupMeterPhase = (RadioGroup)findViewById(R.id.radioGroupMeterPhase); 
		radioGroupMeterType = (RadioGroup)findViewById(R.id.radioGroupMeterType); 
		radioGroupConnectionStatus = (RadioGroup)findViewById(R.id.radioGroupConnectionStatus);
		radioGroupBorewell = (RadioGroup)findViewById(R.id.radioGroupBorewell);		
		radioGroupPipeTypeBorewell = (RadioGroup)findViewById(R.id.radioGroupPipeTypeBorewell);		
		radioGroupTypeOfSupply = (RadioGroup)findViewById(R.id.radioGroupTypeOfSupply);
		radioGroupPipeTypeOutlet  = (RadioGroup)findViewById(R.id.radioGroupPipeTypeOutlet);

		radioMetered = (RadioButton)findViewById(R.id.radioMetered);
		radioUnMetered = (RadioButton)findViewById(R.id.radioUnMetered);
		radioSingle = (RadioButton)findViewById(R.id.radioSingle);
		radioThreeCT = (RadioButton)findViewById(R.id.radioThreeCT);
		radioThreeNonCT = (RadioButton)findViewById(R.id.radioThreeNonCT);
		radioDLMS = (RadioButton)findViewById(R.id.radioDLMS);
		radioNonDLMS = (RadioButton)findViewById(R.id.radioNonDLMS);
		radioActive = (RadioButton)findViewById(R.id.radioActive);
		radioInActive = (RadioButton)findViewById(R.id.radioInActive);



		radioNotPresent = (RadioButton)findViewById(R.id.radioNotPresent);
		radioWorking = (RadioButton)findViewById(R.id.radioWorking);
		radioNotWorking = (RadioButton)findViewById(R.id.radioNotWorking);
		radioBorewellMetallic = (RadioButton)findViewById(R.id.radioBorewellMetallic);
		radioBorewellPVC = (RadioButton)findViewById(R.id.radioBorewellPVC);
		radioBorewellCPVC = (RadioButton)findViewById(R.id.radioBorewellCPVC);

		radioOverhead = (RadioButton)findViewById(R.id.radioOverhead);
		radioDirect = (RadioButton)findViewById(R.id.radioDirect);

		radioOutletMetallic = (RadioButton)findViewById(R.id.radioOutletMetallic);
		radioOutletPVC = (RadioButton)findViewById(R.id.radioOutletPVC);
		radioOutletCPVC = (RadioButton)findViewById(R.id.radioOutletCPVC);


		lytMeterDetails = (LinearLayout)findViewById(R.id.lytMeterDetails);
		lytOverhead = (LinearLayout)findViewById(R.id.lytOverhead);

		btnsurveysave = (Button)findViewById(R.id.btnsurveysave);		
		btnsurveyreset = (Button)findViewById(R.id.btnsurveyreset);		

		imgAdd = (ImageView)findViewById(R.id.imgAdd);		
		imgCross = (ImageView)findViewById(R.id.imgCross);		

		txtTankAdd = (TextView)findViewById(R.id.txtTankAdd);		



		districtId =talukId = grmPamId = villageId = tankid = tankname ="0";
		tId = tName = "";
		try
		{

			if(BillingObject.GetBillingObject().getmRRNo()==null || BillingObject.GetBillingObject().getmConnectionNo() == null)
			{
				CustomToast.makeText(SurveyWaterActivity.this, "Error in Data.",  Toast.LENGTH_SHORT);
				Intent iCheck = new Intent(SurveyWaterActivity.this, LoginActivity.class);
				startActivity(iCheck);
				return;					
			}
			else
			{

				textConnId.setText(ConstantClass.mSurvryName + "(Water)"); 
				textRRNo.setText("ACCNO/CONS NAME: " + BillingObject.GetBillingObject().getmRRNo());

			}
			ddlDistrict.setAdapter(db.getMasterData(1,"0"));
			ddlTaluk.setAdapter(db.getMasterData(0,"0"));
			ddlGramPanchayat.setAdapter(db.getMasterData(0,"0"));
			ddlVillage.setAdapter(db.getMasterData(0,"0"));
			ddlTank.setAdapter(db.getMasterData(0,"0"));
			ddlWaterSource.setAdapter(db.getWaterSource());
			ddlmetermake.setAdapter(db.getMeterMakeHESCOM());
			ddlSimType.setAdapter(db.getSIMType());

			radioMetered.setChecked(true);
			radioOverhead.setChecked(true);


		}
		catch(Exception e)
		{	
		}



		ddlDistrict.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try
				{

					DDLItem k = (DDLItem)arg0.getItemAtPosition(arg2);
					districtId = k.getId();

					if(!districtId.equals("0"))
					{

						ddlTaluk.setAdapter(db.getMasterData(2,districtId));
						ddlGramPanchayat.setAdapter(db.getMasterData(0,"0"));
						ddlVillage.setAdapter(db.getMasterData(0,"0"));
					}
				}
				catch(Exception e)
				{
					Log.d("", e.toString()); 
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		ddlTaluk.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try
				{

					DDLItem k = (DDLItem)arg0.getItemAtPosition(arg2);
					talukId = k.getId();
					if(!talukId.equals("0"))
					{
						ddlGramPanchayat.setAdapter(db.getMasterData(3,talukId));
						ddlVillage.setAdapter(db.getMasterData(0,"0"));
					}
				}
				catch(Exception e)
				{
					Log.d("", e.toString()); 
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		ddlGramPanchayat.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try
				{

					DDLItem k = (DDLItem)arg0.getItemAtPosition(arg2);
					grmPamId = k.getId();
					if(!grmPamId.equals("0"))
					{
						ddlVillage.setAdapter(db.getMasterData(4,grmPamId));
					}
				}
				catch(Exception e)
				{
					Log.d("", e.toString()); 
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		ddlVillage.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try
				{

					DDLItem k = (DDLItem)arg0.getItemAtPosition(arg2);
					villageId = k.getId();
					if(!villageId.equals("0"))
					{
						ddlTank.setAdapter(db.getMasterData(5,villageId));

					}
				}
				catch(Exception e)
				{
					Log.d("", e.toString()); 
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		ddlTank.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try
				{

					DDLItem k = (DDLItem)arg0.getItemAtPosition(arg2);
					tankid = k.getId();									
					tankname = k.getValue();			

				}
				catch(Exception e)
				{
					Log.d("", e.toString()); 
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		ddlmetermake.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try
				{
					DDLItem k = (DDLItem)  arg0.getItemAtPosition(arg2);
					sd.setMeterMake(k.getId().toString());
				}
				catch (Exception e) {
					e.printStackTrace(); 
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		ddlWaterSource.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try
				{
					DDLItem k = (DDLItem)  arg0.getItemAtPosition(arg2);
					sd.setWaterSource(k.getId().toString());
				}
				catch (Exception e) {
					e.printStackTrace(); 
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		ddlSimType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try
				{
					DDLItem k = (DDLItem)  arg0.getItemAtPosition(arg2);
					sd.setTypeOfSim(k.getId().toString());
				}
				catch (Exception e) {
					e.printStackTrace(); 
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		radioMetered.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lytMeterDetails.setVisibility(View.VISIBLE);

			}
		});
		radioUnMetered.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				try 
				{
					lytMeterDetails.setVisibility(View.GONE);
					ddlmetermake.setAdapter(db.getMeterMakeHESCOM());

					editmeterslno.setText("");
					radioSingle.setChecked(false);
					radioThreeCT.setChecked(false);
					radioThreeNonCT.setChecked(false);
					radioDLMS.setChecked(false);
					radioNonDLMS.setChecked(false);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		radioOverhead.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				try {
					lytOverhead.setVisibility(View.VISIBLE);
					ddlTank.setAdapter(db.getMasterData(5,villageId));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		radioDirect.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lytOverhead.setVisibility(View.GONE);
				try {
					ddlTank.setAdapter(db.getMasterData(0,"0"));
					editNoOfOutlets.setText("");
					editControlValves.setText("");
					radioOutletMetallic.setChecked(false);
					radioOutletPVC.setChecked(false);
					radioOutletCPVC.setChecked(false);

					editTankDimensionDiameter.setText("");
					editTankDimensionLength.setText("");
					editTankCapacity.setText("");
					editDistance.setText("");
					tId = tName = "0";
					tankid = tankname = "0";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});	

		imgAdd.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {					
				try {
					if(ddlTank.getSelectedItem().toString().equals("--SELECT--"))
					{
						CustomToast.makeText(SurveyWaterActivity.this, "Please Select Tank Name.", Toast.LENGTH_SHORT);
						return;
					}
					if(tId.trim().length()==0)
					{
						tId = tankid;
						tName = tankname;
					}
					else if(tId.contains(tankid))
					{	
						CustomToast.makeText(SurveyWaterActivity.this, "Already Added.", Toast.LENGTH_SHORT);
						return;
					}
					else
					{
						tId =   tId + "#" + tankid;
						tName =   tName + "#" + tankname;
					}

					txtTankAdd.setText(tName);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					CustomToast.makeText(SurveyWaterActivity.this, e.toString(), Toast.LENGTH_SHORT);
					e.printStackTrace();
				}					
			}
		});
		imgCross.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {					
				try {


					tId = "";
					tName ="";
					txtTankAdd.setText(tName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					CustomToast.makeText(SurveyWaterActivity.this, "Error in Calendar 1", Toast.LENGTH_SHORT);
					e.printStackTrace();
				}					
			}
		});

		btnsurveysave.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{

				if(String.valueOf(LoginActivity.gpsTracker.latitude).equals("0.0"))
				{
					try 
					{
						CustomToast.makeText(SurveyWaterActivity.this, "GPS Cordinates Not Obtained.",  Toast.LENGTH_SHORT);
						return;

					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				else
				{
					saveFunction();
				}
			}
		});
		btnsurveyreset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//TODO Auto-generated method stub
				sd = new SurveyDetails();
				Intent i=new Intent(SurveyWaterActivity.this,SurveyWaterActivity.class);
				startActivity(i);
				finish();
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
		return OptionsMenu.navigate(item,SurveyWaterActivity.this);		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		CustomToast.makeText(SurveyWaterActivity.this, "Please use menu to navigate.", Toast.LENGTH_SHORT);
		return;
	}

	public String ImageSaveinSDCard()
	{
		InputStream myInput = null;
		OutputStream myOutput = null;
		String ImageName = "";
		try
		{
			CommonFunction cFun = new CommonFunction();
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
			//08-07-2021
			ImageName = LoginActivity.IMEINumber + "_" +BillingObject.GetBillingObject().getmConnectionNo().trim() +"_"+ cFun.GetCurrentTime().replace(" ", "_").replace("-", "").replace(":", "")+".jpg";
			String root = Environment.getExternalStorageDirectory().getPath();//get the sd Card Path

			myInput = new FileInputStream(ConstantClass.MtrImageSavePath + "/MtrImg.jpg");
			File f = new File(root+"/AMRSurvey/"+timeStamp+"/Photos", ImageName);
			if(!new File(root+"/AMRSurvey/"+timeStamp+"/Photos").exists())
			{
				new File(root+"/AMRSurvey/"+timeStamp+"/Photos").mkdirs();
			}
			myOutput = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int length;
			while((length = myInput.read(buffer))>0)
			{
				myOutput.write(buffer, 0, length);
			}
		}
		catch (Exception e)
		{
			Log.d("pushBtn", e.toString());
			ImageName = "Exception";//Added on 09-06-2014
		}
		finally
		{
			if(myInput != null)
			{
				try 
				{
					myInput.close();
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(myOutput != null)
			{
				try 
				{
					myOutput.close();
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return ImageName;
	}



	public void saveFunction()
	{	

		//District,Taluk,GramPanchayat,Village
		if(ddlDistrict.getSelectedItemPosition()==0)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Select District.",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			sd.setDistrictId(districtId);
		}
		if(ddlTaluk.getSelectedItemPosition()==0)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Select Taluk.",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			sd.setTalukId(talukId);
		}
		if(ddlGramPanchayat.getSelectedItemPosition()==0)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Select Gram Panchayat.",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			sd.setGPId(grmPamId);
		}
		if(ddlVillage.getSelectedItemPosition()==0)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Select Village.",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			sd.setVillageId(villageId);
		}


		//Meter Status
		int GroupVisibility=radioGroupMeterStatus.getCheckedRadioButtonId();
		radioMetered=(RadioButton)findViewById(GroupVisibility);

		if(GroupVisibility == -1 )
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Select Meter Status",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			if(radioMetered.getText().equals("Metered"))
			{

				sd.setMeterStatus("1");


				//Phase
				GroupVisibility=radioGroupMeterPhase.getCheckedRadioButtonId();
				radioSingle=(RadioButton)findViewById(GroupVisibility);
				if(GroupVisibility == -1 )
				{
					CustomToast.makeText(SurveyWaterActivity.this, "Select Meter Phase",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					if(radioSingle.getText().equals("Single"))
					{
						sd.setMeterPhase("1");
					}
					else if(radioSingle.getText().equals("Three-CT"))
					{
						sd.setMeterPhase("2");
					}
					else if(radioSingle.getText().equals("Three-NonCT"))
					{
						sd.setMeterPhase("3");
					}
				}

				//Meter Make
				if(ddlmetermake.getSelectedItemPosition()==0)
				{
					CustomToast.makeText(SurveyWaterActivity.this, "Select Meter Make.",  Toast.LENGTH_SHORT);
					return;
				}

				//Meter Serial No
				if(editmeterslno.getText().toString().trim().length()==0)
				{
					CustomToast.makeText(SurveyWaterActivity.this, "Enter Meter Serial No.",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					sd.setMeterSlNo(editmeterslno.getText().toString().trim());
				}

				//Meter Type
				GroupVisibility=radioGroupMeterType.getCheckedRadioButtonId();
				radioDLMS=(RadioButton)findViewById(GroupVisibility);
				if(GroupVisibility == -1 )
				{
					CustomToast.makeText(SurveyWaterActivity.this, "Select Meter Type",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					if(radioDLMS.getText().equals("DLMS"))
					{
						sd.setMeterType("1");
					}
					else if(radioDLMS.getText().equals("NON DLMS"))
					{
						sd.setMeterType("2");
					}

				}



			}
			else if (radioMetered.getText().equals("UnMetered"))
			{

				sd.setMeterStatus("2");	

				sd.setMeterMake("0");
				sd.setMeterPhase("0");
				sd.setMeterType("0");
				sd.setMeterSlNo("0");
			}					

		}
		//Connection Status
		GroupVisibility=radioGroupConnectionStatus.getCheckedRadioButtonId();
		radioActive=(RadioButton)findViewById(GroupVisibility);

		if(GroupVisibility == -1 )
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Enter Connection Status",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			if(radioActive.getText().equals("Active"))
			{

				sd.setConnectionStatus("1");

			}
			else if (radioActive.getText().equals("InActive"))
			{

				sd.setConnectionStatus("2");		
			}	


		}
		//Water Source
		if(ddlWaterSource.getSelectedItemPosition()==0)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Select Water Source.",  Toast.LENGTH_SHORT);
			return;
		}



		//Borewell
		GroupVisibility=radioGroupBorewell.getCheckedRadioButtonId();
		radioNotPresent=(RadioButton)findViewById(GroupVisibility);

		if(GroupVisibility == -1 )
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Enter Borewell Status",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			if(radioNotPresent.getText().equals("Not Present"))
			{

				sd.setBorewell("1");

			}
			else if (radioNotPresent.getText().equals("Working"))
			{

				sd.setBorewell("2");		
			}	
			else if (radioNotPresent.getText().equals("Not Working"))
			{

				sd.setBorewell("3");		
			}

		}
		//Borewell Depth
		if(editBorewellDepth.getText().toString().trim().length()==0)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Please Enter Borewell Depth",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			sd.setDepth(editBorewellDepth.getText().toString().trim());
		}


		//Sanc Load
		if(editSancLoad.getText().toString().trim().length()==0)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Enter Sanc Load",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			sd.setSancLoad(editSancLoad.getText().toString().trim());
		}

		//Pump capacity
		if(editPumpCapacity.getText().toString().trim().length()==0)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Enter Pump capacity",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			sd.setPumpCapacity(editPumpCapacity.getText().toString().trim());
		}
		//Pipe Dimension
		if(editPipeDimension.getText().toString().trim().length()==0)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Enter Pipe Dimension",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			sd.setPipeDimension(editPipeDimension.getText().toString().trim());
		}

		//Type Of Pipe at Borewell
		GroupVisibility=radioGroupPipeTypeBorewell.getCheckedRadioButtonId();
		radioBorewellMetallic=(RadioButton)findViewById(GroupVisibility);

		if(GroupVisibility == -1 )
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Enter Pipe Type at Borewell",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			if(radioBorewellMetallic.getText().equals("Metallic"))
			{

				sd.setPipeTypeBorewell("1");

			}
			else if (radioBorewellMetallic.getText().equals("PVC"))
			{

				sd.setPipeTypeBorewell("2");		
			}	
			else if (radioBorewellMetallic.getText().equals("CPVC"))
			{

				sd.setPipeTypeBorewell("3");		
			}

		}

		//Type Of Supply
		GroupVisibility=radioGroupTypeOfSupply.getCheckedRadioButtonId();
		radioOverhead=(RadioButton)findViewById(GroupVisibility);

		if(GroupVisibility == -1 )
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Enter Supply Type",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			if(radioOverhead.getText().equals("Overhead"))
			{

				sd.setTypeOfSupply("1");

				if(tId.toString().trim().length()==0)
				{
					CustomToast.makeText(SurveyWaterActivity.this, "Select and Add Tank Names.", Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					sd.setTankId(tId);
				}

				//No Of Outlets
				if(editNoOfOutlets.getText().toString().trim().length()==0)
				{
					CustomToast.makeText(SurveyWaterActivity.this, "Enter Number of Outlets",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					sd.setNoOfOutlets(editNoOfOutlets.getText().toString().trim());
				}
				//No Of Control Valves
				if(editControlValves.getText().toString().trim().length()==0)
				{
					CustomToast.makeText(SurveyWaterActivity.this, "Enter Number of Control Valves",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					int noofoutlets = Integer.valueOf(editNoOfOutlets.getText().toString().trim());
					int noofcontrolvalves = Integer.valueOf(editControlValves.getText().toString().trim());
					if(noofcontrolvalves > noofoutlets)
					{
						CustomToast.makeText(SurveyWaterActivity.this, "Control Valves cannot be greater than number of outlets.",  Toast.LENGTH_SHORT);
						return;
					}
					sd.setControlValve(editControlValves.getText().toString().trim());
				}

				//Type Of Pipe at Outlet
				GroupVisibility=radioGroupPipeTypeOutlet.getCheckedRadioButtonId();
				radioOutletMetallic=(RadioButton)findViewById(GroupVisibility);

				if(GroupVisibility == -1 )
				{
					CustomToast.makeText(SurveyWaterActivity.this, "Enter Pipe Type at Borewell",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					if(radioOutletMetallic.getText().equals("Metallic"))
					{

						sd.setPipeTypeOutlet("1");

					}
					else if (radioOutletMetallic.getText().equals("PVC"))
					{

						sd.setPipeTypeOutlet("2");		
					}	
					else if (radioOutletMetallic.getText().equals("CPVC"))
					{

						sd.setPipeTypeOutlet("3");		
					}

				}


				//Tank Dimension Length
				if(editTankDimensionLength.getText().toString().trim().length()==0)
				{
					CustomToast.makeText(SurveyWaterActivity.this, "Enter Tank Dimension Length",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					sd.setTankDimensionsLength(editTankDimensionLength.getText().toString().trim());
				}

				//Tank Dimension Diameter
				if(editTankDimensionDiameter.getText().toString().trim().length()==0)
				{
					CustomToast.makeText(SurveyWaterActivity.this, "Enter Tank Dimension Diameter",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					sd.setTankDimensionsDiameter(editTankDimensionDiameter.getText().toString().trim());
				}
				//Tank Capacity
				if(editTankCapacity.getText().toString().trim().length()==0)
				{
					CustomToast.makeText(SurveyWaterActivity.this, "Enter Tank Capacity",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					sd.setTankCapacity(editTankCapacity.getText().toString().trim());
				}
				//Distance
				if(editDistance.getText().toString().trim().length()==0)
				{
					CustomToast.makeText(SurveyWaterActivity.this, "Enter Distance",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					sd.setDistance(editDistance.getText().toString().trim());
				}

			}
			else if (radioOverhead.getText().equals("Direct"))
			{

				sd.setTypeOfSupply("2");
				sd.setTankId("0");
				sd.setNoOfOutlets("0");
				sd.setControlValve("0");
				sd.setTankDimensionsLength("0");
				sd.setTankDimensionsDiameter("0");
				sd.setTankCapacity("0");
				sd.setDistance("0");

			}	
		}
		//Water Man
		if(editWaterMan.getText().toString().trim().length()==0)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Enter Water Man Name",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			sd.setWaterManName(editWaterMan.getText().toString().trim());
		}

		//Contact No
		if(editContactNo.getText().toString().trim().length()==0)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Enter Water Man Contact No",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			if(editContactNo.getText().toString().trim().length()!=10)
			{
				CustomToast.makeText(SurveyWaterActivity.this, "Invalid Contact Number",  Toast.LENGTH_SHORT);
				editContactNo.setText("");
				return;
			}
			else if(editContactNo.getText().toString().trim().startsWith("6")  || editContactNo.getText().toString().trim().startsWith("7") ||editContactNo.getText().toString().trim().startsWith("8") || editContactNo.getText().toString().trim().startsWith("9"))
			{
				sd.setContactNo(editContactNo.getText().toString().trim());

			}
			else
			{
				CustomToast.makeText(SurveyWaterActivity.this, "Invalid Contact Number",  Toast.LENGTH_SHORT);
				editContactNo.setText("");
				return;
			}

		}

		//Sim Type
		if(ddlSimType.getSelectedItemPosition()==0)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Select Preferred Sim.",  Toast.LENGTH_SHORT);
			return;
		}

		//sd.setRemarks(ConstantClass.mSurvryName+"|" +editRemarks.getText().toString()); //10-03-2021
		//19-07-2021
		if(editRemarks.getText().toString().trim().equals(""))
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Please Enter Remarks",  Toast.LENGTH_SHORT);
			return;
		}	
		else
		{
			sd.setRemarks(ConstantClass.mSurvryName+ " - " + ConstantClass.AppVersion +"|" + editRemarks.getText().toString());


		}	






		sd.setRRNo(BillingObject.GetBillingObject().getmRRNo());
		sd.setConnectionId(BillingObject.GetBillingObject().getmConnectionNo());
		sd.setCustomerName(BillingObject.GetBillingObject().getmCustomerName());



		if(LoginActivity.gpsTracker.canGetLocation())
		{	
			sd.setLattitude(String.valueOf(LoginActivity.gpsTracker.latitude));
			sd.setLongitude(String.valueOf(LoginActivity.gpsTracker.longitude));
		}
		try
		{
			//String imgPath = ImageSaveinSDCard();
			//sd.setImagePath(imgPath);


			/*String imagename = ImageCaptureActivity.imgPath;
			ImageCaptureActivity.imgPath = ImageCaptureActivity.ImageSaveinSDCard(imagename);
			sd.setImageName1(ImageCaptureActivity.imgPath);
			sd.setImageName2(ImageCaptureActivity.imgPath);
			sd.setImageName3(ImageCaptureActivity.imgPath);
			sd.setImageName4(ImageCaptureActivity.imgPath);*/

			sd.setImageName1(BillingObject.GetBillingObject().getImageName1());
			sd.setImageName2(BillingObject.GetBillingObject().getImageName2());
			sd.setImageName3(BillingObject.GetBillingObject().getImageName3());
			sd.setImageName4(BillingObject.GetBillingObject().getImageName4());

		}
		catch(Exception e)
		{

		}
		db.CreateIfNotExistsGPWaterSurveyTable();
		try
		{
			//sd.setTransFarmerName(txtscanresults.getText().toString());
			CustomAlert.CustomAlertParameters params = new CustomAlert.CustomAlertParameters(); 
			params.setContext(SurveyWaterActivity.this);					
			params.setMainHandler(dh);


			params.setIcon(R.drawable.surveyicon);

			params.setMsg("ACCNO/CONS NAME:"+sd.getRRNo()+
					"\n"+"District :"+ddlDistrict.getSelectedItem().toString()+		
					"\n"+"Taluk :"+ddlTaluk.getSelectedItem().toString()+	
					"\n"+"Gram Panchayat :"+ddlGramPanchayat.getSelectedItem().toString()+	
					"\n"+"Village :"+ddlVillage.getSelectedItem().toString()+
					"\n"+"Meter Status :"+radioMetered.getText().toString().trim()+	
					"\n"+"Meter Details :"+radioSingle.getText().toString().trim()+	 ", " +ddlmetermake.getSelectedItem().toString() + " , " + editmeterslno.getText().toString().trim() + " ," +radioDLMS.getText().toString().trim() + 
					"\n"+"Connection Status:"+radioActive.getText().toString().trim()+	
					"\n"+"Water Source :"+ddlWaterSource.getSelectedItem().toString()+
					"\n"+"Borewell :"+radioNotPresent.getText().toString().trim()+	
					"\n"+"Borewell Depth:"+editBorewellDepth.getText().toString().trim()+	

					"\n"+"SancLoad:"+editSancLoad.getText().toString().trim()+	
					"\n"+"Pump Capacity:"+editPumpCapacity.getText().toString().trim()+	
					"\n"+"Pipe Dimension:"+editPipeDimension.getText().toString().trim()+
					"\n"+"Type Of Pipe at Borewell:"+radioBorewellMetallic.getText().toString().trim()+
					"\n"+"Type Of Supply:"+radioOverhead.getText().toString().trim()+
					"\n"+"Tanks :"+tName.trim()+
					"\n"+"No Of Outlets:"+editNoOfOutlets.getText().toString().trim()+
					"\n"+"Control Valves:"+editControlValves.getText().toString().trim()+
					"\n"+"Type Of Pipe at Outlet:"+radioOutletMetallic.getText().toString().trim()+
					"\n"+"Tank Dimension :"+editTankDimensionLength.getText().toString().trim()+ " * " + editTankDimensionDiameter.getText().toString().trim() +
					"\n"+"Tank Capacity:"+editTankCapacity.getText().toString().trim()+
					"\n"+"Distance:"+editDistance.getText().toString().trim()+
					"\n"+"Water Man:"+editWaterMan.getText().toString().trim()+ ", " +editContactNo.getText().toString().trim() +
					"\n"+"Remarks :"+sd.getRemarks());




			params.setButtonOkText("No");
			params.setButtonCancelText("Yes");
			params.setTitle("Save");
			params.setFunctionality(2);
			CustomAlert cAlert  = new CustomAlert(params);


		}
		catch(Exception e)
		{
			CustomToast.makeText(SurveyWaterActivity.this, "Failed to Save Survey Data",  Toast.LENGTH_SHORT);
			return;
		}	

	}



	@Override
	public void performAction(boolean alertResult, int functionality) {
		// TODO Auto-generated method stub
		if(functionality==0)
		{
		}		
		else if(!alertResult && functionality==1)
		{
			saveFunction();
		}
		else if(!alertResult && functionality==2)
		{
			//17-07-2021 for Signal Strength			
			Billing.signalStrength = Billing.signalStrength + "/" + CommonFunction.getSignalStrength(SurveyWaterActivity.this);
			if(Billing.signalStrength.trim().length() > 40)
			{
				Billing.signalStrength = Billing.signalStrength.trim().substring(0, 40);
			}				
			sd.setNSP(Billing.signalStrength);
			CustomToast.makeText(SurveyWaterActivity.this, "Signal Strength : " + Billing.signalStrength ,  Toast.LENGTH_LONG);
			//End 17-07-2021

			int save = db.insertWaterSurveyDetails(sd);
			//03-03-2017
			db.UpdateSurveyBlCnt(BillingObject.GetBillingObject().getmConnectionNo());
			if(save>=1)
			{                    
				CustomToast.makeText(SurveyWaterActivity.this, "Saved Successfully",  Toast.LENGTH_SHORT);
				Intent iSave = new Intent(SurveyWaterActivity.this, LoginActivity.class);
				startActivity(iSave);
				finish();
			}
			else
			{
				CustomToast.makeText(SurveyWaterActivity.this, "Failed to Save Survey Data",  Toast.LENGTH_SHORT);
				return;
			}
		}
	}
}
