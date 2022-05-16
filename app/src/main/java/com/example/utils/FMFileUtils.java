package com.example.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
//import android.support.v4.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.bluetestchange.R;
import com.example.rgb.video.FcallBack;
//import com.facilityone.wireless.a.arch.R;
//import com.facilityone.wireless.a.arch.callback.FcallBack;
//import com.facilityone.wireless.basiclib.app.FM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.core.content.FileProvider;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:文件操作
 * Date: 2018/7/3 上午11:03
 */
public class FMFileUtils {

    /**
     * 打开文件
     *
     * @param file
     * @param context
     */
    public static void openFile(File file, Context context) {
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //设置intent的Action属性
            intent.setAction(Intent.ACTION_VIEW);
            //获取文件file的MIME类型
            String extension = FileUtils.getFileExtension(file);
            String mimeType = MimeTypeMap.getSingleton()
                    .getMimeTypeFromExtension(extension);
            //设置intent的data和Type属性。
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uriForFile;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String packageName = context.getPackageName();
                uriForFile = FileProvider.getUriForFile(context, packageName + ".fileprovider", file);
            } else {
                uriForFile = Uri.fromFile(file);
            }
            intent.setDataAndType(/*uri*/uriForFile, mimeType);
            //跳转
            context.startActivity(intent);     //这里最好try一下，有可能会报错。 //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(R.string.arch_file_error);
        }
    }

    //缓存文件地址
    public static String getPath(String path) {
        String p = FM.getCachePath() + path + "/";
        File file = new File(p);
        if (!file.exists()) {
            file.mkdirs();
        }
        return p;
    }

    /**
     * 崩溃日志存储地址
     *
     * @return
     */
    public static String getCrashPath() {
        return getPath("crash");
    }

    /**
     * 图片缓存地址
     *
     * @return
     */
    public static String getPicPath() {
        return getPath("pic");
    }

    /**
     * 附件
     *
     * @return
     */
    public static String getAttachmentPath() {
        return getPath("attachment");
    }

    /**
     * audio
     *
     * @return
     */
    public static String getAudioPath() {
        return getPath("audio");
    }

    /**
     * video
     *
     * @return
     */
    public static String getVideoPath() {
        return getPath("video");
    }


    private static String TAG="zhouyang";

    public static void downloadFile(String fileUrl, final String fileName,final FcallBack fcb) {
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(fileUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "请求失败: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        writeFile(body,fileName,fcb);
                    }
                }
            }
        });
    }


    private static void writeFile(ResponseBody body, String fileName, FcallBack fcb) {
        InputStream is = null;  //网络输入流
        FileOutputStream fos = null;  //文件输出流
        is = body.byteStream();

        String filePath = FMFileUtils.getAttachmentPath()+fileName;
        Log.d("zhouyang", "writeFile: "+filePath);
        File file = new File(filePath);
        try {
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            long totalSize = body.contentLength();  //文件总大小
            long sum = 0;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                sum += len;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fcb.onError();
        } catch (IOException e) {
            e.printStackTrace();
            fcb.onError();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //fcb.onError();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //fcb.onError();
                }
            }
            fcb.onFinish();
            fcb.onSuccess(file);
        }
    }
}
