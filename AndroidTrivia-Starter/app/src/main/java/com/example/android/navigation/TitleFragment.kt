package com.example.android.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController

/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_title,null)

        val btnPlay:Button=view.findViewById(R.id.playButton)
        btnPlay.setOnClickListener {
            it.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)  //按鈕觸發navigation
        }

        return view


//        return TextView(activity).apply {
//            setText(R.string.hello_blank_fragment)
//        }
    }

}
