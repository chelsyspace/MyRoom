package com.example.myroom_a187991;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myroom_a187991.dao.VehicleDao;
import com.example.myroom_a187991.entities.Vehicle;

@Database(entities = {Vehicle.class}, version = 1)

public abstract class MyVehicleDB extends RoomDatabase {

    public abstract VehicleDao vehicleDao();

}
