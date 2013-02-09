package vp2.main;
	
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import vp2.adapters.BIAdapter;
import vp2.adapters.SRTListAdapter;
import vp2.items.BI;
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
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
	
public class PlayActv extends Activity 
				implements SurfaceHolder.Callback, Runnable, 
					TabListener, OnNavigationListener {

	private static final String TAG = "VideoPlayer";

	private SurfaceHolder holder;
	  
	private SurfaceView mPreview;
	
	public static MediaPlayer mp = null;
	
	public static VideoView vvPlayer = null;
	
	String f_name = "violin_tutorial.mp4";
	
	String src_path = "/mnt/sdcard-ext/test.mp4";
	
	public static List<SRTItem> srt_list = null;
	
	public static SRTListAdapter aAdapter = null;

	// B5 v-2.1
	String[] opt_menu_labels;
	
	/*********************************
	 * Views
	 *********************************/
	static TextView tv_progress = null;
	
	Task_Progress task_prog = null;
	
	public static ListView lv_srt_items;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	}//public void onCreate(Bundle savedInstanceState)

	private void setupMediaDir() {

		File dpath_media = new File(CONST.DIRPATH_MEDIA);
		
		if (dpath_media.exists()) {
	
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Media dir => exists");
			
		} else {//if (dpath_media.exists())
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Media dir => Doesn't exist");
			
			try {
				
				dpath_media.mkdirs();
				
			} catch (Exception e) {
				
				// Log
				Log.e("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Exception: " + e.toString());
			}//try
			
		}//if (dpath_media.exists())
		
	}//private void setupMediaDir()

	private void initViews() {

		tv_progress = (TextView) findViewById(R.id.actv_play_tv_progress);

	}//private void initViews()

	private void setupDb() {

		DBUtils dbu = new DBUtils(this, CONST.dbname_main);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		dbu.createTable(wdb,
				CONST.tname_main,
				CONST.cols_srt_data,
				CONST.col_types_srt_data);

		wdb.close();
		
	}//private void setupDb()

	private void setListeners() {

		/*********************************
		 * Buttons
		 *********************************/

		ImageButton ib_start = (ImageButton) findViewById(R.id.actv_play_ib_start);

		ImageButton bt_end = (ImageButton) findViewById(R.id.actv_play_ib_end);
	
		/*********************************
		 * Tags
		 *********************************/

		ib_start.setTag(Tags.ButtonTags.actv_main_ib_start);
		bt_end.setTag(Tags.ButtonTags.actv_main_ib_end);
		
		/*********************************
		 * OnTouchListener
		 *********************************/

		ib_start.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_end.setOnTouchListener(new ButtonOnTouchListener(this));
		
		/*********************************
		 * OnClickListener
		 *********************************/

		ib_start.setOnClickListener(new ButtonOnClickListener(this));
		bt_end.setOnClickListener(new ButtonOnClickListener(this));
		
	}//private void setListeners()

	private void setupBookmarkList() {
		Intent i = this.getIntent();
		
		String itemName =
				i.getStringExtra(CONST.intent.mainActv_fileName.name());

		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "itemName=" + itemName);
		
		/*********************************
		 * Convert clip name into table name
		 *********************************/
		String tableName = Methods_VP2.convertItemName2TableName(this, itemName);
		
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "tableName=" + tableName);

		CONST.bookmarkList = Methods_VP2.getBookmarkList(this, tableName);

		// Log
		if (CONST.bookmarkList != null) {
	
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "CONST.bookmarkList.size()=" + CONST.bookmarkList.size());
			

			Methods_VP2.sortBookmark_startTime(CONST.bookmarkList);
	
		} else {//if (CONST.bookmarkList != null)
	
			// Log
			Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "CONST.bookmarkList == null");
			
		}//if (CONST.bookmarkList != null)
		
		//

		CONST.biAdapter = new BIAdapter(
				this,
				R.layout.activity_play_actv_vv,
				CONST.bookmarkList
				);
		
		if (CONST.biAdapter == null) {
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "CONST.biAdapter == null");
			
		} else {//if (CONST.biAdapter == null)

			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "CONST.biAdapter != null");

		}//if (CONST.biAdapter == null)
		
		//
		CONST.lv_bookMarks = (ListView) findViewById(R.id.actv_play_lv);

		if (CONST.lv_bookMarks == null) {
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "CONST.lv_bookMarks == null");
			
		} else {//if (CONST.lv_bookMarks == null)

			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "CONST.lv_bookMarks != null");

		}//if (CONST.lv_bookMarks == null)

		if (CONST.biAdapter != null && CONST.lv_bookMarks != null
					&& CONST.bookmarkList != null) {
			
			CONST.lv_bookMarks.setAdapter(CONST.biAdapter);
			
		} else {//if (CONST.biAdapter == condition)
		
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]",
					"Either CONST.lv_bookMarks is null or " +
						"CONST.biAdapter is null or " +
						"CONST.bookmarkList is null");
			
		}//if (CONST.biAdapter == condition)
		// Tag
		CONST.lv_bookMarks.setTag(Tags.ListTags.actv_main_lv);
		
		// Listener
		CONST.lv_bookMarks.setOnItemClickListener(new ListOnItemClickListener(this));
		
	}//private void setupListView()

	private void startMedia() {
		/*********************************
		 * Get intent
		 *********************************/
		Intent i = this.getIntent();

		String mediaName =
				i.getStringExtra(CONST.intent.mainActv_fileName.name());
		
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "mediaName=" + mediaName);
		
		/*********************************
		 * Setup player
		 *********************************/
		vvPlayer = (VideoView) findViewById(R.id.main_actv_vv);

			String media_name = "test2.mp4";
			
			String dpath_media = StringUtils.join(new String[]{
					CONST.DIRPATH_MEDIA,

					mediaName},
					File.separator
			);//String dpath_media

			vvPlayer.setVideoPath(dpath_media);
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]",
				"Video path=" + dpath_media);
			
			vvPlayer.setMediaController(new MediaController(this));
			
			vvPlayer.requestFocus();
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Focus requested");
			
			// B2 v-2.0
			vvPlayer.setOnPreparedListener(
				new OnPreparedListener(){
		
					public void onPrepared(MediaPlayer arg0) {

				    	try {
				    		
				    		// Log
							Log.d("PlayActv.java" + "["
									+ Thread.currentThread().getStackTrace()[2].getLineNumber()
									+ "]", "vvPlayer.getDuration()=" + vvPlayer.getDuration());
				    		
							vvPlayer.start();
							
							// Log
							Log.d("MainActv.java"
									+ "["
									+ Thread.currentThread().getStackTrace()[2]
											.getLineNumber() + "]", "Calling task...");

							task_prog = new Task_Progress();
							
							task_prog.execute();
							
						} catch (Exception e) {

							// Log
							Log.e("MainActv.java" + "["
									+ Thread.currentThread().getStackTrace()[2].getLineNumber()
									+ "]", "Exception: " + e.toString());
						}//try
		
					}//public void onPrepared(MediaPlayer arg0)
				
				}//new OnPreparedListener()
			);//vvPlayer.setOnPreparedListener

	}//private void startMedia()

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		//REF http://stackoverflow.com/questions/3993924/android-api-level
		if (android.os.Build.VERSION.SDK_INT >= 11) {

			// B5 v-2.1

			MenuItem item1 = menu.add(CONST.opt_menu_main_1_clear_table);
	        // アイコンを設定

			item1.setIcon(android.R.drawable.ic_menu_call);
	 

	        // SHOW_AS_ACTION_ALWAYS:常に表示
	        item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		}//if (android.os.Build.VERSION.SDK_INT == )
		
	    return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		// debug
		int id = item.getItemId();
		
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "id=" + id);

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// B5 v-2.1
		if (item.getTitle().equals(CONST.opt_menu_main_1_clear_table)) {
			
			Methods_VP2.clear_table_main(this);

		} else {//if (item.getTitle().equals(CONST.opt_menu_main_1_clear_table))
			
			// debug
			Toast.makeText(this, "Unknown option", Toast.LENGTH_SHORT).show();
			
		}//if (item.getTitle().equals(CONST.opt_menu_main_1_clear_table))

		return super.onOptionsItemSelected(item);
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}
	
	public void surfaceCreated(SurfaceHolder holder) {

		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Surface created");

		//
		surfaceCreated_m_v_1_5();


	}//public void surfaceCreated(SurfaceHolder holder)
	
	private void surfaceCreated_m_v_1_5() {
		
		String f_name = "violin_tutorial.mp4";
		
		String path = StringUtils.join(new String[]{
				Environment.getExternalStorageDirectory().getPath(),
				f_name
		}, File.separator);
		
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "path=" + path);

		mp = MediaPlayer.create(this, Uri.parse(path));
		
		mp.setDisplay(holder);
		
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Done => setDisplay");
		
		try {
			
	//			mp.prepare();

			mp.start();
			
		} catch (IllegalStateException e) {

			e.printStackTrace();
		}
		
	}//private void surfaceCreated_m_v_1_5()
	
	public void surfaceDestroyed(SurfaceHolder holder) {

		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Surface => Destroyed");
		
		if(mp != null){
			mp.release();
			mp = null;
		}
	
	}

	public boolean onDestroy(MediaPlayer mp, int what, int extra) {
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onDestroy()");
		
		if(mp != null){
			mp.release();
			mp = null;
		}
		
		// B3 v-1.1
		if (!task_prog.isCancelled()) {
			
			task_prog.cancel(true);
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "task_prog => Cancelled");
			
		}//if (task_prog == condition)
		
		return false;
	}

	@Override
	protected void onPause() {

		super.onPause();
		
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onPause()");
	}

	@Override
	protected void onStop() {

		super.onStop();
		
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onStop()");
	
		if (task_prog != null) {
			
			task_prog.cancel(true);
			
		} else {//if (task_prog != null)
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "task_prog == null");
			
		}//if (task_prog != null)
		
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "task_prog => Cancelled");
	
	}//protected void onStop()

	public void run() {
		
		int currentPosition= 0;
		int total = vvPlayer.getDuration();
		
		if (vvPlayer != null) {
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "vvPlayer.getDuration()=" + vvPlayer.getDuration());
			
		} else {//if (vvPlayer != null)
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "vvPlayer != null");
			
		}//if (vvPlayer != null)

		while (vvPlayer!=null && currentPosition<total) {
			try {
				
				Thread.sleep(1000);
				currentPosition= vvPlayer.getCurrentPosition();
				
			} catch (InterruptedException e) {
	
				return;
				
			} catch (Exception e) {
				
				// Log
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Exception: " + e.toString());
				return;
				
			}//try
			
			PlayActv.show_progress(currentPosition);

		}//while (vvPlayer!=null && currentPosition<total)
		
	}

	public static void show_progress(int currentPosition) {

			tv_progress.setText(Methods.convert_milsec_to_digits(currentPosition));
		}

	@SuppressLint("NewApi")
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {

	}

	@SuppressLint("NewApi")
	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {

	}

	@SuppressLint("NewApi")
	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {

	}

	@Override
	public boolean onNavigationItemSelected(int position, long itemId) {
		// TODO Auto-generated method stub
		
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "itemId=" + itemId + "/" + "positioni=" + position);
		
		return false;
	}

	@Override
	protected void onStart() {

		super.onStart();

		getWindow().requestFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
		
		// Option menu labels
		opt_menu_labels =
				this.getResources().getStringArray(R.array.option_menu_labels_actv_main);
		
		setContentView(R.layout.activity_play_actv_vv);
		
		initViews();
		
		// Setup media dir
		setupMediaDir();
	
		// Setup db
		setupDb();
		
		// Set listeners
		setListeners();
	
		// List view
		setupBookmarkList();
		
		// Start media
		startMedia();
		
	}//protected void onStart()
	
}//public class PlayActv extends Activity
