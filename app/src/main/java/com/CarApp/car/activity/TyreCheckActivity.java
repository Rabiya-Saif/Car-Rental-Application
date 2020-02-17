package com.CarApp.car.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.CarApp.car.R;
import com.CarApp.car.app.SessionManager;
import com.CarApp.car.modelclass.ResponseModel;
import com.CarApp.car.retrofit.ApiClient;
import com.CarApp.car.retrofit.ApiInterface;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import retrofit2.Call;
import retrofit2.Callback;

public class TyreCheckActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    EditText edit_kilometer,edit_comment;
    Boolean falg_left_up=false;
    Boolean falg_left_down=false;
    Boolean falg_right_up=false;
    Boolean falg_right_down=false;
    ProgressBar progressBar;
    ImageView image_onevlinks_cross
    , image_onevlinks_tick
    , image_twohlinks_cross
    , image_twohlinks_tick
    , image_onevrect_cross
    , image_onevrect_tick
    , image_twohrect_cross
    , image_twohrect_tick;
    RelativeLayout btn_checkcar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tyre_check);
        toolbar = findViewById(R.id.toolbar);
        edit_kilometer = findViewById(R.id.edit_kilometer);
        btn_checkcar = findViewById(R.id.btn_checkcar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        image_onevlinks_cross = findViewById(R.id.image_onevlinks_cross);
        image_onevlinks_tick = findViewById(R.id.image_onevlinks_tick);
        image_twohlinks_cross = findViewById(R.id.image_twohlink_cross);
        image_twohlinks_tick = findViewById(R.id.image_twohlink_tick);
        image_onevrect_cross = findViewById(R.id.image_onevrecht_cross);
        image_onevrect_tick = findViewById(R.id.image_onevrect_tick);
        image_twohrect_cross = findViewById(R.id.image_twohrect_cross);
        image_twohrect_tick = findViewById(R.id.image_twohrect_tick);
        edit_comment = findViewById(R.id.edit_comment);

        image_onevlinks_cross.setOnClickListener(this);
        image_onevlinks_tick.setOnClickListener(this);
        image_twohlinks_cross.setOnClickListener(this);
        image_twohlinks_tick.setOnClickListener(this);
        image_onevrect_cross.setOnClickListener(this);
        image_onevrect_tick.setOnClickListener(this);
        image_twohrect_cross.setOnClickListener(this);
        image_twohrect_tick.setOnClickListener(this);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_dehaze_black_24dp);
        btn_checkcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_kilometer.getText().toString().isEmpty()){
                    Toast.makeText(TyreCheckActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
             else {
                    progressBar.setVisibility(View.VISIBLE);
                    saveTyreRecord();

                }
            }
        });
  }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.image_onevlinks_cross:
                falg_left_up = false;
                image_onevlinks_cross.setBackgroundColor(Color.parseColor("#DAD8D8"));
                image_onevlinks_tick.setBackgroundColor(Color.WHITE);
                break;
            case R.id.image_onevlinks_tick:
                falg_left_up = true;
                image_onevlinks_cross.setBackgroundColor(Color.WHITE);
                image_onevlinks_tick.setBackgroundColor(Color.parseColor("#DAD8D8"));
                break;
            case R.id.image_twohlink_cross:
                falg_left_down = false;
                image_twohlinks_cross.setBackgroundColor(Color.parseColor("#DAD8D8"));
                image_twohlinks_tick.setBackgroundColor(Color.WHITE);
                break;
            case R.id.image_twohlink_tick:
                falg_left_down = true;
                image_twohlinks_cross.setBackgroundColor(Color.WHITE);
                image_twohlinks_tick.setBackgroundColor(Color.parseColor("#DAD8D8"));
                break;
            case R.id.image_onevrecht_cross:
                falg_right_up = false;
                image_onevrect_cross.setBackgroundColor(Color.parseColor("#DAD8D8"));
                image_onevrect_tick.setBackgroundColor(Color.WHITE);
                break;
            case R.id.image_onevrect_tick:
                falg_right_up = true;
                image_onevrect_cross.setBackgroundColor(Color.WHITE);
                image_onevrect_tick.setBackgroundColor(Color.parseColor("#DAD8D8"));
                break;
            case R.id.image_twohrect_cross:
                falg_right_down = false;
                image_twohrect_cross.setBackgroundColor(Color.parseColor("#DAD8D8"));
                image_twohrect_tick.setBackgroundColor(Color.WHITE);
                break;
            case R.id.image_twohrect_tick:
                falg_right_down = true;
                image_twohrect_cross.setBackgroundColor(Color.WHITE);
                image_twohrect_tick.setBackgroundColor(Color.parseColor("#DAD8D8"));
                break;
        }

    }
    public void saveTyreRecord(){
        ApiInterface apiservice= ApiClient.getClient().create(ApiInterface.class);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String id = sessionManager.getKeyOrigin();
        String comment = "";
        //Toast.makeText(this, ""+falg_right_up, Toast.LENGTH_SHORT).show();
        if (!edit_comment.getText().toString().isEmpty())
            comment = edit_comment.getText().toString();
        Call<ResponseModel> call=apiservice.saveTyreComplain(id,edit_kilometer.getText().toString(),comment,booleantoint(falg_right_up),booleantoint(falg_left_up)
                ,booleantoint(falg_right_down),booleantoint(falg_left_down));
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
                boolean error = response.body().isError();
                if (!error){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(TyreCheckActivity.this,BasicActivity.class));
                    finish();
                }else{
                    Toast.makeText(TyreCheckActivity.this, ""+response.body().getError_msg(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public int booleantoint(boolean b){
        if (b)
            return 1;
        else
            return 0;
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(TyreCheckActivity.this)
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
}
