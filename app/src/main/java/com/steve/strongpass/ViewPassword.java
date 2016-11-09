package com.steve.strongpass;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ClipboardManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPassword extends Activity {

    private DatabaseHelper databaseHelper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_password);

        // Back button in action bar
        ActionBar actionBar = getActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_up);
        actionBar.setDisplayHomeAsUpEnabled(true);


        databaseHelper = new DatabaseHelper(this);
        cursor = databaseHelper.getAccountDetails(getIntent().getExtras().getInt("id"));
        cursor.moveToFirst();

        // Set activity label programmatically
        setTitle(cursor.getString(1));

        final TextView username = (TextView) findViewById(R.id.accountDetails_usernameClick);
        username.setText(cursor.getString(2));
        username.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View c){
                setClipboard(username.getText().toString());
                Toast.makeText(getApplicationContext(), "Username Copied",Toast.LENGTH_SHORT).show();
            }
        });

        final TextView password = (TextView) findViewById(R.id.accountDetails_passwordClick);
        password.setText(cursor.getString(3));
        password.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View c){
                setClipboard(password.getText().toString());
                Toast.makeText(getApplicationContext(), "Password Copied",Toast.LENGTH_SHORT).show();
            }
        });

        final TextView description = (TextView) findViewById(R.id.accountDetails_descriptionClick);
        description.setText(cursor.getString(4));
        description.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View c){
                setClipboard(description.getText().toString());
                Toast.makeText(getApplicationContext(), "Description Copied",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home ) {
            finish();
            return true;
        }
        // other menu select events may be present here

        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("deprecation")
    private void setClipboard(String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }
}
