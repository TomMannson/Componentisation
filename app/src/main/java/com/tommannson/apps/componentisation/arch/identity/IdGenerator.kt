package com.tommannson.apps.componentisation.arch.identity

object IdGenerator {

    var globalSeed: Int = 1;
    @JvmStatic
    @Synchronized
    fun setSeed(lastNumber: Int) {
        globalSeed = lastNumber
    }

    @JvmStatic
    @Synchronized
    fun getNextNumberId(): ComponentId = NumberComponentId(globalSeed++)
}
