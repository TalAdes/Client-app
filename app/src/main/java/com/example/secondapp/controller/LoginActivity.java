package com.example.secondapp.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.secondapp.R;
import com.example.secondapp.models.backend.Consts;
import com.example.secondapp.models.backend.Factory;
import com.example.secondapp.models.backend.IDB;
import com.example.secondapp.models.entities.Client;

import java.util.regex.Pattern;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText userName;
    private EditText passwordT;
    //private EditText emailT;
    //private EditText userT;
    private Button loginButton;
    private Button createButton;
    private SharedPreferences sp;
    private String user;
    private String password;
    private String email;
    private IDB db = Factory.getManager();
    private boolean IsValidName=false,IsValidPassword=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sp = getSharedPreferences("UserDetails", MODE_PRIVATE);
        findViews();
    }

    private void findViews() {
        userName = (EditText)findViewById( R.id.userText );
        passwordT = (EditText)findViewById( R.id.passwordText );
        loginButton = (Button)findViewById( R.id.logInButton );
        createButton = (Button)findViewById(R.id.createBut);
        loginButton.setOnClickListener( this );
        loginButton.setEnabled(false);
        createButton.setOnClickListener(this);
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Pattern ptr = Pattern.compile("\\p{Blank}\t$",Pattern.CASE_INSENSITIVE);

                if(ptr.matcher(userName.getText().toString()).matches())
                    IsValidName=false;
                else    IsValidName=true;
                checkOthers();
            }
        });
        passwordT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Pattern ptr = Pattern.compile("\\p{Blank}\t$",Pattern.CASE_INSENSITIVE);

                if(ptr.matcher(passwordT.getText().toString()).matches())
                    IsValidPassword=false;
                else    IsValidPassword=true;
                checkOthers();
            }
        });
}

    @Override
    public void onClick(View v) {
        if ( v == loginButton ) {
            login();
        }

        if( v == createButton)
        {
            create();
        }

    }

    public void login(){
        user = userName.getText().toString();
        password = passwordT.getText().toString();



        new AsyncTask<Void,Void,Boolean>(){
            private ProgressDialog dialog;


            @Override
            protected void onPreExecute() {
                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("Please wait ...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setCancelable(false);
                dialog.show();
                String us = sp.getString("userName",null);
                Long pass = sp.getLong("password",-1);

                if(us == userName.getText().toString() && pass == Long.parseLong(passwordT.getText().toString()))
                {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                try{

                    if(Consts.vallidLogin(password,user))
                    {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("userName",user);
                        editor.putString("password", password);
                        editor.putString("logged", "logged");
                        editor.commit();
                        return true;
                    }
                    else
                        return false;

                }
                catch (Exception error)
                {
                    return false;
                }


            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                dialog.dismiss();
                if(aBoolean)
                {
                    db.dummyOperation();

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getBaseContext(),"Login failed",Toast.LENGTH_LONG).show();
            }
        }.execute();


    }

    public void create(){
        Intent intent = new Intent(this,NewAccountActivity.class);
        startActivity(intent);
    }

    private void checkOthers() {
        if (IsValidName&IsValidPassword)
        {
            boolean b =loginButton.isEnabled();
            loginButton.setEnabled(true);
        }
        else
        {
            boolean b =loginButton.isEnabled();
            loginButton.setEnabled(false);
        }
    }
}
