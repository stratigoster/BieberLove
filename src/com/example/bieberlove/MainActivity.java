package com.example.bieberlove;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	// Called when an options item is clicked
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemServiceStart:
			startService(new Intent(this, UpdaterService.class));
			break;
		case R.id.itemServiceStop:
			stopService(new Intent(this, UpdaterService.class));
			break;
		case R.id.itemTimeline:
			// startActivity(new Intent(this, TimelineActivity.class));
			onRestart();
			break;
		}
		return true;
	}
}
