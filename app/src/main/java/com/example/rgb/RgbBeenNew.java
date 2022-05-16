package com.example.rgb;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class RgbBeenNew {
//    List<Integer> r;//照片的RGB
//    List<Integer> g;//照片的RGB
//    List<Integer> b;//照片的RGB
    List<Double> r;//照片的RGB
    List<Double> g;//照片的RGB
    List<Double> b;//照片的RGB
    String colorGet;/// 偏色检测
    String artGet;//清晰度检测
    String lightGet;//亮度检测
    Bitmap mBit;//本地拍的那张照片
    File file;//本地视频

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Bitmap getmBit() {
        return mBit;
    }

    public void setmBit(Bitmap mBit) {
        this.mBit = mBit;
    }

    public List<Double> getR() {
        return r;
    }

    public void setR(List<Double> r) {
        this.r = r;
    }

    public List<Double> getG() {
        return g;
    }

    public void setG(List<Double> g) {
        this.g = g;
    }

    public List<Double> getB() {
        return b;
    }

    public void setB(List<Double> b) {
        this.b = b;
    }

    public String getColorGet() {
        return colorGet;
    }

    public void setColorGet(String colorGet) {
        this.colorGet = colorGet;
    }

    public String getArtGet() {
        return artGet;
    }

    public void setArtGet(String artGet) {
        this.artGet = artGet;
    }

    public String getLightGet() {
        return lightGet;
    }

    public void setLightGet(String lightGet) {
        this.lightGet = lightGet;
    }
}
