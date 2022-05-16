package com.ezdata.commonlib.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezdata.commonlib.R;
import com.ezdata.commonlib.core.App;


/**
 * Description：加载框
 * Created by：Kyle
 * Date：2017/2/7
 */
public class DialogUtils {
    private static final int START_DIALOG = 0;
    private static final int UPDATE_DIALOG = 1;
    private static final int STOP_DIALOG = 2;
    private static AlertDialog dialog = null;
    private static TextView title = null;
    private static Context context = null;

    private static Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            String message = "";
            switch (msg.what) {
                case START_DIALOG:// 启动加载框
                    message = (String) msg.obj;
                    if (dialog != null) {
                        stopLoad();
                    }
                    init(message);
                    isTouchDismiss(false);
                    break;
                case UPDATE_DIALOG:// 更新加载框
                    message = (String) msg.obj;
                    if (title.VISIBLE == View.VISIBLE) {
                        if (TextUtils.isEmpty(message)) {
                            title.setVisibility(View.GONE);
                        } else {
                            title.setText(message);
                        }
                    } else {
                        if (!TextUtils.isEmpty(message)) {
                            title.setText(message);
                            title.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case STOP_DIALOG:// 停止加载框
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog.cancel();
                        dialog = null;
                        title = null;
                    }
                    break;
            }
        };
    };

    /**
     * @方法说明:加载控件与布局
     * @方法名称:init
     * @返回值:void
     */
    static View v;
    private static void init(String mssg) {
        if (null != context) {

            if(dialog==null) {
                LayoutInflater flat = LayoutInflater.from(context);
                v= flat.inflate(R.layout.loading_dialog, null);
                // 创建对话
                dialog = new AlertDialog.Builder(context, R.style.loading_dialog).create();
                // 设置返回键点击消失对话框
                dialog.setCancelable(true);
                // 设置点击返回框外边不消失
                dialog.setCanceledOnTouchOutside(false);
                // 给该对话框增加系统权限
//            dialog.getWindow().setType(
//                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                // 显示对话
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            dialog.show();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    dialog = null;
                    title = null;
                }
            });
            // 加载控件
            ImageView progressImageView = (ImageView) v
                    .findViewById(R.id.img);
            title = (TextView) v.findViewById(R.id.tipTextView);
            View loadingGroup = v.findViewById(R.id.dialog_view);

            if (TextUtils.isEmpty(mssg)) {
                title.setVisibility(View.GONE);
//                loadingGroup.setBackgroundColor(App.newInstance().getApplicationContext().getResources().getColor(R.color.colorless));
            } else {
                title.setVisibility(View.VISIBLE);
                title.setText(mssg);
            }
            Animation progressAnimato = AnimationUtils.loadAnimation(
                    App.getInstance().getApplicationContext(), R.anim.loading_animation);
            progressImageView.startAnimation(progressAnimato);

            // 必须放到显示对话框下面，否则显示不出效果
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
            // 加载布局组件
            dialog.getWindow().setContentView(v);
        }
    }

    /**
     * @方法说明:启动对话框
     * @方法名称:startLoad
     * @param con
     * @param msg
     * @返回值:void
     */
    public static void startLoad(Context con, String msg) {
        context = con;
        Message mssage = new Message();
        mssage.what = START_DIALOG;
        mssage.obj = msg;
        handler.sendMessage(mssage);
    }

    /**
     * @方法说明:更新显示的内容
     * @方法名称:UpdateMsg
     * @param msg
     * @返回值:void
     */
    public static void UpdateMsg(String msg) {
        Message message = new Message();
        message.what = UPDATE_DIALOG;
        message.obj = msg;
        handler.sendMessage(message);
    }

    /**
     * @方法说明:允许加载条转动的时候去点击系统返回键
     * @方法名称:openCancelable
     * @param flag
     * @返回值:void
     */
    public static void openCancelable(boolean flag) {
        if (dialog != null) {
            dialog.setCancelable(flag);
        }
    }

    /**
     * @方法说明:允许点击对话框触摸消失
     * @方法名称:isTouchDismiss
     * @param isdimiss
     * @返回值:void
     */
    public static void isTouchDismiss(boolean isdimiss) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(isdimiss);
        }
    }

    /**
     * @方法说明:让警告框消失
     * @方法名称:dismiss
     * @返回值:void
     */
    public static void stopLoad() {
        handler.sendEmptyMessage(STOP_DIALOG);
    }

    /**
     * app升级dialog
     */
//    public static void showAppUpdateDialog(final Context context, String version, final String appPath, String appDescribe, final String appName) {
//        final Dialog updateDialog = new android.app.AlertDialog.Builder(context, R.style.custom_dialog).create();
//
//        updateDialog.setCancelable(true);
//        updateDialog.setCanceledOnTouchOutside(false);
//        updateDialog.show();
//
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_update_version, null);
//        updateDialog.setContentView(view);
//
//        TextView mTvName = (TextView) view.findViewById(R.id.tv_version_name);
//        TextView mTvContent = (TextView) view.findViewById(R.id.tv_update_content);
//        Button mBtnUpdate = (Button) view.findViewById(R.id.btn_update);
//        ImageView mImgClose = (ImageView) view.findViewById(R.id.img_close);
//
//        mTvName.setText(version);
//        mTvContent.setText(appDescribe);
//        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateDialog.dismiss();
//                Intent intent=new Intent(context, DownloadService.class);
//                intent.putExtra("url",appPath);
//                intent.putExtra("apk_name",appName);
//                context.startService(intent);
//            }
//        });
//
//        mImgClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateDialog.dismiss();
//            }
//        });
//    }
}
