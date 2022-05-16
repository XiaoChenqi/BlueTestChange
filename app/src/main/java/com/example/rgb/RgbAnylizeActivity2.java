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
    private List<Integer> list = new ArrayList<>(); //数据集合
    private List<Integer> listNew = new ArrayList<>(); //数据集合
    private List<String> names = new ArrayList<>(); //折线名字集合
    private List<Integer> colour = new ArrayList<>();//折线颜色集合


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgb_anylize);
        init();

        /*请求录像权限*/
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.RECORD_AUDIO)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .permission(Permission.CAMERA)
                .permission(Permission.READ_CONTACTS)
                // 申请多个权限
                //.permission(Permission.Group.BLUETOOTH)
                // 设置权限请求拦截器（局部设置）
                //.interceptor(new PermissionInterceptor())
                // 设置不触发错误检测机制（局部设置）
                //.unchecked()
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
//                            toast("获取录音和日历权限成功");
                        } else {
//                            toast("获取部分权限成功，但部分权限未正常授予");

                            //init();//
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
//                        if (never) {
//                            toast("被永久拒绝授权，请手动授予录音和日历权限");
//                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
//                            XXPermissions.startPermissionActivity(context, permissions);
//                        } else {
//                            toast("获取录音和日历权限失败");
//                        }
                    }
                });


        performCodeWithPermission("获取权限", new PermissionCallback() {
            @Override
            public void hasPermission() {

            }

            @Override
            public void noPermission() {

            }
        }, Manifest.permission.READ_CONTACTS,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Permission.RECORD_AUDIO);


        /**
         * 初始化presenter
         */
        upLoadFilePresenter = new UpLoadFilePresenter();
        upLoadFilePresenter.attachView(this);
    }

    private UpLoadFilePresenter upLoadFilePresenter;
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss", Locale.CHINA);


    //初始化控件
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
                builder.setTitle("图像提示框");
                builder.setMessage("确定吗?");
                builder.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
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
                builder.setNegativeButton("相册", new DialogInterface.OnClickListener() {
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
        //折线名字
        names.add("红");
        names.add("蓝");
        names.add("绿");
        //折线颜色
        colour.add(Color.RED);
        colour.add(Color.BLUE);
        colour.add(Color.GREEN);

        dynamicLineChartManager1 = new DynamicLineChartManager(mChart2, names, colour);


        dynamicLineChartManager1.setYAxis(255, 0, 10);

        //死循环添加数据 todo xcq
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
        //  图片参数
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        upLoadFilePresenter.uploadFile(imageBody,999);
    }

    private void uploadVideo() {
        //File file = new File(picPath);
        //  图片参数
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
                    //图像快速显示
                    Glide.with(this).load(file).into(imageView);
                    //handle(mBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if(requestCode == REQUEST_PICKER){
                System.out.println("hhhhhh");
                zhaopian(data);
            }else if(requestCode == REQUEST_CAMERA_VIDEO){//视频录制成功

                    try {
                        Uri imageUri = data.getData();
                        File f = getExternalCacheDir();


                        /***/

                        //mBitmapSuoluetu = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriSuoluetu));
                        //图像快速显示
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
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind); //调用ThumbnailUtils类的静态方法createVideoThumbnail获取视频的截图；
        if(bitmap!= null){
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);//调用ThumbnailUtils类的静态方法extractThumbnail将原图片（即上方截取的图片）转化为指定大小；
        }
        return bitmap;
    }

    /**
     * 拍完视频以后的交互
     */
    private void showVideoPic() {
        try {
            mBitmapSuoluetu = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriSuoluetu));
            final ByteArrayOutputStream os = new ByteArrayOutputStream();

            //图像快速显示
            Glide.with(this).load(fileSuoluetu).into(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //ImageLoadUtils.loadImageView(RgbAnylizeActivity.this, fileSuoluetu, imageView, R.color.grey_f6, R.drawable.qmui_icon_notify_error);
    }

//    private void handle(Bitmap bitmap) {
//        System.out.println("处理进来");
//        mArr.clear();
//        rgbBeen = new RgbBeen("255.213.123","色差严重","质量差","60",bitmap);
//        mArr.add(rgbBeen);
//        mAdapter = new Adapter(mArr,this);
//        mAdapter.notifyItemInserted(mArr.size());//更新adapter
//        recyclerView.setAdapter(mAdapter);
//    }

    private int currentListNum =0;
    private int n =0;

    private void handleNew(RgbBeenNew rgbBeenNew) {
        rgbBeenNew.setmBit(mBitmap);
        System.out.println("处理进来");
        //mArr.clear();
        //rgbBeen = new RgbBeen("255.213.123","色差严重","质量差","60",bitmap);
        //mArr.add(rgbBeenNew);
        mAdapter = new Adapter(rgbBeenNew,this);

        //mAdapter.notifyItemInserted(mArr.size());//更新adapter
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

        //死循环添加数据 todo xcq
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
            //返回选择图片的uri，参数data.getData()方法获得
            Uri imageUri = data.getData();
            //通过uri的方式返回，部分手机uri可能为空
            Bitmap bm = null;
            if(imageUri != null){
                try {
                    //将获得的图片uri进行压缩，不然图片像素过大，可能会出现错误
                    bm = getBitmapFormUri(imageUri);	//此方法在下面
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                //部分手机可能直接存放在bundle中
                Bundle bundleExtras = data.getExtras();
                if(bundleExtras != null){
                    bm = bundleExtras.getParcelable("data");
                }
            }
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG,100,os);
            System.out.println("os.toByteArray()："+os.toByteArray());
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
    //压缩图片像素
    public Bitmap getBitmapFormUri(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = getContentResolver().openInputStream(uri);

        //这一段代码是不加载文件到内存中也得到bitmap的真是宽高，主要是设置inJustDecodeBounds为true
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;//不加载到内存
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;

        //图片分辨率以480x800为标准
        float hh = 1024f;//这里设置高度为800f
        float ww = 1024f;//这里设置宽度为480f
        //缩放比，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        input = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();


        return compressImage(bitmap);//再进行质量压缩
    }
    public Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            if (options<=0)
                break;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    @Override
    public void onStartRequest(int requestCode) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage("正在分析图片或视频中……");
        progressDialog.show();
    }
    private ProgressDialog progressDialog;

    @Override
    public void onSuccess(int requestCode, Object o) {

        list.clear();
        currentListNum = 0;//每次重新获取的时候，要清空这些数据
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
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
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
     * 获取视频文件第一帧图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }

}