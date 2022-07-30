package in.nsoft.bescomamr;


import java.math.BigDecimal;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ReportSurveyDetailsActivity extends Activity {
	ListView listReport;
	DatabaseHelper db = new DatabaseHelper(this);
	ArrayList<SurveyData> alRep;
	ReportAdapter adapter;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportsurveydetails);

		listReport =  (ListView)findViewById(R.id.listReport);

		try
		{
			alRep = db.GetReportSurveyData(); 

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
	public String getPhase(String phase)
	{	
		phase=(phase.equals("1")? "Single" : "Three");
		return phase;

	}
	public String getMeterMake(String MeterMake)
	{	


		/*if(MeterMake.equals("1")) MeterMake = "DC/NOMETER";
		else if(MeterMake.equals("2")) MeterMake = "L&T";
		else if(MeterMake.equals("8")) MeterMake = "L&G";
		else if(MeterMake.equals("38")) MeterMake = "HPL";
		else if(MeterMake.equals("13")) MeterMake = "TTL";
		else if(MeterMake.equals("3")) MeterMake = "SECURE";
		else if(MeterMake.equals("4")) MeterMake = "BHEL";
		else if(MeterMake.equals("5")) MeterMake = "ACTARIS";
		else if(MeterMake.equals("7")) MeterMake = "AVON";
		else if(MeterMake.equals("9")) MeterMake = "ISKRA";
		else if(MeterMake.equals("10")) MeterMake = "I M";
		else if(MeterMake.equals("11")) MeterMake = "SIEMENS";
		else if(MeterMake.equals("12")) MeterMake = "R.C";
		else if(MeterMake.equals("14")) MeterMake = "PRECESITION";
		else if(MeterMake.equals("15")) MeterMake = "LANDIS";
		else if(MeterMake.equals("19")) MeterMake = "ELYMER";
		else if(MeterMake.equals("17")) MeterMake = "CAPITAL";
		else if(MeterMake.equals("18")) MeterMake = "HAVELLS";
		else if(MeterMake.equals("21")) MeterMake = "OMANI";
		else if(MeterMake.equals("22")) MeterMake ="ACCURATE";
		else if(MeterMake.equals("24")) MeterMake = "BHEK:()";
		else if(MeterMake.equals("26")) MeterMake = "OLAY";
		else if(MeterMake.equals("27")) MeterMake = "DATAK";
		else if(MeterMake.equals("28")) MeterMake = "ALSTOM";
		else if(MeterMake.equals("29")) MeterMake = "EMCO";
		else if(MeterMake.equals("31")) MeterMake = "HIL";
		else if(MeterMake.equals("32")) MeterMake = "INDOTECK";			
		else if(MeterMake.equals("33")) MeterMake = "INDOTECH";
		else if(MeterMake.equals("34")) MeterMake = "GENUS";
		else if(MeterMake.equals("35")) MeterMake = "HTL";
		else if(MeterMake.equals("36")) MeterMake = "ZCE";
		else if(MeterMake.equals("37")) MeterMake = "RAMCO&G";			
		else MeterMake = "";			*/

		if(MeterMake.equals("100")) MeterMake = "EDMI";
		else if(MeterMake.equals("101")) MeterMake = "ITRON";
		else if(MeterMake.equals("102")) MeterMake = "MIM";
		else if(MeterMake.equals("103")) MeterMake = "KRIZIK";
		else if(MeterMake.equals("104")) MeterMake = "SMT";
		else MeterMake = "";

		return MeterMake;

	}
	public String getModel(String model)
	{		
		if(model.equals("1")) model = "L&T";
		else if(model.equals("2")) model = "LGzce";
		else if(model.equals("3")) model = "Saral";
		else if(model.equals("4")) model = "LG110c";
		else if(model.equals("5")) model = "Secure3ph";
		else if(model.equals("6")) model = "LG DLMS";
		else if(model.equals("7")) model = "LG3ph";
		else if(model.equals("8")) model = "LT1ph";
		else if(model.equals("9")) model = "LT3ph";
		else if(model.equals("10")) model = "HPL1ph";
		else if(model.equals("11")) model = "HPL3ph";
		else if(model.equals("12")) model = "Icredit 1ph";
		else if(model.equals("13")) model = "CAP DLMS 1ph";
		else if(model.equals("14")) model = "CAP DLMS 3ph";
		else model = "";
		return model;		
	}
	public String getMeterType(String mtrType)
	{	
		if(mtrType.equals("1")) mtrType = "Electronic";
		else if(mtrType.equals("2")) mtrType = "Mechanical";
		else mtrType = "";
		return mtrType;

	}	
	public String getTypeOfBox(String typeOfBox)
	{
		typeOfBox=(typeOfBox.equals("1")? "Metallic" : (typeOfBox.equals("2") ? "Plastic" : " "));		
		return typeOfBox;		
	}

	public String getMeterPlacement(String mtrPlacement)
	{	
		mtrPlacement=(mtrPlacement.equals("1")? "Inside" : (mtrPlacement.equals("2") ? "Outside" : " "));	
		return mtrPlacement;

	}
	public String getProtocol(String protocol)
	{	
		if(protocol.equals("0"))
		{
			protocol = " ";
		}
		else
		{
			protocol=(protocol.equals("1")? "DLMS" : (protocol.equals("2") ? "Propietry" : " "));
		}
		return protocol;

	}
	public String getFloor(String floor)
	{		


		if(floor.equals("1")) floor = "Basement-1";
		else if(floor.equals("2")) floor = "Basement-2";
		else if(floor.equals("3")) floor = "Basement-3";
		else if(floor.equals("4")) floor = "Basement-4";
		else if(floor.equals("5")) floor = "Ground";
		else if(floor.equals("6")) floor = "First";
		else if(floor.equals("7")) floor = "Second";
		else if(floor.equals("8")) floor = "Third";
		else if(floor.equals("9")) floor = "Fourth";
		else if(floor.equals("10")) floor = "Fifth";
		else if(floor.equals("11")) floor = "Other";
		else floor = "";
		return floor;

	}
	public String getTranformer(String transformerid)
	{	
		String transformername = "";
		DDLAdapter dList;
		try {
			dList = db.getTransformerName();
			for(int i=0;i<dList.getCount();i++)
			{
				if(transformerid.equals(dList.getItem(i).getId()))
				{
					transformername = dList.getItem(i).getValue();

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						

		return transformername;

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
		return OptionsMenu.navigate(item,ReportSurveyDetailsActivity.this);			
	}	

	//Class for List View
	private class ReportAdapter extends ArrayAdapter<SurveyData>
	{

		public ReportAdapter(ArrayList<SurveyData> mReportList) {		
			super(ReportSurveyDetailsActivity.this, R.layout.surveydetailslist, mReportList);		

		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) // If we weren't given a view, inflate one
		{
			try
			{			
				SurveyData rb = getItem(position);
				if (convertView == null) {
					convertView = ReportSurveyDetailsActivity.this.getLayoutInflater().inflate(R.layout.surveydetailslist, null);					
				}	
				// Set List View Parameters
				TextView txtId,txtAccNo,txtPhase,txtConnectionType,txtMeterBoxAvailability,txtTypeOfBox,txtMeterMake,
				txtMeterSlNo,txtYearOfManufacture,txtMeterPlacement,txtComRJ11,txtComOptical,txtFloor,
				txtPresentReading,txtRemarks,txtCapturedOn,txtNetworkStrength;

				txtId = (TextView)convertView.findViewById(R.id.txtId);		
				txtAccNo = (TextView)convertView.findViewById(R.id.txtAccNo);	
				txtPhase = (TextView)convertView.findViewById(R.id.txtPhase);		
				txtConnectionType = (TextView)convertView.findViewById(R.id.txtConnectionType);		
				txtMeterBoxAvailability = (TextView)convertView.findViewById(R.id.txtMeterBoxAvailability);		
				txtTypeOfBox = (TextView)convertView.findViewById(R.id.txtTypeOfBox);		
				txtMeterMake = (TextView)convertView.findViewById(R.id.txtMeterMake);		
				txtMeterSlNo = (TextView)convertView.findViewById(R.id.txtMeterSlNo);		
				txtYearOfManufacture = (TextView)convertView.findViewById(R.id.txtYearOfManufacture);		
				txtMeterPlacement = (TextView)convertView.findViewById(R.id.txtMeterPlacement);		
				txtComRJ11 = (TextView)convertView.findViewById(R.id.txtComRJ11);		
				txtComOptical = (TextView)convertView.findViewById(R.id.txtComOptical);		
				txtFloor = (TextView)convertView.findViewById(R.id.txtFloor);	
				txtPresentReading = (TextView)convertView.findViewById(R.id.txtPresentReading);	
				txtRemarks = (TextView)convertView.findViewById(R.id.txtRemarks);		
				txtCapturedOn = (TextView)convertView.findViewById(R.id.txtCapturedOn);		
				txtNetworkStrength = (TextView)convertView.findViewById(R.id.txtNetworkStrength);	

				txtId.setText(rb.getmConnID()==null ? "" : rb.getmConnID());
				txtAccNo.setText(rb.getmRRNo()==null ? "" : rb.getmRRNo());
				txtPhase.setText(rb.getmPhase()==null ? "" : getPhase(rb.getmPhase()));
				txtConnectionType.setText(rb.getmConnectionType()==null ? "" : getConnType(rb.getmConnectionType()));
				txtMeterBoxAvailability.setText(rb.getmMeterBoxAvailability()==null ? "" : (rb.getmMeterBoxAvailability().equals("1") ? "YES" : "NO"));
				txtTypeOfBox.setText(rb.getmTypeOfBox()==null ? "" : getTypeOfBox(rb.getmTypeOfBox()));
				txtMeterMake.setText(rb.getmMake()==null ? "" : getMeterMake(rb.getmMake()));
				txtMeterSlNo.setText(rb.getmMeterSlNo()==null ? "" : rb.getmMeterSlNo());
				txtYearOfManufacture.setText(rb.getmYearofManufacture()==null ? "" : rb.getmYearofManufacture());
				txtMeterPlacement.setText(rb.getmMeterPlacement()==null ? "" : getMeterPlacement(rb.getmMeterPlacement()));
				txtComRJ11.setText(rb.getmComRJ11()==null ? "" : (rb.getmComRJ11().equals("1") ? "YES" : "NO"));

				String optical  = rb.getmComOptical()==null ? "" : (rb.getmComOptical().equals("1") ? "YES" : "NO");
				String protocol = rb.getmProtocol()==null ? "" : getProtocol(rb.getmProtocol());
				

				txtComOptical.setText(optical + "," + protocol);
				txtFloor.setText(rb.getMfloor()==null ? "" : getFloor(rb.getMfloor()));
				txtCapturedOn.setText(rb.getmDateTime()==null ? "" : DateConvert(rb.getmDateTime()));
				txtNetworkStrength.setText(rb.getmTransFormerName()==null ? "" : rb.getmTransFormerName());
				try
				{
					String reading = rb.getmRemarks().split("\\|")[1].trim();				
					String remarks  = rb.getmRemarks().split("\\|")[2].trim();
					txtPresentReading.setText(reading);
					txtRemarks.setText(remarks);
				}
				catch(Exception e)
				{
					txtPresentReading.setText(rb.getmRemarks()==null ? "" : rb.getmRemarks());
					txtRemarks.setText(rb.getmRemarks()==null ? "" : rb.getmRemarks());
				}

			}		
			catch(Exception e)
			{
				Toast.makeText(ReportSurveyDetailsActivity.this, "Error View " , Toast.LENGTH_LONG).show();

			}
			return convertView;
		}
	}
	@Override
	public void onResume() {
		super.onResume();	
		adapter.notifyDataSetChanged();
	}

	public String DateConvert(String date) //yyyy-MM-dd HH:mm:ss
	{
		String NewFormat = date;
		try
		{
			String mDate = date.substring(0,10);
			String mTime = date.substring(10,date.length());
			String sp[] = mDate.split("-");
			mDate = sp[2] + "-" + sp[1] + "-" + sp[0];
			NewFormat = mDate + " " + mTime;

			return NewFormat;
		}
		catch(Exception e)
		{

		}
		return NewFormat;
	}

}
