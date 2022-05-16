package com.example.rgb;

import android.graphics.Bitmap;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class RgbBeen {
    private int zhaopian;//拍照的照片
    private String rgb;//照片的RGB
    private String difference;/// 照片的色差
    private String good;//照片是否良好
    private String score;//照片评分
    private Bitmap mBit;

    private byte[] datas;
    private FaceRectangleBean face_rectangle;
    public RgbBeen() {

    }

    public Bitmap getBit() {
        return mBit;
    }

    public void setBit(Bitmap bit) {
        mBit = bit;
    }


    public RgbBeen(String rgb, String difference, String good, String score, Bitmap bit) {
        this.zhaopian = zhaopian;
        this.rgb = rgb;
        this.difference = difference;
        this.good = good;
        this.score = score;
        this.mBit = bit;
    }

    public byte[] getDatas() {
        return datas;
    }

    public void setDatas(byte[] datas) {
        this.datas = datas;
    }

    public FaceRectangleBean getFace_rectangle() {
        return face_rectangle;
    }

    public void setFace_rectangle(FaceRectangleBean face_rectangle) {
        this.face_rectangle = face_rectangle;
    }
    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public int getZhaopian() {
        return zhaopian;
    }

    public void setZhaopian(int zhaopian) {
        this.zhaopian = zhaopian;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    public static class FaceRectangleBean {
        /**
         * width : 140
         * top : 89
         * left : 104
         * height : 141
         */

        private int width;
        private int top;
        private int left;
        private int height;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
