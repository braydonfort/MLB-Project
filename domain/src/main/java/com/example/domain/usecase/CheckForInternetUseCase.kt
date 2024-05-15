package com.example.domain.usecase

import android.content.Context
import kotlinx.coroutines.flow.Flow


interface CheckForInternetUseCase {
    suspend fun execute(context: Context): Flow<Boolean>
}