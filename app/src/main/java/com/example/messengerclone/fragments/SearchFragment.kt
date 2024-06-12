package com.example.messengerclone.fragments

import android.os.Bundle
import android.renderscript.Sampler.Value
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerclone.R
import com.example.messengerclone.adapterClasses.UserAdapter
import com.example.messengerclone.modelClasses.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private var userAdapter : UserAdapter? =null
    private var mUsers : List<Users>?=null
    private var recyclerView : RecyclerView?=null
    private var searchUser : EditText? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


       val view :View= inflater.inflate(R.layout.fragment_search, container, false)

        searchUser =view.findViewById<EditText>(R.id.searchUser)
        recyclerView = view.findViewById(R.id.searchList)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context)


        mUsers = ArrayList()
        retrieveallUsers()



        searchUser!!.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchForUser(s.toString().toLowerCase(Locale.ROOT))
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        return view
    }

    private fun retrieveallUsers() {
       var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
        var  refUsers = FirebaseDatabase.getInstance().reference.child("users")
        refUsers.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0:DataSnapshot){
                (mUsers as ArrayList<Users>).clear()
               if(searchUser!!.text.toString()==""){
                   for(snapshot in p0.children){
                       val user : Users? = snapshot.getValue(Users::class.java)
                       if(!(user!!.getUID()).equals(firebaseUserId)){
                           (mUsers as ArrayList<Users>).add(user)
                       }

                   }
                   userAdapter = UserAdapter(context!!,mUsers!!,false)
                   recyclerView!!.adapter = userAdapter
               }
               }
            override fun onCancelled(p0:DatabaseError){
            }
        })
    }
    private fun searchForUser(str:String){
        var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
        var  queryUsers = FirebaseDatabase.getInstance().reference
            .child("users").orderByChild("search")
            .startAt(str)
            .endAt(str +"\uf8ff")

       queryUsers.addValueEventListener(object:ValueEventListener{
           override fun onDataChange(p0: DataSnapshot) {
               (mUsers as ArrayList<Users>).clear()
               for(snapshot in p0.children){
                   val user : Users? = snapshot.getValue(Users::class.java)
                   if(!(user!!.getUID()).equals(firebaseUserId)){
                       (mUsers as ArrayList<Users>).add(user)
                   }

               }
               userAdapter = UserAdapter(context!!,mUsers!!,false)
               recyclerView!!.adapter = userAdapter
           }

           override fun onCancelled(p0: DatabaseError) {

           }

       })
    }

}