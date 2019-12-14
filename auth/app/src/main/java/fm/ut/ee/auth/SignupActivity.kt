package fm.ut.ee.auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import butterknife.ButterKnife
import com.google.firebase.auth.FirebaseAuth
import com.tutorial.shourov.auth.R
import kotlinx.android.synthetic.main.activity_login.email
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.reset_button
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        ButterKnife.bind(this) //using butterknife fot finding widgets
        //click R.layout.activity_signup press alt + enter to generate
//firebase authentication instance
        firebaseAuth = FirebaseAuth.getInstance()
        reset_button.setOnClickListener { this.startActivity(Intent(this@SignupActivity, ResetActivity::class.java)) }
        sign_in_button.setOnClickListener { finish() }
        sign_up_button.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val userEmail = email.text.toString().trim { it <= ' ' }
        val userPassword = password.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(userEmail)) {
            showToast("Enter email address!")
            return
        }
        if (TextUtils.isEmpty(userPassword)) {
            showToast("Enter Password!")
            return
        }
        if (userPassword.length < 6) {
            showToast("Password too short, enter minimum 6 characters")
            return
        }
        progress_bar.visibility = View.VISIBLE
        //register user
        firebaseAuth!!.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this) { task ->
                    Log.d(TAG, "New user registration: " + task.isSuccessful)
                    if (!task.isSuccessful) {
                        showToast("Authentication failed. " + task.exception)
                    } else {
                        this.startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
    }

    override fun onResume() {
        super.onResume()
        progress_bar.visibility = View.GONE
    }

    fun showToast(toastText: String?) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "SignupActivity"
    }
}