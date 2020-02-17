package eu.gitcode.songs.presentation.songslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import eu.gitcode.songapp.app.App
import eu.gitcode.songapp.extension.visible
import eu.gitcode.songs.databinding.SongsFragmentBinding
import eu.gitcode.songs.domain.controller.DataSource
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
        handleSongs()
        handleStates()
    }

    private fun handleSongs() {
        viewModel.songs.observe(viewLifecycleOwner, Observer { songs ->
            adapter?.setData(songs)
            binding.songsRecyclerViews.scrollToPosition(0)
        })
    }

    private fun handleStates() {
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            binding.emptyTxt.visible = state.isEmpty()
            binding.songsRecyclerViews.visible = state.isListed()
            binding.errorTxt.visible = state.isError()
            binding.spinner.visible = state.isLoading()
        })
    }

    private fun setupButtons() {
        binding.loadFromFileBtn.setOnClickListener { viewModel.getSongs(DataSource.FILE) }
        binding.loadFromNetworkBtn.setOnClickListener { viewModel.getSongs(DataSource.NETWORK) }
        binding.loadAllSourcesBtn.setOnClickListener { viewModel.getSongs(DataSource.ALL) }
    }

    private fun setupViewHolder() {
        binding.songsRecyclerViews.adapter = MySongRecyclerViewAdapter()
        binding.songsRecyclerViews.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                VERTICAL
            )
        )
        adapter = binding.songsRecyclerViews.adapter as MySongRecyclerViewAdapter
    }

    private fun inject() {
        DaggerSongsComponent.builder()
            .coreComponent(App.coreComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }
}
