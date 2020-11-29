package com.advanced.settings;

import android.app.Application;
import android.content.pm.PackageManager;

public class MyApplication extends Application {
    public static final String FACEBOOK_PACKAGE_NAME = "com.facebook.katana";
    public static final String MESSENGER_PACKAGE_NAME = "com.facebook.orca";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public boolean getPackageEnabledStatus(String packageName) {
        PackageManager pm = getApplicationContext().getPackageManager();
        return pm.getApplicationEnabledSetting(packageName) < 2;
    }

    public void enablePackage(String packageName) {
        try {
            Process process;
            process = Runtime.getRuntime()
                    .exec(new String[]{ "su", "-c", "pm enable " + packageName});
            process.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void disablePackage(String packageName) {
        try {
            Process process;
            process = Runtime.getRuntime()
                    .exec(new String[]{ "su", "-c", "pm disable-user --user 0 " + packageName});
            process.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
