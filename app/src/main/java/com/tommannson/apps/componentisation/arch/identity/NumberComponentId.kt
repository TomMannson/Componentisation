package com.tommannson.apps.componentisation.arch.identity

import java.util.*


data class NumberComponentId(val number: Int) : ComponentId
data class GUIDComponentId(val uuid: UUID) : ComponentId
