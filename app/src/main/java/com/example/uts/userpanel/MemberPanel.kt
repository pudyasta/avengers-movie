package com.example.uts.userpanel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uts.*
import com.example.uts.databinding.ActivityMainBinding
import com.example.uts.databinding.FragmentMemberPanelBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MemberPanel.newInstance] factory method to
 * create an instance of this fragment.
 */
class MemberPanel : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val binding = FragmentMemberPanelBinding.inflate(inflater, container, false)
        val view = binding.root
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("shared", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY", null)

        if (savedString == null) {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        } else {
            setCurrentFragment(HomeFragment())

            with(binding) {
                bottomNav.background = null
                bottomNav.setOnNavigationItemSelectedListener {
                    when (it.itemId) {
                        R.id.home -> setCurrentFragment(HomeFragment())
                        R.id.profile -> setCurrentFragment(ProfileFragment())
                    }
                    true
                }
                floatBar.setOnClickListener{
                    val intent = Intent(requireContext(), HeroActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        // Inflate the layout for this fragment
        return view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MemberPanel.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MemberPanel().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}