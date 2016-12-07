package com.steve.strongpass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.BigInteger;
import java.security.SecureRandom;

public class AddAccount extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        Button addButton = (Button) findViewById(R.id.addAccount_addButton);
        Button generate = (Button) findViewById(R.id.addAccount_generateButton);
        final EditText passwordInput = (EditText) findViewById(R.id.addAccount_passEdit);
        final TextView genPass = (TextView) findViewById(R.id.addAccount_randomString);
        final TextView crackTime = (TextView) findViewById(R.id.addAccount_timeText);
        final TextView take = (TextView) findViewById(R.id.addAccount_takeText);
        final TextView brute = (TextView) findViewById(R.id.addAccount_brute);
        final ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar4);
        take.setVisibility(View.INVISIBLE);
        brute.setVisibility(View.INVISIBLE);
       // bar.setProgressTintList();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbHelper = new DatabaseHelper(AddAccount.this);
                EditText account = (EditText) findViewById(R.id.addAccount_nameEdit);
                EditText username = (EditText) findViewById(R.id.addAccount_userEdit);
                EditText password = (EditText) findViewById(R.id.addAccount_passEdit);
                EditText description = (EditText) findViewById(R.id.addAccount_descriptionEdit);

                dbHelper.insertAccount(account.getText().toString(), username.getText().toString(), password.getText().toString(), description.getText().toString());
                Intent intent = new Intent(AddAccount.this, AccountList.class);
                startActivity(intent);
            }
        });


        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String randomPass = Crypto.generate();
                genPass.setText(randomPass);
            }
        });

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                take.setVisibility(View.VISIBLE);
                brute.setVisibility(View.VISIBLE);
                crackTime.setText(Crypto.timeToCrack(passwordInput.getText().toString().length()));
                if(passwordInput.getText().toString().length() == 0){
                    take.setVisibility(View.INVISIBLE);
                    brute.setVisibility(View.INVISIBLE);
                }
                bar.setProgress(Crypto.strength(passwordInput.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        genPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View c) {
                setClipboard(genPass.getText().toString());
                Toast.makeText(getApplicationContext(), "Password Copied", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @SuppressWarnings("deprecation")
    private void setClipboard(String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }
}
