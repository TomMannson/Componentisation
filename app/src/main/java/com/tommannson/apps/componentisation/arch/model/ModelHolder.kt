package com.tommannson.apps.componentisation.arch.model

import androidx.lifecycle.ViewModel
import com.tommannson.apps.componentisation.model.pipe.BaseResolver

abstract class ModelHolder : ViewModel() {

    var initialisationNeed = true
    lateinit var listOfModels: List<BaseResolver<*, *>>

    fun init() {
        if (initialisationNeed) {
            initialisationNeed = false
            listOfModels = getList()
            listOfModels.forEach {
                it.start()
            }
        }
    }

    abstract fun getList(): List<BaseResolver<*, *>>;


    override fun onCleared() {
        listOfModels.forEach {
            it.start()
        }
        super.onCleared()
    }


}



