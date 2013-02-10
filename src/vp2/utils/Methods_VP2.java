package vp2.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

import vp2.adapters.SRTListAdapter;
import vp2.items.BI;
import vp2.items.SRTItem;
import vp2.listeners.dialog.DialogListener;
import vp2.listeners.dialog.DialogOnItemClickListener;
import vp2.main.MainActv;
import vp2.main.PlayActv;
import vp2.main.R;

// REF=> http://commons.apache.org/net/download_net.cgi
//REF=> http://www.searchman.info/tips/2640.html

//import org.apache.commons.net.ftp.FTPReply;

public class Methods_VP2 {
	/****************************************
	 * Methods
	 ****************************************/

	public static void export_srt(Activity actv, List<Long> srt_data,
			String fpath_dst) {

		try {
			
			BufferedWriter bw = new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(fpath_dst)
			));
			
			for (int i = 0; i < CONST.srt_data.size(); i++) {
				
				bw.write(i + 1 + "\n");
				
				bw.write(Methods.convert_milsec_to_srt_digits(CONST.srt_data.get(i)));
				
				bw.write(" --> ");
				
//				if (i + 1 < CONST.srt_data.size() - 1) {
				if (i < CONST.srt_data.size() - 1) {
					
					bw.write(Methods.convert_milsec_to_srt_digits(CONST.srt_data.get(i+1) - 100));
					
				}//if (variable == condition)
				
				bw.write("\n\n");
				
//				bw.write(String.valueOf(CONST.srt_data.get(i)) + "/");
				
			}
			
			bw.flush();
			
			bw.close();
//			output.write(CONST.srt_data.get(i));
			
			// Log
			Log.d("ButtonOnClickListener.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "bw => Closed");

			
		} catch (FileNotFoundException e) {
			
			// Log
			Log.d("ButtonOnClickListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
		} catch (IOException e) {

			// Log
			Log.d("ButtonOnClickListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

		}//try

	}//public static void export_srt

	
	public static List<SRTItem> get_srt_list_from_db(Activity actv) {
		
		DBUtils dbu = new DBUtils(actv, CONST.dbname_main);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		// Table name
		String tname = CONST.tname_main;
		
		/*----------------------------
		 * 0. Table exists?
			----------------------------*/
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "tableName=" + tname);
		
		boolean res = dbu.tableExists(wdb, tname);
		
		if (res == false) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "getAllData() => Table doesn't exist: " + tname);
			
			wdb.close();
			
			return null;
			
		}//if (res == false)

		// Query
		//
		String sql = "SELECT * FROM " + tname;
		
		Cursor c = null;
		
		try {
			
			c = wdb.rawQuery(sql, null);
			
			actv.startManagingCursor(c);
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c.getCount()=" + c.getCount());
			
		} catch (Exception e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			wdb.close();
			
			return null;
		}

		// If no record, then return null
		if (c.getCount() < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c.getCount() < 1");
			
			wdb.close();
			
			return null;
			
		}//if (c.getCount() == condition)
		
		// Construct a list
		List<SRTItem> srt_list = new ArrayList<SRTItem>();
		
		c.moveToFirst();
		
		for (int i = 0; i < c.getCount(); i++) {
			
			SRTItem item = new SRTItem(
					c.getLong(3),
					c.getLong(4),
					c.getString(5),
					c.getLong(0)
			);
			
			srt_list.add(item);
			
			c.moveToNext();
		}
		
		// Close db
		wdb.close();
		
		return srt_list;
	}//public static List<SRTItem> get_srt_list_from_db(Activity actv)


	
	public static void sort_list_start_time(List<SRTItem> srt_list) {
		
		Collections.sort(srt_list, new Comparator<SRTItem>(){

//			@Override
			public int compare(SRTItem i1, SRTItem i2) {
				/*********************************
				 * memo
				 *********************************/
				long t1 = i1.getStart_time();
				long t2 = i2.getStart_time();
				
				return (int)(t1 - t2);
					

			}//public int compare(TI lti, TI rti)
			
		});//Collections.sort()

		
	}//public static void sort_list_start_time(List<SRTItem> srt_list)


	
	public static void clear_table_main(Activity actv) {

		/*********************************
		 * #Setup the db,
		 * #Drop the table,
		 * #Create a new one with the same same,
		 * #Close the db,
		 * #Clear the list,
		 * #Notify the adapter of the change
		 *********************************/
		//Setup the db,
		DBUtils dbu = new DBUtils(actv, CONST.dbname_main);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		//Drop the table,
		dbu.dropTable(actv, wdb, CONST.tname_main);
		
		//Create a new one with the same same,
		dbu.createTable(wdb,
				CONST.tname_main,
				CONST.cols_srt_data,
				CONST.col_types_srt_data);

		//Close the db,
		wdb.close();

		//Clear the list,
		if (PlayActv.srt_list != null) {
		
			//Clear the list,
			PlayActv.srt_list.clear();
			
		} else {//if (PlayActv.srt_list != null)
			
			// Log
			Log.d("Methods_VP2.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "PlayActv.srt_list == null");
			
		}//if (PlayActv.srt_list != null)
		
		if (PlayActv.aAdapter != null) {
			
			//Notify the adapter of the change
			PlayActv.aAdapter.notifyDataSetChanged();
			
		} else {//if (PlayActv.srt_list != null)
			
			// Log
			Log.d("Methods_VP2.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "PlayActv.aAdapter == null");
			
		}//if (PlayActv.srt_list != null)
		
	}//public static void clear_table_main(Activity actv)


	
	public static String convertItemName2TableName(Activity actv,
			String itemName) {
		
		String[] tokens = itemName.split("\\.");
		
		// Log
		Log.d("Methods_VP2.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "tokens.length=" + tokens.length);
		
		if (tokens.length < 2) {
			
			return itemName.replace(" ", "_");
			
		}//if (tokens.length == condition)
		
		
		
//		String trunk = Methods.joinArray(tokens, 0, tokens.length - 1);
		String trunk = Methods.joinArray(tokens, 0, tokens.length - 2);
		
		return trunk.replace(" ", "_");
		
	}//public static String convertItemName2TableName(



	public static List<BI> getBookmarkList(Activity actv, String tableName) {
		// TODO Auto-generated method stub
		DBUtils dbu = new DBUtils(actv, CONST.dbname_main);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		// Table name
		String tname = tableName;
		
		/*----------------------------
		 * 0. Table exists?
			----------------------------*/
		// Log
		Log.d("Methods_VP2.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "tableName=" + tname);
		
		boolean res = dbu.tableExists(wdb, tname);
		
		if (res == false) {
			
			// Log
			Log.d("Methods_VP2.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "getAllData() => Table doesn't exist: " + tname);
			

			dbu.createTable(
					wdb,
					tableName,
					CONST.cols_bookmarkData,
					CONST.colTypes_bookmarkData);
			
			wdb.close();
			
			return null;
			
		}//if (res == false)

		// Query
		//
		String sql = "SELECT * FROM " + tname;
		
		Cursor c = null;
		
		try {
			
			c = wdb.rawQuery(sql, null);
			
//			actv.startManagingCursor(c);
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c.getCount()=" + c.getCount());
			
		} catch (Exception e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			wdb.close();
			
			return null;
		}

		// If no record, then return null
		if (c.getCount() < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c.getCount() < 1");
			
			wdb.close();
			
			return null;
			
		}//if (c.getCount() == condition)
		
		// Construct a list
		List<BI> bookmarkList = new ArrayList<BI>();
		
		c.moveToFirst();
		
		for (int i = 0; i < c.getCount(); i++) {
			
			BI item = new BI(
					c.getLong(2 + Methods.getArrayIndex(
									CONST.cols_bookmarkData,
									"startTime")),
					c.getLong(2 + Methods.getArrayIndex(
									CONST.cols_bookmarkData,
									"endTime")),
					c.getString(2 + Methods.getArrayIndex(
							CONST.cols_bookmarkData,
							"title")),
					c.getString(2 + Methods.getArrayIndex(
							CONST.cols_bookmarkData,
							"memo")),
					c.getLong(0)	// database id
			);
			
			bookmarkList.add(item);
			
			c.moveToNext();
		}
		
		// Close db
		wdb.close();
		
		return bookmarkList;
		
	}//public static List<BI> getBookmarkList(Activity actv, String tableName)


	
	public static void sortBookmark_startTime(List<BI> bookmarkList) {
		// TODO Auto-generated method stub
		
		Collections.sort(bookmarkList, new Comparator<BI>(){

//			@Override
			public int compare(BI i1, BI i2) {
				/*********************************
				 * memo
				 *********************************/
				long t1 = i1.getStartTime();
				long t2 = i2.getStartTime();
				
				return (int)(t1 - t2);
					

			}//public int compare(TI lti, TI rti)
			
		});//Collections.sort()
		
	}//public static void sortBookmark_startTime(List<BI> bookmarkList)


	
	public static void showDlg_Admin(Activity actv) {
		// TODO Auto-generated method stub
		Dialog dlg = Methods_dlg.dlg_template_cancel(
				actv, R.layout.dlg_simple_list, 
				R.string.dlg_admin_title, 
				R.id.dlg_simple_list_bt_cancel, 
				Tags.DialogTags.dlg_generic_dismiss);
		
		String[] choices = {
				actv.getString(R.string.dlg_admin_item_backup_db),
		};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
			
			list.add(item);
			
		}
		
		/*----------------------------
		 * 3. Adapter
			----------------------------*/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				actv,
//				R.layout.dlg_db_admin,
				android.R.layout.simple_list_item_1,
				list
				);

		/*----------------------------
		 * 4. Set adapter
			----------------------------*/
		ListView lv = (ListView) dlg.findViewById(R.id.dlg_simple_list_lv);
		
		lv.setAdapter(adapter);
		
		/*----------------------------
		 * 5. Set listener to list
			----------------------------*/
		lv.setTag(Tags.DialogTags.dlg_admin);
		
		lv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg));
		
		/*----------------------------
		 * 6. Show dialog
			----------------------------*/
		dlg.show();

	}//public static void showDlg_Admin(Activity actv)


	
	public static boolean saveBookmark(Activity actv,
						String tableName, long curPosition) {
		
		// Log
		Log.d("Methods_VP2.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "tableName=" + tableName);
		
		
		if (PlayActv.vvPlayer != null) {
			
			DBUtils dbu = new DBUtils(actv, CONST.dbname_main);
			
			//
			SQLiteDatabase wdb = dbu.getWritableDatabase();

			// Log
			Log.d("Methods_VP2.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Calling => DBUtils.insertData_StartPosition()");
			
			long res = DBUtils.insertData_StartPosition(
					wdb,
//					CONST.tname_main,
					tableName,
					CONST.cols_bookmarkData[Methods.getArrayIndex(
									CONST.cols_bookmarkData,
									"startTime")],
					String.valueOf(curPosition));
			
			if (res >= 0) {

				// Log
				Log.d("ButtonOnClickListener.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]",
						"Stored: Current position=" + curPosition
						+ "/"
						+ "DB id=" + res);

			} else {//if (res >= 0)

				// Log
				Log.d("Methods_VP2.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ ":"
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]",
						"DB insertin failed: Position=" + curPosition);
				
			}//if (res >= 0)
			
//			// Log
//			Log.d("ButtonOnClickListener.java"
//					+ "["
//					+ Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + "]",
//					"Stored => " + PlayActv.vvPlayer.getCurrentPosition());
			
			wdb.close();
			
			/*********************************
			 * Refresh the list view
			 *********************************/
//			// Log
//			Log.d("ButtonOnClickListener.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Refreshing the list view ...");
//			
//			if (PlayActv.srt_list != null) {
//				
//				PlayActv.srt_list.clear();
//				
//			}//if (PlayActv.srt_list == condition)
////			PlayActv.srt_list.clear();
//			
//			// Log
//			Log.d("ButtonOnClickListener.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "srt_list => Cleared");
//			
////			PlayActv.srt_list = Methods_vp2.get_srt_list_from_db(actv);
//			
//			if (PlayActv.srt_list != null) {
//
//				PlayActv.srt_list.addAll(Methods_VP2.get_srt_list_from_db(actv));
//				
//				// Log
//				Log.d("ButtonOnClickListener.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "PlayActv.srt_list.size()=" + PlayActv.srt_list.size());
//				
//				Methods_VP2.sort_list_start_time(PlayActv.srt_list);
//				
//				// Log
//				Log.d("ButtonOnClickListener.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "List => Sorted");
//				
//				PlayActv.aAdapter.notifyDataSetChanged();
//
//			} else {//if (condition)
//
//				PlayActv.srt_list = Methods_VP2.get_srt_list_from_db(actv);
//				
//				Methods_VP2.sort_list_start_time(PlayActv.srt_list);
//				
//				PlayActv.aAdapter = new SRTListAdapter(
//						actv,
////						R.layout.activity_main_actv_vv,
//						R.layout.activity_play_actv_vv,
//						PlayActv.srt_list
//				);
//
//				PlayActv.lv_srt_items.setAdapter(PlayActv.aAdapter);
//				
//			}//if (condition)
			
//			PlayActv.srt_list.addAll(Methods_VP2.get_srt_list_from_db(actv));
//			
//			// Log
//			Log.d("ButtonOnClickListener.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "PlayActv.srt_list.size()=" + PlayActv.srt_list.size());
//			
//			Methods_VP2.sort_list_start_time(PlayActv.srt_list);
//			
//			// Log
//			Log.d("ButtonOnClickListener.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "List => Sorted");
//			
//			PlayActv.aAdapter.notifyDataSetChanged();
//			
			
		} else {//if (PlayActv.vvPlayer != null)
			
			// Log
			Log.d("Methods_VP2.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "PlayActv.vvPlayer == null");
			
//			CONST.srt_data.add((long) -1);
//			
//			// Log
//			Log.d("ButtonOnClickListener.java"
//					+ "["
//					+ Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + "]",
//					"Stored => " + -1);
			
		}//if (PlayActv.vvPlayer != null)

		return false;
	}//public static boolean saveBookmark()

	
}//public class Methods

