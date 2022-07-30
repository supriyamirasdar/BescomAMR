package in.nsoft.bescomamr;



import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReportSurveyWaterActivity extends Activity {


	ListView listReport;
	DatabaseHelper db = new DatabaseHelper(this);
	ArrayList<SurveyData> alRep;
	ReportAdapter adapter;	
	String tankname = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_survey_water);


		listReport =  (ListView)findViewById(R.id.listReport);


		try
		{
			alRep = db.GetReportWaterSurveyData(); 

			if(alRep.size() ==0)
			{
				CustomToast.makeText(this, "This Report Does Not Contain Data", Toast.LENGTH_SHORT);

			}
			//To get List Parameters
			adapter = new ReportAdapter(alRep);			
			listReport = (ListView) findViewById(R.id.listReport);		
			listReport.setAdapter(adapter);				

		}
		catch(Exception e)
		{

		}


	}

	public String getConnType(String contype)
	{	
		contype=(contype.equals("1")? "Metered" : (contype.equals("2") ? "UnMetererd" : " "));
		return contype;

	}

	public String getMeterPhase(String meterphase)
	{
		meterphase=(meterphase.equals("1")? "Single" : (meterphase.equals("2") ? "Three-CT" :(meterphase.equals("3") ? "Three-NonCT" : " ")));		
		return meterphase;		
	}

	public String getMeterType(String metertype)
	{	
		metertype=(metertype.equals("1")? "DLMS" : (metertype.equals("2") ? "NON DLMS" : " "));
		return metertype;

	}

	public String getBoreWell(String borewell)
	{
		borewell=(borewell.equals("1")? "Not Present" : (borewell.equals("2") ? "Working" :(borewell.equals("3") ? "Not Working" : " ")));		
		return borewell;		
	}

	public String getConnStatus(String Connstatus)
	{	
		Connstatus=(Connstatus.equals("1")? "Active" : (Connstatus.equals("2") ? "InActive" : " "));
		return Connstatus;

	}

	public String getMeterMakeName(String metermakeId)
	{	
		String metermakename = "";
		DDLAdapter dList;
		try {
			dList = db.getMeterMakeHESCOM();
			for(int i=0;i<dList.getCount();i++)
			{
				if(metermakeId.equals(dList.getItem(i).getId()))
				{
					metermakename = dList.getItem(i).getValue();

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						

		return metermakename;

	}


	public String getPipeTypeofBorewell(String PipeTypeofBorewell)
	{
		PipeTypeofBorewell=(PipeTypeofBorewell.equals("1")? "Metallic" : (PipeTypeofBorewell.equals("2") ? "PVC" :(PipeTypeofBorewell.equals("3") ? "CPVC" : " ")));		
		return PipeTypeofBorewell;		
	}


	public String getTypeOfSupply(String typeofSupply)
	{	
		typeofSupply=(typeofSupply.equals("1")? "Overhead" : (typeofSupply.equals("2") ? "Direct" : " "));
		return typeofSupply;

	}

	public String getWaterSourceName(String WaterSourceid)
	{	
		String watersourcename = "";
		DDLAdapter dList;
		try {
			dList = db.getWaterSource();
			for(int i=0;i<dList.getCount();i++)
			{
				if(WaterSourceid.equals(dList.getItem(i).getId()))
				{
					watersourcename = dList.getItem(i).getValue();

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						

		return watersourcename;

	}

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
		return OptionsMenu.navigate(item,ReportSurveyWaterActivity.this);			
	}	

	//Class for List View
	private class ReportAdapter extends ArrayAdapter<SurveyData>
	{

		public ReportAdapter(ArrayList<SurveyData> mReportList) {		
			super(ReportSurveyWaterActivity.this, R.layout.surveywaterdetailslist, mReportList);		

		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) // If we weren't given a view, inflate one
		{
			try
			{			
				SurveyData rb = getItem(position);
				if (convertView == null) {
					convertView = ReportSurveyWaterActivity.this.getLayoutInflater().inflate(R.layout.surveywaterdetailslist, null);					
				}	
				// Set List View Parameters
				TextView txtId,txtAccNo,txtDistrict,txtTaluk,txtGramPanchayat,txtVillage,txtMeterStatus,txtMeterPhase,txtMeterMake,txtMeterSlno,txtMeterType,
				txtWaterSource,txtBorewell,txtBorewellDepth,txtConnStatus,txtSancLoad,txtPumpCapacity,txtPipeDimension,txtTyprOfPiprBorewell,
				txtTypeOfSupply,txtTankName,txtNoOfOutlets,txtControlValue,txtTypeofpipeOutlets,txtTankDimension,txtTankCapacity,txtDistance,txtWaterMan,txtContactNo,txtSimType,txtRemarks;

				txtId = (TextView)convertView.findViewById(R.id.txtId);		
				txtAccNo = (TextView)convertView.findViewById(R.id.txtAccNo);	
				txtDistrict = (TextView)convertView.findViewById(R.id.txtDistrict);		
				txtTaluk = (TextView)convertView.findViewById(R.id.txtTaluk);		
				txtGramPanchayat = (TextView)convertView.findViewById(R.id.txtGramPanchayat);		
				txtVillage = (TextView)convertView.findViewById(R.id.txtVillage);		
				txtMeterStatus = (TextView)convertView.findViewById(R.id.txtMeterStatus);
				txtMeterPhase = (TextView)convertView.findViewById(R.id.txtMeterPhase);
				txtMeterMake = (TextView)convertView.findViewById(R.id.txtMeterMake);
				txtMeterSlno = (TextView)convertView.findViewById(R.id.txtMeterSlno);
				txtMeterType = (TextView)convertView.findViewById(R.id.txtMeterType);

				txtWaterSource = (TextView)convertView.findViewById(R.id.txtWaterSource);		
				txtBorewell = (TextView)convertView.findViewById(R.id.txtBorewell);		
				txtBorewellDepth = (TextView)convertView.findViewById(R.id.txtBorewellDepth);		
				txtConnStatus = (TextView)convertView.findViewById(R.id.txtConnStatus);		
				txtSancLoad = (TextView)convertView.findViewById(R.id.txtSancLoad);		
				txtPumpCapacity = (TextView)convertView.findViewById(R.id.txtPumpCapacity);	
				txtPipeDimension = (TextView)convertView.findViewById(R.id.txtPipeDimension);	
				txtTyprOfPiprBorewell = (TextView)convertView.findViewById(R.id.txtTyprOfPiprBorewell);	

				txtTypeOfSupply = (TextView)convertView.findViewById(R.id.txtTypeOfSupply);	
				txtTankName = (TextView)convertView.findViewById(R.id.txtTankName);	
				txtNoOfOutlets = (TextView)convertView.findViewById(R.id.txtNoOfOutlets);
				txtControlValue = (TextView)convertView.findViewById(R.id.txtControlValue);	
				txtTypeofpipeOutlets = (TextView)convertView.findViewById(R.id.txtTypeofpipeOutlets);	
				txtTankDimension = (TextView)convertView.findViewById(R.id.txtTankDimension);	
				txtTankCapacity = (TextView)convertView.findViewById(R.id.txtTankCapacity);	
				txtDistance = (TextView)convertView.findViewById(R.id.txtDistance);	
				txtWaterMan = (TextView)convertView.findViewById(R.id.txtWaterMan);	
				txtContactNo = (TextView)convertView.findViewById(R.id.txtContactNo);	
				txtSimType = (TextView)convertView.findViewById(R.id.txtSimType);	
				txtRemarks = (TextView)convertView.findViewById(R.id.txtRemarks);		


				txtId.setText(rb.getmConnID()==null ? "" : rb.getmConnID());
				txtAccNo.setText(rb.getmRRNo()==null ? "" : rb.getmRRNo());
				txtDistrict.setText(rb.getmDistrictId()==null ? "" : db.getMasterName(1, rb.getmDistrictId()));
				txtTaluk.setText(rb.getmTalukId()==null ? "" : db.getMasterName(2, rb.getmTalukId()));
				txtGramPanchayat.setText(rb.getmGPId()==null ? "" : db.getMasterName(3, rb.getmGPId()));
				txtVillage.setText(rb.getmVillageId()==null ? "" : db.getMasterName(4, rb.getmVillageId()));
				txtMeterStatus.setText(rb.getmMeterStatus()==null ? "" : getConnType(rb.getmMeterStatus()));
				txtMeterPhase.setText(rb.getmMeterPhase()==null ? "" : getMeterPhase(rb.getmMeterPhase()));
				txtMeterMake.setText(rb.getmMake()==null ? "" : getMeterMakeName(rb.getmMake()));
				txtMeterSlno.setText(rb.getmMeterSlNo()==null ? "" : rb.getmMeterSlNo());
				txtMeterType.setText(rb.getmMeterType()==null ? "" : getMeterType(rb.getmMeterType()));
				txtWaterSource.setText(rb.getmWaterSource()==null ? "" : getWaterSourceName(rb.getmWaterSource()));
				txtBorewell.setText(rb.getmBorewell()==null ? "" : getBoreWell(rb.getmBorewell()));
				txtBorewellDepth.setText(rb.getmDepth()==null ? "" : rb.getmDepth());
				txtConnStatus.setText(rb.getmConnectionStatus()==null ? "" : getConnStatus(rb.getmConnectionStatus()));
				txtSancLoad.setText(rb.getmSancLoad()==null ? "" : rb.getmSancLoad());
				txtPumpCapacity.setText(rb.getmPumpCapacity()==null ? "" : rb.getmPumpCapacity());
				txtPipeDimension.setText(rb.getmPipeDimension()==null ? "" : rb.getmPipeDimension());
				txtTyprOfPiprBorewell.setText(rb.getmPipeTypeBorewell()==null ? "" : getPipeTypeofBorewell(rb.getmPipeTypeBorewell()));
				txtTypeOfSupply.setText(rb.getmTypeOfSupply()==null ? "" : getTypeOfSupply(rb.getmTypeOfSupply()));
				try
				{

					String tanks[] = rb.getmTankId().split("#");
					tankname = "";
					for(int i =0 ;i<tanks.length;i++)
					{
						if(i==0)
						{
							tankname =  db.getMasterName(5, tanks[i]);
						}
						else
						{
							tankname = tankname + "," + db.getMasterName(5, tanks[i]);
						}
					}
				}
				catch(Exception e)
				{

				}
				txtTankName.setText(rb.getmTankId()==null ? "" : tankname);
				txtNoOfOutlets.setText(rb.getmNoOfOutlets()==null ? "" : rb.getmNoOfOutlets());
				txtControlValue.setText(rb.getmControlValve()==null ? "" : rb.getmControlValve());
				txtTypeofpipeOutlets.setText(rb.getmPipeTypeOutlet()==null ? "" : getPipeTypeofBorewell(rb.getmPipeTypeOutlet()));
				txtTankDimension.setText(rb.getmTankDimensionsLength()==null ? "" : rb.getmTankDimensionsLength());
				txtTankCapacity.setText(rb.getmTankCapacity()==null ? "" : rb.getmTankCapacity());
				txtDistance.setText(rb.getmDistance()==null ? "" : rb.getmDistance());
				txtWaterMan.setText(rb.getmWaterManName()==null ? "" : rb.getmWaterManName());
				txtContactNo.setText(rb.getmContactNo()==null ? "" : rb.getmContactNo());
				txtSimType.setText(rb.getmTypeOfSim()==null ? "" : rb.getmTypeOfSim());
				txtRemarks.setText(rb.getmRemarks()==null ? "" : rb.getmRemarks());

			}		
			catch(Exception e)
			{
				Toast.makeText(ReportSurveyWaterActivity.this, "Error View " , Toast.LENGTH_LONG).show();

			}
			return convertView;
		}
	}
}
