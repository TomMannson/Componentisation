package com.tommannson.apps.componentisation.arch

open class RxAction() {

    val stackOfCreation = Thread.currentThread().stackTrace;
    var consumed =false;

    fun consume() {
        consumed = true
    }

    fun checkAndConsume(): Boolean {
        val last = consumed;
        consume()
        return last;
    }

    fun getUseFullStack(): List<StackTraceElement> {
        var startStack = false;
        return stackOfCreation.filter {
            startStack = startStack || it.className.contains("RxAction")
            return@filter startStack
        }
    }
}