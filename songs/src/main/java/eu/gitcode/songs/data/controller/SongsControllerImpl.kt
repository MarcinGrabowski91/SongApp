package eu.gitcode.songs.data.controller

import eu.gitcode.songs.data.network.SongsApi
import eu.gitcode.songs.domain.controller.SongsController
import eu.gitcode.songs.domain.model.song.Song
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongsControllerImpl
@Inject constructor(private val songsApi: SongsApi) : SongsController {
    override fun getSongsList(): Single<List<Song>> {
        return songsApi.getSongs()
            .map { songsRest -> Song.fromRest(songsRest) }
    }
}