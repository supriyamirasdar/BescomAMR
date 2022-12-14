package in.nsoft.bescomamr;



// this class will contain all static function needed to query the database its a type of 
// class to enable stored procedure concept as a central repository of query
public class StoredProcedure implements Schema {
	private static final String dateFormat="%Y-%m-%d";

	public static QueryParameters GetUserDetails(String id,String pwd)
	{	QueryParameters qParam=new QueryParameters();
	qParam.setSql("select "+ COL_USERNAME_USERS +" from " +TABLE_USERS+ " where "+COL_USERNAME_USERS+ " = ? and  " +COL_PASSWORD_USERS+ " = ?");
	qParam.setSelectionArgs(new String[]{id+"",pwd+""});
	return qParam ;	
	}
	//punit 15022014
	public static QueryParameters GetUserId()
	{	
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("Select "+COL_ConnectionNo + " , "+ COL_CustomerName+" from "+ TABLE_SBM_BillCollDataMain); 
		return qParam;	
	}

	//punit 15022014 

	/*public static QueryParameters GetAllDatafromDb(String custId )
	{	QueryParameters qParam=new QueryParameters();
	qParam.setSql("Select "+COL_UID_S_BcData + " , " + COL_ForMonth+" , " + COL_ForMonth+" , " + COL_BillDate+" , "+COL_SubId+" , "+COL_ConnectionNo+" , "+
			COL_CustomerName+" , "+COL_TourplanId+" , "+COL_BillNo+" , "+COL_DueDate+" , "+COL_FixedCharges+" , "+COL_RebateFlag+" , "+COL_ReaderCode+" , " + 
			COL_TariffCode+" , "+COL_TarifString+" , "+COL_ReadingDay+" , "+COL_PF+" , "+COL_MF+" , "+COL_Status+" , "+COL_AvgCons+" , "+COL_LinMin+" , "+COL_SancHp+" , " + 
			COL_SancKw+" , "+COL_SancLoad+" , "+COL_PrevRdg+" , "+COL_DlCount+" , "+COL_Arears+" , "+COL_IntrstCurnt+" , "+COL_DrFees+" , "+COL_Others+" , " + 
			COL_BillFor+" , "+COL_BlCnt+" , "+COL_RRNo+" , "+COL_LegFol+" , "+COL_TvmMtr+" , "+COL_TaxFlag+" , "+COL_ArrearsOld+" , "+COL_Intrst_Unpaid+" , " + 
			COL_IntrstOld+" , "+COL_Billable+" , "+COL_NewNoofDays+" , "+COL_NoOfDays+" , "+COL_HWCReb+" , "+COL_DLAvgMin+" , "+COL_TvmPFtype+" , "+COL_AccdRdg+" , " 
			+COL_KVAIR+" , "+COL_DLTEc+" , "+COL_RRFlag+" , "+COL_Mtd+" , "+COL_SlowRtnPge+" , "+COL_OtherChargeLegend+" , "+COL_GoKArrears+" , "+COL_DPdate+" , " 
			+COL_ReceiptAmnt+" , "+COL_ReceiptDate+" , "+COL_TcName+" , "+COL_ThirtyFlag+" , "+COL_IODRemarks+" , "+COL_DayWise_Flag+" , "+COL_Old_Consumption+" , "+
			COL_KVAAssd_Cons+" , "+COL_GvpId+" , "+COL_BOBilled_Amount+" , "+COL_KVAH_OldConsumption+" , "+COL_EcsFlag+" , "+COL_Supply_Points+" , "+COL_IODD11_Remarks+
			" , "+COL_LocationCode+" , "+COL_ReaderCode+" , "+COL_BOBillFlag+" , "+COL_Address1+" , "+COL_Address2+" , "+COL_SectionName+" , "+COL_OldConnID+" , "+
			COL_MCHFlag+" , "+COL_FC_Slab_2+" , "+COL_MobileNo+" , "+COL_PreRead+" , "+COL_PreStatus+" , "+COL_SpotSerialNo+" , "+COL_Units+" , "+COL_TFc+" , "+COL_TEc+
			" , "+COL_FLReb+" , "+COL_ECReb+" , "+COL_TaxAmt+" , "+COL_PfPenAmt+" , "+COL_PenExLd+" , "+COL_HCReb+" , "+COL_HLReb+" , "+COL_CapReb+" , "+COL_ExLoad+" , "+
			COL_DemandChrg+" , "+COL_AccdRdg_rtn+" , "+COL_KVAFR+" , "+COL_AbFlag+" , "+COL_BjKj2Lt2+" , "+COL_Remarks+" , "+COL_GoKPayable+" , "+COL_IssueDateTime+" , "+
			COL_RecordDmnd+" , "+COL_KVA_Consumption+" , "+COL_BillTotal+" , "+COL_SBMNumber+" , "+COL_RcptCnt+" , "+COL_Batch_No+" , "+COL_Receipt_No+" , "+COL_DateTime+" , "+
			COL_Payment_Mode+" , "+COL_Paid_Amt+" , "+COL_ChequeDDNo+" , "+COL_ChequeDDDate+" , "+COL_Receipttypeflag+" , "+COL_ApplicationNo+" , "+COL_ChargetypeID+" , "+
			COL_BankID+" , "+COL_Latitude+" , "+COL_Latitude_Dir+" , "+COL_Longitude+" , "+COL_Longitude_Dir+" , "+COL_Gprs_Flag+" , "+COL_ConsPayable+" , "+COL_MtrDisFlag+" , "+
			COL_Meter_type+" , "+COL_Meter_serialno+" , "+COL_Gps_Latitude_image+" , "+COL_Gps_LatitudeCardinal_image+" , "+COL_Gps_Longitude_image+" , "+COL_Gps_LongitudeCardinal_image+" , "+
			COL_Gps_Latitude_print+" , "+COL_ReaderCode+" , "+COL_ReaderCode+" , "+COL_ReaderCode+" , "+COL_Gps_LatitudeCardinal_print+" , "+COL_Gps_Longitude_print+" , "+
			COL_Gps_LongitudeCardinal_print+" , "+COL_Image_Name+" , "+COL_Image_Path+" , "+COL_Image_Cap_Date+" , "+COL_Image_Cap_Time+" from "+TABLE_SBM_BillCollDataMain+ " INNER JOIN " +TABLE_ReadSlabNTariff+ " on " +COL_TarifCode+ " = "+ COL_TariffCode +
			" where "+COL_ConnectionNo + " =? ");
	qParam.setSelectionArgs(new String []{custId});
	return qParam;	
	}*/
	//Modified by Nitish on 21-04-2016
	public static QueryParameters GetAllDatafromDb(String custId)
	{	QueryParameters qParam=new QueryParameters();
	qParam.setSql("Select "+COL_UID_S_BcData + " , " + COL_ForMonth+" , " + COL_ForMonth+" , " + COL_BillDate+" , "+COL_SubId+" , "+COL_ConnectionNo+" , "+
			COL_CustomerName+" , "+COL_TourplanId+" , "+COL_BillNo+" , "+COL_DueDate+" , "+COL_FixedCharges+" , "+COL_RebateFlag+" , "+COL_ReaderCode+" , " + 
			COL_TariffCode+" , "+COL_TarifString+" , "+COL_ReadingDay+" , "+COL_PF+" , "+COL_MF+" , "+COL_Status+" , "+COL_AvgCons+" , "+COL_LinMin+" , "+COL_SancHp+" , " + 
			COL_SancKw+" , "+COL_SancLoad+" , "+COL_PrevRdg+" , "+COL_DlCount+" , "+COL_Arears+" , "+COL_IntrstCurnt+" , "+COL_DrFees+" , "+COL_Others+" , " + 
			COL_BillFor+" , "+COL_BlCnt+" , "+COL_RRNo+" , "+COL_LegFol+" , "+COL_TvmMtr+" , "+COL_TaxFlag+" , "+COL_ArrearsOld+" , "+COL_Intrst_Unpaid+" , " + 
			COL_IntrstOld+" , "+COL_Billable+" , "+COL_NewNoofDays+" , "+COL_NoOfDays+" , "+COL_HWCReb+" , "+COL_DLAvgMin+" , "+COL_TvmPFtype+" , "+COL_AccdRdg+" , " 
			+COL_KVAIR+" , "+COL_DLTEc+" , "+COL_RRFlag+" , "+COL_Mtd+" , "+COL_SlowRtnPge+" , "+COL_OtherChargeLegend+" , "+COL_GoKArrears+" , "+COL_DPdate+" , " 
			+COL_ReceiptAmnt+" , "+COL_ReceiptDate+" , "+COL_TcName+" , "+COL_ThirtyFlag+" , "+COL_IODRemarks+" , "+COL_DayWise_Flag+" , "+COL_Old_Consumption+" , "+
			COL_KVAAssd_Cons+" , "+COL_GvpId+" , "+COL_BOBilled_Amount+" , "+COL_KVAH_OldConsumption+" , "+COL_EcsFlag+" , "+COL_Supply_Points+" , "+COL_IODD11_Remarks+
			" , "+COL_LocationCode+" , "+COL_ReaderCode+" , "+COL_BOBillFlag+" , "+COL_Address1+" , "+COL_Address2+" , "+COL_SectionName+" , "+COL_OldConnID+" , "+
			COL_MCHFlag+" , "+COL_FC_Slab_2+" , "+COL_MobileNo+" , "+COL_PreRead+" , "+COL_PreStatus+" , "+COL_SpotSerialNo+" , "+COL_Units+" , "+COL_TFc+" , "+COL_TEc+
			" , "+COL_FLReb+" , "+COL_ECReb+" , "+COL_TaxAmt+" , "+COL_PfPenAmt+" , "+COL_PenExLd+" , "+COL_HCReb+" , "+COL_HLReb+" , "+COL_CapReb+" , "+COL_ExLoad+" , "+
			COL_DemandChrg+" , "+COL_AccdRdg_rtn+" , "+COL_KVAFR+" , "+COL_AbFlag+" , "+COL_BjKj2Lt2+" , "+COL_Remarks+" , "+COL_GoKPayable+" , "+COL_IssueDateTime+" , "+
			COL_RecordDmnd+" , "+COL_KVA_Consumption+" , "+COL_BillTotal+" , "+COL_SBMNumber+" , "+COL_RcptCnt+" , "+COL_Batch_No+" , "+COL_Receipt_No+" , "+COL_DateTime+" , "+
			COL_Payment_Mode+" , "+COL_Paid_Amt+" , "+COL_ChequeDDNo+" , "+COL_ChequeDDDate+" , "+COL_Receipttypeflag+" , "+COL_ApplicationNo+" , "+COL_ChargetypeID+" , "+
			COL_BankID+" , "+COL_Latitude+" , "+COL_Latitude_Dir+" , "+COL_Longitude+" , "+COL_Longitude_Dir+" , "+COL_Gprs_Flag+" , "+COL_ConsPayable+" , "+COL_MtrDisFlag+" , "+
			COL_Meter_type+" , "+COL_Meter_serialno+" , "+COL_Gps_Latitude_image+" , "+COL_Gps_LatitudeCardinal_image+" , "+COL_Gps_Longitude_image+" , "+COL_Gps_LongitudeCardinal_image+" , "+
			COL_Gps_Latitude_print+" , "+COL_ReaderCode+" , "+COL_ReaderCode+" , "+COL_ReaderCode+" , "+COL_Gps_LatitudeCardinal_print+" , "+COL_Gps_Longitude_print+" , "+
			COL_Gps_LongitudeCardinal_print+" , "+COL_Image_Name+" , "+COL_Image_Path+" , "+COL_Image_Cap_Date+" , "+COL_Image_Cap_Time+ " , "+ //COL_UID_Slab+", "+
			COL_ECRate_Count+", "+COL_ECRate_Row+", "+COL_FCRate_1+", "+COL_FCRate_2+//+COL_ConnectionNo_Slab+", "+COL_RRNo_Slab
			", "+COL_Units_1+", "+COL_Units_2+", "+COL_Units_3+", "+COL_Units_4+", "+COL_Units_5+", "+COL_Units_6+", "+COL_EC_Rate_1+", "+COL_EC_Rate_2+
			", "+COL_EC_Rate_3+", "+COL_EC_Rate_4+", "+COL_EC_Rate_5+", "+COL_EC_Rate_6+", "+COL_EC_1+", "+COL_EC_2+", "+COL_EC_3+", "+COL_EC_4+", "+COL_EC_5+
			", " + COL_EC_6 + ", " + COL_FC_1 + ", "+ COL_FC_2 + ", " + COL_TEc_Slab + ", " + COL_EC_FL_1 + ", " + COL_EC_FL_2 + ", " + COL_EC_FL_3 + ", " + COL_EC_FL_4 + ", " + COL_EC_FL
			+ ", " + COL_new_TEc + ", " + COL_old_TEc + ", " + COL_METER_PRESENT_FLAG + ", " + COL_MTR_NOT_VISIBLE + ", " + COL_DLTEc_GoK 
			+ " , " + COL_AadharNo + " , " +COL_VoterIdNo + " , " + COL_MtrMakeFlag + "," + COL_MtrBodySealFlag + " , " + COL_MtrTerminalCoverFlag + " , " + COL_MtrTerminalCoverSealedFlag + " , " + COL_FACValue + " , " + COL_OldTecBill +
			" from "+TABLE_SBM_BillCollDataMain+ 
			" INNER JOIN " +TABLE_ReadSlabNTariff+ " on " +COL_TarifCode+ " = "+ COL_TariffCode +
			" LEFT JOIN " +TABLE_EC_FC_Slab+ " on trim("+COL_ConnectionNo_Slab+") = trim("+ COL_ConnectionNo +	
			" ) where "+COL_ConnectionNo + " =?  order by " + COL_UID_R_Slab +" asc limit 1 " ); //
	//punit end
	qParam.setSelectionArgs(new String []{custId});
	return qParam;	
	}
	//Nitish 
	//Nitish 24-02-2014
	public static QueryParameters GetConnIdRRNo()
	{	QueryParameters qParam=new QueryParameters();
	qParam.setSql(
			"Select "+COL_ConnectionNo + " as [ConnId], " +" ('COID:' || " + COL_ConnectionNo  + " ) as [ConnIdRRNo]  from "+ TABLE_SBM_BillCollDataMain +
			" Union " + 
			"Select "+COL_ConnectionNo + " as [ConnId], " + "('RRNO:' || " + COL_RRNo + " ) as [ConnIdRRNo]  from "+ TABLE_SBM_BillCollDataMain +
			" Union " +
			"Select "+COL_ConnectionNo + " as [ConnId], " + "('SLNO:' || " + COL_Meter_serialno + " ) as [ConnIdRRNo]  from "+ TABLE_SBM_BillCollDataMain );

	return qParam;	
	}

	//Nitish on 12-03-2014
	public static QueryParameters GetReportBillingList()
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("Select (B.Billed1 + B.NotBilled1) as [TotalInstallations],B.Billed1 as [Billed],B.NotBilled1 as [NotBilled]," +
				"B.BillDate as [BilledDate],B.ReaderCode as [MRName] from " +
				" (Select 0 as [TotalInstallations1], Sum(A.Billed2) as [Billed1],Sum(A.NotBilled2) as [NotBilled1],A.Billdate ," +
				"A.ReaderCode from " +
				" (select  count(" + COL_ConnectionNo + ") as Billed2 , 0 as NotBilled2, " + COL_BillDate + " as [billdate], " + 
				COL_ReaderCode + "  as [ReaderCode] from "+ TABLE_SBM_BillCollDataMain + " where " +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag+" = 1 group by " + 
				COL_BillDate + 
				" union All " +
				" select  0 as Billed2 , count(" + COL_ConnectionNo + ") as NotBilled2," + COL_BillDate + " as [billdate], " + 
				COL_ReaderCode + "  as [ReaderCode]  	from "+ TABLE_SBM_BillCollDataMain + " where " +COL_BlCnt + " = 0 AND "+ COL_BOBillFlag+"= 0 group by " +
				COL_BillDate + ")" +
				"A group by A.Billdate)B ");


		return qParam;
	}

	public static QueryParameters GetReportStatusList()
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("Select (B.Billed1 + B.NotBilled1) as [TotalInstallations],B.Billed1 as [Billed],B.NotBilled1 as [NotBilled],B.Normal1 as [Normal],B.DL1 as [DL],B.MNR1 as [MNR],B.DO1 as [DO],B.MCH1 as [MCH],B.RNF1 as [RNF],B.VA1 as [VA],B.DIS1 as [DIS],B.MS1 as [MS],B.DIR1 as [DIR],B.MB1 as [MB],B.NT1 as [NT],B.BillDate as [BilledDate],B.ReaderCode as [MRName] from " +
				" (Select 0 as [TotalInstallations],Sum(A.Billed2) as [Billed1],Sum(A.NotBilled2) as [NotBilled1],Sum(A.Normal2) as [Normal1] ,Sum(A.DL2) as [DL1],Sum(A.MNR2) as MNR1,Sum(A.DO2) as [DO1],sum(A.MCH2) as MCH1,sum(A.RNF2) as [RNF1],sum(A.VA2) as [VA1],sum(A.DIS2) as [DIS1],sum(A.MS2) as [MS1],sum(A.DIR2) as [DIR1],Sum(A.MB2) as [MB1],Sum(A.NT2) as [NT1],A.Billdate as [BillDate],A.ReaderCode as [ReaderCode] from " +
				" (select  count(" + COL_ConnectionNo + ") as Billed2 , 0 as NotBilled2,0 as Normal2,0 as DL2,0 as MNR2,0 as DO2,0 as MCH2,0 as RNF2,0 as VA2,0 as DIS2,0 as MS2,0 as DIR2,0 as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where " +COL_BlCnt + " > 0 OR "+COL_BOBillFlag+"= 1  group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , count(" + COL_ConnectionNo + ") as NotBilled2,0 as Normal2,0 as DL2,0 as MNR2,0 as DO2,0 as MCH2,0 as RNF2,0 as VA2,0 as DIS2,0 as MS2,0 as DIR2,0 as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where " +COL_BlCnt + " = 0 AND "+COL_BOBillFlag+"= 0 group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , 0 as NotBilled2,count(" + COL_ConnectionNo + ") as Normal2,0 as DL2,0 as MNR2,0 as DO2,0 as MCH2,0 as RNF2,0 as VA2,0 as DIS2,0 as MS2,0 as DIR2,0 as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where (" +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag +"= 1) and (" +COL_PreStatus+ " ='00' OR "+COL_PreStatus+" = '0') group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , 0 as NotBilled2,0 as Normal2,count(" + COL_ConnectionNo + ") as DL2,0 as MNR2,0 as DO2,0 as MCH2,0 as RNF2,0 as VA2,0 as DIS2,0 as MS2,0 as DIR2,0 as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where (" +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag +"= 1) and (" +COL_PreStatus+ "='01' OR "+COL_PreStatus+" = '1') group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , 0 as NotBilled2,0 as Normal2,0 as DL2,count(" + COL_ConnectionNo + ") as MNR2,0 as DO2,0 as MCH2,0 as RNF2,0 as VA2,0 as DIS2,0 as MS2,0 as DIR2,0 as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where (" +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag +"= 1) and (" +COL_PreStatus+ "='02' OR "+COL_PreStatus+" = '2') group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , 0 as NotBilled2,0 as Normal2,0 as DL2,0 as MNR2,count(" + COL_ConnectionNo + ") as DO2,0 as MCH2,0 as RNF2,0 as VA2,0 as DIS2,0 as MS2,0 as DIR2,0 as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where (" +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag +"= 1) and (" +COL_PreStatus+ "='03' OR "+COL_PreStatus+" = '3') group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , 0 as NotBilled2,0 as Normal2,0 as DL2,0 as MNR2,0 as DO2,count(" + COL_ConnectionNo + ") as MCH2,0 as RNF2,0 as VA2,0 as DIS2,0 as MS2,0 as DIR2,0 as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where (" +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag +"= 1) and (" +COL_PreStatus+ "='04' OR "+COL_PreStatus+" = '4') group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , 0 as NotBilled2,0 as Normal2,0 as DL2,0 as MNR2,0 as DO2,0 as MCH2,count(" + COL_ConnectionNo + ") as RNF2,0 as VA2,0 as DIS2,0 as MS2,0 as DIR2,0 as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where (" +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag +"= 1) and (" +COL_PreStatus+ "='05' OR "+COL_PreStatus+" = '5') group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , 0 as NotBilled2,0 as Normal2,0 as DL2,0 as MNR2,0 as DO2,0 as MCH2,0 as RNF2,count(" + COL_ConnectionNo + ") as VA2,0 as DIS2,0 as MS2,0 as DIR2,0 as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where (" +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag +"= 1) and (" +COL_PreStatus+ "='06' OR "+COL_PreStatus+" = '6') group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , 0 as NotBilled2,0 as Normal2,0 as DL2,0 as MNR2,0 as DO2,0 as MCH2,0 as RNF2,0 as VA2,count(" + COL_ConnectionNo + ") as DIS2,0 as MS2,0 as DIR2,0 as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where (" +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag +"= 1) and (" +COL_PreStatus+ "='08' OR "+COL_PreStatus+" = '8') group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , 0 as NotBilled2,0 as Normal2,0 as DL2,0 as MNR2,0 as DO2,0 as MCH2,0 as RNF2,0 as VA2,0 as DIS2,count(" + COL_ConnectionNo + ") as MS2,0 as DIR2,0 as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where (" +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag +"= 1) and (" +COL_PreStatus+ "='09' OR "+COL_PreStatus+" = '9') group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , 0 as NotBilled2,0 as Normal2,0 as DL2,0 as MNR2,0 as DO2,0 as MCH2,0 as RNF2,0 as VA2,0 as DIS2,0 as MS2,count(" + COL_ConnectionNo + ") as DIR2,0 as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where (" +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag +"= 1) and " +COL_PreStatus+ "='10' group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , 0 as NotBilled2,0 as Normal2,0 as DL2,0 as MNR2,0 as DO2,0 as MCH2,0 as RNF2,0 as VA2,0 as DIS2,0 as MS2,0 as DIR2,count(" + COL_ConnectionNo + ") as MB2,0 as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where (" +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag +"= 1) and " +COL_PreStatus+ "='14' group by " + COL_BillDate + 
				" union " +
				" select  0 as Billed2 , 0 as NotBilled2,0 as Normal2,0 as DL2,0 as MNR2,0 as DO2,0 as MCH2,0 as RNF2,0 as VA2,0 as DIS2,0 as MS2,0 as DIR2,0 as MB2,count(" + COL_ConnectionNo + ") as NT2,   " + COL_BillDate + " as [billdate], " + COL_ReaderCode + "  as [ReaderCode] " +
				" from "+ TABLE_SBM_BillCollDataMain + " where (" +COL_BlCnt + " > 0 OR "+ COL_BOBillFlag +"= 1) and " +COL_PreStatus+ "='15' group by " + COL_BillDate + 
				" )A group by A.Billdate) B ");
		return qParam;
	}	
	public static QueryParameters GetReportNotBilledList()
	{
		QueryParameters qParam=new QueryParameters();		

		qParam.setSql("Select " + COL_ConnectionNo + " as [ConnectionNo], "+ COL_RRNo +" as [RRNo]," + COL_TourplanId + " as [TourPlanId] from " +  TABLE_SBM_BillCollDataMain + " where " + COL_BlCnt + " = 0 AND "+ COL_BOBillFlag+"= 0  limit 20 ");


		return qParam;
	}	
	public static QueryParameters GetReportGPRSStatus()
	{
		QueryParameters qParam=new QueryParameters();			

		qParam.setSql("Select Sum(A.TotalInstallations) as [TotalInstallations],Sum(A.Billed) as [Billed],Sum(A.NotBilled) as [NotBilled],A.ReaderCode as [MRName],Sum(A.Gprs_Sent) as [GPRS_Sent] from " +
				" (Select count(" + COL_ConnectionNo + ") as [TotalInstallations],0 as [Billed],0 as [NotBilled],0 as Gprs_Sent," + COL_ReaderCode + "  as [ReaderCode] from SBM_BillCollDataMain " +
				" union " +
				" Select 0 as [TotalInstallations],count(" + COL_ConnectionNo + ") as [Billed],0 as [NotBilled] ,0 as Gprs_Sent ," + COL_ReaderCode + "  as [ReaderCode]  from SBM_BillCollDataMain where " +COL_BlCnt + " > 0  " +
				" union " +
				" Select 0 as [TotalInstallations],0 as [Billed],count(" + COL_ConnectionNo + ") as [NotBilled],0 as Gprs_Sent ," + COL_ReaderCode + "  as [ReaderCode]   from SBM_BillCollDataMain where " +COL_BlCnt + " = 0 " +
				" union " +
				" Select 0 as [TotalInstallations],0 as [Billed],0 as [NotBilled],count(" + COL_ConnectionNo + ") as Gprs_Sent ," + COL_ReaderCode + "  as [ReaderCode]   from SBM_BillCollDataMain where " +COL_BlCnt + " > 0 and " +COL_Gprs_Flag+ " =1 )A ");

		return qParam;
	}
	//End Nitish on 12-03-2014

	//Nitish on 19-03-2014
	public static QueryParameters GetBankList()
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("Select "+COL_BID_BANKLIST + " , "+ COL_BANKID_BANKLIST +" from "+ TABLE_BANKLIST); 
		return qParam;	
	}
	//Nitish on 08-04-2014
	public static QueryParameters GetDataSentToServerColl()
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("SELECT "+COL_ConnectionNo_COLLECTION_TABLE+", "+COL_RRNo_COLLECTION_TABLE+", "+COL_RcptCnt_COLLECTION_TABLE+", "+COL_Batch_No_COLLECTION_TABLE+", "+COL_Receipt_No_COLLECTION_TABLE+", "+
				COL_DateTime_COLLECTION_TABLE+", "+COL_Payment_Mode_COLLECTION_TABLE+", "+COL_Paid_Amt_COLLECTION_TABLE+", "+COL_BankID_COLLECTION_TABLE+", "+COL_ChequeDDNo_COLLECTION_TABLE+", "+COL_ChequeDDDate_COLLECTION_TABLE+", "+COL_GvpId_COLLECTION_TABLE+", "+
				COL_Receipttypeflag_COLLECTION_TABLE + ", " +COL_SBMNumber_COLLECTION_TABLE+", "+COL_LocationCode_COLLECTION_TABLE+				
				" FROM "+TABLE_COLLECTION_TABLE+
				"  WHERE IFNULL("+
				COL_Gprs_Flag_COLLECTION_TABLE+", 0) = 0 AND "+ COL_RcptCnt_COLLECTION_TABLE + " = 1 limit 30");
		return qParam;	
	}

	//Modified Nitish on 12-06-2014
	public static QueryParameters GetMaxReceiptNo(String batchno)
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql(" Select  " + COL_Receipt_No_COLLECTION_TABLE +"  as [MaxReceiptNo] from " + TABLE_COLLECTION_TABLE + " where " + COL_Batch_No_COLLECTION_TABLE + " =  ? order by " +  COL_UID_COLLECTION_TABLE + " desc limit 1") ;		
		qParam.setSelectionArgs(new String []{batchno});
		return qParam;

	}
	//Nitish on 19-04-2014
	public static QueryParameters GetLocationCode()
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql(" Select " + COL_LocationCode + " as [LocationCode] from " + TABLE_SBM_BillCollDataMain + " limit 1 " );		
		return qParam;	
	}
	//Nitish on 19-04-2014
	public static QueryParameters GetCollectionAmount(String batchno)
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql(" Select IFNULL(Sum( " + COL_Paid_Amt_COLLECTION_TABLE + " ), 0) " + " as [CollectionAmt] from " + TABLE_COLLECTION_TABLE  + " WHERE " +COL_Batch_No_COLLECTION_TABLE +" = ?");		
		qParam.setSelectionArgs(new String []{batchno});
		return qParam;	
	}		
	//Modified Nitish on 06-08-2015
	public static QueryParameters GetCashCounterDetails()
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql("Select "+COL_Batch_No_CASHCOUNTER_DETAILS + " , "+ COL_BatchDate_CASHCOUNTER_DETAILS + " , " + COL_CashLimit_CASHCOUNTER_DETAILS + " , " +
				COL_StartTime_CASHCOUNTER_DETAILS + " , " + COL_EndTime_CASHCOUNTER_DETAILS +  " , " + COL_CashCounterOpen_CASHCOUNTER_DETAILS + " , ifnull(" + COL_ExtensionFlag_CASHCOUNTER_DETAILS + ",0) as [ExtFlag] " +
				" from "+ TABLE_CASHCOUNTER_DETAILS + " order by " +  COL_UID_CASHCOUNTER_DETAILS + " desc limit 1"); 
		return qParam;	
	}
	//03-05-2014 Modified 24-07-2014		
	public static QueryParameters GetRcptCntForConnection(String connid,String rcpttype)
	{
		QueryParameters qParam = new QueryParameters();	

		qParam.setSql("Select "+COL_ConnectionNo_COLLECTION_TABLE + " , "+ COL_Batch_No_COLLECTION_TABLE + " , " + COL_RcptCnt_COLLECTION_TABLE +
				" from "+ TABLE_COLLECTION_TABLE + " WHERE "  + COL_ConnectionNo_COLLECTION_TABLE + "= ? and " + COL_Receipttypeflag_COLLECTION_TABLE + " =  ? " );
		qParam.setSelectionArgs(new String[]{connid+"",rcpttype+""});
		return qParam;	
	}
	//Nitish 03-05-2014	//Modified 05-05-2014
	public static QueryParameters GetBankName(int bid)
	{
		QueryParameters qParam = new QueryParameters();	
		qParam.setSql("Select "+COL_BID_BANKLIST + " , "+ COL_BANKID_BANKLIST +" from "+ TABLE_BANKLIST + " WHERE " +COL_BID_BANKLIST +" = ? ");			
		qParam.setSelectionArgs(new String[]{String.valueOf(bid)});
		return qParam;	
	}
	//Modified 05-05-2014 //Modified 19-08-2014
	public static QueryParameters GetCollectionForConnId(String conid,String rcpttype)
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("SELECT "+COL_ConnectionNo_COLLECTION_TABLE+", "+COL_RRNo_COLLECTION_TABLE+", "+COL_CustomerName_COLLECTION_TABLE+ " , "+COL_RcptCnt_COLLECTION_TABLE+", "+COL_Batch_No_COLLECTION_TABLE+", "+COL_Receipt_No_COLLECTION_TABLE+", "+
				COL_DateTime_COLLECTION_TABLE+", "+COL_Payment_Mode_COLLECTION_TABLE+", "+COL_Arrears_COLLECTION_TABLE+ "," + COL_BillTotal_COLLECTION_TABLE + "," + COL_Paid_Amt_COLLECTION_TABLE+","+
				COL_BankID_COLLECTION_TABLE+", "+COL_ChequeDDNo_COLLECTION_TABLE+", "+COL_ChequeDDDate_COLLECTION_TABLE+", "+COL_GvpId_COLLECTION_TABLE+", "+ COL_Receipttypeflag_COLLECTION_TABLE + "," +
				COL_SBMNumber_COLLECTION_TABLE+", "+COL_LocationCode_COLLECTION_TABLE+	","	+ COL_Gprs_Flag_COLLECTION_TABLE + 	","	+ COL_ArrearsBill_Flag_COLLECTION_TABLE + "," + COL_ReaderCode_COLLECTION_TABLE + " , " + COL_IODRemarks_COLLECTION_TABLE +
				" FROM "+TABLE_COLLECTION_TABLE+
				"  WHERE "+COL_ConnectionNo_COLLECTION_TABLE+ " = ?  and " + COL_Receipttypeflag_COLLECTION_TABLE + " = ? " );
		qParam.setSelectionArgs(new String[]{conid+"",rcpttype+""});		
		return qParam;	
	}
	//Nitish 05-05-2014
	public static QueryParameters GetCountGPRSSentConnectionColl()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionNo_COLLECTION_TABLE+") as [COUNT] FROM "+TABLE_COLLECTION_TABLE+" WHERE " +COL_Gprs_Flag_COLLECTION_TABLE+" = 1 ");
		qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//Nitish 05-05-2014
	public static QueryParameters GetCountforColl()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionNo_COLLECTION_TABLE+") as [COUNT] FROM "+TABLE_COLLECTION_TABLE);		
		return qParam;
	}

	//Modified 19-08-2014
	public static QueryParameters GetReportBescomCopyPrint(String batchno)
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("SELECT "+COL_ConnectionNo_COLLECTION_TABLE+", "+COL_RRNo_COLLECTION_TABLE+", "+COL_CustomerName_COLLECTION_TABLE+ " , "+COL_RcptCnt_COLLECTION_TABLE+", "+COL_Batch_No_COLLECTION_TABLE+", "+COL_Receipt_No_COLLECTION_TABLE+", "+
				COL_DateTime_COLLECTION_TABLE+", "+COL_Payment_Mode_COLLECTION_TABLE+", "+COL_Arrears_COLLECTION_TABLE+ "," + COL_BillTotal_COLLECTION_TABLE + "," + COL_Paid_Amt_COLLECTION_TABLE+","+
				COL_BankID_COLLECTION_TABLE+", "+COL_ChequeDDNo_COLLECTION_TABLE+", "+COL_ChequeDDDate_COLLECTION_TABLE+", "+COL_GvpId_COLLECTION_TABLE+", "+ COL_Receipttypeflag_COLLECTION_TABLE + "," +
				COL_SBMNumber_COLLECTION_TABLE+", "+COL_LocationCode_COLLECTION_TABLE+	","	+ COL_Gprs_Flag_COLLECTION_TABLE + 	","	+ COL_ArrearsBill_Flag_COLLECTION_TABLE + "," + COL_ReaderCode_COLLECTION_TABLE + " , " + COL_IODRemarks_COLLECTION_TABLE+
				" FROM " +TABLE_COLLECTION_TABLE+ " WHERE " + COL_Batch_No_COLLECTION_TABLE +" = ? " );
		qParam.setSelectionArgs(new String[]{batchno});
		return qParam;	
	}
	//Nitish 07-05-2014
	public static QueryParameters GetBillAmount()
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql(" Select IFNULL(Sum( " + COL_BillTotal + " ), 0) " + " as [BillAmt] from " + TABLE_SBM_BillCollDataMain  + " WHERE " +COL_BlCnt +" = 1");	
		return qParam;	
	}
	//Nitish 07-05-2014
	public static QueryParameters GetMRName()
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql(" Select " + COL_ReaderCode_COLLECTION_TABLE + " as [MRName] from " + TABLE_COLLECTION_TABLE + " limit 1 " );		
		return qParam;	
	}
	//Nitish 07-05-2014
	public static QueryParameters GetTotalCollectionAmount()
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql(" Select IFNULL(Sum( " + COL_Paid_Amt_COLLECTION_TABLE + " ), 0) " + " as [CollectionAmt] from " + TABLE_COLLECTION_TABLE  );		
		return qParam;	
	}
	//Nitish 08-05-2014
	public static QueryParameters GetCountforBatchColl(String batchno)
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionNo_COLLECTION_TABLE+") as [COUNT] FROM "+TABLE_COLLECTION_TABLE+ " WHERE " +COL_Batch_No_COLLECTION_TABLE +" = ?");			
		qParam.setSelectionArgs(new String []{batchno});
		return qParam;	
	}	

	//Modified 07-05-2014
	public static QueryParameters GetReportRRNOWiseColl(String batchno)
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("SELECT "+COL_ConnectionNo_COLLECTION_TABLE+", "+COL_RRNo_COLLECTION_TABLE+", "+COL_CustomerName_COLLECTION_TABLE+ " , "+COL_RcptCnt_COLLECTION_TABLE+", "+COL_Batch_No_COLLECTION_TABLE+", "+COL_Receipt_No_COLLECTION_TABLE+", "+
				COL_DateTime_COLLECTION_TABLE+", "+COL_Payment_Mode_COLLECTION_TABLE+", "+COL_Arrears_COLLECTION_TABLE+ "," + COL_BillTotal_COLLECTION_TABLE + "," + COL_Paid_Amt_COLLECTION_TABLE+","+
				COL_BankID_COLLECTION_TABLE+", "+COL_ChequeDDNo_COLLECTION_TABLE+", "+COL_ChequeDDDate_COLLECTION_TABLE+", "+COL_GvpId_COLLECTION_TABLE+", "+ COL_Receipttypeflag_COLLECTION_TABLE + "," +
				COL_SBMNumber_COLLECTION_TABLE+", "+COL_LocationCode_COLLECTION_TABLE+	","	+ COL_Gprs_Flag_COLLECTION_TABLE + 	","	+ COL_ArrearsBill_Flag_COLLECTION_TABLE + "," + COL_ReaderCode_COLLECTION_TABLE +
				" FROM " +TABLE_COLLECTION_TABLE + 	" WHERE " + COL_Batch_No_COLLECTION_TABLE + " = ?  order by " + COL_RRNo_COLLECTION_TABLE );		
		qParam.setSelectionArgs(new String []{batchno}); 
		return qParam;	
	}

	//Modified Nitish 21-04-2016
	public static QueryParameters GetValueOnBillingPageColl(String rcpttype)
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("Select "+COL_UID_S_BcData + " , " + COL_ForMonth+" , " + COL_ForMonth+" , " + COL_BillDate+" , "+COL_SubId+" , "+COL_ConnectionNo+" , "+
				COL_CustomerName+" , "+COL_TourplanId+" , "+COL_BillNo+" , "+COL_DueDate+" , "+COL_FixedCharges+" , "+COL_RebateFlag+" , "+COL_ReaderCode+" , " + 
				COL_TariffCode+" , "+COL_TarifString+" , "+COL_ReadingDay+" , "+COL_PF+" , "+COL_MF+" , "+COL_Status+" , "+COL_AvgCons+" , "+COL_LinMin+" , "+COL_SancHp+" , " + 
				COL_SancKw+" , "+COL_SancLoad+" , "+COL_PrevRdg+" , "+COL_DlCount+" , "+COL_Arears+" , "+COL_IntrstCurnt+" , "+COL_DrFees+" , "+COL_Others+" , " + 
				COL_BillFor+" , "+COL_BlCnt+" , "+COL_RRNo+" , "+COL_LegFol+" , "+COL_TvmMtr+" , "+COL_TaxFlag+" , "+COL_ArrearsOld+" , "+COL_Intrst_Unpaid+" , " + 
				COL_IntrstOld+" , "+COL_Billable+" , "+COL_NewNoofDays+" , "+COL_NoOfDays+" , "+COL_HWCReb+" , "+COL_DLAvgMin+" , "+COL_TvmPFtype+" , "+COL_AccdRdg+" , " 
				+COL_KVAIR+" , "+COL_DLTEc+" , "+COL_RRFlag+" , "+COL_Mtd+" , "+COL_SlowRtnPge+" , "+COL_OtherChargeLegend+" , "+COL_GoKArrears+" , "+COL_DPdate+" , " 
				+COL_ReceiptAmnt+" , "+COL_ReceiptDate+" , "+COL_TcName+" , "+COL_ThirtyFlag+" , "+COL_IODRemarks+" , "+COL_DayWise_Flag+" , "+COL_Old_Consumption+" , "+
				COL_KVAAssd_Cons+" , "+COL_GvpId+" , "+COL_BOBilled_Amount+" , "+COL_KVAH_OldConsumption+" , "+COL_EcsFlag+" , "+COL_Supply_Points+" , "+COL_IODD11_Remarks+
				" , "+COL_LocationCode+" , "+COL_ReaderCode+" , "+COL_BOBillFlag+" , "+COL_Address1+" , "+COL_Address2+" , "+COL_SectionName+" , "+COL_OldConnID+" , "+
				COL_MCHFlag+" , "+COL_FC_Slab_2+" , "+COL_MobileNo+" , "+COL_PreRead+" , "+COL_PreStatus+" , "+COL_SpotSerialNo+" , "+COL_Units+" , "+COL_TFc+" , "+COL_TEc+
				" , "+COL_FLReb+" , "+COL_ECReb+" , "+COL_TaxAmt+" , "+COL_PfPenAmt+" , "+COL_PenExLd+" , "+COL_HCReb+" , "+COL_HLReb+" , "+COL_CapReb+" , "+COL_ExLoad+" , "+
				COL_DemandChrg+" , "+COL_AccdRdg_rtn+" , "+COL_KVAFR+" , "+COL_AbFlag+" , "+COL_BjKj2Lt2+" , "+COL_Remarks+" , "+COL_GoKPayable+" , "+COL_IssueDateTime+" , "+
				COL_RecordDmnd+" , "+COL_KVA_Consumption+" , "+COL_BillTotal+" , "+COL_SBMNumber+" , "+COL_RcptCnt+" , "+COL_Batch_No+" , "+COL_Receipt_No+" , "+COL_DateTime+" , "+
				COL_Payment_Mode+" , "+COL_Paid_Amt+" , "+COL_ChequeDDNo+" , "+COL_ChequeDDDate+" , "+COL_Receipttypeflag+" , "+COL_ApplicationNo+" , "+COL_ChargetypeID+" , "+
				COL_BankID+" , "+COL_Latitude+" , "+COL_Latitude_Dir+" , "+COL_Longitude+" , "+COL_Longitude_Dir+" , "+COL_Gprs_Flag+" , "+COL_ConsPayable+" , "+COL_MtrDisFlag+" , "+
				COL_Meter_type+" , "+COL_Meter_serialno+" , "+COL_Gps_Latitude_image+" , "+COL_Gps_LatitudeCardinal_image+" , "+COL_Gps_Longitude_image+" , "+COL_Gps_LongitudeCardinal_image+" , "+
				COL_Gps_Latitude_print+" , "+COL_ReaderCode+" , "+COL_ReaderCode+" , "+COL_ReaderCode+" , "+COL_Gps_LatitudeCardinal_print+" , "+COL_Gps_Longitude_print+" , "+
				COL_Gps_LongitudeCardinal_print+" , "+COL_Image_Name+" , "+COL_Image_Path+" , "+COL_Image_Cap_Date+" , "+COL_Image_Cap_Time+" , "+//COL_UID_Slab+","+
				COL_ECRate_Count+" , "+COL_ECRate_Row+" , "+COL_FCRate_1+" , "+COL_FCRate_2+//+COL_ConnectionNo_Slab+","+COL_RRNo_Slab+","
				" , "+COL_Units_1+" , "+COL_Units_2+" , "+COL_Units_3+" , "+COL_Units_4+" , "+COL_Units_5+" , "+COL_Units_6+" , "+COL_EC_Rate_1+" , "+COL_EC_Rate_2+
				" , "+COL_EC_Rate_3+" , "+COL_EC_Rate_4+" , "+COL_EC_Rate_5+" , "+COL_EC_Rate_6+" , "+COL_EC_1+" , "+COL_EC_2+" , "+COL_EC_3+" , "+COL_EC_4+" , "+COL_EC_5+
				" , "+COL_EC_6+" , "+COL_FC_1+" , "+COL_FC_2+" , "+COL_TEc_Slab+" , "+COL_EC_FL_1+" , "+COL_EC_FL_2+" , "+COL_EC_FL_3+" , "+COL_EC_FL_4+" , "+COL_EC_FL+
				" , "+COL_new_TEc+" , "+COL_old_TEc+ ", " + COL_METER_PRESENT_FLAG + ", " + COL_MTR_NOT_VISIBLE + ", " + COL_DLTEc_GoK +
				" , " + COL_AadharNo + " , " +COL_VoterIdNo + " , " + COL_MtrMakeFlag + "," + COL_MtrBodySealFlag + " , " + COL_MtrTerminalCoverFlag + " , " + COL_MtrTerminalCoverSealedFlag + " , " + COL_FACValue + " , " + COL_OldTecBill +
				" from "+TABLE_SBM_BillCollDataMain+ 
				" LEFT JOIN " +TABLE_COLLECTION_TABLE+ " on trim(  " +COL_ConnectionNo_COLLECTION_TABLE+ " )  = trim("+ COL_ConnectionNo + ") and "  + COL_Receipttypeflag_COLLECTION_TABLE + " = ?" +
				" INNER JOIN " +TABLE_ReadSlabNTariff+ " on " +COL_TarifCode+ " = "+ COL_TariffCode +
				" LEFT JOIN " +TABLE_EC_FC_Slab+ " on trim("+COL_ConnectionNo_Slab+") = trim("+ COL_ConnectionNo +			   
				") where  IFNULL("+ COL_RcptCnt_COLLECTION_TABLE+", 0) = 0 order by "+COL_UID_S_BcData +" asc limit 1");
		qParam.setSelectionArgs(new String []{rcpttype});
		return qParam;
	}
	//Nitish 17-06-2014  Get connid of billed connections whose GPRS is not sent
	public static QueryParameters GetBillingConnIdGPRSNotSent()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT "+COL_ConnectionNo+"  as [NOTSENT] , "  +COL_GPRS_Status +"  as [GPRSSTATUS] FROM "+TABLE_SBM_BillCollDataMain+" WHERE "  +COL_BlCnt+" = 1  and IFNULL( "+	COL_Gprs_Flag+", 0) = 0 ");
		qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//Nitish 17-06-2014 Get connid of Receipts Collected connections whose GPRS is not sent
	public static QueryParameters GetReceiptsConnIdGPRSNotSent()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT "+COL_ConnectionNo_COLLECTION_TABLE+" as [NOTSENT] , " + COL_Receipttypeflag_COLLECTION_TABLE + " , " +COL_GPRS_Status_COLLECTION_TABLE +"  as [GPRSSTATUS] FROM "+TABLE_COLLECTION_TABLE+" WHERE IFNULL( "+ COL_Gprs_Flag_COLLECTION_TABLE +", 0) = 0 ");
		qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//Nitish on  18-06-2014 GetBatchNo with Batch Date
	public static QueryParameters GetCashCounterBatchNos()
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql("Select "+COL_Batch_No_CASHCOUNTER_DETAILS +"  as [BATCHNO] , " +COL_BatchDate_CASHCOUNTER_DETAILS +"  as [BATCHDATE] FROM "+ TABLE_CASHCOUNTER_DETAILS + " order by " +  COL_UID_CASHCOUNTER_DETAILS ); 
		return qParam;	
	}	
	//Nitish on 01-07-2014
	public static QueryParameters GetGPSDataSentToServer()//
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql("SELECT "+COL_UID_GPS_DETAILS+", "+COL_IMEINo_GPS_DETAILS+", "+COL_SIMNo_GPS_DETAILS+", "+COL_DateTime_GPS_DETAILS+", "+COL_MRID_GPS_DETAILS+", "+
				COL_Latitude_GPS_DETAILS+", "+COL_Longitude_GPS_DETAILS+", "+COL_LocationCode_GPS_DETAILS+", "+COL_Gprs_Flag_GPS_DETAILS+					
				" FROM "+TABLE_GPS_DETAILS+
				"  WHERE IFNULL("+COL_Gprs_Flag_GPS_DETAILS+", 0) = 0 ");			
		return qParam;
	}
	//Nitish 07-05-2014
	public static QueryParameters GetMRNameFromSBMBillCollData()
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql(" Select " + COL_ReaderCode + " as [MRName] from " + TABLE_SBM_BillCollDataMain + " limit 1 " );		
		return qParam;	
	}
	//Nitish 04-07-2014
	public static QueryParameters GetCountforGPSDetails()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_UID_GPS_DETAILS+") as [COUNT] FROM "+TABLE_GPS_DETAILS );
		qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//Nitish 04-07-2014
	public static QueryParameters GetCountGPSDetailsSent()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_UID_GPS_DETAILS+") as [COUNT] FROM "+TABLE_GPS_DETAILS+" WHERE " +COL_Gprs_Flag_GPS_DETAILS+" = 1 ");
		return qParam;
	}

	//Nitish 26-08-2014
	public static QueryParameters GetCountforRevnMISCColl(String rcpttype)
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionNo_COLLECTION_TABLE+") as [COUNT] FROM "+TABLE_COLLECTION_TABLE+ "  WHERE  " +COL_Receipttypeflag_COLLECTION_TABLE+"= ?");				
		qParam.setSelectionArgs(new String []{rcpttype}); 
		return qParam;
	}

	//Nitish 26-08-2014
	public static QueryParameters GetTotalRevnMISCCollectionAmount(String rcpttype)
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql(" Select IFNULL(Sum( " + COL_Paid_Amt_COLLECTION_TABLE + " ), 0) " + " as [CollectionAmt] from " + TABLE_COLLECTION_TABLE +"  WHERE  " +COL_Receipttypeflag_COLLECTION_TABLE+"= ?");				
		qParam.setSelectionArgs(new String []{rcpttype}); 
		return qParam;	
	}
	//Nitish  30-08-2014 
	public static QueryParameters GetReason()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("Select " + COL_REASONMASTER_REASONID + " , " +COL_REASONMASTER_REASON
				+ " from " + TABLE_REASONMASTER);		
		return qParam;
	}
	//End Nitish

	//Modified Nitish 21-04-2016
	public static QueryParameters GetPrev(String custId )
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("Select "+COL_UID_S_BcData + " , " + COL_ForMonth+" , " + COL_ForMonth+" , " + COL_BillDate+" , "+COL_SubId+" , "+COL_ConnectionNo+" , "+
				COL_CustomerName+" , "+COL_TourplanId+" , "+COL_BillNo+" , "+COL_DueDate+" , "+COL_FixedCharges+" , "+COL_RebateFlag+" , "+COL_ReaderCode+" , " + 
				COL_TariffCode+" , "+COL_TarifString+" , "+COL_ReadingDay+" , "+COL_PF+" , "+COL_MF+" , "+COL_Status+" , "+COL_AvgCons+" , "+COL_LinMin+" , "+COL_SancHp+" , " + 
				COL_SancKw+" , "+COL_SancLoad+" , "+COL_PrevRdg+" , "+COL_DlCount+" , "+COL_Arears+" , "+COL_IntrstCurnt+" , "+COL_DrFees+" , "+COL_Others+" , " + 
				COL_BillFor+" , "+COL_BlCnt+" , "+COL_RRNo+" , "+COL_LegFol+" , "+COL_TvmMtr+" , "+COL_TaxFlag+" , "+COL_ArrearsOld+" , "+COL_Intrst_Unpaid+" , " + 
				COL_IntrstOld+" , "+COL_Billable+" , "+COL_NewNoofDays+" , "+COL_NoOfDays+" , "+COL_HWCReb+" , "+COL_DLAvgMin+" , "+COL_TvmPFtype+" , "+COL_AccdRdg+" , " 
				+COL_KVAIR+" , "+COL_DLTEc+" , "+COL_RRFlag+" , "+COL_Mtd+" , "+COL_SlowRtnPge+" , "+COL_OtherChargeLegend+" , "+COL_GoKArrears+" , "+COL_DPdate+" , " 
				+COL_ReceiptAmnt+" , "+COL_ReceiptDate+" , "+COL_TcName+" , "+COL_ThirtyFlag+" , "+COL_IODRemarks+" , "+COL_DayWise_Flag+" , "+COL_Old_Consumption+" , "+
				COL_KVAAssd_Cons+" , "+COL_GvpId+" , "+COL_BOBilled_Amount+" , "+COL_KVAH_OldConsumption+" , "+COL_EcsFlag+" , "+COL_Supply_Points+" , "+COL_IODD11_Remarks+
				" , "+COL_LocationCode+" , "+COL_ReaderCode+" , "+COL_BOBillFlag+" , "+COL_Address1+" , "+COL_Address2+" , "+COL_SectionName+" , "+COL_OldConnID+" , "+
				COL_MCHFlag+" , "+COL_FC_Slab_2+" , "+COL_MobileNo+" , "+COL_PreRead+" , "+COL_PreStatus+" , "+COL_SpotSerialNo+" , "+COL_Units+" , "+COL_TFc+" , "+COL_TEc+
				" , "+COL_FLReb+" , "+COL_ECReb+" , "+COL_TaxAmt+" , "+COL_PfPenAmt+" , "+COL_PenExLd+" , "+COL_HCReb+" , "+COL_HLReb+" , "+COL_CapReb+" , "+COL_ExLoad+" , "+
				COL_DemandChrg+" , "+COL_AccdRdg_rtn+" , "+COL_KVAFR+" , "+COL_AbFlag+" , "+COL_BjKj2Lt2+" , "+COL_Remarks+" , "+COL_GoKPayable+" , "+COL_IssueDateTime+" , "+
				COL_RecordDmnd+" , "+COL_KVA_Consumption+" , "+COL_BillTotal+" , "+COL_SBMNumber+" , "+COL_RcptCnt+" , "+COL_Batch_No+" , "+COL_Receipt_No+" , "+COL_DateTime+" , "+
				COL_Payment_Mode+" , "+COL_Paid_Amt+" , "+COL_ChequeDDNo+" , "+COL_ChequeDDDate+" , "+COL_Receipttypeflag+" , "+COL_ApplicationNo+" , "+COL_ChargetypeID+" , "+
				COL_BankID+" , "+COL_Latitude+" , "+COL_Latitude_Dir+" , "+COL_Longitude+" , "+COL_Longitude_Dir+" , "+COL_Gprs_Flag+" , "+COL_ConsPayable+" , "+COL_MtrDisFlag+" , "+
				COL_Meter_type+" , "+COL_Meter_serialno+" , "+COL_Gps_Latitude_image+" , "+COL_Gps_LatitudeCardinal_image+" , "+COL_Gps_Longitude_image+" , "+COL_Gps_LongitudeCardinal_image+" , "+
				COL_Gps_Latitude_print+" , "+COL_ReaderCode+" , "+COL_ReaderCode+" , "+COL_ReaderCode+" , "+COL_Gps_LatitudeCardinal_print+" , "+COL_Gps_Longitude_print+" , "+
				COL_Gps_LongitudeCardinal_print+" , "+COL_Image_Name+" , "+COL_Image_Path+" , "+COL_Image_Cap_Date+" , "+COL_Image_Cap_Time+" , "+//COL_UID_Slab+","+
				COL_ECRate_Count+" , "+COL_ECRate_Row+" , "+COL_FCRate_1+" , "+COL_FCRate_2+//COL_ConnectionNo_Slab+","+COL_RRNo_Slab+","+
				" , "+COL_Units_1+" , "+COL_Units_2+" , "+COL_Units_3+" , "+COL_Units_4+" , "+COL_Units_5+" , "+COL_Units_6+" , "+COL_EC_Rate_1+" , "+COL_EC_Rate_2+
				" , "+COL_EC_Rate_3+" , "+COL_EC_Rate_4+" , "+COL_EC_Rate_5+" , "+COL_EC_Rate_6+" , "+COL_EC_1+" , "+COL_EC_2+" , "+COL_EC_3+" , "+COL_EC_4+" , "+COL_EC_5+
				" , "+COL_EC_6+" , "+COL_FC_1+" , "+COL_FC_2+" , "+COL_TEc_Slab+" , "+COL_EC_FL_1+" , "+COL_EC_FL_2+" , "+COL_EC_FL_3+" , "+COL_EC_FL_4+" , "+COL_EC_FL+
				" , "+COL_new_TEc+" , "+COL_old_TEc+ ", " + COL_METER_PRESENT_FLAG + ", " + COL_MTR_NOT_VISIBLE + ", " + COL_DLTEc_GoK +
				" , " + COL_AadharNo + " , " +COL_VoterIdNo + " , " + COL_MtrMakeFlag + "," + COL_MtrBodySealFlag + " , " + COL_MtrTerminalCoverFlag + " , " + COL_MtrTerminalCoverSealedFlag + " , " + COL_FACValue + " , " + COL_OldTecBill +
				" from "+TABLE_SBM_BillCollDataMain+
				" INNER JOIN " +TABLE_ReadSlabNTariff+ " on " +COL_TarifCode+ " = "+ COL_TariffCode +
				" LEFT JOIN " +TABLE_EC_FC_Slab+ " on trim("+COL_ConnectionNo_Slab+") = trim("+ COL_ConnectionNo +		   
				" ) where "+COL_UID_S_BcData + " =?  limit 1 ");
		qParam.setSelectionArgs(new String []{custId});
		return qParam;
	}
	//punit end 26022014

	//Modified Nitish 21-04-2016
	public static QueryParameters GetValueOnBillingPage()//
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("Select "+COL_UID_S_BcData + " , " + COL_ForMonth+" , " + COL_ForMonth+" , " + COL_BillDate+" , "+COL_SubId+" , "+COL_ConnectionNo+" , "+
				COL_CustomerName+" , "+COL_TourplanId+" , "+COL_BillNo+" , "+COL_DueDate+" , "+COL_FixedCharges+" , "+COL_RebateFlag+" , "+COL_ReaderCode+" , " + 
				COL_TariffCode+" , "+COL_TarifString+" , "+COL_ReadingDay+" , "+COL_PF+" , "+COL_MF+" , "+COL_Status+" , "+COL_AvgCons+" , "+COL_LinMin+" , "+COL_SancHp+" , " + 
				COL_SancKw+" , "+COL_SancLoad+" , "+COL_PrevRdg+" , "+COL_DlCount+" , "+COL_Arears+" , "+COL_IntrstCurnt+" , "+COL_DrFees+" , "+COL_Others+" , " + 
				COL_BillFor+" , "+COL_BlCnt+" , "+COL_RRNo+" , "+COL_LegFol+" , "+COL_TvmMtr+" , "+COL_TaxFlag+" , "+COL_ArrearsOld+" , "+COL_Intrst_Unpaid+" , " + 
				COL_IntrstOld+" , "+COL_Billable+" , "+COL_NewNoofDays+" , "+COL_NoOfDays+" , "+COL_HWCReb+" , "+COL_DLAvgMin+" , "+COL_TvmPFtype+" , "+COL_AccdRdg+" , " 
				+COL_KVAIR+" , "+COL_DLTEc+" , "+COL_RRFlag+" , "+COL_Mtd+" , "+COL_SlowRtnPge+" , "+COL_OtherChargeLegend+" , "+COL_GoKArrears+" , "+COL_DPdate+" , " 
				+COL_ReceiptAmnt+" , "+COL_ReceiptDate+" , "+COL_TcName+" , "+COL_ThirtyFlag+" , "+COL_IODRemarks+" , "+COL_DayWise_Flag+" , "+COL_Old_Consumption+" , "+
				COL_KVAAssd_Cons+" , "+COL_GvpId+" , "+COL_BOBilled_Amount+" , "+COL_KVAH_OldConsumption+" , "+COL_EcsFlag+" , "+COL_Supply_Points+" , "+COL_IODD11_Remarks+
				" , "+COL_LocationCode+" , "+COL_ReaderCode+" , "+COL_BOBillFlag+" , "+COL_Address1+" , "+COL_Address2+" , "+COL_SectionName+" , "+COL_OldConnID+" , "+
				COL_MCHFlag+" , "+COL_FC_Slab_2+" , "+COL_MobileNo+" , "+COL_PreRead+" , "+COL_PreStatus+" , "+COL_SpotSerialNo+" , "+COL_Units+" , "+COL_TFc+" , "+COL_TEc+
				" , "+COL_FLReb+" , "+COL_ECReb+" , "+COL_TaxAmt+" , "+COL_PfPenAmt+" , "+COL_PenExLd+" , "+COL_HCReb+" , "+COL_HLReb+" , "+COL_CapReb+" , "+COL_ExLoad+" , "+
				COL_DemandChrg+" , "+COL_AccdRdg_rtn+" , "+COL_KVAFR+" , "+COL_AbFlag+" , "+COL_BjKj2Lt2+" , "+COL_Remarks+" , "+COL_GoKPayable+" , "+COL_IssueDateTime+" , "+
				COL_RecordDmnd+" , "+COL_KVA_Consumption+" , "+COL_BillTotal+" , "+COL_SBMNumber+" , "+COL_RcptCnt+" , "+COL_Batch_No+" , "+COL_Receipt_No+" , "+COL_DateTime+" , "+
				COL_Payment_Mode+" , "+COL_Paid_Amt+" , "+COL_ChequeDDNo+" , "+COL_ChequeDDDate+" , "+COL_Receipttypeflag+" , "+COL_ApplicationNo+" , "+COL_ChargetypeID+" , "+
				COL_BankID+" , "+COL_Latitude+" , "+COL_Latitude_Dir+" , "+COL_Longitude+" , "+COL_Longitude_Dir+" , "+COL_Gprs_Flag+" , "+COL_ConsPayable+" , "+COL_MtrDisFlag+" , "+
				COL_Meter_type+" , "+COL_Meter_serialno+" , "+COL_Gps_Latitude_image+" , "+COL_Gps_LatitudeCardinal_image+" , "+COL_Gps_Longitude_image+" , "+COL_Gps_LongitudeCardinal_image+" , "+
				COL_Gps_Latitude_print+" , "+COL_ReaderCode+" , "+COL_ReaderCode+" , "+COL_ReaderCode+" , "+COL_Gps_LatitudeCardinal_print+" , "+COL_Gps_Longitude_print+" , "+
				COL_Gps_LongitudeCardinal_print+" , "+COL_Image_Name+" , "+COL_Image_Path+" , "+COL_Image_Cap_Date+" , "+COL_Image_Cap_Time+" , "+//COL_UID_Slab+","+
				COL_ECRate_Count+" , "+COL_ECRate_Row+" , "+COL_FCRate_1+" , "+COL_FCRate_2+//+COL_ConnectionNo_Slab+","+COL_RRNo_Slab+","
				" , "+COL_Units_1+" , "+COL_Units_2+" , "+COL_Units_3+" , "+COL_Units_4+" , "+COL_Units_5+" , "+COL_Units_6+" , "+COL_EC_Rate_1+" , "+COL_EC_Rate_2+
				" , "+COL_EC_Rate_3+" , "+COL_EC_Rate_4+" , "+COL_EC_Rate_5+" , "+COL_EC_Rate_6+" , "+COL_EC_1+" , "+COL_EC_2+" , "+COL_EC_3+" , "+COL_EC_4+" , "+COL_EC_5+
				" , "+COL_EC_6+" , "+COL_FC_1+" , "+COL_FC_2+" , "+COL_TEc_Slab+" , "+COL_EC_FL_1+" , "+COL_EC_FL_2+" , "+COL_EC_FL_3+" , "+COL_EC_FL_4+" , "+COL_EC_FL+
				" , "+COL_new_TEc+" , "+COL_old_TEc+ ", " + COL_METER_PRESENT_FLAG + ", " + COL_MTR_NOT_VISIBLE +", " + COL_DLTEc_GoK +
				" , " + COL_AadharNo + " , " +COL_VoterIdNo + " , " + COL_MtrMakeFlag + "," + COL_MtrBodySealFlag + " , " + COL_MtrTerminalCoverFlag + " , " + COL_MtrTerminalCoverSealedFlag + " , " + COL_FACValue + " , " + COL_OldTecBill +
				" from "+TABLE_SBM_BillCollDataMain+ 
				" INNER JOIN " +TABLE_ReadSlabNTariff+ " on " +COL_TarifCode+ " = "+ COL_TariffCode +
				" LEFT JOIN " +TABLE_EC_FC_Slab+ " on trim("+COL_ConnectionNo_Slab+") = trim("+ COL_ConnectionNo +			   
				") where "+COL_BlCnt + " =0 order by "+COL_UID_S_BcData +" , " + COL_UID_R_Slab + "  asc limit 1");
		//") order by "+COL_UID_S_BcData +" asc limit 1");
		//qParam.setSelectionArgs(new String []{custId});
		return qParam;
	}
	//punit end

	//Tamilselvan on 03-03-2014
	public static QueryParameters GetConnectionCount(String ConNo)
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionNo+") as [COUNT] FROM "+TABLE_SBM_BillCollDataMain+" WHERE " +COL_ConnectionNo +" = ?");
		qParam.setSelectionArgs(new String []{ConNo});
		return qParam;
	}
	//Tamilselvan on 04-03-2014
	public static QueryParameters DeleteRows()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("DELETE FROM "+TABLE_SBM_BillCollDataMain);
		qParam.setSelectionArgs(null);
		return qParam;
	}
	//Tamilselvan on 04-03-2014
	public static QueryParameters GetCountforNotBilledConnection()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionNo+") as [COUNT] FROM "+TABLE_SBM_BillCollDataMain+" WHERE " +COL_BlCnt+" = 0");
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//Tamilselvan on 02-04-2014
	public static QueryParameters GetCountforBilledConnection()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionNo+") as [COUNT] FROM "+TABLE_SBM_BillCollDataMain+" WHERE " +COL_BlCnt+" = 1 ");
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//Tamilselvan on 02-04-2014
	public static QueryParameters GetMaxSpotSerialNumber()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT MAX("+COL_SpotSerialNo+") as [SpotSerialNo] FROM "+TABLE_SBM_BillCollDataMain+" WHERE " +COL_BlCnt+" = 1 ");
		qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//Tamilselvan on 04-03-2014
	public static QueryParameters GetCountBillCollDataMain()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionNo+") as [COUNT] FROM "+TABLE_SBM_BillCollDataMain);
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}

	/**
	 * Tamilselvan on 19-04-2014
	 * Get GPRS Sent Count  
	 * @return 
	 */
	public static QueryParameters GetCountGPRSSentConnection()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionNo+") as [COUNT] FROM "+TABLE_SBM_BillCollDataMain+" WHERE " +COL_Gprs_Flag+" = 1 ");
		qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//Modified 30-07-2015
	public static QueryParameters GetDataToWriteFile()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT "+COL_ForMonth+", "+COL_BillDate+", "+COL_SubId+", "+COL_ConnectionNo+", "+COL_TourplanId+", "+
				COL_PF+", "+COL_SancLoad+", "+COL_DlCount+", "+COL_BillFor+", "+COL_BlCnt+", "+COL_TvmMtr+", "+COL_PreRead+", "+
				COL_PreStatus+", "+COL_SpotSerialNo+", "+COL_Units+", "+COL_TFc+", "+COL_TEc+", "+COL_FLReb+", "+COL_ECReb+", "+COL_TaxAmt+", "+
				COL_PfPenAmt+", "+COL_PenExLd+", "+COL_HCReb+", "+COL_HLReb+", "+COL_CapReb+", "+COL_ExLoad+", "+COL_DemandChrg+", "+
				COL_AccdRdg_rtn+", "+COL_KVAFR+", "+COL_AbFlag+", "+COL_BjKj2Lt2+", "+COL_Remarks+", "+COL_GoKPayable+", "+
				COL_IssueDateTime+", "+COL_RecordDmnd+", "+COL_KVA_Consumption+", "+COL_KVAAssd_Cons+", "+COL_TariffCode+", "+
				COL_LinMin+", "+COL_PrevRdg+", "+COL_RRNo+", "+COL_BillTotal+", "+COL_SBMNumber+", "+COL_LocationCode+", "+COL_EC_1+", "+
				COL_EC_2+", "+COL_EC_3+", "+COL_EC_4+" , "+COL_MobileNo+" , "+COL_MtrDisFlag+", "+COL_Meter_type+", "+COL_Meter_serialno+ ", "+
				COL_Gps_Latitude_image + ", " + COL_Gps_Longitude_image + ", "  + COL_METER_PRESENT_FLAG + ", " + COL_MTR_NOT_VISIBLE +	" , " +
				COL_AadharNo + " , " +COL_VoterIdNo + " , " + COL_MtrMakeFlag + "," + COL_MtrBodySealFlag + " , " + COL_MtrTerminalCoverFlag + " , " + COL_MtrTerminalCoverSealedFlag + 
				" FROM "+TABLE_SBM_BillCollDataMain+
				" LEFT JOIN "+TABLE_EC_FC_Slab+" on trim("+COL_ConnectionNo_Slab+") = trim("+ COL_ConnectionNo +")"
				+" WHERE "+ COL_BlCnt +" = 1"
				);
		//qParam.setSelectionArgs(new String []{""});
		return qParam; 
	}
	//Modified 30-07-2015
	public static QueryParameters GetDataSentToServer()//
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT "+COL_ForMonth+", "+COL_BillDate+", "+COL_SubId+", "+COL_ConnectionNo+", "+COL_TourplanId+", "+
				COL_PF+", "+COL_SancLoad+", "+COL_DlCount+", "+COL_BillFor+", "+COL_BlCnt+", "+COL_TvmMtr+", "+COL_PreRead+", "+
				COL_PreStatus+", "+COL_SpotSerialNo+", "+COL_Units+", "+COL_TFc+", "+COL_TEc+", "+COL_FLReb+", "+COL_ECReb+", "+COL_TaxAmt+", "+
				COL_PfPenAmt+", "+COL_PenExLd+", "+COL_HCReb+", "+COL_HLReb+", "+COL_CapReb+", "+COL_ExLoad+", "+COL_DemandChrg+", "+
				COL_AccdRdg_rtn+", "+COL_KVAFR+", "+COL_AbFlag+", "+COL_BjKj2Lt2+", "+COL_Remarks+", "+COL_GoKPayable+", "+
				COL_IssueDateTime+", "+COL_RecordDmnd+", "+COL_KVA_Consumption+", "+COL_KVAAssd_Cons+", "+COL_TariffCode+", "+
				COL_LinMin+", "+COL_PrevRdg+", "+COL_RRNo+", "+COL_BillTotal+", "+COL_SBMNumber+", "+COL_LocationCode+", "+COL_EC_1+", "+
				COL_EC_2+", "+COL_EC_3+", "+COL_EC_4+", "+COL_MobileNo+", "+COL_MtrDisFlag+", "+COL_Meter_type+", "+COL_Meter_serialno + ", "+
				COL_Gps_Latitude_image + ", " + COL_Gps_Longitude_image + ", " + COL_METER_PRESENT_FLAG + ", " + COL_MTR_NOT_VISIBLE +" , " +
				COL_AadharNo + " , " +COL_VoterIdNo + " , " + COL_MtrMakeFlag + "," + COL_MtrBodySealFlag + " , " + COL_MtrTerminalCoverFlag + " , " + COL_MtrTerminalCoverSealedFlag +
				" FROM "+TABLE_SBM_BillCollDataMain+
				" LEFT JOIN "+TABLE_EC_FC_Slab+" on trim("+COL_ConnectionNo_Slab+") = trim("+ COL_ConnectionNo +") WHERE IFNULL("+
				COL_Gprs_Flag+", 0) = 0 AND "+ COL_BlCnt + " = 1 AND "+ COL_BOBillFlag +" = 0  limit 30");
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//Tamilselvan on 14-03-2014
	public static QueryParameters GetTariffSlab()//
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT "+COL_TarifString+" FROM "+TABLE_ReadSlabNTariff);
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//Tamilselvan on 15-03-2014
	public static QueryParameters GetReprintCount()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT "+COL_Count+" FROM "+TABLE_Reprint_Countf);
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//Tamilselvan on 17-03-2014
	public static QueryParameters GetNextConnectionDetails(String Uid)
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT "+COL_UID_S_BcData+", "+COL_ConnectionNo+" FROM "+ TABLE_SBM_BillCollDataMain + " WHERE "+
				COL_UID_S_BcData + " > ? AND "+ COL_BlCnt + " = 0 limit 1");
		qParam.setSelectionArgs(new String []{Uid});
		return qParam;
	}
	//Tamilselvan on 18-03-2014
	public static QueryParameters GetStatusMasterDetailsByID(String StatusId)
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT "+COL_STATUSID_STATUSMASTER+", "+COL_STATUSDESC_STATUSMASTER+
				", IFNULL("+ COL_STATUS_STATUSMASTER +", 0) as [Status], "+ COL_VALUE_STATUSMASTER +
				" FROM "+ TABLE_STATUSMASTER + " WHERE "+COL_STATUSID_STATUSMASTER + " in ( ? )");
		qParam.setSelectionArgs(new String []{StatusId});
		return qParam;
	}

	//Tamilselvan on 18-03-2014
	public static QueryParameters GetStatusMasterDetailsByIDCheck(String StatusId)
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT "+COL_STATUSID_STATUSMASTER+", "+COL_STATUSDESC_STATUSMASTER+
				", "+ COL_STATUS_STATUSMASTER +" as [Status], "+ COL_VALUE_STATUSMASTER +
				" FROM "+ TABLE_STATUSMASTER + " WHERE "+COL_STATUSID_STATUSMASTER + " = ? ");
		qParam.setSelectionArgs(new String []{StatusId});
		return qParam;
	}

	//Tamilselvan on 18-03-2014
	public static QueryParameters GetStatusMasterCountByID(String StatusId)
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_STATUSID_STATUSMASTER+") as [COUNT] "+
				" FROM "+ TABLE_STATUSMASTER + " WHERE "+COL_STATUSID_STATUSMASTER + " = ? ");
		qParam.setSelectionArgs(new String []{StatusId});
		return qParam;
	}
	//Tamilselvan on 02-04-2014
	public static QueryParameters CheckUIDExists(int uID)
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_UID_S_BcData+") as [COUNT] "+" FROM "+ 
				TABLE_SBM_BillCollDataMain + " WHERE "+COL_UID_S_BcData + " = ? ");
		qParam.setSelectionArgs(new String []{String.valueOf(uID)});
		return qParam;
	}

	public static QueryParameters GetPhotoNameByConnectionNo(String ConnectionNo)
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_Image_Name + " as [PhotoName] "+" FROM "+ 
				TABLE_SBM_BillCollDataMain + " WHERE "+ COL_BlCnt + " = 1 AND " + COL_ConnectionNo + " = ?");
		qParam.setSelectionArgs(new String []{String.valueOf(ConnectionNo)});
		return qParam;
	}
	//Tamilselvan on 05-05-2014
	/**
	 * 
	 * @return
	 */
	public static QueryParameters GetCollDataWriteToFile()
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("SELECT "+COL_ConnectionNo_COLLECTION_TABLE+", "+COL_RRNo_COLLECTION_TABLE+", "+COL_RcptCnt_COLLECTION_TABLE+", "+COL_Batch_No_COLLECTION_TABLE+", "+COL_Receipt_No_COLLECTION_TABLE+", "+
				COL_DateTime_COLLECTION_TABLE+", "+COL_Payment_Mode_COLLECTION_TABLE+", "+COL_Paid_Amt_COLLECTION_TABLE+", "+COL_BankID_COLLECTION_TABLE+", "+COL_ChequeDDNo_COLLECTION_TABLE+", "+COL_ChequeDDDate_COLLECTION_TABLE+", "+COL_GvpId_COLLECTION_TABLE+", "+
				COL_Receipttypeflag_COLLECTION_TABLE + ", " +COL_SBMNumber_COLLECTION_TABLE+", "+COL_LocationCode_COLLECTION_TABLE+				
				" FROM "+TABLE_COLLECTION_TABLE+
				"  WHERE "+ COL_RcptCnt_COLLECTION_TABLE + " = 1");
		return qParam;	
	}

	//Added on 30-08-2014
	public static QueryParameters getMeterType()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_METERTYPEMASTER_METERTYPEID+", "+ COL_METERTYPEMASTER_METERTYPE + " FROM " +
				TABLE_METERTYPEMASTER );
		return qParam;
	}

	//Added on 30-08-2014
	public static QueryParameters getMeterPlacement()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_METERPLACEMENTMASTER_UNIQUEID+", "+ COL_METERPLACEMENTMASTER_METERPLACEMENT + " FROM " +
				TABLE_METERPLACEMENTMASTER );
		return qParam;
	}

	//Added on 30-08-2014
	public static QueryParameters getMeterCondition()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_METERCONDITION_UNIQUEID +", "+ COL_METERCONDITION_METERCONDITION + " FROM " +
				TABLE_METERCONDITION );
		return qParam;
	}

	//Added on 30-08-2014
	public static QueryParameters getBatteryStatus()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_BATTERYSTATUS_STATUSID+", "+ COL_BATTERYSTATUS_STATUS + " FROM " +
				TABLE_BATTERYSTATUS );
		return qParam;
	}

	//Nitish 02-10-2014
	public static QueryParameters GetReportTPFeeder(String issueDate)
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_ConnectionNo+", "+ COL_PreRead + ", "+ COL_PreStatus  +" FROM " +
				TABLE_SBM_BillCollDataMain + " where substr(trim("+COL_IssueDateTime + "),1,6) = ? ");
		qParam.setSelectionArgs(new String []{String.valueOf(issueDate)});
		return qParam;		
	}
	//Nitish 30-06-2015
	public static QueryParameters GetDataSendToServerFaceRecognition()
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("SELECT "+COL_Id_TABLE_FaceRecognition+", "+COL_IMEINumber_TABLE_FaceRecognition+", "+COL_SimNumber_TABLE_FaceRecognition+", "+
				COL_Similarity_TABLE_FaceRecognition+", "+COL_GprsFlag_TABLE_FaceRecognition+", "+COL_CreatedDate_TABLE_FaceRecognition+ ", "+COL_PhotoName_TABLE_FaceRecognition+							
				" FROM "+TABLE_FaceRecognition+	"  WHERE IFNULL("+ COL_GprsFlag_TABLE_FaceRecognition +", 0) = 0  limit 30");
		return qParam;	
	}
	//Nitish 25-07-2015
	public static QueryParameters GetCountBillCollDataMainWithoutTC()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionNo+") as [COUNT] FROM "+TABLE_SBM_BillCollDataMain + " where trim( " + COL_TariffCode + ") <>  '' ");
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//Nitish 25-07-2015
	public static QueryParameters GetCountforBilledBOBConnection()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionNo+") as [COUNT] FROM "+TABLE_SBM_BillCollDataMain+ " where " + COL_BlCnt + " = 1 OR "+ COL_BOBillFlag+"= 1  ");
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	//15-07-2016 Get Billing Details
	public static QueryParameters GetBillingDetails()
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql("Select "+ COL_IMEINo_Billing_Details + ", " + COL_SIMNo_Billing_Details + ", "+ COL_BatchDate_Billing_Details + " , " + COL_LocationCode_Billing_Details + " , " +
				COL_StartTime_Billing_Details + " , " + COL_EndTime_Billing_Details +  " , " + COL_BillingOpen_Billing_Details + " , ifnull(" + COL_ExtensionFlag_Billing_Details + ",0) as [ExtFlag] " +
				" from "+ TABLE_Billing_Details + " order by " +  COL_UID_Billing_Details + " desc limit 1"); 
		return qParam;	
	}

	//////////////////////////Event Log////////////////////////////		
	public static QueryParameters getEventlogStatus()
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("SELECT " + COL_UID_TABLE_EventLog + ", " + COL_IMEINO_TABLE_EventLog + ", " + COL_SIMNO_TABLE_EventLog 
				+ ", " + COL_Flag_TABLE_EventLog +  ", " + COL_Description_TABLE_EventLog + ", " + COL_DateTime_TABLE_EventLog 
				+ ", " + COL_GPRSFlag_TABLE_EventLog + ", " + COL_GPRSStatus_TABLE_EventLog +
				" FROM " + TABLE_EventLog + " WHERE IFNULL ( " + COL_GPRSFlag_TABLE_EventLog + ", 0) = 0  limit 30");
		return qParam;
	}
	/////////////////////////////Event Log End////////////////////////////
	////////////////////////////////////////////29-08-2016 TariffWise Report End///////////////////////////
	public static QueryParameters getTariffreport(String readingday)
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("SELECT " + COL_TariffCode + ", " + " count(" + COL_ConnectionNo + ") as [NoOfConsumers] ,  Sum(Round(" + COL_Units + ",0)) as [Units]  ,  Sum(Round(" +COL_BillTotal + ",0)) as [Billtotal] " 
				+" FROM " + TABLE_SBM_BillCollDataMain +" WHERE " + COL_BlCnt + " =1 and " + COL_ReadingDay + " =? group by "+ COL_TariffCode);
		qParam.setSelectionArgs(new String[]{readingday});
		return qParam;
	}

	/*SELECT distinct(ReadingDay)  FROM SBM_BillCollDataMain where length(trim(ReadingDay)) !=0*/

	public static QueryParameters getTariffDaywise()
	{

		QueryParameters qParam=new QueryParameters();
		qParam.setSql("SELECT distinct( "  + COL_ReadingDay  +  ")  FROM " + TABLE_SBM_BillCollDataMain +  " WHERE " + " Length(trim( " +COL_ReadingDay+" )) !=0" );
		return qParam;
	}

	public static QueryParameters getTariffString(String Tarifcode)
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql("SELECT "  + COL_TarifString  +  " FROM " + TABLE_ReadSlabNTariff + " WHERE " + COL_TarifCode + " = ?  limit 1");
		qParam.setSelectionArgs(new String[]{Tarifcode});
		return qParam;
	}

	////////////////////////////////////////////29-08-2016 TariffWise Report End///////////////////////////

	////////////////////////////////////////////Survey///////////////////////////
	public static QueryParameters GetSurveyDataSendToServer()//
	{


		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_RRNo_TABLE_HescomSurvey + " , " + COL_ConnectionId_TABLE_HescomSurvey+ " , " + COL_ConnectionType_TABLE_HescomSurvey + " , " + COL_Phase_TABLE_HescomSurvey +" , " + COL_MeterMake_TABLE_HescomSurvey + " , " +
				COL_Model_TABLE_HescomSurvey+" , "+COL_MeterType_TABLE_HescomSurvey+" , "+COL_MeterBoxAvailability_TABLE_HescomSurvey+" , "+COL_TypeOfBox_TABLE_HescomSurvey+" , "+COL_MeterPlacement_TABLE_HescomSurvey+" , "+COL_HeightOfMeter_TABLE_HescomSurvey+", "+COL_AvailabilityOfLineOfSiht_TABLE_HescomSurvey+", "+
				COL_floor_TABLE_HescomSurvey+", "+COL_NoOfMeterAvailable_TABLE_HescomSurvey+", "+COL_MeterDiemension_TABLE_HescomSurvey+", "+COL_Remarks_TABLE_HescomSurvey+", "+COL_Lattitude_TABLE_HescomSurvey+", "+COL_Longitude_TABLE_HescomSurvey+", "+COL_DateTime_TABLE_HescomSurvey + " , " + COL_ImagePath_TABLE_HescomSurvey + " , " + COL_SlaveId_TABLE_HescomSurvey + " ," + 
				COL_ComRJ11_TABLE_HescomSurvey+", "+COL_ComOptical_TABLE_HescomSurvey+", "+COL_Protocol_TABLE_HescomSurvey+", "+COL_OpticalReading_TABLE_HescomSurvey+ " ," + 	COL_TransFormerName_TABLE_HescomSurvey + " , " + COL_YearofManufacture_TABLE_HescomSurvey + " , " + COL_MeterSlNo_TABLE_HescomSurvey +
				" FROM " + TABLE_HescomSurvey +
				" WHERE IFNULL("+ COL_GPSFlag_TABLE_HescomSurvey +", 0) = 0 " +" limit 30");
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}

	public static QueryParameters GetCountSurveyDone()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionId_TABLE_HescomSurvey+") as [COUNT] FROM "+TABLE_HescomSurvey);
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}

	public static QueryParameters GetCountSurveyGPRSSent()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionId_TABLE_HescomSurvey+") as [COUNT] FROM "+TABLE_HescomSurvey +" WHERE " +COL_GPSFlag_TABLE_HescomSurvey+" = 1 ");
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}
	public static QueryParameters GetSurveyPhotoNameByConnectionNo(String ConnectionNo)
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_ImagePath_TABLE_HescomSurvey + " as [PhotoName] "+" FROM "+ 
				TABLE_HescomSurvey + " WHERE " + COL_ConnectionId_TABLE_HescomSurvey + " = ?");
		qParam.setSelectionArgs(new String []{String.valueOf(ConnectionNo)});
		return qParam;
	}
	//DCU Master Details

	public static QueryParameters getDcuMaster()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_DCUID_TABLE_DCUMaster + " , " + COL_DCUName_TABLE_DCUMaster + " FROM " + TABLE_DCUMaster );
		//qParam.setSelectionArgs(new String []{String.valueOf(dcuflagWise)});
		return qParam;
	} 
	public static QueryParameters getslaveMaster(String dcuId)
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_SlaveID_TABLE_SlaveMaster + " , " + COL_SlaveName_TABLE_SlaveMaster +" FROM "+ 
				TABLE_SlaveMaster + " WHERE " + COL_DCUID_TABLE_SlaveMaster + " = ?");
		qParam.setSelectionArgs(new String []{String.valueOf(dcuId)});
		return qParam;
	} 

	public static QueryParameters getDCUSlaveMapData()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_Flag_TABLE_DCUSlaveMapData + " , " + COL_Latitude_TABLE_DCUSlaveMapData + " , "
				+ COL_Longitude_TABLE_DCUSlaveMapData + " , " + COL_Name_TABLE_DCUSlaveMapData + " , " + COL_Remarks_TABLE_DCUSlaveMapData + " FROM " + TABLE_DCUSlaveMapData);
		//qParam.setSelectionArgs(new String []{String.valueOf(dcuId)});
		return qParam;
	}

	public static QueryParameters getTransformerName()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_ID_TABLE_TransformerMaster+", "+ COL_Name_TABLE_TransformerMaster + " FROM " +
				TABLE_TransformerMaster );
		return qParam;
	}	
	/////////////////////////////////Survey End/////////////////////////////

	//16-07-2021
	public static QueryParameters GetReportSurveyData()	
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_RRNo_TABLE_HescomSurvey + " , " + COL_ConnectionId_TABLE_HescomSurvey+ " , " + COL_ConnectionType_TABLE_HescomSurvey + " , " + COL_Phase_TABLE_HescomSurvey +" , " + COL_MeterMake_TABLE_HescomSurvey + " , " +
				COL_Model_TABLE_HescomSurvey+" , "+COL_MeterType_TABLE_HescomSurvey+" , "+COL_MeterBoxAvailability_TABLE_HescomSurvey+" , "+COL_TypeOfBox_TABLE_HescomSurvey+" , "+COL_MeterPlacement_TABLE_HescomSurvey+" , "+COL_HeightOfMeter_TABLE_HescomSurvey+", "+COL_AvailabilityOfLineOfSiht_TABLE_HescomSurvey+", "+
				COL_floor_TABLE_HescomSurvey+", "+COL_NoOfMeterAvailable_TABLE_HescomSurvey+", "+COL_MeterDiemension_TABLE_HescomSurvey+", "+COL_Remarks_TABLE_HescomSurvey+", "+COL_Lattitude_TABLE_HescomSurvey+", "+COL_Longitude_TABLE_HescomSurvey+", "+COL_DateTime_TABLE_HescomSurvey + " , " + COL_ImagePath_TABLE_HescomSurvey + " , " + COL_SlaveId_TABLE_HescomSurvey + " ," + 
				COL_ComRJ11_TABLE_HescomSurvey+", "+COL_ComOptical_TABLE_HescomSurvey+", "+COL_Protocol_TABLE_HescomSurvey+", "+COL_OpticalReading_TABLE_HescomSurvey+ " ," + 	COL_TransFormerName_TABLE_HescomSurvey + " , " + COL_YearofManufacture_TABLE_HescomSurvey + " , " + COL_MeterSlNo_TABLE_HescomSurvey +
				" FROM " + TABLE_HescomSurvey);

		return qParam;
	}
	public static QueryParameters GetTCReport()
	{
		QueryParameters qParam=new QueryParameters();
		qParam.setSql(" select T.Name as TransformerName, count (H.ConnectionId) as ConnectionCount " +
				" from HescomSurvey H inner join TransformerMaster T on T.Id = H.TransformerName  group by H.TransformerName ");
		return qParam;
	}


	//Modified Nitish on 12-06-2014
	public static QueryParameters GetMaxSurveyUID()
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql(" Select  max(UID) as UID FROM  HescomSurvey ") ;					
		return qParam;

	}



	///////////////////////////Water Survey   21-10-2021
	public static QueryParameters GetWaterSurveyDataSendToServer()//
	{


		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_RRNo_TABLE_GPWaterSurvey + " , " + COL_ConnectionId_TABLE_GPWaterSurvey+ " , " + COL_CustomerName_TABLE_GPWaterSurvey + " , " + COL_MeterMake_TABLE_GPWaterSurvey + " , " +
				COL_Remarks_TABLE_GPWaterSurvey+" , "+COL_Lattitude_TABLE_GPWaterSurvey+" , "+COL_Longitude_TABLE_GPWaterSurvey+" , "+COL_GPSFlag_TABLE_GPWaterSurvey+" , "+COL_ImagePath_TABLE_GPWaterSurvey+" , "+COL_DateTime_TABLE_GPWaterSurvey+", "+COL_MeterSlNo_TABLE_GPWaterSurvey+", "+ COL_YearofManufacture_TABLE_GPWaterSurvey + "," +
				COL_DistrictId_TABLE_GPWaterSurvey +"," + COL_TalukId_TABLE_GPWaterSurvey + "," +COL_GPId_TABLE_GPWaterSurvey + "," +COL_VillageId_TABLE_GPWaterSurvey + "," +COL_TankId_TABLE_GPWaterSurvey +","+ COL_MeterStatus_TABLE_GPWaterSurvey + "," + COL_WaterSource_TABLE_GPWaterSurvey + "," + COL_Borewell_TABLE_GPWaterSurvey + "," + COL_ConnectionStatus_TABLE_GPWaterSurvey + "," +
				COL_SancLoad_TABLE_GPWaterSurvey  +"," +COL_PumpCapacity_TABLE_GPWaterSurvey + "," +COL_Depth_TABLE_GPWaterSurvey +"," +COL_PipeDimension_TABLE_GPWaterSurvey + ","+COL_TypeOfSupply_TABLE_GPWaterSurvey + ","+COL_NoOfOutlets_TABLE_GPWaterSurvey +"," +COL_TankDimensionsLength_TABLE_GPWaterSurvey + "," +COL_TankDimensionsDiameter_TABLE_GPWaterSurvey +"," +COL_TankCapacity_TABLE_GPWaterSurvey + "," +
				COL_Distance_TABLE_GPWaterSurvey+ ", "+ COL_NSP_TABLE_GPWaterSurvey +", "+
				COL_MeterPhase_TABLE_GPWaterSurvey  +"," +COL_MeterType_TABLE_GPWaterSurvey + "," +COL_PipeTypeBorewell_TABLE_GPWaterSurvey +"," +COL_TankId_TABLE_GPWaterSurvey + "," +COL_ControlValve_TABLE_GPWaterSurvey + ","+COL_PipeTypeOutlet_TABLE_GPWaterSurvey +"," +COL_TypeOfSim_TABLE_GPWaterSurvey +"," +COL_WaterManName_TABLE_GPWaterSurvey + "," +COL_ContactNo_TABLE_GPWaterSurvey+ "," +

                COL_ImageName1_TABLE_GPWaterSurvey  +"," +COL_ImageName2_TABLE_GPWaterSurvey + "," +COL_ImageName3_TABLE_GPWaterSurvey  + "," +COL_ImageName4_TABLE_GPWaterSurvey + "," +

				COL_Param1_TABLE_GPWaterSurvey+", "+COL_Param2_TABLE_GPWaterSurvey+", "+COL_Param3_TABLE_GPWaterSurvey+", "+COL_Param4_TABLE_GPWaterSurvey+", "+COL_Param5_TABLE_GPWaterSurvey+", "+ 
				COL_Param6_TABLE_GPWaterSurvey+", "+COL_Param7_TABLE_GPWaterSurvey+", "+COL_Param8_TABLE_GPWaterSurvey+", "+COL_Param9_TABLE_GPWaterSurvey+", "+COL_Param10_TABLE_GPWaterSurvey+
				" FROM " + TABLE_GPWaterSurvey +
				" WHERE IFNULL("+ COL_GPSFlag_TABLE_GPWaterSurvey +", 0) = 0 " +" limit 30");
		//qParam.setSelectionArgs(new String []{""});
		return qParam;


	}


	public static QueryParameters GetMaxWaterSurveyUID()
	{
		QueryParameters qParam = new QueryParameters();			
		qParam.setSql(" Select  max(UID) as UID FROM  GPWaterSurvey ") ;					
		return qParam;

	}

	public static QueryParameters GetCountWaterSurveyDone()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionId_TABLE_GPWaterSurvey+") as [COUNT] FROM "+TABLE_GPWaterSurvey);
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}

	public static QueryParameters GetCountWaterSurveyGPRSSent()
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT COUNT("+COL_ConnectionId_TABLE_GPWaterSurvey+") as [COUNT] FROM "+TABLE_GPWaterSurvey +" WHERE " +COL_GPSFlag_TABLE_GPWaterSurvey+" = 1 ");
		//qParam.setSelectionArgs(new String []{""});
		return qParam;
	}




	public static QueryParameters GetReportWaterSurveyData()	
	{
		QueryParameters qParam = new QueryParameters();
		qParam.setSql("SELECT " + COL_RRNo_TABLE_GPWaterSurvey + " , " + COL_ConnectionId_TABLE_GPWaterSurvey+ " , " + COL_CustomerName_TABLE_GPWaterSurvey + " , " + COL_MeterMake_TABLE_GPWaterSurvey +" , "  +
				COL_Remarks_TABLE_GPWaterSurvey+" , "+COL_Lattitude_TABLE_GPWaterSurvey+" , "+COL_Longitude_TABLE_GPWaterSurvey+" , "+COL_GPSFlag_TABLE_GPWaterSurvey+" , "+COL_ImagePath_TABLE_GPWaterSurvey+" , "+COL_DateTime_TABLE_GPWaterSurvey+", "+COL_MeterSlNo_TABLE_GPWaterSurvey+", "+ COL_YearofManufacture_TABLE_GPWaterSurvey + "," +
				COL_DistrictId_TABLE_GPWaterSurvey +"," + COL_TalukId_TABLE_GPWaterSurvey + "," +COL_GPId_TABLE_GPWaterSurvey + "," +COL_VillageId_TABLE_GPWaterSurvey + "," + COL_TankId_TABLE_GPWaterSurvey + ","+ COL_MeterStatus_TABLE_GPWaterSurvey + "," + COL_WaterSource_TABLE_GPWaterSurvey + "," + COL_Borewell_TABLE_GPWaterSurvey + "," + COL_ConnectionStatus_TABLE_GPWaterSurvey + "," +
				COL_SancLoad_TABLE_GPWaterSurvey  +"," +COL_PumpCapacity_TABLE_GPWaterSurvey + "," +COL_Depth_TABLE_GPWaterSurvey +"," +COL_PipeDimension_TABLE_GPWaterSurvey  + "," +COL_TypeOfSupply_TABLE_GPWaterSurvey + ","+COL_NoOfOutlets_TABLE_GPWaterSurvey +"," +COL_TankDimensionsLength_TABLE_GPWaterSurvey + "," +COL_TankDimensionsDiameter_TABLE_GPWaterSurvey +"," +COL_TankCapacity_TABLE_GPWaterSurvey + "," +
				COL_Distance_TABLE_GPWaterSurvey+ ", "+ COL_NSP_TABLE_GPWaterSurvey + ", " +
				COL_MeterPhase_TABLE_GPWaterSurvey  +"," +COL_MeterType_TABLE_GPWaterSurvey + "," +COL_PipeTypeBorewell_TABLE_GPWaterSurvey  + "," +COL_ControlValve_TABLE_GPWaterSurvey + ","+COL_PipeTypeOutlet_TABLE_GPWaterSurvey +"," +COL_TypeOfSim_TABLE_GPWaterSurvey +"," +COL_WaterManName_TABLE_GPWaterSurvey + "," +COL_ContactNo_TABLE_GPWaterSurvey+ "," +
				COL_Param1_TABLE_GPWaterSurvey+", "+COL_Param2_TABLE_GPWaterSurvey+", "+COL_Param3_TABLE_GPWaterSurvey+", "+COL_Param4_TABLE_GPWaterSurvey+", "+COL_Param5_TABLE_GPWaterSurvey+", "+ 
				COL_Param6_TABLE_GPWaterSurvey+", "+COL_Param7_TABLE_GPWaterSurvey+", "+COL_Param8_TABLE_GPWaterSurvey+", "+COL_Param9_TABLE_GPWaterSurvey+", "+COL_Param10_TABLE_GPWaterSurvey+
				" FROM " + TABLE_GPWaterSurvey );

		return qParam;
	}

	public static QueryParameters getMasterData(int flag,String id)
	{	
		QueryParameters qParam=new QueryParameters();
		if(id.equals("0"))
		{
			if(flag == 1)
				qParam.setSql(" select " + COL_Id_DistrictMaster + " as [Id] , " + COL_Name_DistrictMaster  + " as [Name] , " + COL_Desc_DistrictMaster +" as [Desc] from " + TABLE_DistrictMaster );
			else if(flag == 2)
				qParam.setSql(" select " + COL_Id_TalukMaster + " as [Id] , " + COL_Name_TalukMaster  + " as [Name] , " + COL_Desc_TalukMaster +" as [Desc] from " + TABLE_TalukMaster  );
			else if(flag == 3)
				qParam.setSql(" select " + COL_Id_GramPanchayatMaster + " as [Id] , " + COL_Name_GramPanchayatMaster  + " as [Name] , " + COL_Desc_GramPanchayatMaster +" as [Desc]  from " + TABLE_GramPanchayatMaster );
			else if(flag == 4)
				qParam.setSql(" select " + COL_Id_VillageMaster + " as [Id] , " + COL_Name_VillageMaster  + " as [Name] , " + COL_Desc_VillageMaster +" as [Desc]  from " + TABLE_VillageMaster );
			else if(flag == 5)
				qParam.setSql(" select " + COL_Id_TankMaster + " as [Id] , " + COL_Name_TankMaster  + " as [Name] , " + COL_Desc_TankMaster +" as [Desc]  from " + TABLE_TankMaster );
		}
		else
		{
			if(flag == 1)
				qParam.setSql(" select " + COL_Id_DistrictMaster + " as [Id] , " + COL_Name_DistrictMaster  + " as [Name] , " + COL_Desc_DistrictMaster +" as [Desc] from " + TABLE_DistrictMaster + " Where Desc <> ? " );
			else if(flag == 2)
				qParam.setSql(" select " + COL_Id_TalukMaster + " as [Id] , " + COL_Name_TalukMaster  + " as [Name] , " + COL_Desc_TalukMaster +" as [Desc] from " + TABLE_TalukMaster + " Where Desc = ?" );
			else if(flag == 3)
				qParam.setSql(" select " + COL_Id_GramPanchayatMaster + " as [Id] , " + COL_Name_GramPanchayatMaster  + " as [Name] , " + COL_Desc_GramPanchayatMaster +" as [Desc]  from " + TABLE_GramPanchayatMaster + " Where Desc  = ?" );
			else if(flag == 4)
				qParam.setSql(" select " + COL_Id_VillageMaster + " as [Id] , " + COL_Name_VillageMaster  + " as [Name] , " + COL_Desc_VillageMaster +" as [Desc]  from " + TABLE_VillageMaster + " Where Desc  = ?" );
			else if(flag == 5)
				qParam.setSql(" select " + COL_Id_TankMaster + " as [Id] , " + COL_Name_TankMaster  + " as [Name] , " + COL_Desc_TankMaster +" as [Desc]  from " + TABLE_TankMaster + " Where Desc  = ?" );
			qParam.setSelectionArgs(new String []{id});
		}
		return qParam;
	}
	public static QueryParameters getMasterName(int flag,String id)
	{	
		QueryParameters qParam=new QueryParameters();
		if(flag == 1)
			qParam.setSql(" select " + COL_Id_DistrictMaster + " as [Id] , " + COL_Name_DistrictMaster  + " as [Name] , " + COL_Desc_DistrictMaster +" as [Desc] from " + TABLE_DistrictMaster + " Where Id = ? " );
		else if(flag == 2)
			qParam.setSql(" select " + COL_Id_TalukMaster + " as [Id] , " + COL_Name_TalukMaster  + " as [Name] , " + COL_Desc_TalukMaster +" as [Desc] from " + TABLE_TalukMaster + " Where Id = ?" );
		else if(flag == 3)
			qParam.setSql(" select " + COL_Id_GramPanchayatMaster + " as [Id] , " + COL_Name_GramPanchayatMaster  + " as [Name] , " + COL_Desc_GramPanchayatMaster +" as [Desc]  from " + TABLE_GramPanchayatMaster + " Where Id  = ?" );
		else if(flag == 4)
			qParam.setSql(" select " + COL_Id_VillageMaster + " as [Id] , " + COL_Name_VillageMaster  + " as [Name] , " + COL_Desc_VillageMaster +" as [Desc]  from " + TABLE_VillageMaster + " Where Id  = ?" );
		else if(flag == 5)
			qParam.setSql(" select " + COL_Id_TankMaster + " as [Id] , " + COL_Name_TankMaster  + " as [Name] , " + COL_Desc_TankMaster +" as [Desc]  from " + TABLE_TankMaster + " Where Id  = ?" );
		qParam.setSelectionArgs(new String []{id});

		return qParam;
	}
}
