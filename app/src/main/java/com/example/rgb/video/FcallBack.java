package com.example.rgb.video;

import java.io.File;

public interface FcallBack {

        void onSuccess(File file);
        void onError();
        void onFinish();

}
