package com.example.rgb;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.MacAddressApp;
import com.example.bluetestchange.R;
import com.example.presenter.UpLoadFilePresenter;
import com.ezdata.commonlib.core.mvp.MvpView;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.internal.LinkedTreeMap;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.example.rgb.video.PictureSelectorManager.REQUEST_CAMERA_VIDEO;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RgbAnylizeActivity2 extends BaseActivity implements MvpView {

    private static final String TAG = "MainActivity";

    private ImageView mPaizhao;
    private ImageView mShipin;
    private ImageView imageView;
    private RecyclerView recyclerView;
    File file = null;
    File fileSuoluetu = null;
    //File file = null;
    Uri uri = null;
    Uri uriSuoluetu = null;
    private int REQUEST_CAMERA = 100;
    private int REQUEST_PICKER = 111;
    AlertDialog.Builder builder;
    private ArrayList mArr = new ArrayList();
    private Adapter mAdapter;
    private RgbBeen rgbBeen;
    private Bitmap mBitmap;
    private Bitmap mBitmapSuoluetu;
    private Button btn;

    private Button btn1;
    private Button btn2;


    private DynamicLineChartManager dynamicLineChartManager1;
    private List<Integer> list = new ArrayList<>(); //????????????
    private List<Integer> listNew = new ArrayList<>(); //????????????
    private List<String> names = new ArrayList<>(); //??????????????????
    private List<Integer> colour = new ArrayList<>();//??????????????????


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgb_anylize);
        init();

        /*??????????????????*/
        XXPermissions.with(this)
                // ??????????????????
                .permission(Permission.RECORD_AUDIO)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .permission(Permission.CAMERA)
                .permission(Permission.READ_CONTACTS)
                // ??????????????????
                //.permission(Permission.Group.BLUETOOTH)
                // ?????????????????????????????????????????????
                //.interceptor(new PermissionInterceptor())
                // ???????????????????????????????????????????????????
                //.unchecked()
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
//                            toast("?????????????????????????????????");
                        } else {
//                            toast("?????????????????????????????????????????????????????????");

                            //init();//
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
//                        if (never) {
//                            toast("????????????????????????????????????????????????????????????");
//                            // ??????????????????????????????????????????????????????????????????
//                            XXPermissions.startPermissionActivity(context, permissions);
//                        } else {
//                            toast("?????????????????????????????????");
//                        }
                    }
                });


        performCodeWithPermission("????????????", new PermissionCallback() {
            @Override
            public void hasPermission() {

            }

            @Override
            public void noPermission() {

            }
        }, Manifest.permission.READ_CONTACTS,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Permission.RECORD_AUDIO);


        /**
         * ?????????presenter
         */
        upLoadFilePresenter = new UpLoadFilePresenter();
        upLoadFilePresenter.attachView(this);
    }

    private UpLoadFilePresenter upLoadFilePresenter;
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss", Locale.CHINA);


    //???????????????
    private void init() {
        mPaizhao = findViewById(R.id.paizhao);
        mShipin = findViewById(R.id.shipin);
        imageView = findViewById(R.id.imageView);

        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImg();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadVideo();
            }
        });

        recyclerView = findViewById(R.id.recylerview);
        mPaizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder( RgbAnylizeActivity2.this );
                builder.setTitle("???????????????");
                builder.setMessage("??????????");
                builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        file = new File(getExternalCacheDir(), simpleDateFormat.format(new Date()) + ".png");
                        uri = FileProvider.getUriForFile(getApplication(),"com.example.experiment.fileProvider",file);
                        System.out.println("uri:"+uri);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent,REQUEST_CAMERA);

                        //
                        //PictureSelectorManager.camera(RgbAnylizeActivity.this, REQUEST_CAMERA, "");
                    }
                });
                builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                        intent1.setType("image/*");
                        startActivityForResult(intent1,REQUEST_PICKER);
                    }
                });
                builder.show();
            }
        });
        mShipin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dynamicLineChartManager1.addEntry((int) (Math.random() * 100));
                //
                //PictureSelectorManager.cameraVideo(RgbAnylizeActivity.this, REQUEST_CAMERA_VIDEO);
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                file = new File(getExternalCacheDir(), simpleDateFormat.format(new Date()) + ".mp4");
                //fileSuoluetu = new File(getExternalCacheDir(), simpleDateFormat.format(new Date()) + ".png");
                uri = FileProvider.getUriForFile(getApplication(),"com.example.experiment.fileProvider",file);
                //uriSuoluetu = FileProvider.getUriForFile(getApplication(),"com.example.experiment.fileProvider",fileSuoluetu);













                System.out.println("uri:"+uri);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent,REQUEST_CAMERA_VIDEO);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        mAdapter = new Adapter(mArr,this);


        LineChart mChart2 = (LineChart) findViewById(R.id.dynamic_chart1);
        //????????????
        names.add("???");
        names.add("???");
        names.add("???");
        //????????????
        colour.add(Color.RED);
        colour.add(Color.BLUE);
        colour.add(Color.GREEN);

        dynamicLineChartManager1 = new DynamicLineChartManager(mChart2, names, colour);


        dynamicLineChartManager1.setYAxis(255, 0, 10);

        //????????????????????? todo xcq
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            list.add((int) (Math.random() * 255) );
//                            list.add((int) (Math.random() * 255) );
//                            list.add((int) (Math.random() * 255) );
//                            dynamicLineChartManager1.addEntry(list);
//                            list.clear();
//                        }
//                    });
//                }
//            }
//        }).start();

    }

    private void uploadImg() {
        //File file = new File(picPath);
        //  ????????????
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        upLoadFilePresenter.uploadFile(imageBody,999);
    }

    private void uploadVideo() {
        //File file = new File(picPath);
        //  ????????????
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part videoBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        upLoadFilePresenter.uploadFileVideo(videoBody,1000);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == REQUEST_CAMERA){

                try {
                    mBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    System.out.println("file:"+file+":::uri:"+uri);
                    final ByteArrayOutputStream os = new ByteArrayOutputStream();
//                    mBitmap.compress(Bitmap.CompressFormat.PNG,50,os);
                    //??????????????????
                    Glide.with(this).load(file).into(imageView);
                    //handle(mBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if(requestCode == REQUEST_PICKER){
                System.out.println("hhhhhh");
                zhaopian(data);
            }else if(requestCode == REQUEST_CAMERA_VIDEO){//??????????????????

                    try {
                        Uri imageUri = data.getData();
                        File f = getExternalCacheDir();


                        /***/

                        //mBitmapSuoluetu = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriSuoluetu));
                        //??????????????????
                        Glide.with(this).load(file).into(imageView);
                        /***/
                        showVideoPic();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }

        }
    }
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,int kind) {
        Bitmap bitmap = null;
        // ????????????????????????
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind); //??????ThumbnailUtils??????????????????createVideoThumbnail????????????????????????
        if(bitmap!= null){
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);//??????ThumbnailUtils??????????????????extractThumbnail??????????????????????????????????????????????????????????????????
        }
        return bitmap;
    }

    /**
     * ???????????????????????????
     */
    private void showVideoPic() {
        try {
            mBitmapSuoluetu = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriSuoluetu));
            final ByteArrayOutputStream os = new ByteArrayOutputStream();

            //??????????????????
            Glide.with(this).load(fileSuoluetu).into(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //ImageLoadUtils.loadImageView(RgbAnylizeActivity.this, fileSuoluetu, imageView, R.color.grey_f6, R.drawable.qmui_icon_notify_error);
    }

//    private void handle(Bitmap bitmap) {
//        System.out.println("????????????");
//        mArr.clear();
//        rgbBeen = new RgbBeen("255.213.123","????????????","?????????","60",bitmap);
//        mArr.add(rgbBeen);
//        mAdapter = new Adapter(mArr,this);
//        mAdapter.notifyItemInserted(mArr.size());//??????adapter
//        recyclerView.setAdapter(mAdapter);
//    }

    private int currentListNum =0;
    private int n =0;

    private void handleNew(RgbBeenNew rgbBeenNew) {
        rgbBeenNew.setmBit(mBitmap);
        System.out.println("????????????");
        //mArr.clear();
        //rgbBeen = new RgbBeen("255.213.123","????????????","?????????","60",bitmap);
        //mArr.add(rgbBeenNew);
        mAdapter = new Adapter(rgbBeenNew,this);

        //mAdapter.notifyItemInserted(mArr.size());//??????adapter
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        //todo xcq

        List<Double> rList = rgbBeenNew.getR();
        List<Double> gList = rgbBeenNew.getG();
        List<Double> bList = rgbBeenNew.getB();


        if(rList.size()>50){
            n=50;
        }else{
            n = rList.size();
        }

        //for (int i =0;i<rList.size();i++){
            for (int i =0;i<n;i++){

            Double a = rList.get(i);
            Double b = gList.get(i);
            Double c = bList.get(i);
            list.add(a.intValue());
            list.add(b.intValue());
            list.add(c.intValue());
//            dynamicLineChartManager1.addEntry(list);
//            list.clear();
        }



        /***/

        //????????????????????? todo xcq
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(currentListNum<n){
                                Double a = rList.get(currentListNum);
                                Double b = gList.get(currentListNum);
                                Double c = bList.get(currentListNum);
                                listNew.add(a.intValue());
                                listNew.add(b.intValue());
                                listNew.add(c.intValue());
                                dynamicLineChartManager1.addEntry(listNew);
                                listNew.clear();
                                currentListNum++;
                            }
//                            else{
//                                currentListNum = 0;
//                            }

                        }
                    });
                }
            }
        }).start();





        //

    }


    public void zhaopian(Intent data){
        try {
            //?????????????????????uri?????????data.getData()????????????
            Uri imageUri = data.getData();
            //??????uri??????????????????????????????uri????????????
            Bitmap bm = null;
            if(imageUri != null){
                try {
                    //??????????????????uri???????????????????????????????????????????????????????????????
                    bm = getBitmapFormUri(imageUri);	//??????????????????
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                //?????????????????????????????????bundle???
                Bundle bundleExtras = data.getExtras();
                if(bundleExtras != null){
                    bm = bundleExtras.getParcelable("data");
                }
            }
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG,100,os);
            System.out.println("os.toByteArray()???"+os.toByteArray());
            //ImageLoadUtils.loadImageView();
            Glide.with(RgbAnylizeActivity2.this).load(imageUri).into(imageView);
            mBitmap = bm;

            file = uriToFileApiQ(imageUri);
            //
            //file = new File();
            //uri = FileProvider.getUriForFile(getApplication(),"com.example.experiment.fileProvider",file);

            //handle(bm);//todo xcq

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //??????????????????
    public Bitmap getBitmapFormUri(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = getContentResolver().openInputStream(uri);

        //??????????????????????????????????????????????????????bitmap?????????????????????????????????inJustDecodeBounds???true
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;//??????????????????
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;

        //??????????????????480x800?????????
        float hh = 1024f;//?????????????????????800f
        float ww = 1024f;//?????????????????????480f
        //????????????????????????????????????????????????????????????????????????????????????????????????
        int be = 1;//be=1???????????????
        if (originalWidth > originalHeight && originalWidth > ww) {//???????????????????????????????????????????????????
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//???????????????????????????????????????????????????
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //????????????
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//??????????????????
        bitmapOptions.inDither = true;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        input = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();


        return compressImage(bitmap);//?????????????????????
    }
    public Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//???????????????????????????100????????????????????????????????????????????????baos???
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //?????????????????????????????????????????????100kb,??????????????????
            baos.reset();//??????baos?????????baos
            //??????????????? ??????????????? ????????????????????? ???????????????100????????????0?????????  ???????????????????????????????????????????????????
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//????????????options?????????????????????????????????baos???
            options -= 10;//???????????????10
            if (options<=0)
                break;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//?????????????????????baos?????????ByteArrayInputStream???
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//???ByteArrayInputStream??????????????????
        return bitmap;
    }

    @Override
    public void onStartRequest(int requestCode) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage("????????????????????????????????????");
        progressDialog.show();
    }
    private ProgressDialog progressDialog;

    @Override
    public void onSuccess(int requestCode, Object o) {

        list.clear();
        currentListNum = 0;//???????????????????????????????????????????????????
        dynamicLineChartManager1.clear();

        LinkedTreeMap<String,Object> tempMap = (LinkedTreeMap) o;
        RgbBeenNew beanTemp = new RgbBeenNew();
        beanTemp.setColorGet((String) tempMap.get("colorGet"));
        beanTemp.setArtGet((String) tempMap.get("artGet"));
        beanTemp.setLightGet((String) tempMap.get("lightGet"));
        beanTemp.setR((List<Double>) tempMap.get("r"));
        beanTemp.setG((List<Double>) tempMap.get("g"));
        beanTemp.setB((List<Double>) tempMap.get("b"));
        //RgbBeenNew bean = (RgbBeenNew) o;
        handleNew(beanTemp);
    }

    @Override
    public void onErrorCode(int resultCode, String msg, int requestCode) {
        progressDialog.dismiss();
    }

    @Override
    public void onEndRequest(int requestCode) {
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(Throwable e) {
        progressDialog.dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static File uriToFileApiQ(Uri uri) {
        File file = null;
        //android10????????????
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //??????????????????????????????
            ContentResolver contentResolver = MacAddressApp.getInstance().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(MacAddressApp.getInstance().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }


    /**
     * ??????????????????????????????
     *
     * @param path ?????????????????????
     * @return Bitmap ???????????????Bitmap
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }

}