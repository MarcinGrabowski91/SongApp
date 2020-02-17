package eu.gitcode.songs.presentation.songslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.gitcode.songs.domain.controller.DataSource
import eu.gitcode.songs.domain.controller.SongsController
import eu.gitcode.songs.domain.model.Song
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SongsViewModel
@Inject constructor(private val songsController: SongsController) : ViewModel() {

    private val _songs: MutableLiveData<List<Song>> by lazy { MutableLiveData<List<Song>>() }
    private val _state: MutableLiveData<SongsViewState> by lazy {
        MutableLiveData<SongsViewState>(
            SongsViewState.Empty
        )
    }
    private var songsDisposable: Disposable? = null

    val songs: LiveData<List<Song>>
        get() = _songs

    val state: LiveData<SongsViewState>
        get() = _state

    fun getSongs(dataSource: DataSource) {
        songsDisposable?.dispose()
        songsDisposable = songsController.getSongsList(dataSource)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.value = SongsViewState.Loading }
            .doOnSuccess { songs ->
                if (songs.isEmpty()) {
                    _state.value = SongsViewState.Empty
                } else {
                    _state.value = SongsViewState.Listed
                }
            }
            .doOnError { _state.value = SongsViewState.Error }
            .subscribeBy(
                onSuccess = {
                    _songs.value = it
                },
                onError = Timber::e
            )
    }

    override fun onCleared() {
        songsDisposable?.dispose()
        super.onCleared()
    }
}