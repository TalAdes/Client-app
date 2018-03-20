package com.example.secondapp.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.secondapp.R;
import com.example.secondapp.models.backend.Consts;
import com.example.secondapp.models.backend.Factory;
import com.example.secondapp.models.backend.IDB;

public class NewAccountActivity extends Activity implements View.OnClickListener {

    private EditText lnameEditText;
    private EditText fnameEditText;
    private EditText idEditText;
    private EditText phoneNumEditText;
    private EditText emailEditText;
    private EditText numCreditEditText;
    private Button addClientButton;
    private IDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account);
        db =Factory.getManager();
        findViews();
    }

    private void findViews() {
        fnameEditText = (EditText) findViewById(R.id.fnameEditText);
        lnameEditText = (EditText) findViewById(R.id.lnameEditText);
        idEditText = (EditText) findViewById(R.id.idEditText);
        phoneNumEditText = (EditText) findViewById(R.id.phoneNumEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        numCreditEditText = (EditText) findViewById(R.id.numCreditEditText);
        addClientButton = (Button) findViewById(R.id.clientButton);
        addClientButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if ( v == addClientButton ) {
            addClient();
        }
    }

    private void addClient()
    {
        final ContentValues contentValues = new ContentValues();


        contentValues.put(Consts.ClientConst.FIRST_NAME,this.fnameEditText.getText().toString());
        contentValues.put(Consts.ClientConst.LAST_NAME,this.lnameEditText.getText().toString());
        contentValues.put(Consts.ClientConst.ID,Long.valueOf(this.idEditText.getText().toString()));
        contentValues.put(Consts.ClientConst.PHONE_NUMBER,this.phoneNumEditText.getText().toString());
        contentValues.put(Consts.ClientConst.EMAIL,this.emailEditText.getText().toString());
        contentValues.put(Consts.ClientConst.NUM_CREDIT,this.numCreditEditText.getText().toString());

        new AsyncTask<Void,Void,String>() {
            String str;
            @Override
            protected String doInBackground(Void... voids) {
                str = db.addClient(contentValues);
                return str;
            }

            @Override
            protected void onPostExecute(String str) {
                if(str!="error")
                {
                    Toast.makeText(NewAccountActivity.this, str, Toast.LENGTH_SHORT).show();
                    finish();
                }
                else Toast.makeText(NewAccountActivity.this, "This client is already exists!", Toast.LENGTH_SHORT).show();
            }
        }.execute();


    }
}


