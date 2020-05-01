package videodemos.example.marketplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import videodemos.example.marketplace.model.Listing

class ListingFragment : Fragment() {
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_listing, container, false)

        setupRecyclerView()
        setupButtons()

        return rootView
    }

    private fun setupRecyclerView() {
        val listingsContainer = rootView.findViewById<RecyclerView>(R.id.rv_listings)

        val listingsDataSet = getDummyListings()

        val viewAdapter : RecyclerView.Adapter<*> = ListingAdapter(listingsDataSet)
        val viewManager : RecyclerView.LayoutManager = GridLayoutManager(activity, 1)

        listingsContainer.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }

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

        val filtersDropDown : ImageButton = rootView.findViewById(R.id.ib_filters)
        filtersDropDown.setOnClickListener{
            changeNumberOfCardsPerRow()
        }

    }

    private fun changeNumberOfCardsPerRow() {
        val listingsContainer = rootView.findViewById<RecyclerView>(R.id.rv_listings)
        listingsContainer.apply{
            if ((layoutManager as GridLayoutManager).spanCount == 1){
                layoutManager = GridLayoutManager(activity, 2)
            } else {
                layoutManager = GridLayoutManager(activity, 1)
            }

        }
    }
}
