package com.example.secondapp.controller.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.secondapp.R;
import com.example.secondapp.models.entities.Branch;

import java.util.List;

/**
 * Created by טל on 28-Nov-17.
 */

public class BranchListAdapter extends BaseAdapter {

    List<Branch> branchesList;
    Context context;

    public BranchListAdapter(List<Branch> BranchesList, Context Context) {
        this.branchesList = BranchesList;
        this.context = Context;
    }


    @Override
    public int getCount() {
        return branchesList.size();
    }

    @Override
    public Object getItem(int position) {
        return branchesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(context, R.layout.branch_next, null);

        TextView idTB = (TextView) convertView.findViewById(R.id.id_TB);
        TextView parkingTB = (TextView) convertView.findViewById(R.id.numParking_TB);
        TextView cityTB = (TextView) convertView.findViewById(R.id.city_TB);
        TextView streetTB = (TextView) convertView.findViewById(R.id.street_TB);
        TextView apartmentTB = (TextView) convertView.findViewById(R.id.apartmentNum_TB);
        ImageView imageB = (ImageView)convertView.findViewById(R.id.branchImage);

        idTB.setText(((Long) branchesList.get(position).getIdBranch()).toString());
        parkingTB.setText(((Integer)branchesList.get(position).getNumParking()).toString());
        cityTB.setText(branchesList.get(position).getCity().toString());
        streetTB.setText((branchesList.get(position).getStreet()).toString());
        apartmentTB.setText(((Integer)branchesList.get(position).getNumApart()).toString());
        Glide.with(context).load(branchesList.get(position).getUrlImage()).error(R.drawable.camera_icon).override(300,400).centerCrop().into(imageB);

        return convertView;
    }
}
