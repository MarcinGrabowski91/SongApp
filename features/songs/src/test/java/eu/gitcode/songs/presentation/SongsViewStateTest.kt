package eu.gitcode.songs.presentation

import eu.gitcode.songs.presentation.songslist.SongsViewState
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class SongsViewStateTest {

    private lateinit var state: SongsViewState

    @Test
    fun `verify that state is Loading`() {
        state = SongsViewState.Loading
        assertTrue(state.isLoading())
    }

    @Test
    fun `verify that state is Listed`() {
        state = SongsViewState.Listed
        assertTrue(state.isListed())
    }

    @Test
    fun `verify that state is Empty`() {
        state = SongsViewState.Empty
        assertTrue(state.isEmpty())
    }

    @Test
    fun `verify that state is Error`() {
        state = SongsViewState.Error
        assertTrue(state.isError())
    }
}