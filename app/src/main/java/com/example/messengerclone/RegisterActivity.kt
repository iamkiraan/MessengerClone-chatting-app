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
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var register : Button
    private lateinit var  auth : FirebaseAuth
    private lateinit var refusers : DatabaseReference
    private lateinit var username : TextView
    private lateinit var email : TextView
    private lateinit var password : TextView
    private val emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex()
    private var firebaseUserId : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        FirebaseApp.initializeApp(this)
        register = findViewById(R.id.Button_register)
        username = findViewById(R.id.username)
        email = findViewById(R.id.email_register)
        password = findViewById(R.id.password_register)


   auth = FirebaseAuth.getInstance()
        register.setOnClickListener{
            registerUser()
        }

    }

    private fun registerUser() {
        val Username: String = username.text.toString()
        val Email: String = email.text.toString()
        val Password: String = password.text.toString()
        if (Username == "") {
            Toast.makeText(this, "username empty", Toast.LENGTH_LONG).show()


        }
        else if(!Email.matches(emailRegex)){
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
            auth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener{
                    task ->
                    if(task.isSuccessful){
                        firebaseUserId = auth.currentUser!!.uid
                        refusers = FirebaseDatabase.getInstance().reference.child("users").child(firebaseUserId)
                        val userHashMap = HashMap<String,Any>()
                        userHashMap["uid"] = firebaseUserId
                        userHashMap["usernname"] = Username
                        userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/mechat-784a7.appspot.com/o/profileFb.png?alt=media&token=1bbf9432-d4bf-4ebe-85f7-a970b534e0c0"
                        userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/mechat-784a7.appspot.com/o/cover.jpg?alt=media&token=e2a53d69-e0ca-47da-b799-16ce3f0c8045"
                        userHashMap["status"] = "offline"
                        userHashMap["search"] = Username.toLowerCase()
                        userHashMap["facebook"] = "https://m.instagram.com"
                        userHashMap["website"] = "https://www.google.com"
                        refusers.updateChildren(userHashMap)
                            .addOnCompleteListener{
                                task->
                                if(task.isSuccessful){
                                    val intent = Intent(this,MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                            }


                    }
                    else {
                        Toast.makeText(this,"error message:"+task.exception!!.message,Toast.LENGTH_SHORT).show()
                    }
                    }
                }
        }
    }
