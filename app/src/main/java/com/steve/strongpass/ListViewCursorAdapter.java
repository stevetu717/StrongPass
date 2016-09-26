package com.steve.strongpass;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by Steve on 9/25/2016.
 */

public class ListViewCursorAdapter extends CursorAdapter{

    public ListViewCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // TODO Auto-generated method stub

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_account_list, parent, false);
    }
}
