package eu.gitcode.songs.data.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import eu.gitcode.core.di.scope.FeatureScope
import eu.gitcode.core.domain.controller.FileController
import eu.gitcode.songs.data.controller.SongsControllerImpl
import eu.gitcode.songs.data.network.SongsApi
import eu.gitcode.songs.domain.controller.SongsController
import retrofit2.Retrofit

@Module
class SongsDataModule {

    @FeatureScope
    @Provides
    fun provideSongsApi(
        retrofit: Retrofit
    ): SongsApi = retrofit.create(SongsApi::class.java)

    @FeatureScope
    @Provides
    fun provideSongsController(
        songsApi: SongsApi,
        moshi: Moshi,
        fileController: FileController
    ): SongsController = SongsControllerImpl(songsApi, moshi, fileController)
}