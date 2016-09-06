package com.jdjt.exchange.common;

import android.app.Application;

import com.android.pc.ioc.app.Ioc;
import com.jdjt.exchange.constant.Constant;
import com.jdjt.exchange.util.ExcptionHandler;
import com.jdjt.exchange.util.FileUtils;
import com.jdjt.exchange.util.PermissionsChecker;
import com.squareup.leakcanary.LeakCanary;

public class AppContext extends Application {

    private static AppContext app;

    public AppContext() {
        app = this;
    }

    public static synchronized AppContext getInstance() {
        if (app == null) {
            app = new AppContext();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Ioc.getIoc().init(this);
        // fir崩溃分析


        ExcptionHandler eh = ExcptionHandler.getInstance();
        eh.init(getApplicationContext());

        PermissionsChecker mPermissionsChecker = new PermissionsChecker(this);
        if (!mPermissionsChecker.lacksPermissions(PermissionsChecker.PERMISSIONS)) {

        }
//        LeakCanary.install(this);
//        registerUncaughtExceptionHandler();
    }

    // 注册App异常崩溃处理器
    private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }

}