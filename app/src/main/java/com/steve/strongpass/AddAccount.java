package com.steve.strongpass;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigInteger;
import java.security.SecureRandom;

public class AddAccount extends Activity{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        Button addButton = (Button) findViewById(R.id.addAccount_addButton);
        Button generate = (Button) findViewById(R.id.addAccount_generateButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbHelper = new DatabaseHelper(AddAccount.this);
                EditText account = (EditText) findViewById(R.id.addAccount_nameEdit);
                EditText username= (EditText) findViewById(R.id.addAccount_userEdit);
                EditText password= (EditText) findViewById(R.id.addAccount_passEdit);
                EditText description= (EditText) findViewById(R.id.addAccount_descriptionEdit);

                dbHelper.insertAccount(account.getText().toString(), username.getText().toString(), password.getText().toString(), description.getText().toString());
                Intent intent = new Intent(AddAccount.this, AccountList.class);
                startActivity(intent);
            }
        });


        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String randomPass = generate();
                TextView genPass = (TextView) findViewById(R.id.addAccount_randomString);
                genPass.setText(randomPass);

            }
        });

    }

    private String generate(){
        SecureRandom random = new SecureRandom();
        StringBuilder pass = new StringBuilder();
        for(int i = 0; i < Crypto.passwordLength; i++){
            System.out.print(1);
            pass.append(Crypto.charSetArray[random.nextInt(Crypto.charSetArray.length)]);
        }

        return pass.toString();
    }
}
