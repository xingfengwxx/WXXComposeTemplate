package com.wangxingxing.wxxcomposetemplate.di

import com.wangxingxing.wxxcomposetemplate.base.coroutines.DefaultDispatcherProvider
import com.wangxingxing.wxxcomposetemplate.base.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}

