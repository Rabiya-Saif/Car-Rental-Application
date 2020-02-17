package com.CarApp.car.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.CarApp.car.R;
import com.CarApp.car.app.SessionManager;
import com.CarApp.car.modelclass.ResponseModel;
import com.CarApp.car.retrofit.ApiClient;
import com.CarApp.car.retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class TermsAndConditionsActivity extends AppCompatActivity {
    EditText edit_one ;
    Switch switch_btn;
    Toolbar toolbar;
    ArrayList<String> dropdownlist;
    Spinner spinner;
    RelativeLayout btn_checkcar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_dehaze_black_24dp);
        spinner = findViewById(R.id.spinner);
        btn_checkcar = findViewById(R.id.btn_checkcar);
        switch_btn = findViewById(R.id.switch_btn);
        edit_one = findViewById(R.id.edit_one);
        edit_one.setClickable(false);
        edit_one.setEnabled(false);
        fetcars();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String number =  dropdownlist.get(i);
                edit_one.setText(number);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switch_btn.isChecked()){
                    btn_checkcar.setEnabled(true);
                }else {
                    btn_checkcar.setEnabled(false);
                }
            }
        });

        btn_checkcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = false;
                if (edit_one.getText().toString().isEmpty())
                    flag=true;
                if (!switch_btn.isChecked())
                    flag=true;
                if (flag)
                    Toast.makeText(TermsAndConditionsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else
                    saveRecord();
            }
        });
    }
    public void createRecord() {
        String number = edit_one.getText().toString().trim() ;
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.setName(number);
        startActivity(new Intent(TermsAndConditionsActivity.this,CameraActivity.class));
       /* if (Build.VERSION.SDK_INT > 24)
        else
            startActivity(new Intent(TermsAndConditionsActivity.this,CameraActivity.class));*/
        finish();
       /* String name = sessionManager.getEmail();
        ParseObject myNewObject = new ParseObject("carnumber");
        myNewObject.put("username", name);
        myNewObject.put("carnum", number);

        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        myNewObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null){
                    startActivity(new Intent(TermsAndConditionsActivity.this,CameraActivity.class));
                }else {
                    Toast.makeText(TermsAndConditionsActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                }
                // Here you can handle errors, if thrown. Otherwise, "e" should be null
            }
        });*/
    }
    public void saveRecord(){
        ApiInterface apiservice= ApiClient.getClient().create(ApiInterface.class);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String number = edit_one.getText().toString().trim();
        String id = sessionManager.getId();
        Call<ResponseModel> call=apiservice.saveData(id,number);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
                boolean error = response.body().isError();
                if (!error){
                    new SessionManager(getApplicationContext()).setKeyOrigin(response.body().getBookingid());
                    startActivity(new Intent(TermsAndConditionsActivity.this,CameraActivity.class));
                    finish();
                }else
                    Toast.makeText(TermsAndConditionsActivity.this, ""+response.body().getError_msg(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(TermsAndConditionsActivity.this)
                .setTitle("Exit ?")
                .setMessage("Do you want to exit ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();

    }
    public void fetcars(){
        ApiInterface apiservice= ApiClient.getClient().create(ApiInterface.class);
         Call<ResponseModel> call=apiservice.fetccars();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
                boolean error = response.body().isError();
                if (!error){
                    dropdownlist = new ArrayList<>();
                    String[] arr={""};
                    for (int i = 0;i<response.body().getCarsstring().size();i++){
                        dropdownlist.add(response.body().getCarsstring().get(i));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(TermsAndConditionsActivity.this,   R.layout.spinnerlist , dropdownlist);
//set the spinners adapter to the previously created one.
                    spinner.setAdapter(adapter);
                }else
                    Toast.makeText(TermsAndConditionsActivity.this, ""+response.body().getError_msg(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
