package in.nsoft.bescomamr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment; 
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VideoRecordActivity extends Activity {

	TextView StatusVideoCaptured;
	Button btnProceed,btnCaptureVideo;
	private Uri fileUri;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	public static VideoRecordActivity ActivityContext =null; 
	public static File mediaFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_record);

		StatusVideoCaptured = (TextView)findViewById(R.id.StatusVideoCaptured);
		btnProceed = (Button)findViewById(R.id.btnProceed);
		btnCaptureVideo = (Button)findViewById(R.id.btnCaptureVideo);
		ActivityContext = this;
		btnProceed.setVisibility(View.GONE);
		
		btnProceed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//17-07-2021 for Signal Strength			
				Billing.signalStrength = Billing.signalStrength + "/" + CommonFunction.getSignalStrength(VideoRecordActivity.this);
				CustomToast.makeText(VideoRecordActivity.this, "Signal Strength : " + Billing.signalStrength ,  Toast.LENGTH_LONG);
				//End 17-07-2021		
				
				Intent i = new Intent(VideoRecordActivity.this, SurveyDetailsActivity.class);
				i.putExtra("barcodevalue", "");
				startActivity(i);
				finish();
				
				
			}
		});
		btnCaptureVideo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//17-07-2021 for Signal Strength			
				Billing.signalStrength = Billing.signalStrength + "/" + CommonFunction.getSignalStrength(VideoRecordActivity.this);
				//End 17-07-2021
				
				btnProceed.setVisibility(View.GONE);
				
				Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO); 
				//fileUri =  getVideoFile();
				//fileUri =ImageSaveinSDCard();
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); 
				intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); 
				startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
				
			}
		});


	
	}
	
	

	private static Uri getOutputMediaFileUri(int type){

		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type){



		// Check that the SDCard is mounted
		CommonFunction cFun = new CommonFunction();
		File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath());
		String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		File f = new File(mediaStorageDir+"/AMRSurvey/"+timeStamp+"/Video/");

		// Create the storage directory(MyCameraVideo) if it does not exist
		if (! new File(mediaStorageDir+"/AMRSurvey/"+timeStamp+"/Video").exists()){

			if (!new File(mediaStorageDir+"/AMRSurvey/"+timeStamp+"/Video").mkdirs()){

				//Toast.makeText(ActivityContext, "Failed to create directory AMRSurvey.", Toast.LENGTH_LONG).show();
				CustomToast.makeText(ActivityContext, "Failed to create directory AMRSurvey.", Toast.LENGTH_LONG);					
				 
				Log.d("AMRSurvey", "Failed to create directory AMRSurvey.");
				return null;
			}
		}



		// For unique file name appending current timeStamp with file name
		java.util.Date date= new java.util.Date();
		String DatetimeStamp = new SimpleDateFormat("ddMMyyyy").format(date.getTime());
		//File mediaFile;

		if(type == MEDIA_TYPE_VIDEO) {

			// For unique video file name appending current timeStamp with file name
			mediaFile = new File(f  ,LoginActivity.IMEINumber + "_"+BillingObject.GetBillingObject().getmConnectionNo() + "_"	+ DatetimeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// After camera screen this code will excuted

		if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {

			if (resultCode == RESULT_OK) {

				//output.setText("Video File : " +data.getData());
				StatusVideoCaptured.setText("Video Captured");
				// Video captured and saved to fileUri specified in the Intent
				//Toast.makeText(this, "Video saved to:" +data.getData(), Toast.LENGTH_LONG).show();
				CustomToast.makeText(this, "Video Captured." , Toast.LENGTH_LONG);		
				btnProceed.setVisibility(View.VISIBLE);
				return;
				//Intent i = new Intent(VideoRecordActivity.this, SurveydatadetailsActivity.class);		
				//startActivity(i);	

			} else if (resultCode == RESULT_CANCELED) {
				if(mediaFile.exists())
					mediaFile.delete();
				// User cancelled the video capture
				StatusVideoCaptured.setText("Video Capture Cancelled.");
				//Toast.makeText(this, "User cancelled the video capture.",Toast.LENGTH_LONG).show();
				CustomToast.makeText(this, "Video Capture Cancelled.", Toast.LENGTH_LONG);		
				btnProceed.setVisibility(View.GONE);
				return;
				


			} else {
				// Video capture failed, advise user
				Toast.makeText(this, "Video capture failed.", 
						Toast.LENGTH_LONG).show();
			}
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
		return OptionsMenu.navigate(item,VideoRecordActivity.this);			
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		CustomToast.makeText(VideoRecordActivity.this, "Please use menu to navigate.", Toast.LENGTH_SHORT);
		return;
	}
}
