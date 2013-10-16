package com.codepath.assignment2;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ImageResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4278879482686471184L;
	private String fullUrl;
	private String thumbUrl;
	
	public ImageResult(JSONObject json) 
	{
		try {
			this.thumbUrl = json.getString("tbUrl");
			this.fullUrl = json.getString("url");
		}
		catch(JSONException e) {
			this.thumbUrl = null;
			this.fullUrl = null;
		}
	}
	
	public String getFullUrl() {
		return fullUrl;
	}
	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	
	@Override
	public String toString() {
		return thumbUrl;
	}

	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray imageJsonResults) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int i = 0; i < imageJsonResults.length(); i++) {
			try {
				results.add(new ImageResult(imageJsonResults.getJSONObject(i)));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return results;
	}
	
	
}
