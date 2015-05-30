package com.example.bieberlove;

import android.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	static final String TAG = "DbHelper";
	static final String DB_NAME = "timeline.db"; // <2>
	static final int DB_VERSION = 1; // <3>
	static final String TABLE = "timeline"; // <4>
	static final String C_ID = "_id";
	static final String C_CREATED_AT = "created_at";
	static final String C_SOURCE = "source";
	static final String C_TEXT = "txt";
	static final String C_USER = "user";
	static final String C_PROFILE_IMAGE_URL = "url";
	Context context;

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE timeline ( _id integer primary key,created_at integer, source text, txt text, user text, url text )";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + TABLE);
		onCreate(db);
	}

}
