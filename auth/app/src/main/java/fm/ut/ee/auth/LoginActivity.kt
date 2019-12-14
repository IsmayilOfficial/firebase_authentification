package fm.ut.ee.auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import butterknife.ButterKnife
import com.google.firebase.auth.FirebaseAuth
import com.tutorial.shourov.auth.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {







    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        //auto login process
//move to main activity if user already sign in
        if (firebaseAuth.getCurrentUser() != null) { // User is logged in
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        firebaseAuth = FirebaseAuth.getInstance()
        btn_signup.setOnClickListener { this.startActivity(Intent(this, SignupActivity::class.java)) }
        reset_button.setOnClickListener { this.startActivity(Intent(this, ResetActivity::class.java)) }
        login_button.setOnClickListener(View.OnClickListener {
            val useremail = email.text.toString()
            val userpassword = password.text.toString()
            if (TextUtils.isEmpty(useremail)) {
                Toast.makeText(this.applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(userpassword)) {
                Toast.makeText(this.applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            progressBar.visibility = View.VISIBLE
            //login user
            firebaseAuth.signInWithEmailAndPassword(useremail, userpassword)
                    .addOnCompleteListener { task ->
                        progressBar.visibility = View.GONE
                        if (!task.isSuccessful) {
                            if (userpassword.length < 6) {
                                password!!.error = this@LoginActivity.getString(R.string.minimum_password)
                            } else {
                                Toast.makeText(this, this@LoginActivity.getString(R.string.auth_failed), Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            this.startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
        })
    }
}