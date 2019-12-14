package fm.ut.ee.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import butterknife.ButterKnife
import com.google.firebase.auth.FirebaseAuth
import com.tutorial.shourov.auth.R
import kotlinx.android.synthetic.main.activity_reset.*

class ResetActivity : AppCompatActivity() {



    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)
        ButterKnife.bind(this)
        firebaseAuth = FirebaseAuth.getInstance()
        btn_back.setOnClickListener { finish() }
        btn_reset_password.setOnClickListener(View.OnClickListener {
            val userEmail = email.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(userEmail)) {
                Toast.makeText(this@ResetActivity, "Enter your register email id", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            progressBar.visibility = View.VISIBLE
            //reset password you will get a mail
            firebaseAuth!!.sendPasswordResetEmail(userEmail)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@ResetActivity, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@ResetActivity, "Failed to send reset email!", Toast.LENGTH_SHORT).show()
                        }
                        progressBar.visibility = View.GONE
                    }
        })
    }
}