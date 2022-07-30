
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyDetailsActivity extends Activity implements AlertInterface {
	//Android_BescomARMSurvey   BescomLive Data base 
	private RadioGroup radioGroupPhase,radioGrouptyped,radioGroupmMeterbox,radioGroupmnmeteredtype,radioGroupMeterPlacement,radioGroupVisibility,radioGrouptypeofbox;
	RadioButton radiosinglePhase,radiothreephase,radiometered,radiounmetered,radiometerboxYes,radiometerboxNo,radiooElectrostatic;
	RadioButton radioButtonMetalic,radioButtonFibre,radioButtoninside,radioButtonoutside,radioButtonVisibilityYes,radioButtonVisibilityNo;
	Spinner ddlspinnermetermake,spinnerfloor,ddlTransformer;
	EditText editheightl,editmeterdetails,editdimension,editRemarks,editslaveid,editmeterslno,editYear,edittransformername,editReading;
	Button btnsurveysave,btnsurveyreset;                              
	LinearLayout linearlayouthide,linearlayouthide2,lineartypeofboxlayoutNo;
	TextView textConnId,txtscanresults,textRRNo;
	LinearLayout height,slaveidlinearlayout,dimensionedit,dimensiontext,tranformernamevalue;
	//03-03-2017                                                   
	private RadioGroup radioGroupProtocol,radioGroupOpticalReading;
	RadioButton radioSuccess,radioFailure;
	RadioButton radioButtonDLMSProprietary,radioButtonSuccessFailure;
	RadioButton radioElectrostatic,radioElectroMechanical,radioDLMS,radioProprietry;

	DatabaseHelper db=new DatabaseHelper(this);
	SurveyDetails sd;
	DDLAdapter conList;
	String meterModel="";
	int Grouptyped;
	CheckBox chkRJ11;
	CheckBox chkOptical;

	Handler dh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey_details);

		sd=new SurveyDetails();
		height=(LinearLayout)findViewById(R.id.height);
		slaveidlinearlayout=(LinearLayout)findViewById(R.id.slaveidlinearlayout);
		dimensionedit=(LinearLayout)findViewById(R.id.dimensionedit);
		dimensiontext=(LinearLayout)findViewById(R.id.dimensiontext);
		tranformernamevalue=(LinearLayout)findViewById(R.id.tranformernamevalue);
		dh=new Handler();
		radioGroupPhase=(RadioGroup)findViewById(R.id.radioGroupPhase);
		radioGrouptyped=(RadioGroup)findViewById(R.id.radioGrouptyped);
		radiounmetered=(RadioButton)findViewById(R.id.radiounmetered);
		radioGroupmMeterbox=(RadioGroup)findViewById(R.id.radioGroupmMeterbox);
		radioGroupmnmeteredtype=(RadioGroup)findViewById(R.id.radioGroupmnmeteredtype);
		textConnId = (TextView)findViewById(R.id.textConnId);	
		textRRNo = (TextView)findViewById(R.id.textRRNo);	
		txtscanresults = (TextView)findViewById(R.id.txtscanresults); //For Bar Code or Optical Reading Status
		ddlspinnermetermake=(Spinner)findViewById(R.id.ddlspinnermetermake);
		ddlTransformer=(Spinner)findViewById(R.id.ddlTransformer);
		editslaveid=(EditText)findViewById(R.id.editslaveid);
		editReading = (EditText)findViewById(R.id.editReading);

		height.setVisibility(LinearLayout.GONE);
		slaveidlinearlayout.setVisibility(LinearLayout.GONE);
		//dimensionedit.setVisibility(LinearLayout.GONE);
		//dimensiontext.setVisibility(LinearLayout.GONE);
		tranformernamevalue.setVisibility(LinearLayout.GONE);

		radioGrouptypeofbox=(RadioGroup)findViewById(R.id.radioGrouptypeofbox);
		radiometerboxNo = (RadioButton)findViewById(R.id.radiometerboxNo);
		radiometerboxYes	= (RadioButton)findViewById(R.id.radiometerboxYes);

		radioGroupMeterPlacement=(RadioGroup)findViewById(R.id.radioGroupMeterPlacement);
		radioButtonoutside=(RadioButton)findViewById(R.id.radioButtonoutside);

		editheightl=(EditText)findViewById(R.id.editheightl);


		spinnerfloor=(Spinner)findViewById(R.id.spinnerfloor);

		radioGroupVisibility=(RadioGroup)findViewById(R.id.radioGroupVisibility);
		radioButtonVisibilityYes=(RadioButton)findViewById(R.id.radioButtonVisibilityYes);
		radioButtonVisibilityNo=(RadioButton)findViewById(R.id.radioButtonVisibilityNo);

		//03-03-2017
		radioGroupProtocol = (RadioGroup)findViewById(R.id.radioGroupProtocol);
		radioGroupOpticalReading = (RadioGroup)findViewById(R.id.radioGroupOpticalReading);			
		chkRJ11 =  (CheckBox)findViewById(R.id.chk1);
		chkOptical = (CheckBox)findViewById(R.id.chk2);		

		editmeterdetails=(EditText)findViewById(R.id.editmeterdetails);
		editdimension=(EditText)findViewById(R.id.editdimension);
		editRemarks=(EditText)findViewById(R.id.editRemarks);
		editmeterslno=(EditText)findViewById(R.id.editmeterslno);
		editYear=(EditText)findViewById(R.id.editYear);
		edittransformername=(EditText)findViewById(R.id.edittransformername);

		btnsurveysave=(Button)findViewById(R.id.btnsurveysave);
		btnsurveyreset=(Button)findViewById(R.id.btnsurveyreset);

		linearlayouthide=(LinearLayout)findViewById(R.id.linearlayouthide);
		lineartypeofboxlayoutNo=(LinearLayout)findViewById(R.id.lineartypeofboxlayoutNo);
		radiometered=(RadioButton)findViewById(R.id.radiometered);
		radioButtonMetalic=(RadioButton)findViewById(R.id.radioButtonMetalic);
		linearlayouthide2=(LinearLayout)findViewById(R.id.linearlayouthide2);



		radioSuccess=(RadioButton)findViewById(R.id.radioSuccess);
		radioFailure=(RadioButton)findViewById(R.id.radioFailure);
		radioElectrostatic=(RadioButton)findViewById(R.id.radioElectrostatic);
		radioElectroMechanical=(RadioButton)findViewById(R.id.radioElectroMechanical);

		radioDLMS=(RadioButton)findViewById(R.id.radioDLMS);
		radioProprietry=(RadioButton)findViewById(R.id.radioProprietry);

		radiometered.setChecked(true);
		radioButtonMetalic.setChecked(true);

		//##############spinner for select MeterMake #########################
		try {

			try
			{
				editmeterslno.setText("");
				if(BillingObject.GetBillingObject().getmRRNo()==null || BillingObject.GetBillingObject().getmConnectionNo() == null)
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Error in Data.",  Toast.LENGTH_SHORT);
					Intent iCheck = new Intent(SurveyDetailsActivity.this, LoginActivity.class);
					startActivity(iCheck);
					return;					
				}
				else
				{

					textConnId.setText(ConstantClass.mSurvryName + "(Electricity)"); //Kochi Survey  19-03-2019
					textRRNo.setText("ACCNO/CONS NAME: " + BillingObject.GetBillingObject().getmRRNo());

				}
			}
			catch(Exception e)
			{	
			}

			conList = db.getMeterMakeHESCOM();
			ddlspinnermetermake.setAdapter(conList);
			txtscanresults.setText("Manual");


			ddlspinnermetermake.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					try
					{
						DDLItem k = (DDLItem)  arg0.getItemAtPosition(arg2);
						sd.setMeterMake(k.getId().toString());
						//sd.setPhasevalue(k.getValue().toString());

						sd.setModel("0");

					}
					catch (Exception e) {
						e.printStackTrace(); 
					}
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		//################################End###################################
		//##############spinner for select Floor#########################
		try {
			conList = db.getTransformerName(); //01-12-2017 for GESCOM
			ddlTransformer.setAdapter(conList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ddlTransformer.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try
				{
					DDLItem k = (DDLItem)  arg0.getItemAtPosition(arg2);
					//sd.setTransFarmerName(k.getId().toString());					
					//sd.setTransFarmerName(BillingObject.GetBillingObject().getmDownloadUploadSpeed()); //17-07-2021 for Download Upload Speed
				}
				catch (Exception e) {
					e.printStackTrace(); 
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		//################################End###################################

		//##############spinner for select Floor#########################
		try {
			conList = db.getFloorHESCOMSurvey();
			spinnerfloor.setAdapter(conList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		spinnerfloor.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try
				{
					DDLItem k = (DDLItem)  arg0.getItemAtPosition(arg2);
					sd.setFloor(k.getId().toString());
					//sd.setPhasevalue(k.getValue().toString());
				}
				catch (Exception e) {
					e.printStackTrace(); 
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		//################################End###################################
		//##############spinner for Model#########################



		//################################End###################################



		radiometered.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				linearlayouthide.setVisibility(View.VISIBLE);

			}
		});


		radiounmetered.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				linearlayouthide.setVisibility(View.GONE);
			}
		});	
		radiometerboxYes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lineartypeofboxlayoutNo.setVisibility(View.VISIBLE);

			}
		});

		radiometerboxNo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lineartypeofboxlayoutNo.setVisibility(View.GONE);

			}
		});

		radioButtonVisibilityYes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				editdimension.setText("");
				linearlayouthide2.setVisibility(View.VISIBLE);

			}
		});


		radioButtonVisibilityNo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				editdimension.setText("");
				linearlayouthide2.setVisibility(View.GONE);
			}
		});



		btnsurveysave.setOnClickListener(new OnClickListener() 
		{



			@Override
			public void onClick(View arg0)
			{

				if(String.valueOf(LoginActivity.gpsTracker.latitude).equals("0.0"))
				{
					try {
						CustomToast.makeText(SurveyDetailsActivity.this, "GPS Cordinates Not Obtained.",  Toast.LENGTH_SHORT);
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
				Intent i=new Intent(SurveyDetailsActivity.this,SurveyDetailsActivity.class);
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
		return OptionsMenu.navigate(item,SurveyDetailsActivity.this);		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		CustomToast.makeText(SurveyDetailsActivity.this, "Please use menu to navigate.", Toast.LENGTH_SHORT);
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
		int GroupPhase=radioGroupPhase.getCheckedRadioButtonId();
		radiosinglePhase=(RadioButton)findViewById(GroupPhase);
		if(GroupPhase == -1 )
		{
			CustomToast.makeText(SurveyDetailsActivity.this, "Select Phase.",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			if(radiosinglePhase.getText().equals("Single"))
			{
				sd.setPhase("1");                    
			}
			else if (radiosinglePhase.getText().equals("Three-CT"))
			{
				sd.setPhase("2");
			}
			else if (radiosinglePhase.getText().equals("Three-NonCT"))
			{
				sd.setPhase("3");
			}
		}




		Grouptyped=radioGrouptyped.getCheckedRadioButtonId();
		radiometered=(RadioButton)findViewById(Grouptyped);

		if(Grouptyped == -1 )
		{

			CustomToast.makeText(SurveyDetailsActivity.this, "Select Connection Type.",  Toast.LENGTH_SHORT);
			return;
		}
		else
		{
			if(radiometered.getText().equals("Metered"))
			{
				sd.setConnectionType("1");
				sd.setMeterBoxAvailability("0");
				sd.setTypeOfBox("0");				
				int GroupMeterBox=radioGroupmMeterbox.getCheckedRadioButtonId();
				radiometerboxYes=(RadioButton)findViewById(GroupMeterBox);
				if(GroupMeterBox == -1 )
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Select MeterBox Availability.",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					if(radiometerboxYes.getText().equals("Yes"))
					{
						sd.setMeterBoxAvailability("1");
					}
					else if (radiometerboxYes.getText().equals("No"))
					{
						sd.setMeterBoxAvailability("2");
					}
				}

				if(ddlspinnermetermake.getSelectedItemPosition()==0)
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Select Meter Make.",  Toast.LENGTH_SHORT);
					return;
				}

				//19-03-2019
				sd.setMeterSlNo("0");
				sd.setYearofManufacture("0");
				sd.setMeterDiemension("0");
				sd.setProtocol("0");
				sd.setOpticalReading("0");
				sd.setComRJ11("0");
				sd.setComOptical("0");

				//CustomToast.makeText(SurveyDetailsActivity.this, sd.getMeterMake()+"savetime",  Toast.LENGTH_SHORT);
				if(editmeterslno.getText().toString().equals(""))
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Please Enter Meter SlNo",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					sd.setMeterSlNo(editmeterslno.getText().toString());
				}
				if(editYear.getText().toString().equals(""))
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Please Enter Year of Manufacture",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					sd.setYearofManufacture(editYear.getText().toString());
				}
				/*if(edittransformername.getText().toString().equals(""))
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Please Write the Transformer Name",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					sd.setTransFarmerName(edittransformername.getText().toString());
				}*/

				/*if(edittransformername.getText().toString().trim()==null || edittransformername.getText().toString().trim().equals(""))
				{
					sd.setTransFarmerName("0");
				}
				else
				{
					sd.setTransFarmerName(edittransformername.getText().toString().trim());
				}*/

				/*if(ddlTransformer.getSelectedItemPosition()==0)
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Select Transformer.",  Toast.LENGTH_SHORT);
					return;
				}*/
				//sd.setTransFarmerName("0"); //16-07-2021
				//sd.setTransFarmerName(BillingObject.GetBillingObject().getmDownloadUploadSpeed()); //17-07-2021 for Download Upload Speed
				int GroupMeterTyped=radioGroupmnmeteredtype.getCheckedRadioButtonId();
				radiooElectrostatic=(RadioButton)findViewById(GroupMeterTyped);
				if(GroupMeterTyped == -1 )
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Select Meter Type.",  Toast.LENGTH_SHORT);
					return;
				}

				else
				{
					if(radiooElectrostatic.getText().equals("Electronic"))
					{
						sd.setMeterType("1");
					}
					else if (radiooElectrostatic.getText().equals("Mechanical"))
					{
						sd.setMeterType("2");
					}
				}

				if(radiometerboxYes.getText().equals("Yes"))
				{

					int Grouptypeofbox=radioGrouptypeofbox.getCheckedRadioButtonId();
					radioButtonMetalic=(RadioButton)findViewById(Grouptypeofbox);
					if(Grouptypeofbox == -1 )
					{

						CustomToast.makeText(SurveyDetailsActivity.this, "Select Type of Box.",  Toast.LENGTH_SHORT);
						return;
					}

					else
					{
						if(radioButtonMetalic.getText().equals("Metalic"))
						{
							sd.setTypeOfBox("1");
						}
						else if (radioButtonMetalic.getText().equals("Plastic"))
						{
							sd.setTypeOfBox("2");
						}
					}
				}
				else if(radiometerboxYes.getText().equals("No"))
				{

					sd.setTypeOfBox("0");
				}
				int GroupMeterPlacement=radioGroupMeterPlacement.getCheckedRadioButtonId();
				radioButtoninside=(RadioButton)findViewById(GroupMeterPlacement);
				if(GroupMeterPlacement == -1 )
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Select Meter Placement",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					if(radioButtoninside.getText().equals("Inside House"))
					{                   
						sd.setMeterPlacement("1");
					}
					else if (radioButtoninside.getText().equals("Outside House"))
					{
						sd.setMeterPlacement("2");
					}
				}
				try {
					sd.setHeightOfMeter("not in use");
				} catch (Exception e) {
					e.printStackTrace();				
				}
				if(spinnerfloor.getSelectedItemPosition()==0)
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Select Floor.",  Toast.LENGTH_SHORT);
					return;
				}
				int GroupVisibility=radioGroupVisibility.getCheckedRadioButtonId();
				radioButtonVisibilityYes=(RadioButton)findViewById(GroupVisibility);

				if(GroupVisibility == -1 )
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Select Meter Placed Group or Not",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					if(radioButtonVisibilityYes.getText().equals("Yes"))
					{
						//sd.setMeterBoxAvailability("1");  //05-07-2017


						/*if(editmeterdetails.getText().toString().equals(""))
						{
							CustomToast.makeText(SurveyDetailsActivity.this, "Please Enter How Many Meters are Available",  Toast.LENGTH_SHORT);
							return;
						}
						else
						{							
							sd.setNoOfMeterAvailable(editmeterdetails.getText().toString());
						}*/
						sd.setNoOfMeterAvailable(editmeterdetails.getText().toString());
						//sd.setMeterDiemension("not in use");
					}
					else if (radioButtonVisibilityYes.getText().equals("No"))
					{
						//sd.setMeterBoxAvailability("2");
						sd.setNoOfMeterAvailable("0");						
					}					
					/*if(editdimension.getText().toString().equals(""))
					{
						CustomToast.makeText(SurveyDetailsActivity.this, "Please Enter Approx Dimension",  Toast.LENGTH_SHORT);
						return;
					}
					else
					{
						sd.setMeterDiemension(editdimension.getText().toString());
					}*/
				}
				//03-03-2017
				try
				{

					/*if(AndroidBarcodeQrActivity.manualandopticalflag.equals("1"))
					{*/
					int DLMSProprietary=radioGroupProtocol.getCheckedRadioButtonId();
					radioButtonDLMSProprietary=(RadioButton)findViewById(DLMSProprietary);
					if(DLMSProprietary == -1 )
					{

						CustomToast.makeText(SurveyDetailsActivity.this, "Select Protocol.",  Toast.LENGTH_SHORT);
						return;
					}
					else
					{
						if(radioButtonDLMSProprietary.getText().equals("DLMS"))
						{
							sd.setProtocol("1");
						}
						else if (radioButtonDLMSProprietary.getText().equals("Proprietry"))
						{
							sd.setProtocol("2");
						}
					}
					//20-05-2017 punit
					/*if(DLMSProprietary == -1 )
					{
						sd.setProtocol("0");

					}
					else
					{
						if(radioButtonDLMSProprietary.getText().equals("DLMS"))
						{
							sd.setProtocol("1");
						}
						else if (radioButtonDLMSProprietary.getText().equals("Proprietry"))
						{
							sd.setProtocol("2");
						}
					}*/
					/*if(OpticalReading == -1 )
					{

						CustomToast.makeText(SurveyDetailsActivity.this, "Select Optical Reading Result.",  Toast.LENGTH_SHORT);
						return;
					}

					else
					{
						if(radioButtonSuccessFailure.getText().equals("Success"))
						{
							sd.setOpticalReading("1");
						}
						else if (radioButtonDLMSProprietary.getText().equals("Failure"))
						{
							sd.setOpticalReading("2");
						}
					}*/



					/*if(AndroidBarcodeQrActivity.manualandopticalflag.equals("1"))
					{
						int OpticalReading=radioGroupOpticalReading.getCheckedRadioButtonId();
						radioButtonSuccessFailure=(RadioButton)findViewById(OpticalReading);
						if(OpticalReading == -1 )
						{
							sd.setOpticalReading("0");
							//CustomToast.makeText(SurveyDetailsActivity.this, "Select Optical Reading Result.",  Toast.LENGTH_SHORT);
							//return;
						}
						else
						{
							if(radioButtonSuccessFailure.getText().equals("Success"))
							{
								//CustomToast.makeText(SurveyDetailsActivity.this, "Success.",  Toast.LENGTH_SHORT);
								sd.setOpticalReading("1");
							}
							else if (radioButtonSuccessFailure.getText().equals("Failure"))
							{
								sd.setOpticalReading("2");
							}
						}
					}*/
					if(chkRJ11.isChecked())
					{
						sd.setComRJ11("1");
					}
					else
					{
						sd.setComRJ11("0");
					}
					if(chkOptical.isChecked())
					{
						sd.setComOptical("1");
					}
					else
					{
						sd.setComOptical("0");
					}
				}
				catch(Exception e)
				{

				}
				//End 03-03-2017




				/*if(editslaveid.getText().toString().equals(""))
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Please Write the Slave ID",  Toast.LENGTH_SHORT);
					return;
				}
				else
				{
					sd.setSlaveId(editslaveid.getText().toString());
				}*/
				sd.setSlaveId("0");
				if(editRemarks.getText().toString().trim().equals(""))
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Please Write the Remarks",  Toast.LENGTH_SHORT);
					return;
				}					
				else if(editReading.getText().toString().trim().equals(""))
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Please enter Reading",  Toast.LENGTH_SHORT);
					return;

				}
				else
				{
					//sd.setRemarks(ConstantClass.mSurvryName+"|" +editRemarks.getText().toString()); //10-03-2021
					//19-07-2021
					sd.setRemarks(ConstantClass.mSurvryName+ " - " + ConstantClass.AppVersion +"|" + editReading.getText().toString() + "|" + editRemarks.getText().toString());
				}



				sd.setRRNo(BillingObject.GetBillingObject().getmRRNo());
				sd.setConnectionId(BillingObject.GetBillingObject().getmConnectionNo());
				sd.setCustomerName(BillingObject.GetBillingObject().getmCustomerName());


				/*sd.setRRNo("0");
				sd.setConnectionId("0");
				sd.setCustomerName("0");*/
				sd.setAvailabilityOfLineOfSiht("1");

				if(LoginActivity.gpsTracker.canGetLocation())
				{	
					sd.setLattitude(String.valueOf(LoginActivity.gpsTracker.latitude));
					sd.setLongitude(String.valueOf(LoginActivity.gpsTracker.longitude));
				}
				try
				{
					String imgPath = ImageSaveinSDCard();
					//String imgPath = "NO IMAGE";
					sd.setImagePath(imgPath);
				}
				catch(Exception e)
				{

				}
				db.CreateIfNotExistsHescomSurveyTable();
				try
				{
					//sd.setTransFarmerName(txtscanresults.getText().toString());
					CustomAlert.CustomAlertParameters params = new CustomAlert.CustomAlertParameters(); 
					params.setContext(SurveyDetailsActivity.this);					
					params.setMainHandler(dh);
					/*
					private String ConnectionId;
					private String CustomerName;
					private String ConnectionType;
					private String Phase;
					private String MeterMake;
					private String Model;
					private String MeterType;
					private String MeterBoxAvailability;
					private String TypeOfBox;
					private String MeterPlacement;
					private String HeightOfMeter;
					private String AvailabilityOfLineOfSiht;
					private String floor;
					private String NoOfMeterAvailable;
					private String MeterDiemension;
					private String Remarks;
					private String Lattitude;
					private String Longitude;
					private String GPSFlag;
					private String ImagePath;
					private String DateTime;
					private String SlaveId;
					private String Protocol;
					private String OpticalReading;
					private String ComRJ11;


					private String meterSlNo;
					private String yearofManufacture;
					private String transFarmerName;*/
					String metrtype="";
					String mtemake="";

					String mmFloor="";
					if(sd.getMeterType().trim().equals("1"))
					{
						metrtype="Electronic";
					}
					else if(sd.getMeterType().trim().equals("2"))
					{
						metrtype="Mechanical";
					}	

					try
					{

						mtemake = ddlspinnermetermake.getSelectedItem().toString().trim();

						mmFloor = spinnerfloor.getSelectedItem().toString().trim();
					}
					catch(Exception e)
					{

					}
					/*if(sd.getMeterMake().trim().equals("2"))
					{
						mtemake="L&T";
					}
					else if(sd.getMeterMake().trim().equals("1"))
					{
						mtemake="DC/NOMETER";
					}
					else if(sd.getMeterMake().trim().equals("4"))
					{
						mtemake="BHEL";
					}
					else if(sd.getMeterMake().trim().equals("5"))
					{
						mtemake="ACTARIS";
					}
					else if(sd.getMeterMake().trim().equals("7"))
					{
						mtemake="AVON";
					}
					else if(sd.getMeterMake().trim().equals("9"))
					{
						mtemake="ISKRA";
					}
					else if(sd.getMeterMake().trim().equals("10"))
					{
						mtemake="I M";
					}
					else if(sd.getMeterMake().trim().equals("11"))
					{
						mtemake="SIEMENS";
					}
					else if(sd.getMeterMake().trim().equals("12"))
					{
						mtemake="R.C";
					}
					else if(sd.getMeterMake().trim().equals("13"))
					{
						mtemake="T.T.L";
					}
					else if(sd.getMeterMake().trim().equals("14"))
					{
						mtemake="PRECESITION";
					}
					else if(sd.getMeterMake().trim().equals("15"))
					{
						mtemake="LANDIS";
					}
					else if(sd.getMeterMake().trim().equals("17"))
					{
						mtemake="CAPITAL";
					}
					else if(sd.getMeterMake().trim().equals("18"))
					{
						mtemake="HAVELLS";
					}
					else if(sd.getMeterMake().trim().equals("19"))
					{
						mtemake="ELYMER";
					}
					else if(sd.getMeterMake().trim().equals("21"))
					{
						mtemake="OMANI";
					}
					else if(sd.getMeterMake().trim().equals("22"))
					{
						mtemake="ACCURATE";
					}
					else if(sd.getMeterMake().trim().equals("24"))
					{
						mtemake="BHEK";
					}
					else if(sd.getMeterMake().trim().equals("26"))
					{
						mtemake="OLAY";
					}
					else if(sd.getMeterMake().trim().equals("27"))
					{
						mtemake="DATAK";
					}
					else if(sd.getMeterMake().trim().equals("28"))
					{
						mtemake="Alstom";
					}
					else if(sd.getMeterMake().trim().equals("29"))
					{
						mtemake="EMCO";
					}
					else if(sd.getMeterMake().trim().equals("31"))
					{
						mtemake="HIL";
					}
					else if(sd.getMeterMake().trim().equals("32"))
					{
						mtemake="INDOTECK";
					}
					else if(sd.getMeterMake().trim().equals("33"))
					{
						mtemake="INDOTECH";
					}
					else if(sd.getMeterMake().trim().equals("34"))
					{
						mtemake="GENUS";
					}
					else if(sd.getMeterMake().trim().equals("35"))
					{
						mtemake="HTL";
					}
					else if(sd.getMeterMake().trim().equals("36"))
					{
						mtemake="ZCE";
					}
					else if(sd.getMeterMake().trim().equals("37"))
					{
						mtemake="RAMCO";
					}
					else if(sd.getMeterMake().trim().equals("38"))
					{
						mtemake="HPL";
					}
					else if(sd.getMeterMake().trim().equals("8"))
					{
						mtemake="L&G";
					}
					else if(sd.getMeterMake().trim().equals("3"))
					{
						mtemake="SECURE";
					}					
					if(sd.getModel().trim().equals("1"))
					{
						mmodel="L&T";
					}
					if(sd.getModel().trim().equals("2"))
					{
						mmodel="LGzce";
					}
					if(sd.getModel().trim().equals("3"))
					{
						mmodel="Saral";
					}
					if(sd.getModel().trim().equals("4"))
					{
						mmodel="LG110c";
					}
					if(sd.getModel().trim().equals("5"))
					{
						mmodel="Secure3ph";
					}
					if(sd.getModel().trim().equals("6"))
					{
						mmodel="LG Dlms";
					}
					if(sd.getModel().trim().equals("7"))
					{
						mmodel="LG3ph";
					}
					if(sd.getModel().trim().equals("8"))
					{
						mmodel="LT1ph";
					}
					if(sd.getModel().trim().equals("9"))
					{
						mmodel="LT3ph";
					}
					if(sd.getModel().trim().equals("10"))
					{
						mmodel="HPL1ph";
					}
					if(sd.getModel().trim().equals("11"))
					{
						mmodel="HPL3ph";
					}
					if(sd.getModel().trim().equals("12"))
					{
						mmodel="Icredit 1ph";
					}
					if(sd.getModel().trim().equals("13"))
					{
						mmodel="CAP DLMS 1ph";
					}
					if(sd.getModel().trim().equals("14"))
					{
						mmodel="CAP DLMS 3ph";
					}*/

					String mtrreading="";
					if(sd.getOpticalReading()==null)
					{
						sd.setOpticalReading("0");
					}					
					else if(sd.getOpticalReading().trim().equals("1"))
					{
						mtrreading="Success";
					}
					else if(sd.getOpticalReading().equals("2"))
					{
						mtrreading="Failure";
					}


					String mtrprotocol="";

					if(sd.getProtocol().trim().equals("1"))
					{
						mtrprotocol="DLMS";
					}
					if(sd.getProtocol().equals("2"))
					{
						mtrprotocol="Proprietry";
					}
					params.setIcon(R.drawable.surveyicon);

					params.setMsg("ACCNO/CONS NAME:"+sd.getRRNo()+"\n"+"Phase:"+radiosinglePhase.getText().toString().trim()+"\n"+"Connecton Type:"+radiometered.getText().toString().trim()
							+"\n"+"MeterBox Availability :"+radiometerboxYes.getText().toString().trim()+"\n"+"Type Of Box:"+radioButtonMetalic.getText().toString().trim()
							+"\n"+"Meter Make:"+mtemake
							+"\n"+"Meter Serial No:"+sd.getMeterSlNo()
							+"\n"+"Year Of Manufacture :"+sd.getYearofManufacture()
							//+"\n"+"Transformer :"+ddlTransformer.getSelectedItem().toString()							
							+"\n"+"Meter Type :"+metrtype
							+"\n"+"Meter Placement :"+radioButtoninside.getText().toString().trim()
							+"\n"+"Floor :"+mmFloor
							//+"\n"+"Optical Port Availability :"+sd.getComOptical()
							//+"\n"+"RJ11 Availability :"+sd.getComRJ11()
							//+"\n"+"Protocal :"+mtrprotocol							
							+"\n"+"Remarks :"+sd.getRemarks());

					/*if(radiometerboxYes.getText().equals("Yes"))
					{
						params.setMsg("RRNO:"+sd.getRRNo()+"\n"+"Phase:"+radiosinglePhase.getText().toString().trim()+"\n"+"Connecton Type:"+radiometered.getText().toString().trim()
								+"\n"+"MeterBox Availability :"+radiometerboxYes.getText().toString().trim()+"\n"+"Type Of Box:"+radioButtonMetalic.getText().toString().trim()+"\n"+"Meter Make:"+mtemake
								+"\n"+"Meter Serial No:"+sd.getMeterSlNo()
								+"\n"+"Year Of Manufacture :"+sd.getYearofManufacture()
								+"\n"+"Transformer :"+ddlTransformer.getSelectedItem().toString()
								+"\n"+"Model :"+mmodel
								+"\n"+"Meter Type :"+metrtype
								+"\n"+"Meter Placement :"+radioButtoninside.getText().toString().trim()
								+"\n"+"Floor :"+sd.getFloor()
								+"\n"+"Optical Port Availability :"+sd.getComOptical()
								+"\n"+"RJ11 Availability :"+sd.getComRJ11()
								+"\n"+"Protocal :"+mtrprotocol
								+"\n"+"Optical Meter Reading :"+mtrreading
								+"\n"+"Reading|Remarks :"+sd.getRemarks());

					}
					else if(radiometerboxYes.getText().equals("No"))
					{

						params.setMsg("RRNO:"+sd.getRRNo()+"\n"+"Phase:"+radiosinglePhase.getText().toString().trim()+"\n"+"Connecton Type:"+radiometered.getText().toString().trim()
								+"\n"+"MeterBox Availability :"+radiometerboxYes.getText().toString().trim()+"\n"+"Meter Make:"+mtemake
								+"\n"+"Meter Serial No:"+sd.getMeterSlNo()
								+"\n"+"Year Of Manufacture :"+sd.getYearofManufacture()
								+"\n"+"Transformer :"+ddlTransformer.getSelectedItem().toString()
								+"\n"+"Model :"+mmodel
								+"\n"+"Meter Type :"+metrtype
								+"\n"+"Meter Placement :"+radioButtoninside.getText().toString().trim()
								+"\n"+"Floor :"+sd.getFloor()
								+"\n"+"Optical Port Availability :"+sd.getComOptical()
								+"\n"+"RJ11 Availability :"+sd.getComRJ11()
								+"\n"+"Protocal :"+mtrprotocol
								+"\n"+"Optical Meter Reading :"+mtrreading
								+"\n"+"Reading|Remarks :"+sd.getRemarks());
					}	*/			
					params.setButtonOkText("No");
					params.setButtonCancelText("Yes");
					params.setTitle("Save");
					params.setFunctionality(2);
					CustomAlert cAlert  = new CustomAlert(params);

					/*int save = db.insertSurveyDetails(sd);
					//03-03-2017
					db.UpdateSurveyBlCnt(BillingObject.GetBillingObject().getmConnectionNo());
					if(save>=1)
					{                    
						CustomToast.makeText(SurveyDetailsActivity.this, "Saved Successfully",  Toast.LENGTH_SHORT);
						Intent iSave = new Intent(SurveyDetailsActivity.this, LoginActivity.class);
						startActivity(iSave);
					}
					else
					{
						CustomToast.makeText(SurveyDetailsActivity.this, "Failed to Save Survey Data",  Toast.LENGTH_SHORT);
						return;
					}*/
				}
				catch(Exception e)
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Failed to Save Survey Data",  Toast.LENGTH_SHORT);
					return;
				}

				

			}
			else if (radiometered.getText().equals("Unmetered"))
			{
				/*if(ddlTransformer.getSelectedItemPosition()==0)
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Select Transformer.",  Toast.LENGTH_SHORT);
					return;
				}*/
				//sd.setTransFarmerName(BillingObject.GetBillingObject().getmDownloadUploadSpeed()); //17-07-2021 for Download Upload Speed
				sd.setConnectionType("2");                     
				sd.setConnectionId(BillingObject.GetBillingObject().getmConnectionNo());
				sd.setRRNo(BillingObject.GetBillingObject().getmRRNo());

				//17-07-2021 for Signal Strength			
				Billing.signalStrength = Billing.signalStrength + "/" + CommonFunction.getSignalStrength(SurveyDetailsActivity.this);
				if(Billing.signalStrength.trim().length() > 40)
				{
					Billing.signalStrength = Billing.signalStrength.trim().substring(0, 40);
				}				
				sd.setTransFarmerName(Billing.signalStrength);
				CustomToast.makeText(SurveyDetailsActivity.this, "Signal Strength : " + Billing.signalStrength ,  Toast.LENGTH_LONG);
				//End 17-07-2021

				int save = db.insertSurveyDetails(sd);
				//03-03-2017
				db.UpdateSurveyBlCnt(BillingObject.GetBillingObject().getmConnectionNo());
				if(save>=1)
				{

					CustomToast.makeText(SurveyDetailsActivity.this, "Saved Successfully",  Toast.LENGTH_SHORT);
					Intent iSave = new Intent(SurveyDetailsActivity.this, LoginActivity.class);
					startActivity(iSave);
					finish();
				}
				else
				{
					CustomToast.makeText(SurveyDetailsActivity.this, "Failed to Save Survey Data2",  Toast.LENGTH_SHORT);
					return;
				}
			}
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
			Billing.signalStrength = Billing.signalStrength + "/" + CommonFunction.getSignalStrength(SurveyDetailsActivity.this);
			if(Billing.signalStrength.trim().length() > 40)
			{
				Billing.signalStrength = Billing.signalStrength.trim().substring(0, 40);
			}				
			sd.setTransFarmerName(Billing.signalStrength);
			CustomToast.makeText(SurveyDetailsActivity.this, "Signal Strength : " + Billing.signalStrength ,  Toast.LENGTH_LONG);
			//End 17-07-2021

			int save = db.insertSurveyDetails(sd);
			//03-03-2017
			db.UpdateSurveyBlCnt(BillingObject.GetBillingObject().getmConnectionNo());
			if(save>=1)
			{                    
				CustomToast.makeText(SurveyDetailsActivity.this, "Saved Successfully",  Toast.LENGTH_SHORT);
				Intent iSave = new Intent(SurveyDetailsActivity.this, LoginActivity.class);
				startActivity(iSave);
				finish();
			}
			else
			{
				CustomToast.makeText(SurveyDetailsActivity.this, "Failed to Save Survey Data",  Toast.LENGTH_SHORT);
				return;
			}
		}
	}
}
