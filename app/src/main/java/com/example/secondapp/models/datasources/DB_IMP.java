package com.example.secondapp.models.datasources;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.secondapp.models.backend.Consts;
import com.example.secondapp.models.backend.IDB;
import com.example.secondapp.models.entities.Branch;
import com.example.secondapp.models.entities.Car;
import com.example.secondapp.models.entities.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import static com.example.secondapp.models.backend.Consts.httpGet;

/**
 * Created by טל on 17-Jan-18.
 */

public class DB_IMP implements IDB {
    @Override public void dummyOperation() {
        int[] a = new int[4];

        /*
        this op made to help debug AsyncTasks
        */

    }

    @Override    public String addClient(ContentValues values) {
        String str = Consts.httpPost("http://tades.vlab.jct.ac.il/setClients.php?",values.valueSet());
        return str;
    }
    @Override    public String addOrder(ContentValues values) {
        String str = Consts.httpPost("http://tades.vlab.jct.ac.il/addOrder.php?",values.valueSet());
        return str;
    }

    @Override    public Cursor getClients()     throws Exception    {return Consts.getClients();}
    @Override    public Cursor getBranches()    throws Exception    {return Consts.getBranches();}
    @Override    public Cursor getCars()        throws Exception    {return Consts.getCars();}
    @Override    public Cursor getCarModels()   throws Exception    {return Consts.getCarModels();}
    @Override    public Cursor getOrders()      throws Exception    {return Consts.getOrders();}


    @Override public Cursor getAvailableCars() throws Exception {return Consts.getAvailableCars();}
    @Override public Cursor getAvailableCarsByBranch(int id) throws Exception {return Consts.getAvailableCarsByBranch(id);}
    @Override public Cursor getBranchesWhereCarModelAvailable(int id) throws Exception {return Consts.getBranchesWhereCarModelAvailable(id);}
    @Override public Cursor getOpenOrders()  throws Exception {return Consts.getOpenOrders();}

    @Override
    public void closeOrder(int orderID, int kilometers) throws Exception {
        JSONArray array = new JSONObject(httpGet("http://tades.vlab.jct.ac.il/getOrderByID.php?"+"orderID=\""+orderID+"\"")).getJSONArray("order");
        JSONObject obj = array.getJSONObject(0);
        Map<String,String> map = new HashMap<>();

        map.put((Consts.OrderConst.customerID),obj.getString(Consts.OrderConst.customerID));
        map.put((Consts.OrderConst.orderStatus),obj.getString(Consts.OrderConst.orderStatus));
        map.put((Consts.OrderConst.startDate),obj.getString(Consts.OrderConst.startDate));
        map.put((Consts.OrderConst.finishDate),obj.getString(Consts.OrderConst.finishDate));
        map.put((Consts.OrderConst.startKilometer),obj.getString(Consts.OrderConst.startKilometer));
        map.put((Consts.OrderConst.finishKilometer),obj.getString(Consts.OrderConst.finishKilometer));
        map.put((Consts.OrderConst.filedGas),obj.getString(Consts.OrderConst.filedGas));
        map.put((Consts.OrderConst.finallyCalculatedPrice),obj.getString(Consts.OrderConst.finallyCalculatedPrice));
        map.put((Consts.OrderConst.orderID),obj.getString(Consts.OrderConst.orderID));
        map.put((Consts.OrderConst.carID),obj.getString(Consts.OrderConst.carID));

        Calendar e = Calendar.getInstance(TimeZone.getTimeZone("GMT+2"));

        int year = e.get(Calendar.YEAR);
        int mon = e.get(Calendar.MONTH);
        int day = e.get(Calendar.DAY_OF_MONTH);
        int hour = e.get(Calendar.HOUR_OF_DAY);
        int min = e.get(Calendar.MINUTE);

    }


    @Override
    public Order closedWithin10Seconds() {
        return null;
    }

    @Override public Cursor getAvailableByDistance() {
        return null;
    }

    @Override
    public  HashMap<Long,List<Car>> getMapAvailableCarsByBranch() throws Exception
    {
        Cursor Branches = Consts.getBranches();
        HashMap<Long,List<Car>> mapIdBranchWithCar = new HashMap<Long,List<Car>>();
        Branches.moveToFirst();
        while (!Branches.isAfterLast()){
            long id = Branches.getLong(Branches.getColumnIndexOrThrow(Consts.BranchConst.ID));
            mapIdBranchWithCar.put(id,Consts.CursorToListCar(Consts.getAvailableCarsByBranch(id)));

            Branches.moveToNext();
        }


        return mapIdBranchWithCar;
    }
}
