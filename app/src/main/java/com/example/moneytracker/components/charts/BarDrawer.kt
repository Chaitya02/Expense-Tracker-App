package com.example.moneytracker.components.charts

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.moneytracker.models.Recurrence
import com.example.moneytracker.ui.theme.Grey20
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.bar.BarDrawer

class BarDrawer constructor(recurrence: Recurrence): BarDrawer {

    private val barPaint = Paint().apply {
        this.isAntiAlias = true
    }

    private val rightOffset = when(recurrence) {
        Recurrence.Weekly -> 24f
        Recurrence.Monthly -> 6f
        Recurrence.Yearly -> 8f
        else -> 0f
    }

    override fun drawBar(
        drawScope: DrawScope,
        canvas: Canvas,
        barArea: Rect,
        bar: BarChartData.Bar
    ) {
        canvas.drawRoundRect(barArea.left, 0f, barArea.right + rightOffset, barArea.bottom, 16f, 16f, barPaint.apply {
            color = Grey20
        })

        canvas.drawRoundRect(barArea.left, barArea.top, barArea.right + rightOffset, barArea.bottom, 16f, 16f, barPaint.apply {
            color = bar.color
        })
    }
}