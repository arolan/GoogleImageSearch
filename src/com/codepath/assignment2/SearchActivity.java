package com.codepath.assignment2;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	private String imageSize;
	private String colorFilter;
	private String imageType;
	private String siteFilter;
	private int currentPage;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        this.imageSize = "medium";
        this.colorFilter = "blue";
        this.imageType = "photo";
        this.siteFilter = "";
        this.currentPage = 0;
        
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
        	
		});
        
        gvResults.setOnScrollListener(new EndlessScrollListener() {
    	    @Override
    	    public void onLoadMore(int page, int totalItemsCount) {
                    // Whatever code is needed to append new items to your AdapterView
                    // probably sending out a network request and appending items to your adapter. 
                    // Use the page or the totalItemsCount to retrieve correct data.
    	    		currentPage = page;
    	    		loadMoreResults(currentPage); 
                    // or customLoadMoreDataFromApi(totalItemsCount); 
    	    }

			
            });
    }

    public void loadMoreResults(int page) {
    	String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		String urlRequestStr = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8" +
		"&start=" + page + 
		"&imgsz=" + this.imageSize + 
		"&imgcolor=" + this.colorFilter +
		"&imgtype=" + this.imageType +
		"&as_sitesearch=" + this.siteFilter +
		"&v=1.0&q=" + Uri.encode(query);
		
		Log.d("DEBUG", "URL requested = " + urlRequestStr);
		
		client.get(urlRequestStr, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					imageAdapter.notifyDataSetChanged();
					Log.d("DEBUG", "image results = " + imageResults.toString());
				}
				catch(JSONException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
    
    private void setupViews() {
    	etQuery = (EditText) findViewById(R.id.etQuery);
    	gvResults = (GridView) findViewById(R.id.gvResults);
    	btnSearch = (Button) findViewById(R.id.btnSearch);
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }
	
	public void onImageSearch(View v)
	{
		imageResults.clear();
		this.loadMoreResults(this.currentPage);
	}
    
	
	public void showSearchSettings(MenuItem mi) {
		Intent i = new Intent(this, SearchSettingsActivity.class);
		i.putExtra("imageSize", this.imageSize);
		i.putExtra("colorFilter", this.colorFilter);
		i.putExtra("imageType", this.imageType);
		i.putExtra("siteFilter", this.siteFilter);
		startActivityForResult(i, 0);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == 0) {
    		if (resultCode == RESULT_OK) {
    			this.imageSize = data.getStringExtra("imageSize");
    			this.colorFilter = data.getStringExtra("colorFilter");
    			this.imageType = data.getStringExtra("imageType");
    			this.siteFilter = data.getStringExtra("siteFilter");
//    			Toast.makeText(this, this.siteFilter, Toast.LENGTH_LONG).show();
			}
		}
    }
	
}
