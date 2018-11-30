package com.hxd.root.app;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import com.hxd.root.utils.DebugUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import com.hxd.root.http.HttpUtils;
import com.thejoyrun.router.Router;

import java.util.Objects;

/*
 * @author Cazaea
 * @time 2017/11/22 22:22
 * @mail wistorm@sina.com
 * @description Inherit MultiDexApplication to implement subcontract processing
 *
 *                        ___====-_  _-====___
 *                  _--^^^#####//      \\#####^^^--_
 *               _-^##########// (    ) \\##########^-_
 *              -############//  |\^^/|  \\############-
 *            _/############//   (@::@)   \\############\_
 *           /#############((     \\//     ))#############\
 *          -###############\\    (oo)    //###############-
 *         -#################\\  / VV \  //#################-
 *        -###################\\/      \//###################-
 *       _#/|##########/\######(   /\   )######/\##########|\#_
 *       |/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *       `  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *          `   `  `      `   / | |  | | \   '      '  '   '
 *                           (  | |  | |  )
 *                          __\ | |  | | /__
 *                         (vvv(VVV)(VVV)vvv)
 *
 *                           HERE BE DRAGONS
 *
 */

public class RootApplication extends MultiDexApplication {

    private static RootApplication sRootApplication;
    public static RootApplication getInstance() {
        return sRootApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        sRootApplication = this;
        // Logger tool initialization
        Logger.addLogAdapter(new AndroidLogAdapter());
        DebugUtil.initDebugMode();
        // Initialize the routing framework
        Router.init(Constants.ROUTER_HEAD);
        Router.setHttpHost(Constants.ROUTER_WEBSITE);
        // Initialize network tools
        HttpUtils.getInstance().init(this);
        // Load memory optimization check tool
        LeakCanary.install(this);
        // Initialize Bugly to force an update
        CrashReport.initCrashReport(getApplicationContext(), "3977b2d86f", DebugUtil.isDebug);
        // Change font size without following the system
        initTextSize();
    }

    /**
     * Make its system change the font size invalid
     */
    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    /**
     * Restart the app and update the server
     */
    public void reStartApp() {
        final Intent intent = this.getPackageManager().getLaunchIntentForPackage(this.getPackageName());
        Objects.requireNonNull(intent).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        System.exit(0);
    }

}
