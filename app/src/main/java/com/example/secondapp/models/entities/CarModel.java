package com.example.secondapp.models.entities;

/**
 * Created by liran on 08/11/2017.
 */

public class CarModel {
    private long idModel;
    private String nameComp;
    private String nameModel;
    private int engineCap;
    private EnumList.GearBox gearbox;
    private int numberOfSeats;

    public CarModel(){}
    public CarModel(long IdModel, String NameComp, String NameModel, int EngineCap, EnumList.GearBox GearBox, int NumberOfSeats) {
        this.idModel = IdModel;
        this.nameComp = NameComp;
        this.nameModel = NameModel;
        this.engineCap = EngineCap;
        this.gearbox = GearBox;
        this.numberOfSeats = NumberOfSeats;}

    public CarModel(CarModel c) {
        this.idModel = c.idModel;
        this.nameComp = c.nameComp;
        this.nameModel = c.nameModel;
        this.engineCap = c.engineCap;
        this.gearbox = c.gearbox;
        this.numberOfSeats = c.numberOfSeats;}

    public long getIdModel() {return idModel;}

    public void setIdModel(long idModel) {this.idModel = idModel;}

    public String getNameComp() {return nameComp;}

    public void setNameComp(String nameComp) {this.nameComp = nameComp;}

    public String getNameModel() {return nameModel;}

    public void setNameModel(String nameModel) {this.nameModel = nameModel;}

    public int getEngineCap() {return engineCap;}

    public void setEngineCap(int engineCap) {this.engineCap = engineCap;}

    public EnumList.GearBox getGeerbox() {return gearbox;}

    public void setGeerbox(EnumList.GearBox gearbox) {this.gearbox = gearbox;}

    public int getNumberOfSeats() {return numberOfSeats;}

    public void setNumberOfSeats(int numberOfSeats) {this.numberOfSeats = numberOfSeats;}
}
