package eu.gitcode.songs.data.di

import dagger.Component
import eu.gitcode.songs.domain.controller.SongsController
import javax.inject.Singleton

@Component(
    modules = [
        SongsDataModule::class
    ]
)
@Singleton
interface SongsComponent {
    fun getSongsController(): SongsController
}