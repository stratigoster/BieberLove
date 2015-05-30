package com.example.bieberlove;

import android.support.v7.app.ActionBarActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	
	ListView listTimeline;
	DbHelper dbHelper;
	SQLiteDatabase db;
	Cursor cursor;
	TimelineAdapter adapter; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listTimeline = (ListView) findViewById(R.id.listTimeline);
		
		 // Connect to database
	    dbHelper = new DbHelper(this);
	    db = dbHelper.getReadableDatabase();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		  // Get the data from the database
	    cursor = db.query(DbHelper.TABLE, null, null, null, null, null,
	        DbHelper.C_CREATED_AT + " DESC");         

	    // Create the adapter
	    adapter = new TimelineAdapter(this, cursor); 
	    listTimeline.setAdapter(adapter);  
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
