package in.nsoft.bescomamr;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageCaptureActivity extends Activity {
	
	Button btnPhoto1,btnPhoto2,btnPhoto3,btnPhoto4,btnProceed;
	ImageView image1,image2,image3,image4;
	ImageView photoView;
	boolean isIdImageCaptured1,isIdImageCaptured2,isIdImageCaptured3,isIdImageCaptured4;
	
	Handler mainThreadHandler;	
	DatabaseHelper db;	
	static SurveyDetails SD;
	static String imgPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_capture);
		
		
		btnPhoto1 = (Button)findViewById(R.id.btnPhoto1);
		btnPhoto2 = (Button)findViewById(R.id.btnPhoto2);
		btnPhoto3 = (Button)findViewById(R.id.btnPhoto3);
		btnPhoto4 = (Button)findViewById(R.id.btnPhoto4);
		btnProceed =(Button)findViewById(R.id.btnProceed);
		image1 = (ImageView)findViewById(R.id.image1);
		image2 = (ImageView)findViewById(R.id.image2);
		image3 = (ImageView)findViewById(R.id.image3);
		image4 = (ImageView)findViewById(R.id.image4);
		
		
		db = new DatabaseHelper(ImageCaptureActivity.this);
		mainThreadHandler = new Handler();
		SD = new SurveyDetails();
		
		
		
		//////////////////////////////////Image1 /////////////////////////////	
		btnPhoto1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selectImage(1);

			}
		});
		image1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder mBuilder = new AlertDialog.Builder(ImageCaptureActivity.this);
				View convertView = ImageCaptureActivity.this.getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);	
				mBuilder.setView(convertView);	               
				photoView = (ImageView)convertView.findViewById(R.id.imageView);
				// photoView.setImageResource(imgbtp);
				photoView.setImageDrawable(image1.getDrawable());
				mBuilder.setView(convertView);
				AlertDialog mDialog = mBuilder.create();
				mDialog.show();						
			}
		});
		///////////////////////////////////////
		
		//////////////////////////////////Image2/////////////////////////////	
		btnPhoto2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selectImage(2);

			}
		});
		image2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder mBuilder = new AlertDialog.Builder(ImageCaptureActivity.this);
				View convertView = ImageCaptureActivity.this.getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);	
				mBuilder.setView(convertView);	               
				photoView = (ImageView)convertView.findViewById(R.id.imageView);
				// photoView.setImageResource(imgbtp);
				photoView.setImageDrawable(image2.getDrawable());
				mBuilder.setView(convertView);
				AlertDialog mDialog = mBuilder.create();
				mDialog.show();						
			}
		});
		///////////////////////////////////////
		
		//////////////////////////////////Image3/////////////////////////////	
		btnPhoto3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selectImage(3);

			}
		});
		image3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder mBuilder = new AlertDialog.Builder(ImageCaptureActivity.this);
				View convertView = ImageCaptureActivity.this.getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);	
				mBuilder.setView(convertView);	               
				photoView = (ImageView)convertView.findViewById(R.id.imageView);
				// photoView.setImageResource(imgbtp);
				photoView.setImageDrawable(image3.getDrawable());
				mBuilder.setView(convertView);
				AlertDialog mDialog = mBuilder.create();
				mDialog.show();						
			}
		});
		///////////////////////////////////////
		
		//////////////////////////////////Image4/////////////////////////////	
		btnPhoto4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selectImage(4);

			}
		});
		image4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder mBuilder = new AlertDialog.Builder(ImageCaptureActivity.this);
				View convertView = ImageCaptureActivity.this.getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);	
				mBuilder.setView(convertView);	               
				photoView = (ImageView)convertView.findViewById(R.id.imageView);
				// photoView.setImageResource(imgbtp);
				photoView.setImageDrawable(image4.getDrawable());
				mBuilder.setView(convertView);
				AlertDialog mDialog = mBuilder.create();
				mDialog.show();						
			}
		});
		///////////////////////////////////////

		btnProceed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!isIdImageCaptured1)
				{
					CustomToast.makeText(ImageCaptureActivity.this, "Please captured photo 1.", Toast.LENGTH_SHORT);
					return;
				}
				else if(!isIdImageCaptured2)
				{
					CustomToast.makeText(ImageCaptureActivity.this, "Please captured photo 2.", Toast.LENGTH_SHORT);
					return;
				}
				else if(!isIdImageCaptured3)
				{
					CustomToast.makeText(ImageCaptureActivity.this, "Please captured photo 3.", Toast.LENGTH_SHORT);
					return;
				}
				else if(!isIdImageCaptured4)
				{
					CustomToast.makeText(ImageCaptureActivity.this, "Please captured photo 4.", Toast.LENGTH_SHORT);
					return;
				}

				Intent  i = new Intent(ImageCaptureActivity.this,SurveyWaterActivity.class);
				startActivity(i);
				
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
		return OptionsMenu.navigate(item,ImageCaptureActivity.this);		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		CustomToast.makeText(ImageCaptureActivity.this, "Please use menu to navigate.", Toast.LENGTH_SHORT);
		return;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			String currentdatetime = new SimpleDateFormat("ddMMyyyyHHmmss").format(Calendar.getInstance().getTime());
			String picturePath = Environment.getExternalStorageDirectory().getPath()+"/AMRSurvey/Photos";
			picturePath = picturePath+"/img.jpg";


			if(requestCode == 1) 
			{
				isIdImageCaptured1 =  true;
				//String imagename = SplashScreenActivity.IMEINumber +"_CHEQUEPROOF_" +db.getMaxIdChequeCollection()+ "_" +currentdatetime + ".jpg";
				String imagename ="IMG1.jpg";
				SD.setTempIamge(imagename);
				int save = saveTempImage(imagename,picturePath);
				ImageView img1 = (ImageView) findViewById(R.id.image1);
				
				imgPath = ImageSaveinSDCard(imagename);
				BillingObject.GetBillingObject().setImageName1(imgPath);
				//17-05-2021
				Bitmap bm = BitmapFactory.decodeFile(picturePath);								
				bm = Bitmap.createScaledBitmap(bm, bm.getWidth()/4, bm.getHeight()/4, true);				
				img1.setImageBitmap(bm);
				
				//Bitmap bm = BitmapFactory.decodeFile(picturePath);
				//img1.setImageBitmap(bm);
				//bm.recycle();
			}
			else if(requestCode == 2) 
			{
				isIdImageCaptured2 =  true;
				//String imagename = SplashScreenActivity.IMEINumber +"_CHEQUEPROOF_" +db.getMaxIdChequeCollection()+ "_" +currentdatetime + ".jpg";
				String imagename ="IMG2.jpg";
				SD.setTempIamge(imagename);
				int save = saveTempImage(imagename,picturePath);
				ImageView img2 = (ImageView) findViewById(R.id.image2);
				
				imgPath = ImageSaveinSDCard(imagename);
				BillingObject.GetBillingObject().setImageName2(imgPath);
				
				//17-05-2021
				Bitmap bm = BitmapFactory.decodeFile(picturePath);								
				bm = Bitmap.createScaledBitmap(bm, bm.getWidth()/4, bm.getHeight()/4, true);				
				img2.setImageBitmap(bm);
				
			}
			
			else if(requestCode == 3) 
			{
				isIdImageCaptured3 =  true;
				//String imagename = SplashScreenActivity.IMEINumber +"_CHEQUEPROOF_" +db.getMaxIdChequeCollection()+ "_" +currentdatetime + ".jpg";
				String imagename ="IMG3.jpg";
				SD.setTempIamge(imagename);
				int save = saveTempImage(imagename,picturePath);
				ImageView img3 = (ImageView) findViewById(R.id.image3);
				
				imgPath = ImageSaveinSDCard(imagename);
				BillingObject.GetBillingObject().setImageName3(imgPath);
				
				//17-05-2021
				Bitmap bm = BitmapFactory.decodeFile(picturePath);								
				bm = Bitmap.createScaledBitmap(bm, bm.getWidth()/4, bm.getHeight()/4, true);				
				img3.setImageBitmap(bm);
			
			}
			else if(requestCode == 4) 
			{
				isIdImageCaptured4 =  true;
				//String imagename = SplashScreenActivity.IMEINumber +"_CHEQUEPROOF_" +db.getMaxIdChequeCollection()+ "_" +currentdatetime + ".jpg";
				String imagename ="IMG4.jpg";
				SD.setTempIamge(imagename);
				int save = saveTempImage(imagename,picturePath);
				ImageView img4 = (ImageView) findViewById(R.id.image4);
				
				imgPath = ImageSaveinSDCard(imagename);
				BillingObject.GetBillingObject().setImageName4(imgPath);
				
				//17-05-2021
				Bitmap bm = BitmapFactory.decodeFile(picturePath);								
				bm = Bitmap.createScaledBitmap(bm, bm.getWidth()/4, bm.getHeight()/4, true);				
				img4.setImageBitmap(bm);
			}
		}
	}
//////
	public int saveTempImage(String imagename,String imagepath)
	{
		InputStream myInput = null;		
		OutputStream myOutput = null;
		int save = 0;
		try
		{
			File fileo = new File("/data/data/in.nsoft.bescomamr/databases/photo");
			if(!fileo.exists())
			{
				fileo.mkdirs();
			}
			myInput = new FileInputStream(imagepath);
			fileo = new File("/data/data/in.nsoft.bescomamr/databases/photo/", imagename);
			myOutput = new FileOutputStream(fileo);

			byte[] buffer = new byte[1024];
			int length;
			while((length = myInput.read(buffer))>0)
			{
				myOutput.write(buffer, 0, length);
			}	
			save=1;		
		}
		catch (Exception e)
		{
			Log.d("pushBtn", e.toString());
			save=0;
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
		return save;

	}


/*	public String ImageSaveinSDCard(String imagename)
	{
		InputStream myInput = null;
		OutputStream myOutput = null;
		String ImageName = "";
		try
		{
			CommonFunction cFun = new CommonFunction();
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
			ImageName = LoginActivity.IMEINumber + "_" +BillingObject.GetBillingObject().getmConnectionNo().trim() +"_"+ cFun.GetCurrentTime().replace(" ", "_").replace("-", "").replace(":", "")+"_"+imagename;
			String root = Environment.getExternalStorageDirectory().getPath();//get the sd Card Path

			myInput = new FileInputStream(ConstantClass.MtrImageSavePath + imagename);
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
			File fDelete = new File("/data/data/in.nsoft.bescomamr/databases/photo/" + inputImage);
			if(fDelete.exists())
				fDelete.delete();
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
	}*/
	
	public static String ImageSaveinSDCard(String imagename)
	{
		InputStream myInput = null;
		OutputStream myOutput = null;
		String ImageName = "";
		try
		{
			CommonFunction cFun = new CommonFunction();
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
			//08-07-2021
			ImageName = LoginActivity.IMEINumber + "_" +BillingObject.GetBillingObject().getmRRNo().trim() +"_"+ cFun.GetCurrentTime().replace(" ", "_").replace("-", "").replace(":", "")+"_"+imagename;
			String root = Environment.getExternalStorageDirectory().getPath();//get the sd Card Path

			myInput = new FileInputStream(ConstantClass.MtrImageSavePath + imagename);
			//myInput = new FileInputStream("/data/data/in.nsoft.bescomamr/databases/photo/" + imagename);
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
			
			File fDelete = new File(ConstantClass.MtrImageSavePath + imagename);
			if(fDelete.exists())
				fDelete.delete();
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

	
	////////////////////////////
	
	
	////////image capture////////
	private void selectImage(final int type) {



		final CharSequence[] options = { "Take Photo", "Cancel" };



		AlertDialog.Builder builder = new AlertDialog.Builder(ImageCaptureActivity.this);

		if(type==1)
			builder.setTitle("Add Photo!");

		builder.setItems(options, new DialogInterface.OnClickListener() {

			@Override

			public void onClick(DialogInterface dialog, int item) {

				if (options[item].equals("Take Photo"))
				{

					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
					String filepath = Environment.getExternalStorageDirectory().getPath()+"/AMRSurvey/Photos";
					if(!new File(filepath).exists())
					{
						new File(filepath).mkdirs();
					}

					File f = new File(filepath, "img.jpg");

					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					startActivityForResult(intent, type);
				}
				else if (options[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});

		builder.show();

	}
	
	
}
