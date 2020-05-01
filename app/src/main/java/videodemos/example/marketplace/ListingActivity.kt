package videodemos.example.marketplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)

        setupRecyclerView()
        setupButtons()
    }

    private fun setupRecyclerView() {
        val listingsContainer = findViewById<RecyclerView>(R.id.rv_listings)

        val listingsDataSet = getDummyListings()

        val viewAdapter : RecyclerView.Adapter<*> = ListingAdapter(listingsDataSet)
        val viewManager : RecyclerView.LayoutManager = LinearLayoutManager(this)

        listingsContainer.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }
    }

    private fun getDummyListings() : MutableList<Listing>{
        val listingA = Listing("MyListingA", 25, "MyDescription", 0, mutableListOf("one", "two"))
        val listingB = Listing("MyListingB", 25, "MyDescription", 0, mutableListOf("one", "two"))
        val listingC = Listing("MyListingC", 25, "MyDescription", 0, mutableListOf("one", "two"))

        return mutableListOf(listingA, listingB, listingC)
    }

    private fun setupButtons() {
        val addNewListing : Button = findViewById(R.id.btn_post_new_listing)
        addNewListing.setOnClickListener{
            Toast.makeText(this, "Button Clicked!", Toast.LENGTH_LONG).show()
        }

    }
}
