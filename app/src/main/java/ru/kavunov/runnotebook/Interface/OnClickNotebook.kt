package ru.kavunov.runnotebook

interface OnClickNotebook {
    fun transitionDetailNotebook()
}

interface OnClickAdapterNotebook {
    fun transitionAdapterDetailNotebook(id: Long)
}