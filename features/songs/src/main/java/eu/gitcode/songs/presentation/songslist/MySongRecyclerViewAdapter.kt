package eu.gitcode.songs.presentation.songslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.gitcode.songs.databinding.SongViewHolderBinding
import eu.gitcode.songs.domain.model.Song

class MySongRecyclerViewAdapter(
    private val songs: MutableList<Song> = mutableListOf()
) : RecyclerView.Adapter<MySongRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val songViewHolderBinding = SongViewHolderBinding.inflate(inflater, parent, false)
        return ViewHolder(songViewHolderBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = songs[position]
        holder.binding.titleTxt.text = item.title
        holder.binding.artistTxt.text = item.artist
        if (item.releaseYear == null) {
            holder.binding.releaseYearTxt.text = ""
        } else {
            holder.binding.releaseYearTxt.text = item.releaseYear.toString()
        }
    }

    override fun getItemCount(): Int = songs.size

    fun setData(songs: List<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: SongViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root)
}