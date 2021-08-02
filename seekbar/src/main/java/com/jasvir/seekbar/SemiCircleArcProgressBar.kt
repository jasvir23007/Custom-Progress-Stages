package com.jasvir.seekbar

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import java.util.*

class SemiCircleArcProgressBar : View {
    private var padding = 25
    private var progressPlaceHolderColor = 0
    private var progressBarColor = 0
    private var progressPlaceHolderWidth = 0
    private var progressBarWidth = 0
    private var percent = 0
    private val mPath = Path()
    internal var top = 0
    internal var left = 0
    internal var right = 0
    internal var bottom = 0
    private var values = ArrayList<Int>()

    //Constructors
    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setAttrs(context, attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        setAttrs(context, attrs)
    }

    override fun onDraw(canvas: Canvas) {
        padding =
            if (progressBarWidth > progressPlaceHolderWidth) progressBarWidth + 25 else progressPlaceHolderWidth + 25
        top = padding
        left = padding
        right = measuredWidth
        bottom = measuredHeight
        val progressAmount = percent * 1.8.toFloat()
        mPath.addArc(progressBarRectF, 135f, 270f)
        val pm = PathMeasure(mPath, false)
        val xyCoordinate = FloatArray(2)
        val pathLength = pm.length
        val arcPoints = arrayOfNulls<PointF>(151)
        for (i in 0..150) {
            pm.getPosTan(pathLength * i / 150, xyCoordinate, null)
            arcPoints[i] = PointF(xyCoordinate[0], xyCoordinate[1])
        }
        canvas.drawPath(mPath, getPaint(progressBarColor, progressBarWidth))
        canvas.drawArc(
            progressBarRectF,
            135f,
            270f,
            false,
            getPaint(progressPlaceHolderColor, progressPlaceHolderWidth)
        ) //arg2: For the starting point, the starting point is 0 degrees from the positive direction of the x coordinate system. How many angles are arg3 selected to rotate clockwise?
        canvas.drawArc(
            progressBarRectF,
            135f,
            progressAmount,
            false,
            getPaint(progressBarColor, progressBarWidth)
        ) //arg2: For the starting point, the starting point is 0 degrees from the positive direction of the x coordinate system. How many angles are arg3 selected to rotate clockwise?
        for (i in values.indices) {
            val progress = values[i].toDouble() / values[values.size - 1] * 150
            val prog = progress.toInt()
            if (prog <= 150) {
                drawTextCircle(
                    canvas,
                    arcPoints[prog]!!.x.toInt(),
                    arcPoints[prog]!!.y.toInt(),
                    progressPlaceHolderColor,
                    values[i].toString()
                )
            }
        }
    }

    //Private Methods
    private fun setAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.SemiCircleArcProgressBar, 0, 0)
        try {
            progressPlaceHolderColor = typedArray.getColor(
                R.styleable.SemiCircleArcProgressBar_progressPlaceHolderColor,
                Color.GRAY
            )
            progressBarColor = typedArray.getColor(
                R.styleable.SemiCircleArcProgressBar_progressBarColor,
                Color.WHITE
            )
            progressPlaceHolderWidth = typedArray.getDimension(
                R.styleable.SemiCircleArcProgressBar_progressPlaceHolderWidth,
                25f
            )
                .toInt()
            progressBarWidth =
                typedArray.getDimension(R.styleable.SemiCircleArcProgressBar_progressBarWidth, 10f)
                    .toInt()
            percent = typedArray.getInt(R.styleable.SemiCircleArcProgressBar_percent, 76)
        } finally {
            typedArray.recycle()
        }
    }

    fun drawTextCircle(canvas: Canvas, x: Int, y: Int, color: Int, text: String?) {
        val paint = Paint()
        paint.color = color
        val textPaint = TextPaint()
        textPaint.textSize = resources.getDimension(R.dimen.text_size_normal)
        textPaint.color = Color.WHITE
        textPaint.textAlign = Paint.Align.CENTER
        val textHeight = textPaint.descent() - textPaint.ascent()
        val textOffset = textHeight / 2 - textPaint.descent()
        val radius = (Math.min(width, height) / 2).toFloat()
        val bounds = RectF(
            (x - padding).toFloat(),
            (y - padding).toFloat(),
            resources.getDimension(R.dimen._25dp) + x,
            resources.getDimension(R.dimen._25dp) + y
        )
        bounds.union(x.toFloat(), y.toFloat())
        canvas.drawOval(bounds, paint)
        canvas.drawText(text!!, bounds.centerX(), bounds.centerY() + textOffset, textPaint)
    }

    fun setData(loyalityData: ArrayList<Int>) {
        this.values = loyalityData
        invalidate()
    }

    //canvas.drawPaint(paint);
    // paint.se
    private val paintForTxt: Paint
        private get() {
            val paint = Paint()
            paint.color = Color.WHITE
            paint.style = Paint.Style.FILL
            //canvas.drawPaint(paint);
            // paint.se
            paint.color = Color.WHITE
            paint.textSize = resources.getDimension(R.dimen.text_size_normal)
            paint.textAlign = Paint.Align.CENTER
            return paint
        }

    private fun setupPaint(): Paint {
        val drawPaint = Paint()
        drawPaint.color = progressPlaceHolderColor
        drawPaint.isAntiAlias = true
        drawPaint.strokeWidth = 5f
        drawPaint.style = Paint.Style.FILL_AND_STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND
        return drawPaint
    }

    private fun getPaint(color: Int, strokeWidth: Int): Paint {
        val paint = Paint()
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth.toFloat()
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND
        return paint
    }

    private val progressBarRectF: RectF
        private get() = RectF(
            left.toFloat(),
            top.toFloat(),
            (right - padding).toFloat(),
            (bottom - padding).toFloat()
        )

    //Setters
    fun setProgressPlaceHolderColor(color: Int) {
        progressPlaceHolderColor = color
        postInvalidate()
    }

    fun setProgressBarColor(color: Int) {
        progressBarColor = color
        postInvalidate()
    }

    fun setProgressPlaceHolderWidth(width: Int) {
        progressPlaceHolderWidth = width
        postInvalidate()
    }

    fun setProgressBarWidth(width: Int) {
        progressBarWidth = width
        postInvalidate()
    }

    fun setPercent(percent: Int) {
        this.percent = Math.min(percent, 150)
        postInvalidate()
    }

    //Custom Setter
    fun setPercentWithAnimation(percent: Int) {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            var step = 0
            override fun run() {
                if (step <= percent) setPercent(step++)
            }
        }, 0, 12)
    }
}