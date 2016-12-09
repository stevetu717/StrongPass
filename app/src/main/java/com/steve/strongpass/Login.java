package com.steve.strongpass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

    private static int attempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void authenticateUser(View view){
        Button loginButton = (Button) findViewById(R.id.button);
        SharedPreferences sharedpreferences = getSharedPreferences(getResources().getString(R.string.sharedPrefs), Context.MODE_PRIVATE);
        EditText passwordTextField = (EditText) findViewById(R.id.passwordInput);

        String passwordInput = passwordTextField.getText().toString();
        byte[] salt = Base64.decode(sharedpreferences.getString("salt", null), Base64.NO_WRAP);
        String masterPassword = sharedpreferences.getString(getResources().getString(R.string.masterPasswordKey), null);
        String hashedPass = Crypto.generateHash(passwordInput, salt);


        if(attempts > 10) {
            passwordTextField.setFocusable(false);
            Toast.makeText(getApplicationContext(), "Locked Out : Try Again Later", Toast.LENGTH_SHORT).show();
            loginButton.setClickable(false);
        }
        else if(masterPassword.equals(hashedPass)){
            attempts = 0;
            loginButton.setClickable(true);
            Intent intent = new Intent(this, AccountList.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Incorrect Password",Toast.LENGTH_SHORT).show();
            attempts++;
        }
    }
}
