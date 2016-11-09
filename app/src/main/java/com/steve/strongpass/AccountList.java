package com.steve.strongpass;

import android.app.Activity;
import android.app.ListActivity;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import static android.R.id.list;

public class AccountList extends ListActivity {

    private DatabaseHelper databaseHelper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        databaseHelper = new DatabaseHelper(this);
        cursor = databaseHelper.getAccountListCursor();

        // listViewCursorAdapter = new ListViewCursorAdapter(this, cursor, 0);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, new String[]{"account", "description"},
                new int[] {android.R.id.text1, android.R.id.text2}, 0);
        this.setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView listView, View view, int pos, long id){
        super.onListItemClick(listView, view, pos, id);
        Intent intent = new Intent(this, ViewPassword.class);
        intent.putExtra("id", pos + 1);                                 // id is unique identifier for accounts in database - primary key
        startActivity(intent);

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
