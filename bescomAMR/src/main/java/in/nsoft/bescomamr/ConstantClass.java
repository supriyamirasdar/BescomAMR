package in.nsoft.bescomamr;


public class ConstantClass {
	//***************************With Camera****************************************************************
	
	//public final static String IPAddress = "http://123.201.131.113:8112/service.asmx";//Testing IP Address  
	//public final static String IPAddress =  "http://124.153.117.121:8185/service.asmx";//Live IP Address
	public final static String IPAddress = "https://gprs-webservices.nsoft.in/service.asmx";//Live IP Address

	public final static int height = 720; //Camera Image Height
	public final static int width = 1280; //Camera Image Width
	//600 337
	//352 288
	// 640 480
    // 960 720
    // 1024 768
    // 1280 720
	
	public final static String LogFileName = "LogError.txt"; 
	public final static String eol = System.getProperty("line.separator");//New Line

	//Billing class===================================================================================================
	public final static String sNB= "Surveyed";
	public final static String sBilled = "Not Surveyed";
	public final static String btnOKText = "Survey"; //Nitish 27-09-2016 Survey
	public final static String btnReprintText = "Survey completed for this Conn";
	//END Billing class===============================================================================================

	//BillingConsumption class========================================================================================
	public final static String strProcessText = "Process";
	public final static String strResetText = "Reset";
	public final static String MtrImageSavePath = "/data/data/in.nsoft.bescomamr/databases/photo/";
	//END BillingConsumption class====================================================================================

	//Print Byte Code=================================================================================================
	public final static byte [] data1 ={0x1B,0x4B,0x07};//28-->01, 30 -->02,36-->03, 38 --> 04 , 41-->05,44-->06,48-->07,52-->08,57-->0E, 52-->0D, 52-->08, 57-->09, 72-->0B
	public final static byte [] data2 ={0x1B,0x4B,0x02};//Punit on 24-03-2014
	public final static byte [] data3 ={0x1B,0x4B,0x01};//Punit on 24-03-2014
	public final static byte [] data72 ={0x1B,0x4B,0x0B};
	
	public final static byte [] doubleFontON ={0x12,0x44};
	public final static byte [] doubleFontOFF ={0x12,0x64};
	
	
	//byte [] data28char ={0x1B,0x4B,0x01};
	public final static byte[] linefeed1={0x0A,0x0D};

	public final static byte[] barCode ={0x1B, 0x7A, 0x32, 0x14, 0x37, 0x59}; 
	//syntax: ESC-0x1B a-0x7A t-0x32 n-0x14(length-20) h-0x37(height) 
	//END Print Byte Code=============================================================================================

	//Collection ===================================================================================================
	public final static String sPaidAmt = "Paid Amount:"; 
	public final static String sNotPaid = "Not Paid"; 	
	public final static String mOpen = "Cash Counter Open";	
	public final static String mclose = "Cash Counter Closed";	

	public final static String mVerified = "Verified"; 
	public final static String mNotVerified = "Not Verified"; 
	public final static String mNAS = "Not Assigned"; 
	public final static String mNAP = "Not Approved";
	public final static String mSNV = "SIM Not Valid";	
	public final static String mAPP = "Approved";	
	public final static String mPending = "Pending";
	public final static String mComplete = "Completed";
	public final static String mbtnGetBatchNo = "Get Current Batch Number";
	public final static String mbtnPrevClose =  "Close Prev Cash Counter";
	//END Collection===============================================================================================

	//Collection Receipt Type 
	public final static String mRevenueRcpt= "REVENUE";
	public final static String mMISCRcpt= "MISC";
	public final static String mRevenueType= "R";
	public final static String mMISCType= "M";
	public final static String mMiscASD= " (ASD)";
	public final static String mBalance= "ASD Balance";
	//END Receipt Type===============================================================================================

	//public final static String[] imeinos = {"911375053925727"};

	//Signal Strength 17-10-2014
	public final static String sLOW = "LOW"; 
	public final static String sNORMAL = "NORMAL"; 
	public final static String sHIGH = "HIGH"; 	
	
	
	//public final static String mSurveyType = "1"; //Electricity Survey 
	public final static String mSurveyType = "2"; //Water Survey 
	public final static String mSurvryName = "BESCOM GP Survey"; 
	
	
	public final static String mTest = "Test Version "; 
	public final static String mLive = "Live Version "; 
	
	public final static String AppVersion = "5.1";//File Version //103  431

}
