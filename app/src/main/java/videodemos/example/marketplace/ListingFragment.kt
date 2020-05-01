package videodemos.example.marketplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import videodemos.example.marketplace.model.Listing

class ListingFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.activity_listing, container, false)

        setupRecyclerView()
        setupButtons()

        return rootView
    }

    private fun setupRecyclerView() {
        val listingsContainer = rootView.findViewById<RecyclerView>(R.id.rv_listings)

        val listingsDataSet = getDummyListings()

        val viewAdapter : RecyclerView.Adapter<*> = ListingAdapter(listingsDataSet)
        val viewManager : RecyclerView.LayoutManager = LinearLayoutManager(activity)

        listingsContainer.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }

//        var tracker = SelectionTracker.Builder(
//            "my-selection-id",
//            listingsContainer,
//            StableIdKeyProvider(listingsContainer),
//            MyDetailsLookup(listingsContainer),
//            StorageStrategy.createLongStorage())
//            .withOnItemActivatedListener(myItemActivatedListener)
//            .build()
    }

    private fun getDummyListings() : MutableList<Listing>{
        val listingA = Listing(
            "Backpack",
            10,
            "MyDescription",
            0,
            mutableListOf("one", "two"),
            R.drawable.test_backpack
        )
        val listingB = Listing(
            "Iphone 11",
            300,
            "MyDescription",
            0,
            mutableListOf("one", "two"),
            R.drawable.test_iphone
        )
        val listingC = Listing(
            "Chair",
            30,
            "MyDescription",
            0,
            mutableListOf("one", "two"),
            R.drawable.test_chair
        )

        return mutableListOf(listingA, listingB, listingC)
    }

    private fun setupButtons() {
        val addNewListing : Button = rootView.findViewById(R.id.btn_post_new_listing)
        addNewListing.setOnClickListener{
            Toast.makeText(activity, "Button Clicked!", Toast.LENGTH_LONG).show()
        }

    }
}
