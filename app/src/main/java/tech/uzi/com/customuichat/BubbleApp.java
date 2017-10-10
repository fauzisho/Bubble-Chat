package tech.uzi.com.customuichat;

import android.app.Application;

import com.qiscus.sdk.Qiscus;

/**
 * Created by uzi on 10/10/17.
 * Email : fauzisholichin@gmail.com
 */

public class BubbleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Qiscus.init(this, "dragongo");
    }
}
