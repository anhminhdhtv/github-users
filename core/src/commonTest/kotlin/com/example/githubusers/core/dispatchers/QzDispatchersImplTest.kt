package com.example.githubusers.core.dispatchers

import kotlinx.coroutines.Dispatchers
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class QzDispatchersImplTest {

    private val dispatchers: QzDispatchers = QzDispatchersImpl()

    @Test
    fun testMainDispatcher() {
        assertEquals(Dispatchers.Main, dispatchers.main)
    }

    @Test
    fun testMainImmediateDispatcher() {
        assertEquals(Dispatchers.Main.immediate, dispatchers.mainImmediate)
    }

    @Test
    fun testIoDispatcher() {
        assertNotNull(dispatchers.io)
    }
}
