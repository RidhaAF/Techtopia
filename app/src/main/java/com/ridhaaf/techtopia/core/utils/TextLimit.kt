package com.ridhaaf.techtopia.core.utils

fun textLimit(text: String, limit: Int): String {
    return if (text.length > limit) {
        text.substring(0, limit) + "..."
    } else {
        text
    }
}