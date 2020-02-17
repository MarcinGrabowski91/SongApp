package eu.gitcode.songs.data.network

import eu.gitcode.songs.data.model.SongsRest
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

interface SongsApi {
    @POST("search")
    fun getSongs(
        @Query("term") term: String = "James", @Query("limit") page: Int = DEFAULT_PAGE_SIZE
    ): Single<SongsRest>

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }
}