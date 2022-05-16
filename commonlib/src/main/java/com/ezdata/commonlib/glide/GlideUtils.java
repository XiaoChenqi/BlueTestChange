/*
 * {EasyGank}  Copyright (C) {2015}  {CaMnter}
 *
 * This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 * You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <http://www.gnu.org/licenses/>.
 *
 * The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <http://www.gnu.org/philosophy/why-not-lgpl.html>.
 */

package com.ezdata.commonlib.glide;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.ezdata.commonlib.R;
import com.ezdata.commonlib.widget.GlideCircleTransform;


/**
 * Description：GlideUtils
 * Created by：Kyle
 * Date：2017/2/10
 */
public class GlideUtils {

    private static final String TAG = "GlideUtils";


    /**
     * glide加载图片
     *
     * @param view view
     * @param url url
     */
    public static void display(ImageView view, String url) {
        displayUrl(view, url, R.mipmap.ic_launcher);
    }
    public static void displayGif(ImageView view, String url) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        Glide.with(context).load(url).placeholder( R.mipmap.ic_launcher).into(view);
    }
    public static void displayCircleGif(ImageView view, String url) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        Glide.with(context).load(url).transform(new GlideCircleTransform(view.getContext())).placeholder( R.mipmap.ic_launcher).into(view);
    }

    /**
     * glide加载图片
     *
     * @param view view
     * @param url url
     * @param defaultImage defaultImage
     */
    public static void displayUrl(final ImageView view, String url,
                                  @DrawableRes int defaultImage) {
        // 不能崩
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？如果view已经关闭，加载图片没有意义，生命周期都没了
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                 .load(url)
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .placeholder(defaultImage)
                 .crossFade()
                 .centerCrop()
                 .into(view)
                 .getSize(new SizeReadyCallback() {
                     @Override
                     public void onSizeReady(int width, int height) {
                         if (!view.isShown()) {
                             view.setVisibility(View.VISIBLE);
                         }
                     }
                 });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 设置图片的scaletype为fitCenter
     * @param view
     * @param url
     * @param defaultImage
     */
    public static void displayUrlCenter(final ImageView view, String url,
                                  @DrawableRes int defaultImage) {
        // 不能崩
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？如果view已经关闭，加载图片没有意义，生命周期都没了
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(defaultImage)
                    .crossFade()
                    .fitCenter()
                    .into(view)
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            if (!view.isShown()) {
                                view.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 防止内存泄漏，牺牲缓存功能，优化些性能的加载方式
     * @param view
     * @param url
     * @param defaultImage
     */
    public static void displayUrlNoCache(final ImageView view, String url,
                                  @DrawableRes int defaultImage) {
        // 不能崩
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？如果view已经关闭，加载图片没有意义，生命周期都没了
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(url)
                    //.skipMemoryCache(true)//禁止使用缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//不缓存图片，除非需求需要反复刷脸
                    .override(180,320)//设置图片宽高
                    .crossFade()
                    .centerCrop()
                    //.thumbnail(0.5f)//使用原图的1/10作为缩略图，先显示缩略图，再显示原图
                    //.placeholder(defaultImage)
                    .into(view)
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            if (!view.isShown()) {
                                view.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayNative(final ImageView view, @DrawableRes int resId) {
        // 不能崩
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                 .load(resId)
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .crossFade()
                 .centerCrop()
                 .into(view)
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            if (!view.isShown()) {
                                view.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void displayCircle(ImageView view, String res) {
        // 不能崩
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                 .load(res)
                 .centerCrop()
                 .placeholder(R.mipmap.ic_launcher)
                 .bitmapTransform(new GlideCircleTransform(context))
                 .crossFade()
                 .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static void displayRound(ImageView view, String res) {
//        // 不能崩
//        if (view == null) {
//            return;
//        }
//        Context context = view.getContext();
//        // View你还活着吗？
//        if (context instanceof Activity) {
//            if (((Activity) context).isFinishing()) {
//                return;
//            }
//        }
//
//        try {
//            Glide.with(context)
//                    .load(res)
//                    .placeholder(R.drawable.threepoint_shooters_1990)
//                    .error(R.drawable.threepoint_shooters_1990)
//                    .bitmapTransform(new GlideRoundTransform(context))
//                    .crossFade()
//                    .into(view);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
