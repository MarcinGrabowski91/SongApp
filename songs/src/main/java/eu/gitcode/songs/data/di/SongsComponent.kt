package eu.gitcode.songs.data.di

import dagger.Component
import eu.gitcode.core.di.CoreComponent
import eu.gitcode.core.di.scope.FeatureScope
import eu.gitcode.songs.domain.controller.SongsController

@Component(
    modules = [
        SongsDataModule::class
    ], dependencies = [CoreComponent::class]
)
@FeatureScope
interface SongsComponent {
    fun getSongsController(): SongsController
}