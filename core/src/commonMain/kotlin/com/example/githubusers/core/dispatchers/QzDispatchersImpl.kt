package com.example.githubusers.core.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class QzDispatchersImpl : QzDispatchers {
    override val io: CoroutineDispatcher
        get() = ioDispatcher
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val mainImmediate: CoroutineDispatcher
        get() = Dispatchers.Main.immediate
}
