package com.CarApp.car.adapter;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.CarApp.car.R;
import com.jpegkit.JpegImageView;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
   public static ArrayList<Bitmap> jpegArrayList = new ArrayList<>();
   Context context=null;
    public MyListAdapter(ArrayList<Bitmap> jpegArrayList, Context activity) {
        this.jpegArrayList = jpegArrayList;
        this.context=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //final Bitmap bitmap1 = BitmapFactory.decodeByteArray(bitmapArrayList.get(position), 0, bitmapArrayList.get(position).length);
        final Bitmap myListData = jpegArrayList.get(position);
//        Bitmap myBitmap = BitmapFactory.decodeFile(jpegArrayList.get(position));
        holder.imageView.setImageBitmap(myListData);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom);
                //dialog.setTitle("Title...");
                JpegImageView image = (JpegImageView) dialog.findViewById(R.id.image_view);
                image.setImageBitmap(((BitmapDrawable) holder.imageView.getDrawable()).getBitmap());
                Button dialogButton = (Button) dialog.findViewById(R.id.tv_view);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return jpegArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView =  itemView.findViewById(R.id.images);
            //this.imageView.setRotation(270);
        }
    }
    public void listdata(ArrayList<Bitmap> list){
        this.jpegArrayList = list;
        this.notifyDataSetChanged();
    }
}