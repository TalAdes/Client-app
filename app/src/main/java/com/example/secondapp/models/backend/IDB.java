package com.example.secondapp.models.backend;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.secondapp.models.entities.Car;
import com.example.secondapp.models.entities.Order;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by טל on 17-Jan-18.
 */

public interface IDB {
    void dummyOperation();
    void setClient(int client);
    String addClient(ContentValues values);
    String addOrder(ContentValues values);

    Cursor getClients()     throws Exception;
    Cursor getBranches()    throws Exception;
    Cursor getCars()        throws Exception;
    Cursor getCarModels()   throws Exception;
    Cursor getOrders()   throws Exception;

    Cursor  getAvailableCars() throws Exception;
    Cursor  getAvailableCarsByBranch(int id) throws Exception;
    Cursor  getBranchesWhereCarModelAvailable(int id) throws Exception;
    Cursor  getOpenOrders() throws Exception;
    HashMap<Long,List<Car>> getMapAvailableCarsByBranch() throws Exception;

    //nedd to imp
    void closeOrder(int OrderID , int kilometers) throws Exception;
    Order   closedWithin10Seconds();
    Cursor  getAvailableByDistance();





}
