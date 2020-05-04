package videodemos.example.marketplace

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import videodemos.example.marketplace.model.Listing
import java.util.*

class ListingAdapter(private val fullListingDataSet: MutableList<Listing>) :
    RecyclerView.Adapter<ListingAdapter.MyViewHolder>(),
    Filterable {

    private val visibleListingDataSet = mutableListOf<Listing>()
    private var lastPosition: Int = -1

    init {
        visibleListingDataSet.addAll(fullListingDataSet)
    }

    class MyViewHolder(val card: CardView) : RecyclerView.ViewHolder(card) {
        //TODO: Trader name on card, show description when user taps on the card, show tags
        val title: TextView = card.findViewById(R.id.tv_card_listing_title)
        val picture: ImageView = card.findViewById(R.id.iv_card_listing_image)
        val cost: TextView = card.findViewById(R.id.tv_card_listing_cost)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListingAdapter.MyViewHolder {

        val containerView = LayoutInflater.from(parent.context)
            .inflate(R.layout.listing_item, parent, false) as CardView

        return MyViewHolder(containerView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentListing = visibleListingDataSet[position]
        holder.title.text = currentListing.title
        holder.picture.setImageResource(currentListing.imageId)
        holder.cost.text = currentListing.cost.toString()

        val context = holder.itemView.context
        val animationResource: Int

        if (lastPosition > position) {
            animationResource = R.anim.slide_card_from_top
        } else {
            animationResource = R.anim.slide_card_from_bottom
        }

        lastPosition = position

        val animation = AnimationUtils.loadAnimation(context, animationResource)
        holder.card.startAnimation(animation)
    }

    override fun getItemCount() = visibleListingDataSet.size

    override fun getFilter(): Filter {
        //TODO: Filter on multiple types of data
        val textFilter = object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val textFilter = constraint.toString().toLowerCase(Locale.getDefault())
                val filteredListingDataSet = mutableListOf<Listing>()

                if (textFilter.isEmpty()) {
                    filteredListingDataSet.addAll(fullListingDataSet)
                } else {
                    filteredListingDataSet.addAll(fullListingDataSet.filter {
                        it.title.toLowerCase(Locale.getDefault()).contains(textFilter)
                    })
                }

                val filterResults = FilterResults()
                filterResults.values = filteredListingDataSet
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                visibleListingDataSet.clear()

                @Suppress("UNCHECKED_CAST")
                visibleListingDataSet.addAll(results?.values as Collection<Listing>)

                notifyDataSetChanged()
            }

        }

        return textFilter
    }
}