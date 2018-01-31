package com.example.secondapp.models.entities;

import java.util.Date;

/**
 * Created by liran on 08/11/2017.
 */

public class Order {
    private long    customerID ;
    private String  orderStatus ;
    private Date    startDate ;
    private Date    finishDate ;
    private int     startKilometer ;
    private int     finishKilometer ;
    private int     filedGas ;
    private int     finallyCalculatedPrice ;
    private long    orderID ;
    private long    carID ;
}
