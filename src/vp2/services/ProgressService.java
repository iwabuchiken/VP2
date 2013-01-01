package vp2.services;

import java.util.Timer;
import java.util.TimerTask;

import vp2.main.MainActv;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class ProgressService extends Service {

	Timer timer;
	
	public PowerManager.WakeLock wl;
	
	Context mContext;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		// Log
		Log.d("ProgressService.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onBind => Done");

		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		// Log
		Log.d("ProgressService.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Service created");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		timer.cancel();
		
		timer = null;
		
		if (wl.isHeld()) {
			//
			wl.release();
			
			// Log
			Log.d("TimerService.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "wl => Released");
			
		}//if (wl.isHeld())

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		mContext = this;
		
		//
		PowerManager pm = 
					(PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
		
		// Log
		Log.d("TimerService.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "PowerManager obtained");

		// Log
		Log.d("TimerService.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Instantiating a WakeLock ...");

		//
		wl = pm.newWakeLock(
						PowerManager.SCREEN_DIM_WAKE_LOCK + 
							PowerManager.ON_AFTER_RELEASE, 
						"My Tag");
		
		// Log
		Log.d("TimerService.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Acquiring a WakeLock ...");
		
		//
		wl.acquire();
		
		// Log
		Log.d("TimerService.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "WakeLock obtained");
	
		B2_v_2_1();
	}

	private void B2_v_2_1() {
		// TODO Auto-generated method stub
		//
		if (timer != null) {
			timer.cancel();
		}//if (timer != null)
		
		//
		timer = new Timer();
		
		// Handler
		final android.os.Handler handler = new android.os.Handler();

		// Start timer
		timer.schedule(
			new TimerTask(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					handler.post(
						new Runnable(){

							public void run() {
								// TODO Auto-generated method stub
								
								int cur_pos = 0;
								int total = MainActv.vvPlayer.getDuration();
								
								while (MainActv.vvPlayer!=null && cur_pos<total) {
									try {
										
										Thread.sleep(1000);
										cur_pos= MainActv.vvPlayer.getCurrentPosition();
										
										// Log
										Log.d("ProgressService.java"
												+ "["
												+ Thread.currentThread()
														.getStackTrace()[2]
														.getLineNumber() + "]",
												"Curret=" + cur_pos);
										
									} catch (InterruptedException e) {

										return;
										
									} catch (Exception e) {
										
										// Log
										Log.d("ProgressService.java"
												+ "["
												+ Thread.currentThread().getStackTrace()[2]
														.getLineNumber() + "]", "Exception: " + e.toString());
										return;
										
									}//try
									
									// Log
									Log.d("ProgressService.java"
											+ "["
											+ Thread.currentThread()
													.getStackTrace()[2]
													.getLineNumber() + "]",
											"Calling => MainActv.show_progress(cur_pos)");
									
//									MainActv.show_progress(cur_pos);
									
//									tv_progress.setText(String.valueOf(currentPosition));
									
								}//while (MainActv.vvPlayer!=null && currentPosition<total)

								// Log
								Log.d("ProgressService.java"
										+ "["
										+ Thread.currentThread()
												.getStackTrace()[2]
												.getLineNumber() + "]",
										"Time elapsed");
								
								timer.cancel();
								
							}//public void run()
							
						}//new Runnable()
						
					);//handler.post
					
				}//public void run()
				
			},//new TimerTask()
			0,
			1000
		);//timer.schedule
		
		
	}//private void B2_v_2_1()

	
	
}//public class ProgressService extends Service
