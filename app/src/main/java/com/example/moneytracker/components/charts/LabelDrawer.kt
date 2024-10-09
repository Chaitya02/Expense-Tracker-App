package com.example.moneytracker.components.charts

import android.graphics.Paint
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import com.example.moneytracker.models.Recurrence
import com.github.tehras.charts.bar.renderer.label.LabelDrawer
import com.github.tehras.charts.piechart.utils.toLegacyInt

class LabelDrawer (val recurrence: Recurrence, val lastDay: Int? = -1): LabelDrawer {

    private val paint = Paint().apply {
        this.color = Color.Black.toLegacyInt()
        this.textAlign = Paint.Align.CENTER
        this.textSize = 42f
    }

    private val leftOffset = when(recurrence) {
        Recurrence.Weekly -> 50f
        Recurrence.Monthly -> 13f
        Recurrence.Yearly -> 24f
        else -> 0f
    }

    override fun drawLabel(
        drawScope: DrawScope,
        canvas: Canvas,
        label: String,
        barArea: Rect,
        xAxisArea: Rect
    ) {
        val monthlyCondition = recurrence == Recurrence.Monthly && (
                Integer.parseInt(label) % 5 == 0 || Integer.parseInt(label) == 1 || Integer.parseInt(label) == lastDay)
        if (monthlyCondition || recurrence != Recurrence.Monthly)
            canvas.nativeCanvas.drawText(
                label,
                barArea.left + leftOffset,
                barArea.bottom + 65f,
                paint
            )
    }

}