package vp2.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vp2.adapters.BIAdapter;
import vp2.adapters.SRTListAdapter;
import vp2.items.BI;
import vp2.items.SRTItem;

import android.os.Environment;
import android.widget.ListView;

public class CONST {

	/*********************************
	 * Path
	 *********************************/
	
//	public static final String DIRPATH_ROOT = "vp";
	public static final String DIRPATH_ROOT = "vp2";
	
	public static final String DIRPATH_media_ROOT = "media";
	
	public static final String DIRPATH_MEDIA = 
				Environment.getExternalStorageDirectory().getPath()
				+ File.separator + DIRPATH_ROOT
				+ File.separator + DIRPATH_media_ROOT;
	
	public static final String DIRPATH_backupRoot = "bk";

	public static final String DIRPATH_BK = 
			Environment.getExternalStorageDirectory().getPath()
			+ File.separator + DIRPATH_ROOT
			+ File.separator + DIRPATH_backupRoot;

	public static List<Long> srt_data = new ArrayList<Long>();
	
	public static String dirPath_db = "/data/data/vp2.main/databases";
	
	public static String dbBackupTrunk = "vp2_backup";
	
//	public static String fileName_db_backup_ext = ".bk";
	public static String dbBackupExt = ".bk";
	
	public static String dirPath_db_backup = 
			Environment.getExternalStorageDirectory().getPath()
			+ "/VP2_backup";
	
	
	/*********************************
	 * DB: Columns
	 *********************************/
	// Main data
	public static String[] cols_srt_data = 
		{
			// Column number
			// 3			4			5
			"start_time", "end_time", "subtitle"
		};
//	"date_modified", "memos", "tags"};

	public static String[] col_types_srt_data =
		{
			"INTEGER",		"INTEGER",	"TEXT"
		};

	public static String[] cols_srt_data_full = 
		{
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"start_time", "end_time", "subtitle"	// 3,4,5
		};
//	"date_modified", "memos", "tags"};

	public static String[] col_types_srt_data_full =
		{
			"INTEGER",
			"INTEGER",	"INTEGER",
			"INTEGER",	"INTEGER",	"TEXT"
		};
	
	public static final String[] cols_bookmarkData = 
		{
			// Column number
			// 3			4			5
			"startTime", "endTime",		"title", "memo"
		};
//	"date_modified", "memos", "tags"};

	public static final String[] colTypes_bookmarkData =
		{
			"INTEGER",		"INTEGER",	"TEXT",	"TEXT"
		};

	public static final String[] cols_basic = {
		
		android.provider.BaseColumns._ID,		// 0
		"created_at", "modified_at",			// 1,2
		
	};
	
	public static String[] colTypes_basic = {
		
		"INTEGER",
		"INTEGER",	"INTEGER",
		
	};
	
	/*********************************
	 * DB: DB & Table names
	 *********************************/
	public static String dbname_main = "vp";
	
	public static String tname_main = "srt_data";
	
	
	
	/*********************************
	 * Option menues
	 *********************************/
	public static String opt_menu_main_1_clear_table = "Clear the table";
	
	/*********************************
	 * Intent-related
	 *********************************/
//	public static String intentMainActv_fileName = "intentMainActv_fileName";
	
	public static enum intent {
		mainActv_fileName
	}
	
	/*********************************
	 * List-related
	 *********************************/
	public static List<BI> bookmarkList = null;

	public static BIAdapter biAdapter = null;
	
	/*********************************
	 * Views
	 *********************************/
	public static ListView lv_bookMarks;

	/*********************************
	 * Constant values
	 *********************************/
	public static final long EXCEPTION_DbInsertion = -2;
}//public class CONST
