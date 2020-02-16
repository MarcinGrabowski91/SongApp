package eu.gitcode.songs.presentation.songslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eu.gitcode.songapp.app.App
import eu.gitcode.songs.R
import eu.gitcode.songs.domain.model.Song
import eu.gitcode.songs.presentation.di.DaggerSongsComponent
import javax.inject.Inject

class SongsListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SongsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inject()
        return inflater.inflate(R.layout.songs_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SongsListViewModel::class.java)
        handleSongsObserver()
    }

    private fun handleSongsObserver() {
        viewModel.observeSongs().observe(viewLifecycleOwner, Observer<List<Song>> { songs ->
            // TODO handle songs
        })
    }

    private fun inject() {
        DaggerSongsComponent.builder()
            .coreComponent(App.coreComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }
}
