import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simodista.databinding.LocationItemRowBinding

class LocationListAdapter : RecyclerView.Adapter<LocationListAdapter.ListViewHolder>() {
    private val location: ArrayList<String> = ArrayList()

    fun setReport(list: ArrayList<String>){
        location.clear()
        location.addAll(list)
        notifyDataSetChanged()
    }

    class ListViewHolder(private val binding: LocationItemRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(location: String){
            binding.textView.text = location
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = LocationItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(location[position])
    }

    override fun getItemCount(): Int = location.size
}