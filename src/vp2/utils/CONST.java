package vp2.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vp2.items.BI;
import vp2.items.SRTItem;

import android.os.Environment;

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
			
	public static List<Long> srt_data = new ArrayList<Long>();
	
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

	public static String[] cols_bookmarkData = 
		{
			// Column number
			// 3			4			5
			"startTime", "endTime",		"title", "memo"
		};
//	"date_modified", "memos", "tags"};

	public static String[] colTypes_bookmarkData =
		{
			"INTEGER",		"INTEGER",	"TEXT",	"TEXT"
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
	
}//public class CONST
