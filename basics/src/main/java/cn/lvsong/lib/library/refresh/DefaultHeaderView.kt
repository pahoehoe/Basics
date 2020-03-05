package cn.lvsong.lib.library.refresh

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import cn.lvsong.lib.library.R

/**
 * Desc:
 * Author: Jooyer
 * Date: 2019-08-06
 * Time: 17:34
 */
open class DefaultHeaderView(context: Context) : LinearLayout(context), IHeaderWrapper {

    private var tvHeaderTip: TextView? = null
    private var cvLoading: ChrysanthemumView? = null

    init {
        initView()
    }

    private fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.header_default, this, false)
        cvLoading = view.findViewById<ChrysanthemumView>(R.id.cv_loading)
        tvHeaderTip = view.findViewById<TextView>(R.id.tv_tip)
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER_VERTICAL
        addView(view, params)
    }

    override fun onMoveDistance(distance: Int) {

    }


    override fun onPullDown() {
        tvHeaderTip?.text = "下拉刷新"
    }

    override fun onPullDownAndReleasable() {
        tvHeaderTip?.text = "松手可刷新"
    }


    override fun onRefreshing() {
        cvLoading?.visibility = View.VISIBLE
        tvHeaderTip?.text = "正在刷新"
    }

    override fun onRefreshComplete(isRefreshSuccess: Boolean) {
        cvLoading?.visibility = View.GONE
        tvHeaderTip?.text = "刷新完成"
    }

    override fun onRefreshFailure() {
        cvLoading?.visibility = View.GONE
        tvHeaderTip?.text = "刷新失败"
    }


    override fun getRefreshHeight(): Int {
        return dp2pxRtInt(50)
    }

    /**
     * dp -> px
     * @param dp
     * @return
     */
    fun dp2pxRtInt(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}