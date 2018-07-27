package com.xiong.kotlin.dandan.libs.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.wifi.WifiManager
import android.os.Build
import android.util.DisplayMetrics
import android.view.ViewConfiguration

/**
 * Created by xionglh on 2018/7/27
 */
class SystemInfoUtil {

    fun getAppVersion(context: Context): String? {
        val pagePackageManager = context.packageManager
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = pagePackageManager.getPackageInfo(
                    context.packageName, 0)
            return packageInfo!!.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }


    /**
     * 获取渠道
     *
     * @param context
     * @return
     */
    fun getChannel(context: Context): String ?{
        var channel: String? = ""
        try {
            channel = context.packageManager.getApplicationInfo(context.packageName,
                    PackageManager.GET_META_DATA).metaData.getString("UMENG_CHANNEL")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return channel
    }

    /**
     * 获取系统的版本号
     *
     * @return
     */
    fun getSystemVersion(): String {
        return Build.VERSION.RELEASE
    }


    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    fun hasNavBar(context: Context): Boolean {
        val res = context.resources
        val resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android")
        if (resourceId != 0) {
            var hasNav = res.getBoolean(resourceId)
            // check override flag
            val sNavBarOverride = getNavBarOverride()
            if ("1" == sNavBarOverride) {
                hasNav = false
            } else if ("0" == sNavBarOverride) {
                hasNav = true
            }
            return hasNav
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey()
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    fun getNavBarOverride(): String? {
        var sNavBarOverride: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                val c = Class.forName("android.os.SystemProperties")
                val m = c.getDeclaredMethod("get", String::class.java)
                m.isAccessible = true
                sNavBarOverride = m.invoke(null, "qemu.hw.mainkeys") as String
            } catch (e: Throwable) {
            }

        }
        return sNavBarOverride
    }

    fun isBackground(context: Context): Boolean {

        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses
        for (appProcess in appProcesses) {
            if (appProcess.processName == context.packageName) {
                return if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    true
                } else {

                    false
                }
            }
        }
        return false
    }

    fun getPackageManagerStatus(context: Context, intent: Intent): Boolean {
        return intent.resolveActivity(context.packageManager) != null
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    fun getStatusHeight(context: Context): Int {

        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(`object`).toString())
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusHeight
    }



    fun activityShot(activity: Activity): Bitmap {
        /*获取windows中最顶层的view*/
        val view = activity.window.decorView

        //允许当前窗口保存缓存信息
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()

        //获取状态栏高度
        val rect = Rect()
        view.getWindowVisibleDisplayFrame(rect)
        val statusBarHeight = rect.top

        val windowManager = activity.windowManager

        //获取屏幕宽和高
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        val width = outMetrics.widthPixels
        val height = outMetrics.heightPixels

        //去掉状态栏
        val bitmap = Bitmap.createBitmap(view.drawingCache, 0, statusBarHeight, width,
                height - statusBarHeight)

        //销毁缓存信息
        view.destroyDrawingCache()
        view.isDrawingCacheEnabled = false

        return bitmap
    }

    fun copyWordProcessor(context: Context, word: String) {
        val cmb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cmb.primaryClip = ClipData.newPlainText(word, word)
    }
}