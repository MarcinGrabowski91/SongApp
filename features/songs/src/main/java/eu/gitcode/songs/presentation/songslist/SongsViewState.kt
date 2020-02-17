package eu.gitcode.songs.presentation.songslist

sealed class SongsViewState {

    object Empty : SongsViewState()

    object Listed : SongsViewState()

    object Loading : SongsViewState()

    object Error : SongsViewState()

    fun isEmpty() = this is Empty

    fun isListed() = this is Listed

    fun isLoading() = this is Loading

    fun isError() = this is Error
}