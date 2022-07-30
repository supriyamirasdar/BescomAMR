package in.nsoft.bescomamr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ReportGPRSSurveyActivity extends Activity implements AlertInterface{

	private TextView  surveyDone, GPRSSent, GPRSNSent;

	ImageView imgRefresh;
	DatabaseHelper db = new DatabaseHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_gprssurvey);

		try
		{
			
			surveyDone = (TextView) findViewById(R.id.txtReportSurveydone);
			imgRefresh=(ImageView) findViewById(R.id.imgRefresh);
			GPRSSent = (TextView) findViewById(R.id.txtReportGPRSSent);
			GPRSNSent = (TextView) findViewById(R.id.txtReportGPRSNotSent);

			
			if(ConstantClass.mSurveyType.equals("2"))
			{
				surveyDone.setText(db.GetCountWaterSurveyDone());
				GPRSSent.setText(db.GetCountWaterSurveyGPRSSent());	
				GPRSNSent.setText(String.valueOf(Integer.parseInt(db.GetCountWaterSurveyDone()) - Integer.parseInt(db.GetCountWaterSurveyGPRSSent()))); //GPRS Not Sent = (Receipts - GPRS Sent)

			}
			else
			{
				surveyDone.setText(db.GetCountSurveyDone());
				GPRSSent.setText(db.GetCountSurveyGPRSSent());	
				GPRSNSent.setText(String.valueOf(Integer.parseInt(db.GetCountSurveyDone()) - Integer.parseInt(db.GetCountSurveyGPRSSent()))); //GPRS Not Sent = (Receipts - GPRS Sent)

			}
		}
		catch(Exception e)
		{

		}




		imgRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				CustomToast.makeText(ReportGPRSSurveyActivity.this, "Refresh of GPRS Report Complete.", Toast.LENGTH_SHORT);
				onCreate(new Bundle()); //Refresh or Load Again					
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
		return OptionsMenu.navigate(item,ReportGPRSSurveyActivity.this);		
	}
	@Override
	public void performAction(boolean alertResult, int functionality) {
		// TODO Auto-generated method stub

	}

}
