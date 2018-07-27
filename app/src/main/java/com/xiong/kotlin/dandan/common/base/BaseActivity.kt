package com.xiong.kotlin.dandan.common.base

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.ViewGroup
import com.xiong.kotlin.dandan.common.dialog.CustomProgressDialog
import com.xiong.kotlin.dandan.libs.utils.StatusBarUtil

/**
 * Created by xionglh on 2018/7/26
 */
abstract class BaseActivity : FragmentActivity() {

    lateinit var customProgressDialog: CustomProgressDialog;
    var statusBarUtil: StatusBarUtil = StatusBarUtil();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customProgressDialog = CustomProgressDialog(this, false, false);
    }

    override fun setContentView(layoutResID: Int) {
        setContentView(getLayoutInflater().inflate(layoutResID,null), ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        setStatusBar()
        initViews()
    }

    abstract fun initViews();

    fun setStatusBar() {
        statusBarUtil.setTransparentForImageView(this, null)

    }


}