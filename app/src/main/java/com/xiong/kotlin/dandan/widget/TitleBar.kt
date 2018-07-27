package com.xiong.kotlin.dandan.widget

import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.xiong.kotlin.dandan.R
import com.xiong.kotlin.dandan.common.base.BaseActivity
import com.xiong.kotlin.dandan.libs.utils.SystemInfoUtil
import com.xiong.kotlin.dandan.libs.utils.VersionUtil

/**
 * Created by xionglh on 2018/7/27
 */
class TitleBar(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    lateinit var mRlViewTitile: RelativeLayout
    lateinit var mTextViewTitle: TextView
    lateinit var mTextViewLeft: TextView
    lateinit var mTextViewRight: TextView
    var mIshowAddBar: Boolean = false
    var mViewbg: Int
    var mTitleBg: Int

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayoutTitlesBar);
        mIshowAddBar = typedArray.getBoolean(R.styleable.LinearLayoutTitlesBar_showStatusBar, true)
        mViewbg = typedArray.getResourceId(R.styleable.LinearLayoutTitlesBar_showStatusBarViewColor, R.drawable.title_bar_bg)
        mTitleBg = typedArray.getResourceId(R.styleable.LinearLayoutTitlesBar_titleBarBg, R.drawable.title_bar_bg)
        typedArray.recycle();
        initView()
    }

    fun initView() {
        id = R.id.titile_id;
        val viewBar: View = View(context);
        val versionUtil: VersionUtil = VersionUtil();
        val systemInfoUtil: SystemInfoUtil = SystemInfoUtil();
        if (versionUtil.sdkVersion19() && mIshowAddBar) {
            val statusHeght = systemInfoUtil.getStatusHeight(context)
            val layoutParams: LayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, statusHeght)
            viewBar.layoutParams = layoutParams;
            viewBar.setBackgroundResource(mTitleBg)
            addView(viewBar)
        }
        val view = LayoutInflater.from(context).inflate(R.layout.layout_titlebar, null)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, context.resources.getDimensionPixelSize(R.dimen.title_common_height))
        view.layoutParams = layoutParams
        addView(view)
        mRlViewTitile = findViewById<View>(R.id.title_bar_layout) as RelativeLayout
        mTextViewTitle = findViewById<View>(R.id.title_bar_center_text_title) as TextView
        mTextViewLeft = findViewById(R.id.title_bar_left_button_back)
        mTextViewRight = findViewById(R.id.title_bar_right_button);
        mRlViewTitile.setBackgroundResource(mTitleBg)

    }

    fun setTitle(title: String) {
        mTextViewTitle.text = title
    }

    fun setTitle(@StringRes titleResId: Int) {
        setTitle(resources.getString(titleResId))
    }

    fun hideLeftButton() {
        mTextViewLeft.visibility = View.GONE
    }


    fun hideRightButton() {
        mTextViewRight.visibility = View.GONE
    }


    fun showLeftButton() {
        mTextViewLeft.visibility = View.VISIBLE
        mTextViewLeft.setOnClickListener { t -> (context as BaseActivity).finish() }
    }


    fun showLeftButtonWithTextListener(text: String, listener: View.OnClickListener) {
        mTextViewLeft.text = text
        mTextViewLeft.setOnClickListener(listener)
        mTextViewLeft.visibility = View.VISIBLE
        mTextViewLeft.setCompoundDrawables(null, null, null, null)

    }

    fun showLeftButtonWithListener(listener: View.OnClickListener) {
        mTextViewLeft.visibility = View.VISIBLE
        mTextViewLeft.setOnClickListener(listener)
    }

    fun showRightButtonWithImageAndListener(@DrawableRes imageId: Int, listener: View.OnClickListener) {
        val nav_up = resources.getDrawable(imageId)
        nav_up.setBounds(0, 0, nav_up.minimumWidth, nav_up.minimumHeight)
        mTextViewRight.setCompoundDrawables(null, null, nav_up, null)
        mTextViewRight.setOnClickListener(listener)
        mTextViewRight.visibility = View.VISIBLE

    }


    fun showRightButtonWithTextAndListener(text: String, listener: View.OnClickListener) {
        mTextViewRight.text = text
        mTextViewRight.setOnClickListener(listener)
        mTextViewRight.visibility = View.VISIBLE

    }

    fun showRightButtonWithTextAndListener(@StringRes textId: Int, listener: View.OnClickListener) {
        showRightButtonWithTextAndListener(context.getString(textId), listener)
    }
}