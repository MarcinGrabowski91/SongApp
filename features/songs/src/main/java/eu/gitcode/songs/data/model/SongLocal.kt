package eu.gitcode.songs.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SongLocal(
    @Json(name = "ARTIST CLEAN")
    val artistClean: String,
    @Json(name = "COMBINED")
    val combined: String,
    @Json(name = "F*G")
    val fG: Int,
    @Json(name = "First?")
    val first: Int,
    @Json(name = "PlayCount")
    val playCount: Int,
    @Json(name = "Release Year")
    val releaseYear: String,
    @Json(name = "Song Clean")
    val songClean: String,
    @Json(name = "Year?")
    val year: Int
)