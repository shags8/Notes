package com.task.notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.task.notes.MainActivity
import com.task.notes.R
import com.task.notes.db.User
import com.task.notes.db.UserDao
import com.task.notes.db.UserDatabase
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userDao: UserDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        auth = FirebaseAuth.getInstance()
        userDao = UserDatabase.getDatabase(requireContext()).userDao()

        val name = auth.currentUser?.displayName
        val email = auth.currentUser?.email
        view.findViewById<TextView>(R.id.name).text = name
        view.findViewById<TextView>(R.id.email).text = email

        view.findViewById<Button>(R.id.Register).setOnClickListener {
            if(view.findViewById<EditText>(R.id.name).text.toString().isNotEmpty() && view.findViewById<EditText>(R.id.email).text.toString().isNotEmpty())
            {
                val user = User(username =  view.findViewById<EditText>(R.id.name).text.toString(), email = view.findViewById<EditText>(R.id.email).text.toString())
             lifecycleScope.launch {
                 userDao.insert(user)
                 }
                (activity as MainActivity).navigateToFragment(NotesFragment())
            }
            else{
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }



        return view
    }
}