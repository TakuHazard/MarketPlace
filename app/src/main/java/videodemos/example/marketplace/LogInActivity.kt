package videodemos.example.marketplace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        //TODO: Move creation of intent to RegisterActivity
        textView_register_for_account.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogIn.setOnClickListener {
            val email = textView_email_log_in.text.toString()
            val password = textView_password_log_in.text.toString()
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    //TODO: Add a constant for the tag
                    Log.d("LogInActivity", "Successful login")
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    Log.d("LoginActivity", "Failed to login")
                }
        }

    }

}

