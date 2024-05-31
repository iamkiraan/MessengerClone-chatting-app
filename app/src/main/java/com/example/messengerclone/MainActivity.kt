package com.example.messengerclone

import android.os.Bundle
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
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar : Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title =""
        val tabLayout : TabLayout = findViewById(R.id.tabLayout)
        val viewPager : ViewPager =  findViewById(R.id.ViewPager)
        val ViewPageAdapter = viewPagerAdapter(supportFragmentManager)
        ViewPageAdapter.addFragments(chatFragment(),"chats")
        ViewPageAdapter.addFragments(SearchFragment(),"Search")
        ViewPageAdapter.addFragments(SettingFragment(),"Setting")
        viewPager.adapter = ViewPageAdapter
        tabLayout.setupWithViewPager(viewPager)


    }
    internal class viewPagerAdapter(fragmentManager : FragmentManager):
            FragmentPagerAdapter(fragmentManager){
                private val fragments:ArrayList<Fragment> = ArrayList<Fragment>()
                private val titles:ArrayList<String> = ArrayList<String>()


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
        override fun getPageTitle(i:Int): CharSequence?{
            return titles[i]
        }

    }
}