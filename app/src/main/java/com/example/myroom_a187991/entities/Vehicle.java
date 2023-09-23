package com.example.myroom_a187991.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vehicles")

public class Vehicle {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "vehicle_id")
    int vehicleID;

    @NonNull
    @ColumnInfo(name = "vehicle_name")
    String vehicleName;

    @NonNull
    @ColumnInfo(name = "vehicle_price")
    float vehiclePrice;

    public Vehicle(@NonNull int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Vehicle(@NonNull String vehicleName, @NonNull float vehiclePrice) {
        this.vehicleName = vehicleName;
        this.vehiclePrice = vehiclePrice;
    }

    public Vehicle(@NonNull int vehicleID, @NonNull String vehicleName, @NonNull float vehiclePrice) {
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.vehiclePrice = vehiclePrice;
    }

    public Vehicle() {
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    @NonNull
    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(@NonNull String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public float getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(float vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }
}
