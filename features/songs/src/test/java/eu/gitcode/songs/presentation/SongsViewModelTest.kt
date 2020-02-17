package eu.gitcode.songs.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import eu.gitcode.songs.RxSchedulersOverrideRule
import eu.gitcode.songs.domain.controller.DataSource
import eu.gitcode.songs.domain.controller.SongsController
import eu.gitcode.songs.domain.model.Song
import eu.gitcode.songs.presentation.songslist.SongsViewModel
import eu.gitcode.songs.presentation.songslist.SongsViewState
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@RunWith(MockitoJUnitRunner::class)
class SongsViewModelTest {

    @Rule
    @JvmField
    internal val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    internal lateinit var songsController: SongsController

    @InjectMocks
    private lateinit var viewModel: SongsViewModel

    @Test
    fun `verify that state is Empty`() {
        // given
        whenever(songsController.getSongsList(DataSource.NETWORK)).thenReturn(
            Single.just(listOf())
        )
        // when
        viewModel.getSongs(DataSource.NETWORK)
        // then
        assertEquals(viewModel.state.value, SongsViewState.Empty)
    }

    @Test
    fun `verify that state is Listed`() {
        // given
        whenever(songsController.getSongsList(DataSource.NETWORK)).thenReturn(
            Single.just(listOf(Song("Music", "Artist", 1999)))
        )
        // when
        viewModel.getSongs(DataSource.NETWORK)
        // then
        assertEquals(viewModel.state.value, SongsViewState.Listed)
    }

    @Test
    fun `verify that state is Error`() {
        // given
        whenever(songsController.getSongsList(DataSource.NETWORK)).thenReturn(
            Single.error(Throwable())
        )
        // when
        viewModel.getSongs(DataSource.NETWORK)
        // then
        assertEquals(viewModel.state.value, SongsViewState.Error)
    }

    @Test
    fun `verify that state is not loading`() {
        // given
        whenever(songsController.getSongsList(DataSource.NETWORK)).thenReturn(
            Single.just(listOf(Song("Music", "Artist", 1999)))
        )
        // when
        viewModel.getSongs(DataSource.NETWORK)
        // then
        assertNotEquals(viewModel.state.value, SongsViewState.Loading)
    }

    @Test
    fun `verify that songs are emitted to livedata`() {
        // given
        val songs = listOf(Song("Music", "Artist", 1999))
        whenever(songsController.getSongsList(DataSource.NETWORK)).thenReturn(
            Single.just(songs)
        )
        // when
        viewModel.getSongs(DataSource.NETWORK)
        // then
        assertEquals(viewModel.songs.value, songs)
    }
}
