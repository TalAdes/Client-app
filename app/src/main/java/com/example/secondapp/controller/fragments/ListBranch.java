package com.example.secondapp.controller.fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.secondapp.controller.adapters.BranchExpandableAdapter;
import com.example.secondapp.models.backend.Consts;
import com.example.secondapp.models.backend.Factory;
import com.example.secondapp.models.backend.IDB;
import com.example.secondapp.models.backend.Services;
import com.example.secondapp.models.entities.Branch;
import com.example.secondapp.models.entities.Car;

import com.example.secondapp.R;

import java.util.HashMap;
import java.util.List;

public class ListBranch extends android.support.v4.app.Fragment {

    private ExpandableListView expand;
    private SearchView searchCity;
    private BranchExpandableAdapter branchEx;
    private List<Branch> branches;
    private HashMap<Long,List<Car>> map;

    IDB db = Factory.getMnager();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.branch_list,container,false);
        final Context context = this.getActivity();
        searchCity = view.findViewById(R.id.branchSearch);

        expand = (ExpandableListView)view.findViewById(R.id.expandAble);
        new AsyncTask<Void,Void,Integer>(){
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

        expand.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int iChild, long id) {

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

    private static BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

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
}
