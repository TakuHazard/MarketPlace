package videodemos.example.marketplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)


        registerForAccountTextView.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()

        }

        btnLogIn.setOnClickListener {
            val email = textViewEmailLogIn.text.toString()
            val password = textViewPasswordLogIn.text.toString()
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnSuccessListener {
                Log.d("LogInActivity","Successful login")

                // Launch Next Activity
            }
                .addOnFailureListener{
                    Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                    Log.d("LoginActivity","Failed to login")
                }
        }


    }

}

