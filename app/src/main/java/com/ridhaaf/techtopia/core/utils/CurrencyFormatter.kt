package com.ridhaaf.techtopia.core.utils

import java.text.NumberFormat
import java.util.Locale

fun currencyFormatter(price: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    format.maximumFractionDigits = 0
    return format.format(price)
}