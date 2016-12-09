package com.steve.strongpass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends Activity {


    private EditText passwordInput;
    private ProgressBar progressBar;
    private TextView passwordStrength;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final TextView upper = (TextView) findViewById(R.id.upper);
        final TextView lower = (TextView) findViewById(R.id.lower);
        final TextView number = (TextView) findViewById(R.id.number);
        final TextView symbol = (TextView) findViewById(R.id.symbol);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        passwordInput = (EditText) findViewById(R.id.masterKeyInput);

        passwordStrength = (TextView) findViewById(R.id.passwordStrengthText);

        progressBar.setVisibility(View.INVISIBLE);

        passwordStrength.setText(getResources().getString(R.string.requirements));
        passwordInput.setHint(R.string.passwordHint);

        passwordInput.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String currentPassword = passwordInput.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                if(currentPassword.length() == 0){
                    passwordStrength.setVisibility(View.INVISIBLE);
//                    passwordStrength.setTextColor(ResourcesCompat.getColor(getResources(), R.color.green, null));
//                    passwordStrength.setText(getResources().getString(R.string.requirements));
                }
                else{
                    passwordStrength.setVisibility(View.VISIBLE);
                    if(Crypto.hasUpper(currentPassword))
                        upper.setTextColor(ResourcesCompat.getColor(getResources(), R.color.green, null));
                    else
                        upper.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                    if(Crypto.hasLower(currentPassword))
                        lower.setTextColor(ResourcesCompat.getColor(getResources(), R.color.green, null));
                    else
                        lower.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                    if(Crypto.hasNumber(currentPassword))
                        number.setTextColor(ResourcesCompat.getColor(getResources(), R.color.green, null));
                    else
                        number.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                    if(Crypto.hasSymbol(currentPassword))
                        symbol.setTextColor(ResourcesCompat.getColor(getResources(), R.color.green, null));
                    else
                        symbol.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));

                    if(meetsRequirements(currentPassword)) {
                        int strength = calculateStrength(currentPassword);
                        progressBar.setProgress(strength);
                        passwordStrength.setText(getStrengthText(strength));
                    }
                    else{
                        progressBar.setProgress(0);
                        passwordStrength.setText(getResources().getString(R.string.invalid));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void register(View view){
        String password = passwordInput.getText().toString();
        if(meetsRequirements(password)){
            SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPrefs), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            byte[] salt  = Crypto.generateSalt();
            String hash = Crypto.generateHash(password, salt);
            editor.putString(getResources().getString(R.string.masterPasswordKey), hash);
            editor.putString("salt", Base64.encodeToString(salt, Base64.NO_WRAP));
            editor.commit();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid), Toast.LENGTH_SHORT).show();
        }
    }

    public int calculateStrength(String password){
        int length = password.length();
        if(length < 5){
            return 25;
        }
        else if(length < 7){
            return 50;
        }
        else if(length < 9){
            return 75;
        }
        else
            return 100;
    }

    public boolean meetsRequirements(String password){
        int length = 0, uppercase = 0, lowercase = 0, digits = 0, symbols = 0;

        for(int i=0; i < password.length(); i++){
            if (Character.isUpperCase(password.charAt(i)))
                uppercase++;
            else if (Character.isLowerCase(password.charAt(i)))
                lowercase++;
            else if (Character.isDigit(password.charAt(i)))
                digits++;

            length++;
        }

        symbols = length - uppercase - lowercase - digits;

        if(length < 4 || uppercase < 1 || lowercase < 1 || digits < 1 || symbols < 1){
            return false;
        }

        return true;
    }

    public String getStrengthText(int strength){
        if(strength < 26){
            return getResources().getString(R.string.weak);
        }
        else if(strength < 51){
            return getResources().getString(R.string.adequate);
        }
        else if(strength < 76){
            return getResources().getString(R.string.strong);
        }
        else
            return getResources().getString(R.string.veryStrong);
    }
}

