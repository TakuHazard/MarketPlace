package videodemos.example.marketplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class ListingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)

        setupButtons()
    }

    private fun setupButtons() {
        val addNewListing : Button = findViewById(R.id.btn_post_new_listing)
        addNewListing.setOnClickListener{
            Toast.makeText(this, "Button Clicked!", Toast.LENGTH_LONG).show()
        }

    }
}
