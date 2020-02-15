package eu.gitcode.songs.domain.controller

import eu.gitcode.songs.domain.model.song.Song
import io.reactivex.Single

interface SongsController {
    fun getSongsList(): Single<List<Song>>
}