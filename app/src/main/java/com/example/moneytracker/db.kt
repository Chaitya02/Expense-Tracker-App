package com.example.moneytracker

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.moneytracker.models.Category
import com.example.moneytracker.models.Expense
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

@RequiresApi(Build.VERSION_CODES.O)
val config = RealmConfiguration.create(schema = setOf(Expense::class, Category::class))
@RequiresApi(Build.VERSION_CODES.O)
val db: Realm = Realm.open(config)
