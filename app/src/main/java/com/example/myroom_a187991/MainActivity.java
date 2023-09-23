package com.example.myroom_a187991;

//import static com.google.android.material.button.MaterialButtonToggleGroup.CornerData.start;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myroom_a187991.entities.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnSave, btnUpdate;
    EditText etName, etPrice;
    TextView tvVehicleId;
    ListView lvVehicle;

    ArrayAdapter<String> vehicleListAdapter;
    ArrayList<String> vehiclesArray;
    ArrayList<Integer> vehiclesID;

    public static MyVehicleDB myVehicleDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.btn_save);
        btnUpdate = findViewById(R.id.btn_update);
        etName = findViewById(R.id.et_name);
        etPrice = findViewById(R.id.et_price);
        tvVehicleId = findViewById(R.id.tv_vehicle_id);
        lvVehicle = findViewById(R.id.lv_main);

        vehicleListAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
        vehiclesArray = new ArrayList<String>();
        vehiclesID = new ArrayList<Integer>();

        myVehicleDB = Room.databaseBuilder(MainActivity.this, MyVehicleDB.class, "vehicleDB").build();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextIsEmpty())
                    return;
                saveVehicle();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextIsEmpty())
                    return;
                updateVehicle();
            }
        });

        lvVehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvVehicleId.setText(vehiclesID.get(i).toString());
            }
        });

        lvVehicle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Remove Vehicle");
                alertDialogBuilder.setMessage("Are you sure to remove Vehicle " + vehiclesID.get(position));
                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        toast(getApplicationContext(),"Action Cancelled");
                    }
                });

                alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Vehicle vehicle = new Vehicle(Integer.parseInt(vehiclesID.get(position).toString()));
                        deleteVehicle(vehicle);
                    }
                });

                alertDialogBuilder.show();


                return false;
            }
        });
    }

    public void saveVehicle(){
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                Vehicle vehicle = new Vehicle(etName.getText().toString(),Float.parseFloat(etPrice.getText().toString()));
                myVehicleDB.vehicleDao().insertVehicle(vehicle);
                toast(getApplicationContext(), "Added");
                getAllVehicles();
            }
        }).start();
        
    }

    public void updateVehicle(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Vehicle vehicle = new Vehicle(Integer.parseInt(tvVehicleId.getText().toString()),etName.getText().toString(),Float.parseFloat(etPrice.getText().toString()));
                myVehicleDB.vehicleDao().updateVehicle(vehicle);
                toast(getApplicationContext(),"Updated");
                getAllVehicles();
            }
        }).start();

    }

    public void deleteVehicle(Vehicle veh){

        new Thread(new Runnable() {
            @Override
            public void run() {
                myVehicleDB.vehicleDao().deleteVehicle(veh);
                toast(getApplicationContext(),"Removed");
                getAllVehicles();
            }
        }).start();

    }

    private void getAllVehicles() {

        vehiclesID.clear();
        vehiclesArray.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Vehicle> vehicles = myVehicleDB.vehicleDao().getAllVehicles();
                String vehicleInfo;
                for (Vehicle vehicle : vehicles) {
                    vehicleInfo = "ID:" + vehicle.getVehicleID() +
                            "\nName: " + vehicle.getVehicleName() +
                            "\nPrice (RM): " + vehicle.getVehiclePrice();

                    vehiclesArray.add(vehicleInfo);
                    vehiclesID.add(vehicle.getVehicleID());
                }
                showDataInListView();
            }
        }).start();

    }

    public void showDataInListView(){

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                vehicleListAdapter.clear();
                vehicleListAdapter.addAll(vehiclesArray);
                lvVehicle.setAdapter(vehicleListAdapter);
            }
        });

    }

    private boolean editTextIsEmpty(){
        if(TextUtils.isEmpty(etName.getText().toString())){
            etName.setError("Cannot be Empty");
        }

        if(TextUtils.isEmpty(etPrice.getText().toString())){
            etPrice.setError("Cannot be Empty");
        }

        if(TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etPrice.getText().toString()) ){
           return true;
        }
        else
            return false;
    }

    public void toast(final Context context,final String text){

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllVehicles();
    }
}
