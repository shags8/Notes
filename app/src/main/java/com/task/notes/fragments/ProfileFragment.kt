package com.task.notes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.task.notes.MainActivity
import com.task.notes.R
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            (activity as MainActivity).navigateToFragment(NotesFragment())
        }
        val userImage = auth.currentUser?.photoUrl
        val profileImageView: CircleImageView = view.findViewById(R.id.UserProfilePhoto)
        Glide.with(this)
            .load(userImage)
            .into(profileImageView)

        view.findViewById<TextView>(R.id.name).text = auth.currentUser?.displayName
        view.findViewById<TextView>(R.id.email).text = auth.currentUser?.email
        view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.signout).setOnClickListener {
            auth.signOut()
            (activity as MainActivity).navigateToFragment(LoginFragment())
        }

        return view
    }

}