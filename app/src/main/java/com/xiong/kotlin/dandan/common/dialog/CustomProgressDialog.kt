package com.xiong.kotlin.dandan.common.dialog

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import com.xiong.kotlin.dandan.R

/**
 * Created by xionglh on 2018/7/26
 */

class CustomProgressDialog(context: Context, canceledOnTouchOutside: Boolean, cancelable: Boolean) : ProgressDialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_common_progress_dialog)
    }

    init {
        setCanceledOnTouchOutside(canceledOnTouchOutside)
        setCancelable(cancelable)
    }

}