package com.example.secondapp.controller.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.TextView;

import com.example.secondapp.R;

public class AboutUsActivity extends Fragment {
    private TextView  email;
    private  TextView phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_about,
                container, false);
        email = (TextView)view.findViewById(R.id.emailC);
        phone = (TextView)view.findViewById(R.id.phoneC);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Phone = phone.getText().toString().replaceAll("-" , "");
                Intent intentCall = new Intent(Intent.ACTION_CALL);
                intentCall.setData(Uri.parse(Phone));
                intentCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentCall);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,Email);
                startActivity(Intent.createChooser(intent,"Choose Email please:"));

            }
        });

        return view;


    }

    }
