package videodemos.example.marketplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRegister.setOnClickListener {

            performRegiser()
        }

        existingAccountRegisterTextView.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()

        }

    }

    private fun performRegiser() {

        val email = textViewEmailRegister.text.toString()

        val userName = textViewUserNameRegister.text.toString()

        val password = textViewPasswordRegister.text.toString()

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

            }
            .addOnFailureListener {
                Log.d("Main", "Failed to create user ${it.message}")
                Toast.makeText(this, "Failed to create user ${it.message}", Toast.LENGTH_LONG)
                    .show()
            }
    }
}
