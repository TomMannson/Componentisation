package com.tommannson.apps.componentisation.arch

import android.view.ViewGroup

interface UIParent {

    fun getFindViewGroup(id: Int): ViewGroup

}
