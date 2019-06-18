package com.jmoraes.componentizationsample.model.ws

/**
 * Created by kmangutov on 3/25/15.
 */
data class Post(val userId: Int = 0,
                val id: Int = 0,
                val title: String? = null,
                val body: String? = null
)
