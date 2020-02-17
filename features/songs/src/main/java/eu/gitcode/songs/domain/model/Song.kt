package eu.gitcode.songs.domain.model

import eu.gitcode.songs.data.model.SongLocal
import eu.gitcode.songs.data.model.SongRest
import eu.gitcode.songs.data.model.SongsRest

data class Song(
    val title: String,
    val artist: String,
    val releaseYear: Int?
) {
    companion object {

        fun fromRest(songsRest: SongsRest): List<Song> {
            val songsList = mutableListOf<Song>()
            for (songRest in songsRest.songsRests) {
                songsList.add(fromRest(songRest))
            }
            return songsList
        }

        fun fromLocalJson(songsLocal: List<SongLocal>): List<Song> {
            val songsList = mutableListOf<Song>()
            for (songLocal in songsLocal) {
                var releaseYear: Int? = null
                if (songLocal.releaseYear != "") {
                    releaseYear = songLocal.releaseYear?.toIntOrNull()
                }
                songsList.add(
                    Song(
                        songLocal.songClean,
                        songLocal.artistClean,
                        releaseYear
                    )
                )
            }
            return songsList
        }

        private fun fromRest(songRest: SongRest): Song {
            return Song(
                songRest.trackName,
                songRest.artistName,
                songRest.releaseDate?.year
            )
        }
    }
}