package com.app.turtlemint.networks

import com.app.turtlemint.BuildConfig

object APIConstants {

    const val BASE_URL = BuildConfig.BASE_URL
    private const val SUB_BASE_URL = "repos/square/okhttp/"
    const val GET_PEOPLE = SUB_BASE_URL.plus("issues")
}
