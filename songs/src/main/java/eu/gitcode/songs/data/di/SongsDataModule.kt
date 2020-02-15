package eu.gitcode.songs.data.di

import dagger.Module
import dagger.Provides
import eu.gitcode.songs.data.controller.SongsControllerImpl
import eu.gitcode.songs.data.network.SongsApi
import eu.gitcode.songs.domain.controller.SongsController
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class SongsDataModule {

    @Singleton
    @Provides
    fun provideSongsApi(
        retrofit: Retrofit
    ): SongsApi = retrofit.create(SongsApi::class.java)

    @Singleton
    @Provides
    fun provideSongsController(
        songsApi: SongsApi
    ): SongsController = SongsControllerImpl(songsApi)
}