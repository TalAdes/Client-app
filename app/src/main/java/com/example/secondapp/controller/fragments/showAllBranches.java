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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.secondapp.R;
import com.example.secondapp.controller.adapters.BranchListAdapter;
import com.example.secondapp.models.backend.Consts;
import com.example.secondapp.models.backend.Factory;
import com.example.secondapp.models.backend.IDB;
import com.example.secondapp.models.backend.Services;
import com.example.secondapp.models.entities.Branch;

import java.util.ArrayList;

public class showAllBranches extends android.support.v4.app.Fragment {

    private ListView listView;
    private SearchView searchCity;
    private ArrayList<Branch> branches;

    IDB db = Factory.getMnager();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_branches_list,container,false);
        final Context context = this.getActivity();
        searchCity = view.findViewById(R.id.branchSearch);
        listView = (ListView)view.findViewById(R.id.all_branches_list_view);

        listView.setOnItemClickListener(new ListView.OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Branch selectedBranch = (Branch) listView.getItemAtPosition(position);
                String city = selectedBranch.getCity();
                String street = selectedBranch.getStreet();
                intent.setData(Uri.parse("geo:0,0?q= " + street + " " + city + ", Israel"));
                startActivity(intent);
            }
        });

        searchCity.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Branch> relevantBranches = new ArrayList<Branch>();
                for (Branch branch:branches)
                {
                    if (branch.getCity().contains(s))
                        relevantBranches.add(branch);
                }
                BranchListAdapter a = new BranchListAdapter(relevantBranches,getContext());
                listView.setAdapter(a);
                return false;
            }
        });

        Intent intent = new Intent(getContext(), Services.class);
        getActivity().startService(intent);


        new AsyncTask<Void,Void,ArrayList<Branch>>(){
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
            protected void onPostExecute(ArrayList<Branch> b) {
                dialog.dismiss();
                if(b!=null)
                {
                    BranchListAdapter a = new BranchListAdapter(b,getContext());
                    listView.setAdapter(a);
                }
                else{
                    Toast.makeText(getContext(),"have a problam to load....",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected ArrayList<Branch> doInBackground(Void... voids) {
                try{
                    branches = Consts.CursorToListBranch(db.getBranches());
                }
                catch (Exception error)
                {
                }
                return branches;

            }
        }.execute();


        return view;
    }

    private void dummy() {
        int[] a = new int[4];
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
