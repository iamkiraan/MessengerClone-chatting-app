package com.example.messengerclone

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager.widget.ViewPager
import com.example.messengerclone.fragments.SearchFragment
import com.example.messengerclone.fragments.SettingFragment
import com.example.messengerclone.fragments.chatFragment
import com.example.messengerclone.modelClasses.Users
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {
    var refUsers : DatabaseReference?=null
    var firebasUser: FirebaseUser?=null
    private lateinit var username : TextView
    private lateinit var profile_image : CircleImageView
    private lateinit var signout : CircleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val toolbar : Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title =""


        username = findViewById(R.id.username)
        profile_image = findViewById(R.id.profile_image)
        firebasUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("users").child(firebasUser!!.uid)

        val tabLayout : TabLayout = findViewById(R.id.tabLayout)
        val viewPager : ViewPager =  findViewById(R.id.ViewPager)
        val ViewPageAdapter = ViewPagerAdapter(supportFragmentManager)


        ViewPageAdapter.addFragments(chatFragment(),"chats")
        ViewPageAdapter.addFragments(SearchFragment(),"Search")
        ViewPageAdapter.addFragments(SettingFragment(),"Setting")
        viewPager.adapter = ViewPageAdapter
        tabLayout.setupWithViewPager(viewPager)

        //display the username and profile picture
        refUsers!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val user: Users? = p0.getValue(Users::class.java)
                        username.text = user!!.getUsername()
                        Picasso.get().load(user.getProfile()).placeholder(R.drawable.profil).into(profile_image)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                // Handle possible errors.
            }
        })



    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//       menuInflater.inflate(R.menu.menu_main,menu)
//        return true
//    }
//    override fun onOptionsItemSelected(item:MenuItem):Boolean{
//        return when(item.itemId){
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }

    }
    internal class ViewPagerAdapter(fragmentManager : FragmentManager):
            FragmentPagerAdapter(fragmentManager){
                private val fragments:ArrayList<Fragment>
                private val titles:ArrayList<String>
        init{
            fragments =ArrayList<Fragment>()
            titles = ArrayList<String>()
        }



        override fun getCount(): Int {
           return fragments.size

        }

        override fun getItem(position: Int): Fragment {
           return fragments[position]
        }
        fun addFragments(fragment: Fragment,title:String){
            fragments.add(fragment)
            titles.add(title)
        }
        override fun getPageTitle(i:Int): CharSequence {
            return titles[i]
        }

    }
