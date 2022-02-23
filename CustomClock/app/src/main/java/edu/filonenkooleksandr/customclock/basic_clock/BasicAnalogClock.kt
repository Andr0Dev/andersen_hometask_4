package edu.filonenkooleksandr.customclock.basic_clock

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import edu.filonenkooleksandr.customclock.R
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class BasicAnalogClock constructor(
    context: Context?,
    attrs: AttributeSet? = null
) :
    View(context, attrs) {

    init {

    }

    private var padding = 0
    private var fontSize = 0
    private val numeralSpacing = 0
    private var handTruncation = 0
    private var hourHandTruncation = 0
    private var radius = 0
    private lateinit var paint: Paint
    private var isInit = false
    private val numbers = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private val rect = Rect()
    private var centerX = 0
    private var centerY = 0

    private fun initValues() {
        centerX = width / 2
        centerY = height / 2
        padding = numeralSpacing + 50
        fontSize =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                24F,
                resources.displayMetrics
            ).toInt()
        val min = min(height, width)
        radius = min / 2 - padding

        paint = Paint()
        isInit = true
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        if (!isInit) initValues()
        canvas?.drawColor(Color.WHITE)

        drawCircle(canvas)
        drawNumeral(canvas)
        canvas?.let { drawHands(it) }
        postInvalidateDelayed(500)
        invalidate()
    }

    private fun drawHand(canvas: Canvas?, location: Float, isHour: Boolean) {
        val angle = Math.PI * location / 30 - Math.PI / 2
        val handRadius =
            if (isHour) radius - handTruncation - hourHandTruncation else radius - handTruncation
        canvas?.drawLine(
            (centerX).toFloat(),
            (centerY).toFloat(),
            (centerX + cos(angle) * handRadius).toFloat(),
            (centerY + sin(angle) * handRadius).toFloat(),
            paint
        )
    }

    private fun drawHands(canvas: Canvas) {

        val calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        if (hour > 12) hour -= 12
        drawHand(canvas, ((hour + calendar.get(Calendar.MINUTE) / 60) * 5f), true)
        drawHand(canvas, calendar.get(Calendar.MINUTE).toFloat(), false)
        drawHand(canvas, calendar.get(Calendar.SECOND).toFloat(), false)
    }

    private fun drawNumeral(canvas: Canvas?) {
        paint.textSize = fontSize.toFloat()

        for (number in numbers) {
            val tempNumber = number.toString()
            paint.getTextBounds(tempNumber, 0, tempNumber.length, rect)
            val angle = Math.PI / 6 * (number - 3)
            val x = ((centerX + cos(angle) * radius - rect.width() / 2).toFloat())
            val y = ((centerY + sin(angle) * radius + rect.height() / 2).toFloat())
            canvas?.drawText(tempNumber, x, y, paint)
        }
    }

    private fun drawCircle(canvas: Canvas?) {
        paint.reset()
        paint.color = Color.BLACK
        paint.strokeWidth = 5F
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        canvas?.drawCircle(
            (centerX).toFloat(), (centerY).toFloat(),
            (radius + padding - 10).toFloat(), paint
        )
    }
}