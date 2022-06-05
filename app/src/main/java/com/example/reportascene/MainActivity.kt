package com.example.reportascene

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance();

        tvRegister.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {

            if(editEmail.text.trim().toString().isNotEmpty() || editPassword.text.trim().toString().isNotEmpty()){
                signInUser(editEmail.text.trim().toString(), editPassword.text.trim().toString())
            }
            else{
                Toast.makeText(this,"Input Required",Toast.LENGTH_LONG).show();
            }
        }

    }

    fun signInUser(email:String, password:String){

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener (this) { task ->

                if(task.isSuccessful){
                    val intent = Intent(this, Report::class.java);
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "ERROR!!"+task.exception, Toast.LENGTH_LONG).show()
                }
            }
    }
}
