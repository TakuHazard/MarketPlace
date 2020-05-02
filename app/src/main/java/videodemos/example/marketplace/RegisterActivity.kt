package videodemos.example.marketplace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val defaultProfileImageUrl = "default_profile_image.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {

            performRegiser()
        }

        textView_existing_account.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun performRegiser() {

        val email = textView_email_register.text.toString()

        val userName = textView_username_register.text.toString()

        val password = textView_password_register.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_LONG).show()
            return
        }

        //Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // else if successful
                Log.d(
                    "RegisterActivity",
                    "successfully created user with uid: ${it.result?.user?.uid}"
                )
                Log.d("RegisterActivity", "calling database")
                saveToDatabase(userName)

            }
            .addOnFailureListener {
                Log.d("Main", "Failed to create user ${it.message}")
                Toast.makeText(this, "Failed to create user ${it.message}", Toast.LENGTH_LONG)
                    .show()
            }
    }

    private fun saveToDatabase(username: String) {
        Log.d("RegisterActivity", "Inside db")
        Log.d("RegisterActivity", username)

        val uid = FirebaseAuth.getInstance().uid ?: ""

        val ref = FirebaseDatabase
            .getInstance()
            .getReference("/users/$uid")

        Log.d("RegisterActivity", ref.toString())
        val defaultKarma = 0
        var defaultImageUrl = ""

        val defaultImage = FirebaseStorage
            .getInstance()
            .getReference(defaultProfileImageUrl)
        defaultImage
            .downloadUrl
            .addOnSuccessListener {
                Log.d("RegisterActivity", it.toString())
                defaultImageUrl = it.toString()
            }
            .addOnFailureListener {
                Log.d(
                    "RegisterActivity",
                    "Failed to retrieve default profile image url from database."
                )
            }
            .addOnCompleteListener {
                val user = User(uid, username, defaultKarma, defaultImageUrl)

                Log.d("RegisterActivity", user.toString())

                ref.setValue(user)
                    .addOnSuccessListener {
                        Log.d("RegisterActivity", "Finally saved the user to Firebase DB")
                    }

                Log.d("RegisterActivity", "done")

                val intent = Intent(this, DashboardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }
}

class User(
    val uid: String,
    val username: String,
    val defaultKarma: Int,
    val profileImageUrl: String
)
