package videodemos.example.marketplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import videodemos.example.marketplace.model.Listing

class ListingFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var viewAdapter: ListingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_listing, container, false)

        setupRecyclerView()
        setupSearchView()
        setupButtons()

        return rootView
    }

    private fun setupRecyclerView() {
        //TODO: Look at Groupie for RecyclerView, https://github.com/lisawray/groupie
        val listingsContainer = rootView.findViewById<RecyclerView>(R.id.rv_listings)

        val listingsDataSet = getDummyListings()

        viewAdapter = ListingAdapter(listingsDataSet)
        val viewManager: RecyclerView.LayoutManager = GridLayoutManager(activity, 1)

        listingsContainer.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }

    }

    private fun getDummyListings(): MutableList<Listing> {
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

        return mutableListOf(
            listingA,
            listingB,
            listingC,
            listingA,
            listingA,
            listingB,
            listingC,
            listingB
        )
    }

    private fun setupSearchView() {
        val searchView: SearchView = rootView.findViewById(R.id.sv_filter_listings)
        searchView.setOnClickListener {
            searchView.isIconified = false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewAdapter.filter.filter(newText)
                return false
            }

        })
    }

    private fun setupButtons() {
        val addNewListing: Button = rootView.findViewById(R.id.btn_post_new_listing)
        addNewListing.setOnClickListener {
            Toast.makeText(activity, "Button Clicked!", Toast.LENGTH_LONG).show()
        }

        val filtersDropDown: ImageButton = rootView.findViewById(R.id.ib_filters)
        filtersDropDown.setOnClickListener {
            changeNumberOfCardsPerRow()
        }
    }

    private fun changeNumberOfCardsPerRow() {
        val listingsContainer = rootView.findViewById<RecyclerView>(R.id.rv_listings)
        //TODO: Simplify.
        listingsContainer.apply {
            layoutManager = if ((layoutManager as GridLayoutManager).spanCount == 1) {
                GridLayoutManager(activity, 2)
            } else {
                GridLayoutManager(activity, 1)
            }
        }
    }

    companion object {
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(): ListingFragment {
            return ListingFragment()
        }
    }
}
