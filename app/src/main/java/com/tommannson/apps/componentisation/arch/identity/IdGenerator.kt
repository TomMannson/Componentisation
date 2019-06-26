package com.tommannson.apps.componentisation.arch.identity

class IdGenerator {

    var seed: Int = 0;

    companion object {
        @Synchronized
        fun get() = IdGenerator()
    }

    fun getNextNumberId(): ComponentId = NumberComponentId(seed++)

    fun reset(){
        seed = 0;
    }
}
