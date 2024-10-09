package com.example.moneytracker.components.charts

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneytracker.models.Expense
import com.example.moneytracker.models.Recurrence
import com.example.moneytracker.models.groupedByDayOfWeek
import com.example.moneytracker.ui.theme.Blue40
import com.example.moneytracker.utils.simplifyNumber
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.BarChartData.Bar
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import java.time.DayOfWeek

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyChart(expenses: List<Expense>) {
    val groupedExpenses = expenses.groupedByDayOfWeek()

    BarChart(
        barChartData = BarChartData(bars = listOf(
            Bar(
                label = DayOfWeek.MONDAY.name.substring(0, 1),
                value = groupedExpenses[DayOfWeek.MONDAY.name]?.total?.toFloat() ?: 0f,
                color = Blue40
            ),
            Bar(
                label = DayOfWeek.TUESDAY.name.substring(0, 1),
                value = groupedExpenses[DayOfWeek.TUESDAY.name]?.total?.toFloat() ?: 0f,
                color = Blue40
            ),
            Bar(
                label = DayOfWeek.WEDNESDAY.name.substring(0, 1),
                value = groupedExpenses[DayOfWeek.WEDNESDAY.name]?.total?.toFloat() ?: 0f,
                color = Blue40
            ),
            Bar(
                label = DayOfWeek.THURSDAY.name.substring(0, 1),
                value = groupedExpenses[DayOfWeek.THURSDAY.name]?.total?.toFloat() ?: 0f,
                color = Blue40
            ),
            Bar(
                label = DayOfWeek.FRIDAY.name.substring(0, 1),
                value = groupedExpenses[DayOfWeek.FRIDAY.name]?.total?.toFloat() ?: 0f,
                color = Blue40
            ),
            Bar(
                label = DayOfWeek.SATURDAY.name.substring(0, 1),
                value = groupedExpenses[DayOfWeek.SATURDAY.name]?.total?.toFloat() ?: 0f,
                color = Blue40
            ),
            Bar(
                label = DayOfWeek.SUNDAY.name.substring(0, 1),
                value = groupedExpenses[DayOfWeek.SUNDAY.name]?.total?.toFloat() ?: 0f,
                color = Blue40
            )
        )),
        labelDrawer = LabelDrawer(recurrence = Recurrence.Weekly),
        yAxisDrawer = SimpleYAxisDrawer(
            labelTextColor = Color.Black,
            axisLineColor = Color.White,
            labelValueFormatter = ::simplifyNumber,
            labelRatio = 7,
            labelTextSize = 14.sp
        ),
        xAxisDrawer = SimpleXAxisDrawer(
            axisLineColor = Color.White
        ),
        barDrawer = BarDrawer(recurrence = Recurrence.Weekly),
        modifier = Modifier.padding(bottom = 20.dp).fillMaxSize()
    )
}
