package com.CarApp.car.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.CarApp.car.R;
import com.CarApp.car.adapter.MyListAdapter;
import com.CarApp.car.app.SessionManager;
import com.CarApp.car.extra.CameraPreview;
import com.CarApp.car.modelclass.ResponseModel;
import com.CarApp.car.modelclass.ServerResponse;
import com.CarApp.car.retrofit.ApiClient;
import com.CarApp.car.retrofit.ApiInterface;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.android.volley.VolleyLog.TAG;

public class CameraActivity extends AppCompatActivity implements   View.OnClickListener, View.OnTouchListener{
    //private static String arraynames;
    //    private CameraKitView cameraKitView;
    FloatingActionButton fab;
    public   int _xDelta;
    private int xDelta;
    private String m_Text = "";
    private int yDelta;
    public   int _yDelta;
    public int xaxis,yaxis;
    public  ImageView image_logo;
    TextView text_complain;
    FrameLayout activityMain;
    RecyclerView recyclerView;
    MyListAdapter myListAdapter;
    int []array = {R.drawable.vaneight,R.drawable.vantwo,R.drawable.vanthree,R.drawable.vanfour,R.drawable.vanfive,R.drawable.vansix,R.drawable.vanseven,R.drawable.vanone};
    private static String []arraynames = {"Front","Rear","Left","Right","Center","3D1","3D2","Meter",""};
    ArrayList<Bitmap> jpegArrayList;
    public static final String EXTRA_CAMERA_DATA = "camera_data";
    private static final String KEY_IS_CAPTURING = "is_capturing";
    //private Camera mCamera;
    private TextView mCaptureImageButton;
    private byte[] mCameraData;
    public  static  int index=0;
    TextView text_count;
    private boolean mIsCapturing;
    private Camera mCamera;
    private ViewGroup mainLayout;
    private CameraPreview mPreview;
    ProgressBar progressBar;
    SessionManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        activityMain = findViewById(R.id.activityMain);
        recyclerView=findViewById(R.id.recycler_view);
        jpegArrayList = new ArrayList<Bitmap>();
        image_logo = findViewById(R.id.image_logo);
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        image_logo.setImageResource(R.drawable.vaneight);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        manager = new SessionManager(getApplicationContext());
        progressBar.setIndeterminateDrawable(doubleBounce);

        /*image_logo = new JpegImageView (this);
        image_logo.setImageResource(R.drawable.vanone);
        image_logo.setScaleType(ImageView.ScaleType.FIT_XY);*/
        fab = findViewById(R.id.fab);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        index=0;
        text_complain = findViewById(R.id.text_complain);
        text_count = findViewById(R.id.text_count);
        text_count.setText("1");
/*        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;*/
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width,height/2);
//        layoutParams.leftMargin = 50;
//        layoutParams.topMargin = 50;
//        image_logo.setLayoutParams(layoutParams);
        image_logo.setOnTouchListener(onTouchListener());
        mCamera = getCameraInstance();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        //mCamera.setDisplayOrientation(90);
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
//        Camera.Parameters params = mCamera.getParameters();
//        List<Camera.Size> sizes = params.getSupportedPictureSizes();
//        params.setPictureSize(height, width);
//        mCamera.setParameters(params);
        Camera.Parameters params = mCamera.getParameters();
//*EDIT*//params.setFocusMode("continuous-picture");
//It is better to use defined constraints as opposed to String, thanks to AbdelHady
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(params);
        mCamera.startPreview();

//        activityMain.addView(image_logo);
        myListAdapter=new MyListAdapter(jpegArrayList,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(CameraActivity.this,LinearLayoutManager.HORIZONTAL,false));
        myListAdapter = new MyListAdapter(jpegArrayList,getApplicationContext());
        recyclerView.setAdapter(myListAdapter);
        text_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this);
                builder.setIcon(R.drawable.rotarymoe);
                builder.setTitle("Complain");

// Set up the input
                final EditText input = new EditText(CameraActivity.this);

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        saveComplain(m_Text);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index>=array.length){
                    new AlertDialog.Builder(CameraActivity.this)
                            .setTitle("Save Images?")
                            .setMessage("Do you want to save these images?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    startActivity(new Intent(CameraActivity.this,TyreCheckActivity.class));
                                    finish();

                                }
                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    index=0;
                                    startActivity(new Intent(CameraActivity.this,BasicActivity.class));
                                    finish();
                                }
                            })
                            .show();
                }else {
                    fab.setClickable(false);
                    progressBar.setVisibility(View.VISIBLE);
                    mCamera.takePicture(null, null, mPicture);
                }
            }
        });
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
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
        return true;
    }



    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean(KEY_IS_CAPTURING, mIsCapturing);
    }

    @Override
    public void onClick(View view) {

    }

    public   Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);

        canvas.drawBitmap(bmp2,image_logo.getLeft() , image_logo.getTop(), null);
        return bmOverlay;
    }
    public   Bitmap mergeBitmap(Bitmap bmp1, Bitmap bmp2) throws IOException {

        /*BitmapMergerTask task = new BitmapMergerTask();
        task.setBaseBitmap(bmp1)
                .setMergeBitmap(bmp2)
                .setMergeListener(new BitmapMergerTask.OnMergeListener() {
                    @Override
                    public void onMerge(BitmapMergerTask task, Bitmap mergedBitmap) {

                    }
                })
                .setScale(0.5)
                .setOffsets(image_logo.getLeft(), image_logo.getTop())
                .merge();*/
        //path = getIntent().getStringExtra("path");
   /*     Bitmap bitmap = BitmapFactory.decodeFile(path);
        ExifInterface exif = new ExifInterface(path);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                break;
        }
        bmp1 = bitmap;*/
        /*DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Matrix matrix = new Matrix();

       // matrix.postRotate(90);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmp1, bmp1.getWidth(), bmp1.getHeight(), true);
        bmp1 = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        bmp2 = Bitmap.createScaledBitmap(bmp2,height/2,width, false);
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, image_logo.getLeft() , image_logo.getTop(), null);*/
        /*int[] location = new int[2];
        image_logo.getLocationOnScreen(location);
        bmp2 = Bitmap.createScaledBitmap(bmp2, 200, 300, false);
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        Matrix matrix = new Matrix();
        // matrix.postScale(1, -1, image_logo.getPivotX(), image_logo.getPivotY());
        canvas.drawBitmap(bmp1, matrix, null);

//        BitmapMergerTask
        canvas.drawBitmap(bmp2, location[1], location[0], null);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bmOverlay.compress(Bitmap.CompressFormat.PNG, 0  *//*Ignored for PNGs*//* , blob);*/
//        FileOutputStream fos = new FileOutputStream(path);
//        fos.write(data);
//        fos.close();
        Bitmap bmOverlay = null;
        try {
            bmOverlay = Bitmap.createBitmap(bmp1.getWidth(),bmp1.getHeight(), bmp1.getConfig());
            Canvas c = new Canvas(bmOverlay);
            bmp2 = Bitmap.createScaledBitmap(bmp2, image_logo.getWidth(), image_logo.getHeight(), false);
            Drawable drawable1 = new BitmapDrawable(bmp1);
            Drawable drawable2 = new BitmapDrawable(bmp2);
//            Matrix matrix = new Matrix();
//             matrix.postScale(1, -1, image_logo.getPivotX(), image_logo.getPivotY());
//            c.drawBitmap(bmp1, matrix, null);
//            c.drawBitmap(bmp2, image_logo.getLeft() , image_logo.getTop(), null);
//            drawable1.setBounds(100, 100, 400, 400);
            drawable1.setBounds(0,0,bmp1.getWidth(),bmp1.getHeight());
//            drawable2.setBounds(100, 100, bmp1.getWidth()-150,bmp1.getHeight()-200);
//            drawable2.setBounds(image_logo.getLeft()-600, image_logo.getTop()-600, image_logo.getRight()-600, image_logo.getBottom()-600);
//            drawable1.setBounds(100, 100, 400, 400);
            drawable2.setBounds(image_logo.getWidth(),image_logo.getHeight(), 0,0);
//            LayerDrawable finalDrawable = new LayerDrawable(new Drawable[] {drawable1, drawable2});
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                finalDrawable.setLayerInsetBottom(0, drawable2.getIntrinsicHeight());
                finalDrawable.setLayerGravity(1,  Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
            }*/
            drawable1.draw(c);
            drawable2.draw(c);
//            finalDrawable.draw(c);

        } catch (Exception e) {
        }

        return bmOverlay;
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
            //progressBar.setVisibility(View.VISIBLE);
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions");
                return;
            }

            final Bitmap bitmap1 = BitmapFactory.decodeByteArray(data, 0, data.length);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                uploadFile(pictureFile.getPath(),arraynames[index]);
                MediaScannerConnection.scanFile(getApplicationContext(),
                        new String[] { pictureFile.toString() }, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("ExternalStorage", "Scanned " + path + ":");
                                Log.i("ExternalStorage", "-> uri=" + uri);
                            }
                        });

                //Bitmap myBitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        jpegArrayList.add(bitmap1);
                        myListAdapter.notifyDataSetChanged();
                        mCamera.startPreview();
                        fab.setClickable(true);
                        progressBar.setVisibility(View.GONE);
                        index +=1;
                        if (index<array.length){
                            image_logo.setImageResource(array[index]);
                            text_count.setText(String.valueOf(index+1));
                        }
                        else{
                            if (index>=array.length){
                                new AlertDialog.Builder(CameraActivity.this)
                                        .setTitle("Save Images?")
                                        .setMessage("Do you want to save these images?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                progressBar.setVisibility(View.VISIBLE);
                                                startActivity(new Intent(CameraActivity.this,TyreCheckActivity.class));
                                                finish();

                                            }
                                        })
                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                index=0;
                                                startActivity(new Intent(CameraActivity.this,BasicActivity.class));
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                            //fab.setClickable(true);
                            image_logo.setVisibility(View.GONE);
                        }
                    }
                },2000);

                //fab.setClickable(true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            // bitmap1 = getBitmap(pictureFile.getPath());
/*            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), array[index]);
            Bitmap bmp  = null;
            try {
                bmp = mergeBitmap(bitmap1,largeIcon);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100  Ignored for PNGs , blob);
            byte[] byteArray = blob.toByteArray();
            final Jpeg jpeg2 = new Jpeg(byteArray);*/
            //camera.setDisplayOrientation(90);

           // mCamera.startPreview();

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
                    "IMG_"+arraynames[index]+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
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

    private Bitmap getBitmap(String path) {
        Log.e("inside of", "getBitmap = "+path);
        try {
            Bitmap b = null;
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            Matrix matrix = new Matrix();
            ExifInterface exifReader = new ExifInterface(path);
            int orientation = exifReader.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            int rotate = 0;
            if (orientation ==ExifInterface.ORIENTATION_NORMAL) {
                // Do nothing. The original image is fine.
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                rotate = 90;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                rotate = 180;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                rotate = 270;
            }
            DisplayMetrics displaymetrics;
            displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int  screenWidth = displaymetrics.widthPixels;
            int screenHeight = displaymetrics.heightPixels;
            matrix.postRotate(rotate);
            try {
                b = loadBitmap(path, rotate, screenWidth, screenHeight);
                //btn_RotateImg.setEnabled(true);
            } catch (OutOfMemoryError e) {
                //btn_RotateImg.setEnabled(false);
            }
            System.gc();
            return b;
        } catch (Exception e) {
            Log.e("my tag", e.getMessage(), e);
            return null;
        }
    }
    public static Bitmap loadBitmap(String path, int orientation, final int targetWidth, final int targetHeight) {
        Bitmap bitmap = null;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int sourceWidth, sourceHeight;
            if (orientation == 90 || orientation == 270) {
                sourceWidth = options.outHeight;
                sourceHeight = options.outWidth;
            } else {
                sourceWidth = options.outWidth;
                sourceHeight = options.outHeight;
            }
            if (sourceWidth > targetWidth || sourceHeight > targetHeight) {
                float widthRatio = (float)sourceWidth / (float)targetWidth;
                float heightRatio = (float)sourceHeight / (float)targetHeight;
                float maxRatio = Math.max(widthRatio, heightRatio);
                options.inJustDecodeBounds = false;
                options.inSampleSize = (int)maxRatio;
                bitmap = BitmapFactory.decodeFile(path, options);
            } else {
                bitmap = BitmapFactory.decodeFile(path);
            }
            if (orientation > 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(orientation);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
            sourceWidth = bitmap.getWidth();
            sourceHeight = bitmap.getHeight();
            if (sourceWidth != targetWidth || sourceHeight != targetHeight) {
                float widthRatio = (float)sourceWidth / (float)targetWidth;
                float heightRatio = (float)sourceHeight / (float)targetHeight;
                float maxRatio = Math.max(widthRatio, heightRatio);
                sourceWidth = (int)((float)sourceWidth / maxRatio);
                sourceHeight = (int)((float)sourceHeight / maxRatio);
                bitmap = Bitmap.createScaledBitmap(bitmap, sourceWidth, sourceHeight, true);
            }
        } catch (Exception e) {
        }
        return bitmap;
    }
    public static Point getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Point(location[0], location[1]);
    }
    public Bitmap combo(Bitmap one,Bitmap two){
        Bitmap bmOverlay = Bitmap.createBitmap(one.getWidth(),one.getHeight(), one.getConfig());
        Canvas comboImage = new Canvas(bmOverlay);
// Then draw the second on top of that
        comboImage.drawBitmap(one,one.getWidth(),one.getHeight(),null);
        comboImage.drawBitmap(two, 0f, 0f, null);
//        comboImage.compress(Bitmap.CompressFormat.PNG, 50, os)
        //jpegArrayList.add(bmOverlay);
        myListAdapter.notifyDataSetChanged();
        return bmOverlay;
    }
    private Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage){

        Bitmap result = Bitmap.createBitmap(firstImage.getWidth(), firstImage.getHeight(), firstImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0f, 0f, null);
        canvas.drawBitmap(secondImage, 10, 10, null);
        //jpegArrayList.add(result);
        myListAdapter.notifyDataSetChanged();
        return result;
    }
    private void uploadFile(String path,String name) {
//        pDialog.show();
        String id = manager.getKeyOrigin();
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        ApiInterface apiService= ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.uploadFile(fileToUpload, filename,name,id);
        call.enqueue(new Callback<ServerResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
//                pDialog.dismiss();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        if (!fab.isClickable())
                            fab.setClickable(true);
                        fab.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplicationContext().getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        if (!fab.isClickable())
                            fab.setClickable(true);
                        fab.setVisibility(View.VISIBLE);
                        //Toast.makeText(getActivity().getApplicationContext().getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                pDialog.dismiss();
                //Toast.makeText(getActivity().getApplicationContext().getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void saveComplain(String complain){
        ApiInterface apiservice= ApiClient.getClient().create(ApiInterface.class);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String id = sessionManager.getKeyOrigin();
        String tag = arraynames[index];
        Call<ResponseModel> call=apiservice.saveComplain(id,complain,tag);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
                boolean error = response.body().isError();
                if (!error){
                    Toast.makeText(CameraActivity.this, ""+response.body().getError_msg(), Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(CameraActivity.this, ""+response.body().getError_msg(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(CameraActivity.this)
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
