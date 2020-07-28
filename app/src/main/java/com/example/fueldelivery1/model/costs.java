package com.example.fueldelivery1.model;

public class costs {

    public Integer
            costBasefare,
            costCNG,
            costDeisel,
            costPerKM,
            costPetrol;


    public costs(){

    }
    public costs(Integer costBasefare,Integer costCNG,Integer costDeisel,Integer costPerKM,Integer costPetrol){
        this.costBasefare = costBasefare;
        this.costCNG = costCNG;
        this.costDeisel = costDeisel;
        this.costPerKM = costPerKM;
        this.costPetrol = costPetrol;
    }

    public Integer getCostBasefare() {
        return costBasefare;
    }

    public void setCostBasefare(Integer costBasefare) {
        this.costBasefare = costBasefare;
    }

    public Integer getCostCNG() {
        return costCNG;
    }

    public void setCostCNG(Integer costCNG) {
        this.costCNG = costCNG;
    }

    public Integer getCostDeisel() {
        return costDeisel;
    }

    public void setCostDeisel(Integer costDeisel) {
        this.costDeisel = costDeisel;
    }

    public Integer getCostPerKM() {
        return costPerKM;
    }

    public void setCostPerKM(Integer costPerKM) {
        this.costPerKM = costPerKM;
    }

    public Integer getCostPetrol() {
        return costPetrol;
    }

    public void setCostPetrol(Integer costPetrol) {
        this.costPetrol = costPetrol;
    }
}
