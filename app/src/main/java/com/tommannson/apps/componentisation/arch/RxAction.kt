package com.tommannson.apps.componentisation.arch

import java.util.*

open class RxAction() {

    val stackOfCreation = Thread.currentThread().stackTrace;
    val uid = UUID.randomUUID()

    fun getUseFullStack(): List<StackTraceElement> {
        var startStack = false;
        return stackOfCreation.filter {
            startStack = startStack || it.className.contains("RxAction")
            return@filter startStack
        }
    }
}