package com.example.secondapp.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.secondapp.R;
import com.example.secondapp.models.entities.Branch;
import com.example.secondapp.models.entities.Car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liran on 20/01/2018.
 */

public class BranchExpandableAdapter extends BaseExpandableListAdapter implements Filterable {
    private Context context;
    private List<Branch> dataHader;
    private List<Branch> dataHeaderOrig;
    private HashMap<Long,List<Car>> ListHashMap;

    public BranchExpandableAdapter(Context cont, List<Branch> listDateheder, HashMap<Long, List<Car>> listChildData)
    {
        this.context = cont;
        this.dataHader = listDateheder;
        this.dataHeaderOrig = listDateheder;
        this.ListHashMap = listChildData;
    }

    @Override
    public int getGroupCount() {
        return dataHader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        long id = getGroupId(i);
        return (ListHashMap.get(id)).size();
    }

    @Override
    public Object getGroup(int i) {
        return dataHader.get(i);
    }

    @Override
    public Object getChild(int i, int iChild) {
        long id = getGroupId(i);
        return (ListHashMap.get(id)).get(iChild);
    }

    @Override
    public long getGroupId(int i) {
        return ((Branch)getGroup(i)).getIdBranch();
    }

    @Override
    public long getChildId(int i, int iChild) {
        return ((Car)getChild(i,iChild)).getIdCar();
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        Branch branch = (Branch)this.getGroup(i);
        if(view == null)
        {
            LayoutInflater layout = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layout.inflate(R.layout.branch_next,null);
        }
        TextView IdBranch = (TextView)view.findViewById(R.id.id_TB);
        IdBranch.setText(String.valueOf(branch.getIdBranch()));

        TextView city = (TextView)view.findViewById(R.id.city_TB);
        city.setText(branch.getCity());

        TextView street = (TextView)view.findViewById(R.id.street_TB);
        street.setText(branch.getStreet());

        TextView numApart = (TextView)view.findViewById(R.id.apartmentNum_TB);
        numApart.setText(String.valueOf(branch.getNumApart()));

        TextView numPark = (TextView)view.findViewById(R.id.numParking_TB);
        numPark.setText(String.valueOf(branch.getNumParking()));

        ImageView imageB = (ImageView)view.findViewById(R.id.branchImage);
        Glide.with(context).load(branch.getUrlImage()).error(R.drawable.camera_icon).override(300,400).centerCrop().into(imageB);

        return view;


    }

    @Override
    public View getChildView(int i, int iChild, boolean b, View view, ViewGroup viewGroup) {
        Car car = (Car)this.getChild(i,iChild);

        if(view == null)
        {
            LayoutInflater layout = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layout.inflate(R.layout.row_car,null);
        }
        TextView idCar = (TextView)view.findViewById(R.id.CarID_TB2);
        idCar.setText(String.valueOf(car.getIdCar()));

        TextView modelIdCar = (TextView)view.findViewById(R.id.modelId_TB2);
        modelIdCar.setText(String.valueOf(car.getIdTypeModel()));

        TextView modelNameCar = (TextView)view.findViewById(R.id.modelName_TB2);
        modelNameCar.setText(car.getModel());

        TextView kilometer = (TextView)view.findViewById(R.id.kilometer_TB2);
        kilometer.setText(String.valueOf(car.getKilometer()));

        ImageView imageC = (ImageView)view.findViewById(R.id.carImage);
        Glide.with(context).load(car.getUrlImage()).error(R.drawable.camera_icon).override(300,400).centerCrop().into(imageC);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                List<Branch> FilteredArrayNames = new ArrayList<Branch>();

                charSequence = charSequence.toString().toLowerCase();

                for (Branch branch : dataHeaderOrig) {
                    if (branch.getCity().toLowerCase().contains(charSequence))
                        FilteredArrayNames.add(branch);
                }

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;


                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataHader = (List<Branch>) filterResults.values;
                notifyDataSetChanged();
            }
        };

        }
    }

