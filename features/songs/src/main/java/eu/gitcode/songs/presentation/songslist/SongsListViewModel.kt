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

class SongsListViewModel
@Inject constructor(private val songsController: SongsController) : ViewModel() {

    private var songsDisposable: Disposable? = null

    private val users: MutableLiveData<List<Song>> = MutableLiveData()

    fun observeSongs(): LiveData<List<Song>> {
        return users
    }

    fun getSongs(dataSource: DataSource) {
        songsDisposable?.dispose()
        songsDisposable = songsController.getSongsList(dataSource)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { users.value = it },
                onError = Timber::e
            )
    }

    override fun onCleared() {
        songsDisposable?.dispose()
        super.onCleared()
    }
}
