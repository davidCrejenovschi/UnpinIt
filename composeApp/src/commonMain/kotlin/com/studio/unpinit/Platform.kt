package com.studio.unpinit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform