package com.example.githubusers.core.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UseCaseTest {

    private class TestUseCase : UseCase<String, Int>() {
        override suspend fun invoke(input: String): Int {
            return input.length
        }
    }

    private class TestFlowUseCase : FlowUseCase<String, Int>() {
        override fun invoke(input: String): Flow<Int> {
            return flowOf(input.length)
        }
    }

    @Test
    fun testUseCaseInvoke() = runTest {
        val useCase = TestUseCase()
        val result = useCase("hello")
        assertEquals(5, result)
    }

    @Test
    fun testFlowUseCaseInvoke() = runTest {
        val flowUseCase = TestFlowUseCase()
        val result = flowUseCase("hello").toList()
        assertEquals(listOf(5), result)
    }
}
