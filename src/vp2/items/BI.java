package vp2.items;

import android.graphics.Bitmap;

public class BI {

	Long startTime;
	
	Long endTime;
	
	String title;
	
	Bitmap thumbnail;
	
	String memo;
	
	Long db_id;

	public BI(long start_time, long end_time,
				String title, String memo,
				long db_id) {
		// TODO Auto-generated constructor stub
		this.startTime = start_time;
		
		this.endTime = end_time;
		
		this.title = title;
		
		this.memo = memo;
		
		this.db_id = db_id;
		
	}//public SRTItem(long start_time, long end_time, String subtitle, long db_id)

	public Long getStartTime() {
		return startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public String getSubtitle() {
		return title;
	}

	public Bitmap getThumbnail() {
		return thumbnail;
	}

	public Long getDb_id() {
		return db_id;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public void setSubtitle(String subtitle) {
		this.title = subtitle;
	}

	public void setThumbnail(Bitmap thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setDb_id(Long db_id) {
		this.db_id = db_id;
	}

		
	
}//public class SRTItem
