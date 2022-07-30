package in.nsoft.bescomamr;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReportTCWiseSurveyActivity extends Activity {
	
	private final DatabaseHelper mDb =new DatabaseHelper(this);
	Handler dh ;
	ArrayList<ReportTCwise> alRep ;
	ReportAdapter adapter;
	TextView texttotal,total1;
	int surveydone = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_tcwise_survey);
		texttotal=(TextView)findViewById(R.id.texttotal);
		total1=(TextView)findViewById(R.id.total1);
		dh=new Handler();
		texttotal.setText(mDb.GetCountSurveyDone());
		
		
		alRep = new  ArrayList <ReportTCwise>();
		try {
			alRep = mDb.GetReportTCwise();
			
			if(alRep.size() ==0)
			{
				CustomToast.makeText(this, "TC Wise Data does not Exist", Toast.LENGTH_SHORT);
				//btntariffPrint.setVisibility(View.INVISIBLE);				
			}

			else
			{
				for(int i = 0;i<alRep.size() ; i++)
				{

					surveydone = surveydone + Integer.valueOf(alRep.get(i).getSurveyDone());//30-08-2016
				}
				//int Demand= Integer.valueOf((sumDemand).toString());
				total1.setText(String.valueOf(surveydone));//30-08-2016
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	

		//To get List Parameters
		adapter = new ReportAdapter(alRep);			
		ListView lv = (ListView) findViewById(R.id.lsttariff);		
		lv.setAdapter(adapter);
	}
	
	
	
	//Class for List View
		private class ReportAdapter extends ArrayAdapter<ReportTCwise>
		{

			public ReportAdapter(ArrayList<ReportTCwise> mReportList) {		
				super(ReportTCWiseSurveyActivity.this, R.layout.twoitemlist, mReportList);		

			}
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) // If we weren't given a view, inflate one
			{
				try
				{		
					ReportTCwise rb = getItem(position);
					if (convertView == null) {
						convertView = ReportTCWiseSurveyActivity.this.getLayoutInflater().inflate(R.layout.twoitemlist, null);					
					}
					// Set List View Parameters
					TextView lblTransformername=(TextView)convertView.findViewById(R.id.lblTransformername);
					TextView lblSurveyDone = (TextView)convertView.findViewById(R.id.lblSurveyDone);
					

					String Transformername=rb.getTransformername()== null ? "" : rb.getTransformername();
					String SurveyDone = rb.getSurveyDone()== null ? "" : rb.getSurveyDone();
				
					lblTransformername.setText(Transformername);
					lblSurveyDone.setText(SurveyDone);
				

				}		
				catch(Exception e)
				{ 
					if(e.toString().length()>15)
					{
						e.toString().substring(0,13);

					}
					else
					{			

					}
					Toast.makeText(ReportTCWiseSurveyActivity.this, "Error View " , Toast.LENGTH_LONG).show();			
				}
				return convertView;
			}
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
		return OptionsMenu.navigate(item,ReportTCWiseSurveyActivity.this);			
	}

}
