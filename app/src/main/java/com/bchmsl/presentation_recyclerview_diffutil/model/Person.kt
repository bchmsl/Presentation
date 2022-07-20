package com.bchmsl.presentation_recyclerview_diffutil.model

import com.bchmsl.presentation_recyclerview_diffutil.data.lastId

data class Person(
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    var email: String,
    val photo: String? = null,
) {
    var id: Int = lastId

    init {
        lastId++
    }
}
