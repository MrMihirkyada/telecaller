package com.example.grandin.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.grandin.Fragment.Open_Fragment


//class TabAdapter(fm: ActivityDashboardBinding, private val imageUri: String) : FragmentPagerAdapter(fm) {
//    override fun getCount(): Int {
//        return 3
//    }
//
//    override fun getItem(position: Int): Fragment {
//        return when (position) {
//            0 -> RatioFragment().apply {
//                arguments = createArguments()
//            }
//            1 -> LayoutFragment().apply {
//                arguments = createArguments()
//            }
//            else -> LayoutFragment().apply {
//                arguments = createArguments()
//            }
//        }
//    }
//
//    private fun createArguments(): Bundle {
//        return Bundle().apply {
//            putString("imageUri", imageUri)
//        }
//    }
//}



class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Fragment {
        if (position == 0)
        {
            return Open_Fragment()
        }

        return null!!
    }
}