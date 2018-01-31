package com.example.secondapp.models.backend;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.example.secondapp.models.entities.Branch;
import com.example.secondapp.models.entities.Car;
import com.example.secondapp.models.entities.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by טל on 17-Jan-18.
 */

public class Consts {

    public static String httpGet(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        if (con.getResponseCode()==HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();
            return response.toString();
        }
        else return "";
    }
    public static String httpPost(String url, Set<Map.Entry<String, Object>> params) {
        try{
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params)
            {
                if(postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(),"UTF-8"));
                postData.append("=\"");
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
                postData.append("\"");
            }

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(postData.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (con.getResponseCode()==HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);
                in.close();
                return response.toString();
            }
            else return "";
        }
        catch (Exception ex)
        {}
        return "";
    }



    public static class BranchConst{


        public static final String ID = "_id";
        public static final String NUMBER_PARKING = "numParking";
        public static final String CITY = "city";
        public static final String STREET = "street";
        public static final String NUM_APARTMENT = "numApart";
        public static final String IMAGE = "image";
    }
    public static class CarConst{
        public static final String ID_CAR = "_ID";
        public static final String ID_TYPE_MODEL = "modelID";
        public static final String MODEL_NAME = "modelName";
        public static final String KILLOMETER = "kilometer";
        public static final String ID_BRANCH = "branchID";
        public static final String availability = "availability";
    }
    public static class CarModelConst{
        public static final String ID = "_id";
        public static final String NAM_COMP ="company";
        public static final String NAME = "model";
        public static final String ENGINE_CAP = "engine";
        public static final String GEERBOX = "gear";
        public static final String NUMBER_OF_SEATS ="seats";
        public static final String IMAGE = "image";
    }
    public static class ClientConst{
        public static final String FIRST_NAME = "Fname";
        public static final String LAST_NAME = "Lname";
        public static final String ID = "_id";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String EMAIL = "email";
        public static final String NUM_CREDIT = "numCredit";
    }
    public static class OrderConst{
        public static final String customerID = "customerID";
        public static final String orderStatus = "orderStatus";
        public static final String startDate = "startDate";
        public static final String finishDate = "finishDate";
        public static final String startKilometer = "startKilometer";
        public static final String finishKilometer = "finishKilometer";
        public static final String filedGas = "filedGas";
        public static final String finallyCalculatedPrice = "finallyCalculatedPrice";
        public static final String orderID = "orderID";
        public static final String carID = "carID";
    }

    public static Cursor getBranches() throws Exception {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]
                {
                        BranchConst.ID,
                        BranchConst.NUMBER_PARKING,
                        BranchConst.CITY,
                        BranchConst.STREET,
                        BranchConst.NUM_APARTMENT,
                        BranchConst.IMAGE
                });
        String temp = httpGet("http://tades.vlab.jct.ac.il/getBranches.php?");
        JSONObject jsnobject = new JSONObject(temp);
        JSONArray array = jsnobject.getJSONArray("branches");
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            matrixCursor.addRow(new Object[]
                    {
                            obj.getInt(BranchConst.ID),
                            obj.getString(BranchConst.NUMBER_PARKING),
                            obj.getString(BranchConst.CITY),
                            obj.getString(BranchConst.STREET),
                            obj.getString(BranchConst.NUM_APARTMENT),
                            obj.getString(BranchConst.IMAGE)
                    });
        }
        return matrixCursor;
    }

    public static Cursor getCarModels() throws Exception {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]
                {
                        CarModelConst.ID,
                        CarModelConst.NAM_COMP,
                        CarModelConst.NAME,
                        CarModelConst.ENGINE_CAP,
                        CarModelConst.GEERBOX,
                        CarModelConst.NUMBER_OF_SEATS,
                        CarModelConst.IMAGE
                });
        String temp = httpGet("http://tades.vlab.jct.ac.il/getCarModels.php?");
        JSONObject jsnobject = new JSONObject(temp);
        JSONArray array = jsnobject.getJSONArray("carModels");
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            matrixCursor.addRow(new Object[]
                    {
                            obj.getInt(CarModelConst.ID),
                            obj.getString(CarModelConst.NAM_COMP),
                            obj.getString(CarModelConst.NAME),
                            obj.getInt(CarModelConst.ENGINE_CAP),
                            obj.getString(CarModelConst.GEERBOX),
                            obj.getInt(CarModelConst.NUMBER_OF_SEATS),
                            obj.getString(CarModelConst.IMAGE)
                    });
        }
        return matrixCursor;
    }
    public static Cursor getClients() throws Exception {

        MatrixCursor matrixCursor = new MatrixCursor(new String[]
                {
                        ClientConst.FIRST_NAME,
                        ClientConst.LAST_NAME,
                        ClientConst.ID,
                        ClientConst.PHONE_NUMBER,
                        ClientConst.EMAIL,
                        ClientConst.NUM_CREDIT
                });

        String temp = httpGet("http://tades.vlab.jct.ac.il/getClients.php?");
        JSONObject jsnobject = new JSONObject(temp);
        JSONArray array = jsnobject.getJSONArray("clients");
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            matrixCursor.addRow(new Object[]
                    {
                            obj.getString(ClientConst.FIRST_NAME),
                            obj.getString(ClientConst.LAST_NAME),
                            obj.getInt(ClientConst.ID),
                            obj.getString(ClientConst.PHONE_NUMBER),
                            obj.getString(ClientConst.EMAIL),
                            obj.getString(ClientConst.NUM_CREDIT)
                    });
        }
        return matrixCursor;
    }
    public static Cursor getCars() throws Exception {
        MatrixCursor carsCursor= new MatrixCursor(new String[]
                {
                        CarConst.ID_BRANCH,
                        CarConst.ID_TYPE_MODEL,
                        CarConst.KILLOMETER,
                        CarConst.ID_CAR,
                        CarConst.MODEL_NAME,
                        CarConst.availability,
                });
        JSONArray array = new JSONObject(httpGet("http://tades.vlab.jct.ac.il/getCars.php?")).getJSONArray("cars");
        for(int i=0;i<array.length();i++)
        {
            JSONObject obj = array.getJSONObject(i);
            carsCursor .addRow(new Object[]{
                    obj.getInt(Consts.CarConst.ID_BRANCH),
                    obj.getInt(CarConst.ID_TYPE_MODEL),
                    obj.getInt(CarConst.KILLOMETER),
                    obj.getInt(CarConst.ID_CAR),
                    obj.getString(CarConst.MODEL_NAME),
                    obj.getString(CarConst.availability)
            });
        }
        return carsCursor;
    }
    public static Cursor getOrders() throws Exception {
        MatrixCursor orderCursor= new MatrixCursor(new String[]
                {
                        OrderConst.customerID,
                        OrderConst.orderStatus,
                        OrderConst.startDate,
                        OrderConst.finishDate,
                        OrderConst.startKilometer,
                        OrderConst.finishKilometer,
                        OrderConst.filedGas,
                        OrderConst.finallyCalculatedPrice,
                        OrderConst.orderID,
                        OrderConst.carID
                });
        JSONArray array = new JSONObject(httpGet("http://tades.vlab.jct.ac.il/getOrders.php?")).getJSONArray("cars");
        for(int i=0;i<array.length();i++)
        {
            JSONObject obj = array.getJSONObject(i);
            orderCursor.addRow(new Object[]{
                    obj.getString(OrderConst.customerID),
                    obj.getString(OrderConst.orderStatus),
                    obj.getString(OrderConst.startDate),
                    obj.getString(OrderConst.finishDate),
                    obj.getString(OrderConst.startKilometer),
                    obj.getString(OrderConst.finishKilometer),
                    obj.getString(OrderConst.filedGas),
                    obj.getString(OrderConst.finallyCalculatedPrice),
                    obj.getString(OrderConst.orderID),
                    obj.getString(OrderConst.carID)
            });
        }
        return orderCursor;
    }

    public static Cursor getAvailableCars() throws Exception {
        MatrixCursor carsCursor= new MatrixCursor(new String[]
                {
                        CarConst.ID_BRANCH,
                        CarConst.ID_TYPE_MODEL,
                        CarConst.KILLOMETER,
                        CarConst.ID_CAR,
                        CarConst.MODEL_NAME,
                        CarConst.availability,
                });
        JSONArray array = new JSONObject(httpGet("http://tades.vlab.jct.ac.il/getAvailableCars.php?")).getJSONArray("availableCars");
        for(int i=0;i<array.length();i++)
        {
            JSONObject obj = array.getJSONObject(i);
            carsCursor .addRow(new Object[]{
                    obj.getInt(Consts.CarConst.ID_BRANCH),
                    obj.getInt(CarConst.ID_TYPE_MODEL),
                    obj.getInt(CarConst.KILLOMETER),
                    obj.getInt(CarConst.ID_CAR),
                    obj.getString(CarConst.MODEL_NAME),
                    obj.getString(CarConst.availability)
            });
        }
        return carsCursor;
    }
    public static Cursor getAvailableCarsByBranch(long id) throws Exception {
        MatrixCursor carsCursor= new MatrixCursor(new String[]
                {
                        CarConst.ID_BRANCH,
                        CarConst.ID_TYPE_MODEL,
                        CarConst.KILLOMETER,
                        CarConst.ID_CAR,
                        CarConst.MODEL_NAME,
                        CarConst.availability,
                });
        JSONArray array = new JSONObject(httpGet("http://tades.vlab.jct.ac.il/getAvailableCarsByBranch.php?"+"branchID=\""+id+"\"")).getJSONArray("availableCars");
        for(int i=0;i<array.length();i++)
        {
            JSONObject obj = array.getJSONObject(i);
            carsCursor .addRow(new Object[]{
                    obj.getInt(Consts.CarConst.ID_BRANCH),
                    obj.getInt(CarConst.ID_TYPE_MODEL),
                    obj.getInt(CarConst.KILLOMETER),
                    obj.getInt(CarConst.ID_CAR),
                    obj.getString(CarConst.MODEL_NAME),
                    obj.getString(CarConst.availability)
            });
        }
        return carsCursor;
    }

    public static Cursor getBranchesWhereCarModelAvailable(int id) throws Exception {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]
                {
                        BranchConst.ID,
                        BranchConst.NUMBER_PARKING,
                        BranchConst.CITY,
                        BranchConst.STREET,
                        BranchConst.NUM_APARTMENT,
                        BranchConst.IMAGE
                });
        JSONArray array = new JSONObject(httpGet("http://tades.vlab.jct.ac.il/getBranchesWhereCarModelAvailable.php?"+"modelID=\""+id+"\"")).getJSONArray("Branches");
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            matrixCursor.addRow(new Object[]
                    {
                            obj.getInt(BranchConst.ID),
                            obj.getString(BranchConst.NUMBER_PARKING),
                            obj.getString(BranchConst.CITY),
                            obj.getString(BranchConst.STREET),
                            obj.getString(BranchConst.NUM_APARTMENT),
                            obj.getString(BranchConst.IMAGE)
                    });
        }
        return matrixCursor;
    }

    public static Cursor getOpenOrders() throws Exception {
        MatrixCursor orderCursor= new MatrixCursor(new String[]
                {
                        OrderConst.customerID,
                        OrderConst.orderStatus,
                        OrderConst.startDate,
                        OrderConst.finishDate,
                        OrderConst.startKilometer,
                        OrderConst.finishKilometer,
                        OrderConst.filedGas,
                        OrderConst.finallyCalculatedPrice,
                        OrderConst.orderID,
                        OrderConst.carID
                });
        JSONArray array = new JSONObject(httpGet("http://tades.vlab.jct.ac.il/getOpenOrders.php?")).getJSONArray("openOrders");
        for(int i=0;i<array.length();i++)
        {
            JSONObject obj = array.getJSONObject(i);
            orderCursor.addRow(new Object[]{
                    obj.getString(OrderConst.customerID),
                    obj.getString(OrderConst.orderStatus),
                    obj.getString(OrderConst.startDate),
                    obj.getString(OrderConst.finishDate),
                    obj.getString(OrderConst.startKilometer),
                    obj.getString(OrderConst.finishKilometer),
                    obj.getString(OrderConst.filedGas),
                    obj.getString(OrderConst.finallyCalculatedPrice),
                    obj.getString(OrderConst.orderID),
                    obj.getString(OrderConst.carID)
            });
        }
        return orderCursor;
    }

    public static List<Car> CursorToListCar(Cursor cursor)
    {
        List<Car> cars = new ArrayList<Car>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            Car car = new Car();
            car.setIdBranch(cursor.getLong(cursor.getColumnIndexOrThrow(CarConst.ID_BRANCH)));
            car.setIdCar(cursor.getLong(cursor.getColumnIndexOrThrow(CarConst.ID_CAR)));
            car.setIdTypeModel(cursor.getLong(cursor.getColumnIndexOrThrow(CarConst.ID_TYPE_MODEL)));
            car.setKilometer(cursor.getInt(cursor.getColumnIndexOrThrow(CarConst.KILLOMETER)));
            car.setModel(cursor.getString(cursor.getColumnIndexOrThrow(CarConst.MODEL_NAME)));
            cars.add(car);

            cursor.moveToNext();
        }
        return cars;

    }

    public static ArrayList<Branch> CursorToListBranch(Cursor cursor){
        ArrayList<Branch> branches = new ArrayList<Branch>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Branch branch = new Branch();
            branch.setIdBranch(cursor.getLong(cursor.getColumnIndexOrThrow(BranchConst.ID)));
            branch.setCity(cursor.getString(cursor.getColumnIndexOrThrow(BranchConst.CITY)));
            branch.setStreet(cursor.getString(cursor.getColumnIndexOrThrow(BranchConst.STREET)));
            branch.setNumApart(cursor.getInt(cursor.getColumnIndexOrThrow(BranchConst.NUM_APARTMENT)));
            branch.setNumParking(cursor.getInt(cursor.getColumnIndexOrThrow(BranchConst.NUMBER_PARKING)));
            branch.setUrlImage("http://tades.vlab.jct.ac.il/" + cursor.getString(cursor.getColumnIndexOrThrow(BranchConst.IMAGE)));
            branches.add(branch);
            cursor.moveToNext();
        }
        return branches;
    }

    public static int dateSubtract(String a ,String b) {
        int yearA = Integer.parseInt(a.substring(0,4));
        int monA = Integer.parseInt(a.substring(5,7));
        int dayA = Integer.parseInt(a.substring(8,10));
        int hourA = Integer.parseInt(a.substring(11,13));
        int minA = Integer.parseInt(a.substring(14,16));

        int yearB = Integer.parseInt(b.substring(0,4));
        int monB = Integer.parseInt(b.substring(5,7));
        int dayB = Integer.parseInt(b.substring(8,10));
        int hourB = Integer.parseInt(b.substring(11,13));
        int minB = Integer.parseInt(b.substring(14,16));

        int year=yearB-yearA;
        int mon=monB-monA;
        int day =dayB-dayA;
        int hour = hourB-hourA;

        if(minB - minA > 0 )
            minA = 1;
        else minA = 0;
        return year*8760 + mon*744 + day*24 + hour + minA;
    }
}
