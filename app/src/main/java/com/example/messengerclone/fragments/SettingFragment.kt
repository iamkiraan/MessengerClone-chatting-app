package com.example.messengerclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.messengerclone.R
import com.example.messengerclone.modelClasses.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class SettingFragment : Fragment() {
    private var userReference: DatabaseReference? = null
    private var firebaseUser: FirebaseUser? = null

    private lateinit var username: TextView
    private lateinit var cover: ImageView
    private lateinit var profile: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_setting, container, false)
        username = view.findViewById(R.id.username_setting)
        cover = view.findViewById(R.id.cover_image_setting)
        profile = view.findViewById(R.id.profile_img_setting)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        userReference = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        userReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val user: Users? = p0.getValue(Users::class.java)
                    if (context != null && user != null) {
                        username.text = user.getUsername()
                        Picasso.get().load(user.getProfile()).into(profile)
                        Picasso.get().load(user.getCover()).into(cover)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })

        return view
    }
}
