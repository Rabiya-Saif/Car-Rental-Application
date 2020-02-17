package com.CarApp.car.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.CarApp.car.R;
import com.CarApp.car.adapter.MyListAdapter;
import com.CarApp.car.app.SessionManager;
import com.CarApp.car.extra.BITMAP;
import com.CarApp.car.extra.CameraPreview;
import com.camerakit.CameraKitView;

import com.otaliastudios.cameraview.CameraView;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.android.volley.VolleyLog.TAG;

public class TestActivity extends AppCompatActivity implements Camera.PictureCallback, View.OnTouchListener{
    private CameraKitView cameraKitView;
    private CameraView cameraView;
    FloatingActionButton fab;
    public   int _xDelta;
    private Camera mCamera;
    public   int _yDelta;
    public int xaxis,yaxis;
//    JpegImageView image_logo;
private int xDelta;
    private int yDelta;
    ImageView image_logo;
    private String m_Text = "";
    FrameLayout activityMain;
    RecyclerView recyclerView;
    MyListAdapter myListAdapter;
    int []array = {R.drawable.vanone,R.drawable.vantwo,R.drawable.vanthree,R.drawable.vanfour,R.drawable.vanfive,R.drawable.vansix,R.drawable.vaneight};
    ArrayList<Bitmap> jpegArrayList;
    public static final String EXTRA_CAMERA_DATA = "camera_data";
    private static final String KEY_IS_CAPTURING = "is_capturing";
    private TextView mCaptureImageButton;
    private byte[] mCameraData;
    public  static  int index=0;
    TextView text_count,text_complain;
    private ViewGroup mainLayout;
    private boolean mIsCapturing;
    private CameraPreview mPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        cameraKitView = findViewById(R.id.camera);
//        cameraView = findViewById(R.id.camera);
//        cameraView.setFacing(Facing.BACK);
//        cameraView.start();
//        cameraView.
        //cameraView.setlif(this);
        activityMain = findViewById(R.id.activityMain);
        recyclerView=findViewById(R.id.recycler_view);
        jpegArrayList = new ArrayList<>();
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
       /*image_logo = new JpegImageView (this);

        image_logo.setImageResource(R.drawable.vanone);
        image_logo.setScaleType(ImageView.ScaleType.FIT_XY);*/
        image_logo = findViewById(R.id.image_logo);
        fab = findViewById(R.id.fab);
        index=0;
        text_count = findViewById(R.id.text_count);
        image_logo = findViewById(R.id.image_logo);
        image_logo.setImageResource(R.drawable.vanone);
        text_complain = findViewById(R.id.text_complain);
       /* DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        //FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width/2,height );
        RelativeLayout.LayoutParams layoutParams =
               new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
//        layoutParams.leftMargin = width/4;
//        layoutParams.topMargin = height/4;
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        image_logo.setLayoutParams(layoutParams);*/
        image_logo.setOnTouchListener(onTouchListener());
        text_count.setText(String.valueOf(index)+"/"+String.valueOf(array.length));
        if (android.os.Build.VERSION.SDK_INT<23){
            mCamera.startPreview();
            mCamera = getCameraInstance();
            mPreview = new CameraPreview(this, mCamera);//image_logo = findViewById(R.id.image_logo);
            mCamera.setDisplayOrientation(90);
        }
//        activityMain.addView(image_logo);
       // myListAdapter=new MyListAdapter(jpegArrayList,TestActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(TestActivity.this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(myListAdapter);          
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //if (android.os.Build.VERSION.SDK_INT>22)
                {
                    cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(final CameraKitView cameraKitView, final byte[] bytes) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                cameraKitView.post(new Runnable() {
                                @Override
                                public void run() {
                                if (index>=array.length){
                                    new AlertDialog.Builder(TestActivity.this)
                                            .setMessage("Do you want to save these images?")
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                                                    String name = sessionManager.getEmail();
                                                    String number = sessionManager.getKeyUsername();
                                                    long time= System.currentTimeMillis();
                                                    ParseObject myNewObject = new ParseObject("carnumber");
                                                    myNewObject.put("username", name);
                                                    myNewObject.put("carnum", number);
                                                    myNewObject.put("milies", time);
                                                    index=0;
                                                    // Saves the new object.
                                                    // Notice that the SaveCallback is totally optional!
                                                    myNewObject.saveInBackground(new SaveCallback() {
                                                        @Override
                                                        public void done(ParseException e) {
                                                            if (e==null){
                                                                startActivity(new Intent(TestActivity.this,TyreCheckActivity.class));
                                                            }else {
                                                                Toast.makeText(TestActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                                                            }
                                                            // Here you can handle errors, if thrown. Otherwise, "e" should be null
                                                        }
                                                    });

                                                }
                                            })

                                            // A null listener allows the button to dismiss the dialog and take no further action.
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    index=0;
                                                }
                                            })
                                            //.setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }else {

                                    //jpegArrayList.add(jpeg);
                                    //byte[] bitmapdata = jpeg.getJpegBytes();
                                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    Bitmap largeIcon;
                                    if (index==0)
                                        largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.vanone);
                                    else
                                        largeIcon = BitmapFactory.decodeResource(getResources(), array[index]);
                                    mergeBitmap(bitmap1,largeIcon);
                                    index +=1;
                                    text_count.setText(String.valueOf(index)+"/"+String.valueOf(array.length));
                                    if (index<array.length) {
                                        image_logo.setImageResource(array[index]);
                                    }
                                }
                                }
                            });
                            }
                        }).start();
                    }
                });
                }/*else {

                    if (index>=array.length){
                        new AlertDialog.Builder(TestActivity.this)
                                .setMessage("Do you want to save these images?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                                        String name = sessionManager.getEmail();
                                        String number = sessionManager.getKeyUsername();
                                        long time= System.currentTimeMillis();
                                        ParseObject myNewObject = new ParseObject("carnumber");
                                        myNewObject.put("username", name);
                                        myNewObject.put("carnum", number);
                                        myNewObject.put("milies", time);

                                        // Saves the new object.
                                        // Notice that the SaveCallback is totally optional!
                                        myNewObject.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e==null){
                                                    index=0;
                                                    startActivity(new Intent(TestActivity.this,TyreCheckActivity.class));
                                                }else {
                                                    Toast.makeText(TestActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                                                }
                                                // Here you can handle errors, if thrown. Otherwise, "e" should be null
                                            }
                                        });

                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        index=0;
                                    }
                                })
                                //.setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }else {

                        //jpegArrayList.add(jpeg);
                        //byte[] bitmapdata = jpeg.getJpegBytes();
                        mCamera.takePicture(null, null, mPicture);
                        //mergeBitmap(bitmap1,largeIcon);
                        index +=1;
                        if (index<array.length) {
                            text_count.setText(String.valueOf(index)+"/"+String.valueOf(array.length));
                            image_logo.setImageResource(array[index]);
                        }
                    }
                    //Toast.makeText(TestActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        text_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
                builder.setIcon(R.drawable.rotarymoe);
                builder.setTitle("Complain");

// Set up the input
                final EditText input = new EditText(TestActivity.this);

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                    }
                });
                builder.setNegativeButton("Stornieren", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        float downX = event.getX();
        float downY = event.getY();
        int totalX=0, totalY=0;
        int scrollByX, scrollByY;
        int maxX = (int)((width / 2) - (width / 2));
        int maxY = (int)((width / 2) - (height / 2));

        // set scroll limits
        final int maxLeft = (maxX * -1);
        final int maxRight = maxX;
        final int maxTop = (maxY * -1);
        final int maxBottom = maxY;
        {
            float currentX, currentY;
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    break;

                case MotionEvent.ACTION_MOVE:
                    currentX = event.getX();
                    currentY = event.getY();
                    scrollByX = (int)(downX - currentX);
                    scrollByY = (int)(downY - currentY);

                    // scrolling to left side of image (pic moving to the right)
                    if (currentX > downX)
                    {
                        if (totalX == maxLeft)
                        {
                            scrollByX = 0;
                        }
                        if (totalX > maxLeft)
                        {
                            totalX = totalX + scrollByX;
                        }
                        if (totalX < maxLeft)
                        {
                            scrollByX = maxLeft - (totalX - scrollByX);
                            totalX = maxLeft;
                        }
                    }

                    // scrolling to right side of image (pic moving to the left)
                    if (currentX < downX)
                    {
                        if (totalX == maxRight)
                        {
                            scrollByX = 0;
                        }
                        if (totalX < maxRight)
                        {
                            totalX = totalX + scrollByX;
                        }
                        if (totalX > maxRight)
                        {
                            scrollByX = maxRight - (totalX - scrollByX);
                            totalX = maxRight;
                        }
                    }

                    // scrolling to top of image (pic moving to the bottom)
                    if (currentY > downY)
                    {
                        if (totalY == maxTop)
                        {
                            scrollByY = 0;
                        }
                        if (totalY > maxTop)
                        {
                            totalY = totalY + scrollByY;
                        }
                        if (totalY < maxTop)
                        {
                            scrollByY = maxTop - (totalY - scrollByY);
                            totalY = maxTop;
                        }
                    }

                    // scrolling to bottom of image (pic moving to the top)
                    if (currentY < downY)
                    {
                        if (totalY == maxBottom)
                        {
                            scrollByY = 0;
                        }
                        if (totalY < maxBottom)
                        {
                            totalY = totalY + scrollByY;
                        }
                        if (totalY > maxBottom)
                        {
                            scrollByY = maxBottom - (totalY - scrollByY);
                            totalY = maxBottom;
                        }
                    }

                    image_logo.scrollBy(scrollByX, scrollByY);
                    downX = currentX;
                    downY = currentY;
                    break;

            }

            return true;
        }

/*        final int X = (int) event.getRawX();
        xaxis = (int) event.getX();
        yaxis = (int) event.getY();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                view.setLayoutParams(layoutParams);
                break;
        }
        activityMain.invalidate();
        return true;*/
    }


    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        mCameraData = data;
        setupImageDisplay();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean(KEY_IS_CAPTURING, mIsCapturing);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mIsCapturing = savedInstanceState.getBoolean(KEY_IS_CAPTURING, mCameraData == null);
        if (mCameraData != null) {
            setupImageDisplay();
        } else {
            setupImageCapture();
        }
    }
    private void captureImage() {
        mCamera.takePicture(null, null, this);
    }

    private void setupImageCapture() {

        mCamera.startPreview();
        //mCaptureImageButton.setText(R.string.capture_image);
        mCaptureImageButton.setOnClickListener(mCaptureImageButtonClickListener);
    }

    private void setupImageDisplay() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(mCameraData, 0, mCameraData.length);
      /*  mCameraPreview.setVisibility(View.INVISIBLE);
        mCameraImage.setVisibility(View.VISIBLE);
        mCaptureImageButton.setText(R.string.recapture_image);
        mCaptureImageButton.setOnClickListener(mRecaptureImageButtonClickListener);*/

        BITMAP bitmap1=new BITMAP();
        //bitmap1.setBitmap(overlay(bitmap,BitmapFactory.decodeResource(getResources(),image_ids[index])));
        bitmap1.setName("dddd");
        //next.performClick();
        //bitmapArrayList.add(bitmap1);
        myListAdapter.notifyDataSetChanged();
        setupImageCapture();
    }
    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }
    private View.OnClickListener mCaptureImageButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            captureImage();
        }
    };

    private View.OnClickListener mRecaptureImageButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setupImageCapture();
        }
    };

    private View.OnClickListener mDoneButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCameraData != null) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_CAMERA_DATA, mCameraData);
                setResult(RESULT_OK, intent);
            } else {
                setResult(RESULT_CANCELED);
            }
            finish();
        }
    };
    public   Bitmap mergeBitmap(Bitmap bmp1, Bitmap bmp2) {

        bmp2 = Bitmap.createScaledBitmap(bmp2, image_logo.getWidth(), image_logo.getHeight(), false);
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        Matrix matrix = new Matrix();
        // matrix.postScale(1, -1, image_logo.getPivotX(), image_logo.getPivotY());
        canvas.drawBitmap(bmp1, matrix, null);


        canvas.drawBitmap(bmp2, image_logo.getLeft(), image_logo.getTop(), null);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bmOverlay.compress(Bitmap.CompressFormat.PNG, 0  /*Ignored for PNGs*/ , blob);
//        byte[] byteArray = blob.toByteArray();
//        final Jpeg jpeg2 = new Jpeg(byteArray);
        jpegArrayList.add(bmOverlay);
        myListAdapter.notifyDataSetChanged();
        return bmOverlay;
    }
    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        /*Toast.makeText(TestActivity.this,
                                "thanks for new location!", Toast.LENGTH_SHORT)
                                .show();*/
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }
    public Bitmap merge(Bitmap bitmap1,Bitmap bitmap2){
        Bitmap bitmap = null;
        try {

            bitmap = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), bitmap1.getConfig());
            Canvas c = new Canvas(bitmap);
            Resources res = getResources();
            Drawable drawable1 = new BitmapDrawable(bitmap1);
            Drawable drawable2 = new BitmapDrawable(bitmap2);


            drawable1.setBounds(100, 100, 400, 400);
            drawable2.setBounds(150, 150, 350, 350);
            drawable1.draw(c);
            drawable2.draw(c);


        } catch (Exception e) {
        }
        return bitmap;
    }
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(data, 0, data.length);
                Bitmap largeIcon;
                if (index==0)
                    largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.vanone);
                else
                    if (index<array.length){
                    largeIcon = BitmapFactory.decodeResource(getResources(), array[index]);
                    mergeBitmap(bitmap1,largeIcon);
                    }
                //mCamera.startPreview();
//                mCamera.release();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    private Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage){
        secondImage = Bitmap.createScaledBitmap(secondImage, image_logo.getWidth(), image_logo.getHeight(), false);
        Bitmap result = Bitmap.createBitmap(firstImage.getWidth(), firstImage.getHeight(), firstImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0f, 0f, null);
        canvas.drawBitmap(secondImage, 10, 10, null);
        jpegArrayList.add(result);
        myListAdapter.notifyDataSetChanged();
        return result;
    }
}
