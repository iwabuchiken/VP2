package vp2.items;

import android.graphics.Bitmap;

public class SRTItem {

	Long start_time;
	
	Long end_time;
	
	String subtitle;
	
	Bitmap thumbnail;
	
	Long db_id;

	public SRTItem(long start_time, long end_time, String subtitle, long db_id) {
		// TODO Auto-generated constructor stub
		this.start_time = start_time;
		
		this.end_time = end_time;
		
		this.subtitle = subtitle;
		
		this.db_id = db_id;
		
	}//public SRTItem(long start_time, long end_time, String subtitle, long db_id)

	public Long getStart_time() {
		return start_time;
	}

	public Long getEnd_time() {
		return end_time;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public Bitmap getThumbnail() {
		return thumbnail;
	}

	public Long getDb_id() {
		return db_id;
	}

	public void setStart_time(Long start_time) {
		this.start_time = start_time;
	}

	public void setEnd_time(Long end_time) {
		this.end_time = end_time;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setThumbnail(Bitmap thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setDb_id(Long db_id) {
		this.db_id = db_id;
	}
	
	
	
}//public class SRTItem
