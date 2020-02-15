package eu.gitcode.core.di

import android.content.Context
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class
    ]
)
interface CoreComponent {

    fun context(): Context

    fun retrofit(): Retrofit
}