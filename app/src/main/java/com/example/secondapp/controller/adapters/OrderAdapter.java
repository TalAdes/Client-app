package com.example.secondapp.controller.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.secondapp.R;

/**
 * Created by טל on 04-Feb-18.
 */

public class OrderAdapter extends CursorAdapter {
    Cursor cursor;
    Context context;
    public OrderAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.cursor = cursor;
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.order_template,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        /*
        bind data to widgets
        */

    }
}
