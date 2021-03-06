package cn.lvsong.lib.library.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * px与dp之间的转化
 * Created by Jooyer on 2016/4/18
 */
object DensityUtil {

    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    /**
     * 获取屏幕尺寸
     */
    fun getWindowSize(context: Context): DisplayMetrics {
        return context.resources.displayMetrics
    }

    fun dp2pxRtInt(value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    fun dp2pxRtFloat(value: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }

    fun dp2pxRtInt(value: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    fun dp2pxRtFloat(value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }


    fun sp2pxRtFloat(value: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            value.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }

    fun sp2pxRtFloat(value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            value.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels

    /**
     * 获取NavigationBar的高度
     */
    fun getNavigationBarHeight(activity: Activity): Int {
        val resources = activity.resources
        val resourceId = resources.getIdentifier("navigation_bar_height",
            "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

}