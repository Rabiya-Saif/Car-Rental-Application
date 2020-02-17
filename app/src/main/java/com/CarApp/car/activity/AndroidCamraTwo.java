package com.CarApp.car.activity;

import android.app.Activity;
import android.media.ImageReader;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;

import com.CarApp.car.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

import me.aflak.ezcam.EZCam;
import me.aflak.ezcam.EZCamCallback;

public class AndroidCamraTwo extends Activity implements EZCamCallback, View.OnLongClickListener{
    private TextureView textureView;

    private EZCam cam;
    private SimpleDateFormat dateFormat;

    private final String TAG = "CAM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_camra_two);

        textureView = (TextureView) findViewById(R.id.textureView);
        dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());

        cam = new EZCam(this);
        cam.setCameraCallback(this);

        cam.startPreview();
    }

    @Override
    public boolean onLongClick(View v) {
        cam.takePicture();
        return false;
    }

    @Override
    public void onCameraDisconnected() {
        Log.e(TAG, "Camera disconnected");
    }

    @Override
    public void onPreviewReady() {

    }

    @Override
    public void onPicture(ImageReader imageReader) {

    }

    @Override
    public void onError(String message) {
        Log.e(TAG, message);
    }

    @Override
    public void onCameraOpened() {

    }

    @Override
    protected void onDestroy() {
        cam.closeCamera();
        super.onDestroy();
    }
/*    public   Bitmap mergeBitmap(Bitmap bmp1, Bitmap bmp2) {

        bmp2 = Bitmap.createScaledBitmap(bmp2, image_logo.getWidth(), image_logo.getHeight(), false);
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        Matrix matrix = new Matrix();
        // matrix.postScale(1, -1, image_logo.getPivotX(), image_logo.getPivotY());
        canvas.drawBitmap(bmp1, matrix, null);


        canvas.drawBitmap(bmp2, image_logo.getLeft(), image_logo.getTop(), null);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bmOverlay.compress(Bitmap.CompressFormat.PNG, 0  *//*Ignored for PNGs*//* , blob);
//        byte[] byteArray = blob.toByteArray();
//        final Jpeg jpeg2 = new Jpeg(byteArray);
//        jpegArrayList.add(bmOverlay);
//        myListAdapter.notifyDataSetChanged();
        return bmOverlay;
    }*/
}
/*String filename = "image_"+dateFormat.format(new Date())+".jpg";
            File file = new File(getFilesDir(), filename);
            EZCam.saveImage(image, file);
//            Image image2 = reader.acquireLatestImage();
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.capacity()];
            buffer.get(bytes);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);*/