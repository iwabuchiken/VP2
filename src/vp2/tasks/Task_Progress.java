package vp2.tasks;

import vp2.main.MainActv;
import vp2.main.PlayActv;
import android.os.AsyncTask;
import android.util.Log;

public class Task_Progress extends AsyncTask<Void, Integer, Void> {

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub

		doInBackground_B3_v_1_1a();
		
//		doInBackground_B3_v_1_1();
//		int duration = PlayActv.vvPlayer.getDuration();
//		
//		int current = 0;
//		
//		// Log
//		Log.d("Task_Progress.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "duration=" + duration);
//		
//		do {
//			
//			current = PlayActv.vvPlayer.getCurrentPosition();
//			
//			// Log
//			Log.d("Task_Progress.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "current=" + current);
//			
//			//http://stackoverflow.com/questions/4748964/android-cancel-asynctask-forcefully
//			if(this.isCancelled()) {
//				// Log
//				Log.d("Task_Progress.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "isCancelled");
//				break;
//			}
//			
//			try {
//				
//				Thread.sleep(1000);
//				
//			} catch (InterruptedException e) {
//				
//				// Log
//				Log.d("Task_Progress.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "Exc: " + e.toString());
//				
//			}//try
//			
//		} while (current < duration);
		
		return null;
	}//protected Void doInBackground(Void... arg0)

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		
//		// Log
//		Log.d("Task_Progress.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "current=" + values[0]);
		
		PlayActv.show_progress(values[0]);
	}

	private void doInBackground_B3_v_1_1a() {
		int duration = PlayActv.vvPlayer.getDuration();
		
		int current = 0;
		
		// Log
		Log.d("Task_Progress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "duration=" + duration);
		
		do {
			
			current = PlayActv.vvPlayer.getCurrentPosition();
			
//			// Log
//			Log.d("Task_Progress.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "current=" + current);
			
			publishProgress(current);
			
			
			//http://stackoverflow.com/questions/4748964/android-cancel-asynctask-forcefully
			if(this.isCancelled()) {
				// Log
				Log.d("Task_Progress.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "isCancelled");
				break;
			}
			
			try {
				
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				
				// Log
				Log.d("Task_Progress.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Exc: " + e.toString());
				
			}//try
			
		} while (current < duration);
		
	}//private void doInBackground_B3_v_1_1a()

	private void doInBackground_B3_v_1_1() {
		int duration = PlayActv.vvPlayer.getDuration();
		
		int current = 0;
		
		// Log
		Log.d("Task_Progress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "duration=" + duration);
		
		do {
			
			current = PlayActv.vvPlayer.getCurrentPosition();
			
			// Log
			Log.d("Task_Progress.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "current=" + current);
			
			//http://stackoverflow.com/questions/4748964/android-cancel-asynctask-forcefully
			if(this.isCancelled()) {
				// Log
				Log.d("Task_Progress.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "isCancelled");
				break;
			}
			
			try {
				
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				
				// Log
				Log.d("Task_Progress.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Exc: " + e.toString());
				
			}//try
			
		} while (current < duration);
		
	}//private void doInBackground_B3_v_1_1()

}
