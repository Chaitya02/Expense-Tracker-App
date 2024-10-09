package com.example.moneytracker.mock

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.moneytracker.models.Category
import com.example.moneytracker.models.Expense
import com.example.moneytracker.models.Recurrence
import com.example.moneytracker.ui.theme.Blue40
import com.example.moneytracker.ui.theme.Green20
import com.example.moneytracker.ui.theme.Mint40
import com.example.moneytracker.ui.theme.Red40
import com.example.moneytracker.ui.theme.Yellow20
import io.github.serpro69.kfaker.Faker
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.round
import kotlin.random.Random

val faker = Faker()

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

@RequiresApi(Build.VERSION_CODES.O)
val mockExpenses: List<Expense> = List(30) { _ ->
    Expense(
        amount = Random.nextDouble(10.0, 999.0).round(1),
        date = LocalDateTime.now().minus(Random.nextLong(300, 345600), ChronoUnit.SECONDS),
        note = faker.animal.name()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
        category = listOf(
            Category("Groceries", Green20),
            Category("Bills", Red40),
            Category("Subscriptions", Yellow20),
            Category("Take out", Blue40),
            Category("Hobbies", Mint40)
        ).random(),
        recurrence = listOf(Recurrence.None, Recurrence.Daily, Recurrence.Monthly, Recurrence.Weekly, Recurrence.Yearly).random()
    )
}
