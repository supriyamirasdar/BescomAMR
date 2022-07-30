package in.nsoft.bescomamr;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper implements Schema {
	private static final String DB_NAME = "Bescom_AMR.dat";
	private static final int VERSION = 1;//1,2
	private Context mcntx;
	private Cursor cr;
	int i = 0, Total = 0;
	private long contentLength = 0, totalLength = 0;
	private static final String TAG = DatabaseHelper.class.getName();
	String ReceiveStr = "";
	int proTotal = 0;

	public DatabaseHelper(Context context) {  
		super(context,DB_NAME,null,VERSION);
		mcntx=context;
		// TODO Auto-generated constructor stub	
	}


	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	@Override
	// Added 06-09-2019
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
		if (android.os.Build.VERSION.SDK_INT >= 28)
			db.disableWriteAheadLogging();
	}
	//Function to verify user credential
	public boolean VerifyUser( String id,String pwd ) throws Exception
	{
		//Cursor cr =getReadableDatabase().query(TABLE_USERS, null, null, null, null, null, null);//getReadableDatabase().rawQuery(sql,null) ;

		cr=null ;
		boolean result=false;
		try {
			QueryParameters qParam=StoredProcedure.GetUserDetails(id, pwd);

			cr=getReadableDatabase().rawQuery(qParam.getSql(),qParam.getSelectionArgs());

			if(cr.getCount()==1)
			{
				result= true;
				cr.moveToFirst();
				/*	UserDetails.SetUserDetails(cr.getInt(cr.getColumnIndex(COL_USERID_USERS)), cr.getString(cr.getColumnIndex(COL_USERNAME_USERS))
							,cr.getString(cr.getColumnIndex(COL_FULLNAME_USERS)) , cr.getString(cr.getColumnIndex(COL_MOBILENO_USERS))
							,cr.getInt(cr.getColumnIndex(COL_ROLEID_USERS)), cr.getString(cr.getColumnIndex(COL_LOCATIONCODE_USERS))
							, cr.getInt(cr.getColumnIndex(COL_BLOCKED_USERS)));*/
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
			throw e;

		}
		finally
		{
			if(cr!=null)
				cr.close();

		}
		return result;
	}
	//punit 15022014
	public AutoDDLAdapter getAllConnectionNo() throws Exception
	{
		AutoDDLAdapter pList = null;
		cr=null ;
		try
		{
			QueryParameters qParam = StoredProcedure.GetUserId();
			cr = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());		
			for(int i=1; i<=cr.getCount();++i)
			{
				if(pList==null)
				{
					pList=new AutoDDLAdapter(mcntx,new ArrayList<DDLItem>());
				}
				if(i==1)
					cr.moveToFirst();
				else
					cr.moveToNext();	
				pList.AddItem(String.valueOf(cr.getInt(cr.getColumnIndex(COL_CustomerName))), cr.getString(cr.getColumnIndex(COL_ConnectionNo)));


			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return pList;
	}

	//Modified 30-07-2015
	public void GetAllDatafromDb(String custId ) throws Exception
	{
		ReadSlabNTarifSbmBillCollection CustDetails = BillingObject.GetBillingObject();


		cr=null ;
		try
		{
			QueryParameters qParam = StoredProcedure.GetAllDatafromDb(custId);
			cr = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());	
			if(cr.getCount() > 0)//Cursor IF Start
			{
				cr.moveToFirst();				
				CustDetails.setmAbFlag(cr.getString(cr.getColumnIndex(COL_AbFlag)));
				CustDetails.setmAccdRdg(cr.getString(cr.getColumnIndex(COL_AccdRdg)));
				CustDetails.setmAccdRdg_rtn(cr.getString(cr.getColumnIndex(COL_AccdRdg_rtn)));
				CustDetails.setmAddress1(cr.getString(cr.getColumnIndex(COL_Address1)));
				CustDetails.setmAddress2(cr.getString(cr.getColumnIndex(COL_Address2)));
				CustDetails.setmApplicationNo(cr.getString(cr.getColumnIndex(COL_ApplicationNo)));
				CustDetails.setmArears(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Arears))));
				CustDetails.setmArrearsOld(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_ArrearsOld))));
				CustDetails.setmAvgCons(cr.getString(cr.getColumnIndex(COL_AvgCons)));
				CustDetails.setmBankID(cr.getInt(cr.getColumnIndex(COL_BankID)));
				CustDetails.setmBatch_No(cr.getString(cr.getColumnIndex(COL_Batch_No)));
				CustDetails.setmBillable(cr.getString(cr.getColumnIndex(COL_Billable))); 
				CustDetails.setmBillDate(cr.getString(cr.getColumnIndex(COL_BillDate)));
				CustDetails.setmBillFor(cr.getString(cr.getColumnIndex(COL_BillFor)));
				CustDetails.setmBillNo(cr.getString(cr.getColumnIndex(COL_BillNo)));
				CustDetails.setmBillTotal(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_BillTotal))));
				CustDetails.setmBjKj2Lt2(cr.getString(cr.getColumnIndex(COL_BjKj2Lt2)));
				CustDetails.setmBlCnt(cr.getString(cr.getColumnIndex(COL_BlCnt)));
				CustDetails.setmBOBilled_Amount(cr.getString(cr.getColumnIndex(COL_BOBilled_Amount)));
				CustDetails.setmBOBillFlag(cr.getInt(cr.getColumnIndex(COL_BOBillFlag)));
				CustDetails.setmCapReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_CapReb))));
				CustDetails.setmChargetypeID(cr.getInt(cr.getColumnIndex(COL_ChargetypeID)));
				CustDetails.setmChequeDDDate(cr.getString(cr.getColumnIndex(COL_ChequeDDDate)));
				CustDetails.setmChequeDDNo(cr.getInt(cr.getColumnIndex(COL_ChequeDDNo)));
				CustDetails.setmConnectionNo(cr.getString(cr.getColumnIndex(COL_ConnectionNo)));
				CustDetails.setmConsPayable(cr.getString(cr.getColumnIndex(COL_ConsPayable)));
				CustDetails.setmCustomerName(cr.getString(cr.getColumnIndex(COL_CustomerName)));
				CustDetails.setmDateTime(cr.getString(cr.getColumnIndex(COL_DateTime)));
				CustDetails.setmDayWise_Flag(cr.getString(cr.getColumnIndex(COL_DayWise_Flag)));
				CustDetails.setmDemandChrg(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_DemandChrg))));
				CustDetails.setmDLAvgMin(cr.getString(cr.getColumnIndex(COL_DLAvgMin)));
				CustDetails.setmDlCount(cr.getInt(cr.getColumnIndex(COL_DlCount)));				
				CustDetails.setmDLTEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_DLTEc))));
				CustDetails.setmDPdate(cr.getString(cr.getColumnIndex(COL_DPdate)));
				CustDetails.setmDrFees(cr.getString(cr.getColumnIndex(COL_DrFees)));
				CustDetails.setmDueDate(cr.getString(cr.getColumnIndex(COL_DueDate)));
				CustDetails.setmECReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_ECReb))));
				CustDetails.setmEcsFlag(cr.getString(cr.getColumnIndex(COL_EcsFlag)));
				CustDetails.setmExLoad(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_ExLoad))));
				CustDetails.setmFC_Slab_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FC_Slab_2))));
				CustDetails.setmFixedCharges(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FixedCharges))));
				CustDetails.setmFLReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FLReb))));
				CustDetails.setmForMonth(cr.getString(cr.getColumnIndex(COL_ForMonth)));
				CustDetails.setmGoKArrears(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_GoKArrears))));
				CustDetails.setmGoKPayable(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_GoKPayable))));
				CustDetails.setmGprs_Flag(cr.getInt(cr.getColumnIndex(COL_Gprs_Flag)));
				CustDetails.setmGps_Latitude_image(cr.getString(cr.getColumnIndex(COL_Gps_Latitude_image)));
				CustDetails.setmGps_Latitude_print(cr.getString(cr.getColumnIndex(COL_Gps_Latitude_print)));
				CustDetails.setmGps_LatitudeCardinal_image(cr.getString(cr.getColumnIndex(COL_Gps_LatitudeCardinal_image)));
				CustDetails.setmGps_LatitudeCardinal_print(cr.getString(cr.getColumnIndex(COL_Gps_LatitudeCardinal_print)));
				CustDetails.setmGps_Longitude_image(cr.getString(cr.getColumnIndex(COL_Gps_Longitude_image)));
				CustDetails.setmGps_Longitude_print(cr.getString(cr.getColumnIndex(COL_Gps_Longitude_print)));
				CustDetails.setmGps_LongitudeCardinal_image(cr.getString(cr.getColumnIndex(COL_Gps_LongitudeCardinal_image)));
				CustDetails.setmGps_LatitudeCardinal_print(cr.getString(cr.getColumnIndex(COL_Gps_LatitudeCardinal_print)));
				CustDetails.setmGvpId(cr.getString(cr.getColumnIndex(COL_GvpId)));
				CustDetails.setmHCReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_HCReb))));
				CustDetails.setmHLReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_HLReb))));
				CustDetails.setmHWCReb(cr.getString(cr.getColumnIndex(COL_HWCReb)));
				CustDetails.setmImage_Cap_Date(cr.getString(cr.getColumnIndex(COL_Image_Cap_Date)));
				CustDetails.setmImage_Cap_Time(cr.getString(cr.getColumnIndex(COL_Image_Cap_Time)));
				CustDetails.setmImage_Name(cr.getString(cr.getColumnIndex(COL_Image_Name)));
				CustDetails.setmImage_Path(cr.getString(cr.getColumnIndex(COL_Image_Path)));
				CustDetails.setmIntrst_Unpaid(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Intrst_Unpaid))));
				CustDetails.setmIntrstCurnt(cr.getString(cr.getColumnIndex(COL_IntrstCurnt)));
				CustDetails.setmIntrstOld(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_IntrstOld))));
				CustDetails.setmIODD11_Remarks(cr.getString(cr.getColumnIndex(COL_IODD11_Remarks)));
				CustDetails.setmIODRemarks(cr.getString(cr.getColumnIndex(COL_IODRemarks)));
				CustDetails.setmIssueDateTime(cr.getString(cr.getColumnIndex(COL_IssueDateTime)));
				CustDetails.setmKVA_Consumption(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_KVA_Consumption))));
				CustDetails.setmKVAAssd_Cons(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_KVAAssd_Cons))));
				CustDetails.setmKVAFR(cr.getString(cr.getColumnIndex(COL_KVAFR)));
				CustDetails.setmKVAH_OldConsumption(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_KVAFR))));
				CustDetails.setmKVAIR(cr.getString(cr.getColumnIndex(COL_KVAIR)));
				CustDetails.setmLatitude(cr.getString(cr.getColumnIndex(COL_Latitude)));
				CustDetails.setmLatitude_Dir(cr.getString(cr.getColumnIndex(COL_Latitude_Dir)));
				CustDetails.setmLegFol(cr.getString(cr.getColumnIndex(COL_LegFol)));
				CustDetails.setmLinMin(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_LinMin))));
				CustDetails.setmLocationCode(cr.getString(cr.getColumnIndex(COL_LocationCode)));
				CustDetails.setmLongitude(cr.getString(cr.getColumnIndex(COL_Longitude)));
				CustDetails.setmLongitude_Dir(cr.getString(cr.getColumnIndex(COL_Longitude_Dir)));
				CustDetails.setmMCHFlag(cr.getString(cr.getColumnIndex(COL_MCHFlag)));
				CustDetails.setmMeter_serialno(cr.getString(cr.getColumnIndex(COL_Meter_serialno)));
				CustDetails.setmMeter_type(cr.getString(cr.getColumnIndex(COL_Meter_type)));
				CustDetails.setmMF(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_MF))));
				CustDetails.setmMobileNo(cr.getString(cr.getColumnIndex(COL_MobileNo)));
				CustDetails.setmMtd(cr.getString(cr.getColumnIndex(COL_Mtd)));
				CustDetails.setmMtrDisFlag(cr.getInt(cr.getColumnIndex(COL_MtrDisFlag)));
				CustDetails.setmNewNoofDays(cr.getString(cr.getColumnIndex(COL_NewNoofDays)));
				CustDetails.setmNoOfDays(cr.getString(cr.getColumnIndex(COL_NoOfDays)));
				CustDetails.setmOld_Consumption(cr.getString(cr.getColumnIndex(COL_Old_Consumption)));
				CustDetails.setmOldConnID(cr.getString(cr.getColumnIndex(COL_OldConnID)));
				CustDetails.setmOtherChargeLegend(cr.getString(cr.getColumnIndex(COL_OtherChargeLegend)));
				CustDetails.setmOthers(cr.getString(cr.getColumnIndex(COL_Others)));
				CustDetails.setmPaid_Amt(cr.getInt(cr.getColumnIndex(COL_Paid_Amt)));
				CustDetails.setmPayment_Mode(cr.getString(cr.getColumnIndex(COL_Payment_Mode)));
				CustDetails.setmPenExLd(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_PenExLd))));
				CustDetails.setmPF(cr.getString(cr.getColumnIndex(COL_PF)));
				CustDetails.setmPfPenAmt(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_PfPenAmt))));
				CustDetails.setmPreRead(cr.getString(cr.getColumnIndex(COL_PreRead)));
				CustDetails.setmPreStatus(cr.getString(cr.getColumnIndex(COL_PreStatus)));//Current/Present status
				CustDetails.setmPrevRdg(cr.getString(cr.getColumnIndex(COL_PrevRdg)));
				CustDetails.setmRcptCnt(cr.getInt(cr.getColumnIndex(COL_RcptCnt)));
				CustDetails.setmReaderCode(cr.getString(cr.getColumnIndex(COL_ReaderCode)));
				CustDetails.setmReadingDay(cr.getString(cr.getColumnIndex(COL_ReadingDay)));
				CustDetails.setmRebateFlag(cr.getString(cr.getColumnIndex(COL_RebateFlag)));
				CustDetails.setmReceipt_No(cr.getInt(cr.getColumnIndex(COL_Receipt_No)));
				CustDetails.setmReceiptAmnt(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_ReceiptAmnt))));
				CustDetails.setmReceiptDate(cr.getString(cr.getColumnIndex(COL_ReceiptDate)));
				CustDetails.setmReceipttypeflag(cr.getString(cr.getColumnIndex(COL_Receipttypeflag)));
				CustDetails.setmRecordDmnd(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_RecordDmnd))));
				CustDetails.setmRemarks(cr.getString(cr.getColumnIndex(COL_Remarks)));
				CustDetails.setmRRFlag(cr.getString(cr.getColumnIndex(COL_RRFlag)));
				CustDetails.setmRRNo(cr.getString(cr.getColumnIndex(COL_RRNo)));
				CustDetails.setmSancHp(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_SancHp))));
				CustDetails.setmSancKw(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_SancKw))));
				CustDetails.setmSancLoad(cr.getString(cr.getColumnIndex(COL_SancLoad)));
				CustDetails.setmSBMNumber(cr.getString(cr.getColumnIndex(COL_SBMNumber)));
				CustDetails.setmSectionName(cr.getString(cr.getColumnIndex(COL_SectionName)));
				CustDetails.setmSlowRtnPge(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_SlowRtnPge))));
				CustDetails.setmSpotSerialNo(cr.getInt(cr.getColumnIndex(COL_SpotSerialNo)));
				CustDetails.setmStatus(cr.getInt(cr.getColumnIndex(COL_Status)));//Previous Status
				CustDetails.setmSubId(cr.getString(cr.getColumnIndex(COL_SubId)));
				CustDetails.setmSupply_Points(cr.getInt(cr.getColumnIndex(COL_Supply_Points)));           
				CustDetails.setmTariffCode(cr.getInt(cr.getColumnIndex(COL_TariffCode)));
				CustDetails.setmTarifString(cr.getString(cr.getColumnIndex(COL_TarifString)));     
				CustDetails.setmTaxAmt(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_TaxAmt))));
				CustDetails.setmTaxFlag(cr.getString(cr.getColumnIndex(COL_TaxFlag)));
				CustDetails.setmTcName(cr.getString(cr.getColumnIndex(COL_TcName)));
				CustDetails.setmTEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_TEc))));
				CustDetails.setmTFc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_TFc))));
				CustDetails.setmThirtyFlag(cr.getString(cr.getColumnIndex(COL_ThirtyFlag)));
				CustDetails.setmTourplanId(cr.getString(cr.getColumnIndex(COL_TourplanId)));
				CustDetails.setmTvmMtr(cr.getString(cr.getColumnIndex(COL_TvmMtr)));
				CustDetails.setmTvmPFtype(cr.getString(cr.getColumnIndex(COL_TvmPFtype)));
				//CustDetails.setmUIDR_Slab(cr.getInt(cr.getColumnIndex(COL_UID_Slab)));
				CustDetails.setmUnits(cr.getString(cr.getColumnIndex(COL_Units)));
				CustDetails.setMmUID(cr.getInt(cr.getColumnIndex(COL_UID_S_BcData)));//punit 25022014
				CustDetails.setmMeter_Present_Flag(cr.getString(cr.getColumnIndex(COL_METER_PRESENT_FLAG)) == null ? 0 : cr.getInt(cr.getColumnIndex(COL_METER_PRESENT_FLAG)));
				CustDetails.setmMtr_Not_Visible(cr.getString(cr.getColumnIndex(COL_MTR_NOT_VISIBLE)) == null ? 0 : cr.getInt(cr.getColumnIndex(COL_MTR_NOT_VISIBLE)));

				CustDetails.setmDLTEc_GoK(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_DLTEc_GoK))));//31-12-2014
				CustDetails.setmOldTecBill(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_OldTecBill))));//Nitish on 21-04-2016

				//30-07-2015
				CustDetails.setmAadharNo(cr.getString(cr.getColumnIndex(COL_AadharNo)));
				CustDetails.setmVoterIdNo(cr.getString(cr.getColumnIndex(COL_VoterIdNo)));				
				CustDetails.setmMtrMakeFlag(cr.getInt(cr.getColumnIndex(COL_MtrMakeFlag)));
				CustDetails.setmMtrBodySealFlag(cr.getInt(cr.getColumnIndex(COL_MtrBodySealFlag)));
				CustDetails.setmMtrTerminalCoverFlag(cr.getInt(cr.getColumnIndex(COL_MtrTerminalCoverFlag)));
				CustDetails.setmMtrTerminalCoverSealedFlag(cr.getInt(cr.getColumnIndex(COL_MtrTerminalCoverSealedFlag)));


				CustDetails.setmFACValue(cr.getString(cr.getColumnIndex(COL_FACValue)));//19-12-2015
				//CustDetails.setmUID(cr.getInt(cr.getColumnIndex(COL_UID_Slab)));
				//CustDetails.setMmConnectionNo(cr.getString(cr.getColumnIndex(COL_ConnectionNo_Slab)));
				//CustDetails.setMmRRNo(cr.getString(cr.getColumnIndex(COL_RRNo_Slab)));
				CustDetails.setMmECRate_Count(cr.getInt(cr.getColumnIndex(COL_ECRate_Count)));
				CustDetails.setMmECRate_Row(cr.getInt(cr.getColumnIndex(COL_ECRate_Row)));
				CustDetails.setMmFCRate_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FCRate_1))));
				CustDetails.setMmCOL_FCRate_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FCRate_2))));
				CustDetails.setMmUnits_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_1))));
				CustDetails.setMmUnits_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_2))));
				CustDetails.setMmUnits_3(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_3))));
				CustDetails.setMmUnits_4(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_4))));
				CustDetails.setMmUnits_5(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_5))));
				CustDetails.setMmUnits_6(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_6))));
				CustDetails.setMmEC_Rate_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_1))));
				CustDetails.setMmEC_Rate_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_2))));
				CustDetails.setMmEC_Rate_3(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_3))));
				CustDetails.setMmEC_Rate_4(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_4))));
				CustDetails.setMmEC_Rate_5(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_5))));
				CustDetails.setMmEC_Rate_6(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_6))));
				CustDetails.setMmEC_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_1))));
				CustDetails.setMmEC_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_2))));
				CustDetails.setMmEC_3(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_3))));
				CustDetails.setMmEC_4(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_4))));
				CustDetails.setMmEC_5(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_5))));
				CustDetails.setMmEC_6(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_6))));
				CustDetails.setMmFC_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FC_1))));
				CustDetails.setMmFC_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FC_2))));
				CustDetails.setMmTEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_TEc_Slab))));
				CustDetails.setMmEC_FL_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL_1))));
				CustDetails.setMmEC_FL_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL_2))));
				CustDetails.setMmEC_FL_3(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL_3))));
				CustDetails.setMmEC_FL_4(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL_4))));
				CustDetails.setMmEC_FL(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL))));
				CustDetails.setMmnew_TEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_new_TEc))));
				CustDetails.setMmold_TEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_old_TEc))));



			}//Cursor IF END
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("punit", e.toString());
			//throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}

	}
	//Nitish 24-02-2014 //Get ConnectionId and RRNo
	public AutoDDLAdapter GetConnIdRRNo() throws Exception
	{
		AutoDDLAdapter conList = null;
		cr=null ;
		try
		{
			QueryParameters qParam = StoredProcedure.GetConnIdRRNo();
			cr = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());		
			for(int i=1; i<=cr.getCount();++i)
			{
				if(conList == null)
				{
					conList = new AutoDDLAdapter(mcntx, new ArrayList<DDLItem>(), 5);
				}
				if(i==1)
					cr.moveToFirst();
				else
					cr.moveToNext();		
				conList.AddItem(cr.getString(cr.getColumnIndex("ConnId")), cr.getString(cr.getColumnIndex("ConnIdRRNo")));

			}
		} catch (Exception e) {
			// TODO: handle exception
			//throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return conList;
	}





	//punit 25022014
	public void GetPrev(String custId ) throws Exception
	{
		ReadSlabNTarifSbmBillCollection CustDetails = BillingObject.GetBillingObject();

		cr=null ;
		try
		{
			QueryParameters qParam = StoredProcedure.GetPrev(custId);
			cr = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());	
			if(cr.getCount() > 0)//Cursor IF Start
			{
				//for(int i=1; i<=cr.getCount();++i)
				//{

				//if(i==1){ 
				cr.moveToFirst();



				CustDetails.setmAbFlag(cr.getString(cr.getColumnIndex(COL_AbFlag)));
				CustDetails.setmAccdRdg(cr.getString(cr.getColumnIndex(COL_AccdRdg)));
				CustDetails.setmAccdRdg_rtn(cr.getString(cr.getColumnIndex(COL_AccdRdg_rtn)));
				CustDetails.setmAddress1(cr.getString(cr.getColumnIndex(COL_Address1)));
				CustDetails.setmAddress2(cr.getString(cr.getColumnIndex(COL_Address2)));
				CustDetails.setmApplicationNo(cr.getString(cr.getColumnIndex(COL_ApplicationNo)));
				CustDetails.setmArears(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Arears))));
				CustDetails.setmArrearsOld(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_ArrearsOld))));
				CustDetails.setmAvgCons(cr.getString(cr.getColumnIndex(COL_AvgCons)));
				CustDetails.setmBankID(cr.getInt(cr.getColumnIndex(COL_BankID)));
				CustDetails.setmBatch_No(cr.getString(cr.getColumnIndex(COL_Batch_No)));
				CustDetails.setmBillable(cr.getString(cr.getColumnIndex(COL_Billable))); 
				CustDetails.setmBillDate(cr.getString(cr.getColumnIndex(COL_BillDate)));
				CustDetails.setmBillFor(cr.getString(cr.getColumnIndex(COL_BillFor)));
				CustDetails.setmBillNo(cr.getString(cr.getColumnIndex(COL_BillNo)));
				CustDetails.setmBillTotal(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_BillTotal))));
				CustDetails.setmBjKj2Lt2(cr.getString(cr.getColumnIndex(COL_BjKj2Lt2)));
				CustDetails.setmBlCnt(cr.getString(cr.getColumnIndex(COL_BlCnt)));
				CustDetails.setmBOBilled_Amount(cr.getString(cr.getColumnIndex(COL_BOBilled_Amount)));
				CustDetails.setmBOBillFlag(cr.getInt(cr.getColumnIndex(COL_BOBillFlag)));
				CustDetails.setmCapReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_CapReb))));
				CustDetails.setmChargetypeID(cr.getInt(cr.getColumnIndex(COL_ChargetypeID)));
				CustDetails.setmChequeDDDate(cr.getString(cr.getColumnIndex(COL_ChequeDDDate)));
				CustDetails.setmChequeDDNo(cr.getInt(cr.getColumnIndex(COL_ChequeDDNo)));
				CustDetails.setmConnectionNo(cr.getString(cr.getColumnIndex(COL_ConnectionNo)));
				CustDetails.setmConsPayable(cr.getString(cr.getColumnIndex(COL_ConsPayable)));
				CustDetails.setmCustomerName(cr.getString(cr.getColumnIndex(COL_CustomerName)));
				CustDetails.setmDateTime(cr.getString(cr.getColumnIndex(COL_DateTime)));
				CustDetails.setmDayWise_Flag(cr.getString(cr.getColumnIndex(COL_DayWise_Flag)));
				CustDetails.setmDemandChrg(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_DemandChrg))));
				CustDetails.setmDLAvgMin(cr.getString(cr.getColumnIndex(COL_DLAvgMin)));
				CustDetails.setmDlCount(cr.getInt(cr.getColumnIndex(COL_DlCount)));
				CustDetails.setmDlCount(cr.getInt(cr.getColumnIndex(COL_DlCount)));
				CustDetails.setmDLTEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_DLTEc))));
				CustDetails.setmDPdate(cr.getString(cr.getColumnIndex(COL_DPdate)));
				CustDetails.setmDrFees(cr.getString(cr.getColumnIndex(COL_DrFees)));
				CustDetails.setmDueDate(cr.getString(cr.getColumnIndex(COL_DueDate)));
				CustDetails.setmECReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_ECReb))));
				CustDetails.setmEcsFlag(cr.getString(cr.getColumnIndex(COL_EcsFlag)));
				CustDetails.setmExLoad(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_ExLoad))));
				CustDetails.setmFC_Slab_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FC_Slab_2))));
				CustDetails.setmFixedCharges(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FixedCharges))));
				CustDetails.setmFLReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FLReb))));
				CustDetails.setmForMonth(cr.getString(cr.getColumnIndex(COL_ForMonth)));
				CustDetails.setmGoKArrears(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_GoKArrears))));
				CustDetails.setmGoKPayable(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_GoKPayable))));
				CustDetails.setmGprs_Flag(cr.getInt(cr.getColumnIndex(COL_Gprs_Flag)));
				CustDetails.setmGps_Latitude_image(cr.getString(cr.getColumnIndex(COL_Gps_Latitude_image)));
				CustDetails.setmGps_Latitude_print(cr.getString(cr.getColumnIndex(COL_Gps_Latitude_print)));
				CustDetails.setmGps_LatitudeCardinal_image(cr.getString(cr.getColumnIndex(COL_Gps_LatitudeCardinal_image)));
				CustDetails.setmGps_LatitudeCardinal_print(cr.getString(cr.getColumnIndex(COL_Gps_LatitudeCardinal_print)));
				CustDetails.setmGps_Longitude_image(cr.getString(cr.getColumnIndex(COL_Gps_Longitude_image)));
				CustDetails.setmGps_Longitude_print(cr.getString(cr.getColumnIndex(COL_Gps_Longitude_print)));
				CustDetails.setmGps_LongitudeCardinal_image(cr.getString(cr.getColumnIndex(COL_Gps_LongitudeCardinal_image)));
				CustDetails.setmGps_LatitudeCardinal_print(cr.getString(cr.getColumnIndex(COL_Gps_LatitudeCardinal_print)));
				CustDetails.setmGvpId(cr.getString(cr.getColumnIndex(COL_GvpId)));
				CustDetails.setmHCReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_HCReb))));
				CustDetails.setmHLReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_HLReb))));
				CustDetails.setmHWCReb(cr.getString(cr.getColumnIndex(COL_HWCReb)));
				CustDetails.setmImage_Cap_Date(cr.getString(cr.getColumnIndex(COL_Image_Cap_Date)));
				CustDetails.setmImage_Cap_Time(cr.getString(cr.getColumnIndex(COL_Image_Cap_Time)));
				CustDetails.setmImage_Name(cr.getString(cr.getColumnIndex(COL_Image_Name)));
				CustDetails.setmImage_Path(cr.getString(cr.getColumnIndex(COL_Image_Path)));
				CustDetails.setmIntrst_Unpaid(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Intrst_Unpaid))));
				CustDetails.setmIntrstCurnt(cr.getString(cr.getColumnIndex(COL_IntrstCurnt)));
				CustDetails.setmIntrstOld(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_IntrstOld))));
				CustDetails.setmIODD11_Remarks(cr.getString(cr.getColumnIndex(COL_IODD11_Remarks)));
				CustDetails.setmIODRemarks(cr.getString(cr.getColumnIndex(COL_IODRemarks)));
				CustDetails.setmIssueDateTime(cr.getString(cr.getColumnIndex(COL_IssueDateTime)));
				CustDetails.setmKVA_Consumption(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_KVA_Consumption))));
				CustDetails.setmKVAAssd_Cons(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_KVAAssd_Cons))));
				CustDetails.setmKVAFR(cr.getString(cr.getColumnIndex(COL_KVAFR)));
				CustDetails.setmKVAH_OldConsumption(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_KVAFR))));
				CustDetails.setmKVAIR(cr.getString(cr.getColumnIndex(COL_KVAIR)));
				CustDetails.setmLatitude(cr.getString(cr.getColumnIndex(COL_Latitude)));
				CustDetails.setmLatitude_Dir(cr.getString(cr.getColumnIndex(COL_Latitude_Dir)));
				CustDetails.setmLegFol(cr.getString(cr.getColumnIndex(COL_LegFol)));
				CustDetails.setmLinMin(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_LinMin))));
				CustDetails.setmLocationCode(cr.getString(cr.getColumnIndex(COL_LocationCode)));
				CustDetails.setmLongitude(cr.getString(cr.getColumnIndex(COL_Longitude)));
				CustDetails.setmLongitude_Dir(cr.getString(cr.getColumnIndex(COL_Longitude_Dir)));
				CustDetails.setmMCHFlag(cr.getString(cr.getColumnIndex(COL_MCHFlag)));
				CustDetails.setmMeter_serialno(cr.getString(cr.getColumnIndex(COL_Meter_serialno)));
				CustDetails.setmMeter_type(cr.getString(cr.getColumnIndex(COL_Meter_type)));
				CustDetails.setmMF(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_MF))));
				CustDetails.setmMobileNo(cr.getString(cr.getColumnIndex(COL_MobileNo)));
				CustDetails.setmMtd(cr.getString(cr.getColumnIndex(COL_Mtd)));
				CustDetails.setmMtrDisFlag(cr.getInt(cr.getColumnIndex(COL_MtrDisFlag)));
				CustDetails.setmNewNoofDays(cr.getString(cr.getColumnIndex(COL_NewNoofDays)));
				CustDetails.setmNoOfDays(cr.getString(cr.getColumnIndex(COL_NoOfDays)));
				CustDetails.setmOld_Consumption(cr.getString(cr.getColumnIndex(COL_Old_Consumption)));
				CustDetails.setmOldConnID(cr.getString(cr.getColumnIndex(COL_OldConnID)));
				CustDetails.setmOtherChargeLegend(cr.getString(cr.getColumnIndex(COL_OtherChargeLegend)));
				CustDetails.setmOthers(cr.getString(cr.getColumnIndex(COL_Others)));
				CustDetails.setmPaid_Amt(cr.getInt(cr.getColumnIndex(COL_Paid_Amt)));
				CustDetails.setmPayment_Mode(cr.getString(cr.getColumnIndex(COL_Payment_Mode)));
				CustDetails.setmPenExLd(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_PenExLd))));
				CustDetails.setmPF(cr.getString(cr.getColumnIndex(COL_PF)));
				CustDetails.setmPfPenAmt(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_PfPenAmt))));
				CustDetails.setmPreRead(cr.getString(cr.getColumnIndex(COL_PreRead)));
				CustDetails.setmPreStatus(cr.getString(cr.getColumnIndex(COL_PreStatus)));//current status
				CustDetails.setmPrevRdg(cr.getString(cr.getColumnIndex(COL_PrevRdg)));
				CustDetails.setmRcptCnt(cr.getInt(cr.getColumnIndex(COL_RcptCnt)));
				CustDetails.setmReaderCode(cr.getString(cr.getColumnIndex(COL_ReaderCode)));
				CustDetails.setmReadingDay(cr.getString(cr.getColumnIndex(COL_ReadingDay)));
				CustDetails.setmRebateFlag(cr.getString(cr.getColumnIndex(COL_RebateFlag)));
				CustDetails.setmReceipt_No(cr.getInt(cr.getColumnIndex(COL_Receipt_No)));
				CustDetails.setmReceiptAmnt(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_ReceiptAmnt))));
				CustDetails.setmReceiptDate(cr.getString(cr.getColumnIndex(COL_ReceiptDate)));
				CustDetails.setmReceipttypeflag(cr.getString(cr.getColumnIndex(COL_Receipttypeflag)));
				CustDetails.setmRecordDmnd(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_RecordDmnd))));
				CustDetails.setmRemarks(cr.getString(cr.getColumnIndex(COL_Remarks)));
				CustDetails.setmRRFlag(cr.getString(cr.getColumnIndex(COL_RRFlag)));
				CustDetails.setmRRNo(cr.getString(cr.getColumnIndex(COL_RRNo)));
				CustDetails.setmSancHp(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_SancHp))));
				CustDetails.setmSancKw(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_SancKw))));
				CustDetails.setmSancLoad(cr.getString(cr.getColumnIndex(COL_SancLoad)));
				CustDetails.setmSBMNumber(cr.getString(cr.getColumnIndex(COL_SBMNumber)));
				CustDetails.setmSectionName(cr.getString(cr.getColumnIndex(COL_SectionName)));
				CustDetails.setmSlowRtnPge(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_SlowRtnPge))));
				CustDetails.setmSpotSerialNo(cr.getInt(cr.getColumnIndex(COL_SpotSerialNo)));
				CustDetails.setmStatus(cr.getInt(cr.getColumnIndex(COL_Status)));//previous status
				CustDetails.setmSubId(cr.getString(cr.getColumnIndex(COL_SubId)));
				CustDetails.setmSupply_Points(cr.getInt(cr.getColumnIndex(COL_Supply_Points)));
				CustDetails.setmTariffCode(cr.getInt(cr.getColumnIndex(COL_TariffCode)));
				CustDetails.setmTarifString(cr.getString(cr.getColumnIndex(COL_TarifString)));     
				CustDetails.setmTaxAmt(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_TaxAmt))));
				CustDetails.setmTaxFlag(cr.getString(cr.getColumnIndex(COL_TaxFlag)));
				CustDetails.setmTcName(cr.getString(cr.getColumnIndex(COL_TcName)));
				CustDetails.setmTEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_TEc))));
				CustDetails.setmTFc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_TFc))));
				CustDetails.setmThirtyFlag(cr.getString(cr.getColumnIndex(COL_ThirtyFlag)));
				CustDetails.setmTourplanId(cr.getString(cr.getColumnIndex(COL_TourplanId)));
				CustDetails.setmTvmMtr(cr.getString(cr.getColumnIndex(COL_TvmMtr)));
				CustDetails.setmTvmPFtype(cr.getString(cr.getColumnIndex(COL_TvmPFtype)));
				//CustDetails.setmUIDR_Slab(cr.getInt(cr.getColumnIndex(COL_UID_Slab)));
				CustDetails.setmUnits(cr.getString(cr.getColumnIndex(COL_Units)));

				CustDetails.setMmUID(cr.getInt(cr.getColumnIndex(COL_UID_S_BcData)));//punit 25022014
				CustDetails.setmMeter_Present_Flag(cr.getString(cr.getColumnIndex(COL_METER_PRESENT_FLAG)) == null ? 0 : cr.getInt(cr.getColumnIndex(COL_METER_PRESENT_FLAG)));
				CustDetails.setmMtr_Not_Visible(cr.getString(cr.getColumnIndex(COL_MTR_NOT_VISIBLE)) == null ? 0 : cr.getInt(cr.getColumnIndex(COL_MTR_NOT_VISIBLE)));

				CustDetails.setmDLTEc_GoK(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_DLTEc_GoK))));//31-12-2014
				CustDetails.setmOldTecBill(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_OldTecBill))));//Nitish on 21-04-2016

				//30-07-2015
				CustDetails.setmAadharNo(cr.getString(cr.getColumnIndex(COL_AadharNo)));
				CustDetails.setmVoterIdNo(cr.getString(cr.getColumnIndex(COL_VoterIdNo)));				
				CustDetails.setmMtrMakeFlag(cr.getInt(cr.getColumnIndex(COL_MtrMakeFlag)));
				CustDetails.setmMtrBodySealFlag(cr.getInt(cr.getColumnIndex(COL_MtrBodySealFlag)));
				CustDetails.setmMtrTerminalCoverFlag(cr.getInt(cr.getColumnIndex(COL_MtrTerminalCoverFlag)));
				CustDetails.setmMtrTerminalCoverSealedFlag(cr.getInt(cr.getColumnIndex(COL_MtrTerminalCoverSealedFlag)));

				CustDetails.setmFACValue(cr.getString(cr.getColumnIndex(COL_FACValue)));//19-12-2015
				//CustDetails.setmUID(cr.getInt(cr.getColumnIndex(COL_UID_Slab)));
				//CustDetails.setMmConnectionNo(cr.getString(cr.getColumnIndex(COL_ConnectionNo_Slab)));
				//CustDetails.setMmRRNo(cr.getString(cr.getColumnIndex(COL_RRNo_Slab)));
				CustDetails.setMmECRate_Count(cr.getInt(cr.getColumnIndex(COL_ECRate_Count)));
				CustDetails.setMmECRate_Row(cr.getInt(cr.getColumnIndex(COL_ECRate_Row)));
				CustDetails.setMmFCRate_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FCRate_1))));
				CustDetails.setMmCOL_FCRate_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FCRate_2))));
				CustDetails.setMmUnits_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_1))));
				CustDetails.setMmUnits_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_2))));
				CustDetails.setMmUnits_3(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_3))));
				CustDetails.setMmUnits_4(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_4))));
				CustDetails.setMmUnits_5(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_5))));
				CustDetails.setMmUnits_6(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_6))));
				CustDetails.setMmEC_Rate_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_1))));
				CustDetails.setMmEC_Rate_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_2))));
				CustDetails.setMmEC_Rate_3(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_3))));
				CustDetails.setMmEC_Rate_4(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_4))));
				CustDetails.setMmEC_Rate_5(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_5))));
				CustDetails.setMmEC_Rate_6(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_6))));
				CustDetails.setMmEC_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_1))));
				CustDetails.setMmEC_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_2))));
				CustDetails.setMmEC_3(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_3))));
				CustDetails.setMmEC_4(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_4))));
				CustDetails.setMmEC_5(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_5))));
				CustDetails.setMmEC_6(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_6))));
				CustDetails.setMmFC_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FC_1))));
				CustDetails.setMmFC_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FC_2))));
				CustDetails.setMmTEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_TEc_Slab))));
				CustDetails.setMmEC_FL_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL_1))));
				CustDetails.setMmEC_FL_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL_2))));
				CustDetails.setMmEC_FL_3(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL_3))));
				CustDetails.setMmEC_FL_4(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL_4))));
				CustDetails.setMmEC_FL(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL))));
				CustDetails.setMmnew_TEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_new_TEc))));
				CustDetails.setMmold_TEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_old_TEc))));

				//punit end





				//anusha end

				//}
				//else
				//cr.moveToNext();	



				//}
			}//Cursor IF END
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("punit", e.toString());
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
	}
	//punit end
	public void GetValueOnBillingPage() throws Exception
	{
		ReadSlabNTarifSbmBillCollection CustDetails = BillingObject.GetBillingObject();

		cr=null ;
		try
		{
			QueryParameters qParam = StoredProcedure.GetValueOnBillingPage();
			cr = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());	
			if(cr.getCount() > 0)//Cursor IF Start
			{
				//for(int i=1; i<=cr.getCount();++i)
				//{

				//if(i==1){ 
				cr.moveToFirst();



				CustDetails.setmAbFlag(cr.getString(cr.getColumnIndex(COL_AbFlag)));
				CustDetails.setmAccdRdg(cr.getString(cr.getColumnIndex(COL_AccdRdg)));
				CustDetails.setmAccdRdg_rtn(cr.getString(cr.getColumnIndex(COL_AccdRdg_rtn)));
				CustDetails.setmAddress1(cr.getString(cr.getColumnIndex(COL_Address1)));
				CustDetails.setmAddress2(cr.getString(cr.getColumnIndex(COL_Address2)));
				CustDetails.setmApplicationNo(cr.getString(cr.getColumnIndex(COL_ApplicationNo)));
				CustDetails.setmArears(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Arears))));
				CustDetails.setmArrearsOld(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_ArrearsOld))));
				CustDetails.setmAvgCons(cr.getString(cr.getColumnIndex(COL_AvgCons)));
				CustDetails.setmBankID(cr.getInt(cr.getColumnIndex(COL_BankID)));
				CustDetails.setmBatch_No(cr.getString(cr.getColumnIndex(COL_Batch_No)));
				CustDetails.setmBillable(cr.getString(cr.getColumnIndex(COL_Billable))); 
				CustDetails.setmBillDate(cr.getString(cr.getColumnIndex(COL_BillDate)));
				CustDetails.setmBillFor(cr.getString(cr.getColumnIndex(COL_BillFor)));
				CustDetails.setmBillNo(cr.getString(cr.getColumnIndex(COL_BillNo)));
				CustDetails.setmBillTotal(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_BillTotal))));
				CustDetails.setmBjKj2Lt2(cr.getString(cr.getColumnIndex(COL_BjKj2Lt2)));
				CustDetails.setmBlCnt(cr.getString(cr.getColumnIndex(COL_BlCnt)));
				CustDetails.setmBOBilled_Amount(cr.getString(cr.getColumnIndex(COL_BOBilled_Amount)));
				CustDetails.setmBOBillFlag(cr.getInt(cr.getColumnIndex(COL_BOBillFlag)));
				CustDetails.setmCapReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_CapReb))));
				CustDetails.setmChargetypeID(cr.getInt(cr.getColumnIndex(COL_ChargetypeID)));
				CustDetails.setmChequeDDDate(cr.getString(cr.getColumnIndex(COL_ChequeDDDate)));
				CustDetails.setmChequeDDNo(cr.getInt(cr.getColumnIndex(COL_ChequeDDNo)));
				CustDetails.setmConnectionNo(cr.getString(cr.getColumnIndex(COL_ConnectionNo)));
				CustDetails.setmConsPayable(cr.getString(cr.getColumnIndex(COL_ConsPayable)));
				CustDetails.setmCustomerName(cr.getString(cr.getColumnIndex(COL_CustomerName)));
				CustDetails.setmDateTime(cr.getString(cr.getColumnIndex(COL_DateTime)));
				CustDetails.setmDayWise_Flag(cr.getString(cr.getColumnIndex(COL_DayWise_Flag)));
				CustDetails.setmDemandChrg(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_DemandChrg))));
				CustDetails.setmDLAvgMin(cr.getString(cr.getColumnIndex(COL_DLAvgMin)));
				CustDetails.setmDlCount(cr.getInt(cr.getColumnIndex(COL_DlCount)));
				CustDetails.setmDlCount(cr.getInt(cr.getColumnIndex(COL_DlCount)));
				CustDetails.setmDLTEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_DLTEc))));
				CustDetails.setmDPdate(cr.getString(cr.getColumnIndex(COL_DPdate)));
				CustDetails.setmDrFees(cr.getString(cr.getColumnIndex(COL_DrFees)));
				CustDetails.setmDueDate(cr.getString(cr.getColumnIndex(COL_DueDate)));
				CustDetails.setmECReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_ECReb))));
				CustDetails.setmEcsFlag(cr.getString(cr.getColumnIndex(COL_EcsFlag)));
				CustDetails.setmExLoad(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_ExLoad))));
				CustDetails.setmFC_Slab_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FC_Slab_2))));
				CustDetails.setmFixedCharges(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FixedCharges))));
				CustDetails.setmFLReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FLReb))));
				CustDetails.setmForMonth(cr.getString(cr.getColumnIndex(COL_ForMonth)));
				CustDetails.setmGoKArrears(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_GoKArrears))));
				CustDetails.setmGoKPayable(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_GoKPayable))));
				CustDetails.setmGprs_Flag(cr.getInt(cr.getColumnIndex(COL_Gprs_Flag)));
				CustDetails.setmGps_Latitude_image(cr.getString(cr.getColumnIndex(COL_Gps_Latitude_image)));
				CustDetails.setmGps_Latitude_print(cr.getString(cr.getColumnIndex(COL_Gps_Latitude_print)));
				CustDetails.setmGps_LatitudeCardinal_image(cr.getString(cr.getColumnIndex(COL_Gps_LatitudeCardinal_image)));
				CustDetails.setmGps_LatitudeCardinal_print(cr.getString(cr.getColumnIndex(COL_Gps_LatitudeCardinal_print)));
				CustDetails.setmGps_Longitude_image(cr.getString(cr.getColumnIndex(COL_Gps_Longitude_image)));
				CustDetails.setmGps_Longitude_print(cr.getString(cr.getColumnIndex(COL_Gps_Longitude_print)));
				CustDetails.setmGps_LongitudeCardinal_image(cr.getString(cr.getColumnIndex(COL_Gps_LongitudeCardinal_image)));
				CustDetails.setmGps_LatitudeCardinal_print(cr.getString(cr.getColumnIndex(COL_Gps_LatitudeCardinal_print)));
				CustDetails.setmGvpId(cr.getString(cr.getColumnIndex(COL_GvpId)));
				CustDetails.setmHCReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_HCReb))));
				CustDetails.setmHLReb(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_HLReb))));
				CustDetails.setmHWCReb(cr.getString(cr.getColumnIndex(COL_HWCReb)));
				CustDetails.setmImage_Cap_Date(cr.getString(cr.getColumnIndex(COL_Image_Cap_Date)));
				CustDetails.setmImage_Cap_Time(cr.getString(cr.getColumnIndex(COL_Image_Cap_Time)));
				CustDetails.setmImage_Name(cr.getString(cr.getColumnIndex(COL_Image_Name)));
				CustDetails.setmImage_Path(cr.getString(cr.getColumnIndex(COL_Image_Path)));
				CustDetails.setmIntrst_Unpaid(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Intrst_Unpaid))));
				CustDetails.setmIntrstCurnt(cr.getString(cr.getColumnIndex(COL_IntrstCurnt)));
				CustDetails.setmIntrstOld(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_IntrstOld))));
				CustDetails.setmIODD11_Remarks(cr.getString(cr.getColumnIndex(COL_IODD11_Remarks)));
				CustDetails.setmIODRemarks(cr.getString(cr.getColumnIndex(COL_IODRemarks)));
				CustDetails.setmIssueDateTime(cr.getString(cr.getColumnIndex(COL_IssueDateTime)));
				CustDetails.setmKVA_Consumption(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_KVA_Consumption))));
				CustDetails.setmKVAAssd_Cons(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_KVAAssd_Cons))));
				CustDetails.setmKVAFR(cr.getString(cr.getColumnIndex(COL_KVAFR)));
				CustDetails.setmKVAH_OldConsumption(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_KVAFR))));
				CustDetails.setmKVAIR(cr.getString(cr.getColumnIndex(COL_KVAIR)));
				CustDetails.setmLatitude(cr.getString(cr.getColumnIndex(COL_Latitude)));
				CustDetails.setmLatitude_Dir(cr.getString(cr.getColumnIndex(COL_Latitude_Dir)));
				CustDetails.setmLegFol(cr.getString(cr.getColumnIndex(COL_LegFol)));
				CustDetails.setmLinMin(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_LinMin))));
				CustDetails.setmLocationCode(cr.getString(cr.getColumnIndex(COL_LocationCode)));
				CustDetails.setmLongitude(cr.getString(cr.getColumnIndex(COL_Longitude)));
				CustDetails.setmLongitude_Dir(cr.getString(cr.getColumnIndex(COL_Longitude_Dir)));
				CustDetails.setmMCHFlag(cr.getString(cr.getColumnIndex(COL_MCHFlag)));
				CustDetails.setmMeter_serialno(cr.getString(cr.getColumnIndex(COL_Meter_serialno)));
				CustDetails.setmMeter_type(cr.getString(cr.getColumnIndex(COL_Meter_type)));
				CustDetails.setmMF(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_MF))));
				CustDetails.setmMobileNo(cr.getString(cr.getColumnIndex(COL_MobileNo)));
				CustDetails.setmMtd(cr.getString(cr.getColumnIndex(COL_Mtd)));
				CustDetails.setmMtrDisFlag(cr.getInt(cr.getColumnIndex(COL_MtrDisFlag)));
				CustDetails.setmNewNoofDays(cr.getString(cr.getColumnIndex(COL_NewNoofDays)));
				CustDetails.setmNoOfDays(cr.getString(cr.getColumnIndex(COL_NoOfDays)));
				CustDetails.setmOld_Consumption(cr.getString(cr.getColumnIndex(COL_Old_Consumption)));
				CustDetails.setmOldConnID(cr.getString(cr.getColumnIndex(COL_OldConnID)));
				CustDetails.setmOtherChargeLegend(cr.getString(cr.getColumnIndex(COL_OtherChargeLegend)));
				CustDetails.setmOthers(cr.getString(cr.getColumnIndex(COL_Others)));
				CustDetails.setmPaid_Amt(cr.getInt(cr.getColumnIndex(COL_Paid_Amt)));
				CustDetails.setmPayment_Mode(cr.getString(cr.getColumnIndex(COL_Payment_Mode)));
				CustDetails.setmPenExLd(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_PenExLd))));
				CustDetails.setmPF(cr.getString(cr.getColumnIndex(COL_PF)));
				CustDetails.setmPfPenAmt(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_PfPenAmt))));
				CustDetails.setmPreRead(cr.getString(cr.getColumnIndex(COL_PreRead)));
				CustDetails.setmPreStatus(cr.getString(cr.getColumnIndex(COL_Status)));
				CustDetails.setmPrevRdg(cr.getString(cr.getColumnIndex(COL_PrevRdg)));
				CustDetails.setmRcptCnt(cr.getInt(cr.getColumnIndex(COL_RcptCnt)));
				CustDetails.setmReaderCode(cr.getString(cr.getColumnIndex(COL_ReaderCode)));
				CustDetails.setmReadingDay(cr.getString(cr.getColumnIndex(COL_ReadingDay)));
				CustDetails.setmRebateFlag(cr.getString(cr.getColumnIndex(COL_RebateFlag)));
				CustDetails.setmReceipt_No(cr.getInt(cr.getColumnIndex(COL_Receipt_No)));
				CustDetails.setmReceiptAmnt(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_ReceiptAmnt))));
				CustDetails.setmReceiptDate(cr.getString(cr.getColumnIndex(COL_ReceiptDate)));
				CustDetails.setmReceipttypeflag(cr.getString(cr.getColumnIndex(COL_Receipttypeflag)));
				CustDetails.setmRecordDmnd(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_RecordDmnd))));
				CustDetails.setmRemarks(cr.getString(cr.getColumnIndex(COL_Remarks)));
				CustDetails.setmRRFlag(cr.getString(cr.getColumnIndex(COL_RRFlag)));
				CustDetails.setmRRNo(cr.getString(cr.getColumnIndex(COL_RRNo)));
				CustDetails.setmSancHp(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_SancHp))));
				CustDetails.setmSancKw(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_SancKw))));
				CustDetails.setmSancLoad(cr.getString(cr.getColumnIndex(COL_SancLoad)));
				CustDetails.setmSBMNumber(cr.getString(cr.getColumnIndex(COL_SBMNumber)));
				CustDetails.setmSectionName(cr.getString(cr.getColumnIndex(COL_SectionName)));
				CustDetails.setmSlowRtnPge(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_SlowRtnPge))));
				CustDetails.setmSpotSerialNo(cr.getInt(cr.getColumnIndex(COL_SpotSerialNo)));
				CustDetails.setmStatus(cr.getInt(cr.getColumnIndex(COL_Status)));
				CustDetails.setmSubId(cr.getString(cr.getColumnIndex(COL_SubId)));
				CustDetails.setmSupply_Points(cr.getInt(cr.getColumnIndex(COL_Supply_Points)));           
				CustDetails.setmTariffCode(cr.getInt(cr.getColumnIndex(COL_TariffCode)));
				CustDetails.setmTarifString(cr.getString(cr.getColumnIndex(COL_TarifString)));     
				CustDetails.setmTaxAmt(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_TaxAmt))));
				CustDetails.setmTaxFlag(cr.getString(cr.getColumnIndex(COL_TaxFlag)));
				CustDetails.setmTcName(cr.getString(cr.getColumnIndex(COL_TcName)));
				CustDetails.setmTEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_TEc))));
				CustDetails.setmTFc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_TFc))));
				CustDetails.setmThirtyFlag(cr.getString(cr.getColumnIndex(COL_ThirtyFlag)));
				CustDetails.setmTourplanId(cr.getString(cr.getColumnIndex(COL_TourplanId)));
				CustDetails.setmTvmMtr(cr.getString(cr.getColumnIndex(COL_TvmMtr)));
				CustDetails.setmTvmPFtype(cr.getString(cr.getColumnIndex(COL_TvmPFtype)));
				//CustDetails.setmUIDR_Slab(cr.getInt(cr.getColumnIndex(COL_UID_Slab)));
				CustDetails.setmUnits(cr.getString(cr.getColumnIndex(COL_Units)));

				CustDetails.setMmUID(cr.getInt(cr.getColumnIndex(COL_UID_S_BcData)));//punit 25022014
				CustDetails.setmMeter_Present_Flag(cr.getString(cr.getColumnIndex(COL_METER_PRESENT_FLAG)) == null ? 0 : cr.getInt(cr.getColumnIndex(COL_METER_PRESENT_FLAG)));
				CustDetails.setmMtr_Not_Visible(cr.getString(cr.getColumnIndex(COL_MTR_NOT_VISIBLE)) == null ? 0 : cr.getInt(cr.getColumnIndex(COL_MTR_NOT_VISIBLE)));

				CustDetails.setmDLTEc_GoK(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_DLTEc_GoK))));//31-12-2014
				CustDetails.setmOldTecBill(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_OldTecBill))));//Nitish on 21-04-2016
				//30-07-2015
				CustDetails.setmAadharNo(cr.getString(cr.getColumnIndex(COL_AadharNo)));
				CustDetails.setmVoterIdNo(cr.getString(cr.getColumnIndex(COL_VoterIdNo)));				
				CustDetails.setmMtrMakeFlag(cr.getInt(cr.getColumnIndex(COL_MtrMakeFlag)));
				CustDetails.setmMtrBodySealFlag(cr.getInt(cr.getColumnIndex(COL_MtrBodySealFlag)));
				CustDetails.setmMtrTerminalCoverFlag(cr.getInt(cr.getColumnIndex(COL_MtrTerminalCoverFlag)));
				CustDetails.setmMtrTerminalCoverSealedFlag(cr.getInt(cr.getColumnIndex(COL_MtrTerminalCoverSealedFlag)));
				CustDetails.setmFACValue(cr.getString(cr.getColumnIndex(COL_FACValue)));//19-12-2015
				//CustDetails.setmUID(cr.getInt(cr.getColumnIndex(COL_UID_Slab)));
				//CustDetails.setMmConnectionNo(cr.getString(cr.getColumnIndex(COL_ConnectionNo_Slab)));
				//CustDetails.setMmRRNo(cr.getString(cr.getColumnIndex(COL_RRNo_Slab)));
				CustDetails.setMmECRate_Count(cr.getInt(cr.getColumnIndex(COL_ECRate_Count)));
				CustDetails.setMmECRate_Row(cr.getInt(cr.getColumnIndex(COL_ECRate_Row)));
				CustDetails.setMmFCRate_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FCRate_1))));
				CustDetails.setMmCOL_FCRate_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FCRate_2))));
				CustDetails.setMmUnits_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_1))));
				CustDetails.setMmUnits_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_2))));
				CustDetails.setMmUnits_3(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_3))));
				CustDetails.setMmUnits_4(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_4))));
				CustDetails.setMmUnits_5(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_5))));
				CustDetails.setMmUnits_6(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_Units_6))));
				CustDetails.setMmEC_Rate_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_1))));
				CustDetails.setMmEC_Rate_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_2))));
				CustDetails.setMmEC_Rate_3(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_3))));
				CustDetails.setMmEC_Rate_4(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_4))));
				CustDetails.setMmEC_Rate_5(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_5))));
				CustDetails.setMmEC_Rate_6(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_Rate_6))));
				CustDetails.setMmEC_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_1))));
				CustDetails.setMmEC_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_2))));
				CustDetails.setMmEC_3(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_3))));
				CustDetails.setMmEC_4(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_4))));
				CustDetails.setMmEC_5(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_5))));
				CustDetails.setMmEC_6(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_6))));
				CustDetails.setMmFC_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FC_1))));
				CustDetails.setMmFC_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_FC_2))));
				CustDetails.setMmTEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_TEc_Slab))));
				CustDetails.setMmEC_FL_1(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL_1))));
				CustDetails.setMmEC_FL_2(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL_2))));
				CustDetails.setMmEC_FL_3(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL_3))));
				CustDetails.setMmEC_FL_4(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL_4))));
				CustDetails.setMmEC_FL(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_EC_FL))));
				CustDetails.setMmnew_TEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_new_TEc))));
				CustDetails.setMmold_TEc(new BigDecimal(cr.getDouble(cr.getColumnIndex(COL_old_TEc))));

				//punit end





				//anusha end

				//}
				//else
				//cr.moveToNext();	



				//}
			}//Cursor IF END
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("punit", e.toString());
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}

	}
	//punit end

	//Tamilselvan on 14-03-2014
	public List<String> calldb()
	{
		List<String> alStr = new ArrayList<String>();
		Cursor crSlab = null;
		try
		{
			QueryParameters qParam = StoredProcedure.GetTariffSlab();
			crSlab = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			if(crSlab != null && crSlab.getCount() > 0)
			{
				crSlab.moveToFirst();
				do
				{
					alStr.add(crSlab.getString(crSlab.getColumnIndex(COL_TarifString)));
				}while(crSlab.moveToNext());
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return alStr;
	}
	/**
	 * Tamilselvan on 21-04-2014
	 * Check DataBase is Empty or not and If not empty then check any connection billed or not
	 * @return
	 */
	public boolean isAnyConnectionBilledOrEmpty()
	{
		boolean isBilledorEmpty = false;
		Cursor crValue = null;
		try
		{
			//Query to check Count of Connection
			QueryParameters qParam = StoredProcedure.GetCountBillCollDataMain();
			crValue = getReadableDatabase().rawQuery(qParam.getSql(), null);
			if(crValue != null && crValue.getCount() > 0)
			{
				crValue.moveToFirst();
				//if the count is not 0
				if(crValue.getInt(crValue.getColumnIndex("COUNT")) != 0)
				{
					//Check billed count
					QueryParameters qParamBill = StoredProcedure.GetCountforBilledConnection();
					crValue = getReadableDatabase().rawQuery(qParamBill.getSql(), null);
					if(crValue != null && crValue.getCount() > 0)
					{
						crValue.moveToFirst();
						//Billed count is > 0 
						if((crValue.getInt(crValue.getColumnIndex("COUNT"))) > 0)
						{
							isBilledorEmpty = true;
						}
						else if((crValue.getInt(crValue.getColumnIndex("COUNT"))) == 0)//billed count is 0
						{
							isBilledorEmpty = false;	
						}
					}
				}
				else//if the count is 0 then DB empty
				{
					isBilledorEmpty = false;				
				}
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return isBilledorEmpty;
	}
	/**
	 * Tamilselvan on 19-04-2014
	 * @return
	 */
	public int CheckFlagToLoadFile()
	{
		int rtrVal = 0, billedTotal = 0, gprsTotal = 0;
		boolean isValid = true;
		Cursor crValue = null;
		try
		{
			//Check status 9(File Downloaded)======================================================================
			if(isValid)
			{
				QueryParameters qParamFileDown = StoredProcedure.GetStatusMasterDetailsByIDCheck("9");//GetStatusMasterDetailsByID("9");
				crValue = getReadableDatabase().rawQuery(qParamFileDown.getSql(), qParamFileDown.getSelectionArgs());
				if(crValue != null && crValue.getCount() > 0)
				{
					crValue.moveToFirst();
					String downloadStatus = crValue.getString(crValue.getColumnIndex("Status"));
					if(downloadStatus == null)
					{
						rtrVal = 3;
						isValid = false;
					}
					else if(!downloadStatus.equals("1"))
					{
						rtrVal = 3;
						isValid = false;
					}
				}
			}
			//END Check status 9(File Downloaded)==================================================================
			crValue = null;
			//Check GPRS Sent all billed connection or not=========================================================
			if(isValid)
			{
				QueryParameters qParamBilled = StoredProcedure.GetCountforBilledConnection();
				crValue = getReadableDatabase().rawQuery(qParamBilled.getSql(), null);
				if(crValue != null && crValue.getCount() > 0)
				{
					crValue.moveToFirst();
					billedTotal = crValue.getInt(crValue.getColumnIndex("COUNT"));
				}
				crValue = null;
				QueryParameters qParamGPRSSent = StoredProcedure.GetCountGPRSSentConnection();
				crValue = getReadableDatabase().rawQuery(qParamGPRSSent.getSql(), null);
				if(crValue != null && crValue.getCount() > 0)
				{
					crValue.moveToFirst();
					gprsTotal = crValue.getInt(crValue.getColumnIndex("COUNT"));
				}
				if(gprsTotal < billedTotal)
				{
					rtrVal = 4;
					isValid = false;
				}
			}
			//END Check GPRS Sent all billed connection or not=====================================================
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return rtrVal;
	}

	/**
	 * Tamilselvan on 03-03-2014
	 * Insert Data from File into Database
	 * @param alrfp array list of ReadFileParameters
	 * @param rfpTariff
	 * @param mainHan Handler
	 * @param pb Progressbar
	 * @param lblStatus TestView
	 * @return
	 */
	public int InsertSBFromFile(String strDecrypt, Handler mainHan, final ProgressBar pb, final TextView lblStatus, long totalRecord)
	{
		Cursor crCheckCount = null;
		int Count = 0;
		long rtValue = 0;
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();
		//SQLiteDatabase sldb1 = dh.getWritableDatabase();
		InputStream ipStream = null;
		InputStreamReader ipStrReader = null;
		BufferedReader buffReader = null;
		try
		{
			ContentValues values = new ContentValues();//1
			pb.setProgress(0);
			pb.setMax((int)totalRecord);
			contentLength = totalRecord;
			mainHan.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					lblStatus.setText("");
				}
			});

			//DROP and RE-CREATE Tables in SBM_BillCollDataMain==============================================			
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_SBM_BillCollDataMain);
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_EC_FC_Slab);
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_ReadSlabNTariff);
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLECTION_TABLE);
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_CASHCOUNTER_DETAILS);			

			//sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_Billing_Details);//15-07-2016
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_EventLog);//15-07-2016

			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_HescomSurvey);//27-09-2016 punit 

			sldb.execSQL("CREATE TABLE SBM_BillCollDataMain(UID INTEGER PRIMARY KEY, ForMonth TEXT, BillDate TEXT, "+
					"SubId TEXT, ConnectionNo TEXT, CustomerName TEXT, TourplanId TEXT, BillNo TEXT, DueDate TEXT, "+
					"FixedCharges REAL, RebateFlag TEXT, ReaderCode TEXT, TariffCode INTEGER, ReadingDay TEXT, "+
					"PF TEXT, MF REAL, Status INTEGER, AvgCons TEXT, LinMin REAL, SancHp REAL, SancKw REAL,"+
					"SancLoad TEXT, PrevRdg TEXT, DlCount INTEGER, Arears REAL, IntrstCurnt REAL, DrFees REAL, "+
					"Others REAL, BillFor TEXT, BlCnt TEXT, RRNo TEXT, LegFol TEXT, TvmMtr TEXT, TaxFlag TEXT, "+
					"ArrearsOld REAL, Intrst_Unpaid REAL, IntrstOld REAL, Billable TEXT, NewNoofDays TEXT, "+
					"NoOfDays TEXT, HWCReb TEXT, DLAvgMin TEXT, TvmPFtype TEXT, AccdRdg TEXT, KVAIR TEXT, "+
					"DLTEc REAL, RRFlag TEXT, Mtd TEXT, SlowRtnPge REAL, OtherChargeLegend TEXT, GoKArrears REAL, "+
					"DPdate TEXT,  ReceiptAmnt REAL, ReceiptDate TEXT, TcName TEXT, ThirtyFlag TEXT, IODRemarks TEXT, "+
					"DayWise_Flag TEXT,  Old_Consumption TEXT, KVAAssd_Cons REAL, GvpId TEXT, BOBilled_Amount TEXT, "+
					"KVAH_OldConsumption REAL, EcsFlag TEXT, Supply_Points INTEGER, IODD11_Remarks TEXT, LocationCode TEXT, "+
					"BOBillFlag INTEGER, Address1 TEXT, Address2 TEXT, SectionName TEXT, OldConnID TEXT, MCHFlag TEXT, "+
					"FC_Slab_2 REAL, MobileNo TEXT, PreRead TEXT, PreStatus TEXT, SpotSerialNo INTEGER, Units TEXT, TFc REAL, "+
					"TEc REAL, FLReb REAL, ECReb REAL, TaxAmt REAL, PfPenAmt REAL, PenExLd REAL, HCReb REAL, HLReb REAL, "+
					"CapReb REAL, ExLoad REAL, DemandChrg REAL, AccdRdg_rtn TEXT, KVAFR TEXT, AbFlag TEXT, BjKj2Lt2 TEXT, "+
					"Remarks TEXT, GoKPayable REAL, IssueDateTime TEXT, RecordDmnd REAL, KVA_Consumption REAL, BillTotal REAL, "+
					"SBMNumber TEXT, RcptCnt INTEGER, Batch_No TEXT, Receipt_No INTEGER, DateTime TEXT, Payment_Mode TEXT, "+
					"Paid_Amt INTEGER, ChequeDDNo INTEGER, ChequeDDDate TEXT, Receipttypeflag TEXT, ApplicationNo TEXT, "+
					"ChargetypeID INTEGER, BankID INTEGER, Latitude TEXT, Latitude_Dir TEXT, Longitude TEXT, Longitude_Dir TEXT,"+ 
					"Gprs_Flag INTEGER, ConsPayable TEXT,MtrDisFlag INTEGER, Meter_type TEXT, Meter_serialno TEXT, "+
					"Gps_Latitude_image TEXT, Gps_LatitudeCardinal_image TEXT,  Gps_Longitude_image TEXT, "+
					"Gps_LongitudeCardinal_image TEXT, Gps_Latitude_print TEXT, Gps_LatitudeCardinal_print TEXT, "+
					"Gps_Longitude_print TEXT, Gps_LongitudeCardinal_print TEXT, Image_Name TEXT, Image_Path TEXT, "+
					"Image_Cap_Date TEXT, Image_Cap_Time TEXT , GPRS_Status TEXT, ReasonId INTEGER, Meter_Present_Flag INTEGER, " +
					"Mtr_Not_Visible INTEGER, DLTEc_GoK REAL ,AadharNo TEXT, VoterIdNo TEXT ," +
					"MtrLocFlag INTEGER,MtrMakeFlag INTEGER, MtrBodySealFlag INTEGER, MtrTerminalCoverFlag INTEGER , MtrTerminalCoverSealedFlag INTEGER,  FACValue TEXT, OldTecBill REAL);");/**/ // Modified 21-04-2016

			sldb.execSQL("CREATE TABLE EC_FC_Slab(UID INTEGER PRIMARY KEY, ConnectionNo TEXT, "+
					"RRNo TEXT, ECRate_Count INTEGER, ECRate_Row INTEGER, FCRate_1 REAL, "+
					"FCRate_2 REAL, Units_1 REAL, Units_2 REAL, Units_3 REAL, Units_4 REAL, "+
					"Units_5 REAL, Units_6 REAL, EC_Rate_1 REAL, EC_Rate_2 REAL, "+
					"EC_Rate_3 REAL, EC_Rate_4 REAL, EC_Rate_5 REAL, EC_Rate_6 REAL, "+
					"EC_1 REAL, EC_2 REAL, EC_3 REAL, EC_4 REAL, EC_5 REAL, EC_6 REAl, "+
					"FC_1 REAL, FC_2 REAL, TEc REAL, EC_FL_1 REAL, EC_FL_2 REAL, "+
					"EC_FL_3 REAL, EC_FL_4 REAL, EC_FL REAL, new_TEc REAL, old_TEc REAL);");

			sldb.execSQL("CREATE TABLE ReadSlabNTariff(UID INTEGER PRIMARY KEY, TarifCode INTEGER, TarifString TEXT);");

			sldb.execSQL("CREATE TABLE Collection_TABLE(UID INTEGER PRIMARY KEY,ConnectionNo TEXT,RRNo TEXT,CustomerName TEXT,RcptCnt INTEGER," +
					"Batch_No TEXT,Receipt_No TEXT,DateTime TEXT,Payment_Mode TEXT,Arrears TEXT,BillTotal TEXT,Paid_Amt INTEGER," +
					"BankID INTEGER,ChequeDDNo INTEGER,ChequeDDDate TEXT,Receipttypeflag TEXT," +
					"GvpId TEXT,SBMNumber TEXT,LocationCode TEXT,Gprs_Flag INTEGER,ArrearsBill_Flag INTEGER," +
					"ReaderCode TEXT,GPRS_Status TEXT,IODRemarks TEXT);");

			sldb.execSQL("CREATE TABLE CashCounter_Details(UID INTEGER PRIMARY KEY,IMEINo TEXT,SIMNo TEXT,BatchDate TEXT,CashLimit TEXT," +
					"StartTime TEXT,EndTime TEXT,Batch_No TEXT,DateTime Text,LocationCode Text,CashCounterOpen TEXT,CounterCloseDateTime TEXT, ExtensionFlag TEXT , ExtensionDateTime TEXT);"); //06-08-2015


			//punit Hescom survey 27-09-2016
			sldb.execSQL("CREATE TABLE HescomSurvey(UID INTEGER PRIMARY KEY,RRNo TEXT,ConnectionId TEXT,CustomerName TEXT,ConnectionType TEXT," +
					"Phase TEXT,MeterMake TEXT,Model TEXT,MeterType Text,MeterBoxAvailability Text,TypeOfBox TEXT,MeterPlacement TEXT, HeightOfMeter TEXT , AvailabilityOfLineOfSiht TEXT ," +
					"floor TEXT,NoOfMeterAvailable TEXT,MeterDiemension TEXT,Remarks TEXT,Lattitude TEXT,Longitude TEXT,GPSFlag TEXT,ImagePath TEXT,DateTime TEXT,SlaveId TEXT,ComRJ11 TEXT,ComOptical TEXT,Protocol TEXT,OpticalReading TEXT,MeterSlNo TEXT,YearofManufacture TEXT,TransFormerName TEXT);"); //06-08-2015





			//sldb.execSQL("CREATE TABLE Billing_Details(UID INTEGER PRIMARY KEY,IMEINo TEXT,SIMNo TEXT,BatchDate TEXT," +
			//		"StartTime TEXT,EndTime TEXT,Batch_No TEXT,DateTime Text,LocationCode TEXT,BillingOpen Text,BillingCloseDateTime TEXT , ExtensionFlag TEXT , ExtensionDateTime TEXT);"); //15-07-2016

			//25-07-2016
			sldb.execSQL("CREATE TABLE TABLE_EventLog(UID INTEGER PRIMARY KEY,IMEINO TEXT,SIMNO TEXT,Flag TEXT,Description TEXT,DateTime TEXT , GPRSFlag INTEGER, GPRS TEXT);");

			sldb.beginTransaction();
			String FACValue = "";
			StringBuilder sb = new StringBuilder();
			ipStream = new ByteArrayInputStream(strDecrypt.getBytes());
			ipStrReader = new InputStreamReader(ipStream);
			buffReader = new BufferedReader(ipStrReader);
			Total = 0;
			//DROP and RE-CREATE Tables in SBM_BillCollDataMain==============================================		
			while((ReceiveStr = buffReader.readLine()) != null)					
			{
				//ReceiveStr = scStrDec.useDelimiter("\r\n").next();
				sb.delete(0, sb.length());							
				sb.append(ReceiveStr);

				if(sb.toString().contains("^^"))
				{
					//rt = true;
				}
				else if(sb.toString().contains("@@"))
				{
					if(sb.toString().contains("|"))
					{										
						String[] arrSubD1 = sb.toString().split("\\|");						
						//Update Rows in StatusMaster====================================================================

						ContentValues valuesUpdateVersion = new ContentValues();
						valuesUpdateVersion.put("Value", arrSubD1[0].replace("@", ""));
						int upVer = sldb.update("StatusMaster", valuesUpdateVersion, " StatusID = ? ", new String[]{"2"});//Version

						ContentValues valuesUpdateSubDivision = new ContentValues();
						valuesUpdateSubDivision.put("Value", (arrSubD1[1].replace("$", "")).trim());
						int upSubDiv = sldb.update("StatusMaster", valuesUpdateSubDivision, " StatusID = ? ", new String[]{"15"});//SubDivision

						//int upVer = UpdatestatusMaster("2", arrSubD1[0].replace("@", ""));
						//bdBackup.UpdatestatusMaster("2", arrSubD1[0].replace("@", ""));//Insert into Backup Database
						//int upSubDiv = UpdatestatusMaster("15", (arrSubD1[1].replace("$", "")).trim());
						//bdBackup.UpdatestatusMaster("15", (arrSubD1[1].replace("$", "")).trim());//Insert into Backup Database
						//END Update Rows in StatusMaster================================================================
						//Modified 19-12-2015
						//Example @@442|DEVANAHALLI#0.04$ 
						try
						{
							if(sb.toString().contains("#"))
							{
								ContentValues valuesUpdateSubDivisionnew = new ContentValues();
								valuesUpdateSubDivisionnew.put("Value", (arrSubD1[1].split("#")[0].trim()));
								int upSubDivnew = sldb.update("StatusMaster", valuesUpdateSubDivisionnew, " StatusID = ? ", new String[]{"15"});//SubDivision

								FACValue = arrSubD1[1].split("#")[1].replace("$", "").trim();	
							}
						}
						catch(Exception e)
						{
							Log.d(TAG, e.toString());
						}
					}


				}
				else if(sb.toString().contains("%%"))
				{
					ReadFileParameters.SlabNTariff snt = (new ReadFileParameters()).new SlabNTariff();
					String[] sntSplit = sb.toString().split("~");
					String[] sntSubSplit = sntSplit[0].toString().split("#");
					//INSERT Rows in ReadSlabNTariff=================================================================
					ContentValues valuesSNT = new ContentValues();
					valuesSNT.put("TarifCode", sntSubSplit[1]);
					valuesSNT.put("TarifString", sb.toString().trim());
					long tariffCount= sldb.insert("ReadSlabNTariff", null, valuesSNT);//
					//bdBackup.InsertSlabNTariffBackUpDB(sntSubSplit[1], sb.toString().trim());//Insert into Backup Database
					if(tariffCount <= 0)
					{	
						throw new Exception("Problem in Inserting ReadSlabNTariff");
					}
					//END INSERT Rows in ReadSlabNTariff=============================================================
				}
				else//Connection Insert
				{
					if(sb.toString().length() == 685 || sb.toString().length() == 1022)//String should contain 653 characters 31-12-2014
					{

						Total++;
						/*QueryParameters qParam = StoredProcedure.GetConnectionCount(sb.substring(14, 24));
						crCheckCount = sldb.rawQuery(qParam.getSql(), qParam.getSelectionArgs());
						if(crCheckCount != null && crCheckCount.getCount() > 0)
						{
							crCheckCount.moveToFirst();
							Count = crCheckCount.getInt(crCheckCount.getColumnIndex("COUNT"));
							if(Count == 0)//Count If 
							{*/
						values.clear();
						values.put("ForMonth", sb.substring(0, 4));//2
						values.put("BillDate", sb.substring(4, 12));//3
						values.put("SubId", sb.substring(12, 14));//4
						values.put("ConnectionNo", sb.substring(14, 24));//5
						values.put("CustomerName", sb.substring(24, 54));//6
						values.put("TourplanId", sb.substring(54, 63));//7
						values.put("BillNo", sb.substring(63, 74));//8
						values.put("DueDate", sb.substring(74, 82));//9
						values.put("FixedCharges", sb.substring(82, 89));//10
						values.put("RebateFlag", sb.substring(89, 90));//11
						values.put("ReaderCode", sb.substring(90, 100));//12
						values.put("TariffCode", sb.substring(100, 103));//13
						values.put("ReadingDay", sb.substring(103, 105));//14
						values.put("PF", sb.substring(105, 108));//15
						values.put("MF", sb.substring(108, 114));//16
						values.put("Status", sb.substring(114, 116));//17
						values.put("AvgCONs", sb.substring(116, 126));//18
						values.put("LinMin", sb.substring(126, 131));//19
						values.put("SancHp", sb.substring(131, 136));//20
						values.put("SancKw", sb.substring(136, 141));//21
						values.put("SancLoad", sb.substring(141, 146));//22
						values.put("PrevRdg", sb.substring(146, 158));//23
						values.put("DlCount", sb.substring(158, 159));//24
						values.put("Arears", sb.substring(159, 173));//25
						values.put("IntrstCurnt", sb.substring(173, 182));//26
						values.put("DrFees", sb.substring(182, 188));//27
						values.put("Others", sb.substring(188, 194));//28
						values.put("BillFor", sb.substring(194, 195));//29
						values.put("BlCnt", sb.substring(195, 196));//30

						//26-09-2015
						/*values.put("RRNo", sb.substring(196, 209));//31
						values.put("LegFol", sb.substring(209, 217));//32*/						
						//values.put("RRNo", sb.substring(196, 217));//31 //13+8=21

						values.put("RRNo", sb.substring(196, 209));//31 //13+8=21


						values.put("TvmMtr", sb.substring(217, 218));//33
						values.put("TaxFlag", sb.substring(218, 219));//34
						values.put("ArrearsOld", sb.substring(219, 228));//35
						values.put("Intrst_unpaid", sb.substring(228, 234));//36
						values.put("IntrstOld", sb.substring(234, 248));//37
						values.put("Billable", sb.substring(248, 249));//38
						values.put("NewNoofDays", sb.substring(249, 253));//39
						values.put("NoOfDays", sb.substring(253, 255));//40
						values.put("HWCReb", sb.substring(255, 258));//41
						values.put("DLAvgMin", sb.substring(258, 259));//42
						values.put("TvmPFtype", sb.substring(259, 260));//43
						values.put("AccdRdg", sb.substring(260, 272));//44
						values.put("KVAIR", sb.substring(272, 284));//45
						values.put("DLTEc", sb.substring(284, 296));//46
						values.put("RRFlag", sb.substring(296, 297));//47
						values.put("Mtd", sb.substring(297, 298));//48
						values.put("SlowRtnPge", sb.substring(298, 304));//49
						values.put("OtherChargeLegEND", sb.substring(304, 319));//50
						values.put("GoKArrears", sb.substring(319, 328));//51
						values.put("DPdate", sb.substring(328, 336));//52
						values.put("ReceiptAmnt", sb.substring(336, 348));//53
						values.put("ReceiptDate", sb.substring(348, 356));//54
						values.put("TcName", sb.substring(356, 373));//55
						values.put("ThirtyFlag", sb.substring(373, 374));//56
						values.put("IODRemarks", sb.substring(374, 424));//57
						values.put("DayWise_Flag", sb.substring(424, 425));//58
						values.put("Old_CONSUMptiON", sb.substring(425, 435));//59
						values.put("KVAAssd_Cons", sb.substring(435, 445));//60
						values.put("GvpId", sb.substring(445, 459));//61
						values.put("BOBilled_Amount", sb.substring(459, 471));//62
						values.put("KVAH_OldConsumption", sb.substring(471, 481));//63
						values.put("EcsFlag", sb.substring(481, 482));//64
						values.put("Supply_Points", sb.substring(482, 486));//65
						values.put("IODD11_Remarks", sb.substring(486, 516));//66
						values.put("LocationCode", sb.substring(516, 526));//67
						values.put("BOBillFlag", sb.substring(526, 527));//68
						values.put("Address1", sb.substring(527, 547));//69
						values.put("Address2", sb.substring(547, 577));//70
						values.put("SectionName", sb.substring(577, 597));//71
						values.put("OldConnID", sb.substring(597, 604));//72
						values.put("MCHFLag", sb.substring(604, 605));//73
						values.put("FC_Slab_2", sb.substring(605, 612));//74
						values.put("MobileNo", sb.substring(612, 642));//75

						//values.put("DLTEc_GoK", sb.substring(642,  sb.length()));//76 //31-12-2014
						values.put("DLTEc_GoK", sb.substring(642,  653));//76 //09-12-2017


						values.put("Gps_Latitude_image", sb.substring(653,  663));//78 //31-12-2014						
						//values.put("Gps_Longitude_image", sb.substring(663,  sb.length()));//79 //31-12-2014						


						values.put("Gps_Longitude_image", sb.substring(663,  673));//29-09-2016 //80
						values.put("DlCount", sb.substring(673, 675));//29-09-2016 //81
						values.put("MobileNo", sb.substring(675,  685));//29-09-2016 //82 //10 digits

						values.put("FACValue", FACValue.trim().toString());//79 //19-12-2015

						rtValue = sldb.insert("SBM_BillCollDataMain", null, values);//Insert into Main DB
						//bdBackup.InsertBillCollDataMainBackUpDB(sb.toString());//Insert into Backup Database
						if(rtValue <= 0)
						{	
							throw new Exception("Problem in Inserting SBM_BillCollDataMain");
						}
						if(rtValue <= 0)
						{
							mainHan.post(new Runnable() {								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									//getWritableDatabase().endTransaction();
									CustomToast.makeText(mcntx, "Problem in Importing Data from file to DataBase." , Toast.LENGTH_SHORT);
									//throw new Exception("Insertion failed for SBM_BillCollDataMain");
									try {
										throw new Exception();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});

						}
						mainHan.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								pb.setProgress((int)(Total));												
								lblStatus.setText("Records Inserted..." + Total+" out of "+contentLength);//((totalLength*100)/contentLength)+"%");
								//pb.setProgress(i);
								//lblStatus.setText("Records Inserted :"+i+" out of "+Total);
							}
						});
						//}//END Count If 
						//}
					} 
				}//END Connection Insert
			}//END While Loop /**/

			ContentValues valuesUpdateConnBilled = new ContentValues();
			valuesUpdateConnBilled.putNull("Status");
			sldb.update("StatusMaster", valuesUpdateConnBilled, " StatusID in (?)", new String[]{"8"});

			ContentValues valuesUpdateFileDownload = new ContentValues();
			valuesUpdateFileDownload.putNull("Status");
			sldb.update("StatusMaster", valuesUpdateFileDownload, " StatusID in (?)", new String[]{"9"});

			ContentValues valuesUpdateGPRS = new ContentValues();
			valuesUpdateGPRS.putNull("Status");
			sldb.update("StatusMaster", valuesUpdateGPRS, " StatusID in (?)", new String[]{"10"});

			ContentValues valuesUpdateFileInserted = new ContentValues();
			valuesUpdateFileInserted.put("Status", "1");
			sldb.update("StatusMaster", valuesUpdateFileInserted, " StatusID in (?)", new String[]{"7"});

			//UpdateStatusMasterDetailsByID("8", null, "");
			//UpdateStatusMasterDetailsByID("9", null, "");
			//UpdateStatusMasterDetailsByID("10", null, "");
			//UpdateStatusMasterDetailsByID("7", "1", "");//Update new file Loaded in StatusMaster Status = 1 where StatusID = 7

			sldb.setTransactionSuccessful();
			return 1;
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
			//sldb.endTransaction();
			return 0;
		}
		finally
		{
			if(buffReader != null)
			{
				try {
					buffReader.close();									
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ipStream != null)
			{
				try {
					ipStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			sldb.endTransaction();
		}
		//return rtValue;
	}
	//Created by Tamilselvan on 10-03-2014
	public int getCountofRecods()
	{
		Cursor crCount = null;
		int count = 0;
		QueryParameters qParam = StoredProcedure.GetCountBillCollDataMain();
		crCount = getReadableDatabase().rawQuery(qParam.getSql(), null);	
		if(crCount != null && crCount.getCount() > 0)//Cursor 1
		{
			crCount.moveToFirst();
			count = crCount.getInt(crCount.getColumnIndex("COUNT"));
		}
		return count;
	}



	/**
	 * Tamilselvan on 14-03-2014
	 * Update Gprs_Flag = 1 in SBM_BillCollDataMain by ConnectionNo
	 * @param ConnectionNo
	 * @return
	 */
	public String UpdateBlCnt(String ConnectionNo)
	{	
		String strValue = "";
		try
		{
			ContentValues valuesUpdateMaintbl = new ContentValues();
			valuesUpdateMaintbl.put("Gprs_Flag", "1");
			int up = getWritableDatabase().update("SBM_BillCollDataMain", valuesUpdateMaintbl, "ConnectionNo = ?", new String[]{ConnectionNo});
			strValue = GPRSFlag();
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return strValue;
	}

	/**
	 * Tamilselvan on 19-04-2014
	 * GET GPRS ===> Billed count and GPRS SENT Count
	 * @return
	 */
	public String GPRSFlag()
	{
		Cursor crCount = null;
		String strValue = "";
		try	
		{
			QueryParameters qParam1 = StoredProcedure.GetCountGPRSSentConnection();
			crCount = getReadableDatabase().rawQuery(qParam1.getSql(), null);
			if(crCount != null && crCount.getCount() > 0)
			{
				crCount.moveToFirst();
				strValue = crCount.getString(crCount.getColumnIndex("COUNT"));
			}
			strValue += "/";
			QueryParameters qParam = StoredProcedure.GetCountforBilledConnection();
			crCount = getReadableDatabase().rawQuery(qParam.getSql(), null);
			if(crCount != null && crCount.getCount() > 0)
			{
				crCount.moveToFirst();
				strValue += crCount.getString(crCount.getColumnIndex("COUNT"));
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return strValue;
	}
	/**
	 * Tamilselvan on 19-04-2014
	 * GET Billed Count and Total Connection Count
	 * @return
	 */
	public String ConnectionBilledFlag()
	{
		Cursor crCount = null;
		String strValue = "";
		try	
		{
			QueryParameters qParam = StoredProcedure.GetCountforBilledConnection();
			crCount = getReadableDatabase().rawQuery(qParam.getSql(), null);
			if(crCount != null && crCount.getCount() > 0)
			{
				strValue = crCount.getString(crCount.getColumnIndex("COUNT"));
			}
			strValue += "/";
			QueryParameters qParam1 = StoredProcedure.GetCountBillCollDataMain();
			crCount = getReadableDatabase().rawQuery(qParam1.getSql(), null);
			if(crCount != null && crCount.getCount() > 0)
			{
				strValue += crCount.getString(crCount.getColumnIndex("COUNT"));
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return strValue;		
	}
	//Tamilselvan on 15-03-2014
	public void UpdateRePrint()//Check reprint count and Increment the count for each reprint 
	{
		Cursor crCount = null;
		int count = 0;
		try
		{
			QueryParameters qParam = StoredProcedure.GetReprintCount();
			crCount = getReadableDatabase().rawQuery(qParam.getSql(), null);		
			if(crCount != null && crCount.getCount() > 0)
			{
				crCount.moveToFirst();
				count = Integer.valueOf(crCount.getString(crCount.getColumnIndex(COL_Count)));
			}
			else
			{
				count = 1;
			}
			ContentValues valuesUpdateMaintbl = new ContentValues();
			valuesUpdateMaintbl.put("Gprs_Flag", count);
			getWritableDatabase().update("SBM_BillCollDataMain", valuesUpdateMaintbl, null, null);
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
	}
	//Tamilselvan on 17-03-2014
	public boolean MovetoNextConnectionNo(String Uid)
	{
		Cursor crCount = null;
		int count = 0;
		boolean boolConn = true;
		try	
		{
			QueryParameters qParamCount = StoredProcedure.GetCountforNotBilledConnection();//Get Not Billed count
			crCount = getReadableDatabase().rawQuery(qParamCount.getSql(), null);	
			if(crCount != null && crCount.getCount() > 0)
			{
				crCount.moveToFirst();
				count = crCount.getInt(crCount.getColumnIndex("COUNT"));//Get Not Billed count
				if(count > 0)//Count Not Billed 
				{
					crCount = null;
					QueryParameters qParam = StoredProcedure.GetNextConnectionDetails(Uid);
					crCount = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());		
					if(crCount != null && crCount.getCount() > 0)
					{
						crCount.moveToFirst();
						GetAllDatafromDb(crCount.getString(crCount.getColumnIndex(COL_ConnectionNo)));
						boolConn = true;
					}
				}
				else//Get Not Billed count
				{
					boolConn = false;
				}
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return boolConn;
	}
	//Tamilselvan on 18-03-2014
	public int UpdatestatusMaster(String StatusId, String Value)
	{
		int update = 0, count = 0;
		Cursor crCount = null;
		try
		{			
			QueryParameters qParam = StoredProcedure.GetStatusMasterCountByID(StatusId);
			crCount = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());		
			if(crCount != null && crCount.getCount() > 0)
			{
				crCount.moveToFirst();
				count = crCount.getInt(crCount.getColumnIndex("COUNT"));
				if(count > 0)
				{
					ContentValues valuesUpdateSubDiv = new ContentValues();
					valuesUpdateSubDiv.put("Value", Value);
					update = getWritableDatabase().update("StatusMaster", valuesUpdateSubDiv, " StatusID = ? ", new String[]{StatusId});
				}
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return update;
	}
	//Tamilselvan on 29-03-2014
	public int UpdateStatusInStatusMaster(String StatusId, String Status)
	{
		int UpStatus = 0;
		try
		{
			ContentValues valuesUpdateSubDiv = new ContentValues();
			valuesUpdateSubDiv.put("Status", Status);
			UpStatus = getWritableDatabase().update("StatusMaster", valuesUpdateSubDiv, " StatusID in (?)", new String[]{StatusId});
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return UpStatus;
	}
	//Tamilselvan on 29-03-2014
	public int GetStatusMasterDetailsByID(String StatusId)
	{
		int Status = 0;
		Cursor crDetails = null;
		try
		{
			QueryParameters qParam = StoredProcedure.GetStatusMasterDetailsByID(StatusId);
			crDetails = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			if(crDetails != null && crDetails.getCount() > 0)
			{
				crDetails.moveToFirst();
				Status = crDetails.getInt(crDetails.getColumnIndex("Status"));
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return Status;
	}
	/**
	 * Update Status Master -- Status and Value by StatusID
	 * @param StatusId
	 * @param status
	 * @param value
	 * @return 
	 */
	public int UpdateStatusMasterDetailsByID(String StatusId, String status, String value)
	{
		int UpStatus = 0;
		try
		{
			ContentValues valuesUpdateSubDiv = new ContentValues();
			valuesUpdateSubDiv.put("Status", status);
			valuesUpdateSubDiv.put("Value", value);
			UpStatus = getWritableDatabase().update("StatusMaster", valuesUpdateSubDiv, " StatusID in (?)", new String[]{StatusId});
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return UpStatus;
	}

	/**
	 * Tamilselvan on 18-03-2014
	 * Get Subdivision Name
	 * @return subDiv
	 */
	public String GetSubDivisionName()
	{
		Cursor crCount = null;
		String subDiv = "";
		try
		{
			QueryParameters qParam = StoredProcedure.GetStatusMasterDetailsByID("15");
			crCount = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());		
			if(crCount != null && crCount.getCount() > 0)
			{
				crCount.moveToFirst();
				//crCount.moveToLast();
				subDiv = crCount.getString(crCount.getColumnIndex(COL_VALUE_STATUSMASTER));
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return subDiv;
	}
	//Tamilselvan on 02-04-2014
	//Checking UId exists in Database or not
	public boolean IsUIDExists(int uid)
	{
		Cursor crCount = null;
		boolean isExits = false;
		try
		{
			QueryParameters qParam = StoredProcedure.CheckUIDExists(uid);
			crCount = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());		
			if(crCount != null && crCount.getCount() > 0)
			{
				crCount.moveToFirst();
				if(crCount.getInt(crCount.getColumnIndex("COUNT")) > 0)
				{
					isExits = true;
				}
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return isExits;
	}
	//Tamilselvan on 05-04-2014
	//Check Every Connection Billed or Not
	public boolean isEveryConnectionBilled()
	{
		Cursor crBillCount = null, crBilledCount = null;
		boolean isBilled = false;
		int TotalBill = 0, TotalBilled = 0;
		try
		{
			//Get Total Connection===========================================================
			QueryParameters qParam = StoredProcedure.GetCountBillCollDataMain();
			crBillCount = getReadableDatabase().rawQuery(qParam.getSql(), null);		
			if(crBillCount != null && crBillCount.getCount() > 0)
			{
				crBillCount.moveToFirst();
				TotalBill = crBillCount.getInt(crBillCount.getColumnIndex("COUNT"));
			}
			//END Get Total Connection=======================================================

			//Get Total Connection===========================================================
			QueryParameters qParam1 = StoredProcedure.GetCountforBilledConnection();
			crBilledCount = getReadableDatabase().rawQuery(qParam1.getSql(), null);		
			if(crBilledCount != null && crBilledCount.getCount() > 0)
			{
				crBilledCount.moveToFirst();
				TotalBilled = crBilledCount.getInt(crBilledCount.getColumnIndex("COUNT"));
			}
			//END Get Total Connection=======================================================
			if(TotalBill == TotalBilled)
			{
				isBilled = true;
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return isBilled;
	}
	/**
	 * Tamilselvan on 29-04-2014
	 * get Photo name form DB by ConnectionNo
	 * @return
	 */
	public String GetPhotoNameByConnectionNo(String ConnectionNo)
	{
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getReadableDatabase();
		Cursor crImgName = null;
		String ImgName = "";
		try
		{
			QueryParameters qParam = StoredProcedure.GetPhotoNameByConnectionNo(ConnectionNo);//sql query for get count for billed connection 
			crImgName = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			if(crImgName != null && crImgName.getCount() > 0)
			{
				crImgName.moveToFirst();
				ImgName = crImgName.getString(crImgName.getColumnIndex("PhotoName"));
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return ImgName;
	}
	/**
	 * Tamilselvan on 12-03-2014 for Bill Save 
	 * @return 1 as Saved succussfully, 0 as problem
	 */
	public int BillSave()
	{
		int rtrValue = 0;
		try
		{
			DatabaseHelper dh = new DatabaseHelper(mcntx);
			SQLiteDatabase sldb = dh.getWritableDatabase();
			CommonFunction cFun = new CommonFunction();
			ReadSlabNTarifSbmBillCollection sbc = BillingObject.GetBillingObject();
			try
			{
				Cursor check = null;
				//Check For Serial Number===========================================================================
				QueryParameters qParam = StoredProcedure.GetCountforBilledConnection();//sql query for get count for billed connection 
				check = getReadableDatabase().rawQuery(qParam.getSql(), null);			
				if(check != null && check.getCount() > 0)
				{
					check.moveToFirst();
					if(check.getInt(check.getColumnIndex("COUNT")) == 0)//Nothing is Billed then 
					{//hard code 1 as serial number.
						sbc.setmSpotSerialNo(1);
					}
					else if(check.getInt(check.getColumnIndex("COUNT")) > 0)//get the max spot serial number make + 1 and assign
					{
						qParam = StoredProcedure.GetMaxSpotSerialNumber();//sql query for get count for billed connection 
						check = getReadableDatabase().rawQuery(qParam.getSql(), null);
						if(check != null && check.getCount() > 0)
						{
							check.moveToFirst();
							sbc.setmSpotSerialNo(check.getInt(check.getColumnIndex("SpotSerialNo")) + 1);
						}
					}
				}
				//END Check For Serial Number=======================================================================
				sbc.setmIssueDateTime(cFun.GetCurrentTimeWOSPChar());
				sbc.setmBlCnt("1");//Added on 09-06-2014 
				sldb.beginTransaction();
				ContentValues valuesUpdateMaintbl = new ContentValues();
				valuesUpdateMaintbl.put("SancHp", String.valueOf(sbc.getmSancHp()));
				valuesUpdateMaintbl.put("PF", sbc.getmPF());
				valuesUpdateMaintbl.put("LinMin", String.valueOf(sbc.getmLinMin()));
				valuesUpdateMaintbl.put("BlCnt", sbc.getmBlCnt());//set BlCnt as 1 (it is billed) 
				valuesUpdateMaintbl.put("PreRead", sbc.getmPreRead());//Present Reading
				valuesUpdateMaintbl.put("PreStatus", sbc.getmPreStatus());//Present Status
				valuesUpdateMaintbl.put("spotserialno", sbc.getmSpotSerialNo());//-->SpotSerialNo
				valuesUpdateMaintbl.put("units", sbc.getmUnits());//-->Consumption
				valuesUpdateMaintbl.put("TFc", String.valueOf(sbc.getmTFc()));
				valuesUpdateMaintbl.put("TEc", String.valueOf(sbc.getmTEc()));
				valuesUpdateMaintbl.put("FlReb", String.valueOf(sbc.getmFLReb()));
				valuesUpdateMaintbl.put("EcReb", String.valueOf(sbc.getmECReb()));
				valuesUpdateMaintbl.put("TaxAmt", String.valueOf(sbc.getmTaxAmt()));
				valuesUpdateMaintbl.put("PfPenAmt", String.valueOf(sbc.getmPfPenAmt()));
				valuesUpdateMaintbl.put("PenExLd", String.valueOf(sbc.getmPenExLd()));
				valuesUpdateMaintbl.put("HCReb", String.valueOf(sbc.getmHCReb()));
				valuesUpdateMaintbl.put("HLReb", String.valueOf(sbc.getmHLReb()));
				valuesUpdateMaintbl.put("CapReb", String.valueOf(sbc.getmCapReb()));
				valuesUpdateMaintbl.put("ExLoad", String.valueOf(sbc.getmExLoad()));
				valuesUpdateMaintbl.put("DemandChrg", String.valueOf(sbc.getmDemandChrg()));
				valuesUpdateMaintbl.put("AccdRdg_rtn", sbc.getmAccdRdg_rtn());
				valuesUpdateMaintbl.put("kVAFR", sbc.getmKVAFR());
				valuesUpdateMaintbl.put("AbFlag", sbc.getmAbFlag());
				valuesUpdateMaintbl.put("BjKj2Lt2", sbc.getmBjKj2Lt2());
				valuesUpdateMaintbl.put("Remarks", sbc.getmRemarks());
				valuesUpdateMaintbl.put("GoKPayable", String.valueOf(sbc.getmGoKPayable()));
				valuesUpdateMaintbl.put("IssueDateTime", sbc.getmIssueDateTime());
				valuesUpdateMaintbl.put("RecordDmnd", String.valueOf(sbc.getmRecordDmnd()));
				valuesUpdateMaintbl.put("KVA_Consumption", String.valueOf(sbc.getmKVA_Consumption()));
				valuesUpdateMaintbl.put("KVAAssd_Cons", String.valueOf(sbc.getmKVAAssd_Cons()));
				valuesUpdateMaintbl.put("TvmMtr", sbc.getmTvmMtr());
				valuesUpdateMaintbl.put("BillTotal", String.valueOf(sbc.getmBillTotal()));
				valuesUpdateMaintbl.put("SBMNumber", sbc.getmSBMNumber());
				valuesUpdateMaintbl.put("ConsPayable", sbc.getmConsPayable());
				valuesUpdateMaintbl.put("MtrDisFlag", sbc.getmMtrDisFlag());
				valuesUpdateMaintbl.put("Meter_type", sbc.getmMeter_type());
				valuesUpdateMaintbl.put("Meter_serialno", sbc.getmMeter_serialno());
				valuesUpdateMaintbl.put("Gps_Latitude_image", sbc.getmGps_Latitude_image());
				valuesUpdateMaintbl.put("Gps_Longitude_image", sbc.getmGps_Longitude_image());
				valuesUpdateMaintbl.put("Image_Name", sbc.getmImage_Name());

				//21-04-2016
				valuesUpdateMaintbl.put("OldTecBill", String.valueOf(sbc.getmOldTecBill().setScale(2, RoundingMode.HALF_UP))); //30-04-2015
				//30-08-2014				
				valuesUpdateMaintbl.put("Meter_Type", sbc.getmMeter_type());//Meter Type
				valuesUpdateMaintbl.put("MtrDisFlag", sbc.getmMtrDisFlag());//Meter Fixed
				valuesUpdateMaintbl.put("Meter_Present_Flag", sbc.getmMeter_Present_Flag()); //Installation
				valuesUpdateMaintbl.put("MtrMakeFlag", sbc.getmMtrMakeFlag());	//Meter Make
				valuesUpdateMaintbl.put("Mtr_Not_Visible", sbc.getmMtr_Not_Visible());	//Meter Location Inside or Outside					
				//30-07-2015				
				valuesUpdateMaintbl.put("AadharNo", sbc.getmAadharNo());
				valuesUpdateMaintbl.put("VoterIdNo", sbc.getmVoterIdNo());
				valuesUpdateMaintbl.put("MobileNo", sbc.getmMobileNo());
				valuesUpdateMaintbl.put("MtrBodySealFlag", sbc.getmMtrBodySealFlag()); //Yes or No --1 or 2
				valuesUpdateMaintbl.put("MtrTerminalCoverFlag", sbc.getmMtrTerminalCoverFlag());//Yes or No --1 or 2
				valuesUpdateMaintbl.put("MtrTerminalCoverSealedFlag", sbc.getmMtrTerminalCoverSealedFlag());//Yes or No --1 or 2

				int updateResult = sldb.update("SBM_BillCollDataMain", valuesUpdateMaintbl, "ConnectionNo = ?", new String[]{sbc.getmConnectionNo()});
				if(updateResult <= 0)
				{				
					sldb.endTransaction();
					throw new Exception("Updation Failed for SBM_BillCollDataMain");
				}
				ContentValues valuesInsertSlabtbl = new ContentValues();
				valuesInsertSlabtbl.put("ConnectionNo", sbc.getmConnectionNo());
				valuesInsertSlabtbl.put("RRNo", sbc.getmRRNo());
				valuesInsertSlabtbl.put("ECRate_Count", sbc.getMmECRate_Count());
				valuesInsertSlabtbl.put("ECRate_Row", sbc.getMmECRate_Row());
				valuesInsertSlabtbl.put("FCRate_1", String.valueOf(sbc.getMmFCRate_1())); 
				valuesInsertSlabtbl.put("FCRate_2", String.valueOf(sbc.getMmCOL_FCRate_2())); 
				valuesInsertSlabtbl.put("Units_1", String.valueOf(sbc.getMmUnits_1()));
				valuesInsertSlabtbl.put("Units_2", String.valueOf(sbc.getMmUnits_2()));
				valuesInsertSlabtbl.put("Units_3", String.valueOf(sbc.getMmUnits_3()));
				valuesInsertSlabtbl.put("Units_4", String.valueOf(sbc.getMmUnits_4()));
				valuesInsertSlabtbl.put("Units_5", String.valueOf(sbc.getMmUnits_5()));
				valuesInsertSlabtbl.put("Units_6", String.valueOf(sbc.getMmUnits_6()));
				valuesInsertSlabtbl.put("EC_Rate_1", String.valueOf(sbc.getMmEC_Rate_1()));
				valuesInsertSlabtbl.put("EC_Rate_2", String.valueOf(sbc.getMmEC_Rate_2()));
				valuesInsertSlabtbl.put("EC_Rate_3", String.valueOf(sbc.getMmEC_Rate_3()));
				valuesInsertSlabtbl.put("EC_Rate_4", String.valueOf(sbc.getMmEC_Rate_4()));
				valuesInsertSlabtbl.put("EC_Rate_5", String.valueOf(sbc.getMmEC_Rate_5()));
				valuesInsertSlabtbl.put("EC_Rate_6", String.valueOf(sbc.getMmEC_Rate_6()));
				valuesInsertSlabtbl.put("EC_1", String.valueOf(sbc.getMmEC_1()));
				valuesInsertSlabtbl.put("EC_2", String.valueOf(sbc.getMmEC_2()));
				valuesInsertSlabtbl.put("EC_3", String.valueOf(sbc.getMmEC_3()));
				valuesInsertSlabtbl.put("EC_4", String.valueOf(sbc.getMmEC_4()));
				valuesInsertSlabtbl.put("EC_5", String.valueOf(sbc.getMmEC_5()));
				valuesInsertSlabtbl.put("EC_6", String.valueOf(sbc.getMmEC_6()));
				valuesInsertSlabtbl.put("FC_1", String.valueOf(sbc.getMmFC_1()));
				valuesInsertSlabtbl.put("FC_2", String.valueOf(sbc.getMmFC_2()));
				valuesInsertSlabtbl.put("TEc", String.valueOf(sbc.getMmTEc()));
				valuesInsertSlabtbl.put("EC_FL_1", String.valueOf(sbc.getMmEC_FL_1()));
				valuesInsertSlabtbl.put("EC_FL_2", String.valueOf(sbc.getMmEC_FL_1()));
				valuesInsertSlabtbl.put("EC_FL_3", String.valueOf(sbc.getMmEC_FL_1()));
				valuesInsertSlabtbl.put("EC_FL_4", String.valueOf(sbc.getMmEC_FL_1()));
				valuesInsertSlabtbl.put("EC_FL", String.valueOf(sbc.getMmEC_FL()));
				valuesInsertSlabtbl.put("new_TEc",  String.valueOf(sbc.getMmnew_TEc()));
				valuesInsertSlabtbl.put("old_TEc", String.valueOf(sbc.getMmold_TEc()));
				long insertResult = sldb.insert("EC_FC_Slab", null, valuesInsertSlabtbl);
				if(insertResult <= 0)
				{	
					sldb.endTransaction();
					throw new Exception("Insertion failed for EC_FC_Slab");
				}
				ContentValues valuesUpdateSubDiv = new ContentValues();
				valuesUpdateSubDiv.put("Status", "1");
				sldb.update("StatusMaster", valuesUpdateSubDiv, " StatusID in (?)", new String[]{"8"});
				ContentValues valuesUpdateFileDownload = new ContentValues();
				valuesUpdateFileDownload.put("Status", "");
				sldb.update("StatusMaster", valuesUpdateFileDownload, " StatusID in (?)", new String[]{"9"});
				//UpdateStatusMasterDetailsByID("8", "1", "");
				sldb.setTransactionSuccessful();
				//DatabaseHelperBackupDB bdBackup = new DatabaseHelperBackupDB(mcntx);
				//bdBackup.BackupDBBillSave();//Back Up database save
				rtrValue = 1;
				//return 1;
			}
			catch(Exception e)
			{
				Log.d("", e.toString());
				//return 0;
				rtrValue = 0;
			}	
			finally
			{
				sldb.endTransaction();
			}
		}
		catch(Exception e)
		{
			Log.d("", e.toString());
		}	
		return rtrValue;
	}
	//Nitish 30-08-2014
	public DDLAdapter GetReason() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {
			QueryParameters qParam=StoredProcedure.GetReason();		
			cr=getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			for(int i=1; i<=cr.getCount();++i)
			{
				if(lList==null)
				{
					lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
				}
				if(i==1)
					cr.moveToFirst();
				else
					cr.moveToNext();
				lList.AddItem(String.valueOf(cr.getInt(cr.getColumnIndex(COL_REASONMASTER_REASONID))), cr.getString(cr.getColumnIndex(COL_REASONMASTER_REASON)));
			}
		} catch (Exception e) {			
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}
	//Modified 30-08-2014 added ReasonId
	public int InsertSBFromserver(String strDecrypt, Handler mainHan, final ProgressBar pb, final TextView lblStatus, long totalRecord)
	{

		Cursor crCheckCount = null;
		int Count = 0;
		long rtValue = 0;
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();
		//SQLiteDatabase sldb1 = dh.getWritableDatabase();
		InputStream ipStream = null;
		InputStreamReader ipStrReader = null;
		BufferedReader buffReader = null;

		try    
		{
			ContentValues values = new ContentValues();//1
			pb.setProgress(0);
			pb.setMax((int)totalRecord);
			contentLength = totalRecord;
			mainHan.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					lblStatus.setText("");
				}
			});
			//DROP and RE-CREATE Tables in SBM_BillCollDataMain==============================================			
			//DROP and RE-CREATE Tables in SBM_BillCollDataMain==============================================			
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_SBM_BillCollDataMain);
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_EC_FC_Slab);
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_ReadSlabNTariff);
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLECTION_TABLE);
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_CASHCOUNTER_DETAILS);			
			//sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_Billing_Details);//15-07-2016
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_EventLog);//15-07-2016

			sldb.execSQL("CREATE TABLE SBM_BillCollDataMain(UID INTEGER PRIMARY KEY, ForMonth TEXT, BillDate TEXT, "+
					"SubId TEXT, ConnectionNo TEXT, CustomerName TEXT, TourplanId TEXT, BillNo TEXT, DueDate TEXT, "+
					"FixedCharges REAL, RebateFlag TEXT, ReaderCode TEXT, TariffCode INTEGER, ReadingDay TEXT, "+
					"PF TEXT, MF REAL, Status INTEGER, AvgCons TEXT, LinMin REAL, SancHp REAL, SancKw REAL,"+
					"SancLoad TEXT, PrevRdg TEXT, DlCount INTEGER, Arears REAL, IntrstCurnt REAL, DrFees REAL, "+
					"Others REAL, BillFor TEXT, BlCnt TEXT, RRNo TEXT, LegFol TEXT, TvmMtr TEXT, TaxFlag TEXT, "+
					"ArrearsOld REAL, Intrst_Unpaid REAL, IntrstOld REAL, Billable TEXT, NewNoofDays TEXT, "+
					"NoOfDays TEXT, HWCReb TEXT, DLAvgMin TEXT, TvmPFtype TEXT, AccdRdg TEXT, KVAIR TEXT, "+
					"DLTEc REAL, RRFlag TEXT, Mtd TEXT, SlowRtnPge REAL, OtherChargeLegend TEXT, GoKArrears REAL, "+
					"DPdate TEXT,  ReceiptAmnt REAL, ReceiptDate TEXT, TcName TEXT, ThirtyFlag TEXT, IODRemarks TEXT, "+
					"DayWise_Flag TEXT,  Old_Consumption TEXT, KVAAssd_Cons REAL, GvpId TEXT, BOBilled_Amount TEXT, "+
					"KVAH_OldConsumption REAL, EcsFlag TEXT, Supply_Points INTEGER, IODD11_Remarks TEXT, LocationCode TEXT, "+
					"BOBillFlag INTEGER, Address1 TEXT, Address2 TEXT, SectionName TEXT, OldConnID TEXT, MCHFlag TEXT, "+
					"FC_Slab_2 REAL, MobileNo TEXT, PreRead TEXT, PreStatus TEXT, SpotSerialNo INTEGER, Units TEXT, TFc REAL, "+
					"TEc REAL, FLReb REAL, ECReb REAL, TaxAmt REAL, PfPenAmt REAL, PenExLd REAL, HCReb REAL, HLReb REAL, "+
					"CapReb REAL, ExLoad REAL, DemandChrg REAL, AccdRdg_rtn TEXT, KVAFR TEXT, AbFlag TEXT, BjKj2Lt2 TEXT, "+
					"Remarks TEXT, GoKPayable REAL, IssueDateTime TEXT, RecordDmnd REAL, KVA_Consumption REAL, BillTotal REAL, "+
					"SBMNumber TEXT, RcptCnt INTEGER, Batch_No TEXT, Receipt_No INTEGER, DateTime TEXT, Payment_Mode TEXT, "+
					"Paid_Amt INTEGER, ChequeDDNo INTEGER, ChequeDDDate TEXT, Receipttypeflag TEXT, ApplicationNo TEXT, "+
					"ChargetypeID INTEGER, BankID INTEGER, Latitude TEXT, Latitude_Dir TEXT, Longitude TEXT, Longitude_Dir TEXT,"+ 
					"Gprs_Flag INTEGER, ConsPayable TEXT,MtrDisFlag INTEGER, Meter_type TEXT, Meter_serialno TEXT, "+
					"Gps_Latitude_image TEXT, Gps_LatitudeCardinal_image TEXT,  Gps_Longitude_image TEXT, "+
					"Gps_LongitudeCardinal_image TEXT, Gps_Latitude_print TEXT, Gps_LatitudeCardinal_print TEXT, "+
					"Gps_Longitude_print TEXT, Gps_LongitudeCardinal_print TEXT, Image_Name TEXT, Image_Path TEXT, "+
					"Image_Cap_Date TEXT, Image_Cap_Time TEXT , GPRS_Status TEXT, ReasonId INTEGER, Meter_Present_Flag INTEGER, " +
					"Mtr_Not_Visible INTEGER, DLTEc_GoK REAL,AadharNo TEXT, VoterIdNo TEXT ," +
					"MtrLocFlag INTEGER,MtrMakeFlag INTEGER, MtrBodySealFlag INTEGER, MtrTerminalCoverFlag INTEGER , MtrTerminalCoverSealedFlag INTEGER ,FACValue TEXT , OldTecBill REAL);");/**/ // Modified 21-04-2016

			sldb.execSQL("CREATE TABLE EC_FC_Slab(UID INTEGER PRIMARY KEY, ConnectionNo TEXT, "+
					"RRNo TEXT, ECRate_Count INTEGER, ECRate_Row INTEGER, FCRate_1 REAL, "+
					"FCRate_2 REAL, Units_1 REAL, Units_2 REAL, Units_3 REAL, Units_4 REAL, "+
					"Units_5 REAL, Units_6 REAL, EC_Rate_1 REAL, EC_Rate_2 REAL, "+
					"EC_Rate_3 REAL, EC_Rate_4 REAL, EC_Rate_5 REAL, EC_Rate_6 REAL, "+
					"EC_1 REAL, EC_2 REAL, EC_3 REAL, EC_4 REAL, EC_5 REAL, EC_6 REAl, "+
					"FC_1 REAL, FC_2 REAL, TEc REAL, EC_FL_1 REAL, EC_FL_2 REAL, "+
					"EC_FL_3 REAL, EC_FL_4 REAL, EC_FL REAL, new_TEc REAL, old_TEc REAL);");

			sldb.execSQL("CREATE TABLE ReadSlabNTariff(UID INTEGER PRIMARY KEY, TarifCode INTEGER, TarifString TEXT);");

			sldb.execSQL("CREATE TABLE Collection_TABLE(UID INTEGER PRIMARY KEY,ConnectionNo TEXT,RRNo TEXT,CustomerName TEXT,RcptCnt INTEGER," +
					"Batch_No TEXT,Receipt_No TEXT,DateTime TEXT,Payment_Mode TEXT,Arrears TEXT,BillTotal TEXT,Paid_Amt INTEGER," +
					"BankID INTEGER,ChequeDDNo INTEGER,ChequeDDDate TEXT,Receipttypeflag TEXT," +
					"GvpId TEXT,SBMNumber TEXT,LocationCode TEXT,Gprs_Flag INTEGER,ArrearsBill_Flag INTEGER," +
					"ReaderCode TEXT,GPRS_Status TEXT,IODRemarks TEXT);");

			sldb.execSQL("CREATE TABLE CashCounter_Details(UID INTEGER PRIMARY KEY,IMEINo TEXT,SIMNo TEXT,BatchDate TEXT,CashLimit TEXT," +
					"StartTime TEXT,EndTime TEXT,Batch_No TEXT,DateTime Text,LocationCode Text,CashCounterOpen TEXT,CounterCloseDateTime TEXT, ExtensionFlag TEXT , ExtensionDateTime TEXT);"); //06-08-2015



			//sldb.execSQL("CREATE TABLE Billing_Details(UID INTEGER PRIMARY KEY,IMEINo TEXT,SIMNo TEXT,BatchDate TEXT," +
			//		"StartTime TEXT,EndTime TEXT,Batch_No TEXT,DateTime Text,LocationCode TEXT,BillingOpen Text,BillingCloseDateTime TEXT , ExtensionFlag TEXT , ExtensionDateTime TEXT);"); //15-07-2016

			//25-07-2016
			sldb.execSQL("CREATE TABLE TABLE_EventLog(UID INTEGER PRIMARY KEY,IMEINO TEXT,SIMNO TEXT,Flag TEXT,Description TEXT,DateTime TEXT , GPRSFlag INTEGER, GPRS TEXT);");

			sldb.beginTransaction();
			String FACValue = "";
			StringBuilder sb = new StringBuilder();
			ipStream = new ByteArrayInputStream(strDecrypt.getBytes());
			ipStrReader = new InputStreamReader(ipStream);
			buffReader = new BufferedReader(ipStrReader);
			Total = 0;
			//DROP and RE-CREATE Tables in SBM_BillCollDataMain==============================================		
			while((ReceiveStr = buffReader.readLine()) != null)					
			{
				//ReceiveStr = scStrDec.useDelimiter("\r\n").next();
				sb.delete(0, sb.length());							
				sb.append(ReceiveStr);

				if(sb.toString().contains("^^"))
				{
					//rt = true;
				}
				else if(sb.toString().contains("@@"))
				{
					if(sb.toString().contains("|"))
					{										
						String[] arrSubD1 = sb.toString().split("\\|");						
						//Update Rows in StatusMaster====================================================================

						ContentValues valuesUpdateVersion = new ContentValues();
						valuesUpdateVersion.put("Value", arrSubD1[0].replace("@", ""));
						int upVer = sldb.update("StatusMaster", valuesUpdateVersion, " StatusID = ? ", new String[]{"2"});//Version

						ContentValues valuesUpdateSubDivision = new ContentValues();
						valuesUpdateSubDivision.put("Value", (arrSubD1[1].replace("$", "")).trim());
						int upSubDiv = sldb.update("StatusMaster", valuesUpdateSubDivision, " StatusID = ? ", new String[]{"15"});//SubDivision

						//int upVer = UpdatestatusMaster("2", arrSubD1[0].replace("@", ""));
						//bdBackup.UpdatestatusMaster("2", arrSubD1[0].replace("@", ""));//Insert into Backup Database
						//int upSubDiv = UpdatestatusMaster("15", (arrSubD1[1].replace("$", "")).trim());
						//bdBackup.UpdatestatusMaster("15", (arrSubD1[1].replace("$", "")).trim());//Insert into Backup Database
						//END Update Rows in StatusMaster================================================================
						//Modified 19-12-2015
						//Example @@442|DEVANAHALLI#0.04$ 
						try
						{
							if(sb.toString().contains("#"))
							{
								ContentValues valuesUpdateSubDivisionnew = new ContentValues();
								valuesUpdateSubDivisionnew.put("Value", (arrSubD1[1].split("#")[0].trim()));
								int upSubDivnew = sldb.update("StatusMaster", valuesUpdateSubDivisionnew, " StatusID = ? ", new String[]{"15"});//SubDivision

								FACValue = arrSubD1[1].split("#")[1].replace("$", "").trim();	
							}
						}
						catch(Exception e)
						{
							Log.d(TAG, e.toString());
						}

					}

				}
				else if(sb.toString().contains("%%"))
				{
					ReadFileParameters.SlabNTariff snt = (new ReadFileParameters()).new SlabNTariff();
					String[] sntSplit = sb.toString().split("~");
					String[] sntSubSplit = sntSplit[0].toString().split("#");
					//INSERT Rows in ReadSlabNTariff=================================================================
					ContentValues valuesSNT = new ContentValues();
					valuesSNT.put("TarifCode", sntSubSplit[1]);
					valuesSNT.put("TarifString", sb.toString().trim());
					long tariffCount= sldb.insert("ReadSlabNTariff", null, valuesSNT);//
					//bdBackup.InsertSlabNTariffBackUpDB(sntSubSplit[1], sb.toString().trim());//Insert into Backup Database
					if(tariffCount <= 0)
					{	
						throw new Exception("Problem in Inserting ReadSlabNTariff");
					}
					//END INSERT Rows in ReadSlabNTariff=============================================================
				}
				else//Connection Insert
				{
					if(sb.toString().length() == 653)//String should contain 653 characters 31-12-2014
					{

						Total++;
						/*QueryParameters qParam = StoredProcedure.GetConnectionCount(sb.substring(14, 24));
						crCheckCount = sldb.rawQuery(qParam.getSql(), qParam.getSelectionArgs());
						if(crCheckCount != null && crCheckCount.getCount() > 0)
						{
							crCheckCount.moveToFirst();
							Count = crCheckCount.getInt(crCheckCount.getColumnIndex("COUNT"));
							if(Count == 0)//Count If 
							{*/
						values.clear();
						values.put("ForMonth", sb.substring(0, 4));//2
						values.put("BillDate", sb.substring(4, 12));//3
						values.put("SubId", sb.substring(12, 14));//4
						values.put("ConnectionNo", sb.substring(14, 24));//5
						values.put("CustomerName", sb.substring(24, 54));//6
						values.put("TourplanId", sb.substring(54, 63));//7
						values.put("BillNo", sb.substring(63, 74));//8
						values.put("DueDate", sb.substring(74, 82));//9
						values.put("FixedCharges", sb.substring(82, 89));//10
						values.put("RebateFlag", sb.substring(89, 90));//11
						values.put("ReaderCode", sb.substring(90, 100));//12
						values.put("TariffCode", sb.substring(100, 103));//13
						values.put("ReadingDay", sb.substring(103, 105));//14
						values.put("PF", sb.substring(105, 108));//15
						values.put("MF", sb.substring(108, 114));//16
						values.put("Status", sb.substring(114, 116));//17
						values.put("AvgCONs", sb.substring(116, 126));//18
						values.put("LinMin", sb.substring(126, 131));//19
						values.put("SancHp", sb.substring(131, 136));//20
						values.put("SancKw", sb.substring(136, 141));//21
						values.put("SancLoad", sb.substring(141, 146));//22
						values.put("PrevRdg", sb.substring(146, 158));//23
						values.put("DlCount", sb.substring(158, 159));//24
						values.put("Arears", sb.substring(159, 173));//25
						values.put("IntrstCurnt", sb.substring(173, 182));//26
						values.put("DrFees", sb.substring(182, 188));//27
						values.put("Others", sb.substring(188, 194));//28
						values.put("BillFor", sb.substring(194, 195));//29
						values.put("BlCnt", sb.substring(195, 196));//30

						//26-09-2015
						/*values.put("RRNo", sb.substring(196, 209));//31
						values.put("LegFol", sb.substring(209, 217));//32*/						
						//values.put("RRNo", sb.substring(196, 217));//31  //13+8 = 21
						values.put("RRNo", sb.substring(196, 209)); //09-01-2017

						values.put("TvmMtr", sb.substring(217, 218));//33
						values.put("TaxFlag", sb.substring(218, 219));//34
						values.put("ArrearsOld", sb.substring(219, 228));//35
						values.put("Intrst_unpaid", sb.substring(228, 234));//36
						values.put("IntrstOld", sb.substring(234, 248));//37
						values.put("Billable", sb.substring(248, 249));//38
						values.put("NewNoofDays", sb.substring(249, 253));//39
						values.put("NoOfDays", sb.substring(253, 255));//40
						values.put("HWCReb", sb.substring(255, 258));//41
						values.put("DLAvgMin", sb.substring(258, 259));//42
						values.put("TvmPFtype", sb.substring(259, 260));//43
						values.put("AccdRdg", sb.substring(260, 272));//44
						values.put("KVAIR", sb.substring(272, 284));//45
						values.put("DLTEc", sb.substring(284, 296));//46
						values.put("RRFlag", sb.substring(296, 297));//47
						values.put("Mtd", sb.substring(297, 298));//48
						values.put("SlowRtnPge", sb.substring(298, 304));//49
						values.put("OtherChargeLegEND", sb.substring(304, 319));//50
						values.put("GoKArrears", sb.substring(319, 328));//51
						values.put("DPdate", sb.substring(328, 336));//52
						values.put("ReceiptAmnt", sb.substring(336, 348));//53
						values.put("ReceiptDate", sb.substring(348, 356));//54
						values.put("TcName", sb.substring(356, 373));//55
						values.put("ThirtyFlag", sb.substring(373, 374));//56
						values.put("IODRemarks", sb.substring(374, 424));//57
						values.put("DayWise_Flag", sb.substring(424, 425));//58
						values.put("Old_CONSUMptiON", sb.substring(425, 435));//59
						values.put("KVAAssd_Cons", sb.substring(435, 445));//60
						values.put("GvpId", sb.substring(445, 459));//61
						values.put("BOBilled_Amount", sb.substring(459, 471));//62
						values.put("KVAH_OldConsumption", sb.substring(471, 481));//63
						values.put("EcsFlag", sb.substring(481, 482));//64
						values.put("Supply_Points", sb.substring(482, 486));//65
						values.put("IODD11_Remarks", sb.substring(486, 516));//66
						values.put("LocationCode", sb.substring(516, 526));//67
						values.put("BOBillFlag", sb.substring(526, 527));//68
						values.put("Address1", sb.substring(527, 547));//69
						values.put("Address2", sb.substring(547, 577));//70
						values.put("SectionName", sb.substring(577, 597));//71
						values.put("OldConnID", sb.substring(597, 604));//72
						values.put("MCHFLag", sb.substring(604, 605));//73
						values.put("FC_Slab_2", sb.substring(605, 612));//74
						values.put("MobileNo", sb.substring(612, 642));//75

						//values.put("DLTEc_GoK", sb.substring(642,  sb.length()));//76 //31-12-2014
						values.put("DLTEc_GoK", sb.substring(642,  653));//76 //31-12-2014
						values.put("FACValue", FACValue.trim().toString());//79 //19-12-2015

						rtValue = sldb.insert("SBM_BillCollDataMain", null, values);//Insert into Main DB
						//bdBackup.InsertBillCollDataMainBackUpDB(sb.toString());//Insert into Backup Database
						if(rtValue <= 0)
						{	
							throw new Exception("Problem in Inserting SBM_BillCollDataMain");
						}
						if(rtValue <= 0)
						{
							mainHan.post(new Runnable() {								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									//getWritableDatabase().endTransaction();
									CustomToast.makeText(mcntx, "Problem in Importing Data from file to DataBase." , Toast.LENGTH_SHORT);
									//throw new Exception("Insertion failed for SBM_BillCollDataMain");
									try {
										throw new Exception();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});

						}
						mainHan.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								pb.setProgress((int)(Total));												
								lblStatus.setText("Records Inserted..." + Total+" out of "+contentLength);//((totalLength*100)/contentLength)+"%");
								//pb.setProgress(i);
								//lblStatus.setText("Records Inserted :"+i+" out of "+Total);
							}
						});
						//}//END Count If 
						//}
					} 
				}//END Connection Insert
			}//END While Loop /**/

			ContentValues valuesUpdateConnBilled = new ContentValues();
			valuesUpdateConnBilled.putNull("Status");
			sldb.update("StatusMaster", valuesUpdateConnBilled, " StatusID in (?)", new String[]{"8"});

			ContentValues valuesUpdateFileDownload = new ContentValues();
			valuesUpdateFileDownload.putNull("Status");
			sldb.update("StatusMaster", valuesUpdateFileDownload, " StatusID in (?)", new String[]{"9"});

			ContentValues valuesUpdateGPRS = new ContentValues();
			valuesUpdateGPRS.putNull("Status");
			sldb.update("StatusMaster", valuesUpdateGPRS, " StatusID in (?)", new String[]{"10"});

			ContentValues valuesUpdateFileInserted = new ContentValues();
			valuesUpdateFileInserted.put("Status", "1");
			sldb.update("StatusMaster", valuesUpdateFileInserted, " StatusID in (?)", new String[]{"7"});

			//UpdateStatusMasterDetailsByID("8", null, "");
			//UpdateStatusMasterDetailsByID("9", null, "");
			//UpdateStatusMasterDetailsByID("10", null, "");
			//UpdateStatusMasterDetailsByID("7", "1", "");//Update new file Loaded in StatusMaster Status = 1 where StatusID = 7

			sldb.setTransactionSuccessful();
			return 1;
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
			//sldb.endTransaction();
			return 0;
		}
		finally
		{
			if(buffReader != null)
			{
				try {
					buffReader.close();									
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ipStream != null)
			{
				try {
					ipStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			sldb.endTransaction();
		}
		//return rtValue;
	}
	//Added 30-08-2014
	public DDLAdapter getMeterType() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {
			QueryParameters qParam=StoredProcedure.getMeterType();		
			cr=getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			for(int i=1; i<=cr.getCount();++i)
			{
				if(lList==null)
				{
					lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
				}
				if(i==1)
				{
					cr.moveToFirst();
					lList.AddItem("-1", "--SELECT--");
				}
				else
				{
					cr.moveToNext();
				}

				lList.AddItem(String.valueOf(cr.getInt(cr.getColumnIndex(COL_METERTYPEMASTER_METERTYPEID))), cr.getString(cr.getColumnIndex(COL_METERTYPEMASTER_METERTYPE)));

			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}
	//Added 30-08-2014
	public DDLAdapter getMeterPlacement() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {
			QueryParameters qParam=StoredProcedure.getMeterPlacement();		
			cr=getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			for(int i=1; i<=cr.getCount();++i)
			{
				if(lList==null)
				{
					lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
				}
				if(i==1)
				{
					cr.moveToFirst();
					lList.AddItem("-1", "--SELECT--");
				}
				else
				{
					cr.moveToNext();
				}

				lList.AddItem(String.valueOf(cr.getInt(cr.getColumnIndex(COL_METERPLACEMENTMASTER_UNIQUEID))), cr.getString(cr.getColumnIndex(COL_METERPLACEMENTMASTER_METERPLACEMENT)));

			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}
	//Added 30-08-2014
	public DDLAdapter getMeterCondition() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {
			QueryParameters qParam=StoredProcedure.getMeterCondition();		
			cr=getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			for(int i=1; i<=cr.getCount();++i)
			{
				if(lList==null)
				{
					lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
				}
				if(i==1)
				{
					cr.moveToFirst();
					lList.AddItem("-1", "--SELECT--");
				}
				else
				{
					cr.moveToNext();
				}

				lList.AddItem(String.valueOf(cr.getInt(cr.getColumnIndex(COL_METERCONDITION_UNIQUEID))), cr.getString(cr.getColumnIndex(COL_METERCONDITION_METERCONDITION)));

			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}
	//Added 30-08-2014
	public DDLAdapter getBatteryStatus() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {
			QueryParameters qParam=StoredProcedure.getBatteryStatus();		
			cr=getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			for(int i=1; i<=cr.getCount();++i)
			{
				if(lList==null)
				{
					lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
				}
				if(i==1)
				{
					cr.moveToFirst();
					lList.AddItem("-1", "--SELECT--");
				}
				else
				{
					cr.moveToNext();
				}

				lList.AddItem(String.valueOf(cr.getInt(cr.getColumnIndex(COL_BATTERYSTATUS_STATUSID))), cr.getString(cr.getColumnIndex(COL_BATTERYSTATUS_STATUS)));

			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}


	//25-07-2015
	public boolean CheckIfAllConnectionBilled()
	{
		Cursor crValue = null;
		int total=0,billtotal = 0;
		boolean billed = false;
		try
		{		

			QueryParameters qParamBilled = StoredProcedure.GetCountBillCollDataMainWithoutTC();
			crValue = getReadableDatabase().rawQuery(qParamBilled.getSql(), null);
			if(crValue != null && crValue.getCount() > 0)
			{
				crValue.moveToFirst();
				total = crValue.getInt(crValue.getColumnIndex("COUNT"));
			}
			crValue = null;
			QueryParameters qParamGPRSSent = StoredProcedure.GetCountforBilledBOBConnection();
			crValue = getReadableDatabase().rawQuery(qParamGPRSSent.getSql(), null);
			if(crValue != null && crValue.getCount() > 0)
			{
				crValue.moveToFirst();
				billtotal = crValue.getInt(crValue.getColumnIndex("COUNT"));
			}
			if(billtotal == total)
			{
				billed = true;
			}
			else
			{
				billed = false;
			}	

		}
		catch (Exception e) {
			// TODO: handle exception

		}
		finally
		{
			if(crValue!=null)
			{
				crValue.close();
			}
		}		
		return billed;
	}
	//29-07-2015
	public DDLAdapter getMeterTypeHESCOM() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {


			if(lList==null)
			{
				lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			}

			lList.AddItem("0", "--SELECT--");		
			lList.AddItem("1", "ELECTRO MECHANICAL METER");		
			lList.AddItem("2", "HIGH PRECESSION METER");
			lList.AddItem("3", "STATIC/ELECTRONIC/ETV  METER");
			lList.AddItem("4", "DC/NO  METER");


		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}

	public DDLAdapter getMeterFixedHESCOM() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {


			if(lList==null)
			{
				lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			}

			lList.AddItem("0", "--SELECT--");		
			lList.AddItem("1", "SINGLE PHASE METER");		
			lList.AddItem("2", "THREE PHASE METER");
			lList.AddItem("3", "DC/NO METER");


		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}
	public DDLAdapter getInstallationDetailsHescom() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {


			if(lList==null)
			{
				lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			}

			lList.AddItem("0", "--SELECT--");		
			lList.AddItem("1", "SINGLE PHASE INSTALLATION");		
			lList.AddItem("2", "THREE PHASE INSTALLATION");


		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}

	public DDLAdapter getInstallationStatusHescom() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {


			if(lList==null)
			{
				lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			}

			lList.AddItem("0", "--SELECT--");		
			lList.AddItem("1", "Accessible");		
			lList.AddItem("2", "Inaccessible");


		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}

	//Nitish 30-12-2016
	public DDLAdapter getMType() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {


			if(lList==null)
			{
				lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			}

			lList.AddItem("1", "L&T");
			lList.AddItem("9", "L&T-DLMS");	
			lList.AddItem("7", "L&T-Others");			
			lList.AddItem("2", "L&G");	
			lList.AddItem("6", "L&G-DLMS");	
			lList.AddItem("3", "Secure - 3Phase");		
			lList.AddItem("4", "Secure - Saral");		
			lList.AddItem("5", "Secure - ICredit");					
			lList.AddItem("8", "HPL");				



		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}
	//Modified Nitish 18-09-2017
	public DDLAdapter getMeterMakeHESCOM() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {


			if(lList==null)
			{
				lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			}
			/*lList.AddItem("0", "--SELECT--");	
			lList.AddItem("1",	"DC/NOMETER");
			lList.AddItem("2",	"L & T"); 	
			lList.AddItem("3",	"SECURE");
			lList.AddItem("8",	"L & G");		
			lList.AddItem("41",	"Avon Meters Pvt.Ltd");
			lList.AddItem("42",	"Flash Electronics");			
			lList.AddItem("34",	"GENUS");	
			lList.AddItem("38",	"HPL");
			lList.AddItem("17",	"CAPITAL");
			lList.AddItem("43",	"Elymer International");
			lList.AddItem("44",	"MahaShakti Conductors");
			lList.AddItem("49",	"MASS"); 
			lList.AddItem("50",	"TGL"); 
			lList.AddItem("45",	"SARAF INDUSTRIES (BATHINDA)");
			lList.AddItem("46",	"MKI");
			lList.AddItem("47",	"BEM");
			lList.AddItem("48",	"TOWERS TRANSFORMERS CALCUTTA");			
			lList.AddItem("15",	"LANDIS");						
			lList.AddItem("52",	"RICHA");
			lList.AddItem("28",	"Alstom");
			lList.AddItem("53",	"Maxwell India");
			lList.AddItem("53",	"Bentex");
			lList.AddItem("51",	"Others");*/

			/*lList.AddItem("100", "EDMI");
			lList.AddItem("101", "ITRON");
			lList.AddItem("102", "MIM");
			lList.AddItem("103", "KRIZIK");
			lList.AddItem("104", "SMT");
			lList.AddItem("105", "Others");*/

			/*
			 lList.AddItem("9",	"ISKRA");
			lList.AddItem("5",	"ACTARIS");
			lList.AddItem("13",	"T.T.L");
			 lList.AddItem("4",	"BHEL");	
			 lList.AddItem("39",	"JAIPUR");			
			lList.AddItem("40",	"NAKODA"); 		
			lList.AddItem("7",	"AVON");		
			lList.AddItem("10",	"I M");
			lList.AddItem("11",	"SIEMENS");
			lList.AddItem("12",	"R.C");
			lList.AddItem("14",	"PRECESITION");		
			lList.AddItem("19",	"ELYMER");		
			lList.AddItem("18",	"HAVELLS");
			lList.AddItem("21",	"OMANI");
			lList.AddItem("22",	"ACCURATE");
			lList.AddItem("24",	"BHEK:()");
			lList.AddItem("26",	"OLAY");
			lList.AddItem("27",	"DATAK");
			lList.AddItem("28",	"Alstom");
			lList.AddItem("29",	"EMCO");					
			lList.AddItem("32",	"INDOTECH");
			lList.AddItem("37",	"RAMCO");*/

			//lList.AddItem("31",	"HIL");	
			//lList.AddItem("33",	"INDOTECK");
			//lList.AddItem("35",	"HTL");
			//lList.AddItem("36",	"ZCE");

			lList.AddItem("0", "--SELECT--");	
			lList.AddItem("1",	"DC/NOMETER");
			lList.AddItem("2",	"L & T"); 	
			lList.AddItem("3",	"SECURE");
			lList.AddItem("8",	"L & G");		
			lList.AddItem("34",	"GENUS");	
			lList.AddItem("38",	"HPL");
			lList.AddItem("17",	"CAPITAL");
			lList.AddItem("51",	"Others");




		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}
	//29-03-2016
	public DDLAdapter getMeterPhase() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {
			/*QueryParameters qParam=StoredProcedure.getMeterCondition();		
						cr=getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
						for(int i=1; i<=cr.getCount();++i)
						{
							if(lList==null)
							{
								lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
							}
							if(i==1)
							{
								cr.moveToFirst();
								lList.AddItem("-1", "--SELECT--");
							}
							else
							{
								cr.moveToNext();
							}

							lList.AddItem(String.valueOf(cr.getInt(cr.getColumnIndex(COL_METERCONDITION_UNIQUEID))), cr.getString(cr.getColumnIndex(COL_METERCONDITION_METERCONDITION)));

						}*/

			if(lList==null)
			{
				lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			}

			lList.AddItem("-1", "--SELECT--");
			lList.AddItem("1", "Single");
			lList.AddItem("3", "Three");



		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}

	public DDLAdapter getMainMeterType() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {
			/*QueryParameters qParam=StoredProcedure.getMeterCondition();		
						cr=getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
						for(int i=1; i<=cr.getCount();++i)
						{
							if(lList==null)
							{
								lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
							}
							if(i==1)
							{
								cr.moveToFirst();
								lList.AddItem("-1", "--SELECT--");
							}
							else
							{
								cr.moveToNext();
							}

							lList.AddItem(String.valueOf(cr.getInt(cr.getColumnIndex(COL_METERCONDITION_UNIQUEID))), cr.getString(cr.getColumnIndex(COL_METERCONDITION_METERCONDITION)));

						}*/

			if(lList==null)
			{
				lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			}


			/*lList.AddItem("1", "Mechanical");
				lList.AddItem("2", "Electro Static");
				lList.AddItem("3", "Electro Mechanical");
			 */
			//Modified 31-05-2016
			lList.AddItem("3", "Electro Mechanical");
			lList.AddItem("2", "Electro Static");
			lList.AddItem("1", "High Precision");



		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}

	//Nitish 15-07-2016 //Billing_Details Save 
	public int BillingDetailsSave(String imeino, String simno,String str[] )
	{		
		int rtvalue = 0;
		DatabaseHelper dh1 = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb1 = dh1.getWritableDatabase();	

		try
		{		

			sldb1.beginTransaction();
			ContentValues valuesInsertBillingDetails = new ContentValues();				

			//15072016|1000|2100|041815322016
			//sp[0] = 18042014 --BatchDate			
			//sp[1] = 1000 --Start Time
			//sp[2] = 2100 --End Time			
			//sp[3] = 041815322014 -datetime

			valuesInsertBillingDetails.put("IMEINo", imeino);
			valuesInsertBillingDetails.put("SIMNo", simno);	
			valuesInsertBillingDetails.put("BatchDate", str[0]);				
			valuesInsertBillingDetails.put("StartTime", str[1]);
			valuesInsertBillingDetails.put("EndTime ", str[2]);			
			valuesInsertBillingDetails.put("DateTime", str[3]);			
			valuesInsertBillingDetails.put("LocationCode",str[4]);			
			valuesInsertBillingDetails.put("BillingOpen", "1");				

			long insertResult = sldb1.insert("Billing_Details", null, valuesInsertBillingDetails);
			if(insertResult <= 0)
			{	
				sldb1.endTransaction();
				throw new Exception("Insertion failed for Billing_Details Table");
			}				
			sldb1.setTransactionSuccessful();
			rtvalue = 1;

		}
		catch(Exception e)
		{
			Log.d("", e.toString());
			rtvalue = 0;			
		}	
		finally
		{
			sldb1.endTransaction();
		}
		return rtvalue;		
	}	
	//Nitish 15-07-2016 //Set BillingOpen=0 for that BatchDate in Billing_Details table 
	public void CloseBillingDetailsFlag(String BatchDate)
	{		
		String currentdatetime = CommonFunction.GetCurrentDateTime(); 	
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();
		try
		{
			BatchDate = BatchDate.trim();
			ContentValues valuesUpdateColltbl = new ContentValues();
			valuesUpdateColltbl.put("BillingOpen", "0");
			valuesUpdateColltbl.put("BillingCloseDateTime", currentdatetime);
			//sldb.update("Collection_TABLE", valuesUpdateColltbl, "ConnectionNo = ?", new String[]{ConnectionNo});
			sldb.update("Billing_Details", valuesUpdateColltbl, "BatchDate = ? ", new String[]{BatchDate});

		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
	}
	//Nitish 29-07-2014 
	public void DropCreateBillingDetails()
	{
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();

		sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_Billing_Details);		

		sldb.execSQL("CREATE TABLE Billing_Details(UID INTEGER PRIMARY KEY,IMEINo TEXT,SIMNo TEXT,BatchDate TEXT," +
				"StartTime TEXT,EndTime TEXT,Batch_No TEXT,DateTime Text,LocationCode TEXT,BillingOpen Text,BillingCloseDateTime TEXT , ExtensionFlag TEXT , ExtensionDateTime TEXT);"); //15-07-2016
	}

	//////////////////////////////////////End Batch Generation///////////////////////////





	public String getTariffString(String Tarifcode)
	{
		Cursor crBat = null;
		String bat = "";
		try
		{
			QueryParameters qParam = StoredProcedure.getTariffString(Tarifcode);
			crBat = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());	
			if(crBat != null && crBat.getCount() > 0)//Cursor 1
			{
				crBat.moveToFirst();
				bat = crBat.getString(crBat.getColumnIndex("TarifString"));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			//throw e;
		}
		finally
		{
			if(crBat!=null)
			{
				crBat.close();
			}
		}		
		return bat;
	}
	//////////////////////////////////////////Survey//////////////////////////////////////////////	
	public DDLAdapter getFloorHESCOMSurvey() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {


			if(lList==null)
			{
				lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			}
			lList.AddItem("0", "--SELECT--");
			lList.AddItem("1",	"Basement-1");
			lList.AddItem("2",	"Basement-2");
			lList.AddItem("3",	"Basement-3");
			lList.AddItem("4",	"Basement-4");
			lList.AddItem("5",	"Ground Floor");
			lList.AddItem("6",	"First Floor");
			lList.AddItem("7",	"Second Floor");
			lList.AddItem("8",	"Third Floor");
			lList.AddItem("9",	"Fourth Floor");
			lList.AddItem("10",	"Fifth Floor");
			lList.AddItem("11",	"Other Floor");



		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}
	//Modified Nitish 18-09-2017
	public DDLAdapter getModelMaster(String metermake) throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {			


			if(lList==null)
			{
				lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			}
			lList.AddItem("0", "--SELECT--");
			//OLD Meter Models
			/*if(!metermake.equals("0"))
			{
				if(metermake.equals("2"))	//L&T
				{
					lList.AddItem("1",	"L&T");
					lList.AddItem("8",	"LT1ph");
					lList.AddItem("9",	"LT3ph");
				}
				else if(metermake.equals("3"))//Secure
				{

					lList.AddItem("3",	"Saral");				
					lList.AddItem("5",	"Secure3ph");					
					lList.AddItem("12",	"Icredit 1ph");

				}
				else if(metermake.equals("8"))//L&G
				{
					lList.AddItem("2",	"LGzce");				
					lList.AddItem("4",	"LG110c");				
					lList.AddItem("6",	"LG Dlms");
					lList.AddItem("7",	"LG3ph");			

				}
				else if(metermake.equals("38"))//HPL
				{
					lList.AddItem("10",	"HPL1ph");
					lList.AddItem("11",	"HPL3ph");			

				}
				else if(metermake.equals("17"))//Capital
				{					
					lList.AddItem("13",	"CAP DLMS 1ph");
					lList.AddItem("14",	"CAP DLMS 3ph");								
				}
				lList.AddItem("15",	"Others - 1Phase");
				lList.AddItem("16",	"Others - 3-Phase");
			}*/
			//Goa Meter Models
			if(!metermake.equals("0"))
			{
				if(metermake.equals("2"))	//L&T
				{				
					lList.AddItem("1",	"EM101+");
					lList.AddItem("2",	"ER300P");
					lList.AddItem("3",	"ZCE");					
					lList.AddItem("4",	"Others-LT1ph");
					lList.AddItem("5",	"Others-LT3ph");
				}
				else if(metermake.equals("3"))//Secure
				{

					lList.AddItem("6",	"Saral");				
					lList.AddItem("7",	"Secure3ph");					
					lList.AddItem("8",	"Icredit 1ph");
					lList.AddItem("9",	"Others-Secure 1ph");
					lList.AddItem("10",	"Others-Secure 3ph");

				}
				else if(metermake.equals("8"))//L&G
				{					

					lList.AddItem("11",	"LGzce");				
					lList.AddItem("12",	"LG110c");				
					lList.AddItem("13",	"LG Dlms");					
					lList.AddItem("14",	"E150 (DLMS)");
					lList.AddItem("15",	"IEC521");
					lList.AddItem("16",	"E250");
					lList.AddItem("17",	"E150");
					lList.AddItem("18",	"E110");
					lList.AddItem("19",	"E115");
					lList.AddItem("20",	"LGC");
					lList.AddItem("21",	"Others-LG1ph");	
					lList.AddItem("22",	"Others-LG3ph");					

				}
				else if(metermake.equals("38"))//HPL
				{					
					lList.AddItem("23",	"CL101+");
					lList.AddItem("24",	"SPEM01");
					lList.AddItem("25",	"SPEM20");
					lList.AddItem("26",	"SPEM26");
					lList.AddItem("27",	"SPEM36");
					lList.AddItem("28",	"Others-HPL1ph");
					lList.AddItem("29",	"Others-HPL3ph");	

				}
				else if(metermake.equals("17"))//Capital
				{
					lList.AddItem("30",	"Cl1");
					lList.AddItem("31",	"Cl12");
					lList.AddItem("52",	"Cs12"); //Added  27-10-2017
					lList.AddItem("32",	"Others CAP 1ph");
					lList.AddItem("33",	"Others CAP 3ph");								
				}
				else if(metermake.equals("13"))//TTL
				{
					lList.AddItem("34",	"M1");	
					lList.AddItem("35",	"Others TTL 1ph");
					lList.AddItem("36",	"Others TTL 3ph");	

				}
				else if(metermake.equals("9"))//ISKRA
				{
					lList.AddItem("37",	"T31C2");
					lList.AddItem("38",	"Others ISKRA 1ph");
					lList.AddItem("39",	"Others ISKRA 3ph");	

				}
				else if(metermake.equals("34"))//GENUS
				{
					lList.AddItem("40",	"01b");	
					lList.AddItem("53",	"03b");	//Added  27-10-2017
					lList.AddItem("41",	"Others GENUS 1ph");
					lList.AddItem("42",	"Others GENUS 3ph");	
				}	
				else if(metermake.equals("39"))//JAIPUR
				{
					lList.AddItem("43",	"JAIPUR");						
				}
				else if(metermake.equals("41"))//Avon 27-10-2017
				{
					lList.AddItem("44",	"AEM 106");	
					lList.AddItem("45",	"AEM 304");	
					lList.AddItem("46",	"AEM 306");	
					lList.AddItem("47",	"Trion");
					lList.AddItem("48",	"Others AVON 1ph");
					lList.AddItem("49",	"Others AVON 3ph");
				}
				else if(metermake.equals("42"))//FLASH 27-10-2017
				{				
					lList.AddItem("56",	"FE-S346");	
					lList.AddItem("57",	"FH-S126");
					lList.AddItem("58",	"FE-S346");
					lList.AddItem("59",	"SP-201");
					lList.AddItem("60",	"Others FLASH 1ph");
					lList.AddItem("61",	"Others FLASH 3ph");
				}
				else if(metermake.equals("43"))//Elymer 27-10-2017
				{

					lList.AddItem("62",	"EH-124");	
					lList.AddItem("63",	"Others ELYMER 1ph");
					lList.AddItem("64",	"Others ELYMER 3ph");
				}
				else if(metermake.equals("44"))//Mahashakti 27-10-2017
				{						
					lList.AddItem("65",	"MC-301");	
					lList.AddItem("66",	"Others Mahashakti 1ph");
					lList.AddItem("67",	"Others Mahashakti 3ph");
				}				
				else if(metermake.equals("49"))//MASS 27-10-2017
				{						
					lList.AddItem("68",	"MI-101");	
					lList.AddItem("69",	"Others MASS 1ph");
					lList.AddItem("70",	"Others MASS 3ph");
				}
				else if(metermake.equals("50"))//TGL 27-10-2017
				{						
					lList.AddItem("71",	"AT-121");	
					lList.AddItem("72",	"Others TGL 1ph");
					lList.AddItem("73",	"Others TGL 3ph");
				}
				else
				{
					lList.AddItem("50",	"Others - 1Phase");
					lList.AddItem("51",	"Others - 3-Phase");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}
	//03-03-2017
	public void CreateIfNotExistsHescomSurveyTable()
	{
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();

		sldb.execSQL("CREATE TABLE IF NOT EXISTS HescomSurvey(UID INTEGER PRIMARY KEY,RRNo TEXT,ConnectionId TEXT,CustomerName TEXT,ConnectionType TEXT," +
				"Phase TEXT,MeterMake TEXT,Model TEXT,MeterType Text,MeterBoxAvailability Text,TypeOfBox TEXT,MeterPlacement TEXT, HeightOfMeter TEXT , AvailabilityOfLineOfSiht TEXT ," +
				"floor TEXT,NoOfMeterAvailable TEXT,MeterDiemension TEXT,Remarks TEXT,Lattitude TEXT,Longitude TEXT,GPSFlag TEXT,ImagePath TEXT,DateTime TEXT,SlaveId TEXT,ComRJ11 TEXT,ComOptical TEXT,Protocol TEXT,OpticalReading TEXT,MeterSlNo TEXT,YearofManufacture TEXT,TransFormerName TEXT);"); //06-08-2015
	}

	public int insertSurveyDetails(SurveyDetails sd)
	{

		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();
		try
		{
			String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
			sldb.beginTransaction();
			ContentValues valuesInsertGPStbl = new ContentValues();
			valuesInsertGPStbl.put("RRNo", sd.getRRNo()==null?"0":sd.getRRNo());
			valuesInsertGPStbl.put("ConnectionId", sd.getConnectionId()==null?"0":sd.getConnectionId());	
			valuesInsertGPStbl.put("CustomerName", sd.getCustomerName()==null?"0":sd.getCustomerName());
			valuesInsertGPStbl.put("ConnectionType", sd.getConnectionType()==null?"0":sd.getConnectionType());			
			valuesInsertGPStbl.put("Phase", sd.getPhase()==null?"0":sd.getPhase());
			valuesInsertGPStbl.put("MeterMake", sd.getMeterMake()==null?"0":sd.getMeterMake());
			valuesInsertGPStbl.put("Model", sd.getModel()==null?"0":sd.getModel());
			valuesInsertGPStbl.put("MeterType", sd.getMeterType()==null?"0":sd.getMeterType());
			valuesInsertGPStbl.put("MeterBoxAvailability", sd.getMeterBoxAvailability()==null?"0":sd.getMeterBoxAvailability());	
			valuesInsertGPStbl.put("TypeOfBox", sd.getTypeOfBox()==null?"0":sd.getTypeOfBox());
			valuesInsertGPStbl.put("MeterPlacement", sd.getMeterPlacement()==null?"0":sd.getMeterPlacement());		
			valuesInsertGPStbl.put("HeightOfMeter", sd.getHeightOfMeter()==null?"0":sd.getHeightOfMeter().toString());
			valuesInsertGPStbl.put("AvailabilityOfLineOfSiht", sd.getAvailabilityOfLineOfSiht()==null?"0":sd.getAvailabilityOfLineOfSiht());
			valuesInsertGPStbl.put("floor", sd.getFloor()==null?"0":sd.getFloor());	
			valuesInsertGPStbl.put("NoOfMeterAvailable", sd.getNoOfMeterAvailable()==null?"0":sd.getNoOfMeterAvailable());
			valuesInsertGPStbl.put("MeterDiemension", sd.getMeterDiemension()==null?"0":sd.getMeterDiemension().toString());
			valuesInsertGPStbl.put("Remarks", sd.getRemarks()==null?"0":sd.getRemarks());
			valuesInsertGPStbl.put("Lattitude", sd.getLattitude()==null?"0":sd.getLattitude());			
			valuesInsertGPStbl.put("Longitude", sd.getLongitude()==null?"0":sd.getLongitude());
			//valuesInsertGPStbl.put("GPSFlag", sd.getGPSFlag()==null?"0":sd.getGPSFlag());
			valuesInsertGPStbl.put("ImagePath", sd.getImagePath()==null?"0":sd.getImagePath());
			valuesInsertGPStbl.put("DateTime", currentTime);
			valuesInsertGPStbl.put("SlaveId", sd.getSlaveId()==null?"0":sd.getSlaveId().toString());

			//03-03-2017
			valuesInsertGPStbl.put("ComRJ11", sd.getComRJ11()==null?"0":sd.getComRJ11());
			valuesInsertGPStbl.put("ComOptical", sd.getComOptical()==null?"0":sd.getComOptical());
			valuesInsertGPStbl.put("Protocol", sd.getProtocol()==null?"0":sd.getProtocol());
			valuesInsertGPStbl.put("OpticalReading", sd.getOpticalReading()==null?"0":sd.getOpticalReading());
			//19-05-2017
			valuesInsertGPStbl.put("MeterSlNo", sd.getMeterSlNo()==null?"0":sd.getMeterSlNo());
			valuesInsertGPStbl.put("YearofManufacture", sd.getYearofManufacture()==null?"0":sd.getYearofManufacture());
			valuesInsertGPStbl.put("TransFormerName", sd.getTransFarmerName()==null?"0":sd.getTransFarmerName());


			long insertResult = sldb.insert("HescomSurvey", null, valuesInsertGPStbl);
			if(insertResult <= 0)
			{	
				sldb.endTransaction();
				throw new Exception("Insertion failed for HescomSurvey");
			}				
			sldb.setTransactionSuccessful();
			return 1;
		}
		catch(Exception e)
		{
			Log.d("", e.toString());
			return 0;
		}	
		finally
		{
			sldb.endTransaction();
		}	
	}



	public ArrayList<String> GetSurveyDataSendToServer()
	{
		ArrayList<String> alStr = new ArrayList<String>();
		Cursor crWrite = null;
		StringBuilder sb = new StringBuilder();
		try
		{
			QueryParameters qParam = StoredProcedure.GetSurveyDataSendToServer();
			crWrite = getReadableDatabase().rawQuery(qParam.getSql(), null);
			if(crWrite != null && crWrite.getCount() > 0)
			{
				crWrite.moveToFirst();
				do
				{
					try                         
					{
						//MCHMP30038           ,3154284   ,1,2,7,ffh,1,558,1,1,58,null,3,188,cc,0.0,0.0,null,3154284_27092016_151033.jpg
						sb.delete(0, sb.length());
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_RRNo_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_ConnectionId_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_ConnectionType_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_Phase_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_MeterMake_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_Model_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_MeterType_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_MeterBoxAvailability_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_TypeOfBox_TABLE_HescomSurvey))+",");	//8
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_MeterPlacement_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_HeightOfMeter_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_AvailabilityOfLineOfSiht_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_floor_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_NoOfMeterAvailable_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_MeterDiemension_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_Remarks_TABLE_HescomSurvey))+",");
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_Lattitude_TABLE_HescomSurvey))+",");	
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_Longitude_TABLE_HescomSurvey))+",");
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_DateTime_TABLE_HescomSurvey))+",");
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_ImagePath_TABLE_HescomSurvey))+",");
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_SlaveId_TABLE_HescomSurvey))+",");
						//03-03-2017
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_ComRJ11_TABLE_HescomSurvey))+","); //21
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_ComOptical_TABLE_HescomSurvey))+",");
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_Protocol_TABLE_HescomSurvey))+",");
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_OpticalReading_TABLE_HescomSurvey))+",");

						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_TransFormerName_TABLE_HescomSurvey))+",");
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_YearofManufacture_TABLE_HescomSurvey))+",");
						sb.append(crWrite.getString(crWrite.getColumnIndex(COL_MeterSlNo_TABLE_HescomSurvey)));


						alStr.add(sb.toString());
					}
					catch(Exception e)
					{
						Log.d(TAG, e.toString());
					}

				}while(crWrite.moveToNext());
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return alStr;
	}




	public int UpdateStatusSurveyByID(String ConnectionId, String status , String rrno)
	{
		int upStatus = 0;
		try
		{
			ContentValues valuesUpdateMaintbl = new ContentValues();
			valuesUpdateMaintbl.put("GPSFlag", status);
			upStatus = getWritableDatabase().update("HescomSurvey", valuesUpdateMaintbl, "trim(ConnectionId) = ? and trim(RRNo) = ?",new String[]{ConnectionId.trim(),rrno.trim()});
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return upStatus;
	}

	public String GetTotalNoOFConn()
	{
		Cursor crBat = null;
		String cnt = "";
		try
		{
			QueryParameters qParam = StoredProcedure.GetCountBillCollDataMain();
			crBat = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());	
			if(crBat != null && crBat.getCount() > 0)//Cursor 1
			{
				crBat.moveToFirst();
				cnt = crBat.getString(crBat.getColumnIndex("COUNT"));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			//throw e;
		}
		finally
		{
			if(crBat!=null)
			{
				crBat.close();
			}
		}		
		return cnt;
	}
	public String GetCountSurveyDone()
	{
		Cursor crBat = null;
		String cnt = "";
		try
		{
			QueryParameters qParam = StoredProcedure.GetCountSurveyDone();
			crBat = getReadableDatabase().rawQuery(qParam.getSql(), null);	
			if(crBat != null && crBat.getCount() > 0)//Cursor 1
			{
				crBat.moveToFirst();
				cnt = crBat.getString(crBat.getColumnIndex("COUNT"));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			//throw e;
		}
		finally
		{
			if(crBat!=null)
			{
				crBat.close();
			}
		}		
		return cnt;
	}
	public String GetCountSurveyGPRSSent()
	{
		Cursor crBat = null;
		String cnt = "";
		try
		{
			QueryParameters qParam = StoredProcedure.GetCountSurveyGPRSSent();
			crBat = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());	
			if(crBat != null && crBat.getCount() > 0)//Cursor 1
			{
				crBat.moveToFirst();
				cnt = crBat.getString(crBat.getColumnIndex("COUNT"));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			//throw e;
		}
		finally
		{
			if(crBat!=null)
			{
				crBat.close();
			}
		}		
		return cnt;
	}

	public String UpdateSurveyBlCnt(String ConnectionNo)
	{	
		String strValue = "";
		try
		{
			ContentValues valuesUpdateMaintbl = new ContentValues();
			valuesUpdateMaintbl.put("BlCnt", "1");
			int up = getWritableDatabase().update("SBM_BillCollDataMain", valuesUpdateMaintbl, "ConnectionNo = ?", new String[]{ConnectionNo});
			strValue = GPRSFlag();
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return strValue;
	}
	public String GetSurveyPhotoNameByConnectionNo(String ConnectionNo)
	{
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getReadableDatabase();
		Cursor crImgName = null;
		String ImgName = "";
		try
		{
			QueryParameters qParam = StoredProcedure.GetPhotoNameByConnectionNo(ConnectionNo);//sql query for get count for billed connection 
			crImgName = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			if(crImgName != null && crImgName.getCount() > 0)
			{
				crImgName.moveToFirst();
				ImgName = crImgName.getString(crImgName.getColumnIndex("PhotoName"));
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return ImgName;
	}

	/////////////////////////////////Survey End/////////////////////////////
	public int	insertDCUMaster(ArrayList<String> lststr)
	{

		int rtvalue = 0;		
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();
		String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
		try                             

		{
			sldb.beginTransaction();
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_DCUMaster);
			sldb.execSQL("CREATE TABLE DCUMaster(UID INTEGER PRIMARY KEY,DCUID TEXT,DCUName TEXT);");



			if(lststr.size()>0)                  
			{
				try {
					for (String stringdata : lststr) 
					{
						ContentValues valuesInsertMaintbl = new ContentValues();
						String[] str=	stringdata.toString().split(",");

						valuesInsertMaintbl.put("DCUID", str[0].trim());	
						valuesInsertMaintbl.put("DCUName", str[1].trim());                   

						long insertResult = sldb.insert("DCUMaster", null, valuesInsertMaintbl);					
						if(insertResult <= 0)
						{	
							sldb.endTransaction();
							throw new Exception("Insertion failed for DCUMaster");
						}
					}
				}
				catch (Exception e)
				{
					Log.d("", e.toString());
				}
				sldb.setTransactionSuccessful();
				rtvalue = 1;
			}
			else
			{
				sldb.setTransactionSuccessful();
			}
		}
		catch(Exception e)
		{			
			Log.d("", e.toString());
			rtvalue = 0;		

		}	
		finally         
		{
			sldb.endTransaction();                                     
		}	

		return rtvalue;
	}
	/*public int	insertSlaveMaster(ArrayList<String> lststr)
	{

		int rtvalue = 0;		
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();
		String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
		try                             

		{
			sldb.beginTransaction();
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_SlaveMaster);
			sldb.execSQL("CREATE TABLE SlaveMaster(UID INTEGER PRIMARY KEY,DCUID TEXT,SlaveID TEXT, SlaveName TEXT);");

			if(lststr.size()>0)                  
			{
				try {
					for (String stringdata : lststr) 
					{
						ContentValues valuesInsertMaintbl = new ContentValues();
						String[] str=	stringdata.toString().split(",");
						valuesInsertMaintbl.put("DCUID", str[0].trim());
						valuesInsertMaintbl.put("SlaveID", str[1].trim());	
						valuesInsertMaintbl.put("SlaveName", str[2].trim());

						long insertResult = sldb.insert("SlaveMaster", null, valuesInsertMaintbl);					
						if(insertResult <= 0)
						{	
							sldb.endTransaction();
							throw new Exception("Insertion failed for SlaveMaster");
						}
					}
				}
				catch (Exception e)
				{
					Log.d("", e.toString());
				}
				sldb.setTransactionSuccessful();
				rtvalue = 1;
			}
			else
			{
				sldb.setTransactionSuccessful();
			}
		}
		catch(Exception e)
		{			
			Log.d("", e.toString());
			rtvalue = 0;		

		}	
		finally         
		{
			sldb.endTransaction();                                     
		}	

		return rtvalue;
	}*/

	public int	insertDCUSlaveMapData(ArrayList<String> lststr)
	{

		int rtvalue = 0;		
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();
		String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
		try                             

		{
			sldb.beginTransaction();
			sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_DCUSlaveMapData);
			sldb.execSQL("CREATE TABLE DCUSlaveMapData(UID INTEGER PRIMARY KEY,Flag TEXT,Latitude TEXT,Longitude TEXT,Name TEXT,Remarks TEXT);");
			if(lststr.size()>0)                  
			{
				try {
					for (String stringdata : lststr) 
					{
						ContentValues valuesInsertMaintbl = new ContentValues();
						String[] str=	stringdata.toString().split(",");						
						valuesInsertMaintbl.put("Latitude", str[0].trim());	
						valuesInsertMaintbl.put("Longitude", str[1].trim());
						valuesInsertMaintbl.put("Flag", str[2].trim());
						valuesInsertMaintbl.put("Name", str[3].trim());	
						valuesInsertMaintbl.put("Remarks", str[4].trim());

						long insertResult = sldb.insert("DCUSlaveMapData", null, valuesInsertMaintbl);					
						if(insertResult <= 0)
						{	
							sldb.endTransaction();
							throw new Exception("Insertion failed for DCUSlaveMapData");
						}
					}
				}
				catch (Exception e)
				{
					Log.d("", e.toString());
				}
				sldb.setTransactionSuccessful();
				rtvalue = 1;
			}
			else
			{
				sldb.setTransactionSuccessful();
			}
		}
		catch(Exception e)
		{			
			Log.d("", e.toString());
			rtvalue = 0;		

		}	
		finally         
		{
			sldb.endTransaction();                                     
		}	

		return rtvalue;
	}

	public ArrayList<LatLong> getDCUSlaveMapData() 
	{
		ArrayList<LatLong> mList = new  ArrayList<LatLong>();

		Cursor crRep=null ;
		try
		{
			QueryParameters qParam = StoredProcedure.getDCUSlaveMapData();
			crRep = getReadableDatabase().rawQuery(qParam.getSql(),qParam.getSelectionArgs());	
			if(crRep!=null)
			{
				for(int i=1; i<=crRep.getCount();i++)
				{

					if(mList==null)
					{
						mList=new ArrayList<LatLong>();
					}
					if(i==1)				
						crRep.moveToFirst();				
					else
						crRep.moveToNext();

					LatLong rb = new LatLong();						


					/*rb.setmMRName(crRep.getString(crRep.getColumnIndex(COL_MRNAME_CURRENT_MR_STATUS)));*/
					rb.setmLatitude(crRep.getString(crRep.getColumnIndex(COL_Latitude_TABLE_DCUSlaveMapData)));
					rb.setmLongitude(crRep.getString(crRep.getColumnIndex(COL_Longitude_TABLE_DCUSlaveMapData)));	
					rb.setmFlag(crRep.getString(crRep.getColumnIndex(COL_Flag_TABLE_DCUSlaveMapData)));
					rb.setmName(crRep.getString(crRep.getColumnIndex(COL_Name_TABLE_DCUSlaveMapData)));
					rb.setmRemarks(crRep.getString(crRep.getColumnIndex(COL_Remarks_TABLE_DCUSlaveMapData)));

					mList.add(rb);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception

		}
		finally
		{
			if(crRep!=null)
			{
				crRep.close();
			}
		}
		return mList;                             
	}



	public DDLAdapter getslaveMaster(String GPId) throws Exception
	{
		DDLAdapter conList = null;
		cr=null ;
		try
		{
			QueryParameters qParam = StoredProcedure.getslaveMaster(GPId);
			cr = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			if(conList==null)
			{
				conList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
				conList.AddItem("0", "--All--");
			}

			for(int i=1; i<=cr.getCount();++i)
			{
				if(conList==null)
				{
					conList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());

				}
				if(i==1)
					cr.moveToFirst();
				else
					cr.moveToNext();		
				conList.AddItem(cr.getString(cr.getColumnIndex(COL_SlaveID_TABLE_SlaveMaster)), cr.getString(cr.getColumnIndex(COL_SlaveID_TABLE_SlaveMaster)));

			}
		} 
		catch (Exception e)
		{

			Log.d("", "");
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return conList;
	}


	public DDLAdapter getDcuMaster() throws Exception
	{
		DDLAdapter conList = null;
		cr=null ;
		try
		{
			QueryParameters qParam = StoredProcedure.getDcuMaster();
			cr = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			if(conList==null)
			{
				conList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
				//conList.AddItem("0", "--All--");
			}

			for(int i=1; i<=cr.getCount();++i)
			{
				if(conList==null)
				{
					conList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());

				}
				if(i==1)
					cr.moveToFirst();
				else
					cr.moveToNext();		
				conList.AddItem(cr.getString(cr.getColumnIndex(COL_DCUID_TABLE_DCUMaster)), cr.getString(cr.getColumnIndex(COL_DCUID_TABLE_DCUMaster)));

			}
		} 
		catch (Exception e)
		{

			Log.d("", "");
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return conList;
	}

	public DDLAdapter getAllTransformerName() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {

			if(lList==null)
			{
				lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			}
			//Shilong Modified - 08-05-2018
			lList.AddItem("-1", "--SELECT--");	
			/*lList.AddItem("1","THANA ROAD_01500E");  //
			lList.AddItem("2","HOTEL ALPINE CONTINENTAL_01500F"); //
			lList.AddItem("3","HOTEL BOULEVARDI_01500G"); //
			lList.AddItem("4","CHAND GOTHIYA_01500G"); //
			lList.AddItem("5","BHAJANLAL SRINIVAS_01500J");//
			lList.AddItem("6","GLORYS PLAZA_01500K");//
			lList.AddItem("7","DEEDAR MARKET_01500L"); //
			lList.AddItem("8","URBAN AFFAIRS COMPLEX(MUDA)_01500M");//
			lList.AddItem("9","PARKING LOT-2_01500N");//
			lList.AddItem("10","CHOUDHURY PHARMACY_01500P");//
			lList.AddItem("11","BHAJANLAL SRINIVAS_01500R");//
			lList.AddItem("12","O.B. SHOPPING MALL_01500S");//
			lList.AddItem("13","UPPER JAIL ROAD_01500V");//
			lList.AddItem("14","HOTEL GRACE_01500W");//
			lList.AddItem("15","MUNICIPAL_01500X");//
			lList.AddItem("16","ELDORADO   COMPLEX_01500Y"); ///
			lList.AddItem("17","HOTEL PEGASUS-1_01500Z"); //
			lList.AddItem("18","ASSEMBLY PRESS_015011");//
			lList.AddItem("19","SALONSAR MANSION_015012");//
			lList.AddItem("20","RURAL BANK_015013");//
			lList.AddItem("21","EEE CEE RESTOURANT_015014"); //
			lList.AddItem("22","MTC(MEGHALAYA TRANSPORT CORPORATION),JAIL RO_015015");//
			lList.AddItem("23","G.H. SYIEMLIEH_015016"); //
			lList.AddItem("24","MUNICIPAL_01502X");//
			lList.AddItem("25","WALINGDON COMMUNITY HALL_016001");///
			lList.AddItem("26","WALINGDON COMMUNITY HALL_016002");///
			lList.AddItem("27","EMRI_01B00C");//			
			lList.AddItem("32","Pasteur Institute");
			lList.AddItem("33","NEIGHRIHMS");
			lList.AddItem("34","Nurse Hotel");
			lList.AddItem("35","Ganesh Das Hospital");
			lList.AddItem("36","NIMHANS");			
			lList.AddItem("37","Police Reserve-1");
			lList.AddItem("38","Police Reserve-2");			
			lList.AddItem("39","Eden Residency");			
			lList.AddItem("40","Quinton Road");
			lList.AddItem("41","MARBA Hub");
			lList.AddItem("42","District Jail");
			lList.AddItem("43","Banik");
			lList.AddItem("44","Vishal  Market");*/
			lList.AddItem("1","TC-1");
			lList.AddItem("2","TC-2");
			lList.AddItem("3","TC-3");
			lList.AddItem("4","TC-4");
			lList.AddItem("5","TC-5");
			lList.AddItem("6","TC-6");
			lList.AddItem("7","TC-7");
			lList.AddItem("8","TC-8");
			lList.AddItem("9","TC-9");
			lList.AddItem("10","TC-10");

			/*
			//Gescom
			 * 
			lList.AddItem("-1", "--SELECT--");			
			lList.AddItem("1","THANA ROAD_01500E");
			lList.AddItem("2","Other");
			 */

			/*lList.AddItem("-1", "--SELECT--");			
			lList.AddItem("1","250 KVA Rathod DTC");
			lList.AddItem("2","Other");*/

			/*lList.AddItem("1","Sonu Chai vale ke Paas NavaJyothi Jevim Nagar Panchavati_63");
			lList.AddItem("2","Masani Road Jevim Nagar Panchavati_100");
			lList.AddItem("3","Masani Road Jevim Nagar Deepa Art_25");
			lList.AddItem("4","Masani Road Jevim Raju Supaari_250");
			lList.AddItem("5","Masani Road Jevim Raju Supaari_25");
			lList.AddItem("6","Masani Road Jevim Steel Plant_63");
			lList.AddItem("7","Balaji Masani Road Saraswati Kund_400");
			lList.AddItem("8","Kabrasthan Chamund Nagar Panchavati ke Peeche_400");
			lList.AddItem("9","Shashank Mitl Saraswati Kund_63");
			lList.AddItem("10","Davu Baba_63");
			lList.AddItem("11","Nivad Panchavati ke Samne_25");
			lList.AddItem("12","Chamund Panchavati Park Me_250");
			lList.AddItem("13","Tayuval Park me Panchavati_25");
			lList.AddItem("14","Chamund Colony Asandiyo Office ke Paas_400");
			lList.AddItem("15","Hemaraj Gharelu Saraswati Kund_25");
			lList.AddItem("16","Agravatike ke Peeche_100");
			lList.AddItem("17","Agravatike ke Peeche_63");
			lList.AddItem("18","Krishna Gardan_63");
			lList.AddItem("19","Asambeayem Byck Vala Craman_25");
			lList.AddItem("20","Mahendranagar Panchavati Hanumaan ke Paas_250");
			lList.AddItem("21","Tavar Mahendra nagar_25");
			lList.AddItem("22","Tavar Tetivaal ke Samne_25");
			lList.AddItem("23","Adarsha Nagar Relve Line ke Paas_100");
			lList.AddItem("24","Tavar Calector Caloni_25");
			lList.AddItem("25","Saadi Factory Savayar ke saamne_25");
			lList.AddItem("26","Pushpa Vihar Hospital_400");
			lList.AddItem("27","Sheela Sharma Hospital_63");
			lList.AddItem("28","Maa Kaali Savol Radha Ashok Hotel ke Saamne_25");
			lList.AddItem("29","Sweet Suparike Peeche_63");
			lList.AddItem("30","Goyal Namkin Shivaji Nagar_63");
			lList.AddItem("31","Sheel Sharma ke Peeche_250");
			lList.AddItem("32","Nayana Saadi Sheel Sharma ke Peeche_63");
			lList.AddItem("33","Shayam Vidhyalay ke Peeche_100");
			lList.AddItem("34","Shankar Veehar_100");
			lList.AddItem("35","Adiya Tavar Narasi Village ke Peeche Shivaji Nagar_25");
			lList.AddItem("36","Paape Factory narasi village ke peeche_63");
			lList.AddItem("37","Daal nil narsi village ke peeche_100");
			lList.AddItem("38","Sanjeev Vaisal saadi Factory_63");
			lList.AddItem("39","Kukkar Factory Nayaa Samyojan_63");
			lList.AddItem("40","Nivad Factory Ganapathi ke Ssamne_25");
			lList.AddItem("41","Ganapathi Enclave Ganapathi ke Saamne_250");
			lList.AddItem("42","Ganapathi Enclave Ganapathi ke Saamne_250");
			lList.AddItem("43","Radheshyam Calony Nayi Masijad_400");
			lList.AddItem("44","Treetment Plant Radheshyam calony_25");
			lList.AddItem("45","Puraathathva Vibhag Radheshyam Calony_25");
			lList.AddItem("46","Kankir tila_250");
			lList.AddItem("47","Radheshyam calony Purani Masijad_400");
			lList.AddItem("48","Sarvan Press Kanha Makaan ke Paas_63");
			lList.AddItem("49","Press Radha Mohan Mandir ke paas_63");
			lList.AddItem("50","Bank Food Naale ke paas_25");
			lList.AddItem("51","DTC-1");
			lList.AddItem("52","DTC-2");	
			lList.AddItem("53","DTC-3");*/		

			/*lList.AddItem("1","01TO246");
			lList.AddItem("2","01TO247");
			lList.AddItem("3","01TO248");
			lList.AddItem("4","01TO249");
			lList.AddItem("5","01TO250");
			lList.AddItem("6","01TO251");
			lList.AddItem("7","01TO256");
			lList.AddItem("8","01TO257");
			lList.AddItem("9","01TO258");
			lList.AddItem("10","DTC-1");
			lList.AddItem("11","DTC-2");
			lList.AddItem("12","DTC-3");*/

			//24-10-2017
			/*lList.AddItem("1","KULWANT COLONY,POCKET NO-7_1001");		
			lList.AddItem("2","AIRPORT WALA_2002");
			lList.AddItem("3","JANRAL BUCH WALA_2003");
			lList.AddItem("4","RAILA WALA_9004");
			lList.AddItem("5","PEPSI WALA_1005");
			lList.AddItem("6","DIFINCE COLONY, NO-1_5006");
			lList.AddItem("7","DIFINCE COLONY, NO-2_6007");
			lList.AddItem("8","YPS MARKET WALA_9008");
			lList.AddItem("9","JUDGE VIJAY SINGH WALA_9009");
			lList.AddItem("10","GOVT. GIRLS COLLEGE TUBWELL WALA_6010");
			lList.AddItem("11","Dr.ENCLAVE WALA_8011");
			lList.AddItem("12","SEKHO SAHEB WALA_0012");
			lList.AddItem("13","BANK WALA_4013");
			lList.AddItem("14","PATHAK WALA_7014");
			lList.AddItem("15","JAJI WALA_2015");
			lList.AddItem("16","D.C GARIYAL WALA_4016");
			lList.AddItem("17","NAGER ENCALAVE_1017");
			lList.AddItem("18","HEAM BAGH WALA_8018");
			lList.AddItem("19","PUNJAB & SINDDH BANK_3019");
			lList.AddItem("20","YADVINDRA PUBLIC SCHOOL_7020");
			lList.AddItem("21","GOVT GIRLS COLLEGE_2021");
			lList.AddItem("22","MORE WALA_4022");
			lList.AddItem("23","YPS CHOK WALA_9023");			
			lList.AddItem("24","DTC-1");	
			lList.AddItem("25","DTC-2");	
			lList.AddItem("26","DTC-3");	
			lList.AddItem("27","DTC-4");	
			lList.AddItem("28","DTC-5");*/

			//28-11-2017
			/*lList.AddItem("1","Naroli Char Rasta");
			lList.AddItem("2","Oppo Telephone Exchange");
			lList.AddItem("3","Gohil Falia");
			lList.AddItem("4","Kakad falia");
			lList.AddItem("5","Nr Lords Resort");
			lList.AddItem("6","Nr Flour Mill (Kamleshbhai)");
			lList.AddItem("7","Street ligh Gohil Falia");
			lList.AddItem("8","R. R. Gupta");
			lList.AddItem("9","Nr Sheetal Bar Gohil falia");
			lList.AddItem("10","Nr Bhavani Temple");
			lList.AddItem("11","N. C Chauhan");
			lList.AddItem("12","High School Campus");
			lList.AddItem("13","Deep Complex I");
			lList.AddItem("14","Deep Complex II");
			lList.AddItem("15","Vad Falia");
			lList.AddItem("16","Panchavati complex");
			lList.AddItem("17","Dhapsa Naroli");
			lList.AddItem("18","Dhapsa Navagram");
			lList.AddItem("19","Haveli falia");
			lList.AddItem("20","Navi Vasahat");
			lList.AddItem("21","Ahirvas");
			lList.AddItem("22","Kuva falia I");
			lList.AddItem("23","Kuva falia II");
			lList.AddItem("24","M Poonam (Orion villa)");
			lList.AddItem("25","Lotus Infra (Char Rasta)");
			lList.AddItem("26","DTC-1");	
			lList.AddItem("27","DTC-2");	
			lList.AddItem("28","DTC-3");	
			lList.AddItem("29","DTC-4");	
			lList.AddItem("30","DTC-5");*/





		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}

	public DDLAdapter getTransformerName() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {

			lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			lList.AddItem("-1", "--SELECT--");
			lList.AddItem("1", "Transformer1");
			lList.AddItem("2", "Transformer2");
			lList.AddItem("3", "Transformer3");
			lList.AddItem("4", "Transformer4");
			lList.AddItem("5", "Transformer5");

			/*QueryParameters qParam=StoredProcedure.getTransformerName();		
			cr=getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			for(int i=1; i<=cr.getCount();++i)
			{			
				if(i==1)				
					cr.moveToFirst();				
				else				
					cr.moveToNext();
				lList.AddItem(String.valueOf(cr.getInt(cr.getColumnIndex(COL_ID_TABLE_TransformerMaster))), cr.getString(cr.getColumnIndex(COL_Name_TABLE_TransformerMaster)));
			}*/
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}
	public void DropCreateTransformerTable()
	{
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();

		sldb.execSQL("DROP TABLE IF EXISTS " + TABLE_TransformerMaster);		

		sldb.execSQL("CREATE TABLE TransformerMaster(ID TEXT,Name TEXT);");
	}	

	public int InsertTransformerTable(String id,String name)
	{

		int rtvalue = 0;		
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();
		try                             
		{
			sldb.beginTransaction();
			sldb.execSQL("CREATE TABLE IF NOT EXISTS TransformerMaster(ID TEXT,Name TEXT);");

			try {

				ContentValues valuesInsertMaintbl = new ContentValues();						
				valuesInsertMaintbl.put("ID", id);
				valuesInsertMaintbl.put("Name", name);							

				long insertResult = sldb.insert("TransformerMaster", null, valuesInsertMaintbl);					
				if(insertResult <= 0)
				{	
					sldb.endTransaction();
					throw new Exception("Insertion failed for TransformerMaster");
				}

			}
			catch (Exception e)
			{
				Log.d("", e.toString());
			}
			sldb.setTransactionSuccessful();
			rtvalue = 1;

		}
		catch(Exception e)
		{			
			Log.d("", e.toString());
			rtvalue = 0;		

		}	
		finally         
		{
			sldb.endTransaction();                                     
		}	

		return rtvalue;
	}


	public ArrayList<ReportTCwise> GetReportTCwise() throws Exception
	{
		ArrayList<ReportTCwise> mList = new ArrayList<ReportTCwise>();
		Cursor crRep=null ;
		try
		{
			QueryParameters qParam = StoredProcedure.GetTCReport();
			crRep = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());	
			if(crRep!=null)
			{
				for(int i=1; i<=crRep.getCount();++i)
				{

					if(mList==null)
					{
						mList=new ArrayList<ReportTCwise>();
					}
					if(i==1)				
						crRep.moveToFirst();				
					else
						crRep.moveToNext();

					ReportTCwise rb = new ReportTCwise();								
					rb.setTransformername(crRep.getString(crRep.getColumnIndex("TransformerName")));
					rb.setSurveyDone(crRep.getString(crRep.getColumnIndex("ConnectionCount")));


					mList.add(rb);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(crRep!=null)
			{
				crRep.close();
			}
		}
		return mList;
	}
	public int GetMaxSurveyUID()
	{
		Cursor crRec = null;
		int recno = 0;
		try
		{
			QueryParameters qParam = StoredProcedure.GetMaxSurveyUID();
			crRec = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());	
			if(crRec != null && crRec.getCount() > 0)//Cursor 1
			{
				crRec.moveToFirst();
				recno = crRec.getInt(crRec.getColumnIndex("UID"));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			//throw e;
		}
		finally
		{
			if(crRec!=null)
			{
				crRec.close();
			}
		}		
		return recno;
	}

	public ArrayList<SurveyData> GetReportSurveyData() throws Exception
	{
		ArrayList<SurveyData> mList = new ArrayList<SurveyData>();
		Cursor crWrite=null ;
		try
		{
			QueryParameters qParam = StoredProcedure.GetReportSurveyData();
			crWrite = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			if(crWrite!=null)
			{
				for(int i=1; i<=crWrite.getCount();++i)
				{

					if(mList==null)
					{
						mList=new ArrayList<SurveyData>();
					}
					if(i==1)				
						crWrite.moveToFirst();				
					else
						crWrite.moveToNext();

					SurveyData rb = new SurveyData();			

					rb.setmRRNo(crWrite.getString(crWrite.getColumnIndex(COL_RRNo_TABLE_HescomSurvey)));	
					rb.setmConnID(crWrite.getString(crWrite.getColumnIndex(COL_ConnectionId_TABLE_HescomSurvey)));	
					rb.setmConnectionType(crWrite.getString(crWrite.getColumnIndex(COL_ConnectionType_TABLE_HescomSurvey)));	
					rb.setmPhase(crWrite.getString(crWrite.getColumnIndex(COL_Phase_TABLE_HescomSurvey)));	
					rb.setmMake(crWrite.getString(crWrite.getColumnIndex(COL_MeterMake_TABLE_HescomSurvey)));	
					rb.setmModel(crWrite.getString(crWrite.getColumnIndex(COL_Model_TABLE_HescomSurvey)));	
					rb.setmMeterType(crWrite.getString(crWrite.getColumnIndex(COL_MeterType_TABLE_HescomSurvey)));	
					rb.setmMeterBoxAvailability(crWrite.getString(crWrite.getColumnIndex(COL_MeterBoxAvailability_TABLE_HescomSurvey)));	
					rb.setmTypeOfBox(crWrite.getString(crWrite.getColumnIndex(COL_TypeOfBox_TABLE_HescomSurvey)));	//8
					rb.setmMeterPlacement(crWrite.getString(crWrite.getColumnIndex(COL_MeterPlacement_TABLE_HescomSurvey)));	
					rb.setmHeightOfMeter(crWrite.getString(crWrite.getColumnIndex(COL_HeightOfMeter_TABLE_HescomSurvey)));	
					rb.setmAvailabilityOfLineOfSiht(crWrite.getString(crWrite.getColumnIndex(COL_AvailabilityOfLineOfSiht_TABLE_HescomSurvey)));	
					rb.setMfloor(crWrite.getString(crWrite.getColumnIndex(COL_floor_TABLE_HescomSurvey)));	
					rb.setmNoOfMeterAvailable(crWrite.getString(crWrite.getColumnIndex(COL_NoOfMeterAvailable_TABLE_HescomSurvey)));	
					rb.setmMeterDiemension(crWrite.getString(crWrite.getColumnIndex(COL_MeterDiemension_TABLE_HescomSurvey)));	
					rb.setmRemarks(crWrite.getString(crWrite.getColumnIndex(COL_Remarks_TABLE_HescomSurvey)));
					rb.setmLattitude(crWrite.getString(crWrite.getColumnIndex(COL_Lattitude_TABLE_HescomSurvey)));	
					rb.setmLongitude(crWrite.getString(crWrite.getColumnIndex(COL_Longitude_TABLE_HescomSurvey)));
					rb.setmDateTime(crWrite.getString(crWrite.getColumnIndex(COL_DateTime_TABLE_HescomSurvey)));
					rb.setmImagePath(crWrite.getString(crWrite.getColumnIndex(COL_ImagePath_TABLE_HescomSurvey)));
					rb.setmSlaveId(crWrite.getString(crWrite.getColumnIndex(COL_SlaveId_TABLE_HescomSurvey)));

					rb.setmComRJ11(crWrite.getString(crWrite.getColumnIndex(COL_ComRJ11_TABLE_HescomSurvey))); //21
					rb.setmComOptical(crWrite.getString(crWrite.getColumnIndex(COL_ComOptical_TABLE_HescomSurvey)));
					rb.setmProtocol(crWrite.getString(crWrite.getColumnIndex(COL_Protocol_TABLE_HescomSurvey)));
					rb.setmOpticalReading(crWrite.getString(crWrite.getColumnIndex(COL_OpticalReading_TABLE_HescomSurvey)));

					rb.setmTransFormerName(crWrite.getString(crWrite.getColumnIndex(COL_TransFormerName_TABLE_HescomSurvey)));
					rb.setmYearofManufacture(crWrite.getString(crWrite.getColumnIndex(COL_YearofManufacture_TABLE_HescomSurvey)));
					rb.setmMeterSlNo(crWrite.getString(crWrite.getColumnIndex(COL_MeterSlNo_TABLE_HescomSurvey)));




					mList.add(rb);
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(crWrite!=null)
			{
				crWrite.close();
			}
		}
		return mList;
	}	




	///////////////////////////Water Survey   21-10-2021
	//03-03-2017
	public void CreateIfNotExistsGPWaterSurveyTable()
	{
		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();

		sldb.execSQL("CREATE TABLE IF NOT EXISTS GPWaterSurvey(UID INTEGER PRIMARY KEY,RRNo TEXT,ConnectionId TEXT,CustomerName TEXT,MeterMake TEXT," +
				"Remarks TEXT,Lattitude TEXT,Longitude TEXT,GPSFlag TEXT,ImagePath TEXT,DateTime TEXT,MeterSlNo TEXT,YearofManufacture TEXT," +
				"DistrictId TEXT,TalukId TEXT,GPId TEXT,VillageId TEXT,MeterStatus TEXT,WaterSource TEXT,Borewell TEXT,ConnectionStatus TEXT," +
				"SancLoad TEXT,PumpCapacity TEXT,Depth TEXT,PipeDimension TEXT,TypeOfSupply TEXT,NoOfOutlets TEXT,TankDimensionsLength TEXT,TankDimensionsDiameter,TankCapacity TEXT," +
				"MeterPhase TEXT,MeterType TEXT,PipeTypeBorewell TEXT,TankId TEXT,ControlValve TEXT,PipeTypeOutlet TEXT,TypeOfSim TEXT,WaterManName TEXT,ContactNo TEXT," +
				"ImageName1 TEXT,ImageName2 TEXT,ImageName3 TEXT,ImageName4 TEXT," +

				"Distance TEXT,NSP TEXT,Param1 TEXT,Param2 TEXT,Param3 TEXT,Param4 TEXT,Param5 TEXT,Param6 TEXT,Param7 TEXT,Param8 TEXT,Param9 TEXT,Param10 TEXT);"); //06-08-2015

		//sldb.execSQL("CREATE TABLE IF NOT EXISTS VillageMasterCapture(UID INTEGER PRIMARY KEY,DistrictId TEXT,TalukId TEXT,GPId TEXT, VillageName TEXT, GPSFlag TEXT);"); //23-10-2021

		//sldb.execSQL("CREATE TABLE IF NOT EXISTS TankMasterCapture(UID INTEGER PRIMARY KEY,DistrictId TEXT,TalukId TEXT,GPId TEXT, VillageId TEXT,TankName TEXT, GPSFlag TEXT);"); //23-10-2021

	}

	public int insertWaterSurveyDetails(SurveyDetails sd)
	{

		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();
		try
		{
			String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
			sldb.beginTransaction();
			ContentValues valuesInsertGPStbl = new ContentValues();
			valuesInsertGPStbl.put("RRNo", sd.getRRNo()==null?"0":sd.getRRNo());
			valuesInsertGPStbl.put("ConnectionId", sd.getConnectionId()==null?"0":sd.getConnectionId());	
			valuesInsertGPStbl.put("CustomerName", sd.getCustomerName()==null?"0":sd.getCustomerName());

			valuesInsertGPStbl.put("MeterMake", sd.getMeterMake()==null?"0":sd.getMeterMake());
			valuesInsertGPStbl.put("Remarks", sd.getRemarks()==null?"0":sd.getRemarks());
			valuesInsertGPStbl.put("Lattitude", sd.getLattitude()==null?"0":sd.getLattitude());			
			valuesInsertGPStbl.put("Longitude", sd.getLongitude()==null?"0":sd.getLongitude());
			//valuesInsertGPStbl.put("GPSFlag", sd.getGPSFlag()==null?"0":sd.getGPSFlag());
			valuesInsertGPStbl.put("ImagePath", sd.getImagePath()==null?"0":sd.getImagePath());
			valuesInsertGPStbl.put("DateTime", currentTime);			
			valuesInsertGPStbl.put("MeterSlNo", sd.getMeterSlNo()==null?"0":sd.getMeterSlNo());
			valuesInsertGPStbl.put("YearofManufacture", sd.getYearofManufacture()==null?"0":sd.getYearofManufacture());

			valuesInsertGPStbl.put("DistrictId", sd.getDistrictId()==null?"0":sd.getDistrictId());
			valuesInsertGPStbl.put("TalukId",  sd.getTalukId()==null?"0":sd.getTalukId());
			valuesInsertGPStbl.put("GPId",  sd.getGPId()==null?"0":sd.getGPId());
			valuesInsertGPStbl.put("VillageId",  sd.getVillageId()==null?"0":sd.getVillageId());
			valuesInsertGPStbl.put("MeterStatus",  sd.getMeterStatus()==null?"0":sd.getMeterStatus());
			valuesInsertGPStbl.put("WaterSource",  sd.getWaterSource()==null?"0":sd.getWaterSource());
			valuesInsertGPStbl.put("Borewell",  sd.getBorewell()==null?"0":sd.getBorewell());
			valuesInsertGPStbl.put("ConnectionStatus",  sd.getConnectionStatus()==null?"0":sd.getConnectionStatus());
			valuesInsertGPStbl.put("SancLoad",  sd.getSancLoad()==null?"0":sd.getSancLoad());
			valuesInsertGPStbl.put("PumpCapacity",  sd.getPumpCapacity()==null?"0":sd.getPumpCapacity());
			valuesInsertGPStbl.put("Depth", sd.getDepth()==null?"0":sd.getDepth());
			valuesInsertGPStbl.put("PipeDimension",  sd.getPipeDimension()==null?"0":sd.getPipeDimension());
			valuesInsertGPStbl.put("TypeOfSupply",  sd.getTypeOfSupply()==null?"0":sd.getTypeOfSupply());
			valuesInsertGPStbl.put("NoOfOutlets",  sd.getNoOfOutlets()==null?"0":sd.getNoOfOutlets());
			valuesInsertGPStbl.put("TankDimensionsLength",  sd.getTankDimensionsLength()==null?"0":sd.getTankDimensionsLength());
			valuesInsertGPStbl.put("TankDimensionsDiameter",  sd.getTankDimensionsDiameter()==null?"0":sd.getTankDimensionsDiameter());
			valuesInsertGPStbl.put("TankCapacity",  sd.getTankCapacity()==null?"0":sd.getTankCapacity());
			valuesInsertGPStbl.put("Distance",  sd.getDistance()==null?"0":sd.getDistance());
			valuesInsertGPStbl.put("NSP",  sd.getNSP()==null?"0":sd.getNSP());


			valuesInsertGPStbl.put("MeterPhase",  sd.getMeterPhase()==null?"0":sd.getMeterPhase());
			valuesInsertGPStbl.put("MeterType",  sd.getMeterType()==null?"0":sd.getMeterType());
			valuesInsertGPStbl.put("PipeTypeBorewell",  sd.getPipeTypeBorewell()==null?"0":sd.getPipeTypeBorewell());
			valuesInsertGPStbl.put("TankId",  sd.getTankId()==null?"0":sd.getTankId());
			valuesInsertGPStbl.put("ControlValve",  sd.getControlValve()==null?"0":sd.getControlValve());
			valuesInsertGPStbl.put("PipeTypeOutlet",  sd.getPipeTypeOutlet()==null?"0":sd.getPipeTypeOutlet());
			valuesInsertGPStbl.put("TypeOfSim",  sd.getTypeOfSim()==null?"0":sd.getTypeOfSim());
			valuesInsertGPStbl.put("WaterManName",  sd.getWaterManName()==null?"0":sd.getWaterManName());
			valuesInsertGPStbl.put("ContactNo",  sd.getContactNo()==null?"0":sd.getContactNo());


			valuesInsertGPStbl.put("ImageName1",  sd.getImageName1()==null?"0":sd.getImageName1());
			valuesInsertGPStbl.put("ImageName2",  sd.getImageName2()==null?"0":sd.getImageName2());
			valuesInsertGPStbl.put("ImageName3",  sd.getImageName3()==null?"0":sd.getImageName3());
			valuesInsertGPStbl.put("ImageName4",  sd.getImageName4()==null?"0":sd.getImageName4());

			valuesInsertGPStbl.put("Param1", sd.getParam1()==null?"0":sd.getParam1());
			valuesInsertGPStbl.put("Param2",  sd.getParam2()==null?"0":sd.getParam2());
			valuesInsertGPStbl.put("Param3",  sd.getParam3()==null?"0":sd.getParam3());
			valuesInsertGPStbl.put("Param4",  sd.getParam4()==null?"0":sd.getParam4());
			valuesInsertGPStbl.put("Param5",  sd.getParam5()==null?"0":sd.getParam5());
			valuesInsertGPStbl.put("Param6",  sd.getParam6()==null?"0":sd.getParam6());
			valuesInsertGPStbl.put("Param7",  sd.getParam7()==null?"0":sd.getParam7());
			valuesInsertGPStbl.put("Param8",  sd.getParam8()==null?"0":sd.getParam8());
			valuesInsertGPStbl.put("Param9",  sd.getParam9()==null?"0":sd.getParam9());
			valuesInsertGPStbl.put("Param10",  sd.getParam10()==null?"0":sd.getParam10());









			long insertResult = sldb.insert("GPWaterSurvey", null, valuesInsertGPStbl);
			if(insertResult <= 0)
			{	
				sldb.endTransaction();
				throw new Exception("Insertion failed for GPWaterSurvey");
			}				
			sldb.setTransactionSuccessful();
			return 1;
		}
		catch(Exception e)
		{
			Log.d("", e.toString());
			return 0;
		}	
		finally
		{
			sldb.endTransaction();
		}	
	}
	public ArrayList<SurveyDetails> GetWaterSurveyDataSendToServer()
	{
		ArrayList<SurveyDetails> alStr = new ArrayList<SurveyDetails>();
		Cursor crWrite = null;

		try
		{
			QueryParameters qParam = StoredProcedure.GetWaterSurveyDataSendToServer();
			crWrite = getReadableDatabase().rawQuery(qParam.getSql(), null);
			if(crWrite != null && crWrite.getCount() > 0)
			{
				crWrite.moveToFirst();
				do
				{
					SurveyDetails sd = new SurveyDetails();

					try                         
					{
						//MCHMP30038           ,3154284   ,1,2,7,ffh,1,558,1,1,58,null,3,188,cc,0.0,0.0,null,3154284_27092016_151033.jpg
						sd.setRRNo(crWrite.getString(crWrite.getColumnIndex(COL_RRNo_TABLE_GPWaterSurvey)));
						sd.setConnectionId(crWrite.getString(crWrite.getColumnIndex(COL_ConnectionId_TABLE_GPWaterSurvey)));
						sd.setCustomerName(crWrite.getString(crWrite.getColumnIndex(COL_CustomerName_TABLE_GPWaterSurvey)));	
						sd.setMeterMake(crWrite.getString(crWrite.getColumnIndex(COL_MeterMake_TABLE_GPWaterSurvey)));
						sd.setRemarks(crWrite.getString(crWrite.getColumnIndex(COL_Remarks_TABLE_GPWaterSurvey)));
						sd.setLattitude(crWrite.getString(crWrite.getColumnIndex(COL_Lattitude_TABLE_GPWaterSurvey)));
						sd.setLongitude(crWrite.getString(crWrite.getColumnIndex(COL_Longitude_TABLE_GPWaterSurvey)));
						sd.setGPSFlag(crWrite.getString(crWrite.getColumnIndex(COL_GPSFlag_TABLE_GPWaterSurvey)));
						sd.setImagePath(crWrite.getString(crWrite.getColumnIndex(COL_ImagePath_TABLE_GPWaterSurvey)));
						sd.setDateTime(crWrite.getString(crWrite.getColumnIndex(COL_DateTime_TABLE_GPWaterSurvey)));
						sd.setMeterSlNo(crWrite.getString(crWrite.getColumnIndex(COL_MeterSlNo_TABLE_GPWaterSurvey)));
						sd.setYearofManufacture(crWrite.getString(crWrite.getColumnIndex(COL_YearofManufacture_TABLE_GPWaterSurvey)));
						sd.setParam1(crWrite.getString(crWrite.getColumnIndex(COL_Param1_TABLE_GPWaterSurvey)));
						sd.setParam2(crWrite.getString(crWrite.getColumnIndex(COL_Param2_TABLE_GPWaterSurvey)));
						sd.setParam3(crWrite.getString(crWrite.getColumnIndex(COL_Param3_TABLE_GPWaterSurvey)));
						sd.setParam4(crWrite.getString(crWrite.getColumnIndex(COL_Param4_TABLE_GPWaterSurvey)));
						sd.setParam5(crWrite.getString(crWrite.getColumnIndex(COL_Param5_TABLE_GPWaterSurvey)));
						sd.setParam6(crWrite.getString(crWrite.getColumnIndex(COL_Param6_TABLE_GPWaterSurvey)));
						sd.setParam7(crWrite.getString(crWrite.getColumnIndex(COL_Param7_TABLE_GPWaterSurvey)));
						sd.setParam8(crWrite.getString(crWrite.getColumnIndex(COL_Param8_TABLE_GPWaterSurvey)));
						sd.setParam9(crWrite.getString(crWrite.getColumnIndex(COL_Param9_TABLE_GPWaterSurvey)));
						sd.setParam10(crWrite.getString(crWrite.getColumnIndex(COL_Param10_TABLE_GPWaterSurvey)));

						sd.setDistrictId(crWrite.getString(crWrite.getColumnIndex(COL_DistrictId_TABLE_GPWaterSurvey)));
						sd.setTalukId(crWrite.getString(crWrite.getColumnIndex(COL_TalukId_TABLE_GPWaterSurvey)));
						sd.setGPId(crWrite.getString(crWrite.getColumnIndex(COL_GPId_TABLE_GPWaterSurvey)));
						sd.setVillageId(crWrite.getString(crWrite.getColumnIndex(COL_VillageId_TABLE_GPWaterSurvey)));
						sd.setTankId(crWrite.getString(crWrite.getColumnIndex(COL_TankId_TABLE_GPWaterSurvey)));
						sd.setMeterStatus(crWrite.getString(crWrite.getColumnIndex(COL_MeterStatus_TABLE_GPWaterSurvey)));
						sd.setWaterSource(crWrite.getString(crWrite.getColumnIndex(COL_WaterSource_TABLE_GPWaterSurvey)));
						sd.setBorewell(crWrite.getString(crWrite.getColumnIndex(COL_Borewell_TABLE_GPWaterSurvey)));
						sd.setConnectionStatus(crWrite.getString(crWrite.getColumnIndex(COL_ConnectionStatus_TABLE_GPWaterSurvey)));
						sd.setSancLoad(crWrite.getString(crWrite.getColumnIndex(COL_SancLoad_TABLE_GPWaterSurvey)));
						sd.setPumpCapacity(crWrite.getString(crWrite.getColumnIndex(COL_PumpCapacity_TABLE_GPWaterSurvey)));
						sd.setDepth(crWrite.getString(crWrite.getColumnIndex(COL_Depth_TABLE_GPWaterSurvey)));
						sd.setPipeDimension(crWrite.getString(crWrite.getColumnIndex(COL_PipeDimension_TABLE_GPWaterSurvey)));
						sd.setTypeOfSupply(crWrite.getString(crWrite.getColumnIndex(COL_TypeOfSupply_TABLE_GPWaterSurvey)));
						sd.setNoOfOutlets(crWrite.getString(crWrite.getColumnIndex(COL_NoOfOutlets_TABLE_GPWaterSurvey)));
						sd.setTankDimensionsLength(crWrite.getString(crWrite.getColumnIndex(COL_TankDimensionsLength_TABLE_GPWaterSurvey)));
						sd.setTankDimensionsDiameter(crWrite.getString(crWrite.getColumnIndex(COL_TankDimensionsDiameter_TABLE_GPWaterSurvey)));
						sd.setTankCapacity(crWrite.getString(crWrite.getColumnIndex(COL_TankCapacity_TABLE_GPWaterSurvey)));
						sd.setDistance(crWrite.getString(crWrite.getColumnIndex(COL_Distance_TABLE_GPWaterSurvey)));
						sd.setNSP(crWrite.getString(crWrite.getColumnIndex(COL_NSP_TABLE_GPWaterSurvey)));

						sd.setMeterPhase(crWrite.getString(crWrite.getColumnIndex(COL_MeterPhase_TABLE_GPWaterSurvey)));
						sd.setMeterType(crWrite.getString(crWrite.getColumnIndex(COL_MeterType_TABLE_GPWaterSurvey)));
						sd.setPipeTypeBorewell(crWrite.getString(crWrite.getColumnIndex(COL_PipeTypeBorewell_TABLE_GPWaterSurvey)));
						sd.setControlValve(crWrite.getString(crWrite.getColumnIndex(COL_ControlValve_TABLE_GPWaterSurvey)));
						sd.setPipeTypeOutlet(crWrite.getString(crWrite.getColumnIndex(COL_PipeTypeOutlet_TABLE_GPWaterSurvey)));
						sd.setTypeOfSim(crWrite.getString(crWrite.getColumnIndex(COL_TypeOfSim_TABLE_GPWaterSurvey)));
						sd.setWaterManName(crWrite.getString(crWrite.getColumnIndex(COL_WaterManName_TABLE_GPWaterSurvey)));
						sd.setContactNo(crWrite.getString(crWrite.getColumnIndex(COL_ContactNo_TABLE_GPWaterSurvey)));

						sd.setImageName1(crWrite.getString(crWrite.getColumnIndex(COL_ImageName1_TABLE_GPWaterSurvey)));
						sd.setImageName2(crWrite.getString(crWrite.getColumnIndex(COL_ImageName2_TABLE_GPWaterSurvey)));
						sd.setImageName3(crWrite.getString(crWrite.getColumnIndex(COL_ImageName3_TABLE_GPWaterSurvey)));
						sd.setImageName4(crWrite.getString(crWrite.getColumnIndex(COL_ImageName4_TABLE_GPWaterSurvey)));


						alStr.add(sd);
					}
					catch(Exception e)
					{
						Log.d(TAG, e.toString());
					}

				}while(crWrite.moveToNext());
			}
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return alStr;
	}
	public int GetMaxWaterSurveyUID()
	{
		Cursor crRec = null;
		int recno = 0;
		try
		{
			QueryParameters qParam = StoredProcedure.GetMaxWaterSurveyUID();
			crRec = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());	
			if(crRec != null && crRec.getCount() > 0)//Cursor 1
			{
				crRec.moveToFirst();
				recno = crRec.getInt(crRec.getColumnIndex("UID"));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			//throw e;
		}
		finally
		{
			if(crRec!=null)
			{
				crRec.close();
			}
		}		
		return recno;
	}
	public int UpdateStatusWaterSurveyByID(String ConnectionId, String status , String rrno)
	{
		int upStatus = 0;
		try
		{
			ContentValues valuesUpdateMaintbl = new ContentValues();
			valuesUpdateMaintbl.put("GPSFlag", status);
			upStatus = getWritableDatabase().update("GPWaterSurvey", valuesUpdateMaintbl, "trim(ConnectionId) = ? and trim(RRNo) = ?",new String[]{ConnectionId.trim(),rrno.trim()});
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return upStatus;
	}
	public String GetCountWaterSurveyDone()
	{
		Cursor crBat = null;
		String cnt = "";
		try
		{
			QueryParameters qParam = StoredProcedure.GetCountWaterSurveyDone();
			crBat = getReadableDatabase().rawQuery(qParam.getSql(), null);	
			if(crBat != null && crBat.getCount() > 0)//Cursor 1
			{
				crBat.moveToFirst();
				cnt = crBat.getString(crBat.getColumnIndex("COUNT"));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			//throw e;
		}
		finally
		{
			if(crBat!=null)
			{
				crBat.close();
			}
		}		
		return cnt;
	}
	public String GetCountWaterSurveyGPRSSent()
	{
		Cursor crBat = null;
		String cnt = "";
		try
		{
			QueryParameters qParam = StoredProcedure.GetCountWaterSurveyGPRSSent();
			crBat = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());	
			if(crBat != null && crBat.getCount() > 0)//Cursor 1
			{
				crBat.moveToFirst();
				cnt = crBat.getString(crBat.getColumnIndex("COUNT"));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			//throw e;
		}
		finally
		{
			if(crBat!=null)
			{
				crBat.close();
			}
		}		
		return cnt;
	}

	public ArrayList<SurveyData> GetReportWaterSurveyData() throws Exception
	{
		ArrayList<SurveyData> alStr = new ArrayList<SurveyData>();
		Cursor crWrite=null ;
		try
		{
			QueryParameters qParam = StoredProcedure.GetReportWaterSurveyData();
			crWrite = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());
			if(crWrite!=null)
			{
				for(int i=1; i<=crWrite.getCount();++i)
				{

					if(alStr==null)
					{
						alStr=new ArrayList<SurveyData>();
					}
					if(i==1)				
						crWrite.moveToFirst();				
					else
						crWrite.moveToNext();

					SurveyData sd = new SurveyData();

					try                         
					{
						//MCHMP30038           ,3154284   ,1,2,7,ffh,1,558,1,1,58,null,3,188,cc,0.0,0.0,null,3154284_27092016_151033.jpg
						sd.setmRRNo(crWrite.getString(crWrite.getColumnIndex(COL_RRNo_TABLE_GPWaterSurvey)));
						sd.setmConnID(crWrite.getString(crWrite.getColumnIndex(COL_ConnectionId_TABLE_GPWaterSurvey)));

						sd.setmMake(crWrite.getString(crWrite.getColumnIndex(COL_MeterMake_TABLE_GPWaterSurvey)));
						sd.setmRemarks(crWrite.getString(crWrite.getColumnIndex(COL_Remarks_TABLE_GPWaterSurvey)));
						sd.setmLattitude(crWrite.getString(crWrite.getColumnIndex(COL_Lattitude_TABLE_GPWaterSurvey)));
						sd.setmLongitude(crWrite.getString(crWrite.getColumnIndex(COL_Longitude_TABLE_GPWaterSurvey)));

						sd.setmImagePath(crWrite.getString(crWrite.getColumnIndex(COL_ImagePath_TABLE_GPWaterSurvey)));
						sd.setmDateTime(crWrite.getString(crWrite.getColumnIndex(COL_DateTime_TABLE_GPWaterSurvey)));
						sd.setmMeterSlNo(crWrite.getString(crWrite.getColumnIndex(COL_MeterSlNo_TABLE_GPWaterSurvey)));
						sd.setmYearofManufacture(crWrite.getString(crWrite.getColumnIndex(COL_YearofManufacture_TABLE_GPWaterSurvey)));
						sd.setmParam1(crWrite.getString(crWrite.getColumnIndex(COL_Param1_TABLE_GPWaterSurvey)));
						sd.setmParam2(crWrite.getString(crWrite.getColumnIndex(COL_Param2_TABLE_GPWaterSurvey)));
						sd.setmParam3(crWrite.getString(crWrite.getColumnIndex(COL_Param3_TABLE_GPWaterSurvey)));
						sd.setmParam4(crWrite.getString(crWrite.getColumnIndex(COL_Param4_TABLE_GPWaterSurvey)));
						sd.setmParam5(crWrite.getString(crWrite.getColumnIndex(COL_Param5_TABLE_GPWaterSurvey)));
						sd.setmParam6(crWrite.getString(crWrite.getColumnIndex(COL_Param6_TABLE_GPWaterSurvey)));
						sd.setmParam7(crWrite.getString(crWrite.getColumnIndex(COL_Param7_TABLE_GPWaterSurvey)));
						sd.setmParam8(crWrite.getString(crWrite.getColumnIndex(COL_Param8_TABLE_GPWaterSurvey)));
						sd.setmParam9(crWrite.getString(crWrite.getColumnIndex(COL_Param9_TABLE_GPWaterSurvey)));
						sd.setmParam10(crWrite.getString(crWrite.getColumnIndex(COL_Param10_TABLE_GPWaterSurvey)));

						sd.setmDistrictId(crWrite.getString(crWrite.getColumnIndex(COL_DistrictId_TABLE_GPWaterSurvey)));
						sd.setmTalukId(crWrite.getString(crWrite.getColumnIndex(COL_TalukId_TABLE_GPWaterSurvey)));
						sd.setmGPId(crWrite.getString(crWrite.getColumnIndex(COL_GPId_TABLE_GPWaterSurvey)));
						sd.setmVillageId(crWrite.getString(crWrite.getColumnIndex(COL_VillageId_TABLE_GPWaterSurvey)));
						sd.setmMeterStatus(crWrite.getString(crWrite.getColumnIndex(COL_MeterStatus_TABLE_GPWaterSurvey)));
						sd.setmWaterSource(crWrite.getString(crWrite.getColumnIndex(COL_WaterSource_TABLE_GPWaterSurvey)));
						sd.setmBorewell(crWrite.getString(crWrite.getColumnIndex(COL_Borewell_TABLE_GPWaterSurvey)));
						sd.setmConnectionStatus(crWrite.getString(crWrite.getColumnIndex(COL_ConnectionStatus_TABLE_GPWaterSurvey)));
						sd.setmSancLoad(crWrite.getString(crWrite.getColumnIndex(COL_SancLoad_TABLE_GPWaterSurvey)));
						sd.setmPumpCapacity(crWrite.getString(crWrite.getColumnIndex(COL_PumpCapacity_TABLE_GPWaterSurvey)));
						sd.setmDepth(crWrite.getString(crWrite.getColumnIndex(COL_Depth_TABLE_GPWaterSurvey)));
						sd.setmPipeDimension(crWrite.getString(crWrite.getColumnIndex(COL_PipeDimension_TABLE_GPWaterSurvey)));
						sd.setmTypeOfSupply(crWrite.getString(crWrite.getColumnIndex(COL_TypeOfSupply_TABLE_GPWaterSurvey)));
						sd.setmNoOfOutlets(crWrite.getString(crWrite.getColumnIndex(COL_NoOfOutlets_TABLE_GPWaterSurvey)));
						sd.setmTankDimensionsLength(crWrite.getString(crWrite.getColumnIndex(COL_TankDimensionsLength_TABLE_GPWaterSurvey)));
						sd.setmTankDimensionsDiameter(crWrite.getString(crWrite.getColumnIndex(COL_TankDimensionsDiameter_TABLE_GPWaterSurvey)));
						sd.setmTankCapacity(crWrite.getString(crWrite.getColumnIndex(COL_TankCapacity_TABLE_GPWaterSurvey)));
						sd.setmDistance(crWrite.getString(crWrite.getColumnIndex(COL_Distance_TABLE_GPWaterSurvey)));
						sd.setmNSP(crWrite.getString(crWrite.getColumnIndex(COL_NSP_TABLE_GPWaterSurvey)));

						sd.setmMeterPhase(crWrite.getString(crWrite.getColumnIndex(COL_MeterPhase_TABLE_GPWaterSurvey)));
						sd.setmMeterType(crWrite.getString(crWrite.getColumnIndex(COL_MeterType_TABLE_GPWaterSurvey)));
						sd.setmPipeTypeBorewell(crWrite.getString(crWrite.getColumnIndex(COL_PipeTypeBorewell_TABLE_GPWaterSurvey)));
						sd.setmTankId(crWrite.getString(crWrite.getColumnIndex(COL_TankId_TABLE_GPWaterSurvey)));
						sd.setmControlValve(crWrite.getString(crWrite.getColumnIndex(COL_ControlValve_TABLE_GPWaterSurvey)));
						sd.setmPipeTypeOutlet(crWrite.getString(crWrite.getColumnIndex(COL_PipeTypeOutlet_TABLE_GPWaterSurvey)));
						sd.setmTypeOfSim(crWrite.getString(crWrite.getColumnIndex(COL_TypeOfSim_TABLE_GPWaterSurvey)));
						sd.setmWaterManName(crWrite.getString(crWrite.getColumnIndex(COL_WaterManName_TABLE_GPWaterSurvey)));
						sd.setmContactNo(crWrite.getString(crWrite.getColumnIndex(COL_ContactNo_TABLE_GPWaterSurvey)));







						alStr.add(sd);
					}
					catch(Exception e)
					{
						Log.d(TAG, e.toString());
					}

				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(crWrite!=null)
			{
				crWrite.close();
			}
		}
		return alStr;
	}	



	public int insertMasterData(ArrayList<String> lststr,int flag) throws Exception
	{		
		int rtvalue = 0;

		DatabaseHelper dh = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb = dh.getWritableDatabase();		


		DatabaseHelper dh1 = new DatabaseHelper(mcntx);
		SQLiteDatabase sldb1 = dh1.getWritableDatabase();	
		try
		{	
			if (lststr.size() > 0)
			{

				if(flag==1)
				{
					sldb.execSQL("DROP TABLE If Exists DistrictMaster;");
					sldb.execSQL("CREATE TABLE IF NOT EXISTS DistrictMaster(Id TEXT,Name TEXT,Desc TEXT);");
				}
				else if(flag==2)
				{
					sldb.execSQL("DROP TABLE If Exists TalukMaster;");
					sldb.execSQL("CREATE TABLE IF NOT EXISTS TalukMaster(Id TEXT,Name TEXT,Desc TEXT);");
				}
				else if(flag==3)
				{
					sldb.execSQL("DROP TABLE If Exists GramPanchayatMaster;");
					sldb.execSQL("CREATE TABLE IF NOT EXISTS GramPanchayatMaster(Id TEXT,Name TEXT,Desc TEXT);");
				}
				else if(flag==4)
				{
					sldb.execSQL("DROP TABLE If Exists VillageMaster;");
					sldb.execSQL("CREATE TABLE IF NOT EXISTS VillageMaster(Id TEXT,Name TEXT,Desc TEXT);");
				}
				else if(flag==5)
				{
					sldb.execSQL("DROP TABLE If Exists TankMaster;");
					sldb.execSQL("CREATE TABLE IF NOT EXISTS TankMaster(Id TEXT,Name TEXT,Desc TEXT);");
				}

				sldb1.beginTransaction();
				for (int i=0; i< lststr.size() ; i++)
				{					
					String str[] = lststr.get(i).split(",");

					ContentValues valuesInsertMaintbl = new ContentValues();
					valuesInsertMaintbl.put("Id", str[1].trim());
					valuesInsertMaintbl.put("Name",str[2].trim());
					valuesInsertMaintbl.put("Desc",str[3].trim());

					long insertResult = 0;			
					if(flag ==1)
					{

						insertResult = sldb1.insert("DistrictMaster", null, valuesInsertMaintbl);
					}
					else if(flag ==2)
					{
						insertResult = sldb1.insert("TalukMaster", null, valuesInsertMaintbl);
					}
					else if(flag ==3)
					{
						insertResult = sldb1.insert("GramPanchayatMaster", null, valuesInsertMaintbl);
					}
					else if(flag ==4)
					{
						insertResult = sldb1.insert("VillageMaster", null, valuesInsertMaintbl);
					}
					else if(flag ==5)
					{
						insertResult = sldb1.insert("TankMaster", null, valuesInsertMaintbl);
					}
					if(insertResult <= 0)
					{	
						sldb1.setTransactionSuccessful();			
						sldb1.endTransaction();
						throw new Exception("Insertion failed for Master Table");
					}						
					else 
						rtvalue = 1;

				}
			}	
			sldb1.setTransactionSuccessful();				

		}
		catch(Exception e)
		{
			Log.d("", e.toString());
			rtvalue = 0;			
		}	
		finally
		{
			sldb1.endTransaction();
		}
		return rtvalue;		
	}



	public DDLAdapter getMasterData(int flag,String id) throws Exception
	{
		DDLAdapter conList = null;
		Cursor cr1 =null ;
		try
		{
			if(conList==null)
			{
				conList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
				conList.AddItem("0", "--SELECT--");
			}

			if(flag!=0)
			{
				QueryParameters qParam = StoredProcedure.getMasterData(flag,id);
				cr1 = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());	
				for(int i=1; i<=cr1.getCount();++i)
				{

					if(i==1)
						cr1.moveToFirst();
					else
						cr1.moveToNext();				
					conList.AddItem(cr1.getString(cr1.getColumnIndex("Id")), cr1.getString(cr1.getColumnIndex("Name")));

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("", "");
		}

		finally
		{
			if(cr1!=null)
			{
				cr1.close();
			}
		}
		return conList;
	}

	public DDLAdapter getWaterSource() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {

			lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			lList.AddItem("0", "--SELECT--");
			lList.AddItem("1", "Borewell");
			lList.AddItem("2", "Well");
			lList.AddItem("3", "Lake");
			lList.AddItem("4", "Reservoir");
			lList.AddItem("5", "Stream");
			lList.AddItem("6", "River");
			lList.AddItem("7", "Other");


		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}
	public DDLAdapter getSIMType() throws Exception
	{
		DDLAdapter lList=null;
		cr=null ;
		try {

			lList=new DDLAdapter(mcntx,new ArrayList<DDLItem>());
			lList.AddItem("0", "--SELECT--");
			lList.AddItem("1", "AIRTEL");
			lList.AddItem("2", "BSNL");
			lList.AddItem("3", "JIO");			
			lList.AddItem("4", "Other");


		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally
		{
			if(cr!=null)
			{
				cr.close();
			}
		}
		return lList;
	}
	public String getMasterName(int flag,String id)
	{
		Cursor crBat = null;
		String cnt = "";
		try
		{
			QueryParameters qParam = StoredProcedure.getMasterName(flag,id);
			crBat = getReadableDatabase().rawQuery(qParam.getSql(), qParam.getSelectionArgs());	
			if(crBat != null && crBat.getCount() > 0)//Cursor 1
			{
				crBat.moveToFirst();
				cnt = crBat.getString(crBat.getColumnIndex("Name"));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			//throw e;
		}
		finally
		{
			if(crBat!=null)
			{
				crBat.close();
			}
		}		
		return cnt;
	}



}


