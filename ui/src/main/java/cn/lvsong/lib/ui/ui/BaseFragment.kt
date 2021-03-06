package cn.lvsong.lib.ui.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import cn.lvsong.lib.library.utils.OnLazyClickListener
import cn.lvsong.lib.ui.R
import cn.lvsong.lib.ui.state.OnRetryListener
import cn.lvsong.lib.ui.state.StatusManager


/** 参考: https://blog.csdn.net/qq_36486247/article/details/102531304
 *
 *  https://juejin.im/post/5d63cdf7f265da03ed195f68   --> 提供一种新的懒加载方法,其实就是提前加载View,可以和本例结合
 *
 * Desc: Fragment,带懒加载功能
 * Author: Jooyer
 * Date: 2018-07-30
 * Time: 11:16
 */
abstract class BaseFragment  : Fragment(),
    OnLazyClickListener, OnRetryListener {

    open lateinit var mActivity: FragmentActivity
    /**
     * 判断是不是第一次resume
     */
    private var isFirstResume = true

    /**
     * 根布局
     */
    private var mRoot: View? = null
    /**
     * 持久化保存数据
     */
    private var mStateSave: Bundle? = null
    /**
     * 请求网络异常等界面管理
     */
    var mStatusManager: StatusManager? = null
    /**
     * 默认动画持续时间
     */
    @Deprecated("遗弃")
    private var mAnimationDuration = 2000

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            mActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 解决Android jetpack导航组件Navigation返回Fragment重走onCreateView方法刷新视图的问题 步骤1
        return if (null == mRoot) { // 缓存已经创建的视图
            mRoot = initStatusManager(inflater, container, savedInstanceState)
            mRoot
        } else {
            mRoot
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 解决Android jetpack导航组件Navigation返回Fragment重走onCreateView方法刷新视图的问题 步骤2
        if (isFirstResume) { // 如果有做相关处理则不再进行
            setLogic()
            bindEvent()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        restoreStateFromArguments()
        if (null != savedInstanceState) {
            // 可以做一些初始化工作
            val ft = fragmentManager!!.beginTransaction()
            if (isHidden) {
                ft.hide(this)
            } else {
                ft.show(this)
            }
            ft.commit()
        }
    }


    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            isFirstResume = false
            // 懒加载,处理数据
            onFirstUserVisible()
        } else {
            onUserVisible()
        }

    }


    /**
     * 动画进入时
     *
     * @param duration --> 动画持续时间
     */
    @Deprecated("遗弃")
    private fun fadeInView(view: View, duration: Int) {
        view.alpha = 0f
        view.visibility = View.VISIBLE
        view.animate()
            .alpha(1f)
            .setDuration(duration.toLong())
            .setListener(null)
    }

    /**
     * 动画退出时
     *
     * @param duration --> 动画持续时间
     */
    @Deprecated("遗弃")
    private fun fadeOutView(view: View, duration: Int) {
        view.alpha = 1f
        view.visibility = View.VISIBLE
        view.animate()
            .alpha(0f)
            .setDuration(duration.toLong())
            .withEndAction { view.visibility = View.GONE }
    }

    @Deprecated("遗弃")
    fun setAnimationDuration(animationDuration: Int) {
        mAnimationDuration = animationDuration
    }

    /**
     * 第一次对用户可见的时候调用，在这里懒加载数据
     */
    open fun onFirstUserVisible() {

    }

    /**
     * 第二次包括第二次对用户可见的时候调用
     */
    open fun onUserVisible() {

    }

    abstract fun getLayoutId(): Int

    // 在 Kotlin 中这个方法就没有必要重写了
   open fun initializedViews(savedInstanceState: Bundle?, contentView: View) {

    }

    /**
     * 实现过程
     */
    abstract fun setLogic()

    /**
     * 绑定监听
     */
    open fun bindEvent() {

    }

    // 保存临时数据
    private fun saveState(): Bundle {
        val bundle = Bundle()
        onSaveState(bundle)
        return bundle
    }

    // 重写保存数据
    open fun onSaveState(bundle: Bundle) {

    }

    // 重写取出数据
    open fun onRestoreState(stateSave: Bundle?) {

    }


    // 保存 Bundle
    private fun saveStateToArguments() {
        if (null != view)
            mStateSave = saveState()
        val b = arguments
        if (null != mStateSave && null != b) {
            b.putBundle("SavedLocalVar", mStateSave)
        }
    }

    // 恢复 Bundle
    private fun restoreStateFromArguments(): Boolean {
        val b = arguments
        if (null != b) {
            mStateSave = b.getBundle("SavedLocalVar")
            onRestoreState(mStateSave)
            return null != mStateSave
        }
        return false
    }

    /**
     * 此函数开始数据加载的操作，且仅调用一次
     * 主要是加载动画,初始化展示数据的布局
     */
    private fun initStatusManager(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (0 != getLayoutId()) {
            val contentView = inflater.inflate(getLayoutId(), container, false)
            initializedViews(savedInstanceState, contentView)
            return if (useStatusManager()) {
                initialized(contentView)
            } else {
                contentView.visibility = View.VISIBLE
                contentView
            }
        }
        throw IllegalStateException("getLayoutId() 必须调用,且返回正常的布局ID")
    }

    private fun initialized(contentView: View): View {
        mStatusManager = StatusManager.newBuilder(contentView.context)
            .contentView(contentView)
            .loadingView(getLoadingViewLayoutId())
            .emptyDataView(getEmptyDataViewLayoutId())
            .netWorkErrorView(getNetWorkErrorViewLayoutId())
            .errorView(getErrorViewLayoutId())
            .retryViewId(R.id.view_retry_load_data)
            .setLoadingViewBackgroundColor(if (-1 == setLoadingViewBackgroundColor()) StatusConfig.INSTANCE.getLoadingViewBackgroundColor() else setLoadingViewBackgroundColor())
            .onRetryListener(this)
            .build()
        mStatusManager?.setTransY(if (-1 == getTransY()) StatusConfig.INSTANCE.getTranslateY() else getTransY())
        mStatusManager?.showLoading()
        return mStatusManager?.getRootLayout()!!
    }

    /**
     *  当 Activity / Fragment 存在 Toolbar 时,需将StatusManager往下移动 Toolbar高度
     *  可以使用全局配置
     *  {@link cn.lvsong.lib.ui.mvp.StatusConfig }
     *  如果全局不合适,可以重写下面方法
     *  PS: 注意,返回的是 px
     */
    open fun getTransY(): Int {
        return -1
    }

    /**
     * 设置LoadingView背景色
     */
    open fun setLoadingViewBackgroundColor(): Int {
        return -1
    }

    // 此方法有时可能不会回调,在 onDestroyView() 中再调用一次
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveStateToArguments()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveStateToArguments()
    }

    /**
     *  是否使用视图布局管理器
     */
    open fun useStatusManager(): Boolean {
        return false
    }

    /**
     * 重复点击
     */
    override fun onClick(view: View) {

    }

    /**
     * 点击视图中重试按钮
     */
    override fun onRetry() {

    }

    /**
     * 返回加载中布局ID
     */
    open fun getLoadingViewLayoutId() = R.layout.common_ui_loading_page

    /**
     * 返回空视图布局ID
     */
    open fun getEmptyDataViewLayoutId() = R.layout.common_ui_empty_page

    /**
     * 返回网路异常布局ID
     */
    open fun getNetWorkErrorViewLayoutId() = R.layout.common_ui_net_error_page

    /**
     * 返回错误/其他异常布局ID
     */
    open fun getErrorViewLayoutId() = R.layout.common_ui_error_page

    override fun onTriggerClick(view: View) {

    }

}
