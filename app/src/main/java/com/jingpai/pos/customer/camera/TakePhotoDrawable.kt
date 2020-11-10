package com.jingpai.pos.customer.camera

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.min

/**
 * Author: 陈刘磊 1070379530@qq.com <br>
 * Date: 2019/8/30 17:17    <br>
 * Description: 拍照按钮   <br>
 */
class TakePhotoDrawable : Drawable() {
    companion object {
        const val STATUS_TAKE_PHOTO = 0
    }

    //内圆半径
    private var innerRadius = 0.0f
        set(value) {
            outRadius += field - value
            field = value
            invalidateSelf()
        }
    private var outRadius = 0.0f
    private var centerX = 0.0f

    //当前状态，默认为拍照
    var defaultStatus = STATUS_TAKE_PHOTO
        set(value) {
            field = value
        }
    private val paint = Paint()
    private val videoPaint = Paint()

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL

        videoPaint.isAntiAlias = true
        videoPaint.color = Color.parseColor("#00AAFF")
        videoPaint.style = Paint.Style.STROKE
    }


    override fun draw(canvas: Canvas) {
        //绘制大小圆
        paint.color = Color.parseColor("#80ffffff")
        canvas.drawCircle(centerX, centerX, outRadius, paint)
        paint.color = Color.WHITE
        canvas.drawCircle(centerX, centerX, innerRadius, paint)

    }


    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        val width = bounds.right - bounds.left
        val height = bounds.bottom - bounds.top

        centerX = min(width, height).toFloat() / 2


        resetRadius()

    }

    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }


    /**
     * 重置内外圆的半径
     */
    private fun resetRadius() {
        innerRadius = centerX / 2

        outRadius = innerRadius + innerRadius / 2
    }
}