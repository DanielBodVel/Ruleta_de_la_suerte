package com.example.ruletadelasuerte

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val ARG_TEXT = "param1"
private const val ARG_ACTIVITY = "param2"

class ButtonFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_TEXT)
            param2 = it.getString(ARG_ACTIVITY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_button, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ButtonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TEXT, param1)
                    putString(ARG_ACTIVITY, param2)
                }
            }
    }
}