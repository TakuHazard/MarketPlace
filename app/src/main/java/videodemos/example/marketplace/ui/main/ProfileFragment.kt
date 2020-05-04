package videodemos.example.marketplace.ui.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import videodemos.example.marketplace.R
import videodemos.example.marketplace.RegisterActivity
import videodemos.example.marketplace.User
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private var sectionNumber: Int? = null
    private var profilePath: String? = null

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
            .uid

        if (user == null) {
            launchRegisterActivity()
        } else {
            retrieveUserInformation(view, user)
        }

        view.findViewById<Button>(R.id.btn_logout).setOnClickListener {
            logOutOfDatabase()
        }

        view.findViewById<CircleImageView>(R.id.imgview_profileImage).setOnClickListener {
            selectImage()
        }

        return view
    }

    private fun retrieveUserInformation(view: View, user: String) {
        val ref = FirebaseDatabase
            .getInstance()
            .getReference("/users/${user}")

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(
                    "ProfileFragment",
                    "Error when retrieving user information from the database: ${p0.message}"
                )
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val dbUser = dataSnapshot.getValue<User>()
                    if (dbUser != null) {
                        val username = view.findViewById<TextView>(R.id.txt_profileUsername)
                        val karma = view.findViewById<TextView>(R.id.txt_profileKarma)
                        username.text = dbUser.username
                        karma.text = getString(R.string.karma_template, dbUser.karma)
                        profilePath = dbUser.profileImageUrl
                        Picasso
                            .get()
                            .load(dbUser.profileImageUrl)
                            .into(view.findViewById<CircleImageView>(R.id.imgview_profileImage))
                    } else {
                        Log.d(
                            "ProfileFragment",
                            "Error when retrieving user data from the database."
                        )
                    }
                }
            }
        }
        ref.addListenerForSingleValueEvent(valueEventListener)
    }

    private fun selectImage() {
        Log.d("ProfileFragment", "Show image selector")

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("ResultActivity", "Photo selected")
        }

        val selectedImage = data?.data

        if (selectedImage != null) {
            profilePath = selectedImage.toString()
        }

        Picasso
            .get()
            .load(profilePath)
            .into(view?.findViewById<CircleImageView>(R.id.imgview_profileImage))

        if (selectedImage != null) {
            uploadImageToFirebase(selectedImage)
        }
    }

    private fun uploadImageToFirebase(selectedPhotoUrl: Uri) {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage
            .getInstance()
            .getReference("/images/$filename")

        ref.putFile(selectedPhotoUrl)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully uploaded image: $it.metadata?.path")
                ref.downloadUrl.addOnSuccessListener {
                    updateProfileImagePathInDB(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to upload image to the database")
            }
    }

    private fun updateProfileImagePathInDB(path: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""

        val ref = FirebaseDatabase
            .getInstance()
            .getReference("/users/$uid")

        ref.child("profileImageUrl").setValue(path)
            .addOnSuccessListener {
                Log.d(TAG, "Updated profile image path on firebase database")
            }
            .addOnFailureListener {
                Log.d(
                    TAG,
                    "Failed to update profile image path in firebase database: ${it.message}"
                )
            }
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
        private const val TAG = "ProfileFragment"
        private const val SECTION_NUMBER = "sectionNumber"

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
