package eu.gitcode.core.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Component
import eu.gitcode.core.domain.controller.FileController
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        CoreModule::class
    ]
)
interface CoreComponent {

    fun context(): Context

    fun retrofit(): Retrofit

    fun moshi(): Moshi

    fun fileController(): FileController
}