package eu.gitcode.songs.data.controller

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import eu.gitcode.core.domain.controller.FileController
import eu.gitcode.songs.data.model.SongLocal
import eu.gitcode.songs.data.network.SongsApi
import eu.gitcode.songs.domain.controller.DataSource
import eu.gitcode.songs.domain.controller.SongsController
import eu.gitcode.songs.domain.model.Song
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongsControllerImpl
@Inject constructor(
    private val songsApi: SongsApi,
    private val moshi: Moshi,
    private val fileController: FileController
) : SongsController {

    override fun getSongsList(dataSource: DataSource): Single<List<Song>> {
        return when (dataSource) {
            DataSource.NETWORK -> {
                getSongsListFromNetwork()
            }
            DataSource.FILE -> {
                getSongsListFromLocalJson()
            }
            DataSource.ALL -> {
                getSongsFromAllSources()
            }
        }
    }

    private fun getSongsListFromNetwork(): Single<List<Song>> {
        return songsApi.getSongs()
            .map { songsRest -> Song.fromRest(songsRest) }
    }

    private fun getSongsListFromLocalJson(): Single<List<Song>> {
        val listType = Types.newParameterizedType(List::class.java, SongLocal::class.java)
        val adapter: JsonAdapter<List<SongLocal>> = moshi.adapter(listType)

        return fileController.getStringFromFile(SONGS_JSON_FILE_NAME)
            .map { adapter.fromJson(it) }
            .map { Song.fromLocalJson(it) }
    }

    private fun getSongsFromAllSources(): Single<List<Song>> {
        return Singles.zip(getSongsListFromNetwork(), getSongsListFromLocalJson())
            .map { pair -> pair.first + pair.second }
    }

    companion object {
        const val SONGS_JSON_FILE_NAME = "songs-list.json"
    }
}