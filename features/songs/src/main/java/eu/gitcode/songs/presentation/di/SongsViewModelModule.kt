package eu.gitcode.songs.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import eu.gitcode.songapp.app.di.ViewModelFactory
import eu.gitcode.songapp.app.di.ViewModelKey
import eu.gitcode.songs.presentation.songslist.SongsViewModel

@Module
abstract class SongsViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SongsViewModel::class)
    abstract fun bindSongsListViewModel(viewModel: SongsViewModel): ViewModel
}