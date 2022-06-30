package com.adyen.android.assignment.utils

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<in P, R> {
    operator fun invoke(request: P): Flow<R>
}