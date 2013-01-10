package vp2.main;
	
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
	
public class MainActv extends Activity 
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
	
	    // Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onCreate()");

		B6_v_1_0();
		
//		B5_v_2_1();
//		B5_v_2_0();
//		B5_v_1_1();
//		B5_v_1_0();
		
//		B4_v_1_0();
	    
	}//public void onCreate(Bundle savedInstanceState)
	
	
	private void B6_v_1_0() {
		// TODO Auto-generated method stub
		B5_v_2_1();
	}//private void B6_v_1_0()

	private void B5_v_2_1() {
		
		getWindow().requestFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
		
		B5_v_1_0();
		
		// Option menu labels
		opt_menu_labels =
				this.getResources().getStringArray(R.array.option_menu_labels_actv_main);
		
		
	}//private void B5_v_2_1()


	@SuppressLint("NewApi")
	private void B5_v_2_0() {
		
		getWindow().requestFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
		
		B5_v_1_0();
		
		SpinnerAdapter mSpinnerAdapter = null;
		
		ActionBar mActionBar = getActionBar();
		
		String[] modeArray = null;
		
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        
        mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.action_list,
//                android.R.layout.simple_spinner_dropdown_item);
                R.layout.ddrow_actv_main_menu);
        
        mActionBar.setListNavigationCallbacks(mSpinnerAdapter, this);
        
//        modeArray = getResources().getStringArray(R.array.action_list);
//        
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        current = new MyFragment();
//        ft.replace(R.id.frameLayout1, current, FRAGMENT_TAG);
//        ft.commit();
        
	}//private void B5_v_2_0()
	


	@SuppressLint("NewApi")
	private void B5_v_1_1() {
		// TODO Auto-generated method stub
		getWindow().requestFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
		
//		setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar);
		
		B5_v_1_0();
		
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			final ActionBar mActionBar = getActionBar();
			mActionBar.addTab(mActionBar.newTab().setText("Tab 1")
					.setTabListener(this));
			mActionBar.addTab(mActionBar.newTab().setText("Tab 2")
					.setTabListener(this));
			mActionBar.addTab(mActionBar.newTab().setText("Tab 3")
					.setTabListener(this));
			
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			
		}//if (condition)
		
	}//private void B5_v_1_1()


	private void B5_v_1_0() {
		// TODO Auto-generated method stub
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sdk=" + android.os.Build.VERSION.SDK_INT);
		
		B4_v_1_0();
	}//private void B5_v_1_0()


	private void B4_v_1_0() {
		
		setContentView(R.layout.activity_main_actv_vv);
		
		B2_v_2_0_initialize_views();
		
		// Setup media dir
		B2_v_1_1_setup_media_dir();
	
		// Setup db
		B4_v_1_0_setup_db();
		
		// Set listeners
		B2_v_1_1_set_listeners();
	
		// List view
		B4_v_1_0_setup_listview();
		
		// Start media
		B3_v_1_1_start_media();
		
	}//private void B4_v_1_0()
	
	
	private void B4_v_1_0_setup_listview() {
		
		srt_list = Methods_VP2.get_srt_list_from_db(this);
		
//		Methods_VP2.sort_list_start_time(srt_list);
		
		// Log
		if (srt_list != null) {
	
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "srt_list.size()=" + srt_list.size());
			
			Methods_VP2.sort_list_start_time(srt_list);
	
		} else {//if (srt_list != null)
	
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "srt_list == null");
			
		}//if (srt_list != null)
		
		//
		aAdapter = new SRTListAdapter(
				this,
				R.layout.activity_main_actv_vv,
				srt_list
		);
		
		if (aAdapter == null) {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "aAdapter == null");
			
		} else {//if (aAdapter == null)

			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "aAdapter != null");

		}//if (aAdapter == null)
		
		//
		lv_srt_items = (ListView) findViewById(R.id.actv_main_lv);

		if (lv_srt_items == null) {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "lv_srt_items == null");
			
		} else {//if (lv_srt_items == null)

			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "lv_srt_items != null");

		}//if (lv_srt_items == null)

		if (aAdapter != null) {
			
			lv_srt_items.setAdapter(aAdapter);
			
		}//if (aAdapter == condition)
//		lv_srt_items.setAdapter(aAdapter);
		
		// Tag
		lv_srt_items.setTag(Tags.ListTags.actv_main_lv);
		
		// Listener
		lv_srt_items.setOnItemClickListener(new ListOnItemClickListener(this));
		
	}//private void B4_v_1_0_setup_listview()
	
	
	private void B4_v_1_0_setup_db() {
		
		DBUtils dbu = new DBUtils(this, CONST.dbname_main);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		dbu.createTable(wdb,
				CONST.tname_main,
				CONST.cols_srt_data,
				CONST.col_types_srt_data);
	
	
		wdb.close();
		
	}//private void B4_v_1_0_setup_db()
	
	
	private void B3_v_1_1a() {
		// TODO Auto-generated method stub
		B3_v_1_1();
	}
	
	
	private void B3_v_1_1() {
		setContentView(R.layout.activity_main_actv_vv);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Start => B2_v_1_2()");
		
		// Set listeners
		B2_v_1_1_set_listeners();
		
	//    	// Log
	//		Log.d("MainActv.java" + "["
	//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//				+ "]", "Listeners => Set");
		
		B2_v_2_0_initialize_views();
		
		// Setup media dir
		B2_v_1_1_setup_media_dir();
	
		B3_v_1_1_start_media();
		
	}//private void B3_v_1_1()
	
	private void B3_v_1_1_start_media() {
		
		vvPlayer = (VideoView) findViewById(R.id.main_actv_vv);
		
	//    	String media_name = "test.mp4";
		String media_name = "test2.mp4";
		
		String dpath_media = StringUtils.join(new String[]{
				CONST.DIRPATH_MEDIA,
				media_name},
				File.separator
		);//String dpath_media
	//    	
	//    	Uri uri = Uri.parse(url_s);
		
	//    	vvPlayer.setVideoURI(uri);
		vvPlayer.setVideoPath(dpath_media);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]",
			"Video path=" + dpath_media);
		
		vvPlayer.setMediaController(new MediaController(this));
		
		vvPlayer.requestFocus();
		
		// B2 v-2.0
		vvPlayer.setOnPreparedListener(
			new OnPreparedListener(){
	
				public void onPrepared(MediaPlayer arg0) {
					// TODO Auto-generated method stub
			    	try {
			    		
			    		// Log
						Log.d("MainActv.java" + "["
								+ Thread.currentThread().getStackTrace()[2].getLineNumber()
								+ "]", "vvPlayer.getDuration()=" + vvPlayer.getDuration());
			    		
						vvPlayer.start();
						
						// Log
						Log.d("MainActv.java"
								+ "["
								+ Thread.currentThread().getStackTrace()[2]
										.getLineNumber() + "]", "Calling task...");
						
	//						new Task_Progress().execute();
						task_prog = new Task_Progress();
						
						task_prog.execute();
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// Log
						Log.e("MainActv.java" + "["
								+ Thread.currentThread().getStackTrace()[2].getLineNumber()
								+ "]", "Exception: " + e.toString());
					}//try
	
				}//public void onPrepared(MediaPlayer arg0)
			
			}//new OnPreparedListener()
		);//vvPlayer.setOnPreparedListener

		// B6 v-1.0
//		vvPlayer.setOnCompletionListener(new OnCompletionListener(){
//
//				@Override
//				public void onCompletion(MediaPlayer arg0) {
//
//					task_prog.cancel(true);
//					
//					// Log
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "Task cancelled");
//					
//				}//public void onCompletion(MediaPlayer arg0)
//				
//			}//new OnCompletionListener()
//		
//		);//vvPlayer.setOnCompletionListener
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Focus requested");
	
	}//private void B3_v_1_1_start_media()
	
	private void B3_v_1_0() {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.activity_main_actv);
//		
//		getWindow().setFormat(PixelFormat.TRANSPARENT);
//		
//		// Initialize the holder
//		mPreview = (SurfaceView) findViewById(R.id.main_actv_sv);
//		holder = mPreview.getHolder();
//		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//		holder.addCallback(this);
//		
//		final MediaPlayer mp = new MediaPlayer();
//		
//	//    	final String urlString = "http://www.youtube.com/watch?v=Auufbu_ZdDI";
//		final String urlString = 
//				CONST.DIRPATH_MEDIA + File.separator
//				+ "test.mp4";
//				
//		final SurfaceView sv = (SurfaceView) findViewById(R.id.main_actv_sv);
//		
//		final SurfaceHolder sh = sv.getHolder();
//	    sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//	    
//	    // Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "sh.toString()=" + sh.toString());
//	    
//		if ((sh.getSurface() != null)) {
//			// Log
//			Log.d("MainActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Surface obtained");
//		} else {//if ((sh.getSurface() != null))
//			
//			// Log
//			Log.d("MainActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "sh.getSurface() == null");
//			
//		}//if ((sh.getSurface() != null))
//		
//		
//	    mp.setDisplay(sh);
//	    
//	    // Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Done => mp.setDisplay(sh)");
//	    
//	    sh.addCallback(new Callback() {
//	
//	//            @Override
//	        public void surfaceDestroyed(SurfaceHolder holder) {
//	            // “®‰æ’âŽ~
//	            mp.stop();
//	            mp.release();
//	        }
//	
//	//            @Override
//	        public void surfaceCreated(SurfaceHolder holder) {
//	            try {
//	
//	            	// Log
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "Surface created");
//	                // “®‰æ‚ÌURL‚ðÝ’è
//	                mp.setDataSource(urlString);
//	
//	//                    mp.setDisplay(sh);
//	                
//	                // “®‰æÄ¶€”õ
//	                mp.prepare();
//	
//	                // Log
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "Done => mp.prepare()");
//	                
//	                // ‰¡•‚ðŠî€‚É16:9‚É•ÏŠ·
//	                int w = getWindowManager().getDefaultDisplay().getWidth();
//	                int h = w / 16 * 9;
//	                sv.layout(0, 0, w, h);
//	
//	                // “®‰æÄ¶
//	                mp.start();
//	                
//	                // Log
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "Starts => mp.start()");
//	                
//	            } catch (IllegalStateException e) {
//	                e.printStackTrace();
//	            } catch (IllegalArgumentException e) {
//	                e.printStackTrace();
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	
//	//            @Override
//	        public void surfaceChanged(SurfaceHolder holder, int format,
//	            int width, int height) {
//	        }
//	    });
//		
//		
	}//private void B3_v_1_0()
	
	
	private void B2_v_2_1() {
		setContentView(R.layout.activity_main_actv_vv);
		
		// Set listeners
		B2_v_1_1_set_listeners();
		
		// Setup media dir
		B2_v_1_1_setup_media_dir();
	
		// Initialize views
		B2_v_2_0_initialize_views();
	
		// Start media
		B2_v_2_1_start_media();
		
	}//private void B2_v_2_1()
	
	
	private void B2_v_2_1_start_media() {
		vvPlayer = (VideoView) findViewById(R.id.main_actv_vv);
		
	//    	String media_name = "test.mp4";
		String media_name = "test2.mp4";
		
		String dpath_media = StringUtils.join(new String[]{
				CONST.DIRPATH_MEDIA,
				media_name},
				File.separator
		);//String dpath_media
	
		// vvPlayer.setVideoURI(uri);
		vvPlayer.setVideoPath(dpath_media);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]",
			"Video path=" + dpath_media);
		
		vvPlayer.setMediaController(new MediaController(this));
		
		vvPlayer.requestFocus();
		
		vvPlayer.setTag(Tags.ViewTags.actv_main_vv);
		
		// B2 v-2.0
		vvPlayer.setOnPreparedListener(
					new CustomOnPreparedListener(this, Tags.ViewTags.actv_main_vv));//{
	//
	//			public void onPrepared(MediaPlayer arg0) {
	//				// TODO Auto-generated method stub
	//				Intent i = new Intent(MainActv.this, ProgressService.class);
	//				
	//				
	//				
	//			}});
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Focus requested");
		
		try {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "vvPlayer.getDuration()=" + vvPlayer.getDuration());
			
			vvPlayer.start();
			
	//			new Thread(this).start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Log
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
		}
		
	}//private void B2_v_2_1_start_media()
	
	
	private void B2_v_2_0() {
		
		setContentView(R.layout.activity_main_actv_vv);
		
		// Set listeners
		B2_v_1_1_set_listeners();
		
		// Setup media dir
		B2_v_1_1_setup_media_dir();
	
		// Initialize views
		B2_v_2_0_initialize_views();
		
		// Start media
		B2_v_1_2_start_media();
		
	}//private void B2_v_2_0()
	
	
	private void B2_v_2_0_initialize_views() {
		
		tv_progress = (TextView) findViewById(R.id.actv_main_tv_progress);
		
		
	}//private void B2_v_2_0_initialize_views()
	
	
	private void B2_v_1_3() {
		
		B2_v_1_2();
		
		
		
	}//private void B2_v_1_3()
	
	
	private void B2_v_1_2() {
		
		setContentView(R.layout.activity_main_actv_vv);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Start => B2_v_1_2()");
		
		// Set listeners
		B2_v_1_1_set_listeners();
		
	//    	// Log
	//		Log.d("MainActv.java" + "["
	//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//				+ "]", "Listeners => Set");
		
		// Setup media dir
		B2_v_1_1_setup_media_dir();
		
		
		// Start media
		B2_v_1_2_start_media();
		
	//    	// Log
	//		Log.d("MainActv.java" + "["
	//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//				+ "]", "Media dir => Set");
		
	//    	B2_v_1_2_start_media();
		
	}//private void B2_v_1_2()
	
	private void B2_v_1_2_start_media() {
		
		vvPlayer = (VideoView) findViewById(R.id.main_actv_vv);
		
	//    	String media_name = "test.mp4";
		String media_name = "test2.mp4";
		
		String dpath_media = StringUtils.join(new String[]{
				CONST.DIRPATH_MEDIA,
				media_name},
				File.separator
		);//String dpath_media
	//    	
	//    	Uri uri = Uri.parse(url_s);
		
	//    	vvPlayer.setVideoURI(uri);
		vvPlayer.setVideoPath(dpath_media);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]",
			"Video path=" + dpath_media);
		
		vvPlayer.setMediaController(new MediaController(this));
		
		vvPlayer.requestFocus();
		
		// B2 v-2.0
		vvPlayer.setOnPreparedListener(new OnPreparedListener(){
	
			public void onPrepared(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				new Thread(MainActv.this).start();
			}});
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Focus requested");
		
		try {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "vvPlayer.getDuration()=" + vvPlayer.getDuration());
			
			vvPlayer.start();
			
	//			new Thread(this).start();
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Thread started");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Log
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
		}
	
	}
	
	private void B2_v_1_1() {
		
		B1_v_1_6();
		
		String f_name = "sequence.dat";
		
		B2_v_1_1_set_listeners();
		
		B2_v_1_1_setup_media_dir();
		
	//    	// Log
	//		Log.d("MainActv.java" + "["
	//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//				+ "]", "External path=" + Environment.getExternalStorageDirectory().getPath());
	//    	
	//		// Log
	//		Log.d("MainActv.java" + "["
	//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//				+ "]", "FilesDir=" + this.getFilesDir().getPath());
	}//private void B2_v_1_1()
	
	private void B2_v_1_1_setup_media_dir() {
		
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
				
			} catch (Exception e) {
				
				// Log
				Log.e("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Exception: " + e.toString());
			}//try
			
		}//if (dpath_media.exists())
		
	}//private void B2_v_1_1_setup_media_dir()
	
	private void B2_v_1_1_set_listeners() {
		/*********************************
		 * Buttons
		 *********************************/
		Button bt_export = (Button) findViewById(R.id.actv_main_bt_export);
		Button bt_start = (Button) findViewById(R.id.actv_main_bt_start);
		Button bt_end = (Button) findViewById(R.id.actv_main_bt_end);
	
		/*********************************
		 * Tags
		 *********************************/
		bt_export.setTag(Tags.ButtonTags.actv_main_bt_export);
		bt_start.setTag(Tags.ButtonTags.actv_main_bt_start);
		bt_end.setTag(Tags.ButtonTags.actv_main_bt_end);
		
		/*********************************
		 * OnTouchListener
		 *********************************/
		bt_export.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_start.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_end.setOnTouchListener(new ButtonOnTouchListener(this));
		
		/*********************************
		 * OnClickListener
		 *********************************/
		bt_export.setOnClickListener(new ButtonOnClickListener(this));
		bt_start.setOnClickListener(new ButtonOnClickListener(this));
		bt_end.setOnClickListener(new ButtonOnClickListener(this));
		
	}//private void B2_v_1_1_set_listeners()
	
	private void B1_v_2_1() {
		// TODO Auto-generated method stub
		
		B1_v_1_6();
		
	}//private void B1_v_2_1()
	
	private void B1_v_2_0() {
//		// TODO Auto-generated method stub
//		
//		setContentView(R.layout.activity_main_actv);
//		
//		final MediaPlayer mp = new MediaPlayer();
//		
//		final String urlString = "/mnt/sdcard-ext/test.mp4";
//		
//		final SurfaceView sv = (SurfaceView) findViewById(R.id.main_actv_sv);
//		
//		final SurfaceHolder sh = sv.getHolder();
//	    sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//	    
//	    mp.setDisplay(sh);
//	    
//	    // Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Done => mp.setDisplay(sh)");
//	    
//	    sh.addCallback(new Callback() {
//	
//	//            @Override
//	        public void surfaceDestroyed(SurfaceHolder holder) {
//	            // “®‰æ’âŽ~
//	            mp.stop();
//	            mp.release();
//	        }
//	
//	//            @Override
//	        public void surfaceCreated(SurfaceHolder holder) {
//	            try {
//	
//	            	// Log
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "Surface created");
//	                // “®‰æ‚ÌURL‚ðÝ’è
//	                mp.setDataSource(urlString);
//	                
//	                // Log
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "Source set");
//	                
//	                // “®‰æÄ¶€”õ
//	                mp.prepare();
//	
//	                // Log
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "Done => mp.prepare()");
//	                
//					
//	                // ‰¡•‚ðŠî€‚É16:9‚É•ÏŠ·
//	                int w = getWindowManager().getDefaultDisplay().getWidth();
//	                int h = w / 16 * 9;
//	                sv.layout(0, 0, w, h);
//	
//	//                    mp.setLooping(false);
//	                
//	                // “®‰æÄ¶
//	                mp.start();
//	                
//	                // Log
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "Starts => mp.start()");
//	                
//	            } catch (IllegalStateException e) {
//	                e.printStackTrace();
//	            } catch (IllegalArgumentException e) {
//	                e.printStackTrace();
//	            } catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	        }//public void surfaceCreated(SurfaceHolder holder)
//	
//	//            @Override
//	        public void surfaceChanged(SurfaceHolder holder, int format,
//	            int width, int height) {
//	        }
//	    });
	
	}//private void B1_v_2_0()
	
	private void B1_v_1_9() {
		
		setContentView(R.layout.activity_main_actv_vv);
		
		vvPlayer = (VideoView) findViewById(R.id.main_actv_vv);
		
		String src_path = "/mnt/sdcard-ext/test_mp4box.mp4";
		
		// File exists?
		File f = new File(src_path);
		
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "File exists? => " + f.exists());
		
		// Listener
		vvPlayer.setOnPreparedListener(new OnPreparedListener(){
	
			public void onPrepared(MediaPlayer mp) {
				
				// Log
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Prepared");
				
				try {
					
					vvPlayer.start();
					
				} catch (Exception e) {
					
					// Log
					Log.d("MainActv.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]",
							"Exception: " + e.toString());
				}
				
			}//public void onPrepared(MediaPlayer arg0)
			
		});//vvPlayer.setOnPreparedListener
		
		
		vvPlayer.setVideoPath(src_path);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Video path set=" + src_path);
		
		vvPlayer.setMediaController(new MediaController(this));
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Controller set");
		
		vvPlayer.requestFocus();
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Focus requested");
	
	}//private void B1_v_1_9()
	
	private void B1_v_1_8() {
		setContentView(R.layout.activity_main_actv_vv);
		
		
		// Log
		File f = new File(src_path);
		
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "File exists? => " + f.exists());
		
		vvPlayer = (VideoView) findViewById(R.id.main_actv_vv);
		
		String src_path = "/mnt/sdcard-ext/test_mp4box.mp4";
		
		vvPlayer.setVideoPath(src_path);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Video path set=" + src_path);
		
		vvPlayer.setMediaController(new MediaController(this));
		
		vvPlayer.requestFocus();
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Focus requested");
		
		try {
			
			vvPlayer.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Log
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
		}
	
	}//private void B1_v_1_8()
	
	private void B1_v_1_6() {
	
		setContentView(R.layout.activity_main_actv_vv);
		
		vvPlayer = (VideoView) findViewById(R.id.main_actv_vv);
		
	//    	vvPlayer.setVideoPath("android.resource://com.example.android.apis/" + R.raw.test);
	//    	vvPlayer.setVideoPath("android.resource://vp.main/" + R.raw.test);
		
		String url_s = "android.resource://vp2.main/raw/test";
		
		Uri uri = Uri.parse(url_s);
		
		vvPlayer.setVideoURI(uri);
	
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]",
			"Video uri=" + uri.toString());
		
		vvPlayer.setMediaController(new MediaController(this));
		
		vvPlayer.requestFocus();
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Focus requested");
		
		try {
			
			vvPlayer.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Log
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
		}
		
	}//private void B1_v_1_6()
	
	private void B1_v_1_5() {
		ProcessBuilder cmd;
		String result="";
			
		try{
			String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
			cmd = new ProcessBuilder(args);
			
			Process process = cmd.start();
			
			InputStream in = process.getInputStream();
			byte[] re = new byte[1024];
			
			while(in.read(re) != -1){
	//				System.out.println(new String(re));
				result = result + new String(re);
			}
			in.close();
		} catch(IOException e){
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
		}
	
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "result=" + result);
		
	}//private void B1_v_1_5()
	
	private void B1_v_1_3() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main_actv_vv);
		
		
		// Log
		File f = new File(src_path);
		
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "File exists? => " + f.exists());
		
		vvPlayer = (VideoView) findViewById(R.id.main_actv_vv);
		
		vvPlayer.setVideoPath(src_path);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Video path set=" + src_path);
		
		vvPlayer.setMediaController(new MediaController(this));
		
		vvPlayer.requestFocus();
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Focus requested");
		
		try {
			vvPlayer.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Log
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
		}
		
	}//private void B1_v_1_3()
	
	private void B1_v_1_0() {
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Starts => B1_v_1_0()");
		
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main_actv_vv);
		
	//    	VideoView vvPlayer = (VideoView) findViewById(R.id.main_actv_vv);
		vvPlayer = (VideoView) findViewById(R.id.main_actv_vv);
		
	//    	String url_string = "http://www.youtube.com/watch?v=Auufbu_ZdDI";
		String url_string = "rtsp://v4.cache2.c.youtube.com/CjYLENy73wIaLQkydNnvbp_rAhMYDSANFEIJbXYtZ29vZ2xlSARSBXdhdGNoYOaWyvekrbjoUAw=/0/0/0/video.3gp";
		
		
		vvPlayer.setVideoURI(Uri.parse(url_string));
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "URI set");
		
		this.setTitle(this.getClass().getName());
		
		vvPlayer.setMediaController(new MediaController(this));
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Controller set");
		
		vvPlayer.start();
		
	}
	
	private void main_v_1_3() {
		
		
	}



//	private void main_v_1_0() {
//		
//    	// Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Starts => main_v_1_0()");
//    	
//    	final MediaPlayer mp = new MediaPlayer();
//    	
//    	final String urlString = "http://www.youtube.com/watch?v=Auufbu_ZdDI";
//    	
//    	final SurfaceView sv = (SurfaceView) findViewById(R.id.main_actv_vv);
//    	
//    	final SurfaceHolder sh = sv.getHolder();
//        sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        
//        mp.setDisplay(sh);
//        
//        // Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Done => mp.setDisplay(sh)");
//        
//        sh.addCallback(new Callback() {
//
////            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                // “®‰æ’âŽ~
//                mp.stop();
//                mp.release();
//            }
//
////            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                try {
//
//                	// Log
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "Surface created");
//                    // “®‰æ‚ÌURL‚ðÝ’è
////                    mp.setDataSource(urlString);
//
//                    // “®‰æÄ¶€”õ
//                    mp.prepare();
//
//                    // Log
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "Done => mp.prepare()");
//                    
//                    // ‰¡•‚ðŠî€‚É16:9‚É•ÏŠ·
//                    int w = getWindowManager().getDefaultDisplay().getWidth();
//                    int h = w / 16 * 9;
//                    sv.layout(0, 0, w, h);
//
//                    // “®‰æÄ¶
//                    mp.start();
//                    
//                    // Log
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "Starts => mp.start()");
//                    
//                } catch (IllegalStateException e) {
//                    e.printStackTrace();
//                } catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
////            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format,
//                int width, int height) {
//            }
//        });
//
//		
//	}//private void main_v_1_0()

	
	
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	//	        getMenuInflater().inflate(R.menu.activity_main_actv, menu);
		
		//REF http://stackoverflow.com/questions/3993924/android-api-level
		if (android.os.Build.VERSION.SDK_INT >= 11) {

			// B5 v-2.1
//			MenuItem item1 = menu.add(opt_menu_labels[0]);
			MenuItem item1 = menu.add(CONST.opt_menu_main_1_clear_table);
	        // ƒAƒCƒRƒ“‚ðÝ’è
//	        item1.setIcon(android.R.drawable.ic_menu_help);
			item1.setIcon(android.R.drawable.ic_menu_call);
	 
	        
	        
	        // SHOW_AS_ACTION_ALWAYS:í‚É•\Ž¦
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
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Surface created");
		
	//		// Suggestion from: http://stackoverflow.com/questions/9812067/android-videoview-error-1-0 by "ctrl"
	//		this.holder = holder;
		
		//
		surfaceCreated_m_v_1_5();
		
	//		String mediaPath = "http://www.youtube.com/watch?v=Auufbu_ZdDI";
		
	//		// Log
	//		Log.d("MainActv.java" + "["
	//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//				+ "]",
	//				"Environment.getExternalStorageDirectory().getPath()="
	//				+ Environment.getExternalStorageDirectory().getPath());
		
	//		try {
			
			
			
			
	//			AssetFileDescriptor afd = getAssets().openFd("violin_tutorial.mp4");
	//			
	//			mp = new MediaPlayer();
	////			mp.setDataSource(mediaPath);
	//			mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
	//			
	//			// Log
	//			Log.d("MainActv.java" + "["
	//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//					+ "]", "Data source set");
	//			
	//			mp.setDisplay(holder);
	//			mp.prepare();
	//			
	//			// Log
	//			Log.d("MainActv.java" + "["
	//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//					+ "]", "mp prepared");
	//			
	//			mp.start();
			
	//		} catch (IllegalArgumentException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		} catch (IllegalStateException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
		
	}//public void surfaceCreated(SurfaceHolder holder)
	
	private void surfaceCreated_m_v_1_5() {
		
		String f_name = "violin_tutorial.mp4";
		
		String path = StringUtils.join(new String[]{
				Environment.getExternalStorageDirectory().getPath(),
				f_name
		}, File.separator);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "path=" + path);
		
		
		mp = MediaPlayer.create(this, Uri.parse(path));
		
		mp.setDisplay(holder);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Done => setDisplay");
		
		try {
			
	//			mp.prepare();
			
			
			mp.start();
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//private void surfaceCreated_m_v_1_5()
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Surface => Destroyed");
		
		if(mp != null){
			mp.release();
			mp = null;
		}
	
	}
	
	
	public boolean onDestroy(MediaPlayer mp, int what, int extra) {
		// Log
		Log.d("MainActv.java" + "["
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
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "task_prog => Cancelled");
			
		}//if (task_prog == condition)
		
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
		
		// B3 v-1.1
//		while(!task_prog.isCancelled()) {
//			
//			task_prog.cancel(true);
//		}
	
		if (task_prog != null) {
			
			task_prog.cancel(true);
			
		} else {//if (task_prog != null)
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "task_prog == null");
			
		}//if (task_prog != null)
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "task_prog => Cancelled");
	
	//		if (!task_prog.isCancelled()) {
	//			
	//			task_prog.cancel(true);
	//			
	//			// Log
	//			Log.d("MainActv.java" + "["
	//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//					+ "]", "task_prog => Cancelled");
	//			
	//		} else {//if (task_prog == condition)
	//			
	//			// Log
	//			Log.d("MainActv.java" + "["
	//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//					+ "]", "task_prog => Already cancelled");
	//			
	//		}//if (task_prog == condition)
	
	}//protected void onStop()
	
	
	public void run() {
		
		int currentPosition= 0;
		int total = vvPlayer.getDuration();
		
		if (vvPlayer != null) {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "vvPlayer.getDuration()=" + vvPlayer.getDuration());
			
		} else {//if (vvPlayer != null)
			
			// Log
			Log.d("MainActv.java" + "["
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
			
			MainActv.show_progress(currentPosition);
			
	//			tv_progress.setText(String.valueOf(currentPosition));
			
		}//while (vvPlayer!=null && currentPosition<total)
		
	}
	
	
	public static void show_progress(int currentPosition) {
		// TODO Auto-generated method stub
	//		// Log
	//		Log.d("MainActv.java" + "["
	//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//				+ "]", "Starting => show_progress()");
			
			tv_progress.setText(Methods.convert_milsec_to_digits(currentPosition));
		}


	@SuppressLint("NewApi")
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@SuppressLint("NewApi")
	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@SuppressLint("NewApi")
	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onNavigationItemSelected(int position, long itemId) {
		// TODO Auto-generated method stub
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "itemId=" + itemId + "/" + "positioni=" + position);
		
		return false;
	}
}
