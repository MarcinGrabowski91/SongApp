package eu.gitcode.songapp.app.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import eu.gitcode.songapp.app.App
import javax.inject.Provider
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: App): Context = application.applicationContext

    @Suppress("UNCHECKED_CAST")
    @Provides
    @Singleton
    fun provideViewModelFactory(
        providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ) = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return requireNotNull(providers[modelClass as Class<out ViewModel>]).get() as T
        }
    }
}