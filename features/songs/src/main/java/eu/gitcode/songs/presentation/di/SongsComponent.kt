package eu.gitcode.songs.presentation.di

import dagger.Component
import eu.gitcode.core.di.CoreComponent
import eu.gitcode.core.di.scope.FeatureScope
import eu.gitcode.songs.data.di.SongsModule
import eu.gitcode.songs.presentation.songslist.SongsFragment

@FeatureScope
@Component(
    modules = [
        SongsModule::class,
        SongsViewModelModule::class
    ], dependencies = [CoreComponent::class]
)

interface SongsComponent {

    fun inject(fragment: SongsFragment)
}