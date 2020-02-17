package com.CarApp.car.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.CarApp.car.R;
import com.CarApp.car.app.SessionManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                boolean flag = sessionManager.isLoggedIn();
                if (flag)
                    startActivity(new Intent(SplashActivity.this,BasicActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }
        },2000);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(SplashActivity.this)
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
