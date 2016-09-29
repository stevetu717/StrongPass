package com.steve.strongpass;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AccountList extends Activity {

    private DatabaseHelper databaseHelper;
    private ListView listView;
    private Cursor cursor;
    private ListViewCursorAdapter listViewCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        databaseHelper = new DatabaseHelper(this);
        cursor = databaseHelper.getAccountsCursor();

        // listViewCursorAdapter = new ListViewCursorAdapter(this, cursor, 0);
        ListAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"account"}, new int[] {android.R.id.text1});
        listView = (ListView) findViewById(R.id.accountList);
        listView.setAdapter(adapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_accountlist_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_addAccount:
                addAccount();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addAccount(){
        Intent intent = new Intent(this, AddAccount.class);
        startActivity(intent);
    }
}
