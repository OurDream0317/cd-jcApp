package com.tuozhi.zhlw.common.base.mymapper;

/**
 * 消息码
 * 
 * @author yaominglei
 * 
 */
public class CdMessageCode {
	public static final int Success = 0;
	public static final int Exception = 1;
	public static final int NoData = 2;
	public static final int Session_Null = 3;
	//信息填写不完整
	public static final int Info_Incomplete = 4;
	public static final int NumberFormatException = 5;
	public static final int CompleteOrOnlyException = 6;
	public static final int LogintimeoutException = 7;
	public static final int NullPointerException  = 8;
	public static final int ClassCastException =9;
	public static final int ArrayIndexOutOfBoundsException =10;
	public static final int IOException=11;
	public static final int SQLException=12;
	public static final int EOFException=13;
	public static final int FileNotFoundException=14;
	public static final int IllegalArgumentException=15;
	public static final int IllegalAccessException=16;
	public static final int IdentifierGenerationException=17;
	public static final int UserNoPower = 18;
	public static final int LastMessage = 19;
	public static final int LoginOut = 20;
	public static final int TimeoutOperation = 21;

	public static final int OldPassWordNull = 22;
	public static final int NewPassWordNull = 23;
	public static final int AdminPassWordErr = 24;
	public static final int AdminPassWordNull = 25;
	public static final int DelFail = 26;
	public static final int SqlFail = 27;
	public static final int DataLarger = 28;
	public static final int SearchDataException = 29;
	

	// 系统管理-错误信息码 100000--109999
	public static final int Dept_UpdateFail = 100001;
	public static final int Dept_InsertFail = 100002;
	public static final int Dept_DeleteFail = 100003;
	public static final int Dept_NoDataByID = 100004;
	public static final int Dept_NoDataByName = 100005;
	public static final int IsDoubleRoleLogin = 120001;

	// 用户管理-错误信息码 110000--119999
	public static final int User_UpdateFail = 110001;
	public static final int User_InsertFail = 110002;
	public static final int User_DeleteFail = 110003;
	public static final int User_NoDataByID = 110004;
	public static final int User_ErrPassword = 110014;
	public static final int User_PasswordErrEq = 110015;
	public static final int User_PasswordNew_OldEq = 110016;

	// 排车排班-错误码 200000-299999
	public static final int Pai_Update = 200001;

	// 时刻表错误信息码310000-319999
	public static final int TimeTemplate_Update = 310001;
	public static final int TimeTemplate_InsertFail = 310002;
	public static final int TimeTemplate_DeleteFail = 310003;
	public static final int TimeTemplate_NoDataByID = 310004;
	
	// 菜单管理-错误信息码 400000--499999
	public static final int Func_UpdateFail = 400001;
	public static final int Func_InsertFail = 400002;
	public static final int Func_DeleteFail = 400003;
	public static final int Func_NoDataByID = 400004;
	public static final int Func_ID_Regist = 400010;
	
	//配车管理-错误信息码 500000--599999
	public static final int Schr_UpdateFail = 500001;
	public static final int Schr_InsertFail = 500002;
	public static final int Schr_DeleteFail = 500003;
	public static final int Schr_NoDataByID = 500004;
	public static final int Schr_Repeat  = 500005;
}
