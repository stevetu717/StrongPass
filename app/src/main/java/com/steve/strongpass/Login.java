package com.steve.strongpass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends FragmentActivity {
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void authenticateUser(View view){
        SharedPreferences sharedpreferences = getSharedPreferences(getResources().getString(R.string.sharedPrefs), Context.MODE_PRIVATE);
        EditText passwordTextField = (EditText) findViewById(R.id.passwordInput);

        String passwordInput = passwordTextField.getText().toString();
        String masterPassword = sharedpreferences.getString(getResources().getString(R.string.masterPasswordKey), null);

        if(passwordInput.equals(masterPassword)){
            Intent intent = new Intent(this, AccountList.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Incorrect Password",Toast.LENGTH_SHORT).show();
        }


    }
}
