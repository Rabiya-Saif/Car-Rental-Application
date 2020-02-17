package com.CarApp.car.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.CarApp.car.R;
import com.CarApp.car.modelclass.carModel;

import java.util.ArrayList;

public class BasicActivityAdater extends BaseAdapter {
    Activity activity;
    ArrayList<carModel> arrayList;
    LayoutInflater inflater;

    public BasicActivityAdater(Activity activity, ArrayList<carModel> arrayList) {
        this.activity=activity;
        inflater=activity.getLayoutInflater();
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=view;
        if (v==null){
            v=inflater.inflate(R.layout.basicitemdesign,null);
        }
        LinearLayout linear_layout_basic = v.findViewById(R.id.linear_layout_basic);
        if (i==0) {
            linear_layout_basic.setBackgroundColor(Color.parseColor("#ECF0F1"));
        }
        /*else
            linear_layout_basic.setBackgroundColor(Color.parseColor("#ffff"));*/
        TextView txt_numbber = v.findViewById(R.id.txt_number);
        TextView txt_date = v.findViewById(R.id.txt_date);
        TextView txt_gas = v.findViewById(R.id.text_gas_police);
        TextView txt_tyrececk = v.findViewById(R.id.tyrecheck);
        carModel model = arrayList.get(i);
        boolean one=inttoboolean(model.getbLWheel());
        boolean two=inttoboolean(model.getbRWheel());
        boolean three= inttoboolean(model.getfLWheel());
        boolean four=inttoboolean(model.getfRWheel());
        if (one&&two&&three&&four) {
            txt_tyrececk.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_fiber_manual_record_black_24dp,0,0,0);
        }else {
            txt_tyrececk.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red_50dp,0,0,0);
        }
        if (inttoboolean(model.getGasInspection())&&inttoboolean(model.getPoliceInspection()))
            txt_gas.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red_24dp,0,R.drawable.ic_check_red_24dp,0);
        else if (inttoboolean(model.getGasInspection())){
            txt_gas.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red_24dp,0,R.drawable.ic_check_black_24dp,0);
        }else if (inttoboolean(model.getPoliceInspection())){
            txt_gas.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_black_24dp,0,R.drawable.ic_check_red_24dp,0);
        }
        txt_numbber.setText(model.getCar().getCarnumber());
        txt_date.setText(model.getDate());
        return v;
    }
    boolean inttoboolean(int i){
        if (i==0)
            return false;
        else
            return true;
    }

}
