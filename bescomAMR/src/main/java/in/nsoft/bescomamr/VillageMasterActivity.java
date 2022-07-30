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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class VillageMasterActivity extends Activity implements AlertInterface{

	Spinner ddlDistrict,ddlTaluk,ddlGramPanchayat,ddlVillage;
	LinearLayout lytVillageDetails;
	EditText txtVillage;
	Button btnsend,btnreset,btnEdit,btnUpdate;
	CustomAlert cAlert;
	DatabaseHelper db;
	SurveyDetails sd;
	Handler dh;
	static String districtId,talukId,grmPamId,villageId = "0";
	//ProgressDialog ringProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_village_master);

		db = new DatabaseHelper(this);
		sd=new SurveyDetails();
		dh=new Handler();

		ddlDistrict = (Spinner)findViewById(R.id.ddlDistrict);
		ddlTaluk = (Spinner)findViewById(R.id.ddlTaluk);
		ddlGramPanchayat = (Spinner)findViewById(R.id.ddlGramPanchayat);
		ddlVillage =(Spinner)findViewById(R.id.ddlVillage);
		txtVillage = (EditText)findViewById(R.id.txtVillage);
		btnsend =(Button)findViewById(R.id.btnsend);
		btnreset =(Button)findViewById(R.id.btnreset);
		btnEdit =(Button)findViewById(R.id.btnEdit);
		btnUpdate=(Button)findViewById(R.id.btnUpdate);
		lytVillageDetails=(LinearLayout)findViewById(R.id.lytVillageDetails);

		districtId =talukId=grmPamId = "0";

		try
		{
			ddlDistrict.setAdapter(db.getMasterData(1,"0"));
			ddlTaluk.setAdapter(db.getMasterData(0,"0"));
			ddlGramPanchayat.setAdapter(db.getMasterData(0,"0"));
			ddlVillage.setAdapter(db.getMasterData(0, "0"));
		}
		catch(Exception e)
		{

		}


		lytVillageDetails.setVisibility(View.GONE);
		btnUpdate.setVisibility(View.GONE);


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


		btnsend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {


					if(ddlDistrict.getSelectedItem().toString().equals("--SELECT--"))
					{
						CustomToast.makeText(VillageMasterActivity.this, "Select District.",  Toast.LENGTH_SHORT);
						return;
					}
					else if(ddlTaluk.getSelectedItem().toString().equals("--SELECT--"))
					{
						CustomToast.makeText(VillageMasterActivity.this, "Please Taluk.", Toast.LENGTH_SHORT);
						return;
					}
					else if(ddlGramPanchayat.getSelectedItem().toString().equals("--SELECT--"))
					{
						CustomToast.makeText(VillageMasterActivity.this, "Select Gram Panchayat.",  Toast.LENGTH_SHORT);
						return;
					}
					else if(txtVillage.getText().toString().trim().length()==0)
					{
						CustomToast.makeText(VillageMasterActivity.this, "Enter Village Name.", Toast.LENGTH_SHORT);
						return;
					}

					sd.setDistrictId(districtId);
					sd.setTalukId(talukId);
					sd.setGPId(grmPamId);


					CustomAlert.CustomAlertParameters params = new CustomAlert.CustomAlertParameters(); 
					params.setContext(VillageMasterActivity.this);					
					params.setMainHandler(dh);
					params.setMsg("Are you Sure You Want To Send Data to Server ? ");
					params.setButtonOkText("No");
					params.setButtonCancelText("Yes");
					params.setTitle("Send");
					params.setFunctionality(1);
					cAlert  = new CustomAlert(params);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}


			}
		});

		btnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lytVillageDetails.setVisibility(View.VISIBLE);
				btnUpdate.setVisibility(View.VISIBLE);
				btnsend.setVisibility(View.GONE);
				
				try
				{
					ddlDistrict.setAdapter(db.getMasterData(1,"0"));
					ddlTaluk.setAdapter(db.getMasterData(0,"0"));
					ddlGramPanchayat.setAdapter(db.getMasterData(0,"0"));
					ddlVillage.setAdapter(db.getMasterData(0,"0"));
				}
				catch(Exception e)
				{

				}
			}
		});
		btnUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					if(ddlDistrict.getSelectedItem().toString().equals("--SELECT--"))
					{
						CustomToast.makeText(VillageMasterActivity.this, "Select District.",  Toast.LENGTH_SHORT);
						return;
					}
					else if(ddlTaluk.getSelectedItem().toString().equals("--SELECT--"))
					{
						CustomToast.makeText(VillageMasterActivity.this, "Please Taluk.", Toast.LENGTH_SHORT);
						return;
					}
					else if(ddlGramPanchayat.getSelectedItem().toString().equals("--SELECT--"))
					{
						CustomToast.makeText(VillageMasterActivity.this, "Select Gram Panchayat.",  Toast.LENGTH_SHORT);
						return;
					}
					else if(ddlVillage.getSelectedItem().toString().equals("--SELECT--"))
					{
						CustomToast.makeText(VillageMasterActivity.this, "Select Village.",  Toast.LENGTH_SHORT);
						return;
					}
					else if(txtVillage.getText().toString().trim().length()==0)
					{
						CustomToast.makeText(VillageMasterActivity.this, "Enter Village Name.", Toast.LENGTH_SHORT);
						return;
					}

					sd.setDistrictId(districtId);
					sd.setTalukId(talukId);
					sd.setGPId(grmPamId);
					sd.setVillageId(villageId);


					CustomAlert.CustomAlertParameters params = new CustomAlert.CustomAlertParameters(); 
					params.setContext(VillageMasterActivity.this);					
					params.setMainHandler(dh);
					params.setMsg("Are you Sure You Want To Send Data to Server ? ");
					params.setButtonOkText("No");
					params.setButtonCancelText("Yes");
					params.setTitle("Send");
					params.setFunctionality(2);
					cAlert  = new CustomAlert(params);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});



		btnreset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					CustomAlert.CustomAlertParameters params = new CustomAlert.CustomAlertParameters(); 
					params.setContext(VillageMasterActivity.this);					
					params.setMainHandler(dh);
					params.setMsg("Are you Sure You Want To Reset ? ");
					params.setButtonOkText("No");
					params.setButtonCancelText("Yes");
					params.setTitle("Reset");
					params.setFunctionality(3);
					cAlert  = new CustomAlert(params);

				} catch (Exception e) {
					// TODO: handle exception
				}


			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spotbillingmenu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return OptionsMenu.navigate(item,VillageMasterActivity.this);			
	}

	@Override
	public void performAction(boolean alertResult, int functionality) {
		// TODO Auto-generated method stub
		if(functionality==0)
		{

		}		
		else if(!alertResult && functionality==1)
		{
			new VillageMaster().execute();		
		}
		else if(!alertResult && functionality==2)
		{
			new UpdateVillageMaster().execute();		
		}
		else if(!alertResult && functionality==3)
		{
			Intent i = new Intent(VillageMasterActivity.this,VillageMasterActivity.class);							
			startActivity(i);
			finish();
		}

	}

	private class VillageMaster extends AsyncTask<Void, Void, Void> {
		final ProgressDialog ringProgress1 =	ProgressDialog.show(VillageMasterActivity.this, "Please wait..","Saving Data...",true);//Spinner
		String rValue = "";
		ArrayList<String> lststrVillageStart;
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpClient httpclt = new DefaultHttpClient();//object for HttpClient	
				HttpPost httpPost = new HttpPost(ConstantClass.IPAddress + "/Android_GetWaterSurvey");
				List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);
				lvp.add(new BasicNameValuePair("Flag", "6"));
				lvp.add(new BasicNameValuePair("IMEINO", LoginActivity.IMEINumber));	
				lvp.add(new BasicNameValuePair("Id", "0"));
				lvp.add(new BasicNameValuePair("RRNO", "0"));	
				lvp.add(new BasicNameValuePair("Name", txtVillage.getText().toString().trim().length()==0?"0":txtVillage.getText().toString().trim()));
				lvp.add(new BasicNameValuePair("DistrictId", districtId));
				lvp.add(new BasicNameValuePair("TalukId", talukId));			
				lvp.add(new BasicNameValuePair("GPId", grmPamId));
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
					lststrVillageStart =  rfsr.Readfilename(rValue);
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
				if(lststrVillageStart.get(0).toString().contains("NACK"))
				{
					CustomToast.makeText(VillageMasterActivity.this, "Village Data Does Not Exist.", Toast.LENGTH_SHORT);
					ringProgress1.setCancelable(false); 
					ringProgress1.dismiss();

				}
				else if(lststrVillageStart.get(0).toString().contains("ACK"))
				{
					new SyncVillageMaster().execute();
				}												
				else
				{
					ringProgress1.setCancelable(false);
					ringProgress1.dismiss();
					CustomToast.makeText(VillageMasterActivity.this, "Error.", Toast.LENGTH_SHORT);

				}	
			}
			catch(Exception e)
			{
				ringProgress1.setCancelable(false);
				ringProgress1.dismiss();
				CustomToast.makeText(VillageMasterActivity.this, "Error Occured.", Toast.LENGTH_SHORT);
			}
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			ringProgress1.setCancelable(false); 
			ringProgress1.dismiss();	 
			CustomToast.makeText(VillageMasterActivity.this,"Error Occured/No Response from server", Toast.LENGTH_LONG);

		}
	}

	private class UpdateVillageMaster extends AsyncTask<Void, Void, Void> {
		final ProgressDialog ringProgress1 =	ProgressDialog.show(VillageMasterActivity.this, "Please wait..","Saving Data...",true);//Spinner
		String rValue = "";
		ArrayList<String> lststrVillageStart;
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpClient httpclt = new DefaultHttpClient();//object for HttpClient	
				HttpPost httpPost = new HttpPost(ConstantClass.IPAddress + "/Android_GetWaterSurvey");
				List<NameValuePair> lvp = new ArrayList<NameValuePair>(1);
				lvp.add(new BasicNameValuePair("Flag", "8"));
				lvp.add(new BasicNameValuePair("IMEINO", LoginActivity.IMEINumber));	
				lvp.add(new BasicNameValuePair("Id", "0"));
				lvp.add(new BasicNameValuePair("RRNO", "0"));	
				lvp.add(new BasicNameValuePair("Name", txtVillage.getText().toString().trim().length()==0?"0":txtVillage.getText().toString().trim()));
				lvp.add(new BasicNameValuePair("DistrictId", districtId));
				lvp.add(new BasicNameValuePair("TalukId", talukId));			
				lvp.add(new BasicNameValuePair("GPId", grmPamId));
				lvp.add(new BasicNameValuePair("VillageId", villageId));
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
					lststrVillageStart =  rfsr.Readfilename(rValue);
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
				if(lststrVillageStart.get(0).toString().contains("NACK"))
				{
					CustomToast.makeText(VillageMasterActivity.this, "Village Data Does Not Exist.", Toast.LENGTH_SHORT);
					ringProgress1.setCancelable(false); 
					ringProgress1.dismiss();

				}
				else if(lststrVillageStart.get(0).toString().contains("ACK"))
				{
					new SyncVillageMaster().execute();
				}												
				else
				{
					ringProgress1.setCancelable(false);
					ringProgress1.dismiss();
					CustomToast.makeText(VillageMasterActivity.this, "Error.", Toast.LENGTH_SHORT);

				}	
			}
			catch(Exception e)
			{
				ringProgress1.setCancelable(false);
				ringProgress1.dismiss();
				CustomToast.makeText(VillageMasterActivity.this, "Error Occured.", Toast.LENGTH_SHORT);
			}
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			ringProgress1.setCancelable(false); 
			ringProgress1.dismiss();	 
			CustomToast.makeText(VillageMasterActivity.this,"Error Occured/No Response from server", Toast.LENGTH_LONG);

		}
	}
	
	//////////////////////////Village Master/////////////////////////////////////
	private class SyncVillageMaster extends AsyncTask<Void, Void, Void> {
		final ProgressDialog ringProgress =	ProgressDialog.show(VillageMasterActivity.this, "Please wait..","Loading Data...",true);//Spinner
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
					CustomToast.makeText(VillageMasterActivity.this, "Could Not Sync Village .", Toast.LENGTH_SHORT);
					

				}
				else if(lststr.get(0).toString().contains("ACK"))
				{
				
					try 
					{
						int ret=  db.insertMasterData(lststr,4);					
						if (ret > 0)
						{

							ringProgress.setCancelable(false); 
							ringProgress.dismiss();
							CustomToast.makeText(VillageMasterActivity.this, "Data Sent SuccessFully!.", Toast.LENGTH_SHORT);
							Intent i = new Intent(VillageMasterActivity.this,VillageMasterActivity.class);
							startActivity(i);	
							finish();

						}
						else
						{

							CustomToast.makeText(VillageMasterActivity.this, "Could Not Sync Village Master.", Toast.LENGTH_SHORT);
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
					CustomToast.makeText(VillageMasterActivity.this, "Error.", Toast.LENGTH_SHORT);

				}	
			}
			catch(Exception e)
			{
				ringProgress.setCancelable(false);
				ringProgress.dismiss();
				CustomToast.makeText(VillageMasterActivity.this, "Error Occured.", Toast.LENGTH_SHORT);
			}
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			ringProgress.setCancelable(false); 
			ringProgress.dismiss();	 
			CustomToast.makeText(VillageMasterActivity.this,"Error Occured/No Response from server", Toast.LENGTH_LONG);

		}
	}
	


}
