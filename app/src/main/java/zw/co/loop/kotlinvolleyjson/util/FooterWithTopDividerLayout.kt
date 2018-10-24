package zw.co.loop.kotlinvolleyjson.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout
import zw.co.loop.kotlinvolleyjson.R


class FooterWithTopDividerLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {

    private val dividerPaint: Paint

    val dividerHeight: Float
        get() = dividerPaint.strokeWidth

    var dividerColor: Int
        get() = dividerPaint.color
        set(color) {
            dividerPaint.color = color
        }

    init {

        dividerPaint = Paint()
        dividerPaint.strokeCap = Paint.Cap.SQUARE

        initFrom(context, attrs)
        setWillNotDraw(false)
    }

    private fun initFrom(context: Context, attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.FooterWithTopDividerLayout)
        if (a != null) {
            try {
                var dividerColor = a.getColor(R.styleable.FooterWithTopDividerLayout_dividerColor, Color.TRANSPARENT)
                dividerPaint.color = dividerColor

                val dividerHeight =
                    a.getDimensionPixelSize(R.styleable.FooterWithTopDividerLayout_dividerHeight, HEIGHT_NONE)
                setDividerHeight(dividerHeight)
            } finally {
                a.recycle()
            }
        }
    }

    // The divider height is the stroke width.
    fun setDividerHeight(heightPx: Int) {
        dividerPaint.strokeWidth = heightPx.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val top = dividerPaint.strokeWidth / 2
        canvas.drawLine(left.toFloat(), top, right.toFloat(), top, dividerPaint)
    }

    companion object {

        private val HEIGHT_NONE = 0
    }

}
