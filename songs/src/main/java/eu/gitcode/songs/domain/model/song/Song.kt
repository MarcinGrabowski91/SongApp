package eu.gitcode.songs.domain.model.song

import eu.gitcode.songs.data.model.SongRest
import eu.gitcode.songs.data.model.SongsRest

data class Song(
    val title: String,
    val artist: String,
    val releaseYear: Int
) {
    companion object {
        fun fromRest(songRest: SongRest): Song {
            return Song(songRest.trackName, songRest.artistName, songRest.releaseDate.year)
        }

        fun fromRest(songsRest: SongsRest): List<Song> {
            val songsList = mutableListOf<Song>()
            songsRest.songRests
                .asSequence()
                .iterator()
                .forEach { songRest -> songsList.add(fromRest(songRest)) }
            return songsList
        }
    }
}