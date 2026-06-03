package com.example.githubusers.core.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface QzDispatchers {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val mainImmediate: CoroutineDispatcher
}
