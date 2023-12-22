package com.example.uts.userpanel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.lang.reflect.Member

class UserPanelTabAdapter( fm: FragmentManager,private val context: Context) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        // Retrieve the user role from SharedPreferences
        val userRole = getUserRoleFromPreferences()

        // Return the appropriate number of tabs based on the user role
        return if (userRole == "ADMIN") {
            2
        } else {
            1
        }
    }

    override fun getItem(position: Int): Fragment {
        val userRole = getUserRoleFromPreferences()
        return when {
            userRole == "ADMIN" && position == 0 -> MemberPanel()
            userRole == "ADMIN" && position == 1 -> AdminFragment()
            userRole != "ADMIN" && position == 0 -> MemberPanel()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // Return the appropriate tab title based on the user role
        return when {
            position == 0 && getUserRoleFromPreferences() != "ADMIN" -> "MEMBER"
            position == 0 && getUserRoleFromPreferences() == "ADMIN" -> "MEMBER"
            position == 1 && getUserRoleFromPreferences() == "ADMIN" -> "ADMIN"
            else -> null
        }
    }

    private fun getUserRoleFromPreferences(): String {
        val sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
        Log.d("www",sharedPreferences.getString("ROLE", null).toString())
        return sharedPreferences.getString("ROLE", null).toString()
    }
}