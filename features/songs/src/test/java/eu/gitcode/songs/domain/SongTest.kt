package eu.gitcode.songs.domain

import eu.gitcode.songs.data.model.SongLocal
import eu.gitcode.songs.data.model.SongRest
import eu.gitcode.songs.data.model.SongsRest
import eu.gitcode.songs.domain.model.Song
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.threeten.bp.LocalDate
import kotlin.test.assertTrue


@RunWith(MockitoJUnitRunner::class)
class SongTest {

    @Test
    fun `verify that Song is empty if SongsRest is empty`() {
        val songsRest = SongsRest(1, listOf())
        val song = Song.fromRest(songsRest)
        assertTrue { song.isEmpty() }
    }

    @Test
    fun `verify that Song values are the same as from SongRest`() {
        val artistName = "John"
        val releaseDate = LocalDate.of(1999, 11, 11)
        val trackName = "Random track"
        val songsRest = SongsRest(
            1, listOf(
                SongRest(
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
            )
        )
        val song = Song.fromRest(songsRest)
        assertTrue { song[0].artist == artistName }
        assertTrue { song[0].title == trackName }
        assertTrue { song[0].releaseYear == releaseDate.year }
    }

    @Test
    fun `verify that Song values are the same as from SongLocal`() {
        val artistName = "John"
        val releaseDate = "1999"
        val trackName = "Random track"
        val songsLocal = listOf(
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
        val song = Song.fromLocal(songsLocal)
        assertTrue { song[0].artist == artistName }
        assertTrue { song[0].title == trackName }
        assertTrue { song[0].releaseYear == releaseDate.toInt() }
    }
}