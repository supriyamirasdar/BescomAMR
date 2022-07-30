package in.nsoft.bescomamr;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class TransformerMasterActivity extends Activity {

	Spinner ddlAllTransformer,ddlMasterTransformer;
	Button buttonAdd,buttonDrop;	
	DatabaseHelper db;
	String id,name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transformer_master);
		db = new DatabaseHelper(this);
		id = "0";
		name ="0";
		ddlAllTransformer = (Spinner) findViewById(R.id.ddlAllTransformer);
		ddlMasterTransformer = (Spinner) findViewById(R.id.ddlMasterTransformer);
		buttonAdd = (Button) findViewById(R.id.buttonAdd);
		buttonDrop = (Button) findViewById(R.id.buttonDrop);

		try {			
			ddlAllTransformer.setAdapter(db.getAllTransformerName());
			ddlMasterTransformer.setAdapter(db.getTransformerName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ddlAllTransformer.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try
				{
					DDLItem k = (DDLItem)  arg0.getItemAtPosition(arg2);
					id = k.getId();
					name = k.getValue();

				}
				catch (Exception e) {
					e.printStackTrace(); 
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		ddlMasterTransformer.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try
				{
					DDLItem k = (DDLItem)  arg0.getItemAtPosition(arg2);

				}
				catch (Exception e) {
					e.printStackTrace(); 
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		buttonAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try 
				{
					if(id.equals("-1"))
					{
						CustomToast.makeText(TransformerMasterActivity.this, "Please Select Transformer.", Toast.LENGTH_SHORT);
						return;
					}
					else
					{
						DDLAdapter dList = db.getTransformerName();						
						for(int i=0;i<dList.getCount();i++)
						{
							if(id.equals(dList.getItem(i).getId()))
							{
								CustomToast.makeText(TransformerMasterActivity.this, name + " Already Exists", Toast.LENGTH_LONG);
								return;
							}
						}						
						if(db.InsertTransformerTable(id, name) > 0)
						{
							ddlMasterTransformer.setAdapter(db.getTransformerName());
							CustomToast.makeText(TransformerMasterActivity.this, name + " Added", Toast.LENGTH_LONG);
							return;
						}
					}
											

				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		buttonDrop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try 
				{
					db.DropCreateTransformerTable();			
					CustomToast.makeText(TransformerMasterActivity.this, "Transformer Master Refreshed successfully.", Toast.LENGTH_SHORT);
					ddlMasterTransformer.setAdapter(db.getTransformerName());
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		return OptionsMenu.navigate(item,TransformerMasterActivity.this);			
	}

}
