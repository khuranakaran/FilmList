import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedemo.databinding.AdapterMovieBinding
import com.example.moviedemo.models.Search

class MainAdapter: RecyclerView.Adapter<MainViewHolder>() {
    private var movies = ArrayList<Search>()
    fun setMovieList(movies: ArrayList<Search>) {
        this.movies = movies
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterMovieBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movies
        holder.binding.name.text = movie[position].title
        Glide.with(holder.itemView.context).load(movie[position].poster).into(holder.binding.imageview)
    }
    override fun getItemCount(): Int {
        return movies.size
    }

    // method for filtering our recyclerview items.
    fun filterList(filteredList: ArrayList<Search>) {
        // below line is to add our filtered
        // list in our course array list.
        this.movies = filteredList
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }
}
class MainViewHolder(val binding: AdapterMovieBinding) : RecyclerView.ViewHolder(binding.root) {
}