package com.xiong.kotlin.dandan.libs.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
 * Created by xionglh on 2018/7/27
 */
class VersionUtil {

    private val version = Build.VERSION.SDK_INT

    private  fun getSdkVersion(): Int {
        return version
    }

    @TargetApi(8)
    fun sdkVersion8(): Boolean {
        return getSdkVersion() >= 8
    }

    @TargetApi(11)
    fun sdkVersion11(): Boolean {
        return getSdkVersion() >= 11
    }

    @TargetApi(19)
    fun sdkVersion19(): Boolean {
        return getSdkVersion() >= 19
    }

    @TargetApi(17)
    fun sdkVersion17(): Boolean {
        return getSdkVersion() >= 17
    }

    @TargetApi(21)
    fun sdkVersion21(): Boolean {
        return getSdkVersion() >= 21
    }

    @TargetApi(23)
    fun sdkVersion23(): Boolean {
        return getSdkVersion() > 23
    }

    /**
     * 获取渠道
     *
     * @param context context
     * @return
     */
    fun getChannel(context: Context): String? {
        var channel: String? = ""
        try {
            channel = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA).metaData.getString("UMENG_CHANNEL")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return channel;
    }
}