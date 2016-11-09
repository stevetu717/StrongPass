package com.steve.strongpass;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Steve on 9/25/2016.
 */

public class ListViewCursorAdapter{
/*
    private LayoutInflater cursorInflater;

    public ListViewCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView rowTitle = (TextView) view.findViewById(R.id.row_title);

        String title = cursor.getString( cursor.getColumnIndex( MyTable.COLUMN_TITLE ) );
        textViewTitle.setText(title);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_accountlist_rowlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.row_title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.row_icon);
        //textView.setText(values[position]);

        return rowView;
    }*/
}
