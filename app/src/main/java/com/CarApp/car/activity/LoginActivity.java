package com.CarApp.car.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.CarApp.car.R;
import com.CarApp.car.app.AppConfig;
import com.CarApp.car.app.AppController;
import com.CarApp.car.app.SessionManager;
import com.CarApp.car.modelclass.ResponseModel;
import com.CarApp.car.retrofit.ApiClient;
import com.CarApp.car.retrofit.ApiInterface;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button button_login;
    TextView signup;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.text_email);
        password = findViewById(R.id.text_pass);
        button_login = findViewById(R.id.btn_login);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        signup = findViewById(R.id.signup);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = true;
                if (email.getText().toString().isEmpty())
                    flag = false;
                if (password.getText().toString().isEmpty())
                    flag = false;
                if (flag){
                    progressBar.setVisibility(View.VISIBLE);
                    loginResponse(email.getText().toString().trim().toLowerCase(),password.getText().toString());
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                finish();
            }
        });
        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }
    private void login(final String email, final String password) {
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Tag", "Login Response: " + response.toString());
                /*try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        startActivity(new Intent(LoginActivity.this,BasicActivity.class));
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Tag", "Login Error: " + error.getMessage());
                Toast.makeText(getApplication(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("action", "login");
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    public void loginResponse(final String email, final String password){
        ApiInterface apiservice= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel> call=apiservice.login(email,password);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
                boolean error = response.body().isError();
                if (!error){
                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                    sessionManager.setName(email);
                    sessionManager.setId(String.valueOf(response.body().getUser_id()));
                    sessionManager.setLogin(true);
                    startActivity(new Intent(LoginActivity.this,BasicActivity.class));
                    finish();
                 }else
                    Toast.makeText(LoginActivity.this, ""+response.body().getError_msg(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loginUser(final String email, String password) {
        ParseUser.logInInBackground(email, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                    sessionManager.setEmail(email);
                    startActivity(new Intent(LoginActivity.this,BasicActivity.class));
                    finish();
                    // Hooray! The user is logged in.
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    button_login.setClickable(false);
                    Toast.makeText(LoginActivity.this, "Permission denied can't move forward", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(LoginActivity.this)
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
