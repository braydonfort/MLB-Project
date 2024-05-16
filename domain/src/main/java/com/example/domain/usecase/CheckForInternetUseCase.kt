package com.example.domain.usecase

import android.content.Context
import kotlinx.coroutines.flow.Flow

/**
 * CheckForInternetUseCase
 * Interface for checking if user has internet access
 * @see[CheckForInternetUseCaseImpl]
 * execute funtion has @param[Context]
 */
interface CheckForInternetUseCase {
    suspend fun execute(context: Context): Flow<Boolean>
}