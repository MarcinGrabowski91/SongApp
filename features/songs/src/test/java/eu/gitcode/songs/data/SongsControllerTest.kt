package eu.gitcode.songs.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import eu.gitcode.core.domain.controller.FileController
import eu.gitcode.songs.RxSchedulersOverrideRule
import eu.gitcode.songs.data.controller.SongsControllerImpl
import eu.gitcode.songs.data.model.SongLocal
import eu.gitcode.songs.data.model.SongRest
import eu.gitcode.songs.data.model.SongsRest
import eu.gitcode.songs.data.network.SongsApi
import eu.gitcode.songs.domain.controller.DataSource
import eu.gitcode.songs.domain.controller.SongsController
import eu.gitcode.songs.domain.model.Song
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.threeten.bp.LocalDate
import kotlin.test.assertEquals


@RunWith(MockitoJUnitRunner::class)
class SongsControllerTest {

    @Rule
    @JvmField
    internal val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    internal lateinit var songsApi: SongsApi
    @Mock
    internal lateinit var moshi: Moshi
    @Mock
    internal lateinit var fileController: FileController

    private lateinit var songsController: SongsController

    @Before
    fun setUp() {
        songsController = SongsControllerImpl(songsApi, moshi, fileController)
    }

    @Test
    fun `verify that SongsRest from network are converted correctly to Song`() {
        // given
        val songsRest = provideSongsRest()
        whenever(songsApi.getSongs()).thenReturn(Single.just(songsRest))
        // when
        songsController.getSongsList(DataSource.NETWORK)
        // then
        assertEquals(
            songsController.getSongsList(DataSource.NETWORK).blockingGet(),
            Song.fromRest(songsRest)
        )
    }

    @Test
    fun `verify that SongsLocal from file are converted correctly to Song`() {
        // given
        val songsLocal = provideSongsLocal()
        val song = Song.fromLocal(songsLocal)
        val listType = Types.newParameterizedType(List::class.java, SongLocal::class.java)
        // when
        whenever(fileController.getStringFromFile(anyString())).thenReturn(Single.just(""))
        @Suppress("UNCHECKED_CAST")
        whenever(moshi.adapter<List<SongLocal>>(listType)).thenReturn(mock(JsonAdapter::class.java) as JsonAdapter<List<SongLocal>>)
        whenever(moshi.adapter<List<SongLocal>>(listType).fromJson(anyString())).thenReturn(
            songsLocal
        )
        // when
        assertEquals(songsController.getSongsList(DataSource.FILE).blockingGet(), song)
        // then
    }

    @Test
    fun `verify that user will get all the songs when he will load them from all the sources`() {
        // given
        val songsLocal = provideSongsLocal()
        val song = Song.fromLocal(songsLocal)
        val listType = Types.newParameterizedType(List::class.java, SongLocal::class.java)
        val songsRest = provideSongsRest()
        //when

        whenever(fileController.getStringFromFile(anyString())).thenReturn(Single.just("{\"artistClean\":\"artistClean\",\"releaseYear\":\"releaseYear\",\"songClean\":\"songClean\"}"))
        @Suppress("UNCHECKED_CAST")
        whenever(moshi.adapter<List<SongLocal>>(listType)).thenReturn(mock(JsonAdapter::class.java) as JsonAdapter<List<SongLocal>>)
        whenever(moshi.adapter<List<SongLocal>>(listType).fromJson(anyString())).thenReturn(
            songsLocal
        )
        whenever(songsApi.getSongs()).thenReturn(Single.just(songsRest))
        // when
        val networkSongsSize = songsController.getSongsList(DataSource.NETWORK).blockingGet().size
        val fileSongsSize = songsController.getSongsList(DataSource.FILE).blockingGet().size
        val allSongsSize = songsController.getSongsList(DataSource.ALL).blockingGet().size
        // then
        assertEquals(allSongsSize, networkSongsSize + fileSongsSize)
    }

    private fun provideSongsLocal(): List<SongLocal> {
        val artistName = "John"
        val releaseDate = "1999"
        val trackName = "Random track"
        return listOf(
            SongLocal(
                artistClean = artistName,
                combined = null,
                fG = null,
                first = null,
                playCount = null,
                releaseYear = releaseDate,
                songClean = trackName,
                year = null
            )
        )
    }

    private fun provideSongsRest(): SongsRest {
        val artistName = "John"
        val releaseDate = LocalDate.of(1999, 11, 11)
        val trackName = "Random track"
        val songRest = SongRest(
            artistId = null,
            artistName = artistName,
            artistViewUrl = null,
            artworkUrl100 = null,
            artworkUrl30 = null,
            artworkUrl60 = null,
            collectionCensoredName = null,
            collectionExplicitness = null,
            collectionId = null,
            collectionName = null,
            collectionPrice = null,
            collectionViewUrl = null,
            country = null,
            currency = null,
            discCount = null,
            discNumber = null,
            isStreamable = null,
            kind = null,
            previewUrl = null,
            primaryGenreName = null,
            releaseDate = releaseDate,
            trackCensoredName = null,
            trackCount = null,
            trackExplicitness = null,
            trackId = null,
            trackName = trackName,
            trackNumber = null,
            trackPrice = null,
            trackTimeMillis = null,
            trackViewUrl = null,
            wrapperType = null
        )
        return SongsRest(1, listOf(songRest))
    }
}
