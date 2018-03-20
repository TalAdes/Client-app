package com.example.secondapp.models.entities;

/**
 * Created by liran on 08/11/2017.
 */

public class Car
{
    private long idBranch;
    private long idTypeModel;
    private int kilometer;
    private long idCar;
    private String model;
    private String urlImage;

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getIdBranch() {return idBranch;}

    public void setIdBranch(long idBranch) {this.idBranch = idBranch;}

    public long getIdTypeModel() {return idTypeModel;}

    public void setIdTypeModel(long IdtypeModel) {this.idTypeModel = IdtypeModel;}

    public int getKilometer() {return kilometer;}

    public void setKilometer(int kilometer) {this.kilometer = kilometer;}

    public long getIdCar() {return idCar;}

    public void setIdCar(long idCar) {this.idCar = idCar;}
}





