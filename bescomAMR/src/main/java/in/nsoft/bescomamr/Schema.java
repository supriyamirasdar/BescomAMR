package in.nsoft.bescomamr;

public interface Schema {
	//punit_gupta 11022014 START

	//schema for EC_FC_Slab Table //needed by Billing logic
	public static final String TABLE_EC_FC_Slab="EC_FC_Slab";
	public static final String COL_UID_Slab="EC_FC_Slab.UID";//Integer PRIMARY KEY
	public static final String COL_ConnectionNo_Slab="EC_FC_Slab.ConnectionNo"; //TEXT
	public static final String COL_RRNo_Slab="EC_FC_Slab.RRNo";  //TEXT
	public static final String COL_ECRate_Count="EC_FC_Slab.ECRate_Count";//Integer
	public static final String COL_ECRate_Row="EC_FC_Slab.ECRate_Row"; //INTEGER
	public static final String COL_FCRate_1="EC_FC_Slab.FCRate_1";//REAL 
	public static final String COL_FCRate_2="EC_FC_Slab.FCRate_2"; //REAL
	public static final String COL_Units_1="EC_FC_Slab.Units_1";//REAL
	public static final String COL_Units_2="EC_FC_Slab.Units_2";//REAL
	public static final String COL_Units_3="EC_FC_Slab.Units_3";//REAL
	public static final String COL_Units_4="EC_FC_Slab.Units_4";//REAL
	public static final String COL_Units_5="EC_FC_Slab.Units_5";//REAL
	public static final String COL_Units_6="EC_FC_Slab.Units_6";//REAL
	public static final String COL_EC_Rate_1="EC_FC_Slab.EC_Rate_1";//REAL-->ec rate 
	public static final String COL_EC_Rate_2="EC_FC_Slab.EC_Rate_2";//REAL
	public static final String COL_EC_Rate_3="EC_FC_Slab.EC_Rate_3";//REAl
	public static final String COL_EC_Rate_4="EC_FC_Slab.EC_Rate_4";//REAL
	public static final String COL_EC_Rate_5="EC_FC_Slab.EC_Rate_5";//REAL
	public static final String COL_EC_Rate_6="EC_FC_Slab.EC_Rate_6";//REAL
	public static final String COL_EC_1="EC_FC_Slab.EC_1";//REAL-->ec amount 1
	public static final String COL_EC_2="EC_FC_Slab.EC_2";//REAL-->ec amount 2
	public static final String COL_EC_3="EC_FC_Slab.EC_3";//REAL-->ec amount 3
	public static final String COL_EC_4="EC_FC_Slab.EC_4";//REAL-->ec amount 4
	public static final String COL_EC_5="EC_FC_Slab.EC_5";//REAL
	public static final String COL_EC_6="EC_FC_Slab.EC_6";//REAL
	public static final String COL_FC_1="EC_FC_Slab.FC_1";//REAL-->fc amount 1
	public static final String COL_FC_2="EC_FC_Slab.FC_2";//REAL-->fc amount 2
	public static final String COL_TEc_Slab="EC_FC_Slab.TEc";//REAL
	public static final String COL_EC_FL_1="EC_FC_Slab.EC_FL_1";//REAL
	public static final String COL_EC_FL_2="EC_FC_Slab.EC_FL_2";//REAL
	public static final String COL_EC_FL_3="EC_FC_Slab.EC_FL_3";//REAL
	public static final String COL_EC_FL_4="EC_FC_Slab.EC_FL_4";//REAL
	public static final String COL_EC_FL="EC_FC_Slab.EC_FL";//REAL
	public static final String COL_new_TEc="EC_FC_Slab.new_TEc";//REAL
	public static final String COL_old_TEc="EC_FC_Slab.old_TEc";//REAL

	//schema for ReadSlabNTariff Table //needed by Billing logic
	public static final String TABLE_ReadSlabNTariff="ReadSlabNTariff";
	public static final String COL_UID_R_Slab="ReadSlabNTariff.UID";//INTEGER PRIMARY KEY
	public static final String COL_TarifCode="ReadSlabNTariff.TarifCode";//INTEGER 
	public static final String COL_TarifString="ReadSlabNTariff.TarifString";//TEXT

	//schema for Reprint_Count Table
	public static final String TABLE_Reprint_Countf="Reprint_Count";
	public static final String COL_Count="Reprint_Count.Count"; //INTEGER


	//schema for SBM_BillCollDataMain Table //needed by Billing logic
	public static final String TABLE_SBM_BillCollDataMain="SBM_BillCollDataMain";
	public static final String COL_UID_S_BcData="SBM_BillCollDataMain.UID";//INTEGER PRIMARY KEY

	public static final String COL_ForMonth="SBM_BillCollDataMain.ForMonth";//TEXT
	public static final String COL_BillDate="SBM_BillCollDataMain.BillDate";//TEXT
	public static final String COL_SubId="SBM_BillCollDataMain.SubId";//TEXT
	public static final String COL_ConnectionNo="SBM_BillCollDataMain.ConnectionNo";//TEXT
	public static final String COL_CustomerName="SBM_BillCollDataMain.CustomerName";//TEXT
	public static final String COL_TourplanId="SBM_BillCollDataMain.TourplanId";//TEXT
	public static final String COL_BillNo="SBM_BillCollDataMain.BillNo";//TEXT
	public static final String COL_DueDate="SBM_BillCollDataMain.DueDate";//TEXT
	public static final String COL_FixedCharges="SBM_BillCollDataMain.FixedCharges";//REAL
	public static final String COL_RebateFlag="SBM_BillCollDataMain.RebateFlag";//TEXT
	public static final String COL_ReaderCode="SBM_BillCollDataMain.ReaderCode";//TEXT
	public static final String COL_TariffCode="SBM_BillCollDataMain.TariffCode";//INTEGER
	public static final String COL_ReadingDay="SBM_BillCollDataMain.ReadingDay";//TEXT
	public static final String COL_PF="SBM_BillCollDataMain.PF";//TEXT
	public static final String COL_MF="SBM_BillCollDataMain.MF";//REAL 
	public static final String COL_Status="SBM_BillCollDataMain.Status";//INTEGER -->Current status
	public static final String COL_AvgCons="SBM_BillCollDataMain.AvgCons";//TEXT
	public static final String COL_LinMin="SBM_BillCollDataMain.LinMin";//REAL
	public static final String COL_SancHp="SBM_BillCollDataMain.SancHp";//REAL
	public static final String COL_SancKw="SBM_BillCollDataMain.SancKw";//REAL-->FC
	public static final String COL_SancLoad="SBM_BillCollDataMain.SancLoad";//TEXT
	public static final String COL_PrevRdg="SBM_BillCollDataMain.PrevRdg";//TEXT---->Previous Reading
	public static final String COL_DlCount="SBM_BillCollDataMain.DlCount";//INTEGER
	public static final String COL_Arears="SBM_BillCollDataMain.Arears";//REAL---->Arrears 
	public static final String COL_IntrstCurnt="SBM_BillCollDataMain.IntrstCurnt";//REAL
	public static final String COL_DrFees="SBM_BillCollDataMain.DrFees";//REAL
	public static final String COL_Others="SBM_BillCollDataMain.Others";//REAL
	public static final String COL_BillFor="SBM_BillCollDataMain.BillFor";//TEXT
	public static final String COL_BlCnt="SBM_BillCollDataMain.BlCnt";//TEXT
	public static final String COL_RRNo="SBM_BillCollDataMain.RRNo";//TEXT
	public static final String COL_LegFol="SBM_BillCollDataMain.LegFol";
	public static final String COL_TvmMtr="SBM_BillCollDataMain.TvmMtr";//TEXT
	public static final String COL_TaxFlag="SBM_BillCollDataMain.TaxFlag";//TEXT
	public static final String COL_ArrearsOld="SBM_BillCollDataMain.ArrearsOld";//REAL---->ArrearsOld + IntrstOld ---> Arreras + Intrest
	public static final String COL_Intrst_Unpaid="SBM_BillCollDataMain.Intrst_Unpaid";//REAL
	public static final String COL_IntrstOld="SBM_BillCollDataMain.IntrstOld";//REAL---->ArrearsOld + IntrstOld ---> Arreras + Intrest
	public static final String COL_Billable="SBM_BillCollDataMain.Billable";//TEXT
	public static final String COL_NewNoofDays="SBM_BillCollDataMain.NewNoofDays";//TEXT
	public static final String COL_NoOfDays="SBM_BillCollDataMain.NoOfDays";//TEXT
	public static final String COL_HWCReb="SBM_BillCollDataMain.HWCReb";//TEXT
	public static final String COL_DLAvgMin="SBM_BillCollDataMain.DLAvgMin";//TEXT
	public static final String COL_TvmPFtype="SBM_BillCollDataMain.TvmPFtype";//TEXT
	public static final String COL_AccdRdg="SBM_BillCollDataMain.AccdRdg";//TEXT
	public static final String COL_KVAIR="SBM_BillCollDataMain.KVAIR";//TEXT
	public static final String COL_DLTEc="SBM_BillCollDataMain.DLTEc";//REAL
	public static final String COL_RRFlag="SBM_BillCollDataMain.RRFlag";//Text
	public static final String COL_Mtd="SBM_BillCollDataMain.Mtd";//TEXT
	public static final String COL_SlowRtnPge="SBM_BillCollDataMain.SlowRtnPge";//REAL
	public static final String COL_OtherChargeLegend="SBM_BillCollDataMain.OtherChargeLegend";//TEXT
	public static final String COL_GoKArrears="SBM_BillCollDataMain.GoKArrears";//REAL---->Extra Fields ----> GoK 
	public static final String COL_DPdate="SBM_BillCollDataMain.DPdate";//TEXT
	public static final String COL_ReceiptAmnt="SBM_BillCollDataMain.ReceiptAmnt";//REAL
	public static final String COL_ReceiptDate="SBM_BillCollDataMain.ReceiptDate";//TEXT
	public static final String COL_TcName="SBM_BillCollDataMain.TcName";//TEXT
	public static final String COL_ThirtyFlag="SBM_BillCollDataMain.ThirtyFlag";//TEXT
	public static final String COL_IODRemarks="SBM_BillCollDataMain.IODRemarks";//TEXT-----> if values comes in Extra Fields
	public static final String COL_DayWise_Flag="SBM_BillCollDataMain.DayWise_Flag";//TEXT
	public static final String COL_Old_Consumption="SBM_BillCollDataMain.Old_Consumption";//TEXT
	public static final String COL_KVAAssd_Cons="SBM_BillCollDataMain.KVAAssd_Cons";//REAL
	public static final String COL_GvpId="SBM_BillCollDataMain.GvpId";//TEXT
	public static final String COL_BOBilled_Amount="SBM_BillCollDataMain.BOBilled_Amount";//TEXT
	public static final String COL_KVAH_OldConsumption="SBM_BillCollDataMain.KVAH_OldConsumption";//REAL
	public static final String COL_EcsFlag="SBM_BillCollDataMain.EcsFlag";//TEXt
	public static final String COL_Supply_Points="SBM_BillCollDataMain.Supply_Points";//INTEGER
	public static final String COL_IODD11_Remarks="SBM_BillCollDataMain.IODD11_Remarks";//TEXT-----> if values comes in Extra Fields
	public static final String COL_LocationCode="SBM_BillCollDataMain.LocationCode";//TEXT
	public static final String COL_BOBillFlag="SBM_BillCollDataMain.BOBillFlag";//INTEGER
	public static final String COL_Address1="SBM_BillCollDataMain.Address1";//TEXT
	public static final String COL_Address2="SBM_BillCollDataMain.Address2";//TEXT
	public static final String COL_SectionName="SBM_BillCollDataMain.SectionName";//TEXT
	public static final String COL_OldConnID="SBM_BillCollDataMain.OldConnID";//TEXT
	public static final String COL_MCHFlag="SBM_BillCollDataMain.MCHFlag";//TEXt
	public static final String COL_FC_Slab_2="SBM_BillCollDataMain.FC_Slab_2";//REAL--->value greater than 0 --> 2 rows hp, kw
	public static final String COL_MobileNo="SBM_BillCollDataMain.MobileNo";//TEXT
	public static final String COL_PreRead="SBM_BillCollDataMain.PreRead";//TEXT---->Current Reading
	public static final String COL_PreStatus="SBM_BillCollDataMain.PreStatus";//TEXT---->present/current Status
	public static final String COL_SpotSerialNo="SBM_BillCollDataMain.SpotSerialNo";//INTEGER
	public static final String COL_Units="SBM_BillCollDataMain.Units";//TEXT--->consumptions
	public static final String COL_TFc="SBM_BillCollDataMain.TFc";//REAL
	public static final String COL_TEc="SBM_BillCollDataMain.TEc";//REAL
	public static final String COL_FLReb="SBM_BillCollDataMain.FLReb";//REAL
	public static final String COL_ECReb="SBM_BillCollDataMain.ECReb";//REAL
	public static final String COL_TaxAmt="SBM_BillCollDataMain.TaxAmt";//REAL----> Tax on Amount
	public static final String COL_PfPenAmt="SBM_BillCollDataMain.PfPenAmt";//REAL---->pf pen amt
	public static final String COL_PenExLd="SBM_BillCollDataMain.PenExLd";//REAL----->pen on ex Ld Amt
	public static final String COL_HCReb="SBM_BillCollDataMain.HCReb";//REAL  Weavers
	public static final String COL_HLReb="SBM_BillCollDataMain.HLReb";//REAL  Handicap
	public static final String COL_CapReb="SBM_BillCollDataMain.CapReb";//REAL capacitor or charitable
	public static final String COL_ExLoad="SBM_BillCollDataMain.ExLoad";//REAL
	public static final String COL_DemandChrg="SBM_BillCollDataMain.DemandChrg";//REAl
	public static final String COL_AccdRdg_rtn="SBM_BillCollDataMain.AccdRdg_rtn";//TEXT
	public static final String COL_KVAFR="SBM_BillCollDataMain.KVAFR";//TEXT
	public static final String COL_AbFlag="SBM_BillCollDataMain.AbFlag";//TEXT
	public static final String COL_BjKj2Lt2="SBM_BillCollDataMain.BjKj2Lt2";//TEXT
	public static final String COL_Remarks="SBM_BillCollDataMain.Remarks";//TEXt
	public static final String COL_GoKPayable="SBM_BillCollDataMain.GoKPayable";//REAL--->Extra Fields
	public static final String COL_IssueDateTime="SBM_BillCollDataMain.IssueDateTime";//TEXT
	public static final String COL_RecordDmnd="SBM_BillCollDataMain.RecordDmnd";//real
	public static final String COL_KVA_Consumption="SBM_BillCollDataMain.KVA_Consumption";//REAL
	public static final String COL_BillTotal="SBM_BillCollDataMain.BillTotal";//REAL---->NET Amount Due
	public static final String COL_SBMNumber="SBM_BillCollDataMain.SBMNumber";//TEXT
	public static final String COL_RcptCnt="SBM_BillCollDataMain.RcptCnt";//INTEGER
	public static final String COL_Batch_No="SBM_BillCollDataMain.Batch_No";//TEXT
	public static final String COL_Receipt_No="SBM_BillCollDataMain.Receipt_No";//INTEGER
	public static final String COL_DateTime="SBM_BillCollDataMain.DateTime";//TEXT
	public static final String COL_Payment_Mode="SBM_BillCollDataMain.Payment_Mode";//TEXT
	public static final String COL_Paid_Amt="SBM_BillCollDataMain.Paid_Amt";//INTEGER
	public static final String COL_ChequeDDNo="SBM_BillCollDataMain.ChequeDDNo";//INTEGER
	public static final String COL_ChequeDDDate="SBM_BillCollDataMain.ChequeDDDate";//TEXT
	public static final String COL_Receipttypeflag="SBM_BillCollDataMain.Receipttypeflag";//TEXT
	public static final String COL_ApplicationNo="SBM_BillCollDataMain.ApplicationNo";//TEXT
	public static final String COL_ChargetypeID="SBM_BillCollDataMain.ChargetypeID";//INTEGER
	public static final String COL_BankID="SBM_BillCollDataMain.BankID";//INTEGER
	public static final String COL_Latitude="SBM_BillCollDataMain.Latitude";//TEXT
	public static final String COL_Latitude_Dir="SBM_BillCollDataMain.Latitude_Dir";//TEXT
	public static final String COL_Longitude="SBM_BillCollDataMain.Longitude";//TEXT
	public static final String COL_Longitude_Dir="SBM_BillCollDataMain.Longitude_Dir";//TEXT
	public static final String COL_Gprs_Flag="SBM_BillCollDataMain.Gprs_Flag";//INTEGER
	public static final String COL_ConsPayable="SBM_BillCollDataMain.ConsPayable";//TEXT--->Extra Fiels
	public static final String COL_MtrDisFlag="SBM_BillCollDataMain.MtrDisFlag";//INTEGER
	public static final String COL_Meter_type="SBM_BillCollDataMain.Meter_type";//TEXT
	public static final String COL_Meter_serialno="SBM_BillCollDataMain.Meter_serialno";//TEXT
	public static final String COL_Gps_Latitude_image="SBM_BillCollDataMain.Gps_Latitude_image";//TEXT
	public static final String COL_Gps_LatitudeCardinal_image="SBM_BillCollDataMain.Gps_LatitudeCardinal_image";//TEXT
	public static final String COL_Gps_Longitude_image="SBM_BillCollDataMain.Gps_Longitude_image";//TEXT
	public static final String COL_Gps_LongitudeCardinal_image="SBM_BillCollDataMain.Gps_LongitudeCardinal_image";//TEXT
	public static final String COL_Gps_Latitude_print="SBM_BillCollDataMain.Gps_Latitude_print";//TEXT
	public static final String COL_Gps_LatitudeCardinal_print="SBM_BillCollDataMain.Gps_LatitudeCardinal_print";//TEXT
	public static final String COL_Gps_Longitude_print="SBM_BillCollDataMain.Gps_Longitude_print";//TEXT
	public static final String COL_Gps_LongitudeCardinal_print="SBM_BillCollDataMain.Gps_LongitudeCardinal_print";//TEXT
	public static final String COL_Image_Name="SBM_BillCollDataMain.Image_Name";//TEXT
	public static final String COL_Image_Path="SBM_BillCollDataMain.Image_Path";//TEXT
	public static final String COL_Image_Cap_Date="SBM_BillCollDataMain.Image_Cap_Date";//TEXT
	public static final String COL_Image_Cap_Time="SBM_BillCollDataMain.Image_Cap_Time";//TEXT
	public static final String COL_GPRS_Status = "SBM_BillCollDataMain.GPRS_Status";//TEXT
	public static final String COL_REASONID = "SBM_BillCollDataMain.ReasonId";//INTEGER  //Nitish  30-08-2014 
	public static final String COL_METER_PRESENT_FLAG = "SBM_BillCollDataMain.Meter_Present_Flag";//INTEGER Added on 30-08-2014
	public static final String COL_MTR_NOT_VISIBLE = "SBM_BillCollDataMain.Mtr_Not_Visible";//INTEGER Added on 30-08-2014

	public static final String COL_DLTEc_GoK = "SBM_BillCollDataMain.DLTEc_GoK";//REAL Added on 31-12-2014	
	public static final String COL_OldTecBill="SBM_BillCollDataMain.OldTecBill";//REAL //21-04-2016
	public static final String COL_FACValue="SBM_BillCollDataMain.FACValue";//TEXT //19-12-2015

	//30-07-2015
	public static final String COL_AadharNo = "SBM_BillCollDataMain.AadharNo"; //TEXT
	public static final String COL_VoterIdNo = "SBM_BillCollDataMain.VoterIdNo"; //TEXT
	public static final String COL_MtrMakeFlag = "SBM_BillCollDataMain.MtrMakeFlag"; //INTEGER
	public static final String COL_MtrBodySealFlag = "SBM_BillCollDataMain.MtrBodySealFlag"; //INTEGER
	public static final String COL_MtrTerminalCoverFlag = "SBM_BillCollDataMain.MtrTerminalCoverFlag"; //INTEGER
	public static final String COL_MtrTerminalCoverSealedFlag = "SBM_BillCollDataMain.MtrTerminalCoverSealedFlag"; //INTEGER


	//schema for Sim_Details Table
	public static final String TABLE_Sim_Details="Sim_Details";
	public static final String COL_SIM_No="Sim_Details.SIM_No";//TEXT
	public static final String COL_SBM_No="Sim_Details.SBM_No";//TEXT

	//schema for WriteVarValues Table
	public static final String TABLE_WriteVarValues="WriteVarValues";
	public static final String COL_UID_WVarValue="WriteVarValues.UID";//INTEGER
	public static final String COL_m="WriteVarValues.m";//INTEGER
	public static final String COL_UpFlag="WriteVarValues.UpFlag";//INTEGER
	public static final String COL_Cnt="WriteVarValues.Cnt";//INTEGER
	public static final String COL_SpotSerialno="WriteVarValues.SpotSerialno";//INTEGER
	public static final String COL_Tcbd="WriteVarValues.Tcbd";//INTEGER
	public static final String COL_h="WriteVarValues.h";//INTEGER
	public static final String COL_NoSlab="WriteVarValues.NoSlab";//INTEGER
	public static final String COL_SubDivisionName="WriteVarValues.SubDivisionName";//TEXT
	public static final String COL_FLoadDate="WriteVarValues.FLoadDate";//TEXT
	public static final String COL_AbnormalBlockFlag="WriteVarValues.AbnormalBlockFlag";//INTEGER
	public static final String COL_Bill_Count_Report="WriteVarValues.Bill_Count_Report";//INTEGER
	public static final String COL_Month_Id="WriteVarValues.Month_Id";//INTEGER
	public static final String COL_Year_Of_Bill="WriteVarValues.Year_Of_Bill";//INTEGER




	public static final String TABLE_USERS = "users";
	public static final String COL_USERNAME_USERS = "users.username";//TEXT
	public static final String COL_PASSWORD_USERS = "users.password";//TEXT



	public static final String TABLE_STATUSMASTER = "StatusMaster";
	public static final String COL_STATUSID_STATUSMASTER = "StatusMaster.StatusID";
	public static final String COL_STATUSDESC_STATUSMASTER = "StatusMaster.StatusDesc";
	public static final String COL_STATUS_STATUSMASTER = "StatusMaster.Status";//INTEGER
	public static final String COL_VALUE_STATUSMASTER = "StatusMaster.Value";//TEXT


	//Nitish on 19-03-2014
	public static final String TABLE_BANKLIST = "BANKLIST";
	public static final String COL_UID_BANKLIST = "BANKLIST.UID";//INTEGER
	public static final String COL_BID_BANKLIST = "BANKLIST.BID";//INTEGER
	public static final String COL_BANKID_BANKLIST = "BANKLIST.BANKID";//Text
	//END Nitish on 19-03-2014

	//Nitish on 08-04-2014
	public static final String TABLE_COLLECTION_TABLE= "Collection_TABLE";
	public static final String COL_UID_COLLECTION_TABLE = "Collection_TABLE.UID";//INTEGER
	public static final String COL_ConnectionNo_COLLECTION_TABLE = "Collection_TABLE.ConnectionNo";//TEXT
	public static final String COL_RRNo_COLLECTION_TABLE = "Collection_TABLE.RRNo";//TEXT
	public static final String COL_CustomerName_COLLECTION_TABLE = "Collection_TABLE.CustomerName";//TEXT
	public static final String COL_RcptCnt_COLLECTION_TABLE = "Collection_TABLE.RcptCnt";//INTEGER
	public static final String COL_Batch_No_COLLECTION_TABLE = "Collection_TABLE.Batch_No";//TEXT
	public static final String COL_Receipt_No_COLLECTION_TABLE = "Collection_TABLE.Receipt_No";//TEXT
	public static final String COL_DateTime_COLLECTION_TABLE = "Collection_TABLE.DateTime";//TEXT
	public static final String COL_Payment_Mode_COLLECTION_TABLE = "Collection_TABLE.Payment_Mode";//TEXT
	public static final String COL_Arrears_COLLECTION_TABLE = "Collection_TABLE.Arrears";//TEXT
	public static final String COL_BillTotal_COLLECTION_TABLE = "Collection_TABLE.BillTotal";//TEXT
	public static final String COL_Paid_Amt_COLLECTION_TABLE = "Collection_TABLE.Paid_Amt";//INTEGER
	public static final String COL_BankID_COLLECTION_TABLE = "Collection_TABLE.BankID";//INTEGER
	public static final String COL_ChequeDDNo_COLLECTION_TABLE = "Collection_TABLE.ChequeDDNo";//INTEGER
	public static final String COL_ChequeDDDate_COLLECTION_TABLE = "Collection_TABLE.ChequeDDDate";//TEXT
	public static final String COL_Receipttypeflag_COLLECTION_TABLE = "Collection_TABLE.Receipttypeflag";//TEXT
	public static final String COL_GvpId_COLLECTION_TABLE= "Collection_TABLE.GvpId";//TEXT
	public static final String COL_SBMNumber_COLLECTION_TABLE = "Collection_TABLE.SBMNumber";//TEXT
	public static final String COL_LocationCode_COLLECTION_TABLE = "Collection_TABLE.LocationCode";//TEXT
	public static final String COL_Gprs_Flag_COLLECTION_TABLE="Collection_TABLE.Gprs_Flag";//INTEGER
	public static final String COL_ArrearsBill_Flag_COLLECTION_TABLE="Collection_TABLE.ArrearsBill_Flag";//INTEGER
	public static final String COL_ReaderCode_COLLECTION_TABLE = "Collection_TABLE.ReaderCode";//TEXT
	public static final String COL_GPRS_Status_COLLECTION_TABLE = "Collection_TABLE.GPRS_Status";//TEXT
	public static final String COL_IODRemarks_COLLECTION_TABLE = "Collection_TABLE.IODRemarks";//TEXT
	//END Nitish on 08-04-2014

	//Nitish on 19-04-2014 //Modified 26-09-2014
	public static final String TABLE_CASHCOUNTER_DETAILS= "CashCounter_Details";
	public static final String COL_UID_CASHCOUNTER_DETAILS = "CashCounter_Details.UID";//INTEGER
	public static final String COL_IMEINo_CASHCOUNTER_DETAILS = "CashCounter_Details.IMEINo";//TEXT
	public static final String COL_SIMNo_CASHCOUNTER_DETAILS = "CashCounter_Details.SIMNo";//TEXT
	public static final String COL_BatchDate_CASHCOUNTER_DETAILS = "CashCounter_Details.BatchDate";//TEXT
	public static final String COL_CashLimit_CASHCOUNTER_DETAILS = "CashCounter_Details.CashLimit";//TEXT
	public static final String COL_StartTime_CASHCOUNTER_DETAILS = "CashCounter_Details.StartTime";//TEXT
	public static final String COL_EndTime_CASHCOUNTER_DETAILS = "CashCounter_Details.EndTime";//TEXT
	public static final String COL_Batch_No_CASHCOUNTER_DETAILS = "CashCounter_Details.Batch_No";//TEXT
	public static final String COL_DateTime_CASHCOUNTER_DETAILS = "CashCounter_Details.DateTime";//TEXT	
	public static final String COL_LocationCode_CASHCOUNTER_DETAILS = "CashCounter_Details.LocationCode";//TEXT
	public static final String COL_CashCounterOpen_CASHCOUNTER_DETAILS = "CashCounter_Details.CashCounterOpen";//TEXT
	public static final String COL_CounterCloseDateTime_CASHCOUNTER_DETAILS = "CashCounter_Details.CounterCloseDateTime";//TEXT
	//Nitish 06-08-2015
	public static final String COL_ExtensionFlag_CASHCOUNTER_DETAILS = "CashCounter_Details.ExtensionFlag";//TEXT
	public static final String COL_ExtensionDateTime_CASHCOUNTER_DETAILS = "CashCounter_Details.ExtensionDateTime";//TEXT



	//Nitish on 01-07-2014
	public static final String TABLE_GPS_DETAILS= "GPS_Details";
	public static final String COL_UID_GPS_DETAILS = "GPS_Details.UID";//INTEGER
	public static final String COL_IMEINo_GPS_DETAILS= "GPS_Details.IMEINo";//TEXT
	public static final String COL_SIMNo_GPS_DETAILS = "GPS_Details.SIMNo";//TEXT	
	public static final String COL_DateTime_GPS_DETAILS = "GPS_Details.DateTime";//TEXT		
	public static final String COL_MRID_GPS_DETAILS = "GPS_Details.MRID";//TEXT
	public static final String COL_Latitude_GPS_DETAILS = "GPS_Details.Latitude";//TEXT
	public static final String COL_Longitude_GPS_DETAILS = "GPS_Details.Longitude";//TEXT
	public static final String COL_LocationCode_GPS_DETAILS = "GPS_Details.LocationCode";//TEXT
	public static final String COL_Gprs_Flag_GPS_DETAILS="GPS_Details.Gprs_Flag";//INTEGER
	//END Nitish on 01-07-2014

	//Nitish on 25-07-2014
	public static final String TABLE_REASONMASTER= "ReasonMaster";
	public static final String COL_REASONMASTER_REASONID = "ReasonMaster.ReasonId";//INTEGER
	public static final String COL_REASONMASTER_REASON= "ReasonMaster.Reason";//TEXT

	//Added on 30-08-2014
	public static final String TABLE_METERTYPEMASTER= "MeterTypeMaster";
	public static final String COL_METERTYPEMASTER_METERTYPEID = "MeterTypeMaster.MeterTypeId";//INTEGER
	public static final String COL_METERTYPEMASTER_METERTYPE = "MeterTypeMaster.MeterType";//TEXT

	//Added on 30-08-2014
	public static final String TABLE_METERPLACEMENTMASTER = "MeterPlacementMaster";
	public static final String COL_METERPLACEMENTMASTER_UNIQUEID = "MeterPlacementMaster.UniqueId";//INTEGER
	public static final String COL_METERPLACEMENTMASTER_METERPLACEMENT = "MeterPlacementMaster.MeterPlacement";//TEXT

	//Added on 30-08-2014
	public static final String TABLE_METERCONDITION = "MeterCondition";
	public static final String COL_METERCONDITION_UNIQUEID = "MeterCondition.UniqueId";//INTEGER
	public static final String COL_METERCONDITION_METERCONDITION = "MeterCondition.MeterCondition";//TEXT

	//Added on 30-08-2014
	public static final String TABLE_BATTERYSTATUS = "BatteryStatus";
	public static final String COL_BATTERYSTATUS_STATUSID = "BatteryStatus.StatusId";//INTEGER
	public static final String COL_BATTERYSTATUS_STATUS = "BatteryStatus.Status";//TEXT


	//Nitish on 14-01-2015
	public static final String TABLE_FaceRecognition="TBL_FaceRecognition";
	public static final String COL_Id_TABLE_FaceRecognition="TBL_FaceRecognition.Id"; //TEXT	
	public static final String COL_IMEINumber_TABLE_FaceRecognition="TBL_FaceRecognition.ImeiNumber"; //TEXT	
	public static final String COL_SimNumber_TABLE_FaceRecognition="TBL_FaceRecognition.SimNumber"; //TEXT	
	public static final String COL_Similarity_TABLE_FaceRecognition="TBL_FaceRecognition.Similarity"; //TEXT	
	public static final String COL_GprsFlag_TABLE_FaceRecognition="TBL_FaceRecognition.GprsFlag";//TEXT
	public static final String COL_CreatedDate_TABLE_FaceRecognition="TBL_FaceRecognition.CreatedDate";//TEXT
	public static final String COL_PhotoName_TABLE_FaceRecognition="TBL_FaceRecognition.PhotoName";//TEXT

	//schema for Meter_Details Table
	public static final String TABLE_CONFIGURATION="TABLE_CONFIGURATION";
	public static final String COL_UID_TABLE_CONFIGURATION="TABLE_CONFIGURATION.UID";//TEXT
	public static final String COL_IMEINO_TABLE_CONFIGURATION="TABLE_CONFIGURATION.IMEINO";//TEXT
	public static final String COL_SIMNO_TABLE_CONFIGURATION="TABLE_CONFIGURATION.SIMNO";//TEXT
	public static final String COL_COMPANY_TABLE_CONFIGURATION="TABLE_CONFIGURATION.COMPANY";//TEXT
	public static final String COL_LOCATIONCODE_TABLE_CONFIGURATION="TABLE_CONFIGURATION.LOCATIONCODE";//TEXT
	public static final String COL_SUBDIVISION_TABLE_CONFIGURATION="TABLE_CONFIGURATION.SUBDIVISION";//TEXT
	public static final String COL_STAFFNAME_CONFIGURATION="TABLE_CONFIGURATION.STAFFNAME";//TEXT
	public static final String COL_CAMERAFLAG_CONFIGURATION="TABLE_CONFIGURATION.CAMERAFLAG";//TEXT
	public static final String COL_PRINTERFLAG_TABLE_CONFIGURATION="TABLE_CONFIGURATION.PRINTERFLAG";//TEXT
	public static final String COL_CONFIGFLAG_TABLE_CONFIGURATION="TABLE_CONFIGURATION.CONFIGFLAG";//TEXT
	public static final String COL_VERSIONNO_CONFIGURATION="TABLE_CONFIGURATION.VERSIONNO";//TEXT
	public static final String COL_ENABLEDISABLE_CONFIGURATION="TABLE_CONFIGURATION.ENABLEDISABLE";//TEXT
	public static final String COL_CREATEDDATE_CONFIGURATION="TABLE_CONFIGURATION.CREATEDDATE";//TEXT


	//schema for Billing_Details Table //15-07-2016
	public static final String TABLE_Billing_Details= "Billing_Details";
	public static final String COL_UID_Billing_Details = "Billing_Details.UID";//INTEGER
	public static final String COL_IMEINo_Billing_Details = "Billing_Details.IMEINo";//TEXT
	public static final String COL_SIMNo_Billing_Details = "Billing_Details.SIMNo";//TEXT
	public static final String COL_BatchDate_Billing_Details = "Billing_Details.BatchDate";//TEXT		
	public static final String COL_StartTime_Billing_Details = "Billing_Details.StartTime";//TEXT
	public static final String COL_EndTime_Billing_Details = "Billing_Details.EndTime";//TEXT
	public static final String COL_Batch_No_Billing_Details = "Billing_Details.Batch_No";//TEXT
	public static final String COL_DateTime_Billing_Details = "Billing_Details.DateTime";//TEXT	
	public static final String COL_LocationCode_Billing_Details = "Billing_Details.LocationCode";//TEXT
	public static final String COL_BillingOpen_Billing_Details = "Billing_Details.BillingOpen";//TEXT
	public static final String COL_BillingCloseDateTime_Billing_Details = "Billing_Details.BillingCloseDateTime";//TEXT
	public static final String COL_ExtensionFlag_Billing_Details = "Billing_Details.ExtensionFlag";//TEXT
	public static final String COL_ExtensionDateTime_Billing_Details = "Billing_Details.ExtensionDateTime";//TEXT

	//schema for Event_Log Table //15-07-2016
	public static final String TABLE_EventLog= "TABLE_EventLog";
	public static final String COL_UID_TABLE_EventLog = "TABLE_EventLog.UID";//INTEGER
	public static final String COL_IMEINO_TABLE_EventLog = "TABLE_EventLog.IMEINO";//TEXT
	public static final String COL_SIMNO_TABLE_EventLog = "TABLE_EventLog.SIMNO";//TEXT
	public static final String COL_Flag_TABLE_EventLog = "TABLE_EventLog.Flag";//TEXT		
	public static final String COL_Description_TABLE_EventLog = "TABLE_EventLog.Description";//TEXT
	public static final String COL_DateTime_TABLE_EventLog = "TABLE_EventLog.DateTime";//TEXT
	public static final String COL_GPRSFlag_TABLE_EventLog = "TABLE_EventLog.GPRSFlag";//INTEGER
	public static final String COL_GPRSStatus_TABLE_EventLog = "TABLE_EventLog.GPRS";//TEXT	

	//schema for LogMaster
	public static final String TABLE_LogMaster= "LogMaster";
	public static final String COL_UID_TABLE_LogMaster = "LogMaster.UID";//INTEGER Primary Key
	public static final String COL_ID_TABLE_LogMaster = "LogMaster.ID";//TEXT
	public static final String COL_Event_Timestamp_TABLE_LogMaster = "LogMaster.Event_Timestamp";//TEXT



	//schema for survey
	public static final String TABLE_HescomSurvey= "HescomSurvey";
	public static final String COL_UID_TABLE_HescomSurvey = "HescomSurvey.UID";//INTEGER Primary Key
	public static final String COL_RRNo_TABLE_HescomSurvey = "HescomSurvey.RRNo";//TEXT
	public static final String COL_ConnectionId_TABLE_HescomSurvey = "HescomSurvey.ConnectionId";//TEXT
	public static final String COL_CustomerName_TABLE_HescomSurvey = "HescomSurvey.CustomerName";//TEXT
	public static final String COL_ConnectionType_TABLE_HescomSurvey = "HescomSurvey.ConnectionType";//TEXT
	public static final String COL_Phase_TABLE_HescomSurvey = "HescomSurvey.Phase";//TEXT
	public static final String COL_MeterMake_TABLE_HescomSurvey = "HescomSurvey.MeterMake";//TEXT
	public static final String COL_Model_TABLE_HescomSurvey = "HescomSurvey.Model";//TEXT
	public static final String COL_MeterType_TABLE_HescomSurvey = "HescomSurvey.MeterType";//TEXT
	public static final String COL_MeterBoxAvailability_TABLE_HescomSurvey = "HescomSurvey.MeterBoxAvailability";//TEXT
	public static final String COL_TypeOfBox_TABLE_HescomSurvey = "HescomSurvey.TypeOfBox";//TEXT
	public static final String COL_MeterPlacement_TABLE_HescomSurvey = "HescomSurvey.MeterPlacement";//TEXT
	public static final String COL_HeightOfMeter_TABLE_HescomSurvey = "HescomSurvey.HeightOfMeter";//TEXT
	public static final String COL_AvailabilityOfLineOfSiht_TABLE_HescomSurvey = "HescomSurvey.AvailabilityOfLineOfSiht";//TEXT
	public static final String COL_floor_TABLE_HescomSurvey = "HescomSurvey.floor";//TEXT
	public static final String COL_NoOfMeterAvailable_TABLE_HescomSurvey = "HescomSurvey.NoOfMeterAvailable";//TEXT
	public static final String COL_MeterDiemension_TABLE_HescomSurvey = "HescomSurvey.MeterDiemension";//TEXT
	public static final String COL_Remarks_TABLE_HescomSurvey = "HescomSurvey.Remarks";//TEXT
	public static final String COL_Lattitude_TABLE_HescomSurvey = "HescomSurvey.Lattitude";//TEXT
	public static final String COL_Longitude_TABLE_HescomSurvey = "HescomSurvey.Longitude";//TEXT
	public static final String COL_GPSFlag_TABLE_HescomSurvey = "HescomSurvey.GPSFlag";//TEXT
	public static final String COL_ImagePath_TABLE_HescomSurvey = "HescomSurvey.ImagePath";//TEXT
	public static final String COL_DateTime_TABLE_HescomSurvey = "HescomSurvey.DateTime";//TEXT
	public static final String COL_SlaveId_TABLE_HescomSurvey = "HescomSurvey.SlaveId";//TEXT
	public static final String COL_ComRJ11_TABLE_HescomSurvey = "HescomSurvey.ComRJ11";//TEXT
	public static final String COL_ComOptical_TABLE_HescomSurvey = "HescomSurvey.ComOptical";//TEXT
	public static final String COL_Protocol_TABLE_HescomSurvey = "HescomSurvey.Protocol";//TEXT
	public static final String COL_OpticalReading_TABLE_HescomSurvey = "HescomSurvey.OpticalReading";//TEXT
	public static final String COL_MeterSlNo_TABLE_HescomSurvey = "HescomSurvey.MeterSlNo";//TEXT
	public static final String COL_YearofManufacture_TABLE_HescomSurvey = "HescomSurvey.YearofManufacture";//TEXT
	public static final String COL_TransFormerName_TABLE_HescomSurvey = "HescomSurvey.TransFormerName";//TEXT




	//schema for DCUMaster
	public static final String TABLE_DCUMaster= "DCUMaster";
	public static final String COL_UID_TABLE_DCUMaster = "DCUMaster.UID";//INTEGER Primary Key
	public static final String COL_DCUID_TABLE_DCUMaster = "DCUMaster.DCUID";//TEXT
	public static final String COL_DCUName_TABLE_DCUMaster = "DCUMaster.DCUName";//TEXT

	//schema for SlaveMaster
	public static final String TABLE_SlaveMaster= "SlaveMaster";
	public static final String COL_UID_TABLE_SlaveMaster = "SlaveMaster.UID";//INTEGER Primary Key
	public static final String COL_DCUID_TABLE_SlaveMaster = "SlaveMaster.DCUID";//TEXT
	public static final String COL_SlaveID_TABLE_SlaveMaster = "SlaveMaster.SlaveID";//TEXT
	public static final String COL_SlaveName_TABLE_SlaveMaster = "SlaveMaster.SlaveName";//TEXT

	//schema for DCUSlaveMapData
	public static final String TABLE_DCUSlaveMapData= "DCUSlaveMapData";
	public static final String COL_UID_TABLE_DCUSlaveMapData = "DCUSlaveMapData.UID";//INTEGER Primary Key
	public static final String COL_Flag_TABLE_DCUSlaveMapData = "DCUSlaveMapData.Flag";//TEXT
	public static final String COL_Latitude_TABLE_DCUSlaveMapData = "DCUSlaveMapData.Latitude";//TEXT
	public static final String COL_Longitude_TABLE_DCUSlaveMapData = "DCUSlaveMapData.Longitude";//TEXT
	public static final String COL_Name_TABLE_DCUSlaveMapData = "DCUSlaveMapData.Name";//TEXT	
	public static final String COL_Remarks_TABLE_DCUSlaveMapData = "DCUSlaveMapData.Remarks";//TEXT

	//schema for TransformerMaster
	public static final String TABLE_TransformerMaster= "TransformerMaster";	
	public static final String COL_ID_TABLE_TransformerMaster = "TransformerMaster.ID";//TEXT
	public static final String COL_Name_TABLE_TransformerMaster = "TransformerMaster.Name";//TEXT


	//schema for survey
	public static final String TABLE_GPWaterSurvey= "GPWaterSurvey";
	public static final String COL_UID_TABLE_GPWaterSurvey = "GPWaterSurvey.UID";//INTEGER Primary Key
	public static final String COL_RRNo_TABLE_GPWaterSurvey = "GPWaterSurvey.RRNo";//TEXT
	public static final String COL_ConnectionId_TABLE_GPWaterSurvey = "GPWaterSurvey.ConnectionId";//TEXT
	public static final String COL_CustomerName_TABLE_GPWaterSurvey = "GPWaterSurvey.CustomerName";//TEXT		
	public static final String COL_MeterMake_TABLE_GPWaterSurvey = "GPWaterSurvey.MeterMake";//TEXT		
	public static final String COL_Remarks_TABLE_GPWaterSurvey = "GPWaterSurvey.Remarks";//TEXT
	public static final String COL_Lattitude_TABLE_GPWaterSurvey = "GPWaterSurvey.Lattitude";//TEXT
	public static final String COL_Longitude_TABLE_GPWaterSurvey = "GPWaterSurvey.Longitude";//TEXT
	public static final String COL_GPSFlag_TABLE_GPWaterSurvey = "GPWaterSurvey.GPSFlag";//TEXT
	public static final String COL_ImagePath_TABLE_GPWaterSurvey = "GPWaterSurvey.ImagePath";//TEXT
	public static final String COL_DateTime_TABLE_GPWaterSurvey = "GPWaterSurvey.DateTime";//TEXT		
	public static final String COL_MeterSlNo_TABLE_GPWaterSurvey = "GPWaterSurvey.MeterSlNo";//TEXT
	public static final String COL_YearofManufacture_TABLE_GPWaterSurvey = "GPWaterSurvey.YearofManufacture";//TEXT	
	public static final String COL_DistrictId_TABLE_GPWaterSurvey = "GPWaterSurvey.DistrictId";//TEXT
	public static final String COL_TalukId_TABLE_GPWaterSurvey = "GPWaterSurvey.TalukId";//TEXT
	public static final String COL_GPId_TABLE_GPWaterSurvey = "GPWaterSurvey.GPId";//TEXT
	public static final String COL_VillageId_TABLE_GPWaterSurvey = "GPWaterSurvey.VillageId";//TEXT
	public static final String COL_MeterStatus_TABLE_GPWaterSurvey = "GPWaterSurvey.MeterStatus";//TEXT
	public static final String COL_WaterSource_TABLE_GPWaterSurvey = "GPWaterSurvey.WaterSource";//TEXT
	public static final String COL_Borewell_TABLE_GPWaterSurvey = "GPWaterSurvey.Borewell";//TEXT
	public static final String COL_ConnectionStatus_TABLE_GPWaterSurvey = "GPWaterSurvey.ConnectionStatus";//TEXT
	public static final String COL_SancLoad_TABLE_GPWaterSurvey = "GPWaterSurvey.SancLoad";//TEXT
	public static final String COL_PumpCapacity_TABLE_GPWaterSurvey = "GPWaterSurvey.PumpCapacity";//TEXT
	public static final String COL_Depth_TABLE_GPWaterSurvey = "GPWaterSurvey.Depth";//TEXT
	public static final String COL_PipeDimension_TABLE_GPWaterSurvey = "GPWaterSurvey.PipeDimension";//TEXT
	public static final String COL_TypeOfSupply_TABLE_GPWaterSurvey = "GPWaterSurvey.TypeOfSupply";//TEXT
	public static final String COL_NoOfOutlets_TABLE_GPWaterSurvey = "GPWaterSurvey.NoOfOutlets";//TEXT
	public static final String COL_TankDimensionsLength_TABLE_GPWaterSurvey = "GPWaterSurvey.TankDimensionsLength";//TEXT
	public static final String COL_TankDimensionsDiameter_TABLE_GPWaterSurvey = "GPWaterSurvey.TankDimensionsDiameter";//TEXT
	public static final String COL_TankCapacity_TABLE_GPWaterSurvey = "GPWaterSurvey.TankCapacity";//TEXT
	public static final String COL_Distance_TABLE_GPWaterSurvey = "GPWaterSurvey.Distance";//TEXT
	public static final String COL_NSP_TABLE_GPWaterSurvey = "GPWaterSurvey.NSP";//TEXT
	
	////////////////NEW
	public static final String COL_MeterPhase_TABLE_GPWaterSurvey = "GPWaterSurvey.MeterPhase";//TEXT	
	public static final String COL_MeterType_TABLE_GPWaterSurvey = "GPWaterSurvey.MeterType";//TEXT	
	public static final String COL_PipeTypeBorewell_TABLE_GPWaterSurvey = "GPWaterSurvey.PipeTypeBorewell";//TEXT	
	public static final String COL_TankId_TABLE_GPWaterSurvey = "GPWaterSurvey.TankId";//TEXT
	public static final String COL_ControlValve_TABLE_GPWaterSurvey = "GPWaterSurvey.ControlValve";//TEXT
	public static final String COL_PipeTypeOutlet_TABLE_GPWaterSurvey = "GPWaterSurvey.PipeTypeOutlet";//TEXT
	public static final String COL_TypeOfSim_TABLE_GPWaterSurvey = "GPWaterSurvey.TypeOfSim";//TEXT
	public static final String COL_WaterManName_TABLE_GPWaterSurvey = "GPWaterSurvey.WaterManName";//TEXT
	public static final String COL_ContactNo_TABLE_GPWaterSurvey = "GPWaterSurvey.ContactNo";//TEXT
	
	public static final String COL_ImageName1_TABLE_GPWaterSurvey = "GPWaterSurvey.ImageName1";//TEXT
	public static final String COL_ImageName2_TABLE_GPWaterSurvey = "GPWaterSurvey.ImageName2";//TEXT
	public static final String COL_ImageName3_TABLE_GPWaterSurvey = "GPWaterSurvey.ImageName3";//TEXT
	public static final String COL_ImageName4_TABLE_GPWaterSurvey = "GPWaterSurvey.ImageName4";//TEXT
	
	public static final String COL_GPSFlagImageName1_TABLE_GPWaterSurvey = "GPWaterSurvey.GPSFlagImageName1";//TEXT
	public static final String COL_GPSFlagImageName2_TABLE_GPWaterSurvey = "GPWaterSurvey.GPSFlagImageName2";//TEXT
	public static final String COL_GPSFlagImageName3_TABLE_GPWaterSurvey = "GPWaterSurvey.GPSFlagImageName3";//TEXT
	public static final String COL_GPSFlagImageName4_TABLE_GPWaterSurvey = "GPWaterSurvey.GPSFlagImageName4";//TEXT
	////////////////End NEW
	
	public static final String COL_Param1_TABLE_GPWaterSurvey = "GPWaterSurvey.Param1";//TEXT
	public static final String COL_Param2_TABLE_GPWaterSurvey = "GPWaterSurvey.Param2";//TEXT
	public static final String COL_Param3_TABLE_GPWaterSurvey = "GPWaterSurvey.Param3";//TEXT
	public static final String COL_Param4_TABLE_GPWaterSurvey = "GPWaterSurvey.Param4";//TEXT
	public static final String COL_Param5_TABLE_GPWaterSurvey = "GPWaterSurvey.Param5";//TEXT
	public static final String COL_Param6_TABLE_GPWaterSurvey = "GPWaterSurvey.Param6";//TEXT
	public static final String COL_Param7_TABLE_GPWaterSurvey = "GPWaterSurvey.Param7";//TEXT
	public static final String COL_Param8_TABLE_GPWaterSurvey = "GPWaterSurvey.Param8";//TEXT
	public static final String COL_Param9_TABLE_GPWaterSurvey = "GPWaterSurvey.Param9";//TEXT
	public static final String COL_Param10_TABLE_GPWaterSurvey = "GPWaterSurvey.Param10";//TEXT	



	//schema for District
	public static final String TABLE_DistrictMaster= "DistrictMaster";
	public static final String COL_Id_DistrictMaster = "DistrictMaster.Id";//INTEGER Primary Key
	public static final String COL_Name_DistrictMaster = "DistrictMaster.Name";//TEXT
	public static final String COL_Desc_DistrictMaster = "DistrictMaster.Desc";//TEXT

	//schema for Taluk
	public static final String TABLE_TalukMaster= "TalukMaster";
	public static final String COL_Id_TalukMaster = "TalukMaster.Id";//INTEGER Primary Key
	public static final String COL_Name_TalukMaster = "TalukMaster.Name";//TEXT
	public static final String COL_Desc_TalukMaster = "TalukMaster.Desc";//TEXT



	//schema for GramPanchayat
	public static final String TABLE_GramPanchayatMaster= "GramPanchayatMaster";
	public static final String COL_Id_GramPanchayatMaster = "GramPanchayatMaster.Id";//INTEGER Primary Key
	public static final String COL_Name_GramPanchayatMaster = "GramPanchayatMaster.Name";//TEXT
	public static final String COL_Desc_GramPanchayatMaster = "GramPanchayatMaster.Desc";//TEXT

	//schema for Village master
	public static final String TABLE_VillageMaster= "VillageMaster";
	public static final String COL_Id_VillageMaster = "VillageMaster.Id";//INTEGER Primary Key
	public static final String COL_Name_VillageMaster = "VillageMaster.Name";//TEXT
	public static final String COL_Desc_VillageMaster = "VillageMaster.Desc";//TEXT

	//schema for Tank master
	public static final String TABLE_TankMaster= "TankMaster";
	public static final String COL_Id_TankMaster = "TankMaster.Id";//INTEGER Primary Key
	public static final String COL_Name_TankMaster = "TankMaster.Name";//TEXT
	public static final String COL_Desc_TankMaster = "TankMaster.Desc";//TEXT


	//schema for VillageMasterCapture
	/*public static final String TABLE_VillageMasterCapture= "VillageMasterCapture";
	public static final String COL_UID_VillageMaster = "VillageMaster.UID";//INTEGER Primary Key
	public static final String COL_DistrictId_VillageMasterCapture = "VillageMasterCapture.DistrictId";//INTEGER Primary Key
	public static final String COL_TalukId_VillageMasterCapture = "VillageMasterCapture.TalukId";//TEXT
	public static final String COL_GPId_VillageMasterCapture = "VillageMasterCapture.GPId";//TEXT
	public static final String COL_VillageName_VillageMasterCapture = "VillageMasterCapture.VillageName";//TEXT
	public static final String COL_GPSFlag_VillageMasterCapture = "VillageMasterCapture.GPSFlag";//TEXT
	 */




}
