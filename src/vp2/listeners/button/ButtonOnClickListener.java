package vp2.listeners.button;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.commons.lang.StringUtils;

import vp2.adapters.SRTListAdapter;
import vp2.main.MainActv;
import vp2.main.PlayActv;
import vp2.main.R;
import vp2.utils.CONST;
import vp2.utils.DBUtils;
import vp2.utils.Methods;
import vp2.utils.Methods_VP2;
import vp2.utils.Tags;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ButtonOnClickListener implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	//
	int position;
	
	//
	ListView lv;
	
	public ButtonOnClickListener(Activity actv) {
		//
		this.actv = actv;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public ButtonOnClickListener(Activity actv, int position) {
		//
		this.actv = actv;
		this.position = position;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
		
		
	}//public ButtonOnClickListener(Activity actv, int position)

	public ButtonOnClickListener(Activity actv, ListView lv) {
		// 
		this.actv = actv;
		this.lv = lv;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

//	@Override
	public void onClick(View v) {
//		//
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
//
		vib.vibrate(Methods.vibLength_click);
		
		//
		switch (tag) {
			
		case actv_main_bt_export:
			
			case_actv_main_bt_export();
			
			break;

		case actv_main_ib_start:
			
			case_actv_main_ib_start();
			
			break;

		case actv_main_bt_end:
			
			case_actv_main_bt_end();
			
			break;
			
		default:
			break;
			
		}//switch (tag)
		
	}//public void onClick(View v)

	private void case_actv_main_bt_export() {
	
		String fname_dst = "test.srt";
		
		String fpath_dst = StringUtils.join(
				new String[]{
					CONST.DIRPATH_MEDIA,
					fname_dst
				},
				File.separator
		);//String dpath_media

		Methods_VP2.export_srt(actv, CONST.srt_data, fpath_dst);
		
	}//private void case_actv_main_bt_export()

	private void case_actv_main_bt_end() {

		if (PlayActv.vvPlayer != null) {
			
			CONST.srt_data.add((long) PlayActv.vvPlayer.getCurrentPosition());
			
			// Log
			Log.d("ButtonOnClickListener.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]",
					"Stored => " + PlayActv.vvPlayer.getCurrentPosition());
			
		} else {//if (PlayActv.vvPlayer != null)
			
			CONST.srt_data.add((long) -1);
			
			// Log
			Log.d("ButtonOnClickListener.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]",
					"Stored => " + -1);
			
		}//if (PlayActv.vvPlayer != null)
	
	}//private void case_actv_main_bt_end()

	private void case_actv_main_ib_start() {
		
		// Log
		Log.d("ButtonOnClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "Image button");
		
//		case_actv_main_bt_start_B4_v_1_0();
	
	}//private void case_actv_main_bt_start()

	private void case_actv_main_bt_start_B4_v_1_0() {
		
		if (PlayActv.vvPlayer != null) {
			
//			CONST.srt_data.add((long) PlayActv.vvPlayer.getCurrentPosition());
			
			DBUtils dbu = new DBUtils(actv, CONST.dbname_main);
			
			//
			SQLiteDatabase wdb = dbu.getWritableDatabase();

			DBUtils.insert_data_start_position(
					wdb,
					CONST.tname_main,
					CONST.cols_srt_data[0],
					String.valueOf(PlayActv.vvPlayer.getCurrentPosition()));
			
			// Log
			Log.d("ButtonOnClickListener.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]",
					"Stored => " + PlayActv.vvPlayer.getCurrentPosition());
			
			wdb.close();
			
			/*********************************
			 * Refresh the list view
			 *********************************/
			// Log
			Log.d("ButtonOnClickListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Refreshing the list view ...");
			
			if (PlayActv.srt_list != null) {
				
				PlayActv.srt_list.clear();
				
			}//if (PlayActv.srt_list == condition)
//			PlayActv.srt_list.clear();
			
			// Log
			Log.d("ButtonOnClickListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "srt_list => Cleared");
			
//			PlayActv.srt_list = Methods_vp2.get_srt_list_from_db(actv);
			
			if (PlayActv.srt_list != null) {

				PlayActv.srt_list.addAll(Methods_VP2.get_srt_list_from_db(actv));
				
				// Log
				Log.d("ButtonOnClickListener.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "PlayActv.srt_list.size()=" + PlayActv.srt_list.size());
				
				Methods_VP2.sort_list_start_time(PlayActv.srt_list);
				
				// Log
				Log.d("ButtonOnClickListener.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "List => Sorted");
				
				PlayActv.aAdapter.notifyDataSetChanged();

			} else {//if (condition)

				PlayActv.srt_list = Methods_VP2.get_srt_list_from_db(actv);
				
				Methods_VP2.sort_list_start_time(PlayActv.srt_list);
				
				PlayActv.aAdapter = new SRTListAdapter(
						actv,
//						R.layout.activity_main_actv_vv,
						R.layout.activity_play_actv_vv,
						PlayActv.srt_list
				);

				PlayActv.lv_srt_items.setAdapter(PlayActv.aAdapter);
				
			}//if (condition)
			
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
			
			CONST.srt_data.add((long) -1);
			
			// Log
			Log.d("ButtonOnClickListener.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]",
					"Stored => " + -1);
			
		}//if (PlayActv.vvPlayer != null)

	}//private void case_actv_main_bt_start_B4_v_1_0()

}//public class ButtonOnClickListener implements OnClickListener
