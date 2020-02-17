package com.CarApp.car.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.CarApp.car.R;
import com.CarApp.car.adapter.BasicActivityAdater;
import com.CarApp.car.app.SessionManager;
import com.CarApp.car.modelclass.ResponseModel;
import com.CarApp.car.modelclass.carModel;
import com.CarApp.car.modelclass.itemModel;
import com.CarApp.car.retrofit.ApiClient;
import com.CarApp.car.retrofit.ApiInterface;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class BasicActivity extends AppCompatActivity {
    RelativeLayout btn_chkcar;
    ListView listView;
    ArrayList<itemModel> modelArrayList;
    BasicActivityAdater adater;
    ProgressBar progressBar;
    Toolbar toolbar;
    TextView toolbar_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        btn_chkcar = findViewById(R.id.btn_checkcar);
        toolbar = findViewById(R.id.toolbar);
        toolbar_name = findViewById(R.id.toolbar_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar_name.setText(new SessionManager(getApplicationContext()).getKeyUsername());
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        toolbar.setNavigationIcon(R.drawable.ic_dehaze_black_24dp);
        listView = findViewById(R.id.listview);
        modelArrayList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
//        fetchAllItem();
        fetcUserData();
        btn_chkcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BasicActivity.this,TermsAndConditionsActivity.class));
                finish();

            }
        });
    }
    public void fetchAllItem() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("carnumber");
        query.orderByDescending("milies");
        query.setLimit(5);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> userObjects, ParseException error) {
                if (userObjects != null) {
                    modelArrayList=new ArrayList<>();
                    for (int i =0;i<userObjects.size();i++){
                        ParseObject object = userObjects.get(i);
                        itemModel model = new itemModel();
                        model.setNumber(object.get("carnum").toString());
                        model.setDate(object.get("username").toString());
                        model.setTime(object.get("milies").toString());
                        modelArrayList.add(i,model);

                    }

//                    listView.setSelection(0);
//                    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    //getViewByPosition(0,  listView);

                }else
                    Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }
/*    public void fetchImage(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ImageUpload");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    for(ParseObject object : objects){
                        final ParseFile file = (ParseFile) object.get("ImageFile");
                        hashMap.put(String.valueOf(object.get("ImageName")),file.getUrl());
                        Log.d("URLOFFILE",file.getUrl());

                    }
                    IdeaAdapter adapter = new IdeaAdapter(getActivity(),allObjects,hashMap);
                    listView.setAdapter(adapter);
                }
                else{
                    Log.i("info", e.getMessage());
                }
            }
        });
    }*/
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            View view =  listView.getChildAt(childIndex);
            view.setBackgroundColor(Color.GRAY);
            return view;
        }
    }
    public void fetcUserData(){
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        ApiInterface apiservice= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel> call=apiservice.fetchUserData(sessionManager.getId());
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
                boolean error = response.body().isError();
                if (!error){
                    progressBar.setVisibility(View.GONE);
                    ArrayList<carModel> carModels = response.body().getCarModels();
                    //Toast.makeText(BasicActivity.this, ""+response.body().getCarModels().get(0).getCar().getCarnumber(), Toast.LENGTH_SHORT).show();
                    adater = new BasicActivityAdater(BasicActivity.this,carModels);
                    listView.setAdapter(adater);
                }else
                    Toast.makeText(BasicActivity.this, ""+response.body().getError_msg(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(BasicActivity.this)
                .setTitle("Exit ?")
                .setMessage("Do you want to exit ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       // super.onBackPressed();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.lock){
            new AlertDialog.Builder(BasicActivity.this)
                    .setTitle("Log Out ?")
                    .setMessage("Do you want to Log Out ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // super.onBackPressed();
                            SessionManager sessionManager = new SessionManager(getApplicationContext());
                            sessionManager.setLogin(false);
                            startActivity(new Intent(BasicActivity.this,LoginActivity.class));
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
        return super.onOptionsItemSelected(item);
    }
}
