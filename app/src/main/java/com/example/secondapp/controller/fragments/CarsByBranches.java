package com.example.secondapp.controller.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.secondapp.controller.adapters.BranchExpandableAdapter;
import com.example.secondapp.models.backend.Consts;
import com.example.secondapp.models.backend.Factory;
import com.example.secondapp.models.backend.IDB;
import com.example.secondapp.models.backend.Services;
import com.example.secondapp.models.datasources.DB_IMP;
import com.example.secondapp.models.entities.Branch;
import com.example.secondapp.models.entities.Car;

import com.example.secondapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

public class CarsByBranches extends android.support.v4.app.Fragment {

    private ExpandableListView expand;
    private SearchView searchCity;
    private BranchExpandableAdapter branchEx;
    private List<Branch> branches;
    private HashMap<Long,List<Car>> map;
    private ProgressDialog dialog;
    private String imageURL;

    IDB db = Factory.getManager();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.branch_list,container,false);
        final Context context = this.getActivity();
        searchCity = view.findViewById(R.id.branchSearch);

        expand = (ExpandableListView)view.findViewById(R.id.expandAble);
        new AsyncTask<Void,Void,Integer>(){

            @Override
            protected void onPreExecute() {
                dialog = new ProgressDialog(context);
                dialog.setMessage("Please wait....");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected void onPostExecute(Integer b) {
                super.onPostExecute(b);

                dialog.dismiss();
                if(b == 1)
                {
                    branchEx = new BranchExpandableAdapter(getContext(),branches,map);
                    expand.setAdapter(branchEx);
                    filter();
                }
                else{
                    Toast.makeText(getContext(),"have a problam to load....",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected Integer doInBackground(Void... voids) {
                try{
                    map = db.getMapAvailableCarsByBranch();
                    branches = Consts.CursorToListBranch(db.getBranches());
                    return 1;
                }
                catch (Exception error)
                {
                    return 0;
                }

            }
        }.execute();

        expand.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int iChild, long id)
            {

                final Dialog dialog1 = new Dialog(context);
                View convertView = View.inflate(context, R.layout.order_template, null);
                dialog1.setContentView(convertView);

                Branch branch = (Branch) branchEx.getGroup(i);
                final Car car = (Car) branchEx.getChild(i,iChild);

                TextView Branch = (TextView)convertView.findViewById(R.id.branch_TB2);
                final ImageView imageB = (ImageView)convertView.findViewById(R.id.carImage);
                final TextView carIdTB = (TextView) convertView.findViewById(R.id.CarID_TB2);
                final TextView modelIdTB = (TextView) convertView.findViewById(R.id.modelId_TB2);
                final TextView modelNameTB = (TextView) convertView.findViewById(R.id.modelName_TB2);
                final TextView kilometer = (TextView) convertView.findViewById(R.id.kilometer_TB2);
                final TextView price = (TextView) convertView.findViewById(R.id.price_TB2);
                final TextView date = (TextView) convertView.findViewById(R.id.startDate_TB2);
                Button order = (Button)convertView.findViewById(R.id.Order_Button);
                Button cancel = (Button)convertView.findViewById(R.id.Cancel_Button);

                Branch.setText(String.valueOf(String.valueOf(branch.getCity()+","+String.valueOf(branch.getStreet()+","+String.valueOf(branch.getNumApart())))));

                order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Consts.OrderConst.customerID, DB_IMP.client.getId());
                        contentValues.put(Consts.OrderConst.orderStatus,"open");
                        contentValues.put(Consts.OrderConst.startDate,date.getText().toString());
                        contentValues.put(Consts.OrderConst.finishDate,"null");
                        contentValues.put(Consts.OrderConst.startKilometer,kilometer.getText().toString());
                        contentValues.put(Consts.OrderConst.finishKilometer,car.getKilometer());
                        contentValues.put(Consts.OrderConst.filedGas,0);
                        contentValues.put(Consts.OrderConst.finallyCalculatedPrice,"null");
                        contentValues.put(Consts.OrderConst.orderID,"");
                        contentValues.put(Consts.OrderConst.carID,car.getIdCar());

                        db.addOrder(contentValues);
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            dialog1.hide();
                    }
                });


                new AsyncTask<Void, Void, Cursor>() {

                    @Override
                    protected Cursor doInBackground(Void... voids) {
                        List<Car> cars = new ArrayList<Car>();
                        Cursor cursor = null;
                        try {
                            cursor = db.getCarModels();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return  cursor;
                    }

                    @Override
                    protected void onPostExecute(Cursor cursor) {
                        super.onPostExecute(cursor);
                        cursor.moveToFirst();

                        while (!cursor.isAfterLast())
                        {

                            if(cursor.getLong(cursor.getColumnIndexOrThrow(Consts.CarModelConst.ID))==car.getIdTypeModel())
                            {
                                Calendar e = Calendar.getInstance(TimeZone.getTimeZone("GMT+2"));

                                String year = String.valueOf(e.get(Calendar.YEAR));
                                String mon = String.valueOf(e.get(Calendar.MONTH));
                                String day = String.valueOf(e.get(Calendar.DAY_OF_MONTH));

                                imageURL = cursor.getString(cursor.getColumnIndexOrThrow(Consts.CarModelConst.IMAGE));
                                carIdTB.setText(String.valueOf(car.getIdCar()));
                                modelIdTB.setText(String.valueOf(car.getIdTypeModel()));
                                modelNameTB.setText(car.getModel());
                                kilometer.setText(String.valueOf(car.getKilometer()));
                                date.setText(day+"/"+mon+"/"+year);
                                String s = String.valueOf(ThreadLocalRandom.current().nextInt(50, 200 + 1));
                                price.setText(s);
                                Glide.with(context).load("http://tades.vlab.jct.ac.il/"+ imageURL).error(R.drawable.camera_icon).override(600,400).centerCrop().into(imageB);
                                dialog1.show();
                                break;
                            }
                            cursor.moveToNext();
                        }
                        return;
                    }

                }.execute();

                return true;
            }
        });

        Intent intent = new Intent(getContext(), Services.class);
        getActivity().startService(intent);

        return view;
    }



    private void filter()
    {
        searchCity.setQuery("",true);
        searchCity.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                branchEx.getFilter().filter(s);
                return false;
            }
        });
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver()
    {
        @Override public void onReceive(Context context, Intent intent)
        {
            Toast.makeText(context,intent.getStringExtra("message"),Toast.LENGTH_SHORT).show();
            update();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(myReceiver,new IntentFilter(("com.secondapp.UPDATE")));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(myReceiver);
    }

    private void update()
    {
        final Context context = getActivity();
        new AsyncTask<Void,Void,Boolean>()
        {
            private ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = new ProgressDialog(context);
                dialog.setMessage("Please waite....");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                dialog.dismiss();
                if(aBoolean)
                {
                    branchEx = new BranchExpandableAdapter(getContext(),branches,map);
                    expand.setAdapter(branchEx);
                    filter();
                }
                else
                {
                    Toast.makeText(getContext(),"Problem open new order...", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                try{
                    map = db.getMapAvailableCarsByBranch();
                    branches = Consts.CursorToListBranch(db.getBranches());
                    return true;
                }


                catch (Exception error)
                {
                    return false;
                }
            }
        }.execute();


    }

}
