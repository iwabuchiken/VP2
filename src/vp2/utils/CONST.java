package vp2.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class CONST {

	public static final String DIRPATH_ROOT = "vp";
	
	public static final String DIRPATH_MEDIA = 
				Environment.getExternalStorageDirectory().getPath()
				+ File.separator + DIRPATH_ROOT
				+ File.separator + "media";
			
	public static List<Long> srt_data = new ArrayList<Long>();
	
	/*********************************
	 * DB
	 *********************************/
	// Main data
	public static String[] cols_srt_data = 
		{
			"start_time", "end_time", "subtitle"
		};
//	"date_modified", "memos", "tags"};

	public static String[] col_types_srt_data =
		{
			"INTEGER",	"INTEGER",	"TEXT"
		};

	// Main data
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

	/*********************************
	 * DB: DB & Table names
	 *********************************/
	public static String dbname_main = "vp";
	
	public static String tname_main = "srt_data";
	
	/*********************************
	 * Option menues
	 *********************************/
	public static String opt_menu_main_1_clear_table = "Clear the table";
	
}//public class CONST
