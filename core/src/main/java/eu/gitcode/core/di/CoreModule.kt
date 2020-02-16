package eu.gitcode.core.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import eu.gitcode.core.adapter.DateAdapter
import eu.gitcode.core.data.controller.FileControllerImpl
import eu.gitcode.core.domain.controller.FileController
import javax.inject.Singleton


@Module
class CoreModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(DateAdapter())
            .build()
    }

    @Singleton
    @Provides
    fun provideFileController(context: Context): FileController {
        return FileControllerImpl(context)
    }
}