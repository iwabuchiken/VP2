package vp2.listeners.dialog;

import java.util.List;

import vp2.main.R;
import vp2.utils.CONST;
import vp2.utils.Methods;
import vp2.utils.Tags;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class DialogOnItemClickListener implements OnItemClickListener {

	//
	Activity actv;
	Dialog dlg;
	Dialog dlg2;
	//
	Vibrator vib;
	
	//
//	Methods.DialogTags dlgTag = null;

	public DialogOnItemClickListener(Activity actv, Dialog dlg) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

	public DialogOnItemClickListener(Activity actv, Dialog dlg, Dialog dlg2) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		this.dlg2 = dlg2;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

//	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 1. Get tag
		 * 2. Vibrate
		 * 3. Switching
			----------------------------*/
		
		Tags.DialogTags tag = (Tags.DialogTags) parent.getTag();
//		
//		vib.vibrate(Methods.vibLength_click);
		
		// Log
		Log.d("DialogOnItemClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "tag.name()=" + tag.name());
		
		/*----------------------------
		 * 3. Switching
			----------------------------*/
		switch (tag) {
		
		case dlg_admin:
			
			String itemName = (String) parent.getItemAtPosition(position);
			
			caseDlgAdmin(itemName);
			
			break;
			
		default:
			break;
		}//switch (tag)
		
	}//public void onItemClick(AdapterView<?> parent, View v, int position, long id)

	private void caseDlgAdmin(String itemName) {
		// TODO Auto-generated method stub
		if (itemName.equals(actv.getString(
								R.string.dlg_admin_item_backup_db))) {
			
			Methods.backupDb(actv, CONST.dbname_main, CONST.DIRPATH_BK, dlg);
			
		} else {//if (itemName.equals(actv.getString(R.string.d)))
			
		}//if (itemName.equals(actv.getString(R.string.d)))
		
	}//private void caseDlgAdmin(String itemName)
	
}//public class DialogOnItemClickListener implements OnItemClickListener
