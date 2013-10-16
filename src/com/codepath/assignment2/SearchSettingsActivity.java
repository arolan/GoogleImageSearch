package com.codepath.assignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class SearchSettingsActivity extends Activity {

	Spinner spImageSizes;
	Spinner spColorFilters;
	Spinner spImageTypes;
	EditText etSiteFilter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_settings);
		spImageSizes = (Spinner) findViewById(R.id.spImageSizes);
		spColorFilters = (Spinner) findViewById(R.id.spColorFilters);
		spImageTypes = (Spinner) findViewById(R.id.spnImageTypes);	
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		
		
		this.setSpinnerToValue(spImageSizes, this.getIntent().getStringExtra("imageSize"));
		this.setSpinnerToValue(spColorFilters, this.getIntent().getStringExtra("colorFilter"));
		this.setSpinnerToValue(spImageTypes, this.getIntent().getStringExtra("imageType"));
		etSiteFilter.setText(this.getIntent().getStringExtra("siteFilter"));
	}

	public void setSpinnerToValue(Spinner spinner, String value) {
		int index = 0;
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(value)) {
				index = i;
			}
		}
		spinner.setSelection(index);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_settings, menu);
		return true;
	}
	
	public void saveAdvancedSearchSettings(View v) {
		Intent i = new Intent();
		i.putExtra("imageSize", spImageSizes.getSelectedItem().toString());
		i.putExtra("colorFilter", spColorFilters.getSelectedItem().toString());
		i.putExtra("imageType", spImageTypes.getSelectedItem().toString());
		i.putExtra("siteFilter", etSiteFilter.getText().toString());
		setResult(RESULT_OK, i);
		finish();
	}
}
