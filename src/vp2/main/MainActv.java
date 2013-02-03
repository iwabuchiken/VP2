package vp2.main;
	
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.lang.StringUtils;

import vp2.adapters.MainListAdapter;
import vp2.adapters.SRTListAdapter;
import vp2.items.SRTItem;
import vp2.listeners.button.ButtonOnClickListener;
import vp2.listeners.button.ButtonOnTouchListener;
import vp2.listeners.CustomOnPreparedListener;
import vp2.listeners.list.ListOnItemClickListener;
import vp2.main.R;
import vp2.tasks.Task_Progress;
import vp2.utils.CONST;
import vp2.utils.DBUtils;
import vp2.utils.Methods;
import vp2.utils.Methods_VP2;
import vp2.utils.Tags;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
	
public class MainActv extends ListActivity {
//public class MainActv extends Activity {

	private static final String TAG = "VideoPlayer";

	String[] listFiles;

	public static MainListAdapter mainListAdp = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onCreate()");

		this.setTitle(this.getClass().getName());

//		setContentView(R.layout.activity_play_actv_vv);
	
		B8_v_1_1b();
		
//		B8_v_1_1();
		
	}//public void onCreate(Bundle savedInstanceState)
	
	
	
	private void B8_v_1_1b() {
		// TODO Auto-generated method stub
		setContentView(R.layout.actv_main);

		//debug
		B8_v_1_1_setup_Layouts();
		
		B8_v_1_1_setup_media_dir();
//		
		getFilesList();
		
		setFilesList2ListView();
		
	}//private void B8_v_1_1b()



	private void setFilesList2ListView() {
		// TODO Auto-generated method stub
//		ListView lv = (ListView) findViewById(R.id.actv_main_lv);
		
		mainListAdp = new MainListAdapter(
				this,
				R.layout.listrow_actv_main,
//				fileList()
				listFiles
				);
		
		this.setListAdapter(mainListAdp);
//		lv.setAdapter(mainListAdp);

	}



	private void B8_v_1_1() {
		// TODO Auto-generated method stub
		setContentView(R.layout.actv_main);

		//debug
//		B8_v_1_1_deleteMedirDir();
		
//		B8_v_1_1_deleteRootDir();
		
//		B8_v_1_1_validateRootDir();
		
		B8_v_1_1_setup_Layouts();
		
		B8_v_1_1_setup_media_dir();
//		
		B8_v_1_1_get_files_list();
		
	}//private void B8_v_1_1()
	



	@SuppressLint("NewApi")
	private void B8_v_1_1_setup_Layouts() {
		// TODO Auto-generated method stub
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		
		int width = 0;
		int height = 0;
		
		int sdkVersion = Build.VERSION.SDK_INT;
		
		if (sdkVersion >= 13) {

			display.getSize(size);
			
			width = size.x;
			height = size.y;

		} else {//if (sdkVersion >= 13)
			
			width = display.getWidth();
			
			height = display.getHeight();
			
		}//if (sdkVersion >= 13)
		
//		display.getSize(size);
		
//		width = size.x;
//		height = size.y;
		
		LinearLayout llListView = (LinearLayout) findViewById(R.id.actv_main_ll_listview);
		
		llListView.setLayoutParams(new LinearLayout.LayoutParams((width / 4) * 3, height));
		
		
	}



	private void B8_v_1_1_validateRootDir() {
		// TODO Auto-generated method stub
		File dirRoot = new File(
				Environment.getExternalStorageDirectory().getPath()
				+ File.separator + CONST.DIRPATH_ROOT);
		
		if (dirRoot.exists()) {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Root dir => exists");

		} else {//if (dirRoot.exists())
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Root dir => Doesn't exist");
			
		}//if (dpath_media.exists())
		
	}



	private void B8_v_1_1_deleteRootDir() {
		// TODO Auto-generated method stub
		File dirRoot = new File(
				Environment.getExternalStorageDirectory().getPath()
				+ File.separator + CONST.DIRPATH_ROOT);
		
		if (dirRoot.exists()) {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Media dir => exists");

			try {
				FileDeleteStrategy.FORCE.delete(dirRoot);
				
				// Log
				Log.d("MainActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Dir deleted => " + dirRoot.getAbsolutePath());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// Log
				Log.d("MainActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Exc =>" + e.toString());
			}

		} else {//if (dirRoot.exists())
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Root dir => Doesn't exist");
			
		}//if (dpath_media.exists())
		
	}



	private void B8_v_1_1_deleteMedirDir() {
		// TODO Auto-generated method stub
		//debug
		File dpath_media = new File(CONST.DIRPATH_MEDIA);
		
		try {
			FileDeleteStrategy.FORCE.delete(dpath_media);
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir deleted => " + dpath_media.getAbsolutePath());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exc =>" + e.toString());
		}
	}



	private void B8_v_1_1_get_files_list() {
		// TODO Auto-generated method stub
		File dirMedia = new File(CONST.DIRPATH_MEDIA);
		
//		String[] listFiles = dirMedia.list(new FilenameFilter(){
		listFiles = dirMedia.list(new FilenameFilter(){

			@Override
			public boolean accept(File file, String name) {
				// TODO Auto-generated method stub
				if (name.matches(".*\\.mp4")) {
					
					return true;
					
				} else {//if (name.matches(""))
					
					return false;
					
				}//if (name.matches(""))
				
				
				
			}//public boolean accept(File file, String name)
			
		});//String[] listFiles
		
		//debug
		if (listFiles != null) {

			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "listFiles.length=" + listFiles.length);
			for (String name : listFiles) {
				
				// Log
				Log.d("MainActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "name=" + name);

			}//for (String name : listFiles)
			
		} else {//if (listFiles != null)

			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "listFiles => null");
			
		}//if (listFiles != null)

		
		
		
	}//private void B8_v_1_1_get_files_list()



	private void B8_v_1_1_setup_media_dir() {
		// TODO Auto-generated method stub
		File dpath_media = new File(CONST.DIRPATH_MEDIA);
		
		
		
		if (dpath_media.exists()) {
	
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Media dir => exists");
			
		} else {//if (dpath_media.exists())
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Media dir => Doesn't exist");
			
			try {
				
				dpath_media.mkdirs();
				
				// Log
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]",
						"dpath_media => Created: " + dpath_media.getAbsolutePath());
				
			} catch (Exception e) {
				
				// Log
				Log.e("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Exception: " + e.toString());
			}//try
			
		}//if (dpath_media.exists())
	}



	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	//	        getMenuInflater().inflate(R.menu.activity_main_actv, menu);
		
		//REF http://stackoverflow.com/questions/3993924/android-api-level
		if (android.os.Build.VERSION.SDK_INT >= 11) {

			// B5 v-2.1
//			MenuItem item1 = menu.add(opt_menu_labels[0]);
			MenuItem item1 = menu.add(CONST.opt_menu_main_1_clear_table);
	        // アイコンを設定
//	        item1.setIcon(android.R.drawable.ic_menu_help);
			item1.setIcon(android.R.drawable.ic_menu_call);
	 
	        
	        
	        // SHOW_AS_ACTION_ALWAYS:常に表示
	        item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		}//if (android.os.Build.VERSION.SDK_INT == )
		
	    return true;
	}
	
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		// debug
		int id = item.getItemId();
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "id=" + id);
		
//		if (id != ) {
//			Toast.makeText(this, item.getItemId(), Toast.LENGTH_SHORT).show();
//		}//if (id)
		return super.onMenuItemSelected(featureId, item);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		// debug
//		Toast.makeText(this, item.getItemId(), Toast.LENGTH_SHORT).show();
//		Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
		
		// B5 v-2.1
		if (item.getTitle().equals(CONST.opt_menu_main_1_clear_table)) {
			
			Methods_VP2.clear_table_main(this);
			
//			// debug
//			Toast.makeText(this,
//					CONST.opt_menu_main_1_clear_table, Toast.LENGTH_SHORT).show();
			
		} else {//if (item.getTitle().equals(CONST.opt_menu_main_1_clear_table))
			
			// debug
			Toast.makeText(this, "Unknown option", Toast.LENGTH_SHORT).show();
			
		}//if (item.getTitle().equals(CONST.opt_menu_main_1_clear_table))
		
		
//		switch (item.getItemId()) {
//	
////		case R.id.menu_clear_table:
////			
////			// debug
////			Toast.makeText(this, "Clear the table", Toast.LENGTH_SHORT).show();
////			
////			break;
//			
//		default:
//			break;
//		
//		}//switch (item.getItemId())
		
		return super.onOptionsItemSelected(item);
	}
	
	public boolean onDestroy(MediaPlayer mp, int what, int extra) {
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onDestroy()");
		
		return false;
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onPause()");
	}
	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onStop()");
	
	}//protected void onStop()
	
	private void getFilesList() {
		// TODO Auto-generated method stub
		File dirMedia = new File(CONST.DIRPATH_MEDIA);
		
		listFiles = dirMedia.list(new FilenameFilter(){

			@Override
			public boolean accept(File file, String name) {
				// TODO Auto-generated method stub
				if (name.matches(".*\\.mp4")) {
					
					return true;
					
				} else {//if (name.matches(""))
					
					return false;
					
				}//if (name.matches(""))
				
			}//public boolean accept(File file, String name)
			
		});//String[] listFiles
		
		//debug
		if (listFiles != null) {

			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "listFiles.length=" + listFiles.length);
			
			for (String name : listFiles) {
				
				// Log
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ ":"
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", "name=" + name);

			}//for (String name : listFiles)
			
		} else {//if (listFiles != null)

			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "listFiles => null");
			
		}//if (listFiles != null)
		
	}//private void getFilesList()
	
}
