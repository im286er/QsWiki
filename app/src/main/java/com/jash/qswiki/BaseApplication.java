package com.jash.qswiki;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by jash
 * Date: 15-12-30
 * Time: 下午2:27
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        MyDatabase.setDatabase(new MyDatabase(this));
    }
}
