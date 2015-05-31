package com.example.bieberlove;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import winterwell.jtwitter.OAuthSignpostClient;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	
	static final String TAG = "UpdaterService";
	static final int DELAY = 30000; // 1/2 a min
	
	private static final String JTWITTER_OAUTH_KEY = "CONSUMER_KEY";
	private static final String JTWITTER_OAUTH_SECRET = "CONSUMER_SECRET";
	private static final String JTWITTER_ACCESS_KEY = "ACCESS_TOKEN_KEY";
	private static final String JTWITTER_ACCESS_SECRET = "ACCESS_TOKEN_SECRET";

	UpdaterRunnable updaterRunnable;
	Handler handler;
	Twitter twitter;

	DbHelper dbHelper;
	SQLiteDatabase db;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// open the database

		dbHelper = new DbHelper(this);
		db = dbHelper.getWritableDatabase();

		handler = new Handler();
		updaterRunnable = new UpdaterRunnable();
		handler.post(updaterRunnable);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// Cleanup handler & runnable
		handler.removeCallbacks(updaterRunnable);
		updaterRunnable = null;
		handler = null;

		// Close the database
		db.close();
	}

	class UpdaterRunnable implements Runnable {
		public void run() {
			pullFromTwitter();
			Log.d(TAG, "UpdaterRunnable run'd");
			// Do this again
			handler.postDelayed(updaterRunnable, DELAY);
		}
	}

	// The actual work of connecting to twitter and getting latest data
	private void pullFromTwitter() {
		new MyTask().execute();
	}

	private class MyTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			List<winterwell.jtwitter.Twitter.Status> timeline = null;

			try {
				OAuthSignpostClient client = new OAuthSignpostClient(
						JTWITTER_OAUTH_KEY, JTWITTER_OAUTH_SECRET,
						JTWITTER_ACCESS_KEY, JTWITTER_ACCESS_SECRET);
				twitter = new Twitter("", client);
				timeline = twitter.search("#bieber");
			} catch (TwitterException e) {
				e.printStackTrace();
			}

			ContentValues values = new ContentValues();
			for (winterwell.jtwitter.Twitter.Status status : timeline) {
				values.put(DbHelper.C_PROFILE_IMAGE_URL,
						status.user.profileImageUrl.toString());
				values.put(DbHelper.C_CREATED_AT, status.createdAt.getTime());
				values.put(DbHelper.C_SOURCE, status.source);
				values.put(DbHelper.C_TEXT, status.text);
				values.put(DbHelper.C_USER, status.user.screenName);
				try {
					db.insertOrThrow(DbHelper.TABLE, null, values);
					Log.d(TAG, String.format("%s: %s", status.user.screenName,
							status.text));
				} catch (SQLException e) {

				}
			}
			return null;
		}
	}
}
