package com.sawrose.daggerdemo.login

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.sawrose.daggerdemo.R
import com.sawrose.daggerdemo.model.User
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    val RC_SIGN_IN:Int = 123

    lateinit var mGoogleSignInClient: GoogleSignInClient

    @Inject
    lateinit var mAuth: FirebaseAuth

    @Inject
    lateinit var mDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        googleSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account)
                } catch (exp: ApiException) {
                    Log.w("Login Act:~", "Google sign in failed");
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val cred = GoogleAuthProvider.getCredential(account?.idToken,null)

        mAuth.signInWithCredential(cred)
                .addOnCompleteListener(this
                ) {
                    if (it.isSuccessful){
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login:~", "signInWithCredential:success")
                        val user = mAuth.currentUser
                        saveDataInDatabase(user, account?.idToken!!)
                        Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show();
//                        startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                    }else{
                        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                }
    }

    private fun saveDataInDatabase(user: FirebaseUser?, token:String) {
        if (user != null) {
            with(user){
                val userData = User(
                        user.uid,
                        user.displayName!!,
                        user.email!!,
                        user.photoUrl.toString(),
                        token
                )
                mDatabase.reference
                        .child("User")
                        .child(user.uid)
                        .setValue(userData)
            }
        }
    }
}
