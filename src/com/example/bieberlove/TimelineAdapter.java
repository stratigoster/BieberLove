package com.example.bieberlove;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;

public class TimelineAdapter extends SimpleCursorAdapter{

	public TimelineAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to, 0);
		
	}

	//this is where you bind a cursor to a view
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		super.bindView(view, context, cursor);
	}
	
	
	

}
