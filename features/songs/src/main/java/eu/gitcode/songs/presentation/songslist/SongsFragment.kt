package eu.gitcode.songs.presentation.songslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eu.gitcode.songapp.app.App
import eu.gitcode.songs.databinding.SongsFragmentBinding
import eu.gitcode.songs.domain.controller.DataSource
import eu.gitcode.songs.domain.model.Song
import eu.gitcode.songs.presentation.di.DaggerSongsComponent
import javax.inject.Inject

class SongsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SongsViewModel

    private lateinit var binding: SongsFragmentBinding

    private var adapter: MySongRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inject()
        binding = SongsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupViewHolder()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SongsViewModel::class.java)
        handleSongsObserver()
    }

    private fun handleSongsObserver() {
        viewModel.songs.observe(viewLifecycleOwner, Observer<List<Song>> { songs ->
            adapter?.setData(songs)
        })
    }

    private fun setupButtons() {
        binding.loadFromFileBtn.setOnClickListener { viewModel.getSongs(DataSource.FILE) }
        binding.loadFromNetworkBtn.setOnClickListener { viewModel.getSongs(DataSource.NETWORK) }
        binding.loadAllSourcesBtn.setOnClickListener { viewModel.getSongs(DataSource.ALL) }
    }

    private fun setupViewHolder() {
        binding.songsRecyclerViews.adapter = MySongRecyclerViewAdapter()
        adapter = binding.songsRecyclerViews.adapter as MySongRecyclerViewAdapter
    }

    private fun inject() {
        DaggerSongsComponent.builder()
            .coreComponent(App.coreComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }
}
