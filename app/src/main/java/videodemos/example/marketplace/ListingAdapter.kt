package videodemos.example.marketplace

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import videodemos.example.marketplace.model.Listing

class ListingAdapter(private val listingsDataSet: MutableList<Listing>) :
    RecyclerView.Adapter<ListingAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val card: CardView) : RecyclerView.ViewHolder(card){
        val title : TextView = card.findViewById(R.id.tv_card_listing_title)
        val picture : ImageView = card.findViewById(R.id.iv_card_listing_image)
        val cost : TextView = card.findViewById(R.id.tv_card_listing_cost)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ListingAdapter.MyViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.listing_item, parent, false) as CardView
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(cardView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val currentListing = listingsDataSet[position]
        holder.title.text = currentListing.title
        holder.picture.setImageResource(currentListing.imageId)
        holder.cost.text = currentListing.cost.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listingsDataSet.size
}