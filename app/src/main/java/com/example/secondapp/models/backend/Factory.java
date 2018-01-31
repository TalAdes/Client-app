package com.example.secondapp.models.backend;

import com.example.secondapp.models.datasources.DB_IMP;

/**
 * Created by טל on 17-Jan-18.
 */

public class Factory {
    static IDB manager = null;

    public static IDB getMnager(){
        if(manager == null)
            manager = new DB_IMP();
        return manager;
    }
}
