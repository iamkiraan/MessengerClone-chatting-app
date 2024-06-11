package com.example.messengerclone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class LoginActivity : AppCompatActivity() {
    private lateinit var login : Button
    private val emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex()
    private lateinit var email : TextView
    private lateinit var password : TextView
    private lateinit var  auth : FirebaseAuth
    //private lateinit var refusers : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        login = findViewById(R.id.Button_login)
        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.email_login)
        password =  findViewById(R.id.password_login)
        login.setOnClickListener{
            loginUser()
        }
    }

    private fun loginUser() {
        val Email: String = email.text.toString()
        val Password: String = password.text.toString()
        if(!Email.matches(emailRegex)){
            Toast.makeText(this, "email is not in format", Toast.LENGTH_LONG).show()
        }else if (Email == "") {
            Toast.makeText(this, "email empty", Toast.LENGTH_LONG).show()
        } else if (Password == "" || Password.length < 8) {
            Toast.makeText(
                this,
                "make sure length more than 7 and password non empty",
                Toast.LENGTH_LONG
            ).show()
        }else{
            auth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener{
                    task ->
                    if(task.isSuccessful){
                        val intent = Intent(this,MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,"error message:"+task.exception!!.message,Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }
}