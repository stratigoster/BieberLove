package com.example.bieberlove;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;

public class TimelineAdapter extends SimpleCursorAdapter{

	static final String[] from = { DbHelper.C_CREATED_AT, DbHelper.C_USER, DbHelper.C_TEXT,  DbHelper.C_PROFILE_IMAGE_URL }; 
	static final int[] to = { R.id.textCreatedAt, R.id.textUser, R.id.textText, R.id.avatar};
	  
	public TimelineAdapter(Context context, Cursor c) { 
		super(context,R.layout.row, c, from, to, 0);
	}

	//this is where you bind a cursor to a view
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		super.bindView(view, context, cursor);
	}
}