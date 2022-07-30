package in.nsoft.bescomamr;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.R.string;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;


public class EncryptDecrypt
{
	public static final String TAG="spotbilling";
	private static String TRANSFORMATION="AES/CBC/PKCS7Padding";
	private static String ALGORITHM="BC";
	private static String DIGEST="MD5";

	private static Cipher _cipher;
	private static SecretKey _password;
	private static IvParameterSpec _ivParamSpec;

	//16 byte private key
	private static byte[] iv="ThisIsUrPassword".getBytes();

	private long contentLength = 0, totalLength = 0;//Added by Tamilselvan on 01-04-2014 
	int proTotal = 0;

	/**
	 Constructor
	 @password Public key
	 */
	public EncryptDecrypt(String password)
	{

		try
		{
			//password="nsoft987";
			//Encode digest
			MessageDigest digest;
			digest=MessageDigest.getInstance(DIGEST);
			_password=new SecretKeySpec(digest.digest(password.getBytes()),ALGORITHM);
			//INITIALIZE object
			_cipher=Cipher.getInstance(TRANSFORMATION);
			_ivParamSpec=new IvParameterSpec(iv);


		} 
		catch (NoSuchAlgorithmException e)
		{
			// TODO: handle exception
			Log.e(TAG,"NO such algorithm"+ALGORITHM,e);
		}
		catch (NoSuchPaddingException e)
		{
			// TODO: handle exception
			Log.e(TAG,"NO such padding PKCS 7",e);
		} 
	}
	//public String encrypt(byte[] text)
	@SuppressLint("NewApi")
	public String encrypt(String text)
	{
		InputStream is=null;
		FileOutputStream fos=null;
		CipherInputStream cis=null;
		StringBuilder sb=new StringBuilder();
		try
		{
			/*_cipher.init(Cipher.DECRYPT_MODE,_password,_ivParamSpec);
			byte[] decodedValue=Base64.decode(text.getBytes(), Base64.DEFAULT);
			byte[] decryptedVal=_cipher.doFinal(decodedValue);*/
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			String password ="nsoft987";
			byte[] key =password.getBytes(Charset.forName("UTF-16LE"));
			_cipher.getInstance(TRANSFORMATION,ALGORITHM);
			_cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(key, "AES"),new IvParameterSpec(key));
			//fis=new FileInputStream((new File(text)));
			is = new ByteArrayInputStream(text.getBytes("UTF-8"));
			cis= new CipherInputStream(is, _cipher);
			String root = Environment.getExternalStorageDirectory().getPath();//Get External Storage Path 
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
			File filepath = new File(root+"/BescomAMR/"+timeStamp);//External Storage Path + additional Folder
			if(!filepath.exists())//Check directory Exists or not 
			{
				filepath.mkdirs();//Not Exists Create Directory
			}
			if(filepath.exists())//If Directory Exists
			{
				fos = new FileOutputStream(filepath+"/encrypted.txt");//Get OutputStream of New Directory Path	
			}

			byte[] data=new byte[1024];
			int read = cis.read(data);
			
			while(read!=-1)
			{
				sb.append(data);
				fos.write(data,0,read); //rajeev
				
				sb.append(new String(data,"UTF-8"));
				read = cis.read(data);
				
			}
			return  sb.toString();
		} 
		catch (InvalidKeyException e)
		{
			Log.e(TAG, "invalid key(invalid encoding,wrong length,uninitialized,etc)",e);
			return null;
		}
		catch (InvalidAlgorithmParameterException e)
		{
			// TODO: handle exception
			Log.e(TAG, "invalid or inappropriate algorithm parameter for"+ALGORITHM,e);
			return null;
		}
		catch (NoSuchAlgorithmException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (NoSuchProviderException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (NoSuchPaddingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if(is!=null)
			{
				try 
				{
					is.close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(cis!=null)
			{
				try 
				{
					cis.close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fos!=null)
			{
				try 
				{
					fos.close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//Nitish 23-12-2016 For Optical Billing
		public String encryptData(String strEncrypted,String DeviceID,String MeterType)
		{
			InputStream is = null;
			FileOutputStream fos = null;
			CipherInputStream cis = null;
			StringBuilder sb = new StringBuilder();
			try
			{

				String currentdatetime = new SimpleDateFormat("ddMMyyyyHHmmss").format(Calendar.getInstance().getTime());
				Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
				String password = "nsoft987";
				byte[] key = password.getBytes(Charset.forName("UTF-16LE"));
				_cipher.getInstance(TRANSFORMATION,ALGORITHM);
				_cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(key, "AES"),new IvParameterSpec(key));
				is = new ByteArrayInputStream(strEncrypted.getBytes("UTF-8"));
				cis = new CipherInputStream(is, _cipher);


				String root = Environment.getExternalStorageDirectory().getPath();//Get External Storage Path 
				String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
				File filepath = new File(root+"/OpticalBilling/"+timeStamp+"/"+MeterType);//External Storage Path + additional Folder
				if(!filepath.exists())//Check directory Exists or not 
				{
					filepath.mkdirs();//Not Exists Create Directory
				}
				if(filepath.exists())//If Directory Exists
				{				
					String fileName = MeterType+"_"+ DeviceID +"_"+ currentdatetime + ".Enc";
					fos = new FileOutputStream(filepath + "/" + fileName);//Get OutputStream of New Directory Path	
				}

				byte[] data = new byte[1024];
				int read = cis.read(data);
				//String s;//punit
				while(read!=-1)
				{
					totalLength += read;
					sb.append(data);//rajeev
					fos.write(data,0,read); //rajeev
					sb.append(new String(data,"UTF-8"));
					read = cis.read(data);


				}
				return  sb.toString();
			} 
			catch (InvalidKeyException e)
			{
				Log.e(TAG, "invalid key(invalid encoding,wrong length,uninitialized,etc)",e);
				return null;
			}
			catch (InvalidAlgorithmParameterException e)
			{
				// TODO: handle exception
				Log.e(TAG, "invalid or inappropriate algorithm parameter for"+ALGORITHM,e);
				return null;
			}
			catch (NoSuchAlgorithmException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} 
			catch (NoSuchProviderException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} 
			catch (NoSuchPaddingException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
			finally
			{
				if(is!=null)
				{
					try 
					{
						is.close();
					} 
					catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(cis!=null)
				{
					try 
					{
						cis.close();
					} 
					catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(fos!=null)
				{
					try 
					{
						fos.close();
					} 
					catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	
	@SuppressLint("NewApi")
	public String decrypt(byte[] sd1)
	{
		//FileInputStream fis=null;

		String s=" ";
		InputStream is=null;
		FileOutputStream fos=null;
		CipherInputStream cis=null;
		StringBuilder sb=new StringBuilder();
		try
		{
			/*_cipher.init(Cipher.DECRYPT_MODE,_password,_ivParamSpec);
			byte[] decodedValue=Base64.decode(text.getBytes(), Base64.DEFAULT);
			byte[] decryptedVal=_cipher.doFinal(decodedValue);*/
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			String password ="nsoft987";
			byte[] key =password.getBytes(Charset.forName("UTF-16LE"));
			_cipher.getInstance(TRANSFORMATION,ALGORITHM);
			_cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key, "AES"),new IvParameterSpec(key));
			is = new ByteArrayInputStream(sd1);//text.getBytes("UTF-8"));
			cis= new CipherInputStream(is, _cipher);			
			String root = Environment.getExternalStorageDirectory().getPath();//Get External Storage Path 
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
			File filepath = new File(root+"/BescomAMR/"+timeStamp);//External Storage Path + additional Folder
			if(!filepath.exists())//Check directory Exists or not 
			{
				filepath.mkdir();//Not Exists Create Directory
			}
			if(filepath.exists())//If Directory Exists
			{
				fos = new FileOutputStream(filepath+"/Decrypted.txt");//Get OutputStream of New Directory Path	
			}

			byte[] data=new byte[1024];

			int read=cis.read(data);
			//punit

			while(read!=-1){			
				sb.append(new String(data,"UTF-8"));
				//sb.append(new String(data,"UTF-8"),0,read);
				read = cis.read(data,0,read);			

			}
			return sb.toString();


		} 
		catch (InvalidKeyException e)
		{
			// TODO: handle exception

			Log.e(TAG, "invalid key(invalid encoding,wrong length,uninitialized,etc)",e);
			return null;
		}
		catch (InvalidAlgorithmParameterException e)
		{
			// TODO: handle exception
			Log.e(TAG, "invalid or inappropriate algorithm parameter for"+ALGORITHM,e);
			return null;
		}
		catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(cis!=null){
				try {
					cis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	@SuppressLint("NewApi")
	public String decryptWithHandler(String text, Handler handler, final ProgressBar pb, final TextView lblPBText)
	{
		FileInputStream fis = null;
		FileOutputStream fos = null;
		CipherInputStream cis = null;
		StringBuilder sb=new StringBuilder();
		try//Try Block
		{
			//Get browsefile contentlength=============================================================
			File f1 = new File(text);
			contentLength = f1.length();/**/				
			pb.setMax(100);
			handler.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					lblPBText.setText("Loading file data please wait.");
				}
			});
			//END Get browsefile contentlength=========================================================	

			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			String password = "nsoft987";
			byte[] key = password.getBytes(Charset.forName("UTF-16LE"));
			_cipher.getInstance(TRANSFORMATION,ALGORITHM);
			_cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key, "AES"),new IvParameterSpec(key));
			fis = new FileInputStream((new File(text)));
			cis = new CipherInputStream(fis, _cipher);
			String root = Environment.getExternalStorageDirectory().getPath();//Get External Storage Path 
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
			File filepath = new File(root+"/BescomAMR/"+timeStamp);//External Storage Path + additional Folder
			if(!filepath.exists())//Check directory Exists or not 
			{
				filepath.mkdir();//Not Exists Create Directory
			}
			if(filepath.exists())//If Directory Exists
			{
				fos = new FileOutputStream(filepath+"/Decrypted.txt");//Get OutputStream of New Directory Path	
			}

			byte[] data = new byte[1024];
			int read = cis.read(data);
			while(read != -1)//While Loop Starts
			{				
				totalLength += read;
				sb.append(new String(data,"UTF-8"));
				read = cis.read(data);

				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						pb.setProgress((int)((totalLength*100)/contentLength));
						lblPBText.setText("Loading file data please wait..."+((totalLength * 100)/contentLength)+"%");
					}
				});
			}//END While Loop
			return  sb.toString();
		}//END Try Block
		catch (InvalidKeyException e)
		{
			// TODO: handle exception
			Log.e(TAG, "invalid key(invalid encoding,wrong length,uninitialized,etc)",e);
			return null;
		}
		catch (InvalidAlgorithmParameterException e)
		{
			// TODO: handle exception
			Log.e(TAG, "invalid or inappropriate algorithm parameter for"+ALGORITHM,e);
			return null;
		}
		catch (NoSuchAlgorithmException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (NoSuchProviderException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (NoSuchPaddingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally//finally block
		{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(cis!=null){
				try {
					cis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}//END finally block
	}

	//Created By Tamilselvan on 01-04-2014 
	/**
	 * This Encryption method for Billing Data with progress bar and handler
	 * */
	@SuppressLint("NewApi")
	public String encryptWithHandlerBilling(String strEncrypted, Handler handler, final ProgressBar pb, final TextView lblPBText, String DeviceID, int totalRecords, int progressTotal)
	{
		InputStream is = null;
		FileOutputStream fos = null;
		CipherInputStream cis = null;
		StringBuilder sb = new StringBuilder();
		try
		{
			//Get browsefile contentlength=============================================================			
			contentLength = strEncrypted.length();/**/	
			if(progressTotal == 0)
			{
				totalLength = 0;
				proTotal = 50;
			}
			else
			{
				proTotal = progressTotal;
				//pb.setMax(100);
			}
			//END Get browsefile contentlength=========================================================	

			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			String password = "nsoft987";
			byte[] key = password.getBytes(Charset.forName("UTF-16LE"));
			_cipher.getInstance(TRANSFORMATION,ALGORITHM);
			_cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(key, "AES"),new IvParameterSpec(key));
			is = new ByteArrayInputStream(strEncrypted.getBytes("UTF-8"));
			cis = new CipherInputStream(is, _cipher);


			String root = Environment.getExternalStorageDirectory().getPath();//Get External Storage Path 
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
			File filepath = new File(root+"/BescomAMR/"+timeStamp+"/FromSbmFile/"+ DeviceID + "_" + CommonFunction.GetTimeStampName());//External Storage Path + additional Folder
			if(!filepath.exists())//Check directory Exists or not 
			{
				filepath.mkdirs();//Not Exists Create Directory
			}
			if(filepath.exists())//If Directory Exists
			{				
				String fileName = DeviceID + "_FromSbmFile_" + CommonFunction.GetTimeStampName() + "_Records_" + totalRecords + ".FromSbmEnc";
				fos = new FileOutputStream(filepath + "/" + fileName);//Get OutputStream of New Directory Path	
			}

			byte[] data = new byte[1024];
			int read = cis.read(data);
			//String s;//punit
			while(read!=-1)
			{
				totalLength += read;
				sb.append(data);//rajeev
				fos.write(data,0,read); //rajeev
				sb.append(new String(data,"UTF-8"));
				read = cis.read(data);

				if(progressTotal == 0)
				{
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							pb.setProgress(proTotal + (int)(((totalLength * 100)/contentLength)/4));
							lblPBText.setText("Writing into file please wait..." + (proTotal + (int)(((totalLength * 100)/contentLength)/4)) + "%");
						}
					});
				}
				else
				{
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							pb.setProgress(proTotal + ((int)((totalLength * 100)/contentLength))/5);
							lblPBText.setText("Please Wait... Creating Backup "+ String.valueOf(proTotal + ((int)((totalLength * 100)/contentLength))/5) + "%");
						}
					});
				}
			}
			return  sb.toString();
		} 
		catch (InvalidKeyException e)
		{
			Log.e(TAG, "invalid key(invalid encoding,wrong length,uninitialized,etc)",e);
			return null;
		}
		catch (InvalidAlgorithmParameterException e)
		{
			// TODO: handle exception
			Log.e(TAG, "invalid or inappropriate algorithm parameter for"+ALGORITHM,e);
			return null;
		}
		catch (NoSuchAlgorithmException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (NoSuchProviderException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (NoSuchPaddingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if(is!=null)
			{
				try 
				{
					is.close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(cis!=null)
			{
				try 
				{
					cis.close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fos!=null)
			{
				try 
				{
					fos.close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}//End of Encryption of File with handler

	/**
	 * This Encryption method for Collection Data with progress bar and handler
	 * */
	@SuppressLint("NewApi")
	public String encryptWithHandlerCollection(String strEncrypted, Handler handler, final ProgressBar pb, final TextView lblPBText, String DeviceID, int totalRecords, int progressTotal)
	{
		InputStream is = null;
		FileOutputStream fos = null;
		CipherInputStream cis = null;
		StringBuilder sb = new StringBuilder();
		try
		{
			//Get browsefile contentlength=============================================================			
			contentLength = strEncrypted.length();/**/	
			if(progressTotal == 0)
			{
				totalLength = 0;
				proTotal = 75;
			}
			else
			{
				proTotal = progressTotal;
				//pb.setMax(100);
			}
			//END Get browsefile contentlength=========================================================	

			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			String password = "nsoft987";
			byte[] key = password.getBytes(Charset.forName("UTF-16LE"));
			_cipher.getInstance(TRANSFORMATION,ALGORITHM);
			_cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(key, "AES"),new IvParameterSpec(key));
			is = new ByteArrayInputStream(strEncrypted.getBytes("UTF-8"));
			cis = new CipherInputStream(is, _cipher);


			String root = Environment.getExternalStorageDirectory().getPath();//Get External Storage Path 
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
			File filepath = new File(root+"/BescomAMR/"+timeStamp+"/FromSbmFile/"+ DeviceID + "_" + CommonFunction.GetTimeStampName());//External Storage Path + additional Folder
			if(!filepath.exists())//Check directory Exists or not 
			{
				filepath.mkdirs();//Not Exists Create Directory
			}
			if(filepath.exists())//If Directory Exists
			{				
				String fileName = "BESCOM_ReceiptsFromSbmFile_" + CommonFunction.GetTimeStampName() + "_Records_" + totalRecords + ".FromSbmEnc";
				fos = new FileOutputStream(filepath + "/" + fileName);//Get OutputStream of New Directory Path	
			}

			byte[] data = new byte[1024];
			int read = cis.read(data);
			//String s;//punit
			while(read!=-1)
			{
				totalLength += read;
				sb.append(data);//rajeev
				fos.write(data,0,read); //rajeev
				sb.append(new String(data,"UTF-8"));
				read = cis.read(data);

				if(progressTotal == 0)
				{
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							pb.setProgress(proTotal + (int)(((totalLength * 100)/contentLength)/4));
							lblPBText.setText("Writing into file please wait..." + (proTotal + (int)(((totalLength * 100)/contentLength)/4)) + "%");
						}
					});
				}
				else
				{
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							//proTotal = proTotal + ((int)((totalLength * 100)/contentLength))/4;
							pb.setProgress(proTotal + ((int)((totalLength * 100)/contentLength))/5);
							lblPBText.setText("Please Wait... Creating Backup "+ String.valueOf(proTotal + ((int)((totalLength * 100)/contentLength))/5) + "%");
						}
					});
				}
			}
			return  sb.toString();
		} 
		catch (InvalidKeyException e)
		{
			Log.e(TAG, "invalid key(invalid encoding,wrong length,uninitialized,etc)",e);
			return null;
		}
		catch (InvalidAlgorithmParameterException e)
		{
			// TODO: handle exception
			Log.e(TAG, "invalid or inappropriate algorithm parameter for"+ALGORITHM,e);
			return null;
		}
		catch (NoSuchAlgorithmException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (NoSuchProviderException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (NoSuchPaddingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if(is!=null)
			{
				try 
				{
					is.close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(cis!=null)
			{
				try 
				{
					cis.close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fos!=null)
			{
				try 
				{
					fos.close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}//End of Encryption of File with handler
}
