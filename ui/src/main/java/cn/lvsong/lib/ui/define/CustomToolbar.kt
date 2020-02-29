package cn.lvsong.lib.ui.define

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import cn.lvsong.lib.ui.R

/**
 * Desc: 自定义 Toolbar ,格式如下:
 * 图片--文字        文字          图片&图片 | 文字  ,  右侧有2张图片,一个文本,一般只显示文本或者2个图片
 * Author: Jooyer
 * Date: 2018-08-08
 * Time: 17:13
 */

/*

    <cn.lvsong.lib.ui.define.CustomToolbar
            android:id="@+id/toolbar"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            app:ct_left_arrow_drawable="@drawable/ic_left_back_blue"
            app:ct_left_arrow_width="@dimen/width_45"
            app:ct_left_arrow_padding="@dimen/padding_5"
            app:ct_left_text_info="@string/back"
            app:ct_left_text_color="@color/color_2878FF"
            app:ct_left_text_visible="true"
            app:ct_left_text_size="@dimen/text_size_14"
            app:ct_left_text_left_margin="@dimen/padding_32"
            app:ct_center_text_size="@dimen/text_size_16"
            app:ct_center_text_info="商品详情"
            app:ct_center_text_color="@color/color_333333"
            app:ct_bottom_divider_visible="true"
            app:ct_bottom_divider_color="@color/color_DEDEDE"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
    />

 */

class CustomToolbar(context: Context, attr: AttributeSet, defStyleAttr: Int) :
    ConstraintLayout(context, attr, defStyleAttr) {

    /**
     * Material Design风格
     */
    private val ARROW_STYLE_MATERIAL_DESIGN = 1
    /**
     * 微信风格
     */
    private val ARROW_STYLE_WECHAT_DESIGN = 2

    /**
     * 水平排列
     */
    private val ORIENTATION_HORIZONTAL = 1
    /**
     * 垂直排列
     */
    private val ORIENTATION_VERTICAL = 2

    lateinit var parent: View
    /**
     * 最左侧图标,默认显示
     */
    lateinit var iv_left_icon_menu: BackArrowView
    /**
     * 最左侧文本
     */
    lateinit var tv_left_name_menu: TextView
    /**
     * 中间文本
     */
    lateinit var tv_center_title_menu: TextView
    /**
     * 最右侧文本
     */
    lateinit var tv_right_name_menu: TextView
    /**
     * 最右侧图标,默认显示
     */
    lateinit var iv_right_icon_menu: ImageView
    /**
     * 最右侧图标2,默认隐藏
     */
    lateinit var iv_right_icon_menu2: ImageView
    /**
     * 最右侧更多图标按钮,如果它显示,则  tv_right_name_menu,iv_right_icon_menu,iv_right_icon_menu2 均隐藏
     */
    lateinit var mav_right_icon_menu: MoreActionView
    /**
     * 底部分割线
     */
    lateinit var view_bottom_divider_menu: View


    constructor(context: Context, attr: AttributeSet) : this(context, attr, 0)

    init {
        initView()
        parseAttrs(context, attr)
    }

    private fun initView() {
        parent = LayoutInflater.from(context).inflate(R.layout.common_ui_toolbar_custom, this, true)
        iv_left_icon_menu = parent.findViewById(R.id.iv_left_icon_menu)
        tv_left_name_menu = parent.findViewById(R.id.tv_left_name_menu)
        tv_center_title_menu = parent.findViewById(R.id.tv_center_title_menu)
        tv_right_name_menu = parent.findViewById(R.id.tv_right_name_menu)
        iv_right_icon_menu = parent.findViewById(R.id.iv_right_icon_menu)
        iv_right_icon_menu2 = parent.findViewById(R.id.iv_right_icon_menu2)
        mav_right_icon_menu = parent.findViewById(R.id.mav_right_icon_menu)
        view_bottom_divider_menu = parent.findViewById(R.id.view_bottom_divider_menu)

    }

    private fun parseAttrs(context: Context, attr: AttributeSet) {
        val arr = context.obtainStyledAttributes(attr, R.styleable.CustomToolbar)

        val background = arr.getColor(
            R.styleable.CustomToolbar_ct_view_background_color,
            ContextCompat.getColor(context, R.color.color_FFFFFF)
        )

        val leftArrowVisible = arr.getBoolean(R.styleable.CustomToolbar_ct_left_arrow_visible, true)
        val leftArrowWidth =
            arr.getDimension(R.styleable.CustomToolbar_ct_left_arrow_width, dp2px(45F)).toInt()
        val leftArrowHeight =
            arr.getDimension(R.styleable.CustomToolbar_ct_left_arrow_height, dp2px(50F)).toInt()
        val leftArrowPadding =
            arr.getDimension(R.styleable.CustomToolbar_ct_left_arrow_padding, dp2px(1F))
        val leftArrowLeftMargin =
            arr.getDimension(R.styleable.CustomToolbar_ct_left_arrow_left_margin, 0F).toInt()

        val leftArrowColor = arr.getColor(
            R.styleable.CustomToolbar_ct_left_arrow_color,
            ContextCompat.getColor(context, R.color.color_999999)
        )
        val leftArrowStyle =
            arr.getInt(R.styleable.CustomToolbar_ct_left_arrow_style, ARROW_STYLE_MATERIAL_DESIGN)

        val leftTextVisible = arr.getBoolean(R.styleable.CustomToolbar_ct_left_text_visible, false)
        val leftTextInfo = arr.getText(R.styleable.CustomToolbar_ct_left_text_info)
        val leftTextSize = arr.getDimension(R.styleable.CustomToolbar_ct_left_text_size, dp2px(15F))
        val leftTextLeftMargin =
            arr.getDimension(R.styleable.CustomToolbar_ct_left_text_left_margin, dp2px(25F)).toInt()
        val leftTextColor = arr.getColor(
            R.styleable.CustomToolbar_ct_left_text_color,
            ContextCompat.getColor(context, R.color.color_FFFFFF)
        )

        val centerTextInfo = arr.getText(R.styleable.CustomToolbar_ct_center_text_info)
        val centerTextDrawable = arr.getDrawable(R.styleable.CustomToolbar_ct_center_text_drawable)
        val centerTextDrawablePadding =
            arr.getDimension(R.styleable.CustomToolbar_ct_center_text_drawable_padding, dp2px(10F))
                .toInt()
        val centerTextSize =
            arr.getDimension(R.styleable.CustomToolbar_ct_center_text_size, dp2px(18F))
        val centerTextColor = arr.getColor(
            R.styleable.CustomToolbar_ct_center_text_color,
            ContextCompat.getColor(context, R.color.color_FFFFFF)
        )


        val rightImageVisible =
            arr.getBoolean(R.styleable.CustomToolbar_ct_right_image_visible, true)
        val rightImageDrawable = arr.getDrawable(R.styleable.CustomToolbar_ct_right_image_drawable)
        val rightImageWidth =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_image_width, dp2px(20F)).toInt()
        val rightImageHeight =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_image_height, 20F).toInt()
        val rightImagePadding =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_image_padding, dp2px(0F)).toInt()
        val rightImageRightMargin =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_image_right_margin, dp2px(20F))
                .toInt()

        val rightImage2Visible =
            arr.getBoolean(R.styleable.CustomToolbar_ct_right_image2_visible, false)
        val rightImage2Drawable =
            arr.getDrawable(R.styleable.CustomToolbar_ct_right_image2_drawable)
        val rightImage2Width =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_image2_width, dp2px(22F)).toInt()
        val rightImage2Height =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_image2_height, dp2px(22F)).toInt()
        val rightImage2Padding =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_image2_padding, 0F).toInt()
        val rightImage2RightMargin =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_image2_right_margin, dp2px(10F))
                .toInt()

        val mavColor = arr.getColor(
            R.styleable.CustomToolbar_ct_right_mav_color,
            Color.argb(255, 0, 0, 0)
        )
        val mavOrientation = arr.getInt(
            R.styleable.CustomToolbar_ct_right_mav_orientation,
            ORIENTATION_HORIZONTAL
        )
        val mavDotRadius = arr.getDimension(
            R.styleable.CustomToolbar_ct_right_mav_dot_radius,
            MoreActionView.dip2px(context, 2F).toFloat()
        )
        val mavVisible =
            arr.getBoolean(R.styleable.CustomToolbar_ct_right_mav_visible, false)
        val mavWidth =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_mav_width, dp2px(40F)).toInt()
        val mavHeight =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_mav_height, dp2px(40F)).toInt()
        val mavRightMargin =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_mav_right_margin, dp2px(2F))
                .toInt()
        val rightTextVisible =
            arr.getBoolean(R.styleable.CustomToolbar_ct_right_text_visible, false)
        val rightTextInfo = arr.getText(R.styleable.CustomToolbar_ct_right_text_info)
        val rightTextSize =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_text_size, dp2px(15F))
        val rightTextRightMargin =
            arr.getDimension(R.styleable.CustomToolbar_ct_right_text_right_margin, dp2px(12F))
                .toInt()
        val rightTextColor = arr.getColor(
            R.styleable.CustomToolbar_ct_right_text_color,
            ContextCompat.getColor(context, R.color.color_FFFFFF)
        )

        val bottomDividerVisible =
            arr.getBoolean(R.styleable.CustomToolbar_ct_bottom_divider_visible, false)
        val bottomDividerColor = arr.getColor(
            R.styleable.CustomToolbar_ct_bottom_divider_color,
            ContextCompat.getColor(context, R.color.color_EEEEEE)
        )

        parent.setBackgroundColor(background)

        iv_left_icon_menu.visibility = if (leftArrowVisible) View.VISIBLE else View.GONE
        iv_left_icon_menu.setArrowColor(leftArrowColor)
        iv_left_icon_menu.setArrowStyle(leftArrowStyle)
        iv_left_icon_menu.setPadding(leftArrowPadding)

        if (leftArrowVisible) {
            iv_left_icon_menu.setOnClickListener {
                if (context is Activity) {
                    context.finish()
                }
            }
        }
        val leftArrowLp: ConstraintLayout.LayoutParams =
            iv_left_icon_menu.layoutParams as LayoutParams
        leftArrowLp.width = leftArrowWidth
        leftArrowLp.height = leftArrowHeight
        leftArrowLp.leftMargin = leftArrowLeftMargin

        iv_left_icon_menu.layoutParams = leftArrowLp

        tv_left_name_menu.visibility = if (leftTextVisible) View.VISIBLE else View.GONE
        if (!TextUtils.isEmpty(leftTextInfo)) {
            tv_left_name_menu.text = leftTextInfo
            tv_left_name_menu.paint.textSize = leftTextSize
            tv_left_name_menu.setTextColor(leftTextColor)
        }
        if (leftTextVisible) {
            tv_left_name_menu.setOnClickListener {
                if (context is Activity) {
                    context.finish()
                }
            }
        }

        val leftTextLp: ConstraintLayout.LayoutParams =
            tv_left_name_menu.layoutParams as LayoutParams
        if (ARROW_STYLE_MATERIAL_DESIGN == leftArrowStyle && leftTextVisible) { // 由于此时箭头中间横线较长,不合适再有文本
            tv_left_name_menu.visibility = View.GONE
        } else if (leftTextVisible) {
            leftTextLp.leftMargin = leftTextLeftMargin
        }
        tv_left_name_menu.layoutParams = leftTextLp

        if (!TextUtils.isEmpty(centerTextInfo)) {
            tv_center_title_menu.text = centerTextInfo
            tv_center_title_menu.paint.textSize = centerTextSize
            tv_center_title_menu.paint.isFakeBoldText = true //  medium 效果
            tv_center_title_menu.setTextColor(centerTextColor)
        }

//        tv_center_title_menu.setShadowLayer(0.15F, 0.2F, 0.2F, ContextCompat.getColor(context, R.color.color_write))

        if (null != centerTextDrawable) {
            tv_center_title_menu.setCompoundDrawablesWithIntrinsicBounds(
                centerTextDrawable,
                null,
                null,
                null
            )
            tv_center_title_menu.compoundDrawablePadding = centerTextDrawablePadding
        }


        iv_right_icon_menu.visibility = if (rightImageVisible) View.VISIBLE else View.GONE
        if (null != rightImageDrawable) {
            iv_right_icon_menu.setImageDrawable(rightImageDrawable)
        }
        val rightImageLp: ConstraintLayout.LayoutParams =
            iv_right_icon_menu.layoutParams as LayoutParams
        rightImageLp.width = rightImageWidth
        rightImageLp.height = rightImageHeight
        rightImageLp.rightMargin = rightImageRightMargin
        iv_right_icon_menu.setPadding(
            rightImagePadding,
            rightImagePadding,
            rightImagePadding,
            rightImagePadding
        )
        iv_right_icon_menu.layoutParams = rightImageLp


        iv_right_icon_menu2.visibility = if (rightImage2Visible) View.VISIBLE else View.GONE
        if (null != rightImage2Drawable) {
            iv_right_icon_menu2.setImageDrawable(rightImage2Drawable)
        }
        val rightImageLp2: ConstraintLayout.LayoutParams =
            iv_right_icon_menu2.layoutParams as LayoutParams
        rightImageLp2.width = rightImage2Width
        rightImageLp2.height = rightImage2Height

        if (rightImageVisible) {
            rightImageLp2.rightMargin = rightImage2RightMargin
        } else {
            rightImageLp2.goneEndMargin = rightImage2RightMargin
        }
        iv_right_icon_menu2.setPadding(
            rightImage2Padding,
            rightImage2Padding,
            rightImage2Padding,
            rightImage2Padding
        )
        iv_right_icon_menu2.layoutParams = rightImageLp2

        if (mavVisible){
            mav_right_icon_menu.visibility = View.VISIBLE
            tv_right_name_menu.visibility = View.GONE
            iv_right_icon_menu.visibility = View.GONE
            iv_right_icon_menu2.visibility = View.GONE
            val mavLp: ConstraintLayout.LayoutParams =
                mav_right_icon_menu.layoutParams as LayoutParams
            mavLp.width = mavWidth
            mavLp.height = mavHeight
            mavLp.rightMargin = mavRightMargin
            mav_right_icon_menu.layoutParams = mavLp

            mav_right_icon_menu.setColor(mavColor)
            mav_right_icon_menu.setOrientation(mavOrientation)
            mav_right_icon_menu.setDotRadius(mavDotRadius)
        }else{
            mav_right_icon_menu.visibility = View.GONE
        }


        tv_right_name_menu.visibility = if (rightTextVisible) View.VISIBLE else View.GONE
        if (!TextUtils.isEmpty(rightTextInfo)) {
            tv_right_name_menu.text = rightTextInfo
            tv_right_name_menu.paint.textSize = rightTextSize
            tv_right_name_menu.setTextColor(rightTextColor)
        }
        val rightTextLp: ConstraintLayout.LayoutParams =
            tv_right_name_menu.layoutParams as LayoutParams
        rightTextLp.rightMargin = rightTextRightMargin
        tv_right_name_menu.layoutParams = rightTextLp


        view_bottom_divider_menu.visibility = if (bottomDividerVisible) View.VISIBLE else View.GONE
        view_bottom_divider_menu.setBackgroundColor(bottomDividerColor)

        arr.recycle()
    }

    fun setRightImageListener(listener: View.OnClickListener) {
        iv_right_icon_menu.setOnClickListener(listener)
    }

    fun setRightImage2Listener(listener: View.OnClickListener) {
        iv_right_icon_menu2.setOnClickListener(listener)
    }

    fun setRightTextListener(listener: View.OnClickListener) {
        tv_right_name_menu.setOnClickListener(listener)
    }

    fun setRightTextVisible(visable: Int) {
        tv_right_name_menu.visibility = visable
    }

    fun setcenterText(text: String) {
        tv_center_title_menu.text = text
    }

    fun setRightText(text: String) {
        tv_right_name_menu.text = text
    }

    fun setRightImage2(resource: Int) {
        iv_right_icon_menu2.setImageResource(resource)
    }

    fun setleftArrowVisible(visible: Int) {
        iv_left_icon_menu.visibility = visible
    }

    fun setRightImage2Visible(visible: Int) {
        iv_right_icon_menu2.visibility = visible
    }


    private fun dp2px(def: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            def,
            context.resources.displayMetrics
        )
    }

}