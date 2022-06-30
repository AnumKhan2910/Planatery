package com.adyen.android.assignment.di

import com.adyen.android.assignment.utils.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ManagersModule {

    @Binds
    fun bindStringResourceManager(defaultStringResourceManager: DefaultStringResourceManager)
    : StringResourceManager

    @Binds
    fun bindToastManager(defaultToastManager: DefaultToastManager): ToastManager

}
