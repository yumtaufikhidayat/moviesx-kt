package com.yumtaufikhidayat.moviesx.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yumtaufikhidayat.moviesx.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(Constant.IMAGE_URL + url)
        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
        .into(this)
}

fun String.convertDate(inputFormat: String, outputFormat: String): String {
    val formatter = SimpleDateFormat(inputFormat, Locale.US)
    var formatParser = Date()
    if (this.isNotEmpty()) {
        formatParser = formatter.parse(this) ?: Date()
    }
    val newOutputFormat = SimpleDateFormat(outputFormat, Locale.US)
    return newOutputFormat.format(formatParser)
}

fun toRating(data: Double): String {
    val tenDouble = 10.0
    return ((data * tenDouble).roundToInt() / tenDouble).toString()
}