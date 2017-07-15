package net.bwei.shan.download.app;

import android.app.Application;

import org.xutils.x;

/**
 * Created by shanlianting on 2017/7/12.
 */

public class DownloadApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
