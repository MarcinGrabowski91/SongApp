package eu.gitcode.songs.domain.controller

import eu.gitcode.songs.domain.model.Song
import io.reactivex.Single

interface SongsController {
    fun getSongsList(dataSource: DataSource): Single<List<Song>>
}