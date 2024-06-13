package com.example.messengerclone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.messengerclone.R
import com.example.messengerclone.databinding.FragmentSettingBinding
import com.example.messengerclone.modelClasses.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class SettingFragment : Fragment() {
    private var userReference: DatabaseReference? = null
    private var firebaseUser: FirebaseUser? = null
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "SettingFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize views
        val username: TextView = binding.usernameSetting
        val cover: ImageView = binding.coverImageSetting
        val profile: ImageView = binding.profileImgSetting

        // Initialize Firebase
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            val userId = firebaseUser!!.uid
            Log.d(TAG, "FirebaseUser ID: $userId")

            userReference = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
            Log.d(TAG, "Database Path: ${userReference!!.path}")

            userReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(TAG, "DataSnapshot exists: ${dataSnapshot.exists()}")
                    Log.d(TAG, "DataSnapshot value: ${dataSnapshot.value}")

                    if (dataSnapshot.exists()) {
                        val user: Users? = dataSnapshot.getValue(Users::class.java)
                        if (user != null) {
                            Log.d(TAG, "User data: $user")
                            username.text = user.getUsername()
                            Picasso.get().load(user.getProfile()).into(profile)
                            Picasso.get().load(user.getCover()).into(cover)
                        } else {
                            Log.d(TAG, "User data is null")
                        }
                    } else {
                        Log.d(TAG, "DataSnapshot does not exist")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e(TAG, "Database error: ${databaseError.message}")
                }
            })
        } else {
            Log.e(TAG, "FirebaseUser is null")
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding to avoid memory leaks
    }
}
