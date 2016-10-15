package com.steve.strongpass;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddAccount extends Activity{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        Button addButton = (Button) findViewById(R.id.addAccount_addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbHelper = new DatabaseHelper(AddAccount.this);
                EditText account = (EditText) findViewById(R.id.addAccount_nameEdit);
                EditText username= (EditText) findViewById(R.id.addAccount_userEdit);
                EditText password= (EditText) findViewById(R.id.addAccount_passEdit);
                // EditText description= (EditText) findViewById(R.id.addAccount_nameEdit);

                dbHelper.insertAccount(account.getText().toString(), username.getText().toString(), password.getText().toString());
                Intent intent = new Intent(AddAccount.this, AccountList.class);
                startActivity(intent);
            }
        });


    }

}
