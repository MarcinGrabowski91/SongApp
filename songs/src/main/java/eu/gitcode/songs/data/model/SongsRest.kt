package eu.gitcode.songs.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SongsRest(
    @Json(name = "resultCount")
    val resultCount: Int,
    @Json(name = "results")
    val songRests: List<SongRest>
)