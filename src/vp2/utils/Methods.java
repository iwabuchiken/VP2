package vp2.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import android.os.AsyncTask;
import android.os.Looper;

// Apache
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;

import vp2.listeners.dialog.DialogListener;

// REF=> http://commons.apache.org/net/download_net.cgi
//REF=> http://www.searchman.info/tips/2640.html

//import org.apache.commons.net.ftp.FTPReply;

public class Methods {

	static int counter;		// Used => sortFileList()
	
	
	/****************************************
	 * Enums
	 ****************************************/
	public static enum DialogTags {
		// Generics
		dlg_generic_dismiss, dlg_generic_dismiss_second_dialog, dlg_generic_dismiss_third_dialog,
		
		
		// dlg_create_folder.xml
		dlg_create_folder_ok, dlg_create_folder_cancel,

		// dlg_input_empty.xml
		dlg_input_empty_reenter, dlg_input_empty_cancel,
		
		// dlg_confirm_create_folder.xml
		dlg_confirm_create_folder_ok, dlg_confirm_create_folder_cancel,

		// dlg_confirm_remove_folder.xml
		dlg_confirm_remove_folder_ok, dlg_confirm_remove_folder_cancel,

		// dlg_drop_table.xml
		dlg_drop_table_btn_cancel, dlg_drop_table,
		
		// dlg_confirm_drop.xml
		dlg_confirm_drop_table_btn_ok, dlg_confirm_drop_table_btn_cancel,
		
		// dlg_add_memos.xml
		dlg_add_memos_bt_add, dlg_add_memos_bt_cancel, dlg_add_memos_bt_patterns,
		dlg_add_memos_gv,

		// dlg_move_files.xml
		dlg_move_files_move, dlg_move_files,
		
		// dlg_confirm_move_files.xml	=> ok, cancel, dlg tag
		dlg_confirm_move_files_ok, dlg_confirm_move_files_cancel, dlg_confirm_move_files,

		// dlg_item_menu.xml
		dlg_item_menu_bt_cancel, dlg_item_menu,

		// dlg_create_table.xml
		dlg_create_table_bt_create,

		// dlg_memo_patterns.xml
		dlg_memo_patterns,
		
		// dlg_register_patterns.xml
		dlg_register_patterns_register,

		// dlg_search.xml
		dlg_search, dlg_search_ok,

		// dlg_admin_patterns.xml

		// dlg_confirm_delete_patterns.xml
		dlg_confirm_delete_patterns_ok,
		
	}//public static enum DialogTags
	
	public static enum DialogItemTags {
		// dlg_moveFiles(Activity actv)
		dlg_move_files,
		
		// dlg_add_memos.xml
		dlg_add_memos_gv,

		// dlg_db_admin.xml
		dlg_db_admin_lv,

		// dlg_admin_patterns.xml
		dlg_admin_patterns_lv,

		// dlg_delete_patterns.xml
		dlg_delete_patterns_gv,
		
	}//public static enum DialogItemTags
	
	
	public static enum ButtonTags {
		// MainActivity.java
		ib_up,
		
		// DBAdminActivity.java
		db_manager_activity_create_table, db_manager_activity_drop_table, 
		db_manager_activity_register_patterns,
		
		// thumb_activity.xml
		thumb_activity_ib_back, thumb_activity_ib_bottom, thumb_activity_ib_top,
		
		// image_activity.xml
		image_activity_back, image_activity_prev, image_activity_next,
		
		// TIListAdapter.java
		tilist_cb,
		
	}//public static enum ButtonTags
	
	public static enum ItemTags {
		
		// MainActivity.java
		dir_list,
		
		// ThumbnailActivity.java
		dir_list_thumb_actv,
		
		// Methods.java
		dir_list_move_files,
		
		// TIListAdapter.java
		tilist_checkbox,
		
		
	}//public static enum ItemTags

	public static enum MoveMode {
		// TIListAdapter.java
		ON, OFF,
		
	}//public static enum MoveMode

	public static enum PrefenceLabels {
		
		CURRENT_PATH,
		
		thumb_actv,
		
		chosen_list_item,
		
	}//public static enum PrefenceLabels

	public static enum ListTags {
		// MainActivity.java
		actv_main_lv,
		
		// Main
		main_list_adapter,
		
	}//public static enum ListTags

	
	/****************************************
	 * Vars
	 ****************************************/
	public static final int vibLength_click = 35;

	static int tempRecordNum = 20;

	/****************************************
	 * Methods
	 ****************************************/
	public static void sortFileList(File[] files) {
		// REF=> http://android-coding.blogspot.jp/2011/10/sort-file-list-in-order-by-implementing.html
		/*----------------------------
		 * 1. Prep => Comparator
		 * 2. Sort
			----------------------------*/
		/*----------------------------
		 * 1. Prep => Comparator
			----------------------------*/
		Comparator<? super File> filecomparator = new Comparator<File>(){
			
			public int compare(File file1, File file2) {
				/*----------------------------
				 * 1. Prep => Directory
				 * 2. Calculate
				 * 3. Return
					----------------------------*/
				
				int pad1=0;
				int pad2=0;
				
				if(file1.isDirectory())pad1=-65536;
				if(file2.isDirectory())pad2=-65536;
				
				int res = pad2-pad1+file1.getName().compareToIgnoreCase(file2.getName());
				
				return res;
			} 
		 };//Comparator<? super File> filecomparator = new Comparator<File>()
		 
		/*----------------------------
		 * 2. Sort
			----------------------------*/
		Arrays.sort(files, filecomparator);

	}//public static void sortFileList(File[] files)

	public static void confirm_quit(Activity actv, int keyCode) {
		
		// TODO 自動生成されたメソッド・スタブ
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			
			AlertDialog.Builder dialog=new AlertDialog.Builder(actv);
			
	        dialog.setTitle("アプリの終了");
	        dialog.setMessage("アプリを終了しますか？");
	        
	        dialog.setPositiveButton("終了",new DialogListener(actv, dialog, 0));
	        dialog.setNegativeButton("キャンセル",new DialogListener(actv, dialog, 1));
	        
	        dialog.create();
	        dialog.show();
			
		}//if (keyCode==KeyEvent.KEYCODE_BACK)
		
	}//public static void confirm_quit(Activity actv, int keyCode)

//	private static boolean insertDataIntoDB(Activity actv,
	public static boolean insertDataIntoDB(Activity actv, String dbName,
			String tableName, String[] col_names, String[] values) {
		/*----------------------------
		* Steps
		* 1. Set up db
		* 2. Insert data
		* 3. Show message
		* 4. Close db
		----------------------------*/
		/*----------------------------
		* 1. Set up db
		----------------------------*/
		DBUtils dbu = new DBUtils(actv, dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		/*----------------------------
		* 2. Insert data
		----------------------------*/
		boolean result = dbu.insertData(wdb, tableName, col_names, values);
		
		/*----------------------------
		* 3. Show message
		----------------------------*/
		if (result == true) {
		
			if (Looper.myLooper() == Looper.getMainLooper()) {
				
				// debug
				Toast.makeText(actv, "Data stored", 2000).show();
				
			} else {//if (condition)

				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]",
						"Data stored");
				
			}//if (condition)

//			// debug
//			Toast.makeText(actv, "Data stored", 2000).show();
			
			/*----------------------------
			* 4. Close db
			----------------------------*/
			wdb.close();
			
			return true;
			
		} else {//if (result == true)
		
//			// debug
//			Toast.makeText(actv, "Store data => Failed", 200).show();
			
			if (Looper.myLooper() == Looper.getMainLooper()) {
				
				// debug
				Toast.makeText(actv, "Store data => Failed", 2000).show();
				
			} else {//if (condition)

				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]",
						"Store data => Failed");
				
			}//if (condition)
			
			/*----------------------------
			* 4. Close db
			----------------------------*/
			wdb.close();
			
			return false;
		
		}//if (result == true)
		
		/*----------------------------
		* 4. Close db
		----------------------------*/
	
	}//private static int insertDataIntoDB()

	
	public static String convert_milsec_to_srt_digits(Long milsec) {
		// milsec part
		long mil = milsec % 1000;
		
		long sec = (milsec - mil) / 1000;
		
		String sec_s = Methods.convert_sec_to_srt_digits(sec);
		
//		return sec_s + "," + String.valueOf(mil);
		return sec_s + "," + Methods.convert_sec2digits(mil, 3);
		
//		return sec + " seconds " + mil + " mil sec";
	}//public static String convert_milsec_to_srt_digits(Long long1)

	private static String convert_sec_to_srt_digits(long t) {
		
		long sec = t % 60;
		
		if (t / 60 < 1) {
			
//			return "00:00:" + String.valueOf(sec);
			return "00:00:" + Methods.convert_sec2digits(sec, 2);
			
		}//if (t / 60 < 1)
		
//		long min = (t - sec) % 60;
		long min = ((t - sec) % (60 * 60)) / 60;
		
		if ((t - sec) / (60 * 60) < 1) {
			
//			return "00:" + String.valueOf(min) + ":" + String.valueOf(sec);
			return "00:"
				+ Methods.convert_sec2digits(min, 2) + ":"
				+ Methods.convert_sec2digits(sec, 2);
			
		}//if (variable == condition)
		
//		long hour = (t - min) / 60;
		long hour = (t - sec) / (60 * 60);
				
//		return String.valueOf(hour) + ":"
//				+ String.valueOf(min) + ":"
//				+ String.valueOf(sec);

		return Methods.convert_sec2digits(min, 2) + ":"
		+ Methods.convert_sec2digits(min, 2) + ":"
		+ Methods.convert_sec2digits(sec, 2);

	}//private static String convert_sec2digits(long t)

	private static String convert_sec2digits(long sec, int i) {
		
		int current_len = String.valueOf(sec).length();
		
		if (current_len < i) {
			
			StringBuilder sb = new StringBuilder();
			
			for (int j = 0; j < i - current_len; j++) {
				
				sb.append("0");
			}
			
			sb.append(String.valueOf(sec));
			
			return sb.toString();
			
		}//if (current_len == condition)
		
		return String.valueOf(sec);
		
	}//private static String convert_sec2digits(long sec, int i)


	public static String convert_milsec_to_digits(int currentPosition) {
		
		long mil = currentPosition % 1000;
		
		long sec = (currentPosition - mil) / 1000;
		
		return Methods.convert_sec_to_srt_digits(sec);
		
	}//public static String convert_milsec_to_digits(int currentPosition)

	public static String convert_milsec_to_digits(long currentPosition) {
		
		long mil = currentPosition % 1000;
		
		long sec = (currentPosition - mil) / 1000;
		
		return Methods.convert_sec_to_srt_digits(sec);
		
	}//public static String convert_milsec_to_digits(int currentPosition)

	public static long getMillSeconds_now() {
		
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime().getTime();
		
	}//private long getMillSeconds_now(int year, int month, int date)
	
}//public class Methods

