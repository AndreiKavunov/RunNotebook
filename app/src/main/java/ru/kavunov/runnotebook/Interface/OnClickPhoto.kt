package ru.kavunov.runnotebook

import android.widget.ImageView

interface OnClickPhoto {
    fun transitionDetailPhoto()
}

interface OnClickAdapterPhoto {
    fun transitionAdapterDetailPhoto(imageView: ImageView, id: Long)
}