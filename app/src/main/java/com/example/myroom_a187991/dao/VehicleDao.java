package com.example.myroom_a187991.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myroom_a187991.entities.Vehicle;

import java.util.List;

@Dao
public interface VehicleDao {

    @Insert
    void insertVehicle(Vehicle vehicle);

    @Update
    void updateVehicle(Vehicle vehicle);

    @Delete
    void deleteVehicle(Vehicle vehicle);

    @Query("Select * from vehicles")
    List<Vehicle> getAllVehicles();
}
