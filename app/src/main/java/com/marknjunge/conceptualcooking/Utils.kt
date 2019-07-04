package com.marknjunge.conceptualcooking

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.palette.graphics.Palette
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import java.io.IOException
import java.io.InputStream

fun Picasso.loadAsset(filename: String): RequestCreator = this.load("file:///android_asset/$filename")

fun Bitmap.generatePalette(onComplete: (palette: Palette?) -> Unit) {
    Palette.from(this).generate { palette ->
        onComplete(palette)
    }
}

val Palette.Swatch.rgbString: String
    get() = "#${Integer.toHexString(this.rgb)}"

fun getBitmapFromAsset(context: Context, filePath: String): Bitmap? {
    val assetManager = context.assets

    val inputStream: InputStream
    return try {
        inputStream = assetManager.open(filePath)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: IOException) {
        Log.e("ERROR:", e.message)
        null
    }
}