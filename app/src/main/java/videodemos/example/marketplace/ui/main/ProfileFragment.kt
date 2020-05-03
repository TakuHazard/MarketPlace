package videodemos.example.marketplace.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import videodemos.example.marketplace.R
import videodemos.example.marketplace.RegisterActivity
import videodemos.example.marketplace.User

private const val SECTION_NUMBER = "sectionNumber"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private var sectionNumber: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sectionNumber = it.getInt(SECTION_NUMBER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val user = FirebaseAuth
            .getInstance()
            .currentUser

        if (user != null) {
            val ref = FirebaseDatabase
                .getInstance()
                .getReference("/users/${user.uid}")

            val valueEventListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.d(
                        "ProfileActivity",
                        "Error when retrieving user information from the database: ${p0.message}"
                    )
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val dbUser = dataSnapshot.getValue<User>()
                        if (dbUser != null) {
                            Log.d("ProfileActivity", dbUser.username)

                            val username = view.findViewById<TextView>(R.id.txt_profileUsername)
                            val karma = view.findViewById<TextView>(R.id.txt_profileKarma)
                            username.text = dbUser.username
                            karma.text = getString(R.string.karma_template, dbUser.karma)
                        } else {
                            Log.d(
                                "ProfileActivity",
                                "Error when retrieving user data from the database."
                            )
                        }
                    }
                }
            }
            ref.addListenerForSingleValueEvent(valueEventListener)
        }

        view.findViewById<Button>(R.id.btn_logout).setOnClickListener {
            logOutOfDatabase()
        }

        return view
    }

    private fun logOutOfDatabase() {
        FirebaseAuth
            .getInstance()
            .signOut()

        launchRegisterActivity()
    }

    private fun launchRegisterActivity() {
        val intent = Intent(activity, RegisterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param num Section number.
         * @return A new instance of fragment ProfileFragment.
         */
        @JvmStatic
        fun newInstance(num: Int) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putInt(SECTION_NUMBER, num)
                }
            }
    }
}
