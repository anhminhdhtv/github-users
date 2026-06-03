package com.example.githubusers.core.usecase

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in Input, out Output> {

    abstract operator fun invoke(input: Input): Flow<Output>
}
