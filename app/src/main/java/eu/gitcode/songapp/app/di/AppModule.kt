package eu.gitcode.songapp.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import eu.gitcode.songapp.app.App

@Module
class AppModule {

    @Provides
    fun provideContext(application: App): Context = application.applicationContext
}